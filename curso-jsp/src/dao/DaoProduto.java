package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import beans.Categoria;
import beans.Produto;
import connection.SingleConnection;

public class DaoProduto {
	private Connection connection;

	public DaoProduto() {
		connection = SingleConnection.getConnection();
	}

	public void salvar(Produto produto) {
		String sql = "INSERT INTO produto (nome, quantidade, valor, categoria_id) VALUES (?, ?, ?, ?)";
		try {
			PreparedStatement insert = connection.prepareStatement(sql);
			insert.setString(1, produto.getNome());
			insert.setDouble(2, produto.getQuantidade());
			insert.setDouble(3, produto.getValor());
			insert.setLong(4, produto.getCategoria_id());
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

	public List<Produto> listar() throws Exception {
		List<Produto> produtos = new ArrayList<Produto>();
		try {
			String sql = "SELECT * FROM produto";
			PreparedStatement statement = connection.prepareStatement(sql);
			ResultSet resultSet = statement.executeQuery();
			while (resultSet.next()) {
				Produto produto = new Produto();
				produto.setId(resultSet.getLong("id"));
				produto.setNome(resultSet.getString("nome"));
				produto.setQuantidade(resultSet.getDouble("quantidade"));
				produto.setValor(resultSet.getDouble("valor"));
				produto.setCategoria_id(resultSet.getLong("categoria"));
				produtos.add(produto);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return produtos;
	}
	
	public List<Categoria> listaCategorias() throws Exception{
		List<Categoria> retorno = new ArrayList<Categoria>();
		String sql = "select * from categoria";
		PreparedStatement statement = connection.prepareStatement(sql);
		ResultSet resultSet = statement.executeQuery();
		while(resultSet.next()) {
			Categoria categoria = new Categoria();
			categoria.setId(resultSet.getLong("id"));
			categoria.setNome(resultSet.getString("nome"));
			retorno.add(categoria);
		}
		return retorno;
	}

	public void delete(String id) {
		try {
			String sql = "DELETE FROM produto WHERE id = '" + id + "'";
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

	public Produto consultar(String id) throws Exception {
		String sql = "SELECT * FROM produto WHERE id = '" + id + "'";
		PreparedStatement statement = connection.prepareStatement(sql);
		ResultSet resultSet = statement.executeQuery();
		if (resultSet.next()) {
			Produto produto = new Produto();
			produto.setId(resultSet.getLong("id"));
			produto.setNome(resultSet.getString("nome"));
			produto.setQuantidade(resultSet.getDouble("quantidade"));
			produto.setValor(resultSet.getDouble("valor"));
			produto.setCategoria_id(resultSet.getLong("categoria"));
			return produto;
		}
		return null;
	}

	public boolean validarNome(String nome) throws Exception {
		String sql = "SELECT COUNT(1) AS qtde FROM produto WHERE nome = '" + nome + "'";
		PreparedStatement statement = connection.prepareStatement(sql);
		ResultSet resultSet = statement.executeQuery();
		if (resultSet.next()) {
			return resultSet.getInt("qtd") <= 0;
		}
		return false;
	}

	public void atualizar(Produto produto) {
		try {
			String sql = "UPDATE produto SET nome = ?, valor = ?, quantidade = ? WHERE id = " + produto.getId();
			PreparedStatement statement = connection.prepareStatement(sql);
			statement.setString(1, produto.getNome());
			statement.setDouble(2, produto.getQuantidade());
			statement.setDouble(3, produto.getValor());
			statement.setLong(4, produto.getCategoria_id());
			statement.execute();
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

	public boolean NomeVazio(String nome) {

		return false;
	}
}
