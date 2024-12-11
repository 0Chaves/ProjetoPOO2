package Chaves.ProjetoLicitacoes.controller;

import java.io.IOException;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

import Chaves.ProjetoLicitacoes.dao.FornecedorDAO;
import Chaves.ProjetoLicitacoes.model.Fornecedor;

public class TelaEditarFornecedorController {
    private Stage stage;
    private Scene scene;
    private BorderPane root;
    
    private Fornecedor fornecedor;
    private FornecedorDAO fornecedorDAO = new FornecedorDAO();
    
    @FXML
    private TextArea inputNome;
    
    @FXML
    private TextArea inputCnpj;
    
    @FXML
    private TextArea inputEmail;
    
    @FXML
    private TextArea inputTelefone;
    
    @FXML
    private Label labelMensagem;
    
    @FXML
    private Button btnVoltar;
    
    @FXML
    private Button btnSalvar;

    public void setFornecedor(Fornecedor fornecedor) {
        this.fornecedor = fornecedor;
        preencherCampos();
    }

    private void preencherCampos() {
        if (fornecedor != null) {
            inputNome.setText(fornecedor.getNome());
            inputCnpj.setText(fornecedor.getCnpj());
            inputEmail.setText(fornecedor.getEmail());
            inputTelefone.setText(fornecedor.getTelefone());
        }
    }

    @FXML
    void salvar(ActionEvent event) {
        try {
            Fornecedor fornecedorAtualizado = new Fornecedor(
                fornecedor.getId(),
                inputNome.getText(),
                inputCnpj.getText(),
                inputEmail.getText(),
                inputTelefone.getText()
            );

            if (fornecedorDAO.update(fornecedorAtualizado)) {
                voltar(event);
            } else {
                labelMensagem.setText("Erro ao atualizar fornecedor");
            }
        } catch (IllegalArgumentException | IOException e) {
            labelMensagem.setText(e.getMessage());
        }
    }

    @FXML
    void voltar(ActionEvent event) {
        try {
            root = FXMLLoader.load(getClass().getResource("/TelaListaFornecedores.fxml"));
            scene = new Scene(root);
            stage = (Stage)((Node)event.getSource()).getScene().getWindow();
            stage.setScene(scene);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
            labelMensagem.setText("Erro ao voltar: " + e.getMessage());
        }
    }
}