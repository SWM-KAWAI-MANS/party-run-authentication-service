package online.partyrun.partyrunauthenticationservice.domain.member.event;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import software.amazon.awssdk.services.sns.SnsClient;
import software.amazon.awssdk.services.sns.model.PublishRequest;

@Component
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class MemberEventPublisher {

    String arn;
    SnsClient snsClient;
    ObjectMapper objectMapper;

    public MemberEventPublisher(
            @Value("${aws.sns.arn}") String arn, SnsClient snsClient, ObjectMapper objectMapper) {
        this.arn = arn;
        this.snsClient = snsClient;
        this.objectMapper = objectMapper;
    }

    public void publish(Event event) {
        try {
            final PublishRequest request =
                    PublishRequest.builder()
                            .topicArn(this.arn)
                            .message(objectMapper.writeValueAsString(event))
                            .build();
            snsClient.publish(request);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(String.format("%s 이벤트를 발행하는데에 실패했습니다.", event));
        }
    }
}
