package Chaves.ProjetoLicitacoes.controller;

import java.io.IOException;

import Chaves.ProjetoLicitacoes.model.Fornecedor;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.input.InputMethodEvent;
import javafx.stage.Stage;
import javafx.scene.control.Label;

import javafx.scene.layout.AnchorPane;
import javafx.scene.input.KeyEvent;

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
			root = (AnchorPane)FXMLLoader.load(getClass().getResource("/Chaves/ProjetoLicitacoes/views/TelaInicial.fxml"));
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
    		Fornecedor f = new Fornecedor(inputNome.getText(), inputCnpj.getText(), inputEmail.getText(), inputTelefone.getText());
    	}catch(IOException e) {
    		e.printStackTrace();
    	}
    }
}
