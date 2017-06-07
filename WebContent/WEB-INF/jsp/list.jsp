<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="java.util.List" %>
<%@ page import="java.util.Map" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<!-- <fmt:setLocale value="${param.locale}"/> -->
<!-- fmt:setBundle basename="messages" -->
<% List<Map<String, Object>> users = (List<Map<String, Object>>)request.getAttribute("users"); %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <meta name="description" content="">
    <meta name="author" content="">
    <title>Hello World! 讚!</title>
    <script type="text/javascript">
    	function deleteUser(id){
    		if(confirm('確定刪除?')){
    			document.getElementById('id').value = id;
    			document.getElementById('deleteForm').submit();
    		}
    	}
    </script>
</head>
<body>
<form id="deleteForm" method="POST" action="<%=request.getContextPath() %>/s/delete">
	<input type="hidden" name="id" id="id">
</form>
<H1>Hello World! 讚!</H1>
<p><input type="button" value="新增" onclick="javascript: location.href='<%=request.getContextPath() %>/s/add'; " /></p>
<% for(int i = 0; i < users.size(); i++){
   		Map<String, Object> user = users.get(i); %>
<p>
	<%="(" + (i + 1) + ") 員工編號=" + user.get("employeeId") + "; 姓名=" + user.get("name") 
	 + "; 性別=" + user.get("genderName") 
	 + "; 母語=" + user.get("motherLanguageName")
	 + "; 學歷=" + user.get("educationName")
	 + "; 居住縣市=" + user.get("countyName")
	 + "; 卡號=" + user.get("cardNumber") %>
	&nbsp;&nbsp;&nbsp;&nbsp;
	<a href="javascript: location.href='<%=request.getContextPath() %>/s/update?id=<%=user.get("id") %>'; ">更新</a>
	&nbsp;&nbsp;&nbsp;&nbsp;
	<a href="javascript: deleteUser('<%=user.get("id") %>');">刪除</a>
</p>
<% } %>
</body>
</html>