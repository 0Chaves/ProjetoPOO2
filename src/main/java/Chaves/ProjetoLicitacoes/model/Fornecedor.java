package Chaves.ProjetoLicitacoes.model;
import java.io.IOException;
import java.util.Objects;
import java.util.regex.Pattern;

/**
 * Classe que representa um fornecedor.
 */
public class Fornecedor {
	private int id;
	private String nome;
	private String cnpj;
	private String email;
	private String telefone;

	//PATTERNS
	private static final Pattern CNPJ_PATTERN = Pattern.compile("\\d{2}\\.\\d{3}\\.\\d{3}/\\d{4}-\\d{2}");
    private static final Pattern EMAIL_PATTERN = Pattern.compile("^[\\w-\\.]+@([\\w-]+\\.)+[\\w-]{2,4}$");
    private static final Pattern TELEFONE_PATTERN = Pattern.compile("\\(\\d{2}\\) \\d{4,5}-\\d{4}");
	
	
	/**
     * Construtor para cadastrar um novo fornecedor (id gerado pelo BD).
     *
     * @param nome      Nome do fornecedor.
     * @param cnpj      CNPJ do fornecedor.
     * @param endereco  Endereço do fornecedor.
     * @param email     Email do fornecedor.
     * @param telefone  Telefone do fornecedor.
     * @throws IllegalArgumentException Se algum parâmetro for inválido.
     */
	public Fornecedor(String nome, String cnpj, String email, String telefone) throws IOException {
			setNome(nome);
			setCnpj(cnpj);
			setEmail(email);
			setTelefone(telefone);	
	}
	
	/**
     * Construtor para buscar um fornecedor (recupera o id fornecido pelo BD).
     *
     * @param id        ID do fornecedor.
     * @param nome      Nome do fornecedor.
     * @param cnpj      CNPJ do fornecedor.
     * @param endereco  Endereço do fornecedor.
     * @param email     Email do fornecedor.
     * @param telefone  Telefone do fornecedor.
     * @throws IllegalArgumentException Se algum parâmetro for inválido.
     */
	public Fornecedor(int id, String nome, String cnpj, String email, String telefone) throws IOException {
		setId(id);
		setNome(nome);
		setCnpj(cnpj);
		setEmail(email);
		setTelefone(telefone);	
	}

	//GETTERS
	/**
     * Obtém o nome do fornecedor.
     *
     * @return Nome do fornecedor.
     */
	public String getNome() {
		return nome;
	}

	/**
     * Obtém o CNPJ do fornecedor.
     *
     * @return CNPJ do fornecedor.
     */
	public String getCnpj() {
		return cnpj;
	}


	/**
     * Obtém o email do fornecedor.
     *
     * @return Email do fornecedor.
     */
	public String getEmail() {
		return email;
	}

	/**
     * Obtém o telefone do fornecedor.
     *
     * @return Telefone do fornecedor.
     */
	public String getTelefone() {
		return telefone;
	}
	
	/**
     * Obtém o ID do fornecedor.
     *
     * @return ID do fornecedor.
     */
	public int getId() {
		return id;
	}

	//SETTERS

	/**
     * Define o nome do fornecedor.
     *
     * @param nome Nome do fornecedor.
     * @throws IllegalArgumentException Se o nome for nulo ou vazio.
     */
	private void setNome(String nome) {
        if (nome == null || nome.trim().isEmpty()) {
            throw new IllegalArgumentException("Nome deve ser preenchido");
        }
        this.nome = nome;
    }

	/**
     * Define o CNPJ do fornecedor.
     *
     * @param cnpj CNPJ do fornecedor.
     * @throws IllegalArgumentException Se o CNPJ for nulo ou vazio.
     */
	private void setCnpj(String cnpj) {
        if (cnpj == null || !CNPJ_PATTERN.matcher(cnpj).matches()) {
            throw new IllegalArgumentException("CNPJ inválido");
        }
        this.cnpj = cnpj;
    }

	/**
     * Define o email do fornecedor.
     *
     * @param email Email do fornecedor.
     * @throws IllegalArgumentException Se o email for nulo ou inválido.
     */
	private void setEmail(String email) {
        if (email == null || !EMAIL_PATTERN.matcher(email).matches()) {
            throw new IllegalArgumentException("Email inválido");
        }
        this.email = email;
    }

	/**
     * Define o telefone do fornecedor.
     *
     * @param telefone Telefone do fornecedor.
     * @throws IllegalArgumentException Se o telefone for nulo ou inválido.
     */
	private void setTelefone(String telefone) {
        if (telefone == null || !TELEFONE_PATTERN.matcher(telefone).matches()) {
            throw new IllegalArgumentException("Telefone inválido");
        }
        this.telefone = telefone;
    }

	/**
     * Define o ID do fornecedor.
     *
     * @param id ID do fornecedor.
     */
	private void setId(int id) {
		this.id = id;
	}


	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Fornecedor other = (Fornecedor) obj;
		return Objects.equals(cnpj, other.cnpj);
	}
	
	@Override
    public int hashCode() {
        return Objects.hash(cnpj);
    }

	@Override
    public String toString() {
        return "Fornecedor{" +
                "id=" + id +
                ", nome='" + nome + '\'' +
                ", cnpj='" + cnpj + '\'' +
                ", email='" + email + '\'' +
                ", telefone='" + telefone + '\'' +
                '}';
    }
}
