<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Cadastro de Produtos</title>
<script src="resources/javascript/jquery.min.js" type="text/javascript"></script>
<script src="resources/javascript/jquery.maskMoney.min.js"
	type="text/javascript"></script>
<link rel="stylesheet" href="resources/css/cadastro.css">
</head>
<body>
	<a href="acessoliberado.jsp"><img alt="Home" title="Home"
		src="resources/img/homepage.png" width="30px" height="30px"></a>
	<a href="index.jsp"><img alt="Exit" title="Exit"
		src="resources/img/close.png" width="30px" height="30px"></a>
	<div style="text-align: center;">
		<h1>Cadastro de Produtos</h1>
		<div style="color: red;">
			<h3>${msg}</h3>
		</div>
	</div>

	<form action="salvarProduto" method="post" id="formProduto"
		onsubmit="return validarCampos() ? true : false">
		<ul class="form-style-1">
			<li>
				<table>
					<tr>
						<td>Código:</td>
						<td><input type="text" readonly="readonly" id="id" name="id"
							value="${produto.id}" class="field-long"></td>
					</tr>

					<tr>
						<td>Nome:</td>
						<td><input type="text" id="nome" name="nome"
							value="${produto.nome}"></td>
					</tr>

					<tr>
						<td>Valor R$:</td>
						<td><input type="text" id="valor" name="valor"
							data-thousands="." data-decimal=","
							value="${produto.valorEmTexto}" maxlength="8"></td>
					</tr>

					<tr>
						<td>Quantidade:</td>
						<td><input type="number" id="quantidade" name="quantidade"
							value="${produto.quantidade}" maxlength="7"></td>
					</tr>

					<tr>
						<td>Categoria:</td>
						<td><select id="categorias" name="categoria_id">
								<c:forEach items="${categorias}" var="cate">
									<option value="${cate.id}" id="${cate.id}"
										<c:if test="${cate.id == produto.categoria_id}">
											<c:out value="selected=selected"/>
										</c:if>>
										${cate.nome}</option>
								</c:forEach>
						</select></td>
					</tr>
					<tr>
						<td></td>
						<td><input type="submit" value="salvar"> <input
							type="submit" value="cancelar"
							onclick="document.getElementById('formProduto').action = 'salvarProduto?acao=reset'">
						</td>
					</tr>
				</table>
			</li>
		</ul>
	</form>

	<div class="table-wrapper">
		<table class="fl-table">
			<caption>Produtos Cadastrados</caption>
			<tr>
				<th>Id</th>
				<th>Nome</th>
				<th>Quantidade</th>
				<th>Valor R$</th>
				<th>Delete</th>
				<th>Editar</th>
			</tr>
			<c:forEach items="${produtos}" var="produto">
				<tr>
					<td style="width: 150px"><c:out value="${produto.id}"></c:out></td>
					<td style="width: 150px"><c:out value="${produto.nome}"></c:out></td>
					<td><c:out value="${produto.quantidade}"></c:out></td>
					<fmt:setLocale value="pt_BR" />
					<td><fmt:formatNumber type="number" maxFractionDigits="2"
							value="{produto.valor}" /></td>
					<td><a href="salvarProduto?acao=delete&produto=${produto.id}"><img
							src="resources/img/delete.png" title="Excluir" width="20px"
							height="20px" onclick="return confirm('Confirma a Exclusão')"></a></td>
					<td><a href="salvarProduto?acao=editar&produto=${produto.id}"><img
							src="resources/img/edit.png" title="Editar" width="20px"
							height="20px"></a></td>
				</tr>
			</c:forEach>
		</table>
	</div>
	<script type="text/javascript">
		function validarCampos() {
			if (document.getElementById("nome").value == '') {
				alert('informe o nome');
				return false;
			} else if (document.getElementById("valor").value == '') {
				alert('informe o valor');
				return false;
			} else if (document.getElementById("quantidade").value == '') {
				alert('informe a quantidade');
				return false;
			}
			return true;
		}
	</script>
</body>
<script type="text/javascript">
	$(function() {
		$('#valor').maskMoney();
	})
</script>
</html>