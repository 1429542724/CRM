<%@ page import="java.util.Map" %>
<%@ page import="java.util.Set" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="userInfo" uri="http://java.sun.com/jsp/jstl/core" %>
<%
	String basePath = request.getScheme() + "://" + request.getServerName() + ":"
	+ request.getServerPort() + request.getContextPath() + "/";

	Map<String,String> stageMap = (Map<String, String>) application.getAttribute("stageMap");
	Set<String> keySet = stageMap.keySet();
%>
<html>
<head>
	<base href="<%=basePath%>">

	<link href="jquery/bootstrap_3.3.0/css/bootstrap.min.css" type="text/css" rel="stylesheet" />
	<link href="jquery/bootstrap-datetimepicker-master/css/bootstrap-datetimepicker.min.css" type="text/css" rel="stylesheet" />

	<script type="text/javascript" src="jquery/jquery-1.11.1-min.js"></script>
	<script type="text/javascript" src="jquery/bootstrap_3.3.0/js/bootstrap.min.js"></script>
	<script type="text/javascript" src="jquery/bootstrap-datetimepicker-master/js/bootstrap-datetimepicker.js"></script>
	<script type="text/javascript" src="jquery/bootstrap-datetimepicker-master/locale/bootstrap-datetimepicker.zh-CN.js"></script>

	<script type="text/javascript" src="jquery/bs_typeahead/bootstrap3-typeahead.min.js"></script>
	<script type="text/javascript">
		$(function () {

			//保存交易信息请求，
			$("#saveTransactionBtn").on("click",function () {

				if ($.trim($("#create-transactionName").val()) == ""){
					alert("名称不能为空！")
				}else if ($.trim($("#create-expectedClosingDate").val()) == ""){
					alert("预计成交日期不能为空！")
				}else if ($.trim($("#create-customerName").val()) == ""){
					alert("客户名称不能为空！");
				}else if ($.trim($("#create-transactionStage").val()) == ""){
					alert("阶段不能为空！");
				}else {
					$("#transactionForm").submit();
				}
			})

			//关联联系人功能，
			$("#contactsRelationBtn").on("click",function () {
				var param = $("input[name=cxz]:checked").attr("id");

				$("#hiddenContactsId").val(param);

				$("#create-contactsName").val($("#c"+param).html())

				$("#findContacts").modal("hide");

			})

			//查找联系人功能，
			$("#searchContactsNameFrame").on("keydown",function (event) {

				if (event.keyCode == 13){

					getContactsList();

					return false;
				}
			})

		    //打开搜索联系人窗口功能
		    $("#searchContactsBtn").on("click",function () {

		        $("#searchContactsNameFrame").val("");

		        $("#contactsTbody").html("");

                $("#findContacts").modal("show");
            })

		    //可能性JSON字符串，
			var json = {
				<%
					for (String key:keySet){
						String value = stageMap.get(key);
				%>
				"<%=key%>":"<%=value%>",
				<%
					};
				%>
			}

			//获取可能性，
			$("#create-transactionStage").on("change",function () {

				var stage = $("#create-transactionStage").val();

				var possibility = json[stage];

				$("#create-possibility").val(possibility);
			})

			//关联市场活动功能，
			$("#relationBtn").on("click",function () {
				var param = $("input[name=xz]:checked").attr("id");

				$("#hiddenActivityId").val(param)

				$("#create-activitySrc").val($("#e"+param).html());

				$("#findMarketActivity").modal("hide");
			})

			//打开搜索市场活动窗口功能，
			$("#searchBtn").on("click",function () {
				$("#searchActivity").val("");

				$("#activityFrame").html("");

				$("#findMarketActivity").modal("show");
			})

			//查找市场活动功能，
			$("#searchActivity").on("keydown",function (event) {
				if (event.keyCode == 13){

					getActivityList();

					return false;
				}
			})

			//自动补全插件，
			$("#create-customerName").typeahead({
				source: function (query, process) {
					$.get(
							"workbench/transaction/getCustomerName.do",
							{ "name" : query },
							function (data) {
								//alert(data);
								process(data);
							},
							"json"
					);
				},
				delay: 1000
			});

			//日期插件模块，
			$(".time1").datetimepicker({
				minView: "month",
				language:  'zh-CN',
				format: 'yyyy-mm-dd',
				autoclose: true,
				todayBtn: true,
				pickerPosition: "bottom-left"
			});

			$(".time2").datetimepicker({
				minView: "month",
				language:  'zh-CN',
				format: 'yyyy-mm-dd',
				autoclose: true,
				todayBtn: true,
				pickerPosition: "top-left"
			});


		})

		//搜索市场活动请求，
		function getActivityList() {
			$.ajax({
				url:"workbench.clue/getActivityList.do",
				type:"get",
				data:{"DimSearchFrame":$("#searchActivity").val()},
				dataType:"json",
				success:function (data) {

					var html = "";
					$.each(data,function (k,v) {
						html += '<tr>';
						html += '<td><input type="radio" name="xz" id="'+v.id+'"/></td>';
						html += '<td id="e'+v.id+'">'+v.name+'</td>';
						html += '<td>'+v.startDate+'</td>';
						html += '<td>'+v.endDate+'</td>';
						html += '<td>'+v.owner+'</td>';
						html += '</tr>';
					});

					$("#activityFrame").html(html);
				}
			})
		}

		//搜索联系人请求,
        function getContactsList() {
            $.ajax({
                url:"workbench/transaction/getContactsList.do",
                type:"get",
                data:{"contactsName":$("#searchContactsNameFrame").val()},
                dataType:"json",
                success:function (data) {

                	var html = "";
                	$.each(data,function (k,v) {
						html += '<tr>';
						html += '<td><input type="radio" name="cxz" id="'+v.id+'"/></td>';
						html += '<td id="c'+v.id+'">'+v.fullname+'</td>';
						html += '<td>'+v.email+'</td>';
						html += '<td>'+v.mphone+'</td>';
						html += '</tr>';
					})

					$("#contactsTbody").html(html);

                }
            })
        }

	</script>
