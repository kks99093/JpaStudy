package com.cos.blog.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import com.cos.blog.BoardService;
import com.cos.blog.model.Board;
import com.cos.blog.model.SearchDTO;

@Controller
public class BoardController {
	
	@Autowired
	private BoardService boardService;
	
	//페이징
	@GetMapping("/board/boardList")
									//@PageableDefault 페이지의 기본값을 설정함 sort = {""} 무엇을 기준으로 정렬할것인지
									//direction = Direction.DESC 어떤식으로 정렬할것인지
									// size = ? 한화면에 몇개를 보여줄 것인지
	public String boardList(SearchDTO search, Model model, @PageableDefault(sort = { "createDate" }, direction = Direction.DESC, size = 3) Pageable pageable) {//org.springframework.data.domain.Pageable
		//jsp jstl foreach를 쓸때 List를 넘겨받을떄는 boardList만 적어도 되지만 Page를 넘겨받을때는 .content를 해줘야함
		
		Page<Board> boardList = boardService.selBoardList(search, pageable);
//		System.out.println(boardList.getTotalPages());
		
		int endPage = boardList.getTotalPages()-1;
		if(endPage <0) {
			endPage = 0;
		}
		model.addAttribute("endPage", endPage);
		model.addAttribute("boardList", boardList);
		return "/board/boardList";
	}
	
	//글쓰기
	@GetMapping("/board/boardWrite")
	public String boardWrite() {
		
		return "/board/boardWrite";
	}
	
	@PostMapping("/board/boardWriteProc")
	public String boardWriteProc(Board board) {
			boardService.boardWriteProc(board);
		return "/board/boardList";
	}

}
