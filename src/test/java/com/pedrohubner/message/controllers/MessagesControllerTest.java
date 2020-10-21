package com.pedrohubner.message.controllers;

import com.pedrohubner.facades.MessagesFacade;
import com.pedrohubner.message.models.Messages;
import com.pedrohubner.message.models.MessagesDAO;
import com.pedrohubner.message.repositories.MessagesRepository;
import com.pedrohubner.message.services.MessagesService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.WebFluxTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.reactive.server.WebTestClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import static org.mockito.Mockito.when;

@ExtendWith(SpringExtension.class)
@WebFluxTest(controllers = MessagesController.class)
@ContextConfiguration(classes = {MessagesController.class, MessagesService.class, MessagesDAO.class,
        MessagesFacade.class})
class MessagesControllerTest {

    @Autowired
    WebTestClient webTestClient;

    @MockBean
    MessagesRepository messagesRepository;

    @Test
    void shouldPostChatWithBody() {
        Messages messages = Messages.builder().build();

        when(messagesRepository.insert(messages)).thenReturn(Mono.just(messages));

        webTestClient
                .post()
                .uri("/chats")
                .accept(MediaType.APPLICATION_JSON)
                .bodyValue(messages)
                .exchange()
                .expectStatus()
                .isOk();
    }

    @Test
    void shouldReturnBadRequestWhenPostChatWithoutBody() {
        Messages messages = Messages.builder().build();

        when(messagesRepository.insert(messages)).thenReturn(Mono.just(messages));

        webTestClient
                .post()
                .uri("/chats")
                .accept(MediaType.APPLICATION_JSON)
                .exchange()
                .expectStatus()
                .isBadRequest();
    }

    @Test
    void shouldFindMessageByChatId() {
        String chatId = "5f902f1f13ea582705d9f3c4";
        Messages messages = Messages.builder().build();

        when(messagesRepository.findWithTailableCursorByChannelId(chatId)).thenReturn(Flux.just(messages));

        webTestClient
                .get()
                .uri(uriBuilder -> uriBuilder.path("/chats/stream")
                        .queryParam("chatId", chatId)
                        .build())
                .header(MediaType.TEXT_EVENT_STREAM_VALUE)
                .exchange()
                .expectStatus()
                .isOk();
    }

    @Test
    void shouldReturnBadRequestWhenChatIdNotInformed() {
        webTestClient
                .get()
                .uri("/chats/stream")
                .header(MediaType.TEXT_EVENT_STREAM_VALUE)
                .exchange()
                .expectStatus()
                .isBadRequest();
    }
}