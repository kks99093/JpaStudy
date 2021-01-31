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
<form action="/login" method="POST">
	<input type="text" name="username" placeholder="Username"/><br/>
	<input type="password" name="password" placeholder="Username"/><br/>
	<button>로그인</button>
</form>
<a href="/joinForm">회원가입을 아직 하지 않으셨나요?</a>
</body>
</html>