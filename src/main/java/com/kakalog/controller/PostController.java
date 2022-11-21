package com.kakalog.controller;

import com.kakalog.request.PostCreate;
import com.kakalog.service.PostService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
@RestController
@RequiredArgsConstructor
public class PostController {

    // SSR -> jsp, thymeleaf, mustache, freemarker
    // SPA -> vue, react

    private final PostService postService;

    @PostMapping("/posts")
    public Map<String, String> post(@RequestBody @Valid PostCreate request){
        //db.save(params)
        postService.write(request);
        return Map.of();
    }

}
