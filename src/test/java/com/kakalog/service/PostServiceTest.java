package com.kakalog.service;

import com.kakalog.domain.Post;
import com.kakalog.exception.PostNotFound;
import com.kakalog.repository.PostRepository;
import com.kakalog.request.PostCreate;
import com.kakalog.request.PostEdit;
import com.kakalog.request.PostSearch;
import com.kakalog.response.PostResponse;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest
class PostServiceTest {

    @Autowired
    private PostService postService;

    @Autowired
    private PostRepository postRepository;

    @BeforeEach
    void clean(){
        postRepository.deleteAll();
    }

    @Test
    @DisplayName("글작성")
    void test1(){
        //given
        PostCreate postCreate = PostCreate.builder()
                .title("제목입니다.")
                .content("내용입니다.")
                .build();

        //when
        postService.write(postCreate);

        //then
        Assertions.assertEquals(1L, postRepository.count());
        Post post = postRepository.findAll().get(0);
        assertEquals("제목입니다.", post.getTitle());
        assertEquals("내용입니다.", post.getContent());
    }

    @Test
    @DisplayName("글 1개 조회")
    void test2(){
        //given
        Post requestPost = Post.builder()
                .title("foo")
                .content("bar")
                .build();
        // 클라이언트 요구사항
        // json 응답에서 title 값 길이를 최대 10글자로 해주세요.

        postRepository.save(requestPost);
        //when
        PostResponse response = postService.get(requestPost.getId());
        //then
        assertNotNull(response);
        assertEquals(1L, postRepository.count());
        assertEquals("foo", response.getTitle());
        assertEquals("bar", response.getContent());
    }

    @Test
    @DisplayName("글 1페이지 조회")
    void test3(){
        //given
        List<Post> requestPosts = IntStream.range(0, 20)
                        .mapToObj(i -> Post.builder()
                                .title("카카제목 " + i)
                                .content("글내용 " + i)
                                .build())
                        .collect(Collectors.toList());
        postRepository.saveAll(requestPosts);

        PostSearch postSearch = PostSearch.builder()
                .page(1)
                .build();
        //when
        List<PostResponse> posts = postService.getList(postSearch);

        //then
        assertEquals(10L, posts.size());
        assertEquals("카카제목 19", posts.get(0).getTitle());
    }

    @Test
    @DisplayName("글 제목 수정")
    void test4(){
        //given
        Post post = Post.builder()
                .title("카카제목")
                .content("글내용")
                .build();
        postRepository.save(post);

        PostEdit postEdit = PostEdit.builder()
                .title("카카수정")
                .content("글내용")
                .build();
        //when
        postService.edit(post.getId(), postEdit);
        //then
        Post changePost = postRepository.findById(post.getId())
                .orElseThrow(() -> new RuntimeException("글이 존재하지 않습니다. id =" +post.getId()));
        Assertions.assertEquals("카카수정", changePost.getTitle());
        Assertions.assertEquals("글내용", changePost.getContent());

    }

    @Test
    @DisplayName("글 내용 수정")
    void test5(){
        //given
        Post post = Post.builder()
                .title("카카제목")
                .content("글내용")
                .build();
        postRepository.save(post);

        PostEdit postEdit = PostEdit.builder()
                .title("카카제목")
                .content("글수정")
                .build();
        //when
        postService.edit(post.getId(), postEdit);
        //then
        Post changePost = postRepository.findById(post.getId())
                .orElseThrow(() -> new RuntimeException("글이 존재하지 않습니다. id =" +post.getId()));
        Assertions.assertEquals("카카제목", changePost.getTitle());
        Assertions.assertEquals("글수정", changePost.getContent());

    }
    @Test
    @DisplayName("글 내용만 수정")
    void test6(){
        //given
        Post post = Post.builder()
                .title("카카제목")
                .content("글내용")
                .build();
        postRepository.save(post);

        PostEdit postEdit = PostEdit.builder()
                .title(null)
                .content("글수정")
                .build();
        //when
        postService.edit(post.getId(), postEdit);
        //then
        Post changePost = postRepository.findById(post.getId())
                .orElseThrow(() -> new RuntimeException("글이 존재하지 않습니다. id =" +post.getId()));
        Assertions.assertEquals("카카제목", changePost.getTitle());
        Assertions.assertEquals("글수정", changePost.getContent());

    }

    @Test
    @DisplayName("게시글 삭제")
    void test7(){
        //given
        Post post = Post.builder()
                .title("카카제목")
                .content("글내용")
                .build();
        postRepository.save(post);

        //when
        postService.delete(post.getId());
        //then
        Assertions.assertEquals(0,postRepository.count());
    }

    @Test
    @DisplayName("글 1개 조회 - 존재하지 않는 게시글")
     void test8(){
        //given
        Post post = Post.builder()
                .title("카카제목")
                .content("글 내용")
                .build();
        // 클라이언트 요구사항
        // json 응답에서 title 값 길이를 최대 10글자로 해주세요.

        postRepository.save(post);
        //when
        Assertions.assertThrows(PostNotFound.class, () -> {
            postService.get(post.getId() + 1L);
        });
    }

    @Test
    @DisplayName("게시글 삭제 - 존재하지 않는 게시글")
    void test9(){
        //given
        Post post = Post.builder()
                .title("카카제목")
                .content("글내용")
                .build();
        postRepository.save(post);

        //expected
        Assertions.assertThrows(PostNotFound.class, () -> {
            postService.delete(post.getId() + 1L);
        });
    }

    @Test
    @DisplayName("글 내용 수정 - 존재하지 않는 게시글")
    void test10(){
        //given
        Post post = Post.builder()
                .title("카카제목")
                .content("글내용")
                .build();
        postRepository.save(post);

        PostEdit postEdit = PostEdit.builder()
                .title("카카제목")
                .content("글수정")
                .build();
        //expected
        Assertions.assertThrows(PostNotFound.class, () -> {
            postService.edit(post.getId() + 1L, postEdit);
        });

    }

}

