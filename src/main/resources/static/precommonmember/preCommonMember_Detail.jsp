<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<div>
	<div class="row" style="margin-top: 10px;">
		<div class="col-md-10 col-md-offset-1">
			<span class="col-sm-4 text-right">用户名&nbsp;:</span> 
			<span id="tenderidlb"> 
				<c:if test="${empty preCommonMember.username}">
					--
				</c:if>
				<c:if test="${!empty preCommonMember.username}">
					${preCommonMember.username} 
				</c:if>
			 </span>
		</div>
	<hr/>
	</div>
	
	
	<div class="row" style="margin-top: 10px;">
		<div class="col-md-10 col-md-offset-1">
			<span class="col-sm-4 text-right">真实姓名&nbsp;:</span> 
			<span id="tenderidlb">
				<c:if test="${empty preCommonMember.realname}">
					--
				</c:if>
				<c:if test="${!empty preCommonMember.realname}">
					${preCommonMember.realname}
				</c:if>
			</span>
		</div>
		<hr/>
	</div>

	<div class="row" style="margin-top: 10px;">
		<div class="col-md-10 col-md-offset-1">
			<span class="col-sm-4 text-right">是否管理员&nbsp;:</span> 
			<span id="tenderidlb"> 
				<c:if test="${empty preCommonMember.isadmin}">
					--
				</c:if>
				<c:if test="${!empty preCommonMember.isadmin}">
					<c:if test="${preCommonMember.isadmin eq '1'}">是</c:if>
					<c:if test="${preCommonMember.isadmin eq '0'}">否</c:if>
				</c:if>
			</span>
		</div>
		<hr/>
	</div>
	<div class="row" style="margin-top: 10px;">
		<div class="col-md-10 col-md-offset-1">
			<span class="col-sm-4 text-right">电话号码&nbsp;:</span> 
			<span id="tenderidlb"> 
				<c:if test="${empty preCommonMember.mobile}">
					--
				</c:if>
				<c:if test="${!empty preCommonMember.mobile}">
					${preCommonMember.mobile}
				</c:if>
			</span>
		</div>
		<hr/>
	</div>
	
		<div class="row" style="margin-top: 10px;">
		<div class="col-md-10 col-md-offset-1">
			<span class="col-sm-4 text-right">证件类型&nbsp;:</span> 
			<span id="tenderidlb"> 
				<c:if test="${empty preCommonMember.certtype}">
					--
				</c:if>
				<c:if test="${!empty preCommonMember.certtype}">
					<c:if test="${preCommonMember.certtype eq '1'}">身份证</c:if>
					<c:if test="${preCommonMember.certtype eq '2'}">个人护照</c:if>
				</c:if>
			</span>
		</div>
		<hr/>
	</div>
	
	<div class="row" style="margin-top: 10px;">
		<div class="col-md-10 col-md-offset-1">
			<span class="col-sm-4 text-right">证件号码&nbsp;:</span> 
			<span id="tenderidlb"> 
				<c:if test="${empty preCommonMember.certno}">
					--
				</c:if>
				<c:if test="${!empty preCommonMember.certno}">
					${preCommonMember.certno}
				</c:if>
			</span>
		</div>
		<hr/>
	</div>
	
	<div class="row" style="margin-top: 10px;">
		<div class="col-md-10 col-md-offset-1">
			<span class="col-sm-4 text-right">开户属性&nbsp;:</span> 
			<span id="tenderidlb"> --</span>
		</div>
	<hr/>
	</div>
