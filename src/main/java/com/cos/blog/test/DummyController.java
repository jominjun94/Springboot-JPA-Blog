package com.cos.blog.test;

import java.util.List;
import java.util.function.Supplier;

import javax.transaction.Transactional;



import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.cos.blog.model.RoleType;
import com.cos.blog.model.User;
import com.cos.blog.repository.UserRepository;

@RestController
public class DummyController {
	
	@Autowired
	private UserRepository userRepository;
	
	@DeleteMapping("/dummy/user/{id}")
	public String delete(@PathVariable int id) {
		try {
			userRepository.deleteById(id);
		} catch (EmptyResultDataAccessException e) {
			return "삭제에 실패하였습니다. 해당 id는 DB에 없습니다.";
		}
		
		return "삭제되었습니다. id : "+id;
	}
	
	
	
	
	
		// save함수는 id를 전달하지 않으면 insert를 해주고
		// save함수는 id를 전달하면 해당 id에 대한 데이터가 있으면 update를 해주고
		// save함수는 id를 전달하면 해당 id에 대한 데이터가 없으면 insert를 해요.
		// email, password
	@Transactional // 끝나면 commit 해서 더티채킹해서 날려준다! -> 스프링부트 29강
	@PutMapping("/dummy/update")
	public User updateUser(@PathVariable int id, @RequestBody User requestUser) {
		System.out.println("id" + id);
		System.out.println("password" + requestUser.getPassword());
		System.out.println("email" + requestUser.getEmail());
		
		User user = userRepository.findById(id).orElseThrow(()->{
			return new IllegalArgumentException("수정에 실패했습니다");
		});
		user.setPassword(requestUser.getPassword());
		user.setEmail(requestUser.getEmail());
		//userRepository.save(user); 
		
		//이렇게 해도 되는데 우리는 @Transactional // 함수 종료시에 자동 commit 이 됨.
		//현재 user는 꽉찬상태가 된다.
		return user;
	}
	

	
	
	
	@GetMapping("/dummy/users")
	public List<User> list(){
		return userRepository.findAll();
	}
	
	
	
	
	//http://localhost:8000/blog/dummy/user/page?page=0 // 1페이지
 	//http://localhost:8000/blog/dummy/user/page?page=1 // 2페이지
	@GetMapping("/dummy/user")
	public Page<User> pageList(@PageableDefault(size=2, sort="id",direction = Sort.Direction.DESC) Pageable pageable){

		
	
		
		Page<User> Pageusers =  userRepository.findAll(pageable); //-> 잡다한것들도 전부 딸려온다!
		List<User> users =  Pageusers.getContent();
		
		return Pageusers;
	}
	
	
	
	
	
		
	
	@GetMapping("/dummy/user/{id}")
	public User detail(@PathVariable int id) {
		//userRepository.findById(id) select 해온다!
		User user = userRepository.findById(id).orElseThrow(new Supplier<IllegalArgumentException>() {
			@Override
			public IllegalArgumentException get() { // 나중에 aop를 사용해서 오류 페이지 이동 !
			return new IllegalArgumentException("해당 유저는 없습니다 id"  + id);
			}
		});// 요즘 트랜드 
		
		/*
		User user = userRepository.findById(id).orElseGet(new Supplier<User>() {
			@Override
			public User get() {
		// 찾아서 있으면 findById(id).get() 없다면 return new User();으로 객체를 하나 생성 해준다
				return new User();
			}
		});
		//User user = userRepository.findById(id).get(); - 가져오는값이 null이 아닐때
		*/
		
		return user;
		//요청 : 웹 브라우저
		//user객체 = 자바 오브젝트
		// 변환 (웹브라우저가 이해할수 있는 데이터) -> json
		//스프링부트는 MessageConverter 가 응답시 자동으로 작동
		// 만약에 자바 오브젝트를 리턴하게 되면 Message Converterter 가 jackson 라이브러리 호출
		// user 오브젝트를 json 변환해서 브라우저에게 던진다!
	}
		
	
	
//userRepository.save(user); insert 문 
		//http://localhost:8000/blog/dummy/join(요청)
		@PostMapping("/dummy/join")
		public String join(User user) {
			
			 
			System.out.println(userRepository.save(user));
			user.setRole(RoleType.USER);
			userRepository.save(user);
			return"회원가입이 완료되었습니다.";
			
			
		}
}
