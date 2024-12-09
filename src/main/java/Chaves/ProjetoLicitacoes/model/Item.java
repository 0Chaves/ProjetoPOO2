package Chaves.ProjetoLicitacoes.model;

import java.util.Objects;

public class Item {
	private int id;
	private String descricao;
	private String pregao;
	private int quantidade_maxima;
	private int quantidade_solicitada;
	private double valor_unitario;
	private Fornecedor fornecedor;
	
	
	//Construtor para cadastrar um novo fornecedor (id gerado pelo BD)
	public Item (String descricao, String pregao, int quantidade_maxima, int quantidade_solicitada, double valor_unitario, Fornecedor fornecedor) {
		setDescricao(descricao);
		setPregao(pregao);
		setQuantidade_maxima(quantidade_maxima);
		setQuantidade_solicitada(quantidade_solicitada);
		setValor_unitario(valor_unitario);
		setFornecedor(fornecedor);
	}
	
	//Construtor para buscar um fornecedor (recupera o id fornecido pelo BD)
	public Item (int id, String descricao, String pregao, int quantidade_maxima, int quantidade_solicitada, double valor_unitario, Fornecedor fornecedor) {
		setId(id);
		setDescricao(descricao);
		setPregao(pregao);
		setQuantidade_maxima(quantidade_maxima);
		setQuantidade_solicitada(quantidade_solicitada);
		setValor_unitario(valor_unitario);
		setFornecedor(fornecedor);;
	}

	//GETTERS
	public String getDescricao() {
		return descricao;
	}
	public String getPregao() {
		return pregao;
	}

	public int getQuantidade_maxima() {
		return quantidade_maxima;
	}

	public int getQuantidade_solicitada() {
		return quantidade_solicitada;
	}

	public double getValor_unitario() {
		return valor_unitario;
	}
	
	public int getId() {
		return id;
	}
	
	public Fornecedor getFornecedor() {
		return fornecedor;
	}

	//SETTERS
	private void setDescricao(String descricao) {
		this.descricao = descricao;
	}
	
	private void setPregao(String pregao) {
		this.pregao = pregao;
	}

	private void setQuantidade_maxima(int quantidade_maxima) {
		this.quantidade_maxima = quantidade_maxima;
	}

	private void setQuantidade_solicitada(int quantidade_solicitada) {
		this.quantidade_solicitada = quantidade_solicitada;
	}

	private void setValor_unitario(double valor_unitario) {
		this.valor_unitario = valor_unitario;
	}
	
	private void setId(int id) {
		this.id = id;
	}
	
	private void setFornecedor(Fornecedor fornecedor) {
		this.fornecedor = fornecedor;
	}

	@Override
	public int hashCode() {
		return Objects.hash(descricao, fornecedor, id, pregao, quantidade_maxima, quantidade_solicitada,
				valor_unitario);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (!(obj instanceof Item))
			return false;
		Item other = (Item) obj;
		return Objects.equals(descricao, other.descricao) && Objects.equals(fornecedor, other.fornecedor)
				&& id == other.id && Objects.equals(pregao, other.pregao)
				&& quantidade_maxima == other.quantidade_maxima && quantidade_solicitada == other.quantidade_solicitada
				&& Double.doubleToLongBits(valor_unitario) == Double.doubleToLongBits(other.valor_unitario);
	}

	@Override
	public String toString() {
		return "Item [id=" + id + ", descricao=" + descricao + ", pregao=" + pregao + ", quantidade_maxima="
				+ quantidade_maxima + ", quantidade_solicitada=" + quantidade_solicitada + ", valor_unitario="
				+ valor_unitario + ", fornecedor=" + fornecedor + ", getDescricao()=" + getDescricao()
				+ ", getPregao()=" + getPregao() + ", getQuantidade_maxima()=" + getQuantidade_maxima()
				+ ", getQuantidade_solicitada()=" + getQuantidade_solicitada() + ", getValor_unitario()="
				+ getValor_unitario() + ", getId()=" + getId() + ", getFornecedor()=" + getFornecedor() + "]";
	}

}
