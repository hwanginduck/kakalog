package com.kakalog.service;

import com.kakalog.domain.Post;
import com.kakalog.repository.PostRepository;
import com.kakalog.request.PostCreate;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.Entity;

@Slf4j
@Service
@RequiredArgsConstructor
public class PostService {

    private final PostRepository postRepository;

    public void write(PostCreate postCreate){
        // postCreate -> Entity
        Post post = new Post(postCreate.getTitle(), postCreate.getContent());
        postRepository.save(post);
    }
}
