package br.com.fluentia.model;

import br.com.fluentia.dto.AlunoDto;
import br.com.fluentia.type.StatusAlunoEnum;
import jakarta.persistence.*;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDate;

@Entity
@DynamicUpdate
@DynamicInsert
@Table(name = "aluno")
public class Aluno implements Serializable {

	@Serial
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(name = "ativo")
	private Integer ativo;

	@Column(name = "celular")
	private String celular;

	@Column(name = "cpf")
	private String cpf;

	@Column(name = "email")
	private String email;

	@Column(name = "fk_nivel_aluno_aula")
	private Long idNivelAlunoAula;

	@Column(name = "nome")
	private String nome;

	@Column(name = "saldo")
	private Integer saldo;

	@Column(name = "cargo")
	private String cargo;

	@Column(name = "cep")
	private String cep;

	@Column(name = "cidade")
	private String cidade;

	@Column(name = "complemento")
	private String complemento;

	@Column(name = "logradouro")
	private String logradouro;

	@Column(name = "numero")
	private String numero;

	@Column(name = "rg")
	private String rg;

	@Column(name = "uf")
	private String uf;

	@Column(name = "data_matricula")
	private LocalDate dataMatricula;

	@Column(name = "fk_nivel_aluno_aula_inicial", updatable = false)
	private Long idNivelAlunoAulaInicial;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "fk_empresa", updatable = false, insertable = false)
	private Empresa empresa;

	@Column(name = "fk_empresa")
	private Long idEmpresa;

	@Column(name = "senha")
	private String senha;

	@Column(name = "reset_token")
	private String resetToken;

	@Column(name = "dt_nasc")
	private LocalDate dataNascimento;

	@Column(name = "id_iugu_cliente")
	private String idIugu;
	
	public Aluno() {
	}
	
	public Aluno (Long id, String nome) {
		this.id = id;
		this.nome = nome;
	}

	public Aluno(String email, String nome) {
		this.email = email;
		this.nome = nome;
	}

	public Aluno(Long id, String email, String nome) {
		this.id = id;
		this.email = email;
		this.nome = nome;
	}

	@PrePersist
	private void preInsert(){
		this.ativo = StatusAlunoEnum.ATIVO.getStatus();
		this.dataMatricula = LocalDate.now();
		this.saldo = 0;
	}

	public AlunoDto modelToDto() {
		AlunoDto dto = new AlunoDto();
		dto.setId(this.id);
		dto.setAtivo(this.ativo);
		dto.setCelular(this.celular);
		dto.setCpf(this.cpf);
		dto.setDataNascimento(this.dataNascimento);
		dto.setEmail(this.email);
		dto.setIdNivelAlunoAula(this.idNivelAlunoAula);
		dto.setNome(this.nome);
		dto.setRg(this.rg);
		dto.setCargo(this.cargo);
		dto.setLogradouro(this.logradouro);
		dto.setNumero(this.numero);
		dto.setComplemento(this.complemento);
		dto.setCidade(this.cidade);
		dto.setUf(this.uf);
		dto.setCep(this.cep);
		dto.setDataMatricula(this.dataMatricula);
		dto.setIdNivelAlunoAulaInicial(this.idNivelAlunoAulaInicial);
		if(this.empresa != null){
			dto.setEmpresa(this.empresa.modelToDto());
		}
		dto.setIdEmpresa(this.idEmpresa);
		return dto;
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

	public Empresa getEmpresa() {
		return empresa;
	}

	public void setEmpresa(Empresa empresa) {
		this.empresa = empresa;
	}

	public Long getIdEmpresa() {
		return idEmpresa;
	}

	public void setIdEmpresa(Long idEmpresa) {
		this.idEmpresa = idEmpresa;
	}

	public String getSenha() {
		return senha;
	}

	public void setSenha(String senha) {
		this.senha = senha;
	}

	public String getResetToken() {
		return resetToken;
	}

	public void setResetToken(String resetToken) {
		this.resetToken = resetToken;
	}

	public LocalDate getDataNascimento() {
		return dataNascimento;
	}

	public void setDataNascimento(LocalDate dataNascimento) {
		this.dataNascimento = dataNascimento;
	}

	public String getIdIugu() {
		return idIugu;
	}

	public void setIdIugu(String idIugu) {
		this.idIugu = idIugu;
	}
}