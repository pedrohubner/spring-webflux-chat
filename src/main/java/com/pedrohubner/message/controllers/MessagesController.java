package com.pedrohubner.message.controllers;

import com.pedrohubner.facades.MessagesFacade;
import com.pedrohubner.message.models.Messages;
import com.pedrohubner.message.models.MessagesDAO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import lombok.AllArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@Api(tags = "Messages controller")
@AllArgsConstructor
public class MessagesController {

    private final MessagesFacade messagesFacade;

    @PostMapping(value = "/chats")
    @ApiResponses({
            @ApiResponse(code = 200, message = "Chat was posted successfully."),
            @ApiResponse(code = 400, message = "Messages Body is invalid.")
    })
    public Mono<Messages> postChatWithBody(@RequestBody MessagesDAO messagesDAO) {
        return messagesFacade.insertNewChat(messagesDAO);
    }

    @GetMapping(value = "/chats/stream", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    @ApiResponses({
            @ApiResponse(code = 200, message = "Chat found by id successfully."),
            @ApiResponse(code = 400, message = "Chat id was not informed.")
    })
    public Flux<Messages> findMessageByChatId(@RequestParam(value = "chatId") String chatId) {
        return messagesFacade.findMessagesByChatId(chatId);
    }
}
