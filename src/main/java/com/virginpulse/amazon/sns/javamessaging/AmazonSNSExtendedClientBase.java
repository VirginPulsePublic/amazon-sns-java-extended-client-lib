package com.virginpulse.amazon.sns.javamessaging;

import com.amazonaws.AmazonWebServiceRequest;
import com.amazonaws.ResponseMetadata;
import com.amazonaws.regions.Region;
import com.amazonaws.services.sns.AmazonSNS;
import com.amazonaws.services.sns.model.*;

import java.util.List;

abstract class AmazonSNSExtendedClientBase implements AmazonSNS {
    AmazonSNS amazonSnsToBeExtended;

    public AmazonSNSExtendedClientBase(AmazonSNS snsClient) {
        amazonSnsToBeExtended = snsClient;
    }

    /** @deprecated */
    @Deprecated
    public void setEndpoint(String var1) { throw new RuntimeException("Not Implement"); }

    /** @deprecated */
    @Deprecated
    public void setRegion(Region var1) {
        throw new RuntimeException("Not Implement");
    }

    public AddPermissionResult addPermission(AddPermissionRequest addPermissionRequest) {
        return amazonSnsToBeExtended.addPermission(addPermissionRequest);
    }

    public AddPermissionResult addPermission(String string, String string1, List<String> list, List<String> list1) {
        return amazonSnsToBeExtended.addPermission(string, string1, list, list1);
    }

    public CheckIfPhoneNumberIsOptedOutResult checkIfPhoneNumberIsOptedOut(CheckIfPhoneNumberIsOptedOutRequest checkIfPhoneNumberIsOptedOutRequest) {
        return amazonSnsToBeExtended.checkIfPhoneNumberIsOptedOut(checkIfPhoneNumberIsOptedOutRequest);
    }

    public ConfirmSubscriptionResult confirmSubscription(ConfirmSubscriptionRequest confirmSubscriptionRequest) {
        return amazonSnsToBeExtended.confirmSubscription(confirmSubscriptionRequest);
    }

    public ConfirmSubscriptionResult confirmSubscription(String s1, String s2, String s3) {
        return amazonSnsToBeExtended.confirmSubscription(s1,s2,s3);
    }

    public ConfirmSubscriptionResult confirmSubscription(String s1, String s2) {
        return amazonSnsToBeExtended.confirmSubscription(s1, s2);
    }

    public CreatePlatformApplicationResult createPlatformApplication(CreatePlatformApplicationRequest createPlatformApplicationRequest) {
        return amazonSnsToBeExtended.createPlatformApplication(createPlatformApplicationRequest);
    }

    public CreatePlatformEndpointResult createPlatformEndpoint(CreatePlatformEndpointRequest createPlatformEndpointRequest) {
        return amazonSnsToBeExtended.createPlatformEndpoint(createPlatformEndpointRequest);
    }

    public CreateTopicResult createTopic(CreateTopicRequest createTopicRequest) {
        return amazonSnsToBeExtended.createTopic(createTopicRequest);
    }

    public CreateTopicResult createTopic(String s1) {
        return amazonSnsToBeExtended.createTopic(s1);
    }

    public DeleteEndpointResult deleteEndpoint(DeleteEndpointRequest deleteEndpointRequest) {
        return amazonSnsToBeExtended.deleteEndpoint(deleteEndpointRequest);
    }

    public DeletePlatformApplicationResult deletePlatformApplication(DeletePlatformApplicationRequest deletePlatformApplicationRequest) {
        return amazonSnsToBeExtended.deletePlatformApplication(deletePlatformApplicationRequest);
    }

    public DeleteTopicResult deleteTopic(DeleteTopicRequest deleteTopicRequest) {
        return amazonSnsToBeExtended.deleteTopic(deleteTopicRequest);
    }

    public DeleteTopicResult deleteTopic(String s1) {
        return amazonSnsToBeExtended.deleteTopic(s1);
    }

