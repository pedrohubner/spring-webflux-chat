package com.pedrohubner.message.services;

import com.pedrohubner.message.models.Messages;
import com.pedrohubner.message.models.MessagesDAO;
import com.pedrohubner.message.repositories.MessagesRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
@AllArgsConstructor
public class MessagesService {

    private final MessagesRepository messagesRepository;

    public Mono<Messages> mapToMessages(MessagesDAO messagesDAO) {
        Messages newMessage = Messages.builder()
                .message(messagesDAO.getMessage())
                .addressee(messagesDAO.getAddressee())
                .sender(messagesDAO.getSender())
                .channelId(messagesDAO.getChannelId())
                .build();
        return messagesRepository.insert(newMessage);
    }

    public Flux<Messages> findMessagesByChatId(String chatId) {
        return messagesRepository.findWithTailableCursorByChannelId(chatId);
    }
}
