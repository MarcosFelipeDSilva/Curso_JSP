package servlet;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import javax.imageio.ImageIO;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;
import javax.xml.bind.DatatypeConverter;

import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.apache.tomcat.util.codec.binary.Base64;
import org.apache.tomcat.util.http.fileupload.ByteArrayOutputStream;

import beans.BeanJSP;
import dao.DaoUsuario;

@WebServlet("/salvarUsuario")
@MultipartConfig
public class Usuario extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private DaoUsuario daoUsuario = new DaoUsuario();

	public Usuario() {
		super();

	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		try {
			String acao = request.getParameter("acao");
			String user = request.getParameter("user");
			if (acao != null && acao.equalsIgnoreCase("delete")) {
				daoUsuario.delete(user);
				RequestDispatcher view = request.getRequestDispatcher("/cadastroUsuario.jsp");
				request.setAttribute("usuarios", daoUsuario.listar());
				view.forward(request, response);
			} else if (acao != null && acao.equalsIgnoreCase("editar")) {
				BeanJSP beanJSP = daoUsuario.consultar(user);
				RequestDispatcher view = request.getRequestDispatcher("/cadastroUsuario.jsp");
				request.setAttribute("user", beanJSP);
				view.forward(request, response);
			} else if (acao != null && acao.equalsIgnoreCase("listartodos")) {
				RequestDispatcher view = request.getRequestDispatcher("/cadastroUsuario.jsp");
				request.setAttribute("usuarios", daoUsuario.listar());
				view.forward(request, response);
			} else if (acao != null && acao.equalsIgnoreCase("download")) {
				BeanJSP usuario = daoUsuario.consultar(user);
				if (usuario != null) {
					String tipo = request.getParameter("tipo");
					String contentType = "";
					byte[] fileByte = null;

					if (tipo.equalsIgnoreCase("imagem")) {
						contentType = usuario.getContentType();
						new Base64();
						fileByte = Base64.decodeBase64(usuario.getFoto());
					} else if (tipo.equalsIgnoreCase("pdf")) {
						contentType = usuario.getCotentTypePdf();
						new Base64();
						fileByte = Base64.decodeBase64(usuario.getPdf());
					}
					response.setHeader("Content-Disposition",
							"attachment;filename=arquivo." + contentType.split("\\/")[1]);

					OutputStream os = response.getOutputStream();
					os.write(fileByte);
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

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String acao = request.getParameter("acao");
		if (acao != null && acao.equalsIgnoreCase("reset")) {
			try {
				RequestDispatcher view = request.getRequestDispatcher("/cadastroUsuario.jsp");
				request.setAttribute("usuarios", daoUsuario.listar());
				view.forward(request, response);
			} catch (Exception e) {
				e.printStackTrace();
			}
		} else {
			String id = request.getParameter("id");
			String login = request.getParameter("login");
			String senha = request.getParameter("senha");
			String nome = request.getParameter("nome");
			String fone = request.getParameter("fone");
			String cep = request.getParameter("cep");
			String rua = request.getParameter("rua");
			String bairro = request.getParameter("bairro");
			String cidade = request.getParameter("cidade");
			String estado = request.getParameter("estado");
			String ibge = request.getParameter("ibge");
			String sexo = request.getParameter("sexo");
			String perfil = request.getParameter("perfil");

			BeanJSP usuario = new BeanJSP();
			usuario.setId((id != null && !id.isEmpty()) ? Long.parseLong(id) : null);
			usuario.setLogin(login);
			usuario.setSenha(senha);
			usuario.setNome(nome);
			usuario.setFone(fone);
			usuario.setCep(cep);
			usuario.setRua(rua);
			usuario.setBairro(bairro);
			usuario.setCidade(cidade);
			usuario.setEstado(estado);
			usuario.setIbge(ibge);
			usuario.setSexo(sexo);
			usuario.setPerfil(perfil);
			
			if(request.getParameter("atvo") != null && request.getParameter("atvo").equalsIgnoreCase("on")) {
				usuario.setAtivo(true);
			}else {
				usuario.setAtivo(false);
			}
			try {
				if (ServletFileUpload.isMultipartContent(request)) {
					Part imagemFoto = request.getPart("foto");
					if (imagemFoto != null && imagemFoto.getInputStream().available() > 0) {
						String fotoBase64 = new Base64()
								.encodeBase64String(converteStreamParaByte(imagemFoto.getInputStream()));

						usuario.setFoto(fotoBase64);
						usuario.setContentType(imagemFoto.getContentType());
						/* inicio imagem miniatura */

						/* transforma em um bufferedimage */
						byte[] imageByteDecode = new Base64().decodeBase64(fotoBase64);
						BufferedImage bufferedImage = ImageIO.read(new ByteArrayInputStream(imageByteDecode));
						/* pega o tipo da imagem */
						int type = bufferedImage.getType() == 0 ? bufferedImage.TYPE_INT_ARGB : bufferedImage.getType();
						/* cria imagem em miniatura */
						BufferedImage resizedImage = new BufferedImage(100, 100, type);
						Graphics2D g = resizedImage.createGraphics();
						g.drawImage(bufferedImage, 0, 0, 100, 100, null);
						g.dispose();
						/* escrever imagem */
						ByteArrayOutputStream baos = new ByteArrayOutputStream();
						ImageIO.write(resizedImage, "png", baos);

						String miniatura = "data:image/png;base64,"
								+ DatatypeConverter.printBase64Binary(baos.toByteArray());
						usuario.setMiniFoto(miniatura);

						/* fim imagem miniatura */

					} else {
						usuario.setAtualizarImagem(false);
					}
					Part pdf = request.getPart("pdf");

					if (pdf != null && pdf.getInputStream().available() > 0) {
						String pdfBase64 = new Base64()
								.encodeBase64String(converteStreamParaByte(pdf.getInputStream()));
						usuario.setPdf(pdfBase64);
						usuario.setCotentTypePdf(pdf.getContentType());
					} else {
						usuario.setAtualizarPdf(false);
					}
					String msg = null;
					boolean pod = true;

					if (login == null || login.isEmpty()) {
						msg = "Login deve ser informado";
						pod = false;
					} else if (senha == null || senha.isEmpty()) {
						msg = "Senha deve ser informado";
						pod = false;
					} else if (nome == null || nome.isEmpty()) {
						msg = "Nome deve ser informado";
						pod = false;
					} else if (fone == null || fone.isEmpty()) {
						msg = "Tefefone deve ser informado";
						pod = false;
					} else if (id == null || id.isEmpty() && !daoUsuario.validarLogin(login)) {
						msg = "Login de usuario já cadastrado";
						pod = false;
					} else if (id != null || id.isEmpty() && !daoUsuario.validarLoginUpdate(login, id)) {
						msg = "Login já existe para outro usuario";
						pod = false;
					}
					if (msg != null) {
						request.setAttribute("msg", msg);
					}
					if (id == null || id.isEmpty() && daoUsuario.validarLogin(login)) {
						daoUsuario.salvar(usuario);
					}
					if (id != null && id.isEmpty() && pod) {
						daoUsuario.atualizar(usuario);
					}
					if (!pod) {
						request.setAttribute("user", usuario);
					}

					RequestDispatcher view = request.getRequestDispatcher("/cadastroUsuario.jsp");
					request.setAttribute("usuarios", daoUsuario.listar());
					request.setAttribute("msg", "Salvo com sucesso!");
					view.forward(request, response);
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

	private byte[] converteStreamParaByte(InputStream imagem) throws Exception {
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		int reads = imagem.read();
		while (reads != -1) {
			baos.write(reads);
			reads = imagem.read();
		}
		return baos.toByteArray();
	}
}
