<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>


<%@ include file = "../layout/header.jsp" %>

<div class="container">
  
  <form action = "/auth/loginProc" method = "post">
  <div class="form-group">
    <label for="username">Username🚗</label>
    <input type="text" name = "username" class="form-control" placeholder="Enter username" id="username">
  </div>
 
 
 
  <div class="form-group">
    <label for="pwd">Password🚓</label>
    <input type="password" name = "password" class="form-control" placeholder="Enter password" id="password">
  </div>
 
 

	  <button id = "btn-login" class="btn btn-primary">로그인</button>
	<a href = "/oauth2/authorization/naver"><img height="38px" width="38px" src="/image/네이버 아이디로 로그인_아이콘형_Green.PNG"/></a>
	<a href = "/oauth2/authorization/kakao"><img height="38px" width="38px" src="/image/카카오2.png"/></a>
	<a href = "/oauth2/authorization/google"><img height="38px" width="38px" src="/image/google-plus-2431022_640.png"/></a>
	<a href = "/oauth2/authorization/facebook"><img height="38px" width="38px" src="/image/facebook-2429746_640.png"/></a>
	
	
	 <!-- <a href = "https://kauth.kakao.com/oauth/authorize?client_id=f17fad21a564bc45dd0ec7a1b9836378&redirect_uri=http://localhost:8000/auth/kakao/callback&response_type=code"><img height="38px" src="/image/kakao_login_button.png"/></a>  -->
	</form></br>


</div>
<!--  <script src="/js/user.js"></script> -->
<%@ include file = "../layout/footer.jsp" %>


