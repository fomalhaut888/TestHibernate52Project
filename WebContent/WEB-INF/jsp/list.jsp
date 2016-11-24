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
</head>
<body>
<H1>Hello World! 讚!</H1>
<% for(Map<String, Object> map: users){ %>
<p><%="id=" + map.get("id") + "; employeeId=" + map.get("employeeId") + "; name=" + map.get("name") + "; status=" + map.get("status") + "; motherLanguage=" + map.get("motherLanguageName") %></p>
<% } %>
</body>
</html>