package com.cos.blog.repository;

import java.util.Optional;


import org.springframework.data.domain.Page;
import org.springframework.data.jpa.repository.JpaRepository;

import com.cos.blog.model.Board;
import com.cos.blog.model.User;






//DAO
// 자동으로 빈등록을 해준다
//User, interger -> primary key는 integer
//@Repository 자동으로 해줘서 이거 안해줘도 된다!
public interface BoardRepository extends JpaRepository<Board, Integer>{
	//jpa namaing 전략
	//SELECT * From user WHERE username = ? AND password = ?
	//User findByUsernameAndPassword(String username,String password);
	
//@Query(value = "SELECT * FROM user WHERE username = ?1 AND password = ?2", nativeQuery = true)
//	User login(String username, String password);
	
	
	 // Page<Board> findByAge(int age, org.springframework.data.domain.Pageable pageable);
	//select * from user where username 1?;

}
