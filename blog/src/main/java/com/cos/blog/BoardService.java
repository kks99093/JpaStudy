package com.cos.blog;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.cos.blog.model.Board;
import com.cos.blog.model.SearchDTO;
import com.cos.blog.repository.BoardRepository;

@Service
public class BoardService {
	
	@Autowired
	private BoardRepository boardRepository;
	
	public void boardWriteProc(Board board) {
		boardRepository.save(board);
	}
	
	public Page<Board> selBoardList(SearchDTO search, Pageable pageable) {
		//List<Board> boardList = boardRepository.findAll();// 그냥 전체 리스트 불러오기
		Page<Board> boardList = null;
		if(search.getSerachText() != null) {
			String likeSearchText = "%"+search.getSerachText()+"%";
			boardList = boardRepository.findByTitleLike(likeSearchText,pageable);
			
		}else {
			boardList = boardRepository.findAll(pageable); //PageRequest.of(0, 5) 1번째 파라미터가 페이지, 2번째 파라미터가 사이즈
																	//pageable을 파라미터로 사용할경우 page,size라는 키값으로 쿼리스트링을 보내서 페이지랑 사이즈를 정할수 있다
		}
		return boardList;
	}
	
	public void delBoardProc(List<Board> boardList) {
		for(Board board : boardList) {
			boardRepository.delete(board);
		}
	}

}
