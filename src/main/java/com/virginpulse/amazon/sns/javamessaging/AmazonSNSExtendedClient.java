package com.virginpulse.amazon.sns.javamessaging;

import com.amazonaws.AmazonClientException;
import com.amazonaws.AmazonServiceException;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.amazonaws.services.sns.AmazonSNS;
import com.amazonaws.services.sns.model.PublishRequest;
import com.amazonaws.services.sns.model.PublishResult;
import com.amazonaws.services.sns.model.MessageAttributeValue;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.io.*;
import java.nio.ByteBuffer;
import java.nio.charset.StandardCharsets;
import java.util.Map;
import java.util.UUID;

public class AmazonSNSExtendedClient extends AmazonSNSExtendedClientBase implements AmazonSNS {
    private static final Log LOG = LogFactory.getLog(AmazonSNSExtendedClient.class);

    private ExtendedClientConfiguration clientConfiguration;

    public AmazonSNSExtendedClient(AmazonSNS snsClient) { this(snsClient, new ExtendedClientConfiguration()); }

    public AmazonSNSExtendedClient(AmazonSNS snsClient, ExtendedClientConfiguration extendedClientConfiguration) {
        super(snsClient);
        this.clientConfiguration = extendedClientConfiguration;
    }

    @Override
    public PublishResult publish(PublishRequest publishRequest) {

        if (publishRequest == null) {
            String errorMessage = "publishRequest cannot be null.";
            LOG.error(errorMessage);
            throw new AmazonClientException(errorMessage);
        }

        publishRequest.getRequestClientOptions().appendUserAgent(SNSExtendedClientConstants.USER_AGENT_HEADER);

        if (!clientConfiguration.isLargePayloadSupportEnabled()) {
            return super.publish(publishRequest);
        }

        if (publishRequest.getMessage() == null || "".equals(publishRequest.getMessage())) {
            String errorMessage = "message cannot be null or empty.";
            LOG.error(errorMessage);
            throw new AmazonClientException(errorMessage);
        }

        if (clientConfiguration.isAlwaysThroughS3() || isLarge(publishRequest)) {
            publishRequest = storeMessageInS3(publishRequest);
        }

        return super.publish(publishRequest);
    }

    private boolean isLarge(PublishRequest publishRequest) {
        int msgAttributesSize = getMsgAttributesSize(publishRequest.getMessageAttributes());
        long msgBodySize = getStringSizeInBytes(publishRequest.getMessage());
        long totalMsgSize = msgAttributesSize + msgBodySize;
        return (totalMsgSize > clientConfiguration.getMessageSizeThreshold());
    }

    private int getMsgAttributesSize(Map<String, MessageAttributeValue> msgAttributes) {
        int totalMsgAttributesSize = 0;
        for (Map.Entry<String, MessageAttributeValue> entry : msgAttributes.entrySet()) {
            totalMsgAttributesSize += getStringSizeInBytes(entry.getKey());

            MessageAttributeValue entryVal = entry.getValue();
            if (entryVal.getDataType() != null) {
                totalMsgAttributesSize += getStringSizeInBytes(entryVal.getDataType());
            }

            String stringVal = entryVal.getStringValue();
            if (stringVal != null) {
                totalMsgAttributesSize += getStringSizeInBytes(entryVal.getStringValue());
            }

            ByteBuffer binaryVal = entryVal.getBinaryValue();
            if (binaryVal != null) {
                totalMsgAttributesSize += binaryVal.array().length;
            }
        }
        return totalMsgAttributesSize;
    }

    private static long getStringSizeInBytes(String str) {
        CountingOutputStream counterOutputStream = new CountingOutputStream();
        try {
            Writer writer = new OutputStreamWriter(counterOutputStream, "UTF-8");
            writer.write(str);
            writer.flush();
            writer.close();
        } catch (IOException e) {
            String errorMessage = "Failed to calculate the size of message payload.";
            LOG.error(errorMessage, e);
            throw new AmazonClientException(errorMessage, e);
        }
        return counterOutputStream.getTotalSize();
    }

