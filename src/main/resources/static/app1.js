
var stompClient = null;
var showLoggedIn = false;
var userName = "Stroie";

(function(){
var socket = new SockJS('/chat');
            stompClient = Stomp.over(socket);
            stompClient.connect({}, function (frame) {

                console.log('Connected: ' + frame);
                stompClient.subscribe('/topic/cm', function (fromServer) {

                    showChatMessages(JSON.parse(fromServer.body).content);
                });
            });
})();

function toggleTopButtons() {


    if (showLoggedIn) {
        $("#log-in").show();
        $("#welcome-label").hide();
        $('#send-message').hide();
        showLoggedIn = false;
    }
    else {
        $("#log-in").hide();
        $("#welcome-label").show();
        $('#send-message').show();
        document.getElementById('welcome-label').innerHTML = 'Welcome, ' + userName + '!';
        showLoggedIn = true;
    }
}


async function connect() {

    var dataString = $('#login-form').serialize();
    var req = new XMLHttpRequest();
    var url = "/login";
    req.open("POST", url, true);
    req.setRequestHeader("Content-type", "application/x-www-form-urlencoded");
    req.send(dataString);

    req.onload = () => {
        if (req.status == 200) {
            userName = $('#user-name').val();
            toggleTopButtons();


        }
        else alert('Error logging in');
    }



}

async function disconnect() {
    if (stompClient !== null) {
        stompClient.disconnect();
    }
    //setConnected(false);
    console.log("Disconnected");
    window.location = "/logout";

}

function sendMessage() {
    stompClient.send("/app/hello", {}, JSON.stringify({
        'name': userName,
        'message': $("#message-text").val()
    }));
}

function showChatMessages(message) {
    $("#text-field-container").append("<tr><td>" + message + "</td></tr>");
}

function kp(ev, input) {
if(ev.keyCode==13) {

    ev.preventDefault();
    sendMessage();
    document.getElementById("message-text").value = '';
    input.value = "";

}


}

$(function () {
    $("form").on('submit', function (e) {
        e.preventDefault();
    });
    document.getElementById("connect-disconnect").onclick = function () {
        if (showLoggedIn == false) connect();
        else disconnect();

    }
    $("#send").click(function () {

    sendMessage();
    document.getElementById("message-text").value = '';
    });
});