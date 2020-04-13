<%@page import="beans.BeanJSP"%>
<%@page import="org.apache.tomcat.jni.User"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Cadastro de Usuario</title>
<link rel="stylesheet" href="resources/css/cadastro.css">
<script src="https://code.jquery.com/jquery-3.4.1.min.js"
	integrity="sha256-CSXorXvZcTkaix6Yvo6HppcZGetbYMGWSFlBw8HfCJo="
	crossorigin="anonymous"></script>
</head>
<body>
	<a href="acessoliberado.jsp"><img alt="Home" title="Home"
		src="resources/img/homepage.png" width="30px" height="30px"></a>
	<a href="index.jsp"><img alt="Exit" title="Exit"
		src="resources/img/close.png" width="30px" height="30px"></a>
	<div style="text-align: center;">
		<h1>Cadastro de Usuário</h1>
		<div style="color: red;">
			<h3>${msg}</h3>
		</div>
	</div>

	<form action="salvarUsuario" method="post" id="formUser"
		onsubmit="return validarCampos() ? true : false"
		enctype="multipart/form-data">
		<ul class="form-style-1">
			<li>
				<table>
					<tr>
						<td>Código:</td>
						<td><input type="text" readonly="readonly" id="id" name="id"
							value="${user.id}"></td>
						<td>Cep:</td>
						<td><input type="text" id="cep" name="cep"
							placeholder="Ex.: 00000-000" onblur="consultaCep();"></td>
					</tr>

					<tr>
						<td>Login:</td>
						<td><input type="text" id="login" name="login" maxlength="20"
							value="${user.login}"></td>
						<td>Rua:</td>
						<td><input type="text" id="rua" name="rua"
							value="${user.rua}"></td>
					</tr>

					<tr>
						<td>Senha:</td>
						<td><input type="password" id="senha" name="senha"
							maxlength="15" value="${user.senha}"></td>
						<td>Bairro:</td>
						<td><input type="text" id="bairro" name="bairro"
							value="${user.bairro}"></td>
					</tr>

					<tr>
						<td>Nome:</td>
						<td><input type="text" id="nome" name="nome"
							value="${user.nome}"></td>
						<td>Cidade:</td>
						<td><input type="text" id="cidade" name="cidade"
							value="${user.cidade}"></td>
					</tr>

					<tr>
						<td>Fone:</td>
						<td><input type="text" id="fone" name="fone"
							value="${user.fone}"></td>
						<td>Estado:</td>
						<td><input type="text" id="estado" name="estado"
							value="${user.estado}"></td>
					</tr>

					<tr>
						<td>IBGE:</td>
						<td><input type="text" id="ibge" name="ibge"
							value="${user.ibge}"></td>

						<td>Perfil</td>
						<td><select id="perfil" name="perfil" style="width: 178px;">
								<option value="nao_informado"
									${user.perfil eq 'nao_informado' ? 'selected' : ''}>Selecionar</option>
								<option value="administrador"
									${user.perfil eq 'administrador' ? 'selected' : ''}>Administrador</option>
								<option value="secretario"
									${user.perfil eq 'secretario' ? 'selected' : ''}>Secretário(a)</option>
								<option value="gerente"
									${user.perfil eq 'gerente' ? 'selected' : ''}>Gerente</option>
								<option value="funcionario"
									${user.perfil eq 'funcionario' ? 'selected' : ''}>Funcionário</option>
						</select></td>

						<td>Ativo:</td>
						<td><input type="checkbox" id="ativo" name="ativo"
							<%if (request.getAttribute("user") != null) {
				BeanJSP user = (BeanJSP) request.getAttribute("user");
				if (user.isAtivo()) {
					out.print(" ");
					out.print("checked=\"checked\"");
					out.print(" ");
				}
			}%>></td>
					</tr>

					<tr>
						<td>Foto:</td>
						<td><input type="file" name="foto"></td>

						<td>Sexo</td>
						<td>Masculino <input type="radio"
							${user.sexo eq 'masculino' ? 'checked' : ''} name="sexo"
							value="masculino"> Feminino <input type="radio"
							${user.sexo eq 'feminino' ? 'checked' : ''} name="sexo"
							value="feminino">
						</td>



					</tr>

					<tr>
						<td>PDF:</td>
						<td><input type="file" name="pdf" value="pdf"></td>
					</tr>

					<tr>
						<td></td>
						<td><input type="submit" value="salvar"> <input
							type="submit" value="cancelar"
							onclick="document.getElementById('formUser').action = 'salvarUsuario?acao=reset'">
						</td>
					</tr>
				</table>
			</li>
		</ul>
	</form>

	<form method="post" action="servletPesquisa">
		<ul class="tb">
			<li>
				<table>
					<tr>
						<td>Descrição</td>
						<td><input type="text" id="descricaoconsulta"
							name="descricaoconsulta"></td>
						<td><input type="submit" value="Pesquisar"></td>
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
				<th>Nome</th>
				<th>Foto</th>
				<th>PDF</th>
				<th>Delete</th>
				<th>Editar</th>
				<th>Telefone</th>
			</tr>
			<c:forEach items="${usuarios}" var="user">
				<tr>
					<td style="width: 150px"><c:out value="${user.id}"></c:out></td>
					<td style="width: 150px"><c:out value="${user.login}"></c:out></td>

					<c:if test="${usuario.miniFoto.isEmpty() == false }">
						<td><a
							href="salvarUsuario?acao=download&tipo=imagem&user=${user.id}"><img
								src='<c:out value="${user.miniFoto}"></c:out>' alt="imagem user"
								width="20px" height="20px"></a></td>
					</c:if>

					<c:if test="${usuario.miniFoto == null }">
						<td><img alt="imagem user" src="resources/img/user.png"
							width="20px" height="20px" onclick="alert('Não possui Foto')"></td>
					</c:if>

					<c:if test="${usuario.pdf.isEmpty() == false }">
						<td><a
							href="salvarUsuario?acao=download&tipo=pdf&user=${user.id}"><img
								alt="pdf" src="resources/img/pdf.png" width="20px" height="20px"></a></td>
					</c:if>

					<c:if test="${usuario.pdf == null }">
						<td><img alt="pdf" src="resources/img/missing.png"
							width="20px" height="20px" onclick="alert('Não possui PDF')">
						</td>
					</c:if>

					<td><c:out value="${user.nome}"></c:out></td>
					<td><c:out value="${user.fone }"></c:out></td>
					<td><a href="salvarUsuario?acao=delete&user=${user.id}"
						onclick="return confirm('Confirma a Exclusão')"><img
							src="resources/img/delete.png" title="Excluir" width="20px"
							height="20px"></a></td>
					<td><a href="salvarUsuario?acao=editar&user=${user.id}"><img
							src="resources/img/edit.png" title="Editar" width="20px"
							height="20px"></a></td>
					<td><a href="salvarTelefone?acao=addFone&user=${user.id}"><img
							src="resources/img/phone.png" title="telefone" width="20px"
							height="20px"></a></td>
				</tr>
			</c:forEach>
		</table>
	</div>
	<script type="text/javascript">
		function validarCampos() {
			if (document.getElementById("login").value == '') {
				alert('Informe o login');
				return false;
			} else if (document.getElementById("senha").value == '') {
				alert('informe a senha');
				return false;
			} else if (document.getElementById("nome").value == '') {
				alert('informe seu nome');
				return false;
			} else if (document.getElementById("fone").value == '') {
				alert('informe seu telefone');
				return false;
			}
			return true;
		}

		function consultaCep() {
			var cep = $("#cep").val();

			//Consulta o webservice viacep.com.br/
			$.getJSON("https://viacep.com.br/ws/" + cep + "/json/?callback=?",
					function(dados) {

						if (!("erro" in dados)) {
							$("#rua").val(dados.logradouro);
							$("#bairro").val(dados.bairro);
							$("#cidade").val(dados.localidade);
							$("#estado").val(dados.uf);
							$("#ibge").val(dados.ibge);
						} else {
							//CEP pesquisado não foi encontrado.
							$("#cep").value('');
							$("#rua").val('');
							$("#bairro").val('');
							$("#cidade").val('');
							$("#estado").val('');
							$("#ibge").val('');
							alert("CEP não encontrado.");
						}
					});
		}
	</script>
</body>
</html>