    public GetEndpointAttributesResult getEndpointAttributes(GetEndpointAttributesRequest getEndpointAttributesRequest) {
        return amazonSnsToBeExtended.getEndpointAttributes(getEndpointAttributesRequest);
    }

    public GetPlatformApplicationAttributesResult getPlatformApplicationAttributes(GetPlatformApplicationAttributesRequest getPlatformApplicationAttributesRequest) {
        return amazonSnsToBeExtended.getPlatformApplicationAttributes(getPlatformApplicationAttributesRequest);
    }

    public GetSMSAttributesResult getSMSAttributes(GetSMSAttributesRequest getSMSAttributesRequest) {
        return amazonSnsToBeExtended.getSMSAttributes(getSMSAttributesRequest);
    }

    public GetSubscriptionAttributesResult getSubscriptionAttributes(GetSubscriptionAttributesRequest getSubscriptionAttributesRequest) {
        return amazonSnsToBeExtended.getSubscriptionAttributes(getSubscriptionAttributesRequest);
    }

    public GetSubscriptionAttributesResult getSubscriptionAttributes(String s1) {
        return amazonSnsToBeExtended.getSubscriptionAttributes(s1);
    }

    public GetTopicAttributesResult getTopicAttributes(GetTopicAttributesRequest getTopicAttributesRequest) {
        return amazonSnsToBeExtended.getTopicAttributes(getTopicAttributesRequest);
    }

    public GetTopicAttributesResult getTopicAttributes(String s1) {
        return amazonSnsToBeExtended.getTopicAttributes(s1);
    }

    public ListEndpointsByPlatformApplicationResult listEndpointsByPlatformApplication(ListEndpointsByPlatformApplicationRequest listEndpointsByPlatformApplicationRequest) {
        return amazonSnsToBeExtended.listEndpointsByPlatformApplication(listEndpointsByPlatformApplicationRequest);
    }

    public ListPhoneNumbersOptedOutResult listPhoneNumbersOptedOut(ListPhoneNumbersOptedOutRequest listPhoneNumbersOptedOutRequest) {
        return amazonSnsToBeExtended.listPhoneNumbersOptedOut(listPhoneNumbersOptedOutRequest);
    }

    public ListPlatformApplicationsResult listPlatformApplications(ListPlatformApplicationsRequest listPlatformApplicationsRequest) {
        return amazonSnsToBeExtended.listPlatformApplications(listPlatformApplicationsRequest);
    }

    public ListPlatformApplicationsResult listPlatformApplications() {
        return amazonSnsToBeExtended.listPlatformApplications();
    }

    public ListSubscriptionsResult listSubscriptions(ListSubscriptionsRequest listSubscriptionsRequest) {
        return amazonSnsToBeExtended.listSubscriptions(listSubscriptionsRequest);
    }

    public ListSubscriptionsResult listSubscriptions() {
        return amazonSnsToBeExtended.listSubscriptions();
    }

    public ListSubscriptionsResult listSubscriptions(String s1) {
        return amazonSnsToBeExtended.listSubscriptions(s1);
    }

    public ListSubscriptionsByTopicResult listSubscriptionsByTopic(ListSubscriptionsByTopicRequest listSubscriptionsByTopicRequest) {
        return amazonSnsToBeExtended.listSubscriptionsByTopic(listSubscriptionsByTopicRequest);
    }

    public ListSubscriptionsByTopicResult listSubscriptionsByTopic(String s1) {
        return amazonSnsToBeExtended.listSubscriptionsByTopic(s1);
    }

    public ListSubscriptionsByTopicResult listSubscriptionsByTopic(String s1, String s2) {
        return amazonSnsToBeExtended.listSubscriptionsByTopic(s1, s2);
    }

    public ListTagsForResourceResult listTagsForResource(ListTagsForResourceRequest listTagsForResourceRequest) {
        return amazonSnsToBeExtended.listTagsForResource(listTagsForResourceRequest);
    }

    public ListTopicsResult listTopics(ListTopicsRequest listTopicsRequest) {
        return amazonSnsToBeExtended.listTopics(listTopicsRequest);
    }

