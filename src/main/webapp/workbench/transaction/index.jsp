<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%
String basePath = request.getScheme() + "://" + request.getServerName() + ":"
+ request.getServerPort() + request.getContextPath() + "/";
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

	<link rel="stylesheet" type="text/css" href="jquery/bs_pagination/jquery.bs_pagination.min.css">
	<script type="text/javascript" src="jquery/bs_pagination/jquery.bs_pagination.min.js"></script>
	<script type="text/javascript" src="jquery/bs_pagination/en.js"></script>

	<script type="text/javascript">

		$(function(){

			//加载交易列表信息功能，
			TransactionPageList(1,5);

		});

		//获取交易列表信息请求，
		function TransactionPageList(pages,pageNum) {
			$.ajax({
				url:"workbench/transaction/getTransactionList.do",
				type:"get",
				data:{
					"pages":pages,
					"pageNum":pageNum,
					"owner":$.trim($("#search-owner").val()),
					"name":$.trim($("#search-name").val()),
					"customerName":$.trim($("#search-customerName").val()),
					"contactsName":$.trim($("#search-contactsName").val()),
					"stage":$.trim($("#search-stage").val()),
					"transactionType":$.trim($("#search-transactionType").val()),
					"source":$.trim($("#search-source").val())
				},
				dataType:"json",
				success:function (data) {

					var html = "";

					$.each(data.dataList,function (key,value) {
						html += '<tr class="active">';
						html += '<td><input type="checkbox" id="'+value.id+'"/></td>';
						html += '<td><a style="text-decoration: none; cursor: pointer;" onclick="window.location.href=\'workbench/transaction/getDetailInfo.do?tranId='+value.id+'\';">'+value.name+'</a></td>';
						html += '<td>'+value.customerId+'</td>';
						html += '<td>'+value.stage+'</td>';
						html += '<td>'+value.type+'</td>';
						html += '<td>'+value.owner+'</td>';
						html += '<td>'+value.source+'</td>';
						html += '<td>'+value.contactsId+'</td>';
						html += '</tr>';
					})

					$("#transactionTbody").html(html);

					var totalPages = data.total % pageNum==0 ? data.total/pageNum:parseInt(data.total/pageNum)+1;

					$("#transactionPagination").bs_pagination({
						currentPage: pages,		//页码
						rowsPerPage:pageNum,	//每页显示的记录条数
						maxRowsPerPage: 20,		//每页最多显示的记录条数
						totalPages: totalPages,	//总页数
						totalRows: data.total,	//总记录条数

						visiblePageLinks: 4,	//显示几个卡片

						showGoToPage: true,
						showRowsPerPage: true,
						showRowsInfo: true,
						showRowsDefaultInfo: true,

						onChangePage:function (event,data) {
							TransactionPageList(data.currentPage,data.rowsPerPage);
						}
					});
				}
			})
		}
	</script>
</head>
<body>

	
	
	<div>
		<div style="position: relative; left: 10px; top: -10px;">
			<div class="page-header">
				<h3>交易列表</h3>
			</div>
		</div>
	</div>
	
	<div style="position: relative; top: -20px; left: 0px; width: 100%; height: 100%;">
	
		<div style="width: 100%; position: absolute;top: 5px; left: 10px;">
		
			<div class="btn-toolbar" role="toolbar" style="height: 80px;">
				<form class="form-inline" role="form" style="position: relative;top: 8%; left: 5px;">
				  
				  <div class="form-group">
				    <div class="input-group">
				      <div class="input-group-addon">所有者</div>
				      <input class="form-control" id="search-owner" type="text">
				    </div>
				  </div>
				  
				  <div class="form-group">
				    <div class="input-group">
				      <div class="input-group-addon">名称</div>
				      <input class="form-control" id="search-name" type="text">
				    </div>
				  </div>
				  
				  <div class="form-group">
				    <div class="input-group">
				      <div class="input-group-addon">客户名称</div>
				      <input class="form-control" id="search-customerName" type="text">
				    </div>
				  </div>

					<div class="form-group">
						<div class="input-group">
							<div class="input-group-addon">联系人名称</div>
							<input class="form-control" id="search-contactsName" type="text">
						</div>
					</div>
				  <br>
				  
				  <div class="form-group">
				    <div class="input-group">
				      <div class="input-group-addon">阶段</div>
					  <select class="form-control" id="search-stage">
					  	<option></option>
					  	<c:forEach items="${stage}" var="c">
							<option value="${c.value}">${c.text}</option>
						</c:forEach>
					  </select>
				    </div>
				  </div>
				  
				  <div class="form-group">
				    <div class="input-group">
				      <div class="input-group-addon">类型</div>
					  <select class="form-control" id="search-transactionType">
					  	<option></option>
						  <c:forEach items="${transactionType}" var="t">
							  <option value="${t.value}">${t.text}</option>
						  </c:forEach>
					  </select>
				    </div>
				  </div>
				  
				  <div class="form-group">
				    <div class="input-group">
				      <div class="input-group-addon">来源</div>
				      <select class="form-control" id="search-clueSource">
						  <option></option>
						  <c:forEach items="${source}" var="c">
							  <option value="${c.value}">${c.text}</option>
						  </c:forEach>
						</select>
				    </div>
				  </div>
				  
				  <button type="submit" class="btn btn-default" id="queryBtn">查询</button>
				  
				</form>
			</div>
			<div class="btn-toolbar" role="toolbar" style="background-color: #F7F7F7; height: 50px; position: relative;top: 10px;">
				<div class="btn-group" style="position: relative; top: 18%;">
				  <button type="button" class="btn btn-primary" onclick="window.location.href='workbench/transaction/openSaveWindow.do';"><span class="glyphicon glyphicon-plus"></span> 创建</button>
				  <button type="button" class="btn btn-default" onclick="window.location.href='edit.jsp';"><span class="glyphicon glyphicon-pencil"></span> 修改</button>
				  <button type="button" class="btn btn-danger"><span class="glyphicon glyphicon-minus"></span> 删除</button>
				</div>
				
				
			</div>
			<div style="position: relative;top: 10px;">
				<table class="table table-hover">
					<thead>
						<tr style="color: #B3B3B3;">
							<td><input type="checkbox" /></td>
							<td>名称</td>
							<td>客户名称</td>
							<td>阶段</td>
							<td>类型</td>
							<td>所有者</td>
							<td>来源</td>
							<td>联系人名称</td>
						</tr>
					</thead>
					<tbody id="transactionTbody">
						<%--<tr>
							<td><input type="checkbox" /></td>
							<td><a style="text-decoration: none; cursor: pointer;" onclick="window.location.href='workbench/transaction/detail.jsp';">动力节点-交易01</a></td>
							<td>动力节点</td>
							<td>谈判/复审</td>
							<td>新业务</td>
							<td>zhangsan</td>
							<td>广告</td>
							<td>李四</td>
						</tr>
                        <tr class="active">
                            <td><input type="checkbox" /></td>
                            <td><a style="text-decoration: none; cursor: pointer;" onclick="window.location.href='workbench/transaction/detail.jsp';">动力节点-交易01</a></td>
                            <td>动力节点</td>
                            <td>谈判/复审</td>
                            <td>新业务</td>
                            <td>zhangsan</td>
                            <td>广告</td>
                            <td>李四</td>
                        </tr>--%>
					</tbody>
				</table>
			</div>

			<div style="height: 50px; position: relative;top: 50px;">

				<div id="transactionPagination">

				</div>

			</div>
			
		</div>
		
	</div>
</body>
</html>