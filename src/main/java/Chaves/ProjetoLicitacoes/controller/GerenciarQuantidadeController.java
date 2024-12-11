package Chaves.ProjetoLicitacoes.controller;

import java.io.IOException;
import Chaves.ProjetoLicitacoes.dao.ItemDAO;
import Chaves.ProjetoLicitacoes.model.Item;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

public class GerenciarQuantidadeController {
    
    private Stage stage;
    private Scene scene;
    private AnchorPane root;
    
    private Item item;
    private ItemDAO itemDAO = new ItemDAO();
    
    @FXML
    private Label labelDescricao;
    
    @FXML
    private Label labelQtdMax;
    
    @FXML
    private TextField inputQtdSolicitada;
    
    @FXML
    private Label labelMensagem;

    public void setItem(Item item) {
        this.item = item;
        preencherCampos();
    }

    private void preencherCampos() {
        if (item != null) {
            labelDescricao.setText(item.getDescricao());
            labelQtdMax.setText(String.valueOf(item.getQuantidadeMaxima()));
            inputQtdSolicitada.setText(String.valueOf(item.getQuantidadeSolicitada()));
        }
    }

    @FXML
    void salvar(ActionEvent event) {
        try {
            int novaSolicitacao = Integer.parseInt(inputQtdSolicitada.getText().trim());
            
            if (novaSolicitacao < 0) {
                labelMensagem.setText("Quantidade não pode ser negativa");
                return;
            }
            
            if (novaSolicitacao > item.getQuantidadeMaxima()) {
                labelMensagem.setText("Quantidade não pode exceder o máximo de " + 
                                    item.getQuantidadeMaxima());
                return;
            }
            
            if (itemDAO.updateQuantidadeSolicitada(item.getId(), novaSolicitacao)) {
                voltar(event);
            } else {
                labelMensagem.setText("Erro ao atualizar quantidade");
            }
        } catch (NumberFormatException e) {
            labelMensagem.setText("Digite um número válido");
        } catch (Exception e) {
            labelMensagem.setText("Erro: " + e.getMessage());
        }
    }

    @FXML
    void voltar(ActionEvent event) {
        try {
            BorderPane root = FXMLLoader.load(getClass().getResource("/TelaListaItens.fxml"));
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