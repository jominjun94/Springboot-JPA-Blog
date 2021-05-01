package com.cos.blog.controller;

import java.io.Console;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.cos.blog.model.Board;
import com.cos.blog.service.BoardService;

@Controller
public class BoardController {

	@Autowired
	private BoardService boardService;
	
	@GetMapping("/") //세션 확인 하는 방법!
	public String index(Model model,@PageableDefault(size=4, sort="id",direction = Sort.Direction.DESC) Pageable pageable) {
	
	 	 Page<Board> board = boardService.글목록(pageable);
		model.addAttribute("boards",boardService.글목록(pageable));
		
		return "index";
	}
	
	@GetMapping("/board/{id}")
	public String findById(Model model,@PathVariable int id) {
		model.addAttribute("board", boardService.글상세보기(id));
		return "board/detail";
	}
	
	
	
	@GetMapping("/board/saveForm")
	public String saveForm() {
		return "board/saveForm";
	}

	
	@GetMapping("/board/{id}/updateForm")
	public String updateForm(@PathVariable int id , Model model) {
		model.addAttribute("board", boardService.글상세보기(id));
		return "board/updateForm";
	}
	
	
	
	
	
	
	
	

}



