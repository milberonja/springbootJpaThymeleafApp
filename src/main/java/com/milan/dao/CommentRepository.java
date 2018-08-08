package com.milan.dao;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.milan.entity.Comment;

@Repository
public interface CommentRepository extends CrudRepository<Comment, Integer> {

}
