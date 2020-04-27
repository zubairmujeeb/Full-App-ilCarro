package com.car.rentservice.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@AllArgsConstructor
public class CommentsOutputDTO {

    private final String firstName;
    private final String secondName;
    private final String photoUrl;
    private final LocalDateTime postDate;
    private final String post;
}