<%-- 	<div class="row" style="margin-top: 10px;">
		<div class="col-md-10 col-md-offset-1">
			<span class="col-sm-4 text-right">实名状态&nbsp;:</span> 
			<span id="tenderidlb">
				<c:if test="${empty preCommonMember.realstatus}">
					--
				</c:if>
				<c:if test="${!empty preCommonMember.realstatus}">
					<c:if test="${preCommonMember.realstatus eq 0}">
						未认证
					</c:if>
					<c:if test="${preCommonMember.realstatus eq 1}">
						已认证
					</c:if>
				</c:if>
			 </span>
		</div>
	<hr/>
	</div> --%>
	<div class="row" style="margin-top: 10px;">
		<div class="col-md-10 col-md-offset-1">
			<span class="col-sm-4 text-right">注册设备来源&nbsp;:</span> 
			<span id="tenderidlb"> 
				<c:if test="${empty preCommonMember.device}">
					--
				</c:if>
				<c:if test="${!empty preCommonMember.device}">
					${preCommonMember.device} 
				</c:if>
			</span>	
		</div>
	<hr/>
	</div>
	
	<div class="row" style="margin-top: 10px;">
		<div class="col-md-10 col-md-offset-1">
			<span class="col-sm-4 text-right">注册应用来源&nbsp;:</span> 
			<span id="tenderidlb"> 
				<c:if test="${empty preCommonMember.appdevice}">
					--
				</c:if>
				<c:if test="${!empty preCommonMember.appdevice}">
					${preCommonMember.appdevice} 
				</c:if>
			</span>
		</div>
	<hr/>
	</div>
	
	<div class="row" style="margin-top: 10px;">
		<div class="col-md-10 col-md-offset-1">
			<span class="col-sm-4 text-right">注册IP&nbsp;:</span> 
			<span id="tenderidlb"> 
				<c:if test="${empty preCommonMember.regip}">
					--
				</c:if>
				<c:if test="${!empty preCommonMember.regip}">
					${preCommonMember.regip} 
				</c:if>
			</span>
		</div>
	<hr/>
	</div>
	
	<div class="row" style="margin-top: 10px;">
		<div class="col-md-10 col-md-offset-1">
			<span class="col-sm-4 text-right">登录IP&nbsp;:</span> 
			<span id="tenderidlb"> 
				<c:if test="${empty preCommonMember.lastloginip}">
					--
				</c:if>
				<c:if test="${!empty preCommonMember.lastloginip}">
					${preCommonMember.lastloginip} 
				</c:if>
			</span>
		</div>
	<hr/>
	</div>
	
	<div class="row" style="margin-top: 10px;">
		<div class="col-md-10 col-md-offset-1">
			<span class="col-sm-4 text-right">推广码&nbsp;:</span> 
			<span id="tenderidlb"> 
				<c:if test="${empty preCommonMember.recommendcode}">
					--
				</c:if>
				<c:if test="${!empty preCommonMember.recommendcode}">
					${preCommonMember.recommendcode} 
				</c:if>
			</span>
		</div>
	<hr/>
	</div>
	
	<div class="row" style="margin-top: 10px;">
		<div class="col-md-10 col-md-offset-1">
			<span class="col-sm-4 text-right">email地址&nbsp;:</span> 
			<span id="tenderidlb"> 
				<c:if test="${empty preCommonMember.email}">
					--
				</c:if>
				<c:if test="${!empty preCommonMember.email}">
					${preCommonMember.email}
				</c:if>
			</span>
		</div>
		<hr/>
	</div>
		
	<div class="row" style="margin-top: 10px;">
		<div class="col-md-10 col-md-offset-1">
			<span class="col-sm-4 text-right">备注&nbsp;:</span> 
			<span id="tenderidlb"> 
				<c:if test="${empty preCommonMember.remark}">
					--
				</c:if>
				<c:if test="${!empty preCommonMember.remark}">
					${preCommonMember.remark} 
				</c:if>
			</span>
		</div>
	</div>
	
	<%-- <div class="row" style="margin-top: 10px;">
		<div class="col-md-10 col-md-offset-1">
			<span class="col-sm-4 text-right">注册日期&nbsp;:</span> 
			<span id="tenderidlb"> 
				<c:if test="${!empty preCommonMember.regdate}">${sf.format(preCommonMember.regdate)}</c:if>
				<c:if test="${empty preCommonMember.regdate}">暂无时间</c:if>
			</span>
		</div>
		
	</div> --%>
	
<%-- 	<div class="row" style="margin-top: 10px;">
		<div class="col-md-10 col-md-offset-1">
			<span class="col-sm-4 text-right">注册IP&nbsp;:</span> 
			<span id="tenderidlb"> ${preCommonMember.regip}</span>
		</div>
		<hr/>
	</div> --%>
	
<%-- 	<div class="row" style="margin-top: 10px;">
		<div class="col-md-10 col-md-offset-1">
			<span class="col-sm-4 text-right">注册COOKIE&nbsp;:</span> 
			<span id="tenderidlb"> ${preCommonMember.regcookie}</span>
		</div>
		<hr/>
	</div> --%>
	
<%-- 	<div class="row" style="margin-top: 10px;">
		<div class="col-md-10 col-md-offset-1">
			<span class="col-sm-4 text-right">角色&nbsp;:</span> 
			<span id="tenderidlb"> ${preCommonMember.role}</span>
		</div>
		<hr/>
	</div> --%>
	
<%-- 	<div class="row" style="margin-top: 10px;">
		<div class="col-md-10 col-md-offset-1">
			<span class="col-sm-4 text-right">验证日期（邮件）&nbsp;:</span> 
			<span id="tenderidlb"> ${preCommonMember.everifydate}</span>
		</div>
		<hr/>
	</div> --%>
	
<%-- 	<div class="row" style="margin-top: 10px;">
		<div class="col-md-10 col-md-offset-1">
			<span class="col-sm-4 text-right">修改日期（手机）&nbsp;:</span> 
			<span id="tenderidlb"> ${preCommonMember.mverifydate}</span>
		</div>
		<hr/>
	</div>
	
	<div class="row" style="margin-top: 10px;">
		<div class="col-md-10 col-md-offset-1">
			<span class="col-sm-4 text-right">推荐码&nbsp;:</span> 
			<span id="tenderidlb"> ${preCommonMember.recommendcode}</span>
		</div>
		<hr/>
	</div>
	
	<div class="row" style="margin-top: 10px;">
		<div class="col-md-10 col-md-offset-1">
			<span class="col-sm-4 text-right">推荐人&nbsp;:</span> 
			<span id="tenderidlb"> ${preCommonMember.recommend}</span>
		</div>
		<hr/>
	</div>
	
	<div class="row" style="margin-top: 10px;">
		<div class="col-md-10 col-md-offset-1">
			<span class="col-sm-4 text-right">关联账户数&nbsp;:</span> 
			<span id="tenderidlb"> ${preCommonMember.linkacccount}个</span>
		</div>
		<hr/>
	</div>
	
	<div class="row" style="margin-top: 10px;">
		<div class="col-md-10 col-md-offset-1">
			<span class="col-sm-4 text-right">备注&nbsp;:</span> 
			<span id="tenderidlb"> ${preCommonMember.remark}</span>
		</div>
	</div> --%>

</div>
