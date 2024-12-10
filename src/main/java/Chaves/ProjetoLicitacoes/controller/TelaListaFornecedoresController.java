package Chaves.ProjetoLicitacoes.controller;

import java.util.List;

import Chaves.ProjetoLicitacoes.dao.FornecedorDAO;
import Chaves.ProjetoLicitacoes.model.Fornecedor;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;

public class TelaListaFornecedoresController {
	
	private FornecedorDAO fornecedorDAO = new FornecedorDAO();
	private List<Fornecedor> lista = fornecedorDAO.list(15, 0);
	private ObservableList<Fornecedor> lista_observavel = FXCollections.observableArrayList(lista);
	
	@FXML
    private TableView<Fornecedor> tabela;
    
    @FXML
    private TableColumn<Fornecedor, String> colunaCnpj;

    @FXML
    private TableColumn<Fornecedor, String> colunaEmail;

    @FXML
    private TableColumn<Fornecedor, Integer> colunaId;

    @FXML
    private TableColumn<Fornecedor, String> colunaNome;

    @FXML
    private TableColumn<Fornecedor, String> colunaTelefone;

    @FXML
    private Button Abrir;

    @FXML
    private Button Deletar;

    @FXML
    private Button Editar;

    @FXML
    private Button Pesquisar;

    @FXML
    private Button Voltar;
    
    @FXML
    private TextField txtCnpj;

    @FXML
    private TextField txtNome;

    @FXML
    void abrir(ActionEvent event) {

    }

    @FXML
    void deletar(ActionEvent event) {

    }

    @FXML
    void editar(ActionEvent event) {

    }

    @FXML
    void pesquisar(ActionEvent event) {
    	tabela.setItems(lista_observavel);
    }

    @FXML
    void voltar(ActionEvent event) {

    }

}
