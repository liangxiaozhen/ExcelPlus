<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>

<!DOCTYPE HTML>
<html>
<head>
<base href="<%=basePath%>">

<meta charset="UTF-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta http-equiv="pragma" content="no-cache">
<meta http-equiv="cache-control" content="no-cache">
<meta http-equiv="expires" content="0">
<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
<meta name="viewport"
	content="width=device-width,user-scalable=no,initial-scale=1">
<meta http-equiv="description" content="This is my page">

<!-- Latest compiled and minified CSS -->
<!-- link font icon -->
<!-- <link rel="stylesheet" href="awesome/css/font-awesome.min.css"> -->

<link rel="stylesheet"
	href="${pageContext.request.contextPath}/bootstrap/3.3.5/css/bootstrap.min.css">
<link rel="stylesheet"
	href="${pageContext.request.contextPath}/bootstrap/3.3.5/css/bootstrap-theme.min.css">
<script type="text/javascript">var basePath ="${pageContext.request.contextPath}"</script>
<script type="text/javascript" charset="utf-8"
	src="${pageContext.request.contextPath}/jquery/1.11.3/jquery-1.11.3.min.js"></script>
<script type="text/javascript" charset="utf-8"
	src="${pageContext.request.contextPath}/bootstrap/3.3.5/js/bootstrap.min.js"></script>
<script type="text/javascript" charset="utf-8"
	src="${pageContext.request.contextPath}/js/validate/jquery.validate.js"></script>
<script type="text/javascript" charset="utf-8"
	src="${pageContext.request.contextPath}/js/validate/jquery.metadata.js"></script>
<script type="text/javascript" charset="utf-8"
	src="<%=basePath%>/js/bankcard/bindBankCard.js"></script>
<style type="text/css">
body {font-family: "微软雅黑";font-size: 13px;}
label {font-weight: normal;}
td {border: 1px solid #666;vertical-align: middle !important;}
label {font-weight: normal;}
</style>
</head>
<body>
	<div class="container" style="width: 90%">
		<div class="row clearfix">
			<div class="col-md-12 column"
				style="border-style: none; border-width: 5px; border-color: Black; background-color: none;">
				<div id="area_div" style="margin-top: 50px">
					<form role="form" method="post" id="bankform"
						action="${pageContext.request.contextPath}/userbankcard/queryBankInfoList.action">
						<input type="hidden" id="pageNum" name="pageNum" value="" /> <input
							type="hidden" id="pageSize" name="pageSize" value="" />
						<div class="col-md-4 input-group">
							<span class="input-group-addon"><span
								class="glyphicon glyphicon-search"></span></span> <input type="text"
								name="username" id="username" class="form-control"
								value="${username}" placeholder="请输入姓名搜索..." /> <span
								class="input-group-addon"><button type="submit"
									style="border-style: none">搜索</button></span>
							<div class="col-md-2 col-md-offset-3">
								<button type="button" class="btn" onclick="CardList.reset()">重置</button>
							</div>
						</div>
					</form>
					<!-- 					<div align="right"> -->
					<!-- 						<input type="button" class="btn" value="新增" -->
					<!-- 							onclick="CardList.insert_bank_info()" /> -->
					<!-- 					</div> -->
					<div class="table-responsive">
						<table class="table table-condensed text-center table-hover"
							style="width: 100%">
							<caption>
								<strong>用户银行卡表</strong>
							</caption>
							<thead>
								<tr style="background: #ccc;">
									<td>序号</td>
									<td>姓名</td>
									<td>银行</td>
									<td>卡类型</td>
									<td>绑定方式</td>
									<td>是否快捷绑卡</td>
									<td>是否默认取现卡</td>
									<td>绑定状态</td>
									<td>备注</td>
									<td>操作</td>
								</tr>
							</thead>
							<tbody>
								<c:if test="${!empty pagehelper.list}">
									<c:forEach items="${pagehelper.list}" var="bank" varStatus="varStatus">
										<tr id="bank_tr_${bank.id }">
											<td>${varStatus.index+1}</td>
											<td>${bank.username}</td>
											<td>${bank.bankname}</td>
											<td><c:if test="${!empty cardtypemap}">
													<c:forEach items="${cardtypemap}" var="ctype">
														<c:choose>
															<c:when test="${bank.cardtype==ctype.key}">
														${ctype.value}
														</c:when>
														</c:choose>
													</c:forEach>
												</c:if></td>
											<td><c:if test="${!empty bindmodemap}">
													<c:forEach items="${bindmodemap}" var="bmode">
														<c:choose>
															<c:when test="${bank.bindmode==bmode.key}">
														${bmode.value}
														</c:when>
														</c:choose>
													</c:forEach>
												</c:if></td>
											<td><c:if test="${!empty fastbindmap}">
													<c:forEach items="${fastbindmap}" var="fast">
														<c:choose>
															<c:when test="${bank.isfastbindcard==fast.key}">
														${fast.value}
														</c:when>
														</c:choose>
													</c:forEach>
												</c:if></td>
											<td><c:if test="${!empty defaultcardmap}">
													<c:forEach items="${defaultcardmap}" var="defaultcard">
														<c:choose>
															<c:when test="${bank.isdefaultcard==defaultcard.key}">
														${defaultcard.value}
														</c:when>
														</c:choose>
													</c:forEach>
												</c:if></td>
											<td><c:if test="${!empty bindstatusmap}">
													<c:forEach items="${bindstatusmap}" var="status">
														<c:choose>
															<c:when test="${bank.bindstatus==status.key}">
														${status.value}
														</c:when>
														</c:choose>
													</c:forEach>
												</c:if></td>
											<td><p id="remark" data-toggle='tooltip'
													title="<h5>${bank.remark}</h5>" limit="5">${bank.remark}</p></td>
											<td><div class="btn-group" role="group" aria-label="...">
													<button class="btn"
														onclick="CardList.queryDetail('${bank.id}')">查看详情</button>
													<!-- 													<button class="btn" -->
													<%-- 														onclick="CardList.editbankinfo('${bank.id}')">修改</button> --%>
													<%-- 													<c:if test="${bank.isdefaultcard == 0 && bank.isfastbindcard == 1}"> --%>
													<!-- 													<button class="btn" -->
													<%-- 														onclick="CardList.deletebankinfo('${bank.id}')">删除</button> --%>
													<%-- 													</c:if> --%>
												</div></td>
										</tr>
									</c:forEach>
								</c:if>
								<c:if test="${empty pagehelper.list}">
									<tr>
										<td colspan="100">没有相关数据</td>
									</tr>
								</c:if>
							</tbody>
						</table>
					</div>
					<div id="page_div">
						<%@ include file="./../../common/pagehelper.jsp"%>
					</div>
				</div>
			</div>
		</div>
	</div>

	<!-- 详情模态框（Modal） -->
	<div class="modal fade bs-example-modal-lg" id="myModal" tabindex="-1"
		role="dialog" aria-labelledby="myModalLabel">
		<div class="modal-dialog">
			<div class="modal-content">
				<div class="modal-header">
					<button type="button" class="close" data-dismiss="modal"
						aria-label="Close">
						<span aria-hidden="true">&times;</span>
					</button>
					<h4 class="modal-title" id="myModalLabel">用户银行卡信息详情</h4>
				</div>
				<div class="modal-body" id="modal-body"></div>
				<div class="modal-footer">
					<button type="button" class="btn btn-default" data-dismiss="modal">关闭
					</button>
				</div>
			</div>
		</div>
	</div>
</body>
</html>