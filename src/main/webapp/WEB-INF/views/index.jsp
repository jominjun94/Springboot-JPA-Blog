<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri = "http://java.sun.com/jsp/jstl/core" %>


<%@ include file = "layout/header.jsp" %>

<div class="container">


<c:forEach var = "board" items = "${boards.content}">
 <div class="card m-2">
<div class="card-body">
	
	
	
	<div class="row text-center" style="width: 100%">        
       <div style="width: 30%; float:none; margin:0 auto" >   
	<h5 class="card-title">${board.id}번📑</h5>
     </div>
       </div>
    
     <div class="row text-center" style="width: 100%">        
       <div style="width: 30%; float:none; margin:0 auto" >    
       
    <h3 class="card-title">💌${board.title}</h3>
    
    </div>
       </div>
 
       <div class="row text-center" style="width: 100%">        
       <div style="width: 30%; float:none; margin:0 auto" >    
       
   	   <a href="/board/${board.id }" class="btn btn-primary ">상세 보기📩</a>
     
       </div>
       </div>
 
 
 
  </div>
</div>
</c:forEach>
  
<ul class="pagination justify-content-center">

<c:choose>
	<c:when test = "${boards.first }">
		 <li class="page-item disabled"><a class="page-link" href="?page=${boards.number-1}">⬅Previous</a></li>	
	</c:when>
	<c:otherwise>
 		<li class="page-item"><a class="page-link" href="?page=${boards.number-1}">⬅Previous</a></li>	
	</c:otherwise>
</c:choose>
 
<c:choose>
	<c:when test = "${boards.last }">
		  <li class="page-item disabled"><a class="page-link" href="?page=${boards.number+1 }">Next➡</a></li>
	</c:when>
	<c:otherwise>
 		 <li class="page-item"><a class="page-link" href="?page=${boards.number+1 }">Next➡</a></li>
	</c:otherwise>
</c:choose>


 
</ul>
  
</div>

<%@ include file = "layout/footer.jsp" %>


