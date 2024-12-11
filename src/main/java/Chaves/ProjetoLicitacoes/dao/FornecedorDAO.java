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


/**
 * Classe que implementa as operações de banco de dados para a entidade Fornecedor.
 */
public class FornecedorDAO implements Interface_DAO<Fornecedor> {
	private static final String INSERT_QUERY = "INSERT INTO fornecedores (nome, cnpj, email, telefone) VALUES (?,?,?,?)";
    private static final String DELETE_QUERY = "DELETE FROM fornecedores WHERE id = ?";
    private static final String UPDATE_QUERY = "UPDATE fornecedores SET nome = ?, cnpj = ?, email = ?, telefone = ? WHERE id = ?";
    private static final String SELECT_QUERY = "SELECT * FROM fornecedores WHERE f.id = ?";
    private static final String LIST_QUERY = "SELECT * FROM fornecedores LIMIT ? OFFSET ?";

	/**
     * Insere um novo fornecedor no banco de dados.
     *
     * @param fornecedor O fornecedor a ser inserido.
     * @return true se a inserção for bem-sucedida, false caso contrário.
     */
	@Override
	public boolean insert(Fornecedor fornecedor) {
		if(fornecedor instanceof Fornecedor) {
			try (Connection con = ConnectionFactory.getConnection();
				PreparedStatement pstm = con.prepareStatement(INSERT_QUERY)) {
                pstm.setString(1, fornecedor.getNome());
                pstm.setString(2, fornecedor.getCnpj());
                pstm.setString(3, fornecedor.getEmail());
                pstm.setString(4, fornecedor.getTelefone()); 
				pstm.execute();
				return true;
			} catch (SQLException e) {
				throw new RuntimeException("Erro ao inserir fornecedor", e);
			}
		}
		return false;
	}
	
	/**
     * Exclui um fornecedor do banco de dados pelo ID.
     *
     * @param id O ID do fornecedor a ser excluído.
     * @return true se a exclusão for bem-sucedida, false caso contrário.
     */
	@Override
    public boolean delete(int id) {
        try (Connection con = ConnectionFactory.getConnection();
             PreparedStatement pstm = con.prepareStatement(DELETE_QUERY)) {
            pstm.setInt(1, id);
            return pstm.executeUpdate() > 0;
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao excluir fornecedor", e);
        }
    }
	
	/**
     * Atualiza as informações de um fornecedor no banco de dados.
     *
     * @param fornecedor O fornecedor com as informações atualizadas.
     * @return true se a atualização for bem-sucedida, false caso contrário.
     */
	@Override
    public boolean update(Fornecedor fornecedor) {
        try (Connection con = ConnectionFactory.getConnection()) {
            // Atualiza o fornecedor
            try (PreparedStatement pstm = con.prepareStatement(UPDATE_QUERY)) {
                pstm.setString(1, fornecedor.getNome());
                pstm.setString(2, fornecedor.getCnpj());
                pstm.setString(3, fornecedor.getEmail());
                pstm.setString(4, fornecedor.getTelefone());
                pstm.setInt(5, fornecedor.getId());
                pstm.executeUpdate();
                return true;
            }
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao atualizar fornecedor", e);
        }
    }
	
	/**
     * Lista os fornecedores do banco de dados com paginação.
     *
     * @param limit  O número máximo de fornecedores a serem retornados.
     * @param offset O deslocamento inicial para a listagem.
     * @return Uma lista de fornecedores.
     */
	@Override
    public List<Fornecedor> list(int limit, int offset) {
        List<Fornecedor> fornecedores = new ArrayList<>();
        try (Connection con = ConnectionFactory.getConnection();
             PreparedStatement pstm = con.prepareStatement(LIST_QUERY)) {

            pstm.setInt(1, limit);
            pstm.setInt(2, offset);
            try (ResultSet resultSet = pstm.executeQuery()) {
                while (resultSet.next()) {
                    fornecedores.add(mapResultSetToFornecedor(resultSet));
                }
            }
        } catch (SQLException | IOException e) {
            throw new RuntimeException("Erro ao listar fornecedores", e);
        }
        return fornecedores;
    }
	
	/**
     * Obtém um fornecedor do banco de dados pelo ID.
     *
     * @param id O ID do fornecedor a ser obtido.
     * @return O fornecedor correspondente ao ID, ou null se não encontrado.
     */
	@Override
    public Fornecedor get(int id) {
        try (Connection con = ConnectionFactory.getConnection();
             PreparedStatement pstm = con.prepareStatement(SELECT_QUERY)) {

            pstm.setInt(1, id);
            try (ResultSet resultSet = pstm.executeQuery()) {
                if (resultSet.next()) {
                    return mapResultSetToFornecedor(resultSet);
                }
            }
        } catch (SQLException | IOException e) {
            throw new RuntimeException("Erro ao obter fornecedor", e);
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
    private Fornecedor mapResultSetToFornecedor(ResultSet resultSet) throws SQLException, IOException {
        int id = resultSet.getInt("id");
        String nome = resultSet.getString("nome");
        String cnpj = resultSet.getString("cnpj");
        String email = resultSet.getString("email");
        String telefone = resultSet.getString("telefone");
        return new Fornecedor(id, nome, cnpj, email, telefone);
    }
}
