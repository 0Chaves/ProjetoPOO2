package Chaves.ProjetoLicitacoes.controller;

import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;
import javafx.beans.property.SimpleStringProperty;
import Chaves.ProjetoLicitacoes.dao.ItemDAO;
import Chaves.ProjetoLicitacoes.model.Item;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

public class TelaListaItensController implements Initializable {
    
    private Stage stage;
    private Scene scene;
    private AnchorPane root;
    
    private ItemDAO itemDAO = new ItemDAO();
    private List<Item> lista;
    private ObservableList<Item> lista_observavel;
    
    @FXML
    private TableView<Item> tabela;
    
    @FXML
    private TableColumn<Item, Integer> colunaId;
    @FXML
    private TableColumn<Item, String> colunaDescricao;
    @FXML
    private TableColumn<Item, String> colunaPregao;
    @FXML
    private TableColumn<Item, Integer> colunaQtdMax;
    @FXML
    private TableColumn<Item, Integer> colunaQtdSolic;
    @FXML
    private TableColumn<Item, Double> colunaValor;
    @FXML
    private TableColumn<Item, String> colunaFornecedor;
    
    @FXML
    private TextField txtDescricao;
    @FXML
    private TextField txtPregao;
    @FXML
    private TextField txtFornecedor;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        configurarColunas();
        carregarItens();
    }

    private void configurarColunas() {
        colunaId.setCellValueFactory(new PropertyValueFactory<>("id"));
        colunaDescricao.setCellValueFactory(new PropertyValueFactory<>("descricao"));
        colunaPregao.setCellValueFactory(new PropertyValueFactory<>("pregao"));
        colunaQtdMax.setCellValueFactory(new PropertyValueFactory<>("quantidadeMaxima"));
        colunaQtdSolic.setCellValueFactory(new PropertyValueFactory<>("quantidadeSolicitada"));
        colunaValor.setCellValueFactory(new PropertyValueFactory<>("valorUnitario"));
        colunaFornecedor.setCellValueFactory(
            cellData -> new SimpleStringProperty(cellData.getValue().getFornecedor().getNome())
        );
    }

    private void carregarItens() {
        try {
            lista = itemDAO.list(100, 0);
            lista_observavel = FXCollections.observableArrayList(lista);
            tabela.setItems(lista_observavel);
        } catch (RuntimeException e) {
            exibirErro("Erro ao carregar itens", e.getMessage());
        }
    }

    @FXML
    void deletar(ActionEvent event) {
        Item selecionado = tabela.getSelectionModel().getSelectedItem();
        if (selecionado == null) {
            exibirAlerta("Selecione um item para deletar");
            return;
        }

        Optional<ButtonType> resultado = confirmarDelecao(selecionado);
        if (resultado.get() == ButtonType.OK) {
            try {
                if (itemDAO.delete(selecionado.getId())) {
                    carregarItens();
                    exibirSucesso("Item deletado com sucesso!");
                }
            } catch (RuntimeException e) {
                exibirErro("Erro ao deletar item", e.getMessage());
            }
        }
    }

    @FXML
    void pesquisar(ActionEvent event) {
        String descricaoBusca = txtDescricao.getText().trim().toLowerCase();
        String pregaoBusca = txtPregao.getText().trim();
        String fornecedorBusca = txtFornecedor.getText().trim().toLowerCase();
        
        ObservableList<Item> resultadoFiltrado = lista_observavel.filtered(item -> {
            boolean matchDescricao = descricaoBusca.isEmpty() || 
                                   item.getDescricao().toLowerCase().contains(descricaoBusca);
            boolean matchPregao = pregaoBusca.isEmpty() || 
                                 item.getPregao().contains(pregaoBusca);
            boolean matchFornecedor = fornecedorBusca.isEmpty() || 
                                    item.getFornecedor().getNome().toLowerCase().contains(fornecedorBusca);
            return matchDescricao && matchPregao && matchFornecedor;
        });
        
        tabela.setItems(resultadoFiltrado);
    }

    @FXML
    void voltar(ActionEvent event) {
        try {
            root = FXMLLoader.load(getClass().getResource("/TelaInicial.fxml"));
            scene = new Scene(root);
            stage = (Stage)((Node)event.getSource()).getScene().getWindow();
            stage.setScene(scene);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
            exibirErro("Erro ao voltar para tela inicial", e.getMessage());
        }
    }

    private Optional<ButtonType> confirmarDelecao(Item item) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Confirmar Exclusão");
        alert.setHeaderText("Excluir Item");
        alert.setContentText("Deseja realmente excluir o item?\n" + 
                           "Descrição: " + item.getDescricao() + "\n" +
                           "Pregão: " + item.getPregao());
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

    @FXML
    void editar(ActionEvent event) {
        Item selecionado = tabela.getSelectionModel().getSelectedItem();
        if (selecionado == null) {
            exibirAlerta("Selecione um item para editar");
            return;
        }

        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/TelaEditarItem.fxml"));
            Parent root = loader.load();
            
            TelaEditarItemController controller = loader.getController();
            controller.setItem(selecionado);
            
            stage = (Stage)((Node)event.getSource()).getScene().getWindow();
            scene = new Scene(root);
            stage.setScene(scene);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
            exibirErro("Erro ao abrir tela de edição", e.getMessage());
        }
    }
}