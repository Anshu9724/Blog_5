package com.blog5.service.impl;

import com.blog5.Exception.ResourceNotFoundException;
import com.blog5.entity.Post;
import com.blog5.payload.PostDto;
import com.blog5.repository.PostRepository;
import com.blog5.service.PostService;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class PostServiceImpl implements PostService {


    private PostRepository repo;

    private ModelMapper modelMapper;


    public PostServiceImpl(PostRepository repo , ModelMapper modelMapper) {
        this.repo = repo;
        this.modelMapper=modelMapper;
    }


    @Override
    public PostDto createPost(PostDto postDto) {
        Post post = new Post();
        post.setTitle(postDto.getTitle());
        post.setDescription(postDto.getDescription());
        post.setContent(postDto.getContent());
        Post news = repo.save(post);

         PostDto dto = new PostDto();
         dto.setId(news.getId());
         dto.setTitle(news.getTitle());
         dto.setDescription(news.getDescription());
         dto.setContent(news.getContent());

        return dto;
    }

    @Override
    public List<PostDto> getAllPost(int pageNo, int pageSize, String sortBy, String sortDir) {
        Sort sort =sortDir.equalsIgnoreCase(Sort.Direction.ASC.name())?Sort.by(sortBy).ascending():
                Sort.by(sortBy).descending();

        Pageable pageable = PageRequest.of(pageNo,pageSize,sort);

        Page<Post> listOfPost = repo.findAll(pageable);
        List<Post> posts = listOfPost.getContent();

        List<PostDto> collect = posts.stream().map(post -> mapToDto(post)).collect(Collectors.toList());
        return collect;
    }

    @Override
    public PostDto getAllPostById(long id) {
        Post post = repo.findById(id).orElseThrow(
                ()->new ResourceNotFoundException("Post not found by id:"+id));

        return mapToDto(post);
    }

    @Override
    public PostDto updatePostByID(long id, PostDto postDto) {
        Post post = repo.findById(id).orElseThrow(
                ()->new ResourceNotFoundException("Post not found by id:"+id));

        Post post1 = mapToEntity(postDto);
        post1.setId(id);
        Post save = repo.save(post1);
        PostDto postDto1 = mapToDto(save);
        return postDto1;
    }

    @Override
    public void deletePostByID(long id) {
        Post post = repo.findById(id).orElseThrow(
                ()->new ResourceNotFoundException("Post not found by id:"+id));
        repo.deleteById(id);


    }

    PostDto mapToDto(Post post){
        PostDto dto = modelMapper.map(post, PostDto.class);

//        PostDto dto = new PostDto();
//        dto.setId(post.getId());
//        dto.setTitle(post.getTitle());
//        dto.setDescription(post.getDescription());
//        dto.setContent(post.getContent());
        return dto;

    }

    Post mapToEntity(PostDto postDto){

        Post post = modelMapper.map(postDto, Post.class);

//        Post post = new Post();
//        post.setId(postDto.getId());
//        post.setTitle(postDto.getTitle());
//        post.setDescription(postDto.getDescription());
//        post.setContent(postDto.getContent());
        return post;

    }

}
