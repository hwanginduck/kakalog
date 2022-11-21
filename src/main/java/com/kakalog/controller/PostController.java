package com.kakalog.controller;

import com.kakalog.request.PostCreate;
import com.kakalog.response.PostResponse;
import com.kakalog.service.PostService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@Slf4j
@RestController
@RequiredArgsConstructor
public class PostController {

    // SSR -> jsp, thymeleaf, mustache, freemarker
    // SPA -> vue, react

    private final PostService postService;

    @PostMapping("/posts")
    public void post(@RequestBody @Valid PostCreate request){
        //db.save(params)
        // Case1. 저장한 데이터 Entity -> response 로 응답하기
        // Case2. 저장한 데이터의 primary_id -> response 로 응답하기
        // Client 에서는 수신한 id 를 글 조회 API 를 통해서 데이터를 수신받음.
        // **Case3. 응답 필요 없음 -> Client 에서 모든 post(글) 데이터 context 를 잘 관리함
        // Bad Case : 서버에서 반드시 이렇게 할껍니다 fix
        // --> 서버에서 차라리 유연하게 대응하는게 좋음
        // --> 한번에 일괄적으로 잘 처리되는 케이스가 없다 -> 잘 관리하는 형태가 중요
        postService.write(request);
    }

    /**
     *  /posts = 글 전체 조회(검색 + 페이징)
     *  /posts/{postId} = 글 한개만 조회
     */

    @GetMapping("/posts/{postId}")
    public PostResponse get(@PathVariable Long postId){
        return postService.get(postId);
    }

    @GetMapping("/posts")
    public List<PostResponse> getList(){
        return postService.getList();
    }
}
