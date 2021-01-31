<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>글쓰기</title>
</head>
<body>
	<form action="/board/boardWriteProc" method="post">
		<input type="text" name="title">
		<input type="text" name="content">
		<input type="submit">
	</form>

</body>
</html>