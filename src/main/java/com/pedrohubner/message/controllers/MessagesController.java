package com.pedrohubner.message.controllers;

import com.pedrohubner.message.models.Messages;
import com.pedrohubner.message.models.MessagesDAO;
import com.pedrohubner.message.repositories.MessagesRepository;
import lombok.AllArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import javax.validation.Valid;

@RestController
@AllArgsConstructor
public class MessagesController {

    private final MessagesRepository messagesRepository;

    @PostMapping(value = "/chats")
    public Mono<Messages> postChatWithBody(@Valid @RequestBody MessagesDAO chatMessage) {
        Messages newMessage = Messages.builder()
                .message(chatMessage.getMessage())
                .addressee(chatMessage.getAddressee())
                .sender(chatMessage.getSender())
                .channelId(chatMessage.getChannelId())
                .build();
        return messagesRepository.insert(newMessage);
    }

    @GetMapping(value = "/chats/stream", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    public Flux<Messages> findMessageByChannelId(@RequestParam String channelId) {
        return messagesRepository.findWithTailableCursorByChannelId(channelId)
                .switchIfEmpty(Mono.error(new IllegalStateException("channelId inválido.")));
    }
}
