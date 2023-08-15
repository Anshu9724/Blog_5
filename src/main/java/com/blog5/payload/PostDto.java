package com.blog5.payload;

import lombok.Data;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;


@Data
public class PostDto {
    private long id;
   @NotEmpty
   @Size(min=2, message = "Title Should be more than 2 characters")
    private String title;
    @NotEmpty
    @Size(min=2, message = "description Should be more than 2 characters")
    private String description;
    @NotEmpty
    @Size(min=2, message = "content Should be more than 2 characters")
    private String content;

}
