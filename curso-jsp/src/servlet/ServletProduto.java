package servlet;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import beans.Produto;
import dao.DaoProduto;

@WebServlet("/SalvarProduto")
public class ServletProduto extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private DaoProduto daoProduto = new DaoProduto();

	public ServletProduto() {
		super();
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		try {
			String acao = request.getParameter("acao") != null ? request.getParameter("acao") : "listartodos";
			String produto = request.getParameter("produto");
			
			RequestDispatcher view = request.getRequestDispatcher("/cadastroProduto.jsp");
			if (acao.equalsIgnoreCase("delete")) {
				daoProduto.delete(produto);
				request.setAttribute("produtos", daoProduto.listar());
				
			} else if (acao.equalsIgnoreCase("editar")) {
				Produto beanJSP = daoProduto.consultar(produto);
				request.setAttribute("produto", beanJSP);
				
			} else if (acao.equalsIgnoreCase("listartodos")) {
				request.setAttribute("produtos", daoProduto.listar());
				
			}
			request.setAttribute("categorias", daoProduto.listaCategorias());
			view.forward(request, response);

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		String acao = request.getParameter("acao");

		if (acao != null && acao.equalsIgnoreCase("reset")) {
			try {
				RequestDispatcher view = request.getRequestDispatcher("/cadastroProduto.jsp");
				request.setAttribute("produtos", daoProduto.listar());
				view.forward(request, response);

			} catch (Exception e) {
				e.printStackTrace();
			}

		} else {

			String id = request.getParameter("id");
			String nome = request.getParameter("nome");
			String quantidade = request.getParameter("quantidade");
			String valor = request.getParameter("valor");
			String categoria = request.getParameter("categoria_id");

			try {

				String msg = null;
				boolean podeInserir = true;

				if (id == null || id.isEmpty() && !daoProduto.validarNome(nome)) {
					msg = "Produto já existe com o mesmo nome!";
					podeInserir = false;
				} else if (nome == null || nome.isEmpty()) {
					msg = "Nome deve ser informado";
					podeInserir = false;
				} else if (quantidade == null || quantidade.isEmpty()) {
					msg = "Quantidade deve ser informada";
					podeInserir = false;
				} else if (valor == null || valor.isEmpty()) {
					msg = "Valor deve ser informado";
					podeInserir = false;
				}

				Produto produto = new Produto();
				produto.setNome(nome);
				produto.setId(!id.isEmpty() ? Long.parseLong(id) : null);
				produto.setCategoria_id(Long.parseLong(categoria));

				if (valor != null && !valor.isEmpty()) {
					String valorParse = valor.replaceAll("\\.", "");// 10.500,20
					valorParse = valorParse.replaceAll("\\,", ".");//10500.20
					produto.setValor(Double.parseDouble(valor.replace("\\,", ".")));
				}
				if (quantidade != null && !quantidade.isEmpty())
					produto.setQuantidade(Double.parseDouble(quantidade));

				if (msg != null) {
					request.setAttribute("msg", msg);
				}
				if (id == null || id.isEmpty() && daoProduto.validarNome(nome) && podeInserir) {
					daoProduto.salvar(produto);
				} else if (id != null && !id.isEmpty() && podeInserir) {
					daoProduto.atualizar(produto);
				}
				if (!podeInserir) {
					request.setAttribute("produto", produto);
				}
				RequestDispatcher view = request.getRequestDispatcher("/cadastroProduto.jsp");
				request.setAttribute("produtos", daoProduto.listar());
				request.setAttribute("categorias", daoProduto.listaCategorias());
				view.forward(request, response);

			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

}
