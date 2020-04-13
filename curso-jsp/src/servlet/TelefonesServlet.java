package servlet;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import beans.BeanJSP;
import beans.Telefones;
import dao.DaoTelefones;
import dao.DaoUsuario;

@WebServlet("/salvarTelefone")
public class TelefonesServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private DaoUsuario daoUsuario = new DaoUsuario();
	private DaoTelefones daoTelefones = new DaoTelefones();

	public TelefonesServlet() {
		super();

	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		try {
			String acao = request.getParameter("acao");
			String user = request.getParameter("user");
			BeanJSP usuario = daoUsuario.consultar(user);

			if (acao.endsWith("addFone")) {

				request.getSession().setAttribute("userEsc", usuario);
				request.setAttribute("userEsc", usuario);

				RequestDispatcher view = request.getRequestDispatcher("/telefones.jsp");
				request.setAttribute("telefones", daoTelefones.listar(usuario.getId()));
				view.forward(request, response);
			} else if (acao.endsWith("deleteFone")) {
				String foneId = request.getParameter("fone");
				daoTelefones.delete(foneId);

				RequestDispatcher view = request.getRequestDispatcher("/telefones.jsp");
				request.setAttribute("telefones", daoTelefones.listar(Long.parseLong(user)));
				request.setAttribute("msg", "Removido com sucesso!");
				view.forward(request, response);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		try {
			BeanJSP beanJSP = (BeanJSP) request.getSession().getAttribute("userEsc");

			String numero = request.getParameter("numero");
			String tipo = request.getParameter("tipo");
			String acao = request.getParameter("acao");

			if (acao == null || (acao != null && !acao.equalsIgnoreCase("voltare"))) {

				if (numero == null || (numero != null && numero.isEmpty())) {
					RequestDispatcher view = request.getRequestDispatcher("/telefones.jsp");
					request.setAttribute("telefones", daoTelefones.listar(beanJSP.getId()));
					request.setAttribute("msg", "Informe o Numero de Telefone! ");
					view.forward(request, response);
				} else {

					Telefones telefones = new Telefones();
					telefones.setNumero(numero);
					telefones.setTipo(tipo);
					telefones.setUsuario(beanJSP.getId());
					daoTelefones.salvar(telefones);

					request.getSession().setAttribute("userEsc", beanJSP);
					request.setAttribute("userEsc", beanJSP);

					RequestDispatcher view = request.getRequestDispatcher("/telefones.jsp");
					request.setAttribute("telefones", daoTelefones.listar(beanJSP.getId()));
					request.setAttribute("msg", "Salvo com sucesso!");
					view.forward(request, response);
				}
			} else {
				RequestDispatcher view = request.getRequestDispatcher("/cadastroUsuario.jsp");
				request.setAttribute("usuarios", daoUsuario.listar());
				view.forward(request, response);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
