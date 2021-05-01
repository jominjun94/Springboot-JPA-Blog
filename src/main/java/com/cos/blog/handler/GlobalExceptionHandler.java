package com.cos.blog.handler;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;

import com.cos.blog.dto.ResponseDto;

@ControllerAdvice // 모든 exception 이 받아지는 공간이다.
@RestController
public class GlobalExceptionHandler {

	//IllegalArgumentException 발생 하면 함수를 전달 해서 return 해줍니다.
	//@ExceptionHandler(value = IllegalArgumentException.class)
	//public String handleArgumentException(IllegalArgumentException e) {
	//	return "<h1>"+ e.getMessage() +"</h1>";
	//}
	
	// 이러면 모든 Exception 이 들어와서 받아지게 할수 있다.
	@ExceptionHandler(value = Exception.class)
	public ResponseDto<String> handleArgumentException(Exception e) {
		return new ResponseDto<String>(HttpStatus.INTERNAL_SERVER_ERROR.value(),e.getMessage());
	}
	
	
}
