package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import beans.BeanJSP;
import connection.SingleConnection;

public class DaoUsuario {
	private Connection connection;

	public DaoUsuario() {
		connection = SingleConnection.getConnection();
	}

	public void salvar(BeanJSP usuario) {
		try {
			String sql = "insert into usuario(login, senha, nome, fone, cep, rua, bairro, cidade, estado, ibge, foto, "
					+ "contenttype, pdf, contenttypepdf, minifoto, ativo, sexo, perfil) "
					+ "values(?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
			PreparedStatement insert = connection.prepareStatement(sql);
			insert.setString(1, usuario.getLogin());
			insert.setString(2, usuario.getSenha());
			insert.setString(3, usuario.getNome());
			insert.setString(4, usuario.getFone());
			insert.setString(5, usuario.getCep());
			insert.setString(6, usuario.getRua());
			insert.setString(7, usuario.getBairro());
			insert.setString(8, usuario.getCidade());
			insert.setString(9, usuario.getEstado());
			insert.setString(10, usuario.getIbge());
			insert.setString(11, usuario.getFoto());
			insert.setString(12, usuario.getContentType());
			insert.setString(13, usuario.getPdf());
			insert.setString(14, usuario.getCotentTypePdf());
			insert.setString(15, usuario.getMiniFoto());
			insert.setBoolean(16, usuario.isAtivo());
			insert.setString(17, usuario.getSexo());
			insert.setString(18, usuario.getPerfil());
			insert.execute();
			connection.commit();
		} catch (Exception e) {
			e.printStackTrace();
			try {
				connection.rollback();
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
		}

	}

	public List<BeanJSP> listar(String descricaoConsulta) throws SQLException {
		String sql = "SELECT * FROM usuario WHERE login <> 'admin' AND LOWER(nome) LIKE LOWER('%" + descricaoConsulta
				+ "%') ORDER BY nome";
		return consultarUsuarios(sql);
	}

	public List<BeanJSP> listar() throws Exception {
		String sql = "select * from usuario where login <> 'admin'";
		return consultarUsuarios(sql);
	}

	private List<BeanJSP> consultarUsuarios(String sql) throws SQLException {
		List<BeanJSP> listar = new ArrayList<BeanJSP>();
		PreparedStatement statement = connection.prepareStatement(sql);
		ResultSet resultSet = statement.executeQuery();
		while (resultSet.next()) {
			BeanJSP beanJSP = new BeanJSP();
			beanJSP.setId(resultSet.getLong("id"));
			beanJSP.setLogin(resultSet.getString("login"));
			beanJSP.setSenha(resultSet.getString("senha"));
			beanJSP.setNome(resultSet.getString("nome"));
			beanJSP.setFone(resultSet.getString("fone"));
			beanJSP.setCep(resultSet.getString("cep"));
			beanJSP.setRua(resultSet.getString("rua"));
			beanJSP.setBairro(resultSet.getString("bairro"));
			beanJSP.setCidade(resultSet.getString("cidade"));
			beanJSP.setEstado(resultSet.getString("estado"));
			beanJSP.setIbge(resultSet.getString("ibge"));
			beanJSP.setContentType(resultSet.getString("contenttype"));
			beanJSP.setMiniFoto(resultSet.getString("minifoto"));
			beanJSP.setCotentTypePdf(resultSet.getString("contenttypepdf"));
			beanJSP.setContentType(resultSet.getString("pdf"));
			beanJSP.setAtivo(resultSet.getBoolean("ativo"));
			beanJSP.setSexo(resultSet.getString("sexo"));
			beanJSP.setPerfil(resultSet.getString("perfil"));
			listar.add(beanJSP);
		}
		return listar;
	}

	public void delete(String id) {
		try {
			String sql = "delete from usuario where id = '" + id + "' and login <> 'admin'";
			PreparedStatement preparedStatement = connection.prepareStatement(sql);
			preparedStatement.execute();
			connection.commit();
		} catch (Exception e) {
			e.printStackTrace();
			try {
				connection.rollback();
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
		}
	}

	public BeanJSP consultar(String id) throws Exception {
		String sql = "select * from usuario where id = '" + id + "' and login <> 'admin'";
		PreparedStatement preparedStatement = connection.prepareStatement(sql);
		ResultSet resultSet = preparedStatement.executeQuery();
		if (resultSet.next()) {
			BeanJSP beanJSP = new BeanJSP();
			beanJSP.setId(resultSet.getLong("id"));
			beanJSP.setLogin(resultSet.getString("login"));
			beanJSP.setSenha(resultSet.getString("senha"));
			beanJSP.setNome(resultSet.getString("nome"));
			beanJSP.setFone(resultSet.getString("fone"));
			beanJSP.setCep(resultSet.getString("cep"));
			beanJSP.setRua(resultSet.getString("rua"));
			beanJSP.setBairro(resultSet.getString("bairro"));
			beanJSP.setCidade(resultSet.getString("cidade"));
			beanJSP.setEstado(resultSet.getString("estado"));
			beanJSP.setIbge(resultSet.getString("ibge"));
			beanJSP.setContentType(resultSet.getString("contenttype"));
			beanJSP.setFoto(resultSet.getString("foto"));
			beanJSP.setMiniFoto(resultSet.getString("minifoto"));
			beanJSP.setCotentTypePdf(resultSet.getString("contenttypepdf"));
			beanJSP.setContentType(resultSet.getString("pdf"));
			beanJSP.setAtivo(resultSet.getBoolean("ativo"));
			beanJSP.setSexo(resultSet.getString("sexo"));
			beanJSP.setPerfil(resultSet.getString("perfil"));
			return beanJSP;
		}
		return null;
	}

	public boolean validarLogin(String login) throws Exception {
		String sql = "select count(1) as qtd from usuario where login = '" + login + "'";
		PreparedStatement preparedStatement = connection.prepareStatement(sql);
		ResultSet resultSet = preparedStatement.executeQuery();
		if (resultSet.next()) {
			return resultSet.getInt("qtd") <= 0;
		}
		return false;
	}

	public boolean validarLoginUpdate(String login, String id) throws Exception {
		String sql = "select count(1) as qtd from usuario where login = '" + login + "' and id <>" + id;
		PreparedStatement preparedStatement = connection.prepareStatement(sql);
		ResultSet resultSet = preparedStatement.executeQuery();
		if (resultSet.next()) {
			return resultSet.getInt("qtd") <= 0;
		}
		return false;
	}

	public void atualizar(BeanJSP usuario) {
		try {
			StringBuilder sql = new StringBuilder();

			sql.append(" update usuario set login = ?, senha = ?, nome = ?, fone = ?, cep = ?, ");
			sql.append(" rua = ?, bairro = ?, cidade = ?,  estado = ?, ibge = ?, ativo=?, sexo=?, perfil=?");

			if (usuario.isAtualizarImagem()) {
				sql.append(" ,foto=?, contenttype=? ");
			}

			if (usuario.isAtualizarPdf()) {
				sql.append(" ,pdf=?, contenttypepdf=? ");
			}

			if (usuario.isAtualizarImagem()) {
				sql.append(" ,minifoto=? ");
			}
			sql.append(" where id = " + usuario.getId());

			PreparedStatement preparedStatement = connection.prepareStatement(sql.toString());
			preparedStatement.setString(1, usuario.getLogin());
			preparedStatement.setString(2, usuario.getSenha());
			preparedStatement.setString(3, usuario.getNome());
			preparedStatement.setString(4, usuario.getFone());
			preparedStatement.setString(5, usuario.getCep());
			preparedStatement.setString(6, usuario.getRua());
			preparedStatement.setString(7, usuario.getBairro());
			preparedStatement.setString(8, usuario.getCidade());
			preparedStatement.setString(9, usuario.getEstado());
			preparedStatement.setString(10, usuario.getIbge());
			preparedStatement.setBoolean(11, usuario.isAtivo());
			preparedStatement.setString(12, usuario.getSexo());
			preparedStatement.setString(13, usuario.getPerfil());

			if (usuario.isAtualizarImagem()) {
				preparedStatement.setString(14, usuario.getFoto());
				preparedStatement.setString(15, usuario.getContentType());
			}

			if (usuario.isAtualizarPdf()) {
				if (usuario.isAtualizarPdf() && !usuario.isAtualizarImagem()) {
					preparedStatement.setString(14, usuario.getPdf());
					preparedStatement.setString(15, usuario.getCotentTypePdf());
				} else {
					preparedStatement.setString(16, usuario.getPdf());
					preparedStatement.setString(17, usuario.getCotentTypePdf());
				}
			} else {
				if (usuario.isAtualizarImagem()) {
					preparedStatement.setString(16, usuario.getMiniFoto());
				}
			}

			if (usuario.isAtualizarImagem() && usuario.isAtualizarPdf()) {
				preparedStatement.setString(18, usuario.getMiniFoto());
			}
			preparedStatement.executeUpdate();
			connection.commit();
		} catch (Exception e) {
			e.printStackTrace();
			try {
				connection.rollback();
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
		}
	}
}