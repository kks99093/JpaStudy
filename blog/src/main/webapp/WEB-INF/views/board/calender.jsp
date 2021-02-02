<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>    
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
<style>
	table{border:1px solid black; border-collapse: collapse;}
	tr, th, td{border:1px solid black;}
	th, td{width: 50px; height:50px;}
</style>
</head>
<body>
	<table>
		<tr>
			<th>일</th>
			<th>월</th>
			<th>화</th>
			<th>수</th>
			<th>목</th>
			<th>금</th>
			<th>토</th>
		</tr>
		<tr>
			<td></td>
			<td></td>
			<td></td>
			<td></td>
			<td></td>
			<td></td>
			<td></td>
		</tr>
		<tr>
			<td></td>
			<td></td>
			<td></td>
			<td></td>
			<td></td>
			<td></td>
			<td></td>
		</tr>
		<tr>
			<td></td>
			<td></td>
			<td></td>
			<td></td>
			<td></td>
			<td></td>
			<td></td>
		</tr>
		<tr>
			<td></td>
			<td></td>
			<td></td>
			<td></td>
			<td></td>
			<td></td>
			<td></td>
		</tr>
	</table>
</body>
<script>
	var now = new Date() // 현재 날짜 및 시간
	var year = now.getFullYear() //연도
	console.log("연도 : " + year)
	
	var month = now.getMonth()+1 //월
	console.log("월 : " + month)
	
	var date = now.getDate() //일
	console.log("일 : " + date)
	
	var lastDate = new Date(2021.3.0) //3월의 0일을 넣으면 2월의 마지막날이 찍힘
	//var weekName = new Array('일', '월', '화', '수', '목', '금', '토');
	var weekArr = new Array(1,2,3,4,5,6,7);
	var week = new Date(2021, 2, 1); // 조회할 날짜 셋팅 (연도랑 월은 받아서 쓸거고 1은 고정할듯?) 
	week = weekName[week.getDay()]; //여기에 1~7까지의 숫자(요일)가 들어갈거
	console.log('2021년 2월 1일은 '+ week+ '요일'); // 이걸 이용해서 만들어보자
	
	var line = 7; //한줄에 7개니까??
	
	for(var i = 1; i<week; i++){
		//td생성 (빈공간)
		line -= 1;
	}
	
	for(var i =1; i<lastDate; i++){ // i=1부터? 0부터?
		if(line == 0){ // line이 0이면 7칸을 다채웠단 말이니까 tr해서 칸바꿔주자
			//tr 생성
			line = 7;
		}
		
		//td생성 하면서 i값 넣어주자
		line -= 1;
		
	}
	
	
	//var tbCalendar = document.getElementById("calendar"); 달력을 만들 테이블
	//var row = null;
	//row = tbCalendat.insertRow(); 새로운열삽입 초기화라는데 일단 써봐야알듯?
	// row.insertCell() //이게 td만드는거 같은데 잘 모르겠음 써봐야알듯
	
</script>
</html>