package com.example.cooperation_project;

import com.example.cooperation_project.dto.ReqPostPageableDto;
import com.example.cooperation_project.service.PostService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class CooperationProjectApplicationTests {

    @Autowired
    private PostService postService;

    @Test
    void contextLoads() {
        ReqPostPageableDto dto = new ReqPostPageableDto("post_Id",true,4,10);

       // postService.getProducts(dto);
    }

    @Test
    void test(){

        System.out.println(postService.getPosts());
    }

}