<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
	<link href="jquery/bootstrap_3.3.0/css/bootstrap.min.css" type="text/css" rel="stylesheet" />
	<script type="text/javascript" src="jquery/jquery-1.11.1-min.js"></script>
	<script type="text/javascript" src="jquery/bootstrap_3.3.0/js/bootstrap.min.js"></script>
	<script type="text/javascript">
		$(function () {
			if (window.top != window){
				window.top.location=window.location;
			}

			$("#account").focus()

			$("#account").mousedown(function () {
				$("#msg").html("");
			})

			$("#password").mousedown(function () {
				$("#msg").html("");
			})

			$("#login").click(function () {
				login();
			})

			$(window).keydown(function (event) {
				if (event.keyCode == "13"){
					login();
				}
			})
		})

		function login() {
			var account = $.trim($("#account").val());
			var password = $.trim($("#password").val());
			var msg = $("#msg");
			if(account==""||password==""){
				msg.html("账号&密码不能为空！");
				return false;
			}
			$.ajax({
				async:"false",
				type:"post",
				url:"settings/user/login.do",
				dataType:"json",
				data:{
					"account":account,
					"password":password
				},
				success:function (data) {
					if (data.success){
						window.location.href = "workbench/index.jsp"
					}else {
						$("#msg").html(data.msg);
					}
				}
			})
		}

	</script>
</head>
	<body background="image/background.JPG">
		<div style="position: absolute; top: 0px; left: 0px; width: 60%;">
			<%--<img src="image/IMG_7114.JPG" style="width: 100%; height: 90%; position: relative; top: 50px;">--%>
		</div>
		<div id="top" style="height: 50px; background-color: #3C3C3C; width: 100%;">
			<div style="position: absolute; top: 5px; left: 0px; font-size: 30px; font-weight: 400; color: white; font-family: 'times new roman'">CRM &nbsp;<span style="font-size: 12px;">&copy;2017&nbsp;动力节点</span></div>
		</div>

		<div align="center" style="border: 1px hotpink;border-radius:45px;position: absolute; top: 180px; right: 700px;width:450px;height:310px;background: #EEEEEE">
			<div style="position: absolute; top: 0px; right: 65px;">
				<h1><b style="font-size: 35px;">CRM后台系统登录</b></h1>
				<h1></h1>
				<form action="workbench/index.html" class="form-horizontal" role="form">
					<div class="form-group form-group-lg">
						<div style="width: 350px;">
							<input class="form-control" type="text" placeholder="用户名" id="account">
						</div>
						<div style="width: 350px; position: relative;top: 20px;">
							<input class="form-control" type="password" placeholder="密码" id="password">
						</div>
						<div class="checkbox"  style="position: relative;top: 30px; left: 10px;">
							<span id="msg" style="color: red;font-size:15px;font-weight: bold"></span>
						</div>
						<button type="button" id="login" class="btn btn-primary btn-lg btn-block"  style="width: 350px; position: relative;top: 45px;">登录</button>
					</div>
				</form>
			</div>
		</div>

	</body>
</html>