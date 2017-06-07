<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="java.util.List" %>
<%@ page import="java.util.Map" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<!-- <fmt:setLocale value="${param.locale}"/> -->
<!-- fmt:setBundle basename="messages" -->
<% 
	List<Map<String, Object>> genders = (List<Map<String, Object>>)request.getAttribute("genders");
	List<Map<String, Object>> languages = (List<Map<String, Object>>)request.getAttribute("languages");
	List<Map<String, Object>> educations = (List<Map<String, Object>>)request.getAttribute("educations");
	List<Map<String, Object>> counties = (List<Map<String, Object>>)request.getAttribute("counties");
%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <meta name="description" content="">
    <meta name="author" content="">
    <title>新增</title>
</head>
<body>
<H1>新增!</H1>
<form method="post" action="<%=request.getContextPath() %>/s/addToDb">
<p>員工編號: <input type="text" name="employeeId" required/></p>
<p>姓名: <input type="text" name="name" required /></p>
<p>
	性別: 
	<select name="gender">
<%	for(Map<String, Object> gender: genders){ %>
		<option value="<%=gender.get("id") %>"><%=gender.get("name") %></option>
<%	} %>
	</select>
</p>
<p>
	母語: 
	<select name="motherLanguage">
<%	for(Map<String, Object> language: languages){ %>
		<option value="<%=language.get("id") %>"><%=language.get("name") %></option>
<%	} %>
	</select>
</p>
<p>
	學歷: 
	<select name="education">
<%	for(Map<String, Object> education: educations){ %>
		<option value="<%=education.get("id") %>"><%=education.get("name") %></option>
<%	} %>
	</select>
</p>
<p>
	居住縣市: 
	<select name="county">
<%	for(Map<String, Object> county: counties){ %>
		<option value="<%=county.get("id") %>"><%=county.get("name") %></option>
<%	} %>
	</select>
</p>
<p>
	<input type="submit" value="新增">
	&nbsp;&nbsp;&nbsp;&nbsp;
	<input type="reset" value="重設">
	&nbsp;&nbsp;&nbsp;&nbsp;
	<input type="button" value="回列表" onclick="javascript: location.href='<%=request.getContextPath() %>/s/list'; ">
</p>
</form>
</body>
</html>