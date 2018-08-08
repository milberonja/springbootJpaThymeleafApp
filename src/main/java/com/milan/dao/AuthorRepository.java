package com.milan.dao;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.milan.entity.Author;

@Repository
public interface AuthorRepository extends CrudRepository<Author, Integer> {
	
	Author findByUserNameAndPassword(String userName, String password);

}
