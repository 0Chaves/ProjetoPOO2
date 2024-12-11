package Chaves.ProjetoLicitacoes.controller;

import java.io.IOException;
import java.util.List;

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
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import java.net.URL;
import java.util.ResourceBundle;

public class TelaInserirItemController implements Initializable {
    private Stage stage;
    private Scene scene;
    private AnchorPane root;

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
    private Button botao_voltar;
    
    @FXML
    private Button botaoEnviar;
    
    @FXML
    private Label labelMensagem;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        carregarFornecedores();
        inputPregao.setPromptText("Pregão - Formato: XX/YYYY");
        inputValorUnitario.setPromptText("Valor unitário");
        inputQuantidadeMaxima.setPromptText("Quantidade Máxima");
    }

    private void carregarFornecedores() {
        try {
            FornecedorDAO fornecedorDAO = new FornecedorDAO();
            List<Fornecedor> fornecedores = fornecedorDAO.list(100, 0); // Limit 100 fornecedores
            ObservableList<Fornecedor> observableFornecedores = FXCollections.observableArrayList(fornecedores);
            choiceFornecedor.setItems(observableFornecedores);
        } catch (Exception e) {
            System.err.println("Erro ao carregar fornecedores: " + e.getMessage());
        }
    }

    private boolean validateInputs() {
        try {
            // Clear previous error message
            labelMensagem.setText("");

            // Required fields check
            if (inputDescricao.getText().trim().isEmpty()) {
                throw new IllegalArgumentException("Descrição não pode estar vazia");
            }

            // Pregao format check: XX/YYYY
            String pregao = inputPregao.getText().trim();
            if (!pregao.matches("\\d{2}/\\d{4}")) {
                throw new IllegalArgumentException("Pregão deve estar no formato XX/YYYY");
            }

            // Valor unitário check
            String valorStr = inputValorUnitario.getText().trim().replace(",", ".");
            double valor = Double.parseDouble(valorStr);
            if (valor <= 0) {
                throw new IllegalArgumentException("Valor unitário deve ser maior que zero");
            }

            // Quantidade check
            String qtdStr = inputQuantidadeMaxima.getText().trim();
            int qtd = Integer.parseInt(qtdStr);
            if (qtd <= 0) {
                throw new IllegalArgumentException("Quantidade máxima deve ser maior que zero");
            }

            // Fornecedor check
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
    void enviar(ActionEvent event) {
        try {
            if (!validateInputs()) {
                return;
            }

            double valorUnitario = Double.parseDouble(inputValorUnitario.getText().trim().replace(",", "."));
            int quantidadeMaxima = Integer.parseInt(inputQuantidadeMaxima.getText().trim());

            Item novoItem = new Item(
                inputDescricao.getText().trim(),
                inputPregao.getText().trim(),
                quantidadeMaxima,
                quantidadeMaxima, // Initial qtd_solicitada equals qtd_maxima
                valorUnitario,
                choiceFornecedor.getValue()
            );

            ItemDAO itemDAO = new ItemDAO();
            if (itemDAO.insert(novoItem)) {
                limparCampos();
                voltar(event);
            } else {
                labelMensagem.setText("Erro ao inserir item no banco de dados");
            }

        } catch (Exception e) {
            labelMensagem.setText("Erro inesperado: " + e.getMessage());
            e.printStackTrace();
        }
    }

    private void limparCampos() {
        inputDescricao.clear();
        inputPregao.clear();
        inputValorUnitario.clear();
        inputQuantidadeMaxima.clear();
        choiceFornecedor.setValue(null);
    }

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