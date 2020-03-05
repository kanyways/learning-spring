$(function () {
    if (typeof (WebSocket) == "undefined") {
        alert("您的浏览器不支持WebSocket");
    } else {
        var socketUrl = "/data";
        var socket = new SockJS(socketUrl);
        var stompClient = Stomp.over(socket);//使用STMOP子协议的WebSocket客户端
        stompClient.debug = null;
        stompClient.connect({}, function (frame) {//连接WebSocket服务端
            console.log('Connected:' + frame);
            //广播接收信息
            console.log('开始接受消息...');
            stompTopic();
            console.log('消息接受完毕');

        });


        //广播（一对多）
        function stompTopic() {
            console.log('进入广播接受模式...');
            //通过stompClient.subscribe订阅/topic/getResponse 目标(destination)发送的消息（广播接收信息）
            stompClient.subscribe('/topic/getResponse', function (response) {
                var message = JSON.parse(response.body);
                //展示广播的接收的内容接收
                if (message) {
                    $("#data").html(message["now"]);
                }
            });
        }
    }
});