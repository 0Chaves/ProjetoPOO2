package Chaves.ProjetoLicitacoes.controller;

import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;

import Chaves.ProjetoLicitacoes.dao.FornecedorDAO;
import Chaves.ProjetoLicitacoes.model.Fornecedor;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

public class TelaListaFornecedoresController implements Initializable {
    
    private Stage stage;
    private Scene scene;
    private BorderPane root;
    
    private FornecedorDAO fornecedorDAO = new FornecedorDAO();
    private List<Fornecedor> lista;
    private ObservableList<Fornecedor> lista_observavel;
    
    @FXML
    private TableView<Fornecedor> tabela;
    
    @FXML
    private TableColumn<Fornecedor, Integer> colunaId;
    @FXML
    private TableColumn<Fornecedor, String> colunaNome;
    @FXML
    private TableColumn<Fornecedor, String> colunaCnpj;
    @FXML
    private TableColumn<Fornecedor, String> colunaEmail;
    @FXML
    private TableColumn<Fornecedor, String> colunaTelefone;
    
    @FXML
    private TextField txtNome;
    @FXML
    private TextField txtCnpj;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        configurarColunas();
        carregarFornecedores();
    }

    private void configurarColunas() {
        colunaId.setCellValueFactory(new PropertyValueFactory<>("id"));
        colunaNome.setCellValueFactory(new PropertyValueFactory<>("nome"));
        colunaCnpj.setCellValueFactory(new PropertyValueFactory<>("cnpj"));
        colunaEmail.setCellValueFactory(new PropertyValueFactory<>("email"));
        colunaTelefone.setCellValueFactory(new PropertyValueFactory<>("telefone"));
    }

    private void carregarFornecedores() {
        try {
            lista = fornecedorDAO.list(100, 0);
            lista_observavel = FXCollections.observableArrayList(lista);
            tabela.setItems(lista_observavel);
        } catch (RuntimeException e) {
            exibirErro("Erro ao carregar fornecedores", e.getMessage());
        }
    }

    @FXML
    void deletar(ActionEvent event) {
        Fornecedor selecionado = tabela.getSelectionModel().getSelectedItem();
        if (selecionado == null) {
            exibirAlerta("Selecione um fornecedor para deletar");
            return;
        }

        Optional<ButtonType> resultado = confirmarDelecao(selecionado);
        if (resultado.get() == ButtonType.OK) {
            try {
                if (fornecedorDAO.delete(selecionado.getId())) {
                    carregarFornecedores();
                    exibirSucesso("Fornecedor deletado com sucesso!");
                }
            } catch (RuntimeException e) {
                exibirErro("Erro ao deletar fornecedor", e.getMessage());
            }
        }
    }

    @FXML
    void pesquisar(ActionEvent event) {
        String nomeBusca = txtNome.getText().trim().toLowerCase();
        String cnpjBusca = txtCnpj.getText().trim();
        
        ObservableList<Fornecedor> resultadoFiltrado = lista_observavel.filtered(fornecedor -> {
            boolean matchNome = nomeBusca.isEmpty() || 
                              fornecedor.getNome().toLowerCase().contains(nomeBusca);
            boolean matchCnpj = cnpjBusca.isEmpty() || 
                               fornecedor.getCnpj().contains(cnpjBusca);
            return matchNome && matchCnpj;
        });
        
        tabela.setItems(resultadoFiltrado);
    }

    @FXML
void voltar(ActionEvent event) {
    try {
        AnchorPane root = FXMLLoader.load(getClass().getResource("/TelaInicial.fxml"));
        scene = new Scene(root);
        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        stage.setScene(scene);
        stage.show();
    } catch (IOException e) {
        e.printStackTrace();
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Erro");
        alert.setHeaderText("Erro ao voltar para tela inicial");
        alert.setContentText("Não foi possível retornar à tela inicial: " + e.getMessage());
        alert.showAndWait();
    }
}

    private Optional<ButtonType> confirmarDelecao(Fornecedor fornecedor) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Confirmar Exclusão");
        alert.setHeaderText("Excluir Fornecedor");
        alert.setContentText("Deseja realmente excluir o fornecedor " + fornecedor.getNome() + "?");
        return alert.showAndWait();
    }

    private void exibirErro(String header, String content) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Erro");
        alert.setHeaderText(header);
        alert.setContentText(content);
        alert.showAndWait();
    }

    private void exibirAlerta(String mensagem) {
        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setTitle("Atenção");
        alert.setHeaderText(null);
        alert.setContentText(mensagem);
        alert.showAndWait();
    }

    private void exibirSucesso(String mensagem) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Sucesso");
        alert.setHeaderText(null);
        alert.setContentText(mensagem);
        alert.showAndWait();
    }
}