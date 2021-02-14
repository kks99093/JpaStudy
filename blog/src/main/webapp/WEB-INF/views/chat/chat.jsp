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
	//bang_id는 쿼리스트링으로 날리는 id구나 그럼 방id를 유저 pk로 정하면 되겠다
	$(document).ready(function() { //웹소켓을 켠다???
			webSocket.init({ url: '<c:url value="/chat" />' });			
	});
	var webSocket = {
		init: function(param) {
			this._url = param.url;
			this._initSocket();
		},
		sendChat: function() {
			this._sendMessage('${param.bang_id}', 'CMD_MSG_SEND', $('#message').val()); //여기서 sendMessage함수를 한번더 호출하는구나
			$('#message').val('');
		},
		sendEnter: function() {
			this._sendMessage('${param.bang_id}', 'CMD_ENTER', $('#message').val()); //여기서 sendMessage함수를 한번더 호출하는구나
			$('#message').val('');
		},
		receiveMessage: function(msgData) { //메세지 받을때

			// 정의된 CMD 코드에 따라서 분기 처리
			if(msgData.cmd == 'CMD_MSG_SEND') {					
				$('#divChatData').append('<div>' + msgData.msg + '</div>');
			}
			// 입장
			else if(msgData.cmd == 'CMD_ENTER') {
				$('#divChatData').append('<div>' + msgData.msg + '</div>');
			}
			// 퇴장
			else if(msgData.cmd == 'CMD_EXIT') {					
				$('#divChatData').append('<div>' + msgData.msg + '</div>');
			}
		},
		closeMessage: function(str) {
			$('#divChatData').append('<div>' + '연결 끊김 : ' + str + '</div>');
		},
		disconnect: function() {
			this._socket.close();
		},
		_initSocket: function() {
			this._socket = new SockJS(this._url); // 소켓 연결
			this._socket.onopen = function(evt) {	//내가 입장할때? 내가 onopen할때 보낼 메세지
				webSocket.sendEnter();				
			};
			this._socket.onmessage = function(evt) {
				webSocket.receiveMessage(JSON.parse(evt.data));  //받은 채팅 메세지
			};
			this._socket.onclose = function(evt) {
				webSocket.closeMessage(JSON.parse(evt.data)); //받은 사용자퇴장 메세지
			}
		},
		_sendMessage: function(bang_id, cmd, msg) { //여기서 제이슨형태로 변환해서 값을 넘기는구나
			var msgData = {
					bang_id : bang_id,
					cmd : cmd,
					msg : msg
			};
			var jsonData = JSON.stringify(msgData);
			this._socket.send(jsonData);
		}
	};
</script>	
</body>
</html>