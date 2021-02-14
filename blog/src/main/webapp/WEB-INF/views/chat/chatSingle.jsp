<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>    
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>채팅창</title>

</head>
<body>
	<div style="width: 800px; height: 700px; padding: 10px; border: solid 1px #e1e3e9;">
		<div id="divChatData"></div>
	</div>
	<div style="width: 100%; height: 10%; padding: 10px;">
		<input type="text" id="message" size="110" onkeypress="if(event.keyCode==13){webSocket.sendChat();}" />
		<input type="button" id="btnSend" value="채팅 전송" onclick="webSocket.sendChat()" />
	</div>
<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.5.1/jquery.min.js"></script>
<script src="https://cdnjs.cloudflare.com/ajax/libs/sockjs-client/1.4.0/sockjs.js"></script>
<script type="text/javascript">
//순서가 1.url을 넣어줌 2.intiSocat에서 sockJS를 생성하면서 소켓에 연결됨
	$(document).ready(function() {
			webSocket.init({ url: '<c:url value="/chat" />' });			
	});
	
	var webSocket = {
		init: function(param) {
			this._url = param.url; //localhost:8080/chat
			this._initSocket();
		},
		sendChat: function() {
			this._sendMessage($('#message').val()); // sendMessage라는 함수가 따로있어서 보내주는건가 봄
			$('#message').val(''); // 메세지를 보내고 초기화 해주는거
		},
		receiveMessage: function(str) {
			$('#divChatData').append('<div>' + str + '</div>'); //받음 메세지를 찍어주는 부분				
		},
		closeMessage: function(str) {
			$('#divChatData').append('<div>' + '연결 끊김 : ' + str + '</div>');
		},
		disconnect: function() {
			this._socket.close();
		},
		_initSocket: function() {
			this._socket = new SockJS(this._url); // 소켓을 연결하는 부분(url을 넣어서 연결함)
			this._socket.onmessage = function(evt) { //evt는 웹소켓에서 보내준 데이터 (상대방 채팅을 받을때 사용)
				webSocket.receiveMessage(evt.data);
			};
			this._socket.onclose = function(evt) { //evt는 웹소켓에서 보내준 데이터 (상대방 연결이 끊겼을때 사용)
				webSocket.closeMessage(evt.data);
			}
		},
		_sendMessage: function(str) {
			this._socket.send(str);
		}
	};
</script>	
</body>
</html>