package com.example.demo.SpringProject.util;


import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
public class Message {
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd hh:mm:ss")
    private LocalDateTime timestamp;

    private String message;
    private String desc;
    private String statusCode;

    public Message(String message, String desc, String statusCode) {
        this.message = message;
        this.desc = desc;
        this.statusCode = statusCode;
    }
}