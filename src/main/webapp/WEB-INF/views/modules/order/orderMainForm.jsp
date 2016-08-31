<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>订单管理</title>
	<meta name="decorator" content="default"/>
	<script type="text/javascript">
		$(document).ready(function() {
			//$("#name").focus();
			$("#inputForm").validate({
				submitHandler: function(form){
					loading('正在提交，请稍等...');
					form.submit();
				},
				errorContainer: "#messageBox",
				errorPlacement: function(error, element) {
					$("#messageBox").text("输入有误，请先更正。");
					if (element.is(":checkbox")||element.is(":radio")||element.parent().is(".input-append")){
						error.appendTo(element.parent().parent());
					} else {
						error.insertAfter(element);
					}
				}
			});
		});
	</script>
</head>
<body>
	<ul class="nav nav-tabs">
		<li><a href="${ctx}/order/orderMain/">订单列表</a></li>
		<li class="active"><a href="${ctx}/order/orderMain/form?id=${orderMain.id}">订单<shiro:hasPermission name="order:orderMain:edit">${not empty orderMain.id?'修改':'添加'}</shiro:hasPermission><shiro:lacksPermission name="order:orderMain:edit">查看</shiro:lacksPermission></a></li>
	</ul><br/>
		<c:set var="formUrl" value="${ctx}/order/orderMain/save"/>  
		<c:if test="${not empty orderMain.id}">
			<c:set var="formUrl" value="${ctx}/order/orderMain/saveAudit"/>  
		</c:if>
		
   		<form:form id="inputForm" modelAttribute="orderMain" action="${formUrl}" method="post" class="form-horizontal">
	
		<form:hidden path="id"/>
		<form:hidden path="act.taskId"/>
		<form:hidden path="act.taskName"/>
		<form:hidden path="act.taskDefKey"/>
		<form:hidden path="act.procInsId"/>
		<form:hidden path="act.procDefId"/>
		<form:hidden id="flag" path="act.flag"/>
		<sys:message content="${message}"/>		
		<div class="control-group">
			<label class="control-label">部门ID：</label>
			<div class="controls">
				<sys:treeselect id="office" name="office.id" value="${orderMain.office.id}" labelName="office.name" labelValue="${orderMain.office.name}"
					title="部门" url="/sys/office/treeData?type=2" cssClass="" allowClear="true" notAllowSelectParent="true"/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">负责人ID：</label>
			<div class="controls">
				<sys:treeselect id="user" name="user.id" value="${orderMain.user.id}" labelName="user.name" labelValue="${orderMain.user.name}"
					title="用户" url="/sys/office/treeData?type=3" cssClass="" allowClear="true" notAllowSelectParent="true"/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">区域标号：</label>
			<div class="controls">
				<sys:treeselect id="area" name="area.id" value="${orderMain.area.id}" labelName="area.name" labelValue="${orderMain.area.name}"
					title="区域" url="/sys/area/treeData" cssClass="" allowClear="true" notAllowSelectParent="true"/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">名称：</label>
			<div class="controls">
				<form:input path="name" htmlEscape="false" maxlength="100" class="input-xlarge "/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">编号：</label>
			<div class="controls">
				<form:input path="code" htmlEscape="false" maxlength="10" class="input-xlarge required"/>
				<span class="help-inline"><font color="red">*</font> </span>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">类型：</label>
			<div class="controls">
				<form:radiobuttons path="type" items="${fns:getDictList('order_type')}" itemLabel="label" itemValue="value" htmlEscape="false" class="required"/>
				<span class="help-inline"><font color="red">*</font> </span>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">状态：</label>
			<div class="controls">
				<form:select path="status" class="input-xlarge required">
					<form:option value="" label=""/>
					<form:options items="${fns:getDictList('order_status')}" itemLabel="label" itemValue="value" htmlEscape="false"/>
				</form:select>
				<span class="help-inline"><font color="red">*</font> </span>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">关闭理由：</label>
			<div class="controls">
				<form:select path="closeReason" class="input-xlarge ">
					<form:option value="" label=""/>
					<form:options items="${fns:getDictList('order_close_reason')}" itemLabel="label" itemValue="value" htmlEscape="false"/>
				</form:select>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">详情：</label>
			<div class="controls">
				<form:input path="remark" htmlEscape="false" maxlength="200" class="input-xlarge "/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">备注：</label>
			<div class="controls">
				<form:textarea path="remarks" htmlEscape="false" rows="4" maxlength="255" class="input-xxlarge "/>
			</div>
		</div>
		<c:if test="${not empty orderMain.id}">
		<div class="control-group">
			<label class="control-label">您的意见：</label>
			<div class="controls">
				<form:textarea path="act.comment" class="required" rows="5" maxlength="20" cssStyle="width:500px"/>
			</div>
		</div>
		</c:if>		
		<div class="form-actions">
			<shiro:hasPermission name="order:orderMain:edit">
				<c:if test="${empty orderMain.id}">
					<input id="btnSubmit" class="btn btn-primary" type="submit" value="保 存"/>&nbsp;
				</c:if>		
				<c:if test="${not empty orderMain.id}">
					<input id="btnSubmit" class="btn btn-primary" type="submit" value="同 意" onclick="$('#flag').val('yes')"/>&nbsp;
					<input id="btnSubmit" class="btn btn-inverse" type="submit" value="驳 回" onclick="$('#flag').val('no')"/>&nbsp;
				</c:if>
			</shiro:hasPermission>
			<input id="btnCancel" class="btn" type="button" value="返 回" onclick="history.go(-1)"/>
		
		</div>
		
	
		
		<c:if test="${not empty orderMain.id}">
			<act:histoicFlow procInsId="${orderMain.act.procInsId}" />
		</c:if>
	</form:form>
</body>
</html>