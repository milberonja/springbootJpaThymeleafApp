package com.milan.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.milan.dao.CommentRepository;
import com.milan.entity.Comment;

@Service
@Transactional
public class CommentService {

	@Autowired
	CommentRepository cr;
	
	public void saveComment(Comment comment) {
		cr.save(comment);
	}
	
}
