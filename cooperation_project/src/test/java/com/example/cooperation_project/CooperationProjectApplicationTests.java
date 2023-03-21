package com.example.cooperation_project;

import com.example.cooperation_project.dto.post.PostResponseDto;
import com.example.cooperation_project.dto.post.ReqPostPageableDto;
import com.example.cooperation_project.entity.Post;
import com.example.cooperation_project.repository.PostRepository;
import com.example.cooperation_project.service.PostService;
import java.util.List;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class CooperationProjectApplicationTests {

    @Autowired
    private PostService postService;

    @Autowired
    private PostRepository postRepo;

    @Test
    void contextLoads() {

    }

    @Test
    void test(){

        System.out.println(postService.getPosts());
    }

    @Test @DisplayName("포스트 페이징 처리")
    public void pageablePost(){
        /* given */
        ReqPostPageableDto dto = new ReqPostPageableDto("id",true,4,1);

        /* when */
        List<PostResponseDto> result = postService.getProductsOrderByModified(dto);

        /* then */
        System.out.println(result);
        Assertions.assertEquals(dto.getSize(),result.size());
    }

}
