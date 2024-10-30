package com.sw19.sofa.domain.tag.repository;

import com.sw19.sofa.domain.tag.entity.Tag;

import java.util.List;

public interface TagCustomRepository {
    List<Tag> findAllByArticleId(Long articleId);
}
