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
	$("#updateModal").on("shown.bs.modal",function(){
		$("#salesman_updateForm").bootstrapValidator({
			icon: {
	            valid: 'glyphicon glyphicon-ok',
	            invalid: 'glyphicon glyphicon-remove',
	            validating: 'glyphicon glyphicon-refresh'
	        },
	        fields:{
		        username:{
					validators:{
						notEmpty:{
							message:"请输入用户名"
						}/* ,
                        stringLength: {  
                            min: 5,  
                            max: 30,  
                            message: '用户名长度大于6位并且小于30位'  
                        }, */
					}
		        }
				
		       
			}
		});
	});
});
</script>
</head>
<body  style="font-family:'微软雅黑'; ">

<div class="container"  style="margin-top: 25px;">
			<form class="form-horizontal" role="form"  id="salesman_updateForm">
			<c:if test="${empty preCommonMember}">
			    <label>暂无数据</label>
			</c:if>
			<c:if test="${!empty preCommonMember}">
			<input type="hidden" name="uid" value="${preCommonMember.uid}"/>
				
				<div class="form-group">
					<label class="col-sm-2 control-label laber_from" for="username">用户名</label>
					<div class="col-sm-3">
						<input type="text" name="username" id="username" value="${preCommonMember.username}" class="form-control" />
					</div>
				</div>
				
				<div class="form-group">
					<label class="col-sm-2 control-label laber_from" for="issalesman">是否业务员</label>
					<div class="col-sm-3">
						<select name="issalesman" id="issalesman" class="form-control">
							<option value="1" <c:if test="${preCommonMember.issalesman  eq '1'}">selected="selected"</c:if>>是</option>
							<option value="0" <c:if test="${preCommonMember.issalesman  eq '0' or empty preCommonMember.issalesman}">selected="selected"</c:if>>否</option>
						</select> 
					</div>
				</div>
				
				<div class="form-group">
					<label for="remark" class="col-sm-2 control-label laber_from">业务员备注</label>
					<div class="col-sm-3">
						  <textarea rows="3" class="form-control" name="salesmanremark">${preCommonMember.salesmanremark}</textarea>
					</div>
				</div>
				
				
				
				</c:if>
			</form>
		</div>
								
</body>
</html>
