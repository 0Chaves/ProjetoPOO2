package Chaves.ProjetoLicitacoes.model;

import java.util.Objects;

/**
 * Classe que representa um item.
 */
public class Item {
    private int id;
    private String descricao;
    private String pregao;
    private int quantidadeMaxima;
    private int quantidadeSolicitada;
    private double valorUnitario;
    private Fornecedor fornecedor;

    /**
     * Construtor para cadastrar um novo item (id gerado pelo BD).
     *
     * @param descricao            Descrição do item.
     * @param pregao               Pregão do item.
     * @param quantidadeMaxima     Quantidade máxima do item.
     * @param quantidadeSolicitada Quantidade solicitada do item.
     * @param valorUnitario        Valor unitário do item.
     * @param fornecedor           Fornecedor do item.
     */
    public Item(String descricao, String pregao, int quantidadeMaxima, int quantidadeSolicitada, double valorUnitario, Fornecedor fornecedor) {
        setDescricao(descricao);
        setPregao(pregao);
        setQuantidadeMaxima(quantidadeMaxima);
        setQuantidadeSolicitada(quantidadeSolicitada);
        setValorUnitario(valorUnitario);
        setFornecedor(fornecedor);
    }

    /**
     * Construtor para buscar um item (recupera o id fornecido pelo BD).
     *
     * @param id                   ID do item.
     * @param descricao            Descrição do item.
     * @param pregao               Pregão do item.
     * @param quantidadeMaxima     Quantidade máxima do item.
     * @param quantidadeSolicitada Quantidade solicitada do item.
     * @param valorUnitario        Valor unitário do item.
     * @param fornecedor           Fornecedor do item.
     */
    public Item(int id, String descricao, String pregao, int quantidadeMaxima, int quantidadeSolicitada, double valorUnitario, Fornecedor fornecedor) {
        setId(id);
        setDescricao(descricao);
        setPregao(pregao);
        setQuantidadeMaxima(quantidadeMaxima);
        setQuantidadeSolicitada(quantidadeSolicitada);
        setValorUnitario(valorUnitario);
        setFornecedor(fornecedor);
    }

    // GETTERS
    public String getDescricao() {
        return descricao;
    }

    public String getPregao() {
        return pregao;
    }

    public int getQuantidadeMaxima() {
        return quantidadeMaxima;
    }

    public int getQuantidadeSolicitada() {
        return quantidadeSolicitada;
    }

    public double getValorUnitario() {
        return valorUnitario;
    }

    public int getId() {
        return id;
    }

    public Fornecedor getFornecedor() {
        return fornecedor;
    }

    // SETTERS
    private void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    private void setPregao(String pregao) {
        this.pregao = pregao;
    }

    private void setQuantidadeMaxima(int quantidadeMaxima) {
        this.quantidadeMaxima = quantidadeMaxima;
    }

    private void setQuantidadeSolicitada(int quantidadeSolicitada) {
        this.quantidadeSolicitada = quantidadeSolicitada;
    }

    private void setValorUnitario(double valorUnitario) {
        this.valorUnitario = valorUnitario;
    }

    private void setId(int id) {
        this.id = id;
    }

    private void setFornecedor(Fornecedor fornecedor) {
        this.fornecedor = fornecedor;
    }

    @Override
    public int hashCode() {
        return Objects.hash(descricao, fornecedor, id, pregao, quantidadeMaxima, quantidadeSolicitada, valorUnitario);
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
                && quantidadeMaxima == other.quantidadeMaxima && quantidadeSolicitada == other.quantidadeSolicitada
                && Double.doubleToLongBits(valorUnitario) == Double.doubleToLongBits(other.valorUnitario);
    }

    @Override
    public String toString() {
        return "Item [id=" + id + ", descricao=" + descricao + ", pregao=" + pregao + ", quantidadeMaxima="
                + quantidadeMaxima + ", quantidadeSolicitada=" + quantidadeSolicitada + ", valorUnitario="
                + valorUnitario + ", fornecedor=" + fornecedor + "]";
    }
}