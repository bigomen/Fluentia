package br.com.fluentia.dto;

import br.com.fluentia.model.Aluno;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDate;

public class AlunoDto implements Serializable {

	@Serial
	private static final long serialVersionUID = 1L;

	private Long id;
	private Integer ativo;
	private String celular;

	@NotEmpty(message = "CPF")
	private String cpf;

	@NotEmpty(message = "Email")
	private String email;
	private Long idNivelAlunoAula;

	@NotEmpty(message = "Nome")
	private String nome;

	private Integer saldo;
	private String cargo;
	private String cep;
	private String cidade;
	private String complemento;
	private String logradouro;
	private String numero;
	private String rg;
	private String uf;
	private LocalDate dataMatricula;
	private Long idNivelAlunoAulaInicial;
	private EmpresaDto empresa;
	private Long idEmpresa;

	@NotNull(message = "Data de Nascimento")
	private LocalDate dataNascimento;

	private Boolean cartao;

	public Aluno dtoToModel() {
		Aluno model = new Aluno();
		model.setId(this.id);
		model.setAtivo(this.ativo);
		model.setCelular(this.celular);
		model.setCpf(this.cpf);
		model.setDataMatricula(this.dataMatricula);
		model.setEmail(this.email);
		model.setIdNivelAlunoAula(this.idNivelAlunoAula);
		model.setNome(this.nome);
		model.setRg(this.rg);
		model.setCargo(this.cargo);
		model.setLogradouro(this.logradouro);
		model.setNumero(this.numero);
		model.setComplemento(this.complemento);
		model.setCidade(this.cidade);
		model.setUf(this.uf);
		model.setCep(this.cep);
		model.setIdNivelAlunoAulaInicial(this.idNivelAlunoAulaInicial);
		model.setEmpresa(null);
		model.setIdEmpresa(this.idEmpresa);
		model.setDataNascimento(this.dataNascimento);
		return model;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Integer getAtivo() {
		return ativo;
	}

	public void setAtivo(Integer ativo) {
		this.ativo = ativo;
	}

	public String getCelular() {
		return celular;
	}

	public void setCelular(String celular) {
		this.celular = celular;
	}

	public String getCpf() {
		return cpf;
	}

	public void setCpf(String cpf) {
		this.cpf = cpf;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public Long getIdNivelAlunoAula() {
		return idNivelAlunoAula;
	}

	public void setIdNivelAlunoAula(Long idNivelAlunoAula) {
		this.idNivelAlunoAula = idNivelAlunoAula;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public Integer getSaldo() {
		return saldo;
	}

	public void setSaldo(Integer saldo) {
		this.saldo = saldo;
	}

	public String getCargo() {
		return cargo;
	}

	public void setCargo(String cargo) {
		this.cargo = cargo;
	}

	public String getCep() {
		return cep;
	}

	public void setCep(String cep) {
		this.cep = cep;
	}

	public String getCidade() {
		return cidade;
	}

	public void setCidade(String cidade) {
		this.cidade = cidade;
	}

	public String getComplemento() {
		return complemento;
	}

	public void setComplemento(String complemento) {
		this.complemento = complemento;
	}

	public String getLogradouro() {
		return logradouro;
	}

	public void setLogradouro(String logradouro) {
		this.logradouro = logradouro;
	}

	public String getNumero() {
		return numero;
	}

	public void setNumero(String numero) {
		this.numero = numero;
	}

	public String getRg() {
		return rg;
	}

	public void setRg(String rg) {
		this.rg = rg;
	}

	public String getUf() {
		return uf;
	}

	public void setUf(String uf) {
		this.uf = uf;
	}

	public LocalDate getDataMatricula() {
		return dataMatricula;
	}

	public void setDataMatricula(LocalDate dataMatricula) {
		this.dataMatricula = dataMatricula;
	}

	public Long getIdNivelAlunoAulaInicial() {
		return idNivelAlunoAulaInicial;
	}

	public void setIdNivelAlunoAulaInicial(Long idNivelAlunoAulaInicial) {
		this.idNivelAlunoAulaInicial = idNivelAlunoAulaInicial;
	}

	public Long getIdEmpresa() {
		return idEmpresa;
	}

	public void setIdEmpresa(Long idEmpresa) {
		this.idEmpresa = idEmpresa;
	}

	public EmpresaDto getEmpresa() {
		return empresa;
	}

	public void setEmpresa(EmpresaDto empresa) {
		this.empresa = empresa;
	}

	public LocalDate getDataNascimento() {
		return dataNascimento;
	}

	public void setDataNascimento(LocalDate dataNascimento) {
		this.dataNascimento = dataNascimento;
	}

	public Boolean getCartao() {
		return cartao;
	}

	public void setCartao(Boolean cartao) {
		this.cartao = cartao;
	}
}