package beans;

public class Produto {
	private long id;
	private String nome;
	private double quantidade;
	private double valor;
	private long categoria_id;
	
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public String getNome() {
		return nome;
	}
	public void setNome(String nome) {
		this.nome = nome;
	}
	public double getQuantidade() {
		return quantidade;
	}
	public void setQuantidade(double quantidade) {
		this.quantidade = quantidade;
	}
	public double getValor() {
		return valor;
	}
	public void setValor(double valor) {
		this.valor = valor;
	}
	
	public String getValorEmTexto() {
		return Double.toString(valor).replace('.', ',');
	}
	public long getCategoria_id() {
		return categoria_id;
	}
	public void setCategoria_id(long categoria_id) {
		this.categoria_id = categoria_id;
	}
}
