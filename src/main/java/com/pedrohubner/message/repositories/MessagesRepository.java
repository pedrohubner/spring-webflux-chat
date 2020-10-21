package com.pedrohubner.message.repositories;

import com.pedrohubner.message.models.Messages;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.data.mongodb.repository.Tailable;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;

@Repository
public interface MessagesRepository extends ReactiveMongoRepository<Messages, String> {

    @Tailable
    Flux<Messages> findWithTailableCursorByChannelId(String channelId);
}
