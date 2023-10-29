package com.blog5.contoller;

import com.blog5.payload.PostDto;
import com.blog5.service.PostService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/posts")
public class PostController {

//    @Autowired
    private PostService service;


    public PostController(PostService service) {
        this.service = service;
    }

    // http://localhost:8080/api/posts
    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping
    public ResponseEntity<?> createPost(@Valid @RequestBody PostDto postDto, BindingResult result){

        if(result.hasErrors()){
            return new ResponseEntity<>(result.getFieldError().getDefaultMessage()
                    ,HttpStatus.INTERNAL_SERVER_ERROR);
        }

        PostDto dto = service.createPost(postDto);
        return new ResponseEntity<>(dto, HttpStatus.CREATED);

        }
        // http://localhost:8080/api/posts?pageNo=0&pageSize=2&sortBy=title&sortDir=desc
        @GetMapping
    public List<PostDto> getAllPost(@RequestParam(value = "pageNo",defaultValue = "10",required = false)int pageNo,
                                    @RequestParam(value = "pageSize",defaultValue = "10",required = false)int pageSize,
                                    @RequestParam(value = "sortBy",defaultValue = "id",required = false)String sortBy,
                                    @RequestParam(value = "sortDir",defaultValue = "asc",required = false)String sortDir){
        List<PostDto> dto = service.getAllPost(pageNo,pageSize,sortBy,sortDir);
        return dto;

        }
        @GetMapping("/{id}")
    public ResponseEntity<PostDto>getPostByID(@PathVariable("id")long id){
        PostDto dto = service.getAllPostById(id);
        return new ResponseEntity<>(dto,HttpStatus.OK);
    }
    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping("/{id}")
    public ResponseEntity<?>updateById(@RequestBody PostDto postDto,
                                             @PathVariable("id")long id,BindingResult result){

        if(result.hasErrors()){
            return new ResponseEntity<>(result.getFieldError()
                    .getDefaultMessage(),HttpStatus.INTERNAL_SERVER_ERROR );
        }


        PostDto dto = service.updatePostByID(id,postDto);
        return new ResponseEntity<>(dto,HttpStatus.OK);

    }
    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deletePost(@PathVariable("id")long id){
        service.deletePostByID(id);
        return new ResponseEntity<>("Post is deleted",HttpStatus.OK);




    }
 //   http://localhost:8080/api/12/12/14
    @GetMapping("/{id}/{id1}/{id2}")
    public ResponseEntity<Integer> sum(@PathVariable("id")int id,
                                       @PathVariable("id1")int id1,
                                       @PathVariable("id2")int id2) {
System.out.println(id + id1 + id);
        return new ResponseEntity<>(id+id1+id2,HttpStatus.OK);



    }


        }
