package com.pedrohubner.facades;

import com.pedrohubner.message.models.Messages;
import com.pedrohubner.message.models.MessagesDAO;
import com.pedrohubner.message.services.MessagesService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Component
@AllArgsConstructor
public class MessagesFacade {

    private final MessagesService messagesService;

    public Mono<Messages> insertNewChat(MessagesDAO messagesDAO) {
        return messagesService.mapToMessages(messagesDAO);
    }

    public Flux<Messages> findMessagesByChatId(String chatId) {
        return messagesService.findMessagesByChatId(chatId);
    }
}
