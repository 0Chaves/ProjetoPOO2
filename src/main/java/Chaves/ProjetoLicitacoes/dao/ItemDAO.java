package Chaves.ProjetoLicitacoes.dao;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import Chaves.ProjetoLicitacoes.factory.ConnectionFactory;
import Chaves.ProjetoLicitacoes.model.Fornecedor;
import Chaves.ProjetoLicitacoes.model.Item;


public class ItemDAO implements Interface_DAO<Item> {
	
	private static final String INSERT_QUERY = "INSERT INTO produtos (descricao, valor_unitario, id_fornecedor) VALUES (?,?,?)";
    private static final String DELETE_QUERY = "DELETE FROM produtos WHERE id = ?";
    private static final String UPDATE_QUERY = "UPDATE produtos SET descricao = ?, valor_unitario = ?, id_fornecedor = ? WHERE id = ?";
    private static final String SELECT_QUERY = "SELECT * FROM produtos p INNER JOIN fornecedores f ON p.id_fornecedor = f.id WHERE id = ?";
    private static final String LIST_QUERY = "SELECT * FROM produtos p INNER JOIN fornecedores f ON p.id_fornecedor = f.id LIMIT ? OFFSET ?";

	@Override
	public boolean insert(Item object) {
		if(object instanceof Item) {
			Connection con = ConnectionFactory.getConnection();
			try {
				PreparedStatement pstm = con.prepareStatement(INSERT_QUERY);
				pstm.setString(1, object.getDescricao());
				pstm.setDouble(2, object.getValor_unitario());
				pstm.setInt(3, object.getFornecedor().getId());
				pstm.execute();
			}catch(SQLException e) {
				throw new RuntimeException(e);
			}
		}
		return false;
	}

	@Override
	public boolean delete(int id) {
		Connection con = ConnectionFactory.getConnection();
		try {
			PreparedStatement pstm = con.prepareStatement(DELETE_QUERY);
			pstm.setInt(1, id);
			return pstm.execute();
		}catch(SQLException e) {
			e.printStackTrace();
		}
		return false;
	}

	@Override
	public boolean update(Item object) {
		Connection con = ConnectionFactory.getConnection();
		try {
			PreparedStatement pstm = con.prepareStatement(UPDATE_QUERY);
			pstm.setString(1, object.getDescricao());
			pstm.setDouble(2, object.getValor_unitario());
			pstm.setInt(3, object.getFornecedor().getId());
			pstm.setInt(4, object.getId());
			pstm.execute();
		}catch(SQLException e) {
			throw new RuntimeException(e);
		}
		return false;
	}

	@Override
	public List<Item> list(int limit, int offset) {
		List<Item> itens = new ArrayList<>();
        try (Connection con = ConnectionFactory.getConnection();
            PreparedStatement pstm = con.prepareStatement(LIST_QUERY)) {
            pstm.setInt(1, limit);
            pstm.setInt(2, offset);
            try (ResultSet resultSet = pstm.executeQuery()) {
                while (resultSet.next()) {
                    itens.add(mapResultSetToFornecedor(resultSet));
                }
            }
        } catch (SQLException | IOException e) {
            throw new RuntimeException("Erro ao listar fornecedores", e);
        }
        return itens;
	}

	@Override
	public Item get(int id) {
		Connection con = ConnectionFactory.getConnection();
		try {
			PreparedStatement pstm = con.prepareStatement(SELECT_QUERY);
			pstm.setInt(1, id);
			ResultSet result = pstm.executeQuery();
			if(result.next()) {
				int id_item = result.getInt("id");
				String descricao = result.getString("descricao");
				double valor_unitario = result.getDouble("valor_unitario");
				int id_fornecedor = result.getInt("id_fornecedor");
//				Item item = new Item(id_item, descricao, valor_unitario, null, null);
//				return item;
			}
		}catch(SQLException e) {
			throw new RuntimeException(e);
		}
		return null;
	}
	
	/**
     * Mapeia um ResultSet para um objeto Fornecedor.
     *
     * @param resultSet O ResultSet a ser mapeado.
     * @return O objeto Fornecedor correspondente.
     * @throws SQLException Se ocorrer um erro ao acessar o ResultSet.
     * @throws IOException  Se ocorrer um erro ao criar o objeto Fornecedor.
     */
    private Item mapResultSetToFornecedor(ResultSet resultSet) throws SQLException, IOException {
        int id = resultSet.getInt("id");
        String descricao = resultSet.getString("descricao");
        String pregao = resultSet.getString("pregao");
        int quantidade_maxima = resultSet.getInt("quantidade_maxima");
        int quantidade_solicitada = resultSet.getInt("quantidade_solicitada");
        double valor_unitario = resultSet.getDouble("valor_unitario");
        int id_fornecedor = resultSet.getInt("id_fornecedor");
        String nome = resultSet.getString("nome");
        String cnpj = resultSet.getString("cnpj");
        String email = resultSet.getString("email");
        String telefone = resultSet.getString("telefone");
        Fornecedor fornecedor = new Fornecedor(id_fornecedor, nome, cnpj, email, telefone);
        return new Item(id, descricao, pregao, quantidade_maxima, quantidade_solicitada, valor_unitario, fornecedor);
    }
}
