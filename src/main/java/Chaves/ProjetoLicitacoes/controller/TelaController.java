package Chaves.ProjetoLicitacoes.controller;

import java.io.IOException;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.scene.Node;

public class TelaController {
	private Stage stage;
	private Scene scene;
	private VBox root;
	
    @FXML
    private Button Listar_Itens;

    @FXML
    private Button Listar_fornecedores;
    
    @FXML
    private Button trocarTela_InserirFornecedor;

    @FXML
    private Button trocarTela_InserirItem;

    @FXML
    void trocar_tela_inserir_fornecedor(ActionEvent event) {
    	try {
			root = (VBox)FXMLLoader.load(getClass().getResource("/TelaInserirFornecedor.fxml"));
			scene = new Scene(root);
			stage = (Stage)((Node)event.getSource()).getScene().getWindow();
			stage.setScene(scene);
			stage.show();
		} catch (IOException e) {
			
			e.printStackTrace();
		}
    }

    @FXML
    void trocar_tela_inserir_item(ActionEvent event) {
    	try {
    		root = (VBox)FXMLLoader.load(getClass().getResource("/TelaInserirItem.fxml"));
    		scene = new Scene(root);
    		stage = (Stage)((Node)event.getSource()).getScene().getWindow();
    		stage.setScene(scene);
			stage.show();
    	} catch (IOException e) {
    	 	e.printStackTrace();
    	}
   }

	@FXML
	void trocarTela_ListaFornecedores(ActionEvent event) {
		try {
			BorderPane root = FXMLLoader.load(getClass().getResource("/TelaListaFornecedores.fxml"));
			scene = new Scene(root);
			stage = (Stage)((Node)event.getSource()).getScene().getWindow();
			stage.setScene(scene);
			stage.show();
		} catch (IOException e) {
			e.printStackTrace();
			Alert alert = new Alert(AlertType.ERROR);
			alert.setTitle("Erro");
			alert.setHeaderText("Erro ao carregar tela");
			alert.setContentText("Não foi possível carregar a tela de listagem de fornecedores.");
			alert.showAndWait();
		}
	}
	@FXML
	void trocarTela_ListaItens(ActionEvent event) {
		try {
			BorderPane root = FXMLLoader.load(getClass().getResource("/TelaListaItens.fxml"));
			scene = new Scene(root);
			stage = (Stage)((Node)event.getSource()).getScene().getWindow();
			stage.setScene(scene);
			stage.show();
		} catch (IOException e) {
			e.printStackTrace();
			Alert alert = new Alert(AlertType.ERROR);
			alert.setTitle("Erro");
			alert.setHeaderText("Erro ao carregar tela");
			alert.setContentText("Não foi possível carregar a tela de listagem de itens.");
			alert.showAndWait();
		}
	}
    
}