</head>
<body>

	<!-- 查找市场活动 -->	
	<div class="modal fade" id="findMarketActivity" role="dialog">
		<div class="modal-dialog" role="document" style="width: 45%;">
			<div class="modal-content">
				<div class="modal-header">
					<button type="button" class="close" data-dismiss="modal">
						<span aria-hidden="true">×</span>
					</button>
					<h4 class="modal-title">查找市场活动</h4>
				</div>
				<div class="modal-body">
					<div class="btn-group" style="position: relative; top: 18%; left: 8px;">
						<form class="form-inline" role="form">
						  <div class="form-group has-feedback">
						    <input type="text" id="searchActivity" class="form-control" style="width: 300px;" placeholder="请输入市场活动名称，支持模糊查询">
						    <span class="glyphicon glyphicon-search form-control-feedback"></span>
						  </div>
						</form>
					</div>
					<table id="activityTable3" class="table table-hover" style="width: 900px; position: relative;top: 10px;">
						<thead>
							<tr style="color: #B3B3B3;">
								<td></td>
								<td>名称</td>
								<td>开始日期</td>
								<td>结束日期</td>
								<td>所有者</td>
							</tr>
						</thead>
						<tbody id="activityFrame">
							<%--<tr>
								<td><input type="radio" name="activity"/></td>
								<td>发传单</td>
								<td>2020-10-10</td>
								<td>2020-10-20</td>
								<td>zhangsan</td>
							</tr>
							<tr>
								<td><input type="radio" name="activity"/></td>
								<td>发传单</td>
								<td>2020-10-10</td>
								<td>2020-10-20</td>
								<td>zhangsan</td>
							</tr>--%>
						</tbody>
					</table>
				</div>
				<div class="modal-footer">
					<div id="activityPage">

					</div>
					<button type="button" class="btn btn-default" data-dismiss="modal">取消</button>
					<button type="button" class="btn btn-primary" id="relationBtn">关联</button>
				</div>
			</div>
		</div>
	</div>

	<!-- 查找联系人 -->	
	<div class="modal fade" id="findContacts" role="dialog">
		<div class="modal-dialog" role="document" style="width: 45%;">
			<div class="modal-content">
				<div class="modal-header">
					<button type="button" class="close" data-dismiss="modal">
						<span aria-hidden="true">×</span>
					</button>
					<h4 class="modal-title">查找联系人</h4>
				</div>
				<div class="modal-body">
					<div class="btn-group" style="position: relative; top: 18%; left: 8px;">
						<form class="form-inline" role="form">
						  <div class="form-group has-feedback">
						    <input type="text" id="searchContactsNameFrame" class="form-control" style="width: 300px;" placeholder="请输入联系人名称，支持模糊查询">
						    <span class="glyphicon glyphicon-search form-control-feedback"></span>
						  </div>
						</form>
					</div>
					<table id="activityTable" class="table table-hover" style="width: 900px; position: relative;top: 10px;">
						<thead>
							<tr style="color: #B3B3B3;">
								<td></td>
								<td>名称</td>
								<td>邮箱</td>
								<td>手机</td>
							</tr>
						</thead>
						<tbody id="contactsTbody">
							<%--<tr>
								<td><input type="radio" name="activity"/></td>
								<td>李四</td>
								<td>lisi@bjpowernode.com</td>
								<td>12345678901</td>
							</tr>
							<tr>
								<td><input type="radio" name="activity"/></td>
								<td>李四</td>
								<td>lisi@bjpowernode.com</td>
								<td>12345678901</td>
							</tr>--%>
						</tbody>
					</table>
				</div>
				<div class="modal-footer">
					<button type="button" class="btn btn-default" data-dismiss="modal">取消</button>
					<button type="button" class="btn btn-primary" id="contactsRelationBtn">关联</button>
				</div>
			</div>
		</div>
	</div>
	
	
	<div style="position:  relative; left: 30px;">
		<h3>创建交易</h3>
	  	<div style="position: relative; top: -40px; left: 70%;">
			<button type="button" class="btn btn-primary" id="saveTransactionBtn">保存</button>
			<button type="button" class="btn btn-default"  onclick="window.history.go(-1);">取消</button>
		</div>
		<hr style="position: relative; top: -40px;">
	</div>
	<form class="form-horizontal" role="form" style="position: relative; top: -30px;" action="workbench/transaction/saveTransaction.do" method="post" id="transactionForm">
			<%-- 隐藏作用域 --%>
			<input type="hidden" id="hiddenActivityId" name="hiddenActivityId" value="">
			<input type="hidden" id="hiddenContactsId" name="hiddenContactsId" value="">
			<div class="form-group">
				<label for="create-transactionOwner" class="col-sm-2 control-label">所有者<span style="font-size: 15px; color: red;">*</span></label>
				<div class="col-sm-10" style="width: 300px;">
					<select class="form-control" id="create-transactionOwner" name="create-transactionOwner">
						<userInfo:forEach items="${userInfo}" var="u">
							<option value="${u.id}" ${user.id eq u.id ? "selected":""}>${u.name}</option>
						</userInfo:forEach>
					</select>
				</div>
				<label for="create-amountOfMoney" class="col-sm-2 control-label">金额</label>
				<div class="col-sm-10" style="width: 300px;">
					<input type="text" class="form-control" id="create-amountOfMoney" name="create-amountOfMoney">
				</div>
			</div>

			<div class="form-group">
				<label for="create-transactionName" class="col-sm-2 control-label">名称<span style="font-size: 15px; color: red;">*</span></label>
				<div class="col-sm-10" style="width: 300px;">
					<input type="text" class="form-control" id="create-transactionName" name="create-transactionName">
				</div>
				<label for="create-expectedClosingDate" class="col-sm-2 control-label">预计成交日期<span style="font-size: 15px; color: red;">*</span></label>
				<div class="col-sm-10" style="width: 300px;">
					<input type="text" class="form-control time1" id="create-expectedClosingDate" name="create-expectedClosingDate" readonly>
				</div>
			</div>

			<div class="form-group">
				<label for="create-customerName" class="col-sm-2 control-label">客户名称<span style="font-size: 15px; color: red;">*</span></label>
				<div class="col-sm-10" style="width: 300px;">
					<input type="text" class="form-control" id="create-customerName" name="create-customerName" placeholder="支持自动补全，输入客户不存在则新建">
				</div>
				<label for="create-transactionStage" class="col-sm-2 control-label">阶段<span style="font-size: 15px; color: red;">*</span></label>
				<div class="col-sm-10" style="width: 300px;">
				  <select class="form-control" id="create-transactionStage" name="create-transactionStage">
					<option></option>
					<userInfo:forEach items="${stage}" var="s">
						<option value="${s.value}">${s.text}</option>
					</userInfo:forEach>
				  </select>
				</div>
			</div>

			<div class="form-group">
				<label for="create-transactionType" class="col-sm-2 control-label">类型</label>
				<div class="col-sm-10" style="width: 300px;">
					<select class="form-control" id="create-transactionType" name="create-transactionType">
					  <option></option>
					  <userInfo:forEach items="${transactionType}" var="t">
						  <option value="${t.value}">${t.text}</option>
					  </userInfo:forEach>
					</select>
				</div>
				<label for="create-possibility" class="col-sm-2 control-label">可能性</label>
				<div class="col-sm-10" style="width: 300px;">
					<input type="text" class="form-control" id="create-possibility" name="create-possibility" readonly>
				</div>
			</div>

			<div class="form-group">
				<label for="create-clueSource" class="col-sm-2 control-label">来源</label>
				<div class="col-sm-10" style="width: 300px;">
					<select class="form-control" id="create-clueSource" name="create-clueSource">
					  <option></option>
						<userInfo:forEach items="${source}" var="c">
							<option value="${c.value}">${c.text}</option>
						</userInfo:forEach>
					</select>
				</div>
				<label for="create-activitySrc" class="col-sm-2 control-label">市场活动源&nbsp;&nbsp;<a href="javascript:void(0);" data-target="#findMarketActivity" id="searchBtn"><span class="glyphicon glyphicon-search"></span></a></label>
				<div class="col-sm-10" style="width: 300px;">
					<input type="text" class="form-control" id="create-activitySrc" name="create-activitySrc" readonly>
				</div>
			</div>

			<div class="form-group">
				<label for="create-contactsName" class="col-sm-2 control-label">联系人名称&nbsp;&nbsp;<a href="javascript:void(0);" data-target="#findContacts" id="searchContactsBtn"><span class="glyphicon glyphicon-search"></span></a></label>
				<div class="col-sm-10" style="width: 300px;">
					<input type="text" class="form-control" id="create-contactsName" name="create-contactsName" readonly>
				</div>
			</div>

			<div class="form-group">
				<label for="create-describe" class="col-sm-2 control-label">描述</label>
				<div class="col-sm-10" style="width: 55%;">
					<textarea class="form-control" rows="3" id="create-describe" name="create-describe"></textarea>
				</div>
			</div>

			<div class="form-group">
				<label for="create-contactSummary" class="col-sm-2 control-label">联系纪要</label>
				<div class="col-sm-10" style="width: 55%;">
					<textarea class="form-control" rows="3" id="create-contactSummary" name="create-contactSummary"></textarea>
				</div>
			</div>

			<div class="form-group">
				<label for="create-nextContactTime" class="col-sm-2 control-label">下次联系时间</label>
				<div class="col-sm-10" style="width: 300px;">
					<input type="text" class="form-control time2" id="create-nextContactTime" name="create-nextContactTime" readonly>
				</div>
			</div>

	</form>
</body>
</html>