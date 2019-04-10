<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>

<!DOCTYPE HTML>
<html>
<head>
<style type="text/css">
     *{margin: 0px;padding: 0px;}
	.laber_from {color: #222;font-weight: normal;}
	.route_bg{ background-color: #E7E7E7; border-radius: 4px; padding: 5px; margin-right: 5px;margin-left: 5px;margin-top: 5px; } 
	.route_bg i{ color: #ccc;font-weight: 400;font-size: 12px;padding-right: 5px;line-height: 25px; } 
	.route_bg a{ font-size: 12px; color: #666; text-decoration: none;line-height: 1.6;font-family: "Helvetica Neue","Hiragino Sans GB","Microsoft YaHei","\9ED1\4F53",Arial,sans-serif !important; } 
	.route_bg a:hover{ color: #888; text-decoration: none;}
	hr{
		margin: 5px;
	} 
</style>
<script type="text/javascript">
//修改数据校验
$(function(){
	
	
	$(document).on("change",'select#postingamessagestatus',function(){
		var  postingamessagestatus = $("#postingamessagestatus").val();
		if(postingamessagestatus=="0"){
			$("#fatieauditStutas").hide();
		}
		if(postingamessagestatus=="1"){
			$("#fatieauditStutas").show();
		}
	});
	
	$(document).on("change",'select#replystatus',function(){
		var  replystatus = $("#replystatus").val();
		if(replystatus=="0"){
			$("#huifuAuditStatus").hide();
		}
		if(replystatus=="1"){
			$("#huifuAuditStatus").show();
		}
	});
	
	$(document).on("change",'select#delmassagestatus',function(){
		var  delmassagestatus = $("#delmassagestatus").val();
		if(delmassagestatus=="0"){
			$("#shantieStatus").hide();
		}
		if(delmassagestatus=="1"){
			$("#shantieStatus").show();
		}
	});
});


</script>
</head>
<body  style="font-family:'微软雅黑'; ">

<div class="container"  style="margin-top: 25px;">
			<form class="form-horizontal" role="form"  id="auth_updateForm">
			<c:if test="${empty upp}">
			    <label>暂无数据</label>
			</c:if>
			<c:if test="${!empty upp}">
			<input type="hidden" name="id" value="${upp.id}"/>
			<input type="hidden" name="uid" value="${upp.uid}"/>
				<div class="form-group">
					<label class="col-sm-2 control-label laber_from" for="type">设置模块</label>
					<div class="col-sm-3">
					<%-- 	<select name="type" id="type" class="form-control" >
							<option value="1" <c:if test="${upp.type eq '1'}">selected="selected"</c:if>>茶馆</option>
							<option value="2" <c:if test="${upp.type eq '2'}">selected="selected"</c:if>>资讯</option>
						</select> 
						 --%>
						 <c:if test="${upp.type  eq 1}">
                             <span>茶馆</span>
                         </c:if>
                         <c:if test="${upp.type eq 2 }">
                             <span>资讯</span>
                         </c:if>
					</div>
				</div>
				
			
			
				<div class="form-group">
					<label class="col-sm-2 control-label laber_from" for="postingamessagestatus">能否发帖</label>
					<div class="col-sm-3">
						<select name="postingamessagestatus" id="postingamessagestatus" class="form-control" >
							<option value="1" <c:if test="${upp.postingamessagestatus eq '1'}">selected="selected"</c:if>>能</option>
							<option value="0" <c:if test="${upp.postingamessagestatus eq '0'}">selected="selected"</c:if>>否</option>
						</select> 
					</div>
				</div>
				
				<!-- 能 发帖了 才需要审核 -->
				<div class="form-group" id="fatieauditStutas">
					<%-- <c:if test="${upp.postingamessagestatus eq '1'}"> --%>
						<label class="col-sm-2 control-label laber_from" for="postingamessageauditstatus">发帖是否审核</label>
						<div class="col-sm-3">
							<select name="postingamessageauditstatus" id="postingamessageauditstatus" class="form-control">
								<option value="1" <c:if test="${upp.postingamessageauditstatus eq '1'}">selected="selected"</c:if>>是</option>
								<option value="0" <c:if test="${upp.postingamessageauditstatus eq '0'}">selected="selected"</c:if>>否</option>
							</select> 
						</div>
				<%-- 	</c:if> --%>
				</div>
				
				<div class="form-group">
					<label class="col-sm-2 control-label laber_from" for="replystatus">能否回复</label>
					<div class="col-sm-3">
						<select name="replystatus" id="replystatus" class="form-control">
							<option value="1" <c:if test="${upp.replystatus eq '1'}">selected="selected"</c:if>>能</option>
							<option value="0" <c:if test="${upp.replystatus eq '0'}">selected="selected"</c:if>>否</option>
						</select> 
					</div>
				</div>
				
				<div class="form-group" id="huifuAuditStatus">
					<%-- <c:if test="${upp.replystatus eq '1'}"> --%>
						<label class="col-sm-2 control-label laber_from" for="replyauditstatus">回复是否审核</label>
						<div class="col-sm-3">
							<select name="replyauditstatus" id="replyauditstatus" class="form-control">
								<option value="1" <c:if test="${upp.replyauditstatus eq '1'}">selected="selected"</c:if>>是</option>
								<option value="0" <c:if test="${upp.replyauditstatus eq '0'}">selected="selected"</c:if>>否</option>
							</select> 
						</div>
					<%-- </c:if> --%>
				</div>
				
				<div class="form-group">
					<label class="col-sm-2 control-label laber_from" for="postingamessageupdatestatus">发帖修改</label>
					<div class="col-sm-3">
						<select name="postingamessageupdatestatus" id="postingamessageupdatestatus" class="form-control">
							<option value="1" <c:if test="${upp.postingamessageupdatestatus eq '1'}">selected="selected"</c:if>>定时修改</option>
							<option value="2" <c:if test="${upp.postingamessageupdatestatus eq '2'}">selected="selected"</c:if>>及时修改</option>
						</select> 
					</div>
				</div>
				
				<div class="form-group">
					<label class="col-sm-2 control-label laber_from" for="reworkupdatestatus">回帖修改</label>
					<div class="col-sm-3">
						<select name="reworkupdatestatus" id="reworkupdatestatus" class="form-control">
							<option value="1" <c:if test="${upp.reworkupdatestatus eq '1'}">selected="selected"</c:if>>定时修改</option>
							<option value="2" <c:if test="${upp.reworkupdatestatus eq '2'}">selected="selected"</c:if>>及时修改</option>
						</select> 
					</div>
				</div>
				
				<div class="form-group">
					<label class="col-sm-2 control-label laber_from" for="postinginterval">发帖间隔时间</label>
					<div class="col-sm-3">
						<input type="text" name="postinginterval" id="postinginterval" value="${upp.postinginterval}" class="form-control" />
					</div>
				</div>
				
				<div class="form-group">
					<label class="col-sm-2 control-label laber_from" for="replyinterval">回帖间隔时间</label>
					<div class="col-sm-3">
						<input type="text" name="replyinterval" id="replyinterval" value="${upp.replyinterval}" class="form-control" />
					</div>
				</div>
				
				<div class="form-group">
					<label class="col-sm-2 control-label laber_from" for="delmassagestatus">是否容许删帖</label>
					<div class="col-sm-3">
						<select name="delmassagestatus" id="delmassagestatus" class="form-control">
							<option value="1" <c:if test="${upp.delmassagestatus eq '1'}">selected="selected"</c:if>>容许</option>
							<option value="0" <c:if test="${upp.delmassagestatus eq '0'}">selected="selected"</c:if>>不容许</option>
						</select> 
					</div>
				</div>
				
				
				<div class="form-group" id="shantieStatus">
					<%-- <c:if test="${upp.delmassagestatus eq '1'}"> --%>
						<label class="col-sm-2 control-label laber_from" for="timingortimely">删帖状态</label>
						<div class="col-sm-3">
							<select name="timingortimely" id="timingortimely" class="form-control">
								<option value="1" <c:if test="${upp.timingortimely eq '1'}">selected="selected"</c:if>>定时删帖</option>
								<option value="2" <c:if test="${upp.timingortimely eq '2'}">selected="selected"</c:if>>及时删帖</option>
							</select> 
						</div>
					<%-- </c:if> --%>
				</div>
				
				</c:if>
			</form>
		</div>
								
</body>
</html>
