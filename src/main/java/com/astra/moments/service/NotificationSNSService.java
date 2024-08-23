package com.astra.moments.service;

import io.awspring.cloud.sns.core.SnsTemplate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import software.amazon.awssdk.services.sns.SnsClient;
import software.amazon.awssdk.services.sns.model.*;

@Service
public class NotificationSNSService {
    private Logger LOGGER = LoggerFactory.getLogger(NotificationSNSService.class);
    private final SnsTemplate snsTemplate;
    private final SnsClient snsClient;

    @Value("${sns.topic.arn}")
    private String arnTopic;

    public NotificationSNSService(SnsTemplate snsTemplate, SnsClient snsClient){
        this.snsTemplate = snsTemplate;
        this.snsClient = snsClient;
    }

    public void sendNotification(String message) {
        try {
            PublishRequest request = PublishRequest.builder()
                    .message(message)
                    .topicArn(arnTopic)
                    .build();

            PublishResponse result = snsClient.publish(request);
            LOGGER.info(result.messageId() + " Message sent. Status is " + result.sdkHttpResponse().statusCode());

        } catch (SnsException e) {
            LOGGER.error(e.awsErrorDetails().errorMessage());
        }
    }



    public void subscribeService(String email) {
        try {
            SubscribeRequest request = SubscribeRequest.builder()
                    .protocol("email")
                    .endpoint(email)
                    .returnSubscriptionArn(true)
                    .topicArn(arnTopic)
                    .build();

            SubscribeResponse result = snsClient.subscribe(request);
            LOGGER.info("Subscription ARN: " + result.subscriptionArn() + "\n\n Status is " + result.sdkHttpResponse().statusCode());

        } catch (SnsException e) {
            LOGGER.error(e.awsErrorDetails().errorMessage());
        }
    }

    public String createSNSTopic(String topicName ) {
        CreateTopicResponse result = null;
        try {
            CreateTopicRequest request = CreateTopicRequest.builder()
                    .name(topicName)
                    .build();

            result = snsClient.createTopic(request);
            return result.topicArn();
        } catch (SnsException e) {
            LOGGER.error(e.awsErrorDetails().errorMessage());
        }
        return result.topicArn();
    }

    public void listSNSTopics() {
        try {
            ListTopicsRequest request = ListTopicsRequest.builder()
                    .build();

            ListTopicsResponse result = snsClient.listTopics(request);
            LOGGER.info("Status was " + result.sdkHttpResponse().statusCode() + "\n\nTopics\n\n" + result.topics());

        } catch (SnsException e) {
            LOGGER.error(e.awsErrorDetails().errorMessage());
        }
    }

    public void unSub(String subscriptionArn) {

        try {
            UnsubscribeRequest request = UnsubscribeRequest.builder()
                    .subscriptionArn(subscriptionArn)
                    .build();

            UnsubscribeResponse result = snsClient.unsubscribe(request);

            LOGGER.info("\n\nStatus was " + result.sdkHttpResponse().statusCode()
                    + "\n\nSubscription was removed for " + request.subscriptionArn());

        } catch (SnsException e) {
            LOGGER.error(e.awsErrorDetails().errorMessage());
        }
    }


    public void deleteSNSTopic(String topicArn ) {

        try {
            DeleteTopicRequest request = DeleteTopicRequest.builder()
                    .topicArn(topicArn)
                    .build();

            DeleteTopicResponse result = snsClient.deleteTopic(request);
            System.out.println("\n\nStatus was " + result.sdkHttpResponse().statusCode());

        } catch (SnsException e) {
            System.err.println(e.awsErrorDetails().errorMessage());
            System.exit(1);
        }
    }
}
