package com.cos.blog.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.cos.blog.model.Board;

@Repository
public interface BoardRepository extends JpaRepository<Board, Integer> {
	Page<Board> findByTitleLike(String title, Pageable pageable);

}
