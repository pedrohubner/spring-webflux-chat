package com.pedrohubner.message.models;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@Builder
@Document(collection = "messages")
@NoArgsConstructor
@AllArgsConstructor
public class Messages {

    @Id
    private String id;
    private String sender;
    private String message;
    private String addressee;
    private String channelId;
}
