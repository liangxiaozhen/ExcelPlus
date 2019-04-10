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
		$("#preCommonMember_updateForm").bootstrapValidator({
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
		        },
				email:{
					validators:{
						/* notEmpty:{
							message:"请出入email地址"
						}, */
						regexp:{
	                    	 regexp: /^[A-Za-z0-9\u4e00-\u9fa5]+@[a-zA-Z0-9_-]+(\.[a-zA-Z0-9_-]+)+$/,//网上查找验证方法
	                         message: '请输入正确的email地址'
	                    },
					}
				},
				
				mobile:{
					validators:{
						notEmpty:{
							message:"请输入手机号"
						},
						regexp:{
	                    	 regexp: /^1[34578]\d{9}$/,//网上查找验证方法
	                         message: '请输入正确的电话号码'
	                    },
					}
				},
				
				 certno:{
					validators:{
						/* notEmpty:{
							message:"请输入证件号码"
						}, */
						//var certtype = $("#certtype").val();
						 regexp:{
	                    	 regexp: /^[1-9]\d{5}(18|19|([23]\d))\d{2}((0[1-9])|(10|11|12))(([0-2][1-9])|10|20|30|31)\d{3}[0-9Xx]$/,//网上查找验证方法
	                         message: '请输入正确的证件号码'
	                    }, 
					}
				}  
				
			/* 	realname:{
					validators:{
						notEmpty:{
							message:"请输入姓名"
						}
					}
				} */
		       
			}
		});
	});
});
</script>
</head>
<body  style="font-family:'微软雅黑'; ">

<div class="container"  style="margin-top: 25px;">
			<form class="form-horizontal" role="form"  id="preCommonMember_updateForm">
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
					<label class="col-sm-2 control-label laber_from" for="realname">真实姓名</label>
					<div class="col-sm-3">
						<input type="text" name="realname" id="realname" value="${preCommonMember.realname}" class="form-control" />
					</div>
				</div>
				<div class="form-group">
					<label class="col-sm-2 control-label laber_from" for="email">email地址</label>
					<div class="col-sm-3">
						<input type="text" name="email" id="email" value="${preCommonMember.email}" class="form-control" />
					</div>
				</div>
				<div class="form-group">
					<label class="col-sm-2 control-label laber_from" for="mobile">手机号</label>
					<div class="col-sm-3">
						<input type="text" name="mobile" id="mobile" value="${preCommonMember.mobile}" class="form-control" />
					</div>
				</div>
				<div class="form-group">
					<label class="col-sm-2 control-label laber_from" for="certtype">证件类型</label>
					<div class="col-sm-3">
						<select name="certtype" id="certtype" class="form-control">
							<option value="1" <c:if test="${preCommonMember.certtype eq '1'}">selected="selected"</c:if>>身份证</option>
							<option value="2" <c:if test="${preCommonMember.certtype eq '2'}">selected="selected"</c:if>>个人护照</option>
						</select> 
					</div>
				</div>
				
				
				<%-- <div class="form-group">
					<label class="col-sm-2 control-label laber_from" for="isadmin">是否管理员</label>
					<div class="col-sm-3">
						<select name="isadmin" id="isadmin" class="form-control">
							<option value="1" <c:if test="${preCommonMember.isadmin  eq '1'}">selected="selected"</c:if>>是</option>
							<option value="0" <c:if test="${preCommonMember.isadmin  eq '0'}">selected="selected"</c:if>>否</option>
						</select> 
					</div>
				</div> --%>
				<div class="form-group">
					<label class="col-sm-2 control-label laber_from" for="certno">证件号码</label>
					<div class="col-sm-3">
						<input type="text" name="certno" id="certno" value="${preCommonMember.certno}" class="form-control" />
					</div>
				</div>
				
				<div class="form-group">
					<label for="remark" class="col-sm-2 control-label laber_from">备注</label>
					<div class="col-sm-3">
						  <textarea rows="3" class="form-control" name="remark">${preCommonMember.remark}</textarea>
					</div>
				</div>
				
				
				
				</c:if>
			</form>
		</div>
								
</body>
</html>
