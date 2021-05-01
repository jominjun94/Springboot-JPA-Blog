package com.cos.blog.service;




import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.cos.blog.model.Board;
import com.cos.blog.model.Reply;
import com.cos.blog.model.RoleType;
import com.cos.blog.model.User;
import com.cos.blog.repository.BoardRepository;
import com.cos.blog.repository.ReplyRepository;
import com.cos.blog.repository.UserRepository;



@Service
public class BoardService {
	
	
	@Autowired
	private ReplyRepository replyRepository;

	
	@Autowired
	private BoardRepository boardRepository;

	@Transactional
	public void 글쓰기(Board board,User user) {
		board.setCount(0);
		board.setUser(user);
		boardRepository.save(board);
		
	}
	
	public Page<Board> 글목록(Pageable pageable){
		return boardRepository.findAll(pageable);
	}
	
	public Board 글상세보기(int id) {
		return boardRepository.findById(id)
				.orElseThrow(()->{
					return new IllegalArgumentException("글 상세보기 실패");
				});
	}
	
	@Transactional
	public void 글삭제하기(int id) {
		boardRepository.deleteById(id);
	}
	
	
	
	@Transactional
	public void 글수정하기(int id , Board requestboard) {
		Board board = boardRepository.findById(id)
		.orElseThrow(()->{
			return new IllegalArgumentException("글 상세보기 실패");
		});
		
		board.setTitle(requestboard.getTitle());
		board.setContent(requestboard.getContent());
	
	}
	
	
	@Transactional
	public void 댓글쓰기(User user , int boardId , Reply requestReply){
		
		
		Board board = boardRepository.findById(boardId).orElseThrow(()->{
			return new IllegalArgumentException("글 상세보기 실패");
		});
		
		requestReply.setUser(user);
		requestReply.setBoard(board);
		
		
		replyRepository.save(requestReply);
		
	}
	
	
	@Transactional
	public void 댓글삭제(int replyId) {
		
		replyRepository.deleteById(replyId);
		
		}
	
	
	/*
	@Transactional(readOnly = true)
	public User 로그인(User user ) {
		return userRepository.findByUsernameAndPassword(user.getUsername(), user.getPassword());
	}
	*/
}