    public ListTopicsResult listTopics() {
        return amazonSnsToBeExtended.listTopics();
    }

    public ListTopicsResult listTopics(String s1) {
        return amazonSnsToBeExtended.listTopics(s1);
    }

    public OptInPhoneNumberResult optInPhoneNumber(OptInPhoneNumberRequest optInPhoneNumberRequest) {
        return amazonSnsToBeExtended.optInPhoneNumber(optInPhoneNumberRequest);
    }

    public PublishResult publish(PublishRequest publishRequest) {
        return amazonSnsToBeExtended.publish(publishRequest);
    }

    public PublishResult publish(String s1, String s2) { throw new RuntimeException("Not Implement"); }

    public PublishResult publish(String s1, String s2, String s3) {
        throw new RuntimeException("Not Implement");
    }

    public RemovePermissionResult removePermission(RemovePermissionRequest removePermissionRequest) {
        return amazonSnsToBeExtended.removePermission(removePermissionRequest);
    }

    public RemovePermissionResult removePermission(String s1, String s2) {
        return amazonSnsToBeExtended.removePermission(s1, s2);
    }

    public SetEndpointAttributesResult setEndpointAttributes(SetEndpointAttributesRequest setEndpointAttributesRequest) {
        return amazonSnsToBeExtended.setEndpointAttributes(setEndpointAttributesRequest);
    }

    public SetPlatformApplicationAttributesResult setPlatformApplicationAttributes(SetPlatformApplicationAttributesRequest setPlatformApplicationAttributesRequest) {
        return amazonSnsToBeExtended.setPlatformApplicationAttributes(setPlatformApplicationAttributesRequest);
    }

    public SetSMSAttributesResult setSMSAttributes(SetSMSAttributesRequest setSMSAttributesRequest) {
        return amazonSnsToBeExtended.setSMSAttributes(setSMSAttributesRequest);
    }

    public SetSubscriptionAttributesResult setSubscriptionAttributes(SetSubscriptionAttributesRequest setSubscriptionAttributesRequest) {
        return amazonSnsToBeExtended.setSubscriptionAttributes(setSubscriptionAttributesRequest);
    }

    public SetSubscriptionAttributesResult setSubscriptionAttributes(String s1, String s2, String s3) {
        return amazonSnsToBeExtended.setSubscriptionAttributes(s1, s2, s3);
    }

    public SetTopicAttributesResult setTopicAttributes(SetTopicAttributesRequest setTopicAttributesRequest) {
        return amazonSnsToBeExtended.setTopicAttributes(setTopicAttributesRequest);
    }

    public SetTopicAttributesResult setTopicAttributes(String s1, String s2, String s3) {
        return amazonSnsToBeExtended.setTopicAttributes(s1, s2, s3);
    }

    public SubscribeResult subscribe(SubscribeRequest subscribeRequest) {
        return amazonSnsToBeExtended.subscribe(subscribeRequest);
    }

    public SubscribeResult subscribe(String s1, String s2, String s3) {
        return amazonSnsToBeExtended.subscribe(s1, s2, s3);
    }

    public TagResourceResult tagResource(TagResourceRequest tagResourceRequest) {
        return amazonSnsToBeExtended.tagResource(tagResourceRequest);
    }

    public UnsubscribeResult unsubscribe(UnsubscribeRequest unsubscribeRequest) {
        return amazonSnsToBeExtended.unsubscribe(unsubscribeRequest);
    }

    public UnsubscribeResult unsubscribe(String s1) {
        return amazonSnsToBeExtended.unsubscribe(s1);
    }

    public UntagResourceResult untagResource(UntagResourceRequest untagResourceRequest) {
        return amazonSnsToBeExtended.untagResource(untagResourceRequest);
    }

    public void shutdown() {
        amazonSnsToBeExtended.shutdown();
    }

    public ResponseMetadata getCachedResponseMetadata(AmazonWebServiceRequest amazonWebServiceRequest) {
        return amazonSnsToBeExtended.getCachedResponseMetadata(amazonWebServiceRequest);
    }

}
