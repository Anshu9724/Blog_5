package com.blog5.service;

import com.blog5.payload.PostDto;

import java.util.List;

public interface PostService {


    PostDto createPost(PostDto postDto);

    List<PostDto> getAllPost(int pageNo, int pageSize, String sortBy, String sortDir);

    PostDto getAllPostById(long id);

    PostDto updatePostByID(long id, PostDto postDto);

      void deletePostByID(long id);
}
