<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Cadastro de Telefones</title>
<link rel="stylesheet" href="resources/css/cadastro.css">

</head>
<body>
	<a href="acessoliberado.jsp">Inicio</a>
	<a href="index.jsp">Sair</a>
	<div style="text-align: center;">
		<h1>Cadastro de Telefones</h1>
		<div style="color: red;">
			<h3>${msg}</h3>
		</div>
	</div>
	<div style="text-align: center; color: red;">
		<h2>${msg}</h2>
	</div>
	<form action="salvarTelefone" method="post" id="formUser"
		onsubmit="return validarCampos() ? true : false">
		<ul class="form-style-1">
			<li>
				<table>
					<tr>
						<td>User:</td>
						<td><input type="text" readonly="readonly" id="user"
							name="user" value="${userEsc.id}"></td>
						<td><input type="text" readonly="readonly" id="nome"
							name="nome" value="${UserEsc.nome}"></td>
					</tr>
					<tr>
						<td>Numero:</td>
						<td><input type="text" id="numero" name="numero"
							placeholder="Ex.: 0000-0000"></td>
						<td><select id="tipo" name="tipo">
								<option></option>
								<option>Residencial</option>
								<option>Celular</option>
						</select></td>
					</tr>
					<tr>
						<td />
						<td><input type="submit" value="salvar"> <input
							type="submit" value="voltar"
							onclick="document.getElementById('formUser').action = 'salvarTelefone?acao=voltar'"></td>
					</tr>
				</table>
			</li>
		</ul>
	</form>

	<div class="table-wrapper">
		<table class="fl-table">
			<caption>Usuários Cadastrados</caption>
			<tr>
				<th>Id</th>
				<th>Numero</th>
				<th>Tipo</th>
				<th>Excluir</th>
			</tr>
			<c:forEach items="${telefones}" var="fone">
				<tr>
					<td style="width: 150px"><c:out value="${fone.id}"></c:out></td>
					<td style="width: 150px"><c:out value="${fone.numero}"></c:out></td>
					<td><c:out value="${fone.tipo}"></c:out></td>
					<td><c:out value="${user.fone }"></c:out></td>
					<td><a
						href="salvarTelefone?acao=${fone.usuario}&acao=deleteFone&fone=${fone.id}"><img
							src="resources/img/delete.png" title="Excluir" width="20px"
							height="20px"></a></td>
				</tr>
			</c:forEach>
		</table>
	</div>
	<script type="text/javascript">
		function validarCampos() {
			if (document.getElementById("numero").value == '') {
				alert('Informe o numero');
				return false;
			} else if (document.getElementById("tipo").value == '') {
				alert('Informe o tipo');
				return false;
			}
		}
	</script>
</body>
</html>