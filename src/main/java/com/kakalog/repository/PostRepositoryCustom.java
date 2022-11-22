package com.kakalog.repository;

import com.kakalog.domain.Post;
import com.kakalog.request.PostSearch;

import java.util.List;

public interface PostRepositoryCustom {

    List<Post> getList(PostSearch postSearch);
}
