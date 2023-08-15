package com.blog5.service.impl;

import com.blog5.Exception.ResourceNotFoundException;
import com.blog5.entity.Comment;
import com.blog5.entity.Post;
import com.blog5.payload.CommentDto;
import com.blog5.repository.CommentRepository;
import com.blog5.repository.PostRepository;
import com.blog5.service.CommentService;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CommentServiceImpl implements CommentService {

    private PostRepository postRepository;
    private CommentRepository commentRepository;
    private ModelMapper modelMapper;


    public CommentServiceImpl(PostRepository postRepository, CommentRepository commentRepository,
                               ModelMapper modelMapper) {
        this.postRepository = postRepository;
        this.commentRepository = commentRepository;
        this.modelMapper= modelMapper;
    }


    @Override
    public CommentDto createComment(long postId, CommentDto commentDto) {
        Post post  = postRepository.findById(postId).orElseThrow(
                ()-> new ResourceNotFoundException("Post is not found by this Id:"+postId));
        Comment comment = mapToEntity(commentDto);
        comment.setPost(post);
        Comment save = commentRepository.save(comment);
        return mapToDto(save);


    }

    @Override
    public List<CommentDto> getAllComments(long postId) {
        Post post  = postRepository.findById(postId).orElseThrow(
                ()-> new ResourceNotFoundException("Post is not found by this Id:"+postId));
        List<Comment> byPostId = commentRepository.findByPostId(postId);
        return byPostId.stream().map(posts->mapToDto(posts)).collect(Collectors.toList());

    }

    @Override
    public CommentDto getCommentById(long postId, long commentId) {
        Post post  = postRepository.findById(postId).orElseThrow(
                ()-> new ResourceNotFoundException("Post is not found by this Id:"+postId));
        Comment comment = commentRepository.findById(commentId).orElseThrow(
                ()->new ResourceNotFoundException("Post not found By this Id:"+commentId)
        );


        return mapToDto(comment);
    }

    @Override
    public CommentDto updateCommentById(long postId, long commentId, CommentDto commentDto) {
        Post post  = postRepository.findById(postId).orElseThrow(
                ()-> new ResourceNotFoundException("Post is not found by this Id:"+postId));
        Comment comment = commentRepository.findById(commentId).orElseThrow(
                ()->new ResourceNotFoundException("Post not found By this Id:"+commentId));
        comment.setName(commentDto.getName());
        comment.setEmail(commentDto.getEmail());
        comment.setBody(commentDto.getBody());
//        comment.setPost(post);
        Comment save = commentRepository.save(comment);


        return mapToDto(save);
    }

    @Override
    public void deleteCommentByID(long postId, long commentId) {
        Post post  = postRepository.findById(postId).orElseThrow(
                ()-> new ResourceNotFoundException("Post is not found by this Id:"+postId));
        Comment comment = commentRepository.findById(commentId).orElseThrow(
                ()->new ResourceNotFoundException("Post not found By this Id:"+commentId));
        commentRepository.deleteById(commentId  );


    }

    Comment mapToEntity(CommentDto commentDto){
        Comment com= modelMapper.map(commentDto,Comment.class);
//        Comment com = new Comment();
//        com.setId(commentDto.getId());
//        com.setName(commentDto.getName());
//        com.setEmail(commentDto.getEmail());
//        com.setBody(commentDto.getBody());
        return com;


    }
    CommentDto mapToDto(Comment comment){

        CommentDto dto = modelMapper.map(comment,CommentDto.class);
//        CommentDto dto = new CommentDto();
//        dto.setId(comment.getId());
//        dto.setName(comment.getName());
//        dto.setEmail(comment.getEmail());
//        dto.setBody(comment.getBody());
        return dto;

    }
}
