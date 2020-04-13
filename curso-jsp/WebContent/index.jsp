<jsp:useBean id="calcular" class="beans.BeanJSP" type="beans.BeanJSP"
	scope="page"></jsp:useBean>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
<link rel="stylesheet" href="resources/css/estilo.css">
</head>
<body>

	<c:out value="${'Bem vindo'}" />

	<p />
	<p />
	<p />
	<p />
	<div class="login">
		<h1>Welcome</h1>
		<form action="LoginServlet" method="post">
			Login: <input type="text" id="login" name="login"> <br />
			Password: <input type="password" id="senha" name="senha"> <br />
			<button type="submit" value="logar" class="btn btn-primary btn-block btn-large">Let me</button>
		</form>
	</div>
</body>
</html>