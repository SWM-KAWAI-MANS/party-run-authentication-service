package online.partyrun.partyrunauthenticationservice.domain.member.event;

import com.fasterxml.jackson.core.JsonProcessingException;

import io.awspring.cloud.sns.core.SnsNotification;
import io.awspring.cloud.sns.core.SnsOperations;
import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;

import online.partyrun.partyrunauthenticationservice.global.exception.InternalServerErrorException;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import software.amazon.awssdk.services.sns.model.PublishRequest;

@Component
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class MemberEventPublisher {

    SnsOperations snsOperations;
    String arn;

    public MemberEventPublisher(
            @Value("${spring.cloud.aws.sns.arn}") String arn, SnsOperations snsOperations) {
        this.arn = arn;
        this.snsOperations = snsOperations;
    }

    public void publish(Event event) {
        final SnsNotification<Event> notification = SnsNotification.of(event);
        snsOperations.sendNotification(arn, notification);
    }
}
