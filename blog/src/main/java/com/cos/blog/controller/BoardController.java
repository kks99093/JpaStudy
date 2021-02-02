package com.cos.blog.controller;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

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
import com.cos.blog.repository.BoardRepository;

@Controller
public class BoardController {
	
	@Autowired
	private BoardService boardService;
	
	@Autowired
	private BoardRepository boardRepository;
	
	//페이징
	@GetMapping("/board/boardList")
									//@PageableDefault 페이지의 기본값을 설정함 sort = {""} 무엇을 기준으로 정렬할것인지
									//direction = Direction.DESC 어떤식으로 정렬할것인지
									// size = ? 한화면에 몇개를 보여줄 것인지
	public String boardList(SearchDTO search, Model model, @PageableDefault(sort = { "createDate" }, direction = Direction.DESC, size = 3) Pageable pageable) {//org.springframework.data.domain.Pageable
		//jsp jstl foreach를 쓸때 List를 넘겨받을떄는 boardList만 적어도 되지만 Page를 넘겨받을때는 .content를 해줘야함
		
		Page<Board> boardList = boardService.selBoardList(search, pageable);
//		System.out.println(boardList.getTotalPages());
		
		int userId = 1;
		List<Board> boardList2 = boardRepository.findByUserId(userId);
		System.out.println(boardList2.get(0).getUser().getUsername());
//		SELECT sum(B.sale) as totalSale FROM userinfo A
//		LEFT JOIN sales B
//		on A.id =  B.userId
//		where A.id = 1; 한번써보자 그냥
		
		
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
	
	@GetMapping("/board/calender")
	public String boardCalender() {
		int[] monthDays = {31, 28, 31, 30, 31, 30, 31, 31, 30, 31 , 30, 31};
		Calendar cal = Calendar.getInstance();
		int year = cal.get(Calendar.YEAR);
		int month = cal.get(Calendar.MONTH); //0~11 리턴(1월이면 0임)
		int date = cal.get(Calendar.DATE); //날짜
		int dayOfWeek = cal.get(Calendar.YEAR); // 요일 1~7 리턴
		cal.set(year, month, date);
		Date sdate = cal.getTime();
		System.out.println("day_of_week" + Calendar.DAY_OF_WEEK);
		System.out.println(cal.getFirstDayOfWeek()); //첫쨋날
		
		
		for(int i=0; i<(month-1); i++) {
			//윤달 계산
			if((year%4==0||year%100==0)&&(year%400 == 0)){
				monthDays[1] = 29;
			}else {
				monthDays[1] = 28;
			}
		}
		
		
		return "/board/calender";
	}

}
