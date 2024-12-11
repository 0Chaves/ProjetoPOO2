package Chaves.ProjetoLicitacoes.controller;

import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

import Chaves.ProjetoLicitacoes.dao.FornecedorDAO;
import Chaves.ProjetoLicitacoes.dao.ItemDAO;
import Chaves.ProjetoLicitacoes.model.Fornecedor;
import Chaves.ProjetoLicitacoes.model.Item;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

public class TelaEditarItemController implements Initializable {
    
    private Stage stage;
    private Scene scene;
    private BorderPane root;
    
    private Item item;
    private ItemDAO itemDAO = new ItemDAO();
    
    @FXML
    private TextArea inputDescricao;
    
    @FXML
    private TextArea inputPregao;
    
    @FXML
    private TextArea inputValorUnitario;
    
    @FXML
    private TextArea inputQuantidadeMaxima;
    
    @FXML
    private ChoiceBox<Fornecedor> choiceFornecedor;
    
    @FXML
    private Label labelMensagem;
    
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        carregarFornecedores();
    }
    
    private void carregarFornecedores() {
        try {
            FornecedorDAO fornecedorDAO = new FornecedorDAO();
            List<Fornecedor> fornecedores = fornecedorDAO.list(100, 0);
            ObservableList<Fornecedor> observableFornecedores = FXCollections.observableArrayList(fornecedores);
            choiceFornecedor.setItems(observableFornecedores);
        } catch (Exception e) {
            labelMensagem.setText("Erro ao carregar fornecedores: " + e.getMessage());
        }
    }

    public void setItem(Item item) {
        this.item = item;
        preencherCampos();
    }

    private void preencherCampos() {
        if (item != null) {
            inputDescricao.setText(item.getDescricao());
            inputPregao.setText(item.getPregao());
            inputValorUnitario.setText(String.valueOf(item.getValorUnitario()));
            inputQuantidadeMaxima.setText(String.valueOf(item.getQuantidadeMaxima()));
            choiceFornecedor.setValue(item.getFornecedor());
        }
    }

    private boolean validateInputs() {
        try {
            labelMensagem.setText("");

            if (inputDescricao.getText().trim().isEmpty()) {
                throw new IllegalArgumentException("Descrição não pode estar vazia");
            }

            String pregao = inputPregao.getText().trim();
            if (!pregao.matches("\\d{2}/\\d{4}")) {
                throw new IllegalArgumentException("Pregão deve estar no formato XX/YYYY");
            }

            String valorStr = inputValorUnitario.getText().trim().replace(",", ".");
            double valor = Double.parseDouble(valorStr);
            if (valor <= 0) {
                throw new IllegalArgumentException("Valor unitário deve ser maior que zero");
            }

            String qtdStr = inputQuantidadeMaxima.getText().trim();
            int qtd = Integer.parseInt(qtdStr);
            if (qtd <= 0) {
                throw new IllegalArgumentException("Quantidade máxima deve ser maior que zero");
            }

            if (choiceFornecedor.getValue() == null) {
                throw new IllegalArgumentException("Selecione um fornecedor");
            }

            return true;

        } catch (NumberFormatException e) {
            labelMensagem.setText("Erro de formato: Certifique-se que os campos numéricos estão preenchidos corretamente");
            return false;
        } catch (IllegalArgumentException e) {
            labelMensagem.setText(e.getMessage());
            return false;
        }
    }

    @FXML
    void salvar(ActionEvent event) {
        try {
            if (!validateInputs()) {
                return;
            }

            double valorUnitario = Double.parseDouble(inputValorUnitario.getText().trim().replace(",", "."));
            int quantidadeMaxima = Integer.parseInt(inputQuantidadeMaxima.getText().trim());

            Item itemAtualizado = new Item(
                item.getId(),
                inputDescricao.getText().trim(),
                inputPregao.getText().trim(),
                quantidadeMaxima,
                item.getQuantidadeSolicitada(),
                valorUnitario,
                choiceFornecedor.getValue()
            );

            if (itemDAO.update(itemAtualizado)) {
                voltar(event);
            } else {
                labelMensagem.setText("Erro ao atualizar item");
            }
        } catch (Exception e) {
            labelMensagem.setText("Erro ao atualizar item: " + e.getMessage());
        }
    }

    @FXML
    void voltar(ActionEvent event) {
        try {
            root = FXMLLoader.load(getClass().getResource("/TelaListaItens.fxml"));
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