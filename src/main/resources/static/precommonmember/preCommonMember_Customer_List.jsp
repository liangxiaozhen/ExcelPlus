<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>
<!DOCTYPE html>
<html lang="zh-CN">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<!-- 注意文件的引入顺序 -->
<link
	href="<%=basePath%>/static/plugin/bootstrap/3.3.5/css/bootstrap.min.css"
	rel="stylesheet">
<script
	src="<%=basePath%>/static/plugin/jquery/1.11.3/jquery-1.11.3.min.js"></script>
<script
	src="<%=basePath%>/static/plugin/bootstrap/3.3.5/js/bootstrap.min.js"></script>
<script
	src="<%=basePath%>/static/plugin/bootstrap/bootstrapvalidator/js/bootstrapValidator.min.js"></script>
<script src="<%=basePath%>/static/js/util.js"></script>
<script src="<%=basePath%>/static/js/validate.js"></script>
<script type="text/javascript" src="<%=basePath%>/static/js/common.js"></script>
<!-- 表格样式 -->
<style>
table tr td {
	text-align: center;
	vertical-align: middle !important;
}

.text-center2 td {
	border: 1px solid #666;
}
</style>
<script type="text/javascript">

//详情页面跳转
function deatilUI2(id){
	var  action =  "/admin/preCommonMember/customerList/deail";
	var params = {
			"id":id
	}
	$.post(action, params, function(data){
		$("#detail-modal-body").html(data);
	});
}

//页面查询条件数据回显
$(function(){
	$(".remark-p").each(function() {
		var num = $(this).html();
		if (num.length > 5) {
			$(this).html(num.substr(0, 5) + "...");
		}
	});
	$("#username_select").val("${preCommonMember.username}");
	
});

function queryRecommend(recommend){
	$("#referrerusername_select").val(recommend);
	$("#recommend_select").val(recommend);
	$("#form-select").submit();
}

//重置包括隐藏域
function resetting(){
	$("#username_select").val("");
}
</script>

<title>用户信息列表</title>
</head>
<body style="font-size: 12px; font-family: 微软雅黑">

	<div class="container" style="width: 80%;">
		<div style="float: left;">
			<form method="post" action="customerList" id="form-select" class="form-inline">
				<div style="margin-top: 18px;margin-bottom: 10px">
					<input type="hidden" id="pageNum" name="pageNum" /> 
					<input type="hidden" id="pageSize" name="pageSize" />
					<div class="form-group">
					用户名：<input class="form-control" type="text" id="username_select"
						name="username" style="text-align: center; line-height: 18px;"
						placeholder="填写用户名">
					</div> 
					<div class="form-group">
						<button class="btn btn-default" style="width: 100px;" type="submit">查询</button>
						<button type="reset" class="btn btn-default" style="width: 100px;" onclick="resetting();">重置</button>
					</div>	
				</div>

			</form>
		</div>
		<div class="row clearfix" style="margin-top: 10px">
			<div class="col-md-12 column">

				<table class="table table-hover" id="userRechargeList_table">
					<thead>
						<tr style="background-color: #CCCCCC;" class="text-center2">
							<td style="display: none">ID</td>
							<td>序号</td>
							<!-- <td>姓名</td> -->
							<td>用户名</td>
							<!-- <td>手机号</td> -->
							<td>注册时间</td>
							<!-- <td>证件类型</td>
							<td>证件号码</td> -->
							<td>详情</td>
						</tr>
					</thead>
					<tbody>
						<!-- 这里面${userRecharge.id }是点的model里面的属性 -->
						<c:forEach items="${pagehelper.list}" var="precommonmember"
							varStatus="status">

							<tr id="userRecharge_tr_${precommonmember.uid }"
								class="text-center2">
								<td>${status.count}</td>
								<%-- <td>${precommonmember.realname}</td> --%>
								<td>${precommonmember.username}</td>
								<%-- <td>${precommonmember.mobile}</td> --%>
								<td>
									<c:if test="${!empty precommonmember.regdate}">${sf.format(precommonmember.regdate)}</c:if>
									<c:if test="${empty precommonmember.regdate}">暂时没有时间</c:if>
								</td>
								
								<%-- <td>
									<c:if test="${precommonmember.certtype eq '1'}">身份证</c:if>
									<c:if test="${precommonmember.certtype eq '2'}">个人护照</c:if> 
								</td> --%>

							<%-- 	<td>${precommonmember.certno}</td> --%>
								<td><button class="btn" data-backdrop="static"
										data-toggle="modal" data-dismiss="modal"
										data-target="#detailsModal"
										onclick="deatilUI2('${precommonmember.uid}')">查看</button></td>
							</tr>

						</c:forEach>
					</tbody>
				</table>
				<div id="page_div">
					<%@ include file="./../../common/pagehelper.jsp"%>
				</div>
			</div>
		</div>
	</div>
	<!-- 引入公共的模态框 -->
	<%@ include file="./../../common/modal.jsp"%>
</body>
</html>
