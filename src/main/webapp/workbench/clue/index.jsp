<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<%String basePath = request.getScheme() + "://" + request.getServerName() + ":"
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
			//页面加载获取线索列表信息，
			pageList(1,5);

			//日期组件
			$(".time").datetimepicker({
				minView: "month",
				language:  'zh-CN',
				format: 'yyyy-mm-dd',
				autoclose: true,
				todayBtn: true,
				pickerPosition: "top-left"
			});

			//为创建按钮绑定事件，打开创建线索框。
			$("#createBtn").on("click",function () {
				$.ajax({
					url:"workbench/clue/getUserList.do",
					type:"get",
					dataType:"json",
					success:function (data) {
						var html = "";
						$.each(data,function (key,value) {
							html += "<option value='"+value.id+"'>"+value.name+"</option>";
						})

						$("#create-owner").html(html);

						var id = "${user.id}";
						$("#create-owner").val(id);

						$("#createClueModal").modal("show");
					}
				});
			})

			//为保存按钮绑定事件，执行保存线索请求。
			$("#saveClue").on("click",function () {
				$.ajax({
					url:"workbench/clue/saveClue.do",
					type:"post",
					data: {
						"fullname":$.trim($("#create-name").val()),
						"appellation":$.trim($("#create-call").val()),
						"owner":$.trim($("#create-owner").val()),
						"company":$.trim($("#create-company").val()),
						"job":$.trim($("#create-job").val()),
						"email":$.trim($("#create-email").val()),
						"phone":$.trim($("#create-phone").val()),
						"website":$.trim($("#create-website").val()),
						"mphone":$.trim($("#create-mphone").val()),
						"state":$.trim($("#create-state").val()),
						"source":$.trim($("#create-source").val()),
						"description":$.trim($("#create-describe").val()),
						"contactSummary":$.trim($("#create-contactSummary").val()),
						"nextContactTime":$.trim($("#create-nextContactTime").val()),
						"address":$.trim($("#create-address").val())
					},
					dataType: "json",
					success:function (data) {
						if (data.success){
							pageList(1,$("#activityPage").bs_pagination('getOption', 'rowsPerPage'));

							$("#create-AllInfo")[0].reset();

							$("#createClueModal").modal("hide")
						}else {
							alert(data.message);
						}
					}
				});
			})

			//为查询按钮绑定事件，执行查询线索请求。
			$("#selectBtn").on("click",function () {
				$("#hidden-fullname").val($.trim($("#search-fullname").val()));
				$("#hidden-company").val($.trim($("#search-company").val()));
				$("#hidden-phone").val($.trim($("#search-phone").val()));
				$("#hidden-source").val($.trim($("#search-source").val()));
				$("#hidden-owner").val($.trim($("#search-owner").val()));
				$("#hidden-mphone").val($.trim($("#search-mphone").val()));
				$("#hidden-state").val($.trim($("#search-state").val()));

				pageList(1,$("#activityPage").bs_pagination('getOption', 'rowsPerPage'));
			})

			//为修改按钮绑定事件，打开修改线索框。
			$("#updateBtn").on("click",function () {
				var $xz = $("input[name=xz]:checked");
				if ($xz.length <= 0 ){
					alert("请选择你需要修改的线索！")
				}else if ($xz.length > 1){
					alert("请选择一条线索，不要多选！")
				}else {
					var id = $xz.val()
					$.ajax({
						url:"workbench/clue/modifyWin.do",
						type:"post",
						data:{"id":id},
						dataType:"json",
						success:function (data) {

							var html = "";
							$.each(data.userInfo,function (k,v) {
								html += "<option value='"+v.id+"'>"+v.name+"</opcion>"
							})

							$("#edit-owner").html(html);

							$("#edit-id").val(data.clueInfo.id);
							$("#edit-owner").val(data.clueInfo.owner);
							$("#edit-company").val(data.clueInfo.company);
							$("#edit-appellation").val(data.clueInfo.appellation);
							$("#edit-fullname").val(data.clueInfo.fullname);
							$("#edit-job").val(data.clueInfo.job);
							$("#edit-email").val(data.clueInfo.email);
							$("#edit-phone").val(data.clueInfo.phone);
							$("#edit-website").val(data.clueInfo.website);
							$("#edit-mphone").val(data.clueInfo.mphone);
							$("#edit-state").val(data.clueInfo.state);
							$("#edit-source").val(data.clueInfo.source);
							$("#edit-description").val(data.clueInfo.description);
							$("#edit-contactSummary").val(data.clueInfo.contactSummary);
							$("#edit-nextContactTime").val(data.clueInfo.nextContactTime);
							$("#edit-address").val(data.clueInfo.address);

						}
					})
					$("#editClueModal").modal("show");
				}
			})

            //为更新按钮绑定事件，执行更新线索请求。
            $("#editBtn").on("click",function () {
				$.ajax({
					url:"workbench/clue/updateClue.do",
					tpye:"post",
					data:{
						"id":$.trim($("#edit-id").val()),
						"owner":$.trim($("#edit-owner").val()),
						"company":$.trim($("#edit-company").val()),
						"appellation":$.trim($("#edit-appellation").val()),
						"fullname":$.trim($("#edit-fullname").val()),
						"job":$.trim($("#edit-job").val()),
						"email":$.trim($("#edit-email").val()),
						"phone":$.trim($("#edit-phone").val()),
						"website":$.trim($("#edit-website").val()),
						"mphone":$.trim($("#edit-mphone").val()),
						"state":$.trim($("#edit-state").val()),
						"source":$.trim($("#edit-source").val()),
						"description":$.trim($("#edit-description").val()),
						"contactSummary":$.trim($("#edit-contactSummary").val()),
						"nextContactTime":$.trim($("#edit-nextContactTime").val()),
						"address":$.trim($("#edit-address").val())
					},
					dataType:"json",
					success:function(data){
						if (data.success){
							alert("修改线索成功~")

							pageList($("#activityPage").bs_pagination('getOption', 'currentPage'),
									$("#activityPage").bs_pagination('getOption', 'rowsPerPage'));

							$("#editClueModal").modal("hide")
						}else {
							alert("修改线索失败！")
						}
					}
				})
            })

			//线索列表多条选择功能，
			$("#allSelect").on("click",function () {
				$("input[name=xz]").prop("checked",this.checked)
			})

			//线索列表多条或单挑选择功能，
			$("#clue-info").on("click",function () {
				$("#allSelect").prop("checked",$("input[name=xz]").length == $("input[name=xz]:checked").length)
			})

			//为删除按钮绑定事件，执行删除线索请求。
			$("#deleteBtn").on("click",function () {
				var $xz = $("input[name=xz]:checked");
				if ($xz.length <= 0 ){
					alert("请选择您要删除的数据!")
				}else if (confirm("您确定要删除此线索吗？")){
					var param = "";
					for (var i=0;i<$xz.length;i++){
						param += "id="+$($xz[i]).val();
						if (i<$xz.length-1){
							param += "&"
						}
					}
					$.ajax({
						url:"workbench/clue/deleteClue.do",
						type:"post",
						data:param,
						dataType:"json",
						success:function (data) {
							if (data.success){
								pageList(1,$("#activityPage").bs_pagination('getOption', 'rowsPerPage'));
							}else {
								alert(data.message);
							}
						}

					});
				}
			})

		});

		//刷新线索信息列表请求，
		function pageList(pageNo,pageSize) {

			$("#allSelect").prop("checked",false);

			$("#search-fullname").val($.trim($("#hidden-fullname").val()));
			$("#search-company").val($.trim($("#hidden-company").val()));
			$("#search-phone").val($.trim($("#hidden-phone").val()));
			$("#search-source").val($.trim($("#hidden-source").val()));
			$("#search-owner").val($.trim($("#hidden-owner").val()));
			$("#search-mphone").val($.trim($("#hidden-mphone").val()));
			$("#search-state").val($.trim($("#hidden-state").val()));

			$.ajax({
				url:"workbench/clue/getAllClueInfo.do",
				type:"get",
				data: {
					"pageNo":pageNo,
					"pageSize":pageSize,
					"fullname":$("#search-fullname").val(),
					"company":$("#search-company").val(),
					"phone":$("#search-phone").val(),
					"source":$("#search-source").val(),
					"owner":$("#search-owner").val(),
					"mphone":$("#search-mphone").val(),
					"state":$("#search-state").val()
				},
				dataType:"json",
				success:function (data) {
					var html = "";
					$.each(data.dataList,function (k,v) {
						html += '<tr class="active">'
						html += '<td><input type="checkbox" name="xz" value="'+v.id+'"/></td>'
						html += '<td><a style="text-decoration: none; cursor: pointer;" onclick="window.location.href=\'workbench/clue/detail.do?id='+v.id+'\';">'+v.fullname+'</a></td>'
						html += '<td>'+v.company+'</td>'
						html += '<td>'+v.phone+'</td>'
						html += '<td>'+v.mphone+'</td>'
						html += '<td>'+v.source+'</td>'
						html += '<td>'+v.owner+'</td>'
						html += '<td>'+v.state+'</td>'
						html += '</tr>'
					})

					$("#clue-info").html(html);

					var totalPages = data.total % pageSize==0 ? data.total/pageSize:parseInt(data.total/pageSize)+1;

					$("#activityPage").bs_pagination({
						currentPage: pageNo,	//页码
						rowsPerPage:pageSize,	//每页显示的记录条数
						maxRowsPerPage: 20,	//每页最多显示的记录条数
						totalPages: totalPages,	//总页数
						totalRows: data.total,	//总记录条数

						visiblePageLinks: 4,	//显示几个卡片

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
	<%-- 隐藏域 --%>
	<input type="hidden" id="hidden-fullname"/>
	<input type="hidden" id="hidden-company"/>
	<input type="hidden" id="hidden-phone"/>
	<input type="hidden" id="hidden-source"/>
	<input type="hidden" id="hidden-owner"/>
	<input type="hidden" id="hidden-mphone"/>
	<input type="hidden" id="hidden-state"/>

	<!-- 创建线索的模态窗口 -->
	<div class="modal fade" id="createClueModal" role="dialog">
		<div class="modal-dialog" role="document" style="width: 90%;">
			<div class="modal-content">
				<div class="modal-header">
					<button type="button" class="close" data-dismiss="modal">
						<span aria-hidden="true">×</span>
					</button>
					<h4 class="modal-title" id="myModalLabel">创建线索</h4>
				</div>
				<div class="modal-body">
					<form class="form-horizontal" role="form" id="create-AllInfo">
					
						<div class="form-group">
							<label for="create-owner" class="col-sm-2 control-label">所有者<span style="font-size: 15px; color: red;">*</span></label>
							<div class="col-sm-10" style="width: 300px;">
								<select class="form-control" id="create-owner">
								</select>
							</div>
							<label for="create-company" class="col-sm-2 control-label">公司<span style="font-size: 15px; color: red;">*</span></label>
							<div class="col-sm-10" style="width: 300px;">
								<input type="text" class="form-control" id="create-company">
							</div>
						</div>
						
						<div class="form-group">
							<label for="create-call" class="col-sm-2 control-label">称呼</label>
							<div class="col-sm-10" style="width: 300px;">
								<select class="form-control" id="create-call">
								 	<option></option>
									<c:forEach items="${appellation}" var="a">
										<option value="${a.value}">${a.text}</option>
									</c:forEach>
								</select>
							</div>
							<label for="create-surname" class="col-sm-2 control-label">姓名<span style="font-size: 15px; color: red;">*</span></label>
							<div class="col-sm-10" style="width: 300px;">
								<input type="text" class="form-control" id="create-name">
							</div>
						</div>
						
						<div class="form-group">
							<label for="create-job" class="col-sm-2 control-label">职位</label>
							<div class="col-sm-10" style="width: 300px;">
								<input type="text" class="form-control" id="create-job">
							</div>
							<label for="create-email" class="col-sm-2 control-label">邮箱</label>
							<div class="col-sm-10" style="width: 300px;">
								<input type="text" class="form-control" id="create-email">
							</div>
						</div>
						
						<div class="form-group">
							<label for="create-phone" class="col-sm-2 control-label">公司座机</label>
							<div class="col-sm-10" style="width: 300px;">
								<input type="text" class="form-control" id="create-phone">
							</div>
							<label for="create-website" class="col-sm-2 control-label">公司网站</label>
							<div class="col-sm-10" style="width: 300px;">
								<input type="text" class="form-control" id="create-website">
							</div>
						</div>
						
						<div class="form-group">
							<label for="create-mphone" class="col-sm-2 control-label">手机</label>
							<div class="col-sm-10" style="width: 300px;">
								<input type="text" class="form-control" id="create-mphone">
							</div>
							<label for="create-state" class="col-sm-2 control-label">线索状态</label>
							<div class="col-sm-10" style="width: 300px;">
								<select class="form-control" id="create-state">
								 	<option></option>
									<c:forEach items="${clueState}" var="b">
										<option value="${b.value}">${b.text}</option>
									</c:forEach>
								</select>
							</div>
						</div>
						
						<div class="form-group">
							<label for="create-source" class="col-sm-2 control-label">线索来源</label>
							<div class="col-sm-10" style="width: 300px;">
								<select class="form-control" id="create-source">
								 	<option></option>
									<c:forEach items="${source}" var="c">
										<option value="${c.value}">${c.text}</option>
									</c:forEach>
								</select>
							</div>
						</div>
						

						<div class="form-group">
							<label for="create-describe" class="col-sm-2 control-label">线索描述</label>
							<div class="col-sm-10" style="width: 81%;">
								<textarea class="form-control" rows="3" id="create-describe"></textarea>
							</div>
						</div>
						
						<div style="height: 1px; width: 102%; background-color: #D5D5D5; left: -13px; position: relative;"></div>
						
						<div style="position: relative;top: 15px;">
							<div class="form-group">
								<label for="create-contactSummary" class="col-sm-2 control-label">联系纪要</label>
								<div class="col-sm-10" style="width: 81%;">
									<textarea class="form-control" rows="3" id="create-contactSummary"></textarea>
								</div>
							</div>
							<div class="form-group">
								<label for="create-nextContactTime" class="col-sm-2 control-label">下次联系时间</label>
								<div class="col-sm-10" style="width: 300px;">
									<input type="text" class="form-control time" id="create-nextContactTime" readonly>
								</div>
							</div>
						</div>
						
						<div style="height: 1px; width: 102%; background-color: #D5D5D5; left: -13px; position: relative; top : 10px;"></div>
						
						<div style="position: relative;top: 20px;">
							<div class="form-group">
                                <label for="create-address" class="col-sm-2 control-label">详细地址</label>
                                <div class="col-sm-10" style="width: 81%;">
                                    <textarea class="form-control" rows="1" id="create-address"></textarea>
                                </div>
							</div>
						</div>
					</form>
					
				</div>
				<div class="modal-footer">
					<button type="button" class="btn btn-default" data-dismiss="modal">关闭</button>
					<button type="button" class="btn btn-primary" id="saveClue">保存</button>
				</div>
			</div>
		</div>
	</div>


	<!-- 修改线索的模态窗口 -->
	<div class="modal fade" id="editClueModal" role="dialog">
		<div class="modal-dialog" role="document" style="width: 90%;">
			<div class="modal-content">
				<div class="modal-header">
					<button type="button" class="close" data-dismiss="modal">
						<span aria-hidden="true">×</span>
					</button>
					<h4 class="modal-title">修改线索</h4>
				</div>
				<div class="modal-body">
					<form class="form-horizontal" role="form">
						<input type="hidden" id="edit-id"/>

						<div class="form-group">
							<label for="edit-clueOwner" class="col-sm-2 control-label">所有者<span style="font-size: 15px; color: red;">*</span></label>
							<div class="col-sm-10" style="width: 300px;">
								<select class="form-control" id="edit-owner">
								</select>
							</div>
							<label for="edit-company" class="col-sm-2 control-label">公司<span style="font-size: 15px; color: red;">*</span></label>
							<div class="col-sm-10" style="width: 300px;">
								<input type="text" class="form-control" id="edit-company">
							</div>
						</div>
						
						<div class="form-group">
							<label for="edit-call" class="col-sm-2 control-label">称呼</label>
							<div class="col-sm-10" style="width: 300px;">
								<select class="form-control" id="edit-appellation">
								 	<option></option>
									<c:forEach items="${appellation}" var="app">
										<option value="${app.value}">${app.text}</option>
									</c:forEach>
								</select>
							</div>
							<label for="edit-surname" class="col-sm-2 control-label">姓名<span style="font-size: 15px; color: red;">*</span></label>
							<div class="col-sm-10" style="width: 300px;">
								<input type="text" class="form-control" id="edit-fullname">
							</div>
						</div>
						
						<div class="form-group">
							<label for="edit-job" class="col-sm-2 control-label">职位</label>
							<div class="col-sm-10" style="width: 300px;">
								<input type="text" class="form-control" id="edit-job" value="CTO">
							</div>
							<label for="edit-email" class="col-sm-2 control-label">邮箱</label>
							<div class="col-sm-10" style="width: 300px;">
								<input type="text" class="form-control" id="edit-email">
							</div>
						</div>
						
						<div class="form-group">
							<label for="edit-phone" class="col-sm-2 control-label">公司座机</label>
							<div class="col-sm-10" style="width: 300px;">
								<input type="text" class="form-control" id="edit-phone">
							</div>
							<label for="edit-website" class="col-sm-2 control-label">公司网站</label>
							<div class="col-sm-10" style="width: 300px;">
								<input type="text" class="form-control" id="edit-website">
							</div>
						</div>
						
						<div class="form-group">
							<label for="edit-mphone" class="col-sm-2 control-label">手机</label>
							<div class="col-sm-10" style="width: 300px;">
								<input type="text" class="form-control" id="edit-mphone" value="12345678901">
							</div>
							<label for="edit-status" class="col-sm-2 control-label">线索状态</label>
							<div class="col-sm-10" style="width: 300px;">
								<select class="form-control" id="edit-state">
									<option></option>
									<c:forEach items="${clueState}" var="cstate">
										<option value="${cstate.value}">${cstate.text}</option>
									</c:forEach>
								</select>
							</div>
						</div>
						
						<div class="form-group">
							<label for="edit-source" class="col-sm-2 control-label">线索来源</label>
							<div class="col-sm-10" style="width: 300px;">
								<select class="form-control" id="edit-source">
									<option></option>
									<c:forEach items="${source}" var="s">
										<option value="${s.value}">${s.text}</option>
									</c:forEach>
								</select>
							</div>
						</div>
						
						<div class="form-group">
							<label for="edit-description" class="col-sm-2 control-label">描述</label>
							<div class="col-sm-10" style="width: 81%;">
								<textarea class="form-control" rows="3" id="edit-description"></textarea>
							</div>
						</div>
						
						<div style="height: 1px; width: 102%; background-color: #D5D5D5; left: -13px; position: relative;"></div>
						
						<div style="position: relative;top: 15px;">
							<div class="form-group">
								<label for="edit-contactSummary" class="col-sm-2 control-label">联系纪要</label>
								<div class="col-sm-10" style="width: 81%;">
									<textarea class="form-control" rows="3" id="edit-contactSummary"></textarea>
								</div>
							</div>
							<div class="form-group">
								<label for="edit-nextContactTime" class="col-sm-2 control-label">下次联系时间</label>
								<div class="col-sm-10" style="width: 300px;">
									<input type="text" class="form-control time" id="edit-nextContactTime" readonly>
								</div>
							</div>
						</div>
						
						<div style="height: 1px; width: 102%; background-color: #D5D5D5; left: -13px; position: relative; top : 10px;"></div>

                        <div style="position: relative;top: 20px;">
                            <div class="form-group">
                                <label for="edit-address" class="col-sm-2 control-label">详细地址</label>
                                <div class="col-sm-10" style="width: 81%;">
                                    <textarea class="form-control" rows="1" id="edit-address"></textarea>
                                </div>
                            </div>
                        </div>
					</form>
					
				</div>
				<div class="modal-footer">
					<button type="button" class="btn btn-default" data-dismiss="modal">关闭</button>
					<button type="button" class="btn btn-primary" id="editBtn">更新</button>
				</div>
			</div>
		</div>
	</div>


	<div>
		<div style="position: relative; left: 10px; top: -10px;">
			<div class="page-header">
				<h3>线索列表</h3>
			</div>
		</div>
	</div>


	<div style="position: relative; top: -20px; left: 0px; width: 100%; height: 100%;">
		<div style="width: 100%; position: absolute;top: 5px; left: 10px;">
		
			<div class="btn-toolbar" role="toolbar" style="height: 90px;">
				<form class="form-inline" role="form" style="position: relative;top: 8%; left: 5px;">

					<%--名称--%>
					<div class="form-group">
						<div class="input-group">
							<div class="input-group-addon">名称</div>
							<input class="form-control" type="text" id="search-fullname">
						</div>
					</div>

					<%--公司--%>
					<div class="form-group">
						<div class="input-group">
							<div class="input-group-addon">公司</div>
							<input class="form-control" type="text" id="search-company">
						</div>
					</div>

					<%--公司座机--%>
				 	<div class="form-group">
						<div class="input-group">
				 			<div class="input-group-addon">公司座机</div>
				 			<input class="form-control" type="text" id="search-phone">
						</div>
				 	</div>

					<%--线索来源--%>
					<div class="form-group">
						<div class="input-group">
				    		<div class="input-group-addon">线索来源</div>
							<select class="form-control" id="search-source">
							 	<option></option>
								<c:forEach items="${source}" var="se">
									<option value="${se.value}">${se.text}</option>
								</c:forEach>
							</select>
						</div>
					</div>

					<%--所有者--%>
					<div class="form-group">
				  		<div class="input-group">
				     		<div class="input-group-addon">所有者</div>
				    		<input class="form-control" type="text" id="search-owner">
				  		</div>
					</div><br>

					<%--手机--%>
					<div class="form-group">
						<div class="input-group">
							<div class="input-group-addon">手机</div>
							<input class="form-control" type="text" id="search-mphone">
						</div>
					</div>

					<%--线索状态--%>
					<div class="form-group">
					 	<div class="input-group">
					 		<div class="input-group-addon">线索状态</div>
							<select class="form-control" id="search-state">
								<option></option>
								<c:forEach items="${clueState}" var="cs">
									<option value="${cs.value}">${cs.text}</option>
								</c:forEach>
							  </select>
					 	</div>
					</div>

					<%--查询按钮--%>
					<button type="button" class="btn btn-default" id="selectBtn">查询</button>

				</form>
			</div>

			<div class="btn-toolbar" role="toolbar" style="background-color: #F7F7F7; height: 50px; position: relative;top: 5px;">
				<div class="btn-group" style="position: relative; top: 18%;">
				  <button type="button" class="btn btn-primary" id="createBtn"><span class="glyphicon glyphicon-plus"></span> 创建</button>
				  <button type="button" class="btn btn-default" id="updateBtn"><span class="glyphicon glyphicon-pencil"></span> 修改</button>
				  <button type="button" class="btn btn-danger" id="deleteBtn"><span class="glyphicon glyphicon-minus"></span> 删除</button>
				</div>
			</div>


			<div style="position: relative;top: 10px;">
				<table class="table table-hover">
					<thead>
						<tr style="color: #B3B3B3;">
							<td><input type="checkbox" id="allSelect"/></td>
							<td>名称</td>
							<td>公司</td>
							<td>公司座机</td>
							<td>手机</td>
							<td>线索来源</td>
							<td>所有者</td>
							<td>线索状态</td>
						</tr>
					</thead>
					<tbody id="clue-info">
						<%--<tr>
							<td><input type="checkbox" /></td>
							<td><a style="text-decoration: none; cursor: pointer;" onclick="window.location.href='detail.jsp';">李四先生</a></td>
							<td>动力节点</td>
							<td>010-84846003</td>
							<td>12345678901</td>
							<td>广告</td>
							<td>zhangsan</td>
							<td>已联系</td>
						</tr>
                        <tr class="active">
                            <td><input type="checkbox" /></td>
                            <td><a style="text-decoration: none; cursor: pointer;" onclick="window.location.href='detail.jsp';">李四先生</a></td>
                            <td>动力节点</td>
                            <td>010-84846003</td>
                            <td>12345678901</td>
                            <td>广告</td>
                            <td>zhangsan</td>
                            <td>已联系</td>
                        </tr>--%>
					</tbody>
				</table>
			</div>

			<div style="height: 50px; position: relative;top: 50px;">

				<div id="activityPage">

				</div>
			</div>

		</div>
	</div>

</body>
</html>