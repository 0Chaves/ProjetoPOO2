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

/**
 * Classe que implementa as operações de banco de dados para a entidade Item.
 */
public class ItemDAO implements Interface_DAO<Item> {

	private static final String INSERT_QUERY = "INSERT INTO produtos (descricao, pregao, valor_unitario, qtd_max, qtd_solicitada, id_fornecedor) VALUES (?,?,?,?,?,?)";
	private static final String DELETE_QUERY = "DELETE FROM produtos WHERE id = ?";
    private static final String UPDATE_QUERY = "UPDATE produtos SET descricao = ?, valor_unitario = ?, id_fornecedor = ? WHERE id = ?";
    private static final String SELECT_QUERY = "SELECT * FROM produtos p INNER JOIN fornecedores f ON p.id_fornecedor = f.id WHERE id = ?";
    private static final String LIST_QUERY = "SELECT * FROM produtos p INNER JOIN fornecedores f ON p.id_fornecedor = f.id LIMIT ? OFFSET ?";

    /**
     * Insere um novo item no banco de dados.
     *
     * @param item O item a ser inserido.
     * @return true se a inserção for bem-sucedida, false caso contrário.
     */
    @Override
    public boolean insert(Item item) {
		try (Connection con = ConnectionFactory.getConnection();
			 PreparedStatement pstm = con.prepareStatement(INSERT_QUERY)) {
			pstm.setString(1, item.getDescricao());
			pstm.setString(2, item.getPregao());
			pstm.setDouble(3, item.getValorUnitario());
			pstm.setInt(4, item.getQuantidadeMaxima());
			pstm.setInt(5, item.getQuantidadeMaxima()); // Initially, qtd_solicitada = qtd_max
			pstm.setInt(6, item.getFornecedor().getId());
			pstm.execute();
			return true;
		} catch (SQLException e) {
			throw new RuntimeException("Erro ao inserir item", e);
		}
	}

    /**
     * Exclui um item do banco de dados pelo ID.
     *
     * @param id O ID do item a ser excluído.
     * @return true se a exclusão for bem-sucedida, false caso contrário.
     */
    @Override
    public boolean delete(int id) {
        try (Connection con = ConnectionFactory.getConnection();
             PreparedStatement pstm = con.prepareStatement(DELETE_QUERY)) {
            pstm.setInt(1, id);
            return pstm.executeUpdate() > 0;
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao excluir item", e);
        }
    }

    /**
     * Atualiza as informações de um item no banco de dados.
     *
     * @param item O item com as informações atualizadas.
     * @return true se a atualização for bem-sucedida, false caso contrário.
     */
    @Override
    public boolean update(Item item) {
        try (Connection con = ConnectionFactory.getConnection();
             PreparedStatement pstm = con.prepareStatement(UPDATE_QUERY)) {
            pstm.setString(1, item.getDescricao());
            pstm.setDouble(2, item.getValorUnitario());
            pstm.setInt(3, item.getFornecedor().getId());
            pstm.setInt(4, item.getId());
            pstm.executeUpdate();
            return true;
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao atualizar item", e);
        }
    }

    /**
     * Lista os itens do banco de dados com paginação.
     *
     * @param limit  O número máximo de itens a serem retornados.
     * @param offset O deslocamento inicial para a listagem.
     * @return Uma lista de itens.
     */
    @Override
    public List<Item> list(int limit, int offset) {
        List<Item> itens = new ArrayList<>();
        try (Connection con = ConnectionFactory.getConnection();
             PreparedStatement pstm = con.prepareStatement(LIST_QUERY)) {
            pstm.setInt(1, limit);
            pstm.setInt(2, offset);
            try (ResultSet resultSet = pstm.executeQuery()) {
                while (resultSet.next()) {
                    itens.add(mapResultSetToItem(resultSet));
                }
            }
        } catch (SQLException | IOException e) {
            throw new RuntimeException("Erro ao listar itens", e);
        }
        return itens;
    }

    /**
     * Obtém um item do banco de dados pelo ID.
     *
     * @param id O ID do item a ser obtido.
     * @return O item correspondente ao ID, ou null se não encontrado.
     */
    @Override
    public Item get(int id) {
        try (Connection con = ConnectionFactory.getConnection();
             PreparedStatement pstm = con.prepareStatement(SELECT_QUERY)) {
            pstm.setInt(1, id);
            try (ResultSet resultSet = pstm.executeQuery()) {
                if (resultSet.next()) {
                    return mapResultSetToItem(resultSet);
                }
            }
        } catch (SQLException | IOException e) {
            throw new RuntimeException("Erro ao obter item", e);
        }
        return null;
    }

    /**
     * Mapeia um ResultSet para um objeto Item.
     *
     * @param resultSet O ResultSet a ser mapeado.
     * @return O objeto Item correspondente.
     * @throws SQLException Se ocorrer um erro ao acessar o ResultSet.
     * @throws IOException  Se ocorrer um erro ao criar o objeto Item.
     */
    private Item mapResultSetToItem(ResultSet resultSet) throws SQLException, IOException {
        int id = resultSet.getInt("id");
        String descricao = resultSet.getString("descricao");
        String pregao = resultSet.getString("pregao");
        int quantidadeMaxima = resultSet.getInt("quantidade_maxima");
        int quantidadeSolicitada = resultSet.getInt("quantidade_solicitada");
        double valorUnitario = resultSet.getDouble("valor_unitario");
        int idFornecedor = resultSet.getInt("id_fornecedor");
        String nome = resultSet.getString("nome");
        String cnpj = resultSet.getString("cnpj");
        String email = resultSet.getString("email");
        String telefone = resultSet.getString("telefone");
        Fornecedor fornecedor = new Fornecedor(idFornecedor, nome, cnpj, email, telefone);
        return new Item(id, descricao, pregao, quantidadeMaxima, quantidadeSolicitada, valorUnitario, fornecedor);
    }
}