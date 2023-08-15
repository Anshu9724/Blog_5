package com.blog5.contoller;

import com.blog5.payload.CommentDto;
import com.blog5.service.CommentService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/")
public class CommentController {
    private CommentService commentService;

    public CommentController(CommentService commentService) {
        this.commentService = commentService;
    }

    @PostMapping("/posts/{postId}/comments")
    public ResponseEntity<CommentDto>createComment(@RequestBody CommentDto commentDto,
                                                   @PathVariable("postId")long postId){
        CommentDto dto = commentService.createComment(postId,commentDto);
        return new ResponseEntity<>(dto, HttpStatus.CREATED);
    }
    @GetMapping("/posts/{postId}/comments")
    public List<CommentDto> getAllComment(@PathVariable("postId")long postId){
        return commentService.getAllComments(postId);

    }

    @GetMapping("/posts/{postId}/comments/{Anshu}")
    public ResponseEntity<CommentDto>getCommentById(@PathVariable("postId")long postId,
                                                    @PathVariable("Anshu")long commentId){
        CommentDto dto = commentService.getCommentById(postId,commentId);
        return new ResponseEntity<>(dto , HttpStatus.OK);


    }
    @PutMapping("/posts/{postId}/comments/{id}")
    public ResponseEntity<CommentDto>updateId(@RequestBody CommentDto commentDto,
                                              @PathVariable("postId")long postId,
                                              @PathVariable("id")long commentId){
        CommentDto dto = commentService.updateCommentById(postId,commentId,commentDto);
        return new ResponseEntity<>(dto,HttpStatus.OK);
    }
    @DeleteMapping("/posts/{postId}/comments/{id}")
    public ResponseEntity<String>deleteComment(@PathVariable("postId")long postId,
                                               @PathVariable("id")long commentId){
        commentService.deleteCommentByID(postId,commentId);
        return new ResponseEntity<>("Post is deleted",HttpStatus.OK);


    }

}
