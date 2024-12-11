package Chaves.ProjetoLicitacoes.controller;

import java.io.IOException;

import Chaves.ProjetoLicitacoes.dao.FornecedorDAO;
import Chaves.ProjetoLicitacoes.model.Fornecedor;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.stage.Stage;

import javafx.scene.layout.AnchorPane;

public class TelaFornecedorController {

	private Stage stage;
	private Scene scene;
	private AnchorPane root;
	
    @FXML
    private Button botao_enviar;

    @FXML
    private Button botao_voltar;

    @FXML
    private TextArea inputCnpj;

    @FXML
    private TextArea inputEmail;

    @FXML
    private TextArea inputNome;

    @FXML
    private TextArea inputTelefone;

    @FXML
    void voltar(ActionEvent event) {
    	try {
			root = (AnchorPane)FXMLLoader.load(getClass().getResource("/TelaInicial.fxml"));
			scene = new Scene(root);
			stage = (Stage)((Node)event.getSource()).getScene().getWindow();
			stage.setScene(scene);
			stage.show();
		} catch (IOException e) {
			
			e.printStackTrace();
		}
    }
    
    @FXML
void enviar(ActionEvent event) {
    try {
        // Cria uma instância de FornecedorDAO
        FornecedorDAO fornecedorDAO = new FornecedorDAO();
        
        // Cria um novo fornecedor com os dados dos campos
        Fornecedor novoFornecedor = new Fornecedor(
            inputNome.getText(),
            inputCnpj.getText(), 
            inputEmail.getText(),
            inputTelefone.getText()
        );
        
        // Tenta inserir o fornecedor no banco
        boolean sucesso = fornecedorDAO.insert(novoFornecedor);
        
        if (sucesso) {
            // Limpa os campos após sucesso
            limparCampos();
            // Volta para a tela inicial
            voltar(event);
        } else {
            // TODO: Mostrar mensagem de erro em caso de falha
            System.err.println("Erro ao inserir fornecedor");
        }
        
    } catch (IllegalArgumentException e) {
        // Tratamento de erro de validação dos campos
        System.err.println("Erro de validação: " + e.getMessage());
        // TODO: Mostrar mensagem de erro para o usuário
    } catch (IOException e) {
        // Tratamento de erro de IO
        System.err.println("Erro de IO: " + e.getMessage());
        e.printStackTrace();
    } catch (RuntimeException e) {
        // Tratamento de erro do banco de dados
        System.err.println("Erro de banco de dados: " + e.getMessage());
        e.printStackTrace();
    }
}

// Método auxiliar para limpar os campos do formulário
private void limparCampos() {
    inputNome.clear();
    inputCnpj.clear();
    inputEmail.clear();
    inputTelefone.clear();
}
}
