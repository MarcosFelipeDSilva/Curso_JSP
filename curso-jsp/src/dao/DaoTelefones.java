package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import beans.Telefones;
import connection.SingleConnection;

public class DaoTelefones {
	private Connection connection;

	public DaoTelefones() {
		connection = SingleConnection.getConnection();
	}

	public void salvar(Telefones telefone) {
		String sql = "INSERT INTO telefone (numero, tipo, usuario) VALUES (?, ?, ?)";
		try {
			PreparedStatement insert = connection.prepareStatement(sql);
			insert.setString(1, telefone.getNumero());
			insert.setString(2, telefone.getTipo());
			insert.setLong(3, telefone.getUsuario());
			insert.execute();
			connection.commit();
		} catch (SQLException e) {
			e.printStackTrace();
			try {
				connection.rollback();
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
		}
	}

	public List<Telefones> listar(long user) throws Exception {
		List<Telefones> produtos = new ArrayList<Telefones>();
		try {
			String sql = "SELECT * FROM telefone where usuario = " + user;
			PreparedStatement statement = connection.prepareStatement(sql);
			ResultSet resultSet = statement.executeQuery();
			while (resultSet.next()) {
				Telefones telefone = new Telefones();
				telefone.setId(resultSet.getLong("id"));
				telefone.setNumero(resultSet.getString("numero"));
				telefone.setTipo(resultSet.getString("tipo"));
				telefone.setUsuario(resultSet.getLong("usuario"));
				produtos.add(telefone);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return produtos;
	}

	public void delete(String id) {
		try {
			String sql = "DELETE FROM telefone WHERE id = '" + id + "'";
			PreparedStatement statement = connection.prepareStatement(sql);
			statement.execute();
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
