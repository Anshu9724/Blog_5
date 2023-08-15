package com.blog5.repository;

import com.blog5.entity.Post;
//import com.blog5.payload.PostDto;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PostRepository extends JpaRepository<Post,Long> {
}
