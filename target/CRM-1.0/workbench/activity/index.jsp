<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%
	String basePath = request.getScheme() + "://" + request.getServerName() + ":"
			+ request.getServerPort() + request.getContextPath() + "/";
%>
<html>
<head>
	<base href="<%=basePath%>">
	<meta charset="UTF-8">

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
			//页面加载获取市场活动列表信息，
			pageList(1,3);

			//打开创建市场活动窗口，
			$("#addBtn").click(function () {
				/*
				为创建按钮绑定事件，打开添加操作的模态窗口。
				操作模态窗口的方式：
					需要操作的模态窗口的jquery对象，调用modal方法，为该方法传递参数 show:打开模态窗口   hide：关闭模态窗口。
				*/
				//$("#createActivityModal").modal("show")
				$(".time").datetimepicker({
					minView: "month",
					language:  'zh-CN',
					format: 'yyyy-mm-dd',
					autoclose: true,
					todayBtn: true,
					pickerPosition: "bottom-left"
				});

				$.ajax({
					async:"false",
					url:"workbench/activity/getUserList.do",
					type:"get",
					dataType:"json",
					success:function (data) {
						var html = "";

						$.each(data,function (k,v) {
							html += "<option value='"+v.id+"'>"+v.name+"</opcion>"
						})

						$("#create-marketActivityOwner").html(html);

						var userId = "${user.id}";

						$("#create-marketActivityOwner").val(userId);

						$("#createActivityModal").modal("show")
					}
				})
			})

			//为保存按钮添加事件，执行添加请求。
			$("#saveBtn").click(function(){
				$.ajax({
					async: false,
					url:"workbench/activity/save.do",
					dataType: "json",
					type: "post",
					data: {
						"owner":$.trim($("#create-marketActivityOwner").val()),
						"name":$.trim($("#create-marketActivityName").val()),
						"startDate":$.trim($("#create-startTime").val()),
						"endDate":$.trim($("#create-endTime").val()),
						"cost":$.trim($("#create-cost").val()),
						"description":$.trim($("#create-describe").val())
					},
					success:function (data) {
						if (data.success){

							pageList(1,3);

							$("#activityAddForm")[0].reset();

							$("#createActivityModal").modal("hide");
						}else {
							alert(data.message);
						}
					}
				})
			})

			//为删除按钮添加事件，执行删除请求。
			$("#deleteBtn").on("click",function () {
				if (confirm("您确定删除选中的信息吗？")){
					var $qx = $("input[name=xz]:checked");
					if ($qx.length == 0 ){
						alert("请选择你要删除的数据!")
					}else {
						var param = "";
						for (var i=0;i<$qx.length;i++){

							param += "id="+$($qx[i]).val();
							if (i<$qx.length-1){
								param += "&";
							}
						}
						$.ajax({
							async:false,
							url:"workbench/activity/delete.do",
							type:"post",
							data:param,
							dataType:"json",
							success:function (data) {
								if (data.success()){
									pageList(1,3)
								}else {
									alert("删除市场活动失败");
								}
							}
						});
					}
				}

			})

			//为修改按钮添加事件，执行修改请求
			$("#editBtn").on("click",function () {

				var $xz = $("input[name=xz]:checked");
				if ($xz.length == 0){
					alert("请选择你要修改的数据!")
				}else if ($xz.length > 1){
					alert("请选中一条数据，不要多选。")
				}else {
					var id = $xz.val();

					$.ajax({
						async:"false",
						url:"workbench/activity/edit.do",
						type:"get",
						data:{"id":id},
						dataType:"json",
						success:function () {

						}
					})
				}
			})

			//为查询按钮绑定事件，执行条件查询请求。
			$("#searchBtn").click(function () {

				$("#hidden-name").val($.trim($("#search-name").val()));
				$("#hidden-owner").val($.trim($("#search-owner").val()));
				$("#hidden-startDate").val($.trim($("#search-startDate").val()));
				$("#hidden-endDate").val($.trim($("#search-endDate").val()));

				pageList(1,3);
			})

			//市场活动列表多条选择功能，
			$("#qx").click(function () {

				$("input[name=xz]").prop("checked",this.checked);
			})

			//市场活动列表单条或多条选择功能，
			$("#activityBody").on("click",$("input[name=xz]"),function() {

				$("#qx").prop("checked",$("input[name=xz]").length == $("input[name=xz]:checked").length);
			})

		});

		//查询市场活动列表中所有活动信息函数
		function pageList(pageNo,pageSize){

			$("#qx").prop("checked",false);

			//查询前，将隐藏域中保存的信息取出来，重新赋予到搜索框中
			$("#search-name").val($.trim($("#hidden-name").val()));
			$("#search-owner").val($.trim($("#hidden-owner").val()));
			$("#search-startDate").val($.trim($("#hidden-startDate").val()));
			$("#search-endDate").val($.trim($("#hidden-endDate").val()));

			$.ajax({
				async:false,
				url:"workbench/activity/pageList.do",
				type:"get",
				dataType:"json",
				data:{
					"pageNo":pageNo,
					"pageSize":pageSize,
					"name":$.trim($("#search-name").val()),
					"owner":$.trim($("#search-owner").val()),
					"startDate":$.trim($("#search-startDate").val()),
					"endDate":$.trim($("#search-endDate").val())
				},
				success:function (data) {

					var html = "";
					$.each(data.dataList,function (i,n) {
						html += '<tr class="active">';
						html += '<td><input type="checkbox" name="xz" value="'+n.id+'"/></td>';
						html += '<td><a style="text-decoration: none; cursor: pointer;" ' +
								'onclick="window.location.href=\'workbench/activity/detail.do?id='+n.id+'\';">'+n.name+'</a></td>';
						html += '<td>'+n.owner+'</td>';
						html += '<td>'+n.startDate+'</td>';
						html += '<td>'+n.endDate+'</td>';
						html += '</tr>';
					})

					$("#activityBody").html(html);

					var totalPages = data.total % pageSize==0 ? data.total/pageSize:parseInt(data.total/pageSize)+1;

					$("#activityPage").bs_pagination({
						currentPage: pageNo,	//页码
						rowsPerPage:pageSize,	//每页显示的记录条数
						maxRowsPerPage: 20,	//每页最多显示的记录条数
						totalPages: totalPages,	//总页数
						totalRows: data.total,	//总记录条数

						visiblePageLinks: 3,	//显示几个卡片

						showGoToPage: true,
						showRowsPerPage: true,
						showRowsInfo: true,
						showRowsDefaultInfo: true,

						onChangePage:function (event,data) {
							pageList(data.currentPage,data.rowsPerPage);
						}
					});
				}
			})

		}

	</script>
