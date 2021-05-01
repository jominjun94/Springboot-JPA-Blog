package com.cos.blog.controller.api;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.cos.blog.config.auth.PrincipalDetail;
import com.cos.blog.dto.ResponseDto;
import com.cos.blog.model.Board;
import com.cos.blog.model.Reply;
import com.cos.blog.model.RoleType;
import com.cos.blog.model.User;
import com.cos.blog.service.BoardService;
import com.cos.blog.service.UserService;

@RestController
public class BoardApiController {

	
	@Autowired
	private BoardService boardService;

	
	
	
	
	@PostMapping("/api/board")
	public ResponseDto<Integer> save(@RequestBody Board board, @AuthenticationPrincipal PrincipalDetail principal) {
		
		boardService.글쓰기(board,principal.getUser());
		
		return new ResponseDto<Integer>(HttpStatus.OK.value(),1); 
		// jackson을 통해 자동으로 json 으로 전달
	}
	
	@DeleteMapping("/api/board/{id}")
	public ResponseDto<Integer> deleteById(@PathVariable int id) {
		
		boardService.글삭제하기(id);
		return new ResponseDto<Integer>(HttpStatus.OK.value(),1); 
		// jackson을 통해 자동으로 json 으로 전달
	}
	
	
	
	@PutMapping("/api/board/{id}")
	public ResponseDto<Integer> updateById(@PathVariable int id,@RequestBody Board board) {
		
		boardService.글수정하기(id,board);
		return new ResponseDto<Integer>(HttpStatus.OK.value(),1); 
		// jackson을 통해 자동으로 json 으로 전달
	}
	
	@PostMapping("/api/board/{boardId}/reply")
	public ResponseDto<Integer> replySave(@PathVariable int boardId, @RequestBody Reply reply , @AuthenticationPrincipal PrincipalDetail principal) {
		
		boardService.댓글쓰기(principal.getUser(), boardId, reply);
		return new ResponseDto<Integer>(HttpStatus.OK.value(),1); 
		// jackson을 통해 자동으로 json 으로 전달
	}
	
	
	
	
	
	@DeleteMapping("/api/board/{boardId}/reply/{replyId}")
	public ResponseDto<Integer> replyDelete(@PathVariable int replyId) {
		
		boardService.댓글삭제(replyId);
		return new ResponseDto<Integer>(HttpStatus.OK.value(),1); 
		// jackson을 통해 자동으로 json 으로 전달
	}
	
	
	
	
	
	
	
/*	
	//, HttpSession session 빈등록 가능
	@PostMapping("/api/user/login")
	public ResponseDto<Integer> login(@RequestBody User user) {
		System.out.println("login 호출");
		user.setRole(RoleType.USER);
		 User principal = userService.로그인(user);
		 
		 if(principal != null) {
			 session.setAttribute("principal", principal);
		 }
		return new ResponseDto<Integer>(HttpStatus.OK.value(),1); 
		// jackson을 통해 자동으로 json 으로 전달
	}
	
	*/
}
