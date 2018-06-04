package com.virginpulse.amazon.sns.javamessaging;

import com.amazonaws.util.VersionInfoUtils;

public class SNSExtendedClientConstants {
    public static final String RESERVED_ATTRIBUTE_NAME = "SQSLargePayloadSize";
    public static final int MAX_ALLOWED_ATTRIBUTES = 10 - 1; // 10 for SQS, 1 for the reserved attribute
    public static final int DEFAULT_MESSAGE_SIZE_THRESHOLD = 262144;
    public static final String S3_BUCKET_NAME_MARKER = "-..s3BucketName..-";
    public static final String S3_KEY_MARKER = "-..s3Key..-";

    static final String USER_AGENT_HEADER = AmazonSNSExtendedClient.class.getSimpleName() + "/" + VersionInfoUtils.getVersion();
}