</head>
<body>
	<input type="hidden" id="hidden-name"/>
	<input type="hidden" id="hidden-owner"/>
	<input type="hidden" id="hidden-startDate"/>
	<input type="hidden" id="hidden-endDate"/>

	<!-- 创建市场活动的模态窗口 -->
	<div class="modal fade" id="createActivityModal" role="dialog">
		<div class="modal-dialog" role="document" style="width: 85%;">
			<div class="modal-content">
				<div class="modal-header">
					<button type="button" class="close" data-dismiss="modal">
						<span aria-hidden="true">×</span>
					</button>
					<h4 class="modal-title" id="myModalLabel1">创建市场活动</h4>
				</div>
				<div class="modal-body">
				
					<form class="form-horizontal" role="form" id="activityAddForm">
					
						<div class="form-group">
							<label for="create-marketActivityOwner" class="col-sm-2 control-label">所有者<span style="font-size: 15px; color: red;">*</span></label>
							<div class="col-sm-10" style="width: 300px;">
								<select class="form-control" id="create-marketActivityOwner">

								</select>
							</div>
                            <label for="create-marketActivityName" class="col-sm-2 control-label">名称<span style="font-size: 15px; color: red;">*</span></label>
                            <div class="col-sm-10" style="width: 300px;">
                                <input type="text" class="form-control" id="create-marketActivityName">
                            </div>
						</div>
						
						<div class="form-group">
							<label for="create-startTime" class="col-sm-2 control-label">开始日期</label>
							<div class="col-sm-10" style="width: 300px;">
								<input type="text" class="form-control time" id="create-startTime"readonly/>
							</div>
							<label for="create-endTime" class="col-sm-2 control-label">结束日期</label>
							<div class="col-sm-10" style="width: 300px;">
								<input type="text" class="form-control time" id="create-endTime"readonly/>
							</div>
						</div>
                        <div class="form-group">

                            <label for="create-cost" class="col-sm-2 control-label">成本</label>
                            <div class="col-sm-10" style="width: 300px;">
                                <input type="text" class="form-control" id="create-cost">
                            </div>
                        </div>
						<div class="form-group">
							<label for="create-describe" class="col-sm-2 control-label">描述</label>
							<div class="col-sm-10" style="width: 81%;">
								<textarea class="form-control" rows="3" id="create-describe"></textarea>
							</div>
						</div>
						
					</form>
					
				</div>
				<div class="modal-footer">
					<button type="button" class="btn btn-default" data-dismiss="modal">关闭</button>
					<button type="button" class="btn btn-primary" id="saveBtn">保存</button>
				</div>
			</div>
		</div>
	</div>
	
	<!-- 修改市场活动的模态窗口 -->
	<div class="modal fade" id="editActivityModal" role="dialog">
		<div class="modal-dialog" role="document" style="width: 85%;">
			<div class="modal-content">
				<div class="modal-header">
					<button type="button" class="close" data-dismiss="modal">
						<span aria-hidden="true">×</span>
					</button>
					<h4 class="modal-title" id="myModalLabel2">修改市场活动</h4>
				</div>
				<div class="modal-body">
				
					<form class="form-horizontal" role="form">
					
						<div class="form-group">
							<label for="edit-marketActivityOwner" class="col-sm-2 control-label">所有者<span style="font-size: 15px; color: red;">*</span></label>
							<div class="col-sm-10" style="width: 300px;">
								<select class="form-control" id="edit-owner">

								</select>
							</div>
                            <label for="edit-marketActivityName" class="col-sm-2 control-label">名称<span style="font-size: 15px; color: red;">*</span></label>
                            <div class="col-sm-10" style="width: 300px;">
                                <input type="text" class="form-control" id="edit-name">
                            </div>
						</div>

						<div class="form-group">
							<label for="edit-startTime" class="col-sm-2 control-label">开始日期</label>
							<div class="col-sm-10" style="width: 300px;">
								<input type="text" class="form-control time" id="edit-startDate">
							</div>
							<label for="edit-endTime" class="col-sm-2 control-label">结束日期</label>
							<div class="col-sm-10" style="width: 300px;">
								<input type="text" class="form-control time" id="edit-endDate">
							</div>
						</div>
						
						<div class="form-group">
							<label for="edit-cost" class="col-sm-2 control-label">成本</label>
							<div class="col-sm-10" style="width: 300px;">
								<input type="text" class="form-control" id="edit-cost">
							</div>
						</div>
						
						<div class="form-group">
							<label for="edit-describe" class="col-sm-2 control-label">描述</label>
							<div class="col-sm-10" style="width: 81%;">
								<textarea class="form-control" rows="3" id="edit-describe"></textarea>
							</div>
						</div>
						
					</form>
					
				</div>
				<div class="modal-footer">
					<button type="button" class="btn btn-default" data-dismiss="modal" id="edit-close">关闭</button>
					<button type="button" class="btn btn-primary" data-dismiss="modal" id="edit-update">更新</button>
				</div>
			</div>
		</div>
	</div>
	
	
	
	
	<div>
		<div style="position: relative; left: 10px; top: -10px;">
			<div class="page-header">
				<h3>市场活动列表</h3>
			</div>
		</div>
	</div>
	<div style="position: relative; top: -20px; left: 0px; width: 100%; height: 100%;">
		<div style="width: 100%; position: absolute;top: 5px; left: 10px;">

			<div class="btn-toolbar" role="toolbar" style="height: 80px;">
				<form class="form-inline" role="form" style="position: relative;top: 8%; left: 5px;">

					<div class="form-group">
						<div class="input-group">
							<div class="input-group-addon">名称</div>
							<input class="form-control" type="text" id="search-name">
						</div>
					</div>

					<div class="form-group">
						<div class="input-group">
							<div class="input-group-addon">所有者</div>
							<input class="form-control" type="text" id="search-owner">
						</div>
					</div>


					<div class="form-group">
						<div class="input-group">
							<div class="input-group-addon">开始日期</div>
							<input class="form-control" type="text" id="search-startDate" />
						</div>
					</div>
					<div class="form-group">
						<div class="input-group">
							<div class="input-group-addon">结束日期</div>
							<input class="form-control" type="text" id="search-endDate">
						</div>
					</div>

					<button type="button" id="searchBtn" class="btn btn-default">查询</button>

				</form>
			</div>
			<div class="btn-toolbar" role="toolbar" style="background-color: #F7F7F7; height: 50px; position: relative;top: 5px;">
				<div class="btn-group" style="position: relative; top: 18%;">
				  <button type="button" class="btn btn-primary" id="addBtn" ><span class="glyphicon glyphicon-plus"></span> 创建</button>
				  <button type="button" class="btn btn-default" id="editBtn"><span class="glyphicon glyphicon-pencil"></span> 修改</button>
				  <button type="button" class="btn btn-danger" id="deleteBtn"><span class="glyphicon glyphicon-minus"></span> 删除</button>
				</div>
				
			</div>
			<div style="position: relative;top: 10px;">
				<table class="table table-hover">
					<thead>
						<tr style="color: #B3B3B3;">
							<td><input type="checkbox" id="qx"/></td>
							<td>名称</td>
                            <td>所有者</td>
							<td>开始日期</td>
							<td>结束日期</td>
						</tr>
					</thead>
					<tbody id="activityBody">
						<%--<tr class="active">
							<td><input type="checkbox" /></td>
							<td><a style="text-decoration: none; cursor: pointer;" onclick="window.location.href='workbench/activity/detail.jsp';">发传单</a></td>
							<td>zhangsan</td>
							<td>2020-10-10</td>
							<td>2020-10-20</td>
						</tr>
						<tr class="active">
							<td><input type="checkbox" /></td>
							<td><a style="text-decoration: none; cursor: pointer;" onclick="window.location.href='detail.jsp';">发传单</a></td>
							<td>zhangsan</td>
							<td>2020-10-10</td>
							<td>2020-10-20</td>
						</tr>--%>
					</tbody>
				</table>
			</div>

			<div style="height: 50px; position: relative;top: 30px;">

				<div id="activityPage">

				</div>
			</div>

		</div>
	</div>
</body>
</html>