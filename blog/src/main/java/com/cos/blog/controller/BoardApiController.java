package com.cos.blog.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.cos.blog.BoardService;
import com.cos.blog.model.Board;

@RestController
public class BoardApiController {
	@Autowired
	private BoardService boardService;
	
	@PostMapping("/boardApi/delBoardProc")
	public int delBoardProc(@RequestBody List<Board> boardList) {
		boardService.delBoardProc(boardList);
		return 1;
	}
}
