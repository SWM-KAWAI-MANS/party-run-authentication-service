package online.partyrun.partyrunauthenticationservice.domain.member.service;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;

import online.partyrun.partyrunauthenticationservice.domain.member.event.Event;
import online.partyrun.partyrunauthenticationservice.domain.member.event.MemberEventPublisher;

import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.transaction.event.TransactionalEventListener;

@Async
@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class NotificationService {

    MemberEventPublisher publisher;

    @TransactionalEventListener
    public void notifyMemberEvent(Event event) {
        publisher.publish(event);
    }
}