    private PublishRequest storeMessageInS3(PublishRequest publishRequest) {

        checkMessageAttributes(publishRequest.getMessageAttributes());

        String s3Key = UUID.randomUUID().toString();

        // Read the content of the message from message body
        String messageContentStr = publishRequest.getMessage();

        Long messageContentSize = getStringSizeInBytes(messageContentStr);

        // Add a new message attribute as a flag
        MessageAttributeValue messageAttributeValue = new MessageAttributeValue();
        messageAttributeValue.setDataType("Number");
        messageAttributeValue.setStringValue(messageContentSize.toString());
        publishRequest.addMessageAttributesEntry(SNSExtendedClientConstants.RESERVED_ATTRIBUTE_NAME, messageAttributeValue);

        // Store the message content in S3.
        storeTextInS3(s3Key, messageContentStr, messageContentSize);
        LOG.info("S3 object created, Bucket name: " + clientConfiguration.getS3BucketName() + ", Object key: " + s3Key
                + ".");

        // Convert S3 pointer (bucket name, key, etc) to JSON string
        MessageS3Pointer s3Pointer = new MessageS3Pointer(clientConfiguration.getS3BucketName(), s3Key);

        String s3PointerStr = getJSONFromS3Pointer(s3Pointer);

        // Storing S3 pointer in the message body.
        publishRequest.setMessage(s3PointerStr);

        return publishRequest;
    }

    private void checkMessageAttributes(Map<String, MessageAttributeValue> messageAttributes) {
        int msgAttributesSize = getMsgAttributesSize(messageAttributes);
        if (msgAttributesSize > clientConfiguration.getMessageSizeThreshold()) {
            String errorMessage = "Total size of Message attributes is " + msgAttributesSize
                    + " bytes which is larger than the threshold of " + clientConfiguration.getMessageSizeThreshold()
                    + " Bytes. Consider including the payload in the message body instead of message attributes.";
            LOG.error(errorMessage);
            throw new AmazonClientException(errorMessage);
        }

        int messageAttributesNum = messageAttributes.size();
        if (messageAttributesNum > SNSExtendedClientConstants.MAX_ALLOWED_ATTRIBUTES) {
            String errorMessage = "Number of message attributes [" + messageAttributesNum
                    + "] exceeds the maximum allowed for large-payload messages ["
                    + SNSExtendedClientConstants.MAX_ALLOWED_ATTRIBUTES + "].";
            LOG.error(errorMessage);
            throw new AmazonClientException(errorMessage);
        }

        MessageAttributeValue largePayloadAttributeValue = messageAttributes
                .get(SNSExtendedClientConstants.RESERVED_ATTRIBUTE_NAME);
        if (largePayloadAttributeValue != null) {
            String errorMessage = "Message attribute name " + SNSExtendedClientConstants.RESERVED_ATTRIBUTE_NAME
                    + " is reserved for use by SNS extended client.";
            LOG.error(errorMessage);
            throw new AmazonClientException(errorMessage);
        }
    }

    private void storeTextInS3(String s3Key, String messageContentStr, Long messageContentSize) {
        InputStream messageContentStream = new ByteArrayInputStream(messageContentStr.getBytes(StandardCharsets.UTF_8));
        ObjectMetadata messageContentStreamMetadata = new ObjectMetadata();
        messageContentStreamMetadata.setContentLength(messageContentSize);
        PutObjectRequest putObjectRequest = new PutObjectRequest(clientConfiguration.getS3BucketName(), s3Key,
                messageContentStream, messageContentStreamMetadata);
        try {
            clientConfiguration.getAmazonS3Client().putObject(putObjectRequest);
        } catch (AmazonServiceException e) {
            String errorMessage = "Failed to store the message content in an S3 object. SNS message was not sent.";
            LOG.error(errorMessage, e);
            throw new AmazonServiceException(errorMessage, e);
        } catch (AmazonClientException e) {
            String errorMessage = "Failed to store the message content in an S3 object. SNS message was not sent.";
            LOG.error(errorMessage, e);
            throw new AmazonClientException(errorMessage, e);
        }
    }

    private String getJSONFromS3Pointer(MessageS3Pointer s3Pointer) {
        String s3PointerStr = null;
        try {
            JsonDataConverter jsonDataConverter = new JsonDataConverter();
            s3PointerStr = jsonDataConverter.serializeToJson(s3Pointer);
        } catch (Exception e) {
            String errorMessage = "Failed to convert S3 object pointer to text. Message was not sent.";
            LOG.error(errorMessage, e);
            throw new AmazonClientException(errorMessage, e);
        }
        return s3PointerStr;
    }
}
