package com.example.cooperation_project;

import com.example.cooperation_project.dto.post.PostResponseDto;
import com.example.cooperation_project.dto.post.ReqPostPageableDto;
import com.example.cooperation_project.entity.LovePost;
import com.example.cooperation_project.entity.Post;
import com.example.cooperation_project.repository.LovePostRepository;
import com.example.cooperation_project.repository.PostRepository;
import com.example.cooperation_project.service.PostService;
import java.util.List;
import java.util.Optional;
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

    @Autowired
    private LovePostRepository lovePostRepo;

    @Test
    void contextLoads() {

    }

    /*@Test
    @DisplayName("포스트 페이징 처리")
    public void pageablePost() {

        //given
        ReqPostPageableDto dto = new ReqPostPageableDto("id", true, 4, 1);

        //when
        List<PostResponseDto> result = postService.getPageOfPosts(dto);

        //then
        System.out.println(result);

        Assertions.assertEquals(dto.getSize(), result.size());
    }

    @Test
    public void postCountAll() {
        //given

        //when

        Optional<LovePost> result
            = lovePostRepo.findLovePost(1L, "dignzh12");

        //then

        System.out.println(result.get());
    }*/

}


