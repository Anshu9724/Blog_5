package com.blog5.payload;

import lombok.Data;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Data
public class CommentDto {


    private long id;
    private String name;
    private String email;
    private String body;

}
