<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
<head>
<link rel="stylesheet" href="/css/bootstrap.css">
<script type="text/javascript" src="/js/bootstrap.js"></script>
<meta charset="UTF-8">
<title>보드 리스트</title>
<style>
	table{border: 1px solid black; border-collapse: collapse;}
	td {border: 1px solid black;}
	.disableTest{
			pointer-events: none;
  			opacity: 0.65;
	}
</style>
</head>
<body>
	<form action="/board/boardList" method="get">
		<input type="text" name="serachText">
		<input type="submit">
	</form>
	<c:choose>
		<c:when test="${boardList.totalElements > 0}">
			<table>
				<tr>
					<td><input type="checkbox"></td>
					<td>No</td>
					<td>제목</td>
				</tr>
				<!-- List객체를 넘겨받을떄는 boardList만 적어도 되지만 Page객체를 넘겨받을때는 .content를 해줘야함 -->
				<c:forEach var="boardList" items="${boardList.content}" varStatus="status">
				<tr>
					<td><input name="id" id="checkbox" type="checkbox" value="${boardList.id }"></td>
					<td>${status.index+1}</td>
					<td>${boardList.title}</td>
				</tr>
				</c:forEach>
			</table>
		</c:when>
		<c:otherwise>
			<p>검색 내용을 찾을수 없습니다</p>
		</c:otherwise>
	</c:choose>
	<span onclick="delBoard()">삭제</span>
	
	<!-- 사용할수 있는 키값 size=3 : 화면에 보여줄 게시글 수
						page=1 : 보여줄 페이지수 (0부터 시작)
						sort=id,desc : 정렬방법 (id를 역정렬한것) -->
	
	<nav aria-label="Page navigation example">
	  <ul class="pagination justify-content-center">
	    <li>
	      <a class="${boardList.pageable.pageNumber == 0 ? 'disableTest' : '' }" href="?page=${boardList.number-1}">Previous</a>
	    </li>
	    <c:forEach begin="0" end="${endPage}" varStatus="status">
		    <c:if test=""></c:if>
		    <li><a href="?page=${status.index}" class="${status.index == boardList.pageable.pageNumber ? 'disableTest' : '' }">${status.index+1}</a></li>
	    </c:forEach>
	    <li>
				<a class="${boardList.totalPages-1 == boardList.pageable.pageNumber ? 'disableTest' : '' }" href="?page=${boardList.number+1}">Next</a>
	    </li>
	  </ul>
	</nav>
	<a href="/board/boardWrite">글쓰기</a>
</body>
<script src="//code.jquery.com/jquery-3.3.1.min.js"></script>
<script>	
	
	function delBoard(){
		var cnt = $("input[id='checkbox']:checked").length;
	    var arr = [];
		$("input[id='checkbox']:checked").each(function() {
	            arr.push({id : $(this).val()});
	            
	     });
	     console.log(arr)
	     
	     if(cnt == 0){
	     	alert('선택된 글이 없습니다')
	     }

	     
		$.ajax({
			type: "POST",
			url: "/boardApi/delBoardProc",
			data: JSON.stringify(arr),
			contentType: "application/json",
			dataType:"json"
		}).done(function(resp){
			location.reload();
		})
	}
</script>
</html>