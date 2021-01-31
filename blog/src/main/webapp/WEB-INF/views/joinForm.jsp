<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="sec"
    uri="http://www.springframework.org/security/tags"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>회원 가입 페이지</title>
</head>
<body>
<h1>회원가입 페이지</h1>
<hr/>
<form action="/join" method="POST">
	<input type="text" name="username" placeholder="Username"/><br/>
	<input type="password" name="password" placeholder="PassWord"/><br/>
	<input type="email" name="email" placeholder="Email"/><br/>
	<button>회원가입</button>
</form>
</body>
</html>