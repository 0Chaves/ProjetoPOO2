package Chaves.ProjetoLicitacoes.controller;

import java.io.IOException;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

public class TelaInserirItemController {
	private Stage stage;
	private Scene scene;
	private AnchorPane root;
	

    @FXML
    private Button botao_voltar;

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

}
