<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="sec"
    uri="http://www.springframework.org/security/tags"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>로그인 페이지</title>
</head>
<body>
<h1>로그인 페이지</h1>
<hr/>
<form action="/loginProc" method="POST">
	<input type="text" name="username" placeholder="Username"/><br/>
	<input type="password" name="password" placeholder="Username"/><br/>
	<button>로그인</button>
</form>
<a href="/oauth2/authorization/google">구글 로그인</a>
<!-- oauth2를 쓰면 고정적으로 적어야하는것(내마음대로 바꿔적을수있는 주소감 아님) -->
<a href="/oauth2/authorization/facebook">페이스북 로그인</a>
<a href="/joinForm">회원가입을 아직 하지 않으셨나요?</a>
</body>
</html>