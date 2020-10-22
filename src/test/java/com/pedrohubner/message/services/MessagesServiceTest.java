package com.pedrohubner.message.services;

import com.pedrohubner.message.models.Messages;
import com.pedrohubner.message.models.MessagesDAO;
import com.pedrohubner.message.repositories.MessagesRepository;
import org.junit.Assert;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class MessagesServiceTest {

    @Mock
    MessagesRepository messagesRepository;
    @InjectMocks
    MessagesService messagesService;

    @Test
    void shouldMapToMessages() {
        Messages messages = Messages.builder().build();
        MessagesDAO messagesDAO = MessagesDAO.builder().build();

        when(messagesRepository.insert(messages)).thenReturn(Mono.just(messages));

        Messages messagesMono = messagesService.mapToMessages(messagesDAO).block();

        Assert.assertEquals(messages, messagesMono);
    }
    
    @Test
    void shouldFindMessagesWhenChatIdIsPassed() {
        String chatId = "123";
        Messages messages = Messages.builder().build();

        when(messagesRepository.findWithTailableCursorByChannelId(chatId)).thenReturn(Flux.just(messages));

        Messages messages1 = messagesService.findMessagesByChatId(chatId).blockFirst();

        Assert.assertEquals(messages, messages1);
    }
}