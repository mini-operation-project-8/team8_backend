package com.example.cooperation_project.service;

import com.example.cooperation_project.dto.MsgCodeResponseDto;
import com.example.cooperation_project.dto.PostCommentResponseDto;
import com.example.cooperation_project.dto.PostRequestDto;
import com.example.cooperation_project.dto.PostResponseDto;
import com.example.cooperation_project.dto.ReqPostPageableDto;
import com.example.cooperation_project.entity.LovePost;
import com.example.cooperation_project.entity.Post;
import com.example.cooperation_project.entity.User;
import com.example.cooperation_project.entity.UserRoleEnum;
import com.example.cooperation_project.exception.ApiException;
import com.example.cooperation_project.exception.ExceptionEnum;
import com.example.cooperation_project.exception.NotAuthException;
import com.example.cooperation_project.exception.NotFoundPostException;
import com.example.cooperation_project.repository.LovePostRepository;
import com.example.cooperation_project.repository.PostRepository;
import com.example.cooperation_project.repository.UserRepository;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class PostService {

    private final PostRepository postRepository;
    private final UserRepository userRepository;
    private final LovePostRepository lovePostRepository;

    @Transactional
    public PostResponseDto createPost(PostRequestDto requestDto, User user) {

        Post post = postRepository.save(new Post(requestDto, user));

        return new PostResponseDto(post);
    }

    @Transactional(readOnly = true)
    public List<PostResponseDto> getPosts() {

        List<Post> postList = postRepository
            .findAllByOrderByModifiedAtDesc();

        return postList.stream()
            .map(PostResponseDto::new).toList();
    }

    @Transactional(readOnly = true)
    public PostCommentResponseDto getPostsId(Long post_Id) throws NotFoundPostException {

        Post post = postRepository.findById(post_Id).orElseThrow(
            () -> new NotFoundPostException("해당 게시글이 존재하지 않습니다.")
        );

        return new PostCommentResponseDto(post);
    }

    @Transactional
    public PostResponseDto update(Long post_Id, PostRequestDto postRequestDto, User user)
        throws NotFoundPostException, NotAuthException {

        Post post = postRepository.findById(post_Id).orElseThrow(
            () -> new NotFoundPostException("해당 게시글이 존재하지 않습니다.")
        );

        if (isMatchUser(post, user) || user.getRole() == UserRoleEnum.ADMIN) {
            post.update(postRequestDto);

            return new PostResponseDto(post);

        } else {

            throw new NotAuthException("해당 권한이 없습니다");
        }
    }

    @Transactional
    public MsgCodeResponseDto delete(Long post_Id, User user) {
        MsgCodeResponseDto responseDto = new MsgCodeResponseDto("");

        Post post = postRepository.findById(post_Id).orElseThrow(
            () -> new NotFoundPostException("해당 게시글이 존재하지 않습니다.")
        );

        if (isMatchUser(post, user) || user.getRole() == UserRoleEnum.ADMIN) {
            postRepository.deleteById(post_Id);
            return responseDto;
        } else {
            throw new NotAuthException("해당 권한이 없습니다");
        }
    }

    /*public Page<Post> getProducts(ReqPostPageableDto dto) {

        Pageable pageable = configPageAble(dto);

        Page<Post> products = postRepository.findAllByOrderByModifiedAtDesc(pageable);

        return products;
    }*/

    private Pageable configPageAble(ReqPostPageableDto dto){

        Sort.Direction direction = dto.isAsc()? Direction.ASC : Direction.DESC;
        Sort sort = Sort.by(direction,dto.getSortBy());

        return PageRequest.of(dto.getPage()-1,dto.getSize(),sort);
    }

    @Transactional
    public ResponseEntity<Map<String, HttpStatus>> loveOk(Long id, User user) {

        Post post = postRepository.findById(id).orElseThrow(
            () -> new IllegalArgumentException("게시글이 존재하지 않습니다.")
        );

        List<LovePost> boardLoveList = user.getLovePostList();

        if (user != null) {

            for (LovePost lovePost : boardLoveList) {
                if (lovePost.getPost().getPost_Id() == post.getPost_Id()
                    && lovePost.getUser().getUserId() == user.getUserId()) {
                    if (lovePost.isLove() == false) {
                        lovePost.update();
                        post.LoveOk();
                        return new ResponseEntity("게시글을 좋아요 했습니다.", HttpStatus.OK);
                    } else {
                        lovePost.update();
                        post.LoveCancel();
                        return new ResponseEntity("좋아요를 취소 했습니다.", HttpStatus.OK);
                    }
                } else {
                    LovePost lovePost1 = new LovePost(post, user);
                    lovePostRepository.save(lovePost1);
                    lovePost1.update();
                    post.LoveOk();
                    return new ResponseEntity("게시글을 좋아요 했습니다.", HttpStatus.OK);
                }
            }
            if (boardLoveList.size() == 0) {
                LovePost lovePost1 = new LovePost(post, user);
                lovePostRepository.save(lovePost1);
                lovePost1.update();
                post.LoveOk();
                return new ResponseEntity("게시글을 좋아요 했습니다.", HttpStatus.OK);
            }

        } else {
            throw new IllegalArgumentException("로그인 유저만 좋아요할 수 있습니다.");
        }
        return null;
    }

    /*@Transactional(readOnly = true)
    public List<PostResponseDto> getPosts(int page, int size, String sortBy, boolean isAsc){

        Sort.Direction direction = isAsc ? Sort.Direction.ASC : Sort.Direction.DESC;
        Sort sort = Sort.by(direction, sortBy);
        Pageable pageable = PageRequest.of(page, size, sort);
        List<PostResponseDto> postResponseDtos = new ArrayList<>();
        Page<Post> posts = postRepository.findAll(pageable);

        for(Post post : posts){
            postResponseDtos.add(new PostResponseDto(post));
        }
        return postResponseDtos;
    }*/

    private boolean isMatchUser(Post post, User user) {

        return post.getUser().getUserId().equals(user.getUserId());
    }

}

