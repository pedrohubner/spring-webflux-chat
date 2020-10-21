package com.pedrohubner.message.models;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MessagesDAO {

    private String sender;
    private String message;
    private String addressee;
    private String channelId;
}
