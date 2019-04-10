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
<%-- <script type="text/javascript" src="<%=basePath%>/layer/layer.js"></script> --%>
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
function deatilUI(id){
	var  action =  "/admin/preCommonMember/detail/"+id;
	$.post(action, function(data){
		$("#detail-modal-body").html(data);
	});
}

/* //权限修改页面跳转
function upateUI(id){
	var  action =  "/admin/preCommonMember/userPermissionsupdateView/"+id;
	$.post(action, function(data){
		$("#update-modal-body").html(data);
	});
} */



//页面查询条件数据回显
$(function(){
	$(".remark-p").each(function() {
		var num = $(this).html();
		if (num.length > 5) {
			$(this).html(num.substr(0, 5) + "...");
		}
	});
	$("#mobile_select").val("${preCommonMember.mobile}");
	$("#username_select").val("${preCommonMember.username}");
	$("#realname_select").val("${preCommonMember.realname}");
	$("#certno_select").val("${preCommonMember.certno}");
	$("#isadmin_select").val("${preCommonMember.isadmin}");
	$("#regip_select").val("${preCommonMember.regip}");
	$("#lastloginip_select").val("${preCommonMember.lastloginip}");
	$("#isadmin_select").val("${preCommonMember.isadmin}");
	
});



function queryRecommend(recommend){
	$("#referrerusername_select").val(recommend);
	$("#recommend_select").val(recommend);
	$("#form-select").submit();
}

//重置包括隐藏域
function resetting(){
	$("#isadmin_select").val("");
	$("#mobile_select").val("");
	$("#username_select").val("");
	$("#realname_select").val("");
	$("#certno_select").val("");
}
</script>

<title>用户信息列表</title>
</head>
<body style="font-size: 12px; font-family: 微软雅黑">

	<div class="container" style="width: 80%;">
		<div style="float: left;">
			<form method="post" action="list" id="form-select" class="form-inline">
				<div style="margin-top: 18px;margin-bottom: 10px">
					<input type="hidden" id="pageNum" name="pageNum" /> 
					<input type="hidden" id="pageSize" name="pageSize" />
					<div class="form-group">
					手机号：<input class="form-control" type="text" id="mobile_select"
						name="mobile" style="text-align: center; line-height: 18px;"
						placeholder="填写手机号">
					</div> 
					<div class="form-group">
					用户名：<input class="form-control" type="text" id="username_select"
						name="username" style="text-align: center; line-height: 18px;"
						placeholder="填写用户名">
					</div> 
					<div class="form-group">
					真实姓名：<input class="form-control" type="text" id="realname_select"
						name="realname" style="text-align: center; line-height: 18px;"
						placeholder="填写姓名">
					</div> 
					<div class="form-group">
					身份证号码：<input class="form-control" type="text" id="certno_select"
						name="certno" style="text-align: center; line-height: 18px;"
						placeholder="填写身份证">
					</div> 
					<br/>
					<div class="form-group" style="margin-top: 10px">
					注册IP：<input class="form-control" type="text" id="regip_select"
						name="regip" style="text-align: center; line-height: 18px;"
						placeholder="填写注册IP">
					</div> 
					<div class="form-group"  style="margin-top: 10px">
					登录IP：<input class="form-control" type="text" id="lastloginip_select"
						name="lastloginip" style="text-align: center; line-height: 18px;"
						placeholder="填写登录IP">
					</div> 
					<div class="form-group"  style="margin-top: 10px">
                        <label>是否管理员:</label>
                        <select class="form-control" name="isadmin" id="isadmin_select">
                            <option value="">全部</option>
                            <option value="1">是</option>
                            <option value="0">否</option>
                        </select>&nbsp;&nbsp;
                    </div>
				
					<div class="form-group"  style="margin-top: 10px">
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
							<td>baseid</td>
							<!-- <td>姓名</td> -->
							<td>用户名</td>
							<!-- <td>手机号</td> -->
							<td>注册时间</td>
							<td>实名状态</td>
						<!-- 	<td>注册来源</td> -->
							<!-- <td>证件类型</td>
							<td>证件号码</td> -->
						<!-- 	<td>推荐人(用户名-姓名)</td> -->
							<!-- <td>备注</td> -->
							<!-- <td>操作</td> -->
							<!-- <td>能否发帖</td>
							<td>能否删帖</td>
							<td>能否改帖</td> -->
							<!-- <td>权限设置</td> -->
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
								<td>${precommonmember.uid}</td>
							<%-- 	<td>${precommonmember.realname}</td> --%>
								<td>${precommonmember.username}</td>
								
							<%-- 	<td>${precommonmember.mobile}</td> --%>
								<td>
									<c:if test="${!empty precommonmember.regdate}">${sf.format(precommonmember.regdate)}</c:if>
									<c:if test="${empty precommonmember.regdate}">暂时没有时间</c:if>
								</td>
								
								<td>
									<c:if test="${!empty precommonmember.realstatus}">
										<c:if test="${precommonmember.realstatus eq '1'}">已认证</c:if>
										<c:if test="${precommonmember.realstatus eq '0'}">未认证</c:if> 
									</c:if>
									<c:if test="${empty precommonmember.realstatus}">
										未认证
									</c:if>
									
								</td>
								
							<%-- 	<td>
									 <c:if test="${precommonmember.isuse  eq 1}">
		                                <span style="color:blue;">开启</span>&nbsp;&nbsp;
		                                <span><a style="cursor:pointer;color:black" onclick="startOrStop('${precommonmember.id}','0',this)">关闭</a></span>
		                            </c:if>
		                            <c:if test="${precommonmember.isuse eq 0 }">
		                                <span><a style="cursor:pointer;color: black;" onclick="startOrStop('${precommonmember.id}','1',this)">开启</a></span>&nbsp;&nbsp;
		                                <span style="color: red;">关闭</span>
		                            </c:if>
								
								</td> --%>
								<%-- <td>${precommonmember.source}</td> --%>
								
								<%-- <td>
									<c:if test="${precommonmember.certtype eq '1'}">身份证</c:if>
									<c:if test="${precommonmember.certtype eq '2'}">个人护照</c:if> 
								</td>
 --%>
								<%-- <td>${precommonmember.certno}</td> --%>
								<%-- <td>
									<c:if test="${empty precommonmember.recommend}">无</c:if>
									<c:if test="${!empty precommonmember.recommend}">
										<a style="cursor:pointer;color: blue"  
										onclick="queryRecommend('${precommonmember.recommend}')">
											${precommonmember.referrerusername}--${precommonmember.referrerrealname}
										</a>
									</c:if>
								</td> --%>
								<%-- <td class="remark-p" title="${precommonmember.remark }">${precommonmember.remark }</td> --%>
								<%-- <td>
									<button class="btn" data-backdrop="static"
										data-toggle="modal" data-dismiss="modal"
										data-target="#updateModal"
										onclick="updateUI('${precommonmember.uid}')">修改</button>
								</td> --%>
								
							<%-- 	<td><button class="btn" data-backdrop="static"
										data-toggle="modal" data-dismiss="modal"
										data-target="#updateModal"
										onclick="upateUI('${precommonmember.uid}')">设置</button>
								</td> --%>
								<td><button class="btn" data-backdrop="static"
										data-toggle="modal" data-dismiss="modal"
										data-target="#detailsModal"
										onclick="deatilUI('${precommonmember.uid}')">查看</button>
								</td>
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
