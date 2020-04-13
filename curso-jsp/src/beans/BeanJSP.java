package beans;

public class BeanJSP {
	private Long id;
	private String login;
	private String senha;
	private String nome;
	private String fone;
	private String cep;
	private String rua;
	private String bairro;
	private String cidade;
	private String estado;
	private String ibge;
	private String foto;
	private String miniFoto;
	private String pdf;
	private String contentType;
	private String cotentTypePdf;
	private String formFoto;
	private String sexo;
	private String perfil;
	private boolean ativo;
	private boolean atualizarImagem = true;
	private boolean atualizarPdf = true;
	
	
	public boolean isAtualizarImagem() {
		return atualizarImagem;
	}

	public void setAtualizarImagem(boolean atualizarImagem) {
		this.atualizarImagem = atualizarImagem;
	}

	public boolean isAtualizarPdf() {
		return atualizarPdf;
	}

	public void setAtualizarPdf(boolean atualizarPdf) {
		this.atualizarPdf = atualizarPdf;
	}

	public String getFormFoto() {
		formFoto = "data:" + contentType + ";base64," + foto;
		return formFoto;
	}
	
	public String getFoto() {
		return foto;
	}
	public void setFoto(String foto) {
		this.foto = foto;
	}
	public String getContentType() {
		return contentType;
	}
	public void setContentType(String contentType) {
		this.contentType = contentType;
	}
	public String getCep() {
		return cep;
	}
	public void setCep(String cep) {
		this.cep = cep;
	}
	public String getRua() {
		return rua;
	}
	public void setRua(String rua) {
		this.rua = rua;
	}
	public String getBairro() {
		return bairro;
	}
	public void setBairro(String bairro) {
		this.bairro = bairro;
	}
	public String getCidade() {
		return cidade;
	}
	public void setCidade(String cidade) {
		this.cidade = cidade;
	}
	public String getEstado() {
		return estado;
	}
	public void setEstado(String estado) {
		this.estado = estado;
	}
	public String getIbge() {
		return ibge;
	}
	public void setIbge(String ibge) {
		this.ibge = ibge;
	}
	public String getNome() {
		return nome;
	}
	public void setNome(String nome) {
		this.nome = nome;
	}
	public String getLogin() {
		return login;
	}
	public void setLogin(String login) {
		this.login = login;
	}
	public String getSenha() {
		return senha;
	}
	public void setSenha(String senha) {
		this.senha = senha;
	}
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getFone() {
		return fone;
	}
	public void setFone(String fone) {
		this.fone = fone;
	}

	public String getPdf() {
		return pdf;
	}

	public void setPdf(String pdf) {
		this.pdf = pdf;
	}

	public String getCotentTypePdf() {
		return cotentTypePdf;
	}

	public void setCotentTypePdf(String cotentTypePdf) {
		this.cotentTypePdf = cotentTypePdf;
	}

	public String getMiniFoto() {
		return miniFoto;
	}

	public void setMiniFoto(String miniFoto) {
		this.miniFoto = miniFoto;
	}

	public boolean isAtivo() {
		return ativo;
	}

	public void setAtivo(boolean ativo) {
		this.ativo = ativo;
	}

	public String getSexo() {
		return sexo;
	}

	public void setSexo(String sexo) {
		this.sexo = sexo;
	}

	public String getPerfil() {
		return perfil;
	}

	public void setPerfil(String perfil) {
		this.perfil = perfil;
	}

}
