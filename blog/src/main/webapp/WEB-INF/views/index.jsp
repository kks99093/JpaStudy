<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="sec"
    uri="http://www.springframework.org/security/tags"%>
<sec:authentication property="principal" var="principal"/>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<body>
${principal.role}
</body>
</html>