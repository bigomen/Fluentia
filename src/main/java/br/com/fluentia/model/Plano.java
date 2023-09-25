package br.com.fluentia.model;

import br.com.fluentia.dto.PlanoDto;
import br.com.fluentia.type.StatusPlanoEnum;
import jakarta.persistence.*;

import java.io.Serial;
import java.io.Serializable;
import java.math.BigDecimal;

@Entity
@Table(name = "plano")
public class Plano implements Serializable {

	@Serial
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private Long id;

	@Column(nullable = false, unique = true)
	private String nome;

	@Column
	private String descricao;

	@Column(nullable = false)
	private String frequencia;

	@Column(nullable = false)
	private String modalidade;

	@Column(nullable = false)
	private BigDecimal preco;

	@Column(nullable = false)
	private Integer credito;

	@Column(nullable = false)
	private Integer diasValidade;

	@Column
	private Integer ativo;

	@Column(nullable = false)
	private Boolean publico;

	@Column(name = "codigo_iugu")
	private String codigoIugu;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "fk_empresa", referencedColumnName = "id", updatable = false, insertable = false)
	private Empresa empresa;

	@Column(name = "fk_empresa")
	private Long idEmpresa;

	@Column(name = "valor_empresa")
	private BigDecimal valorEmpresa;

	public PlanoDto modelToDto () {
		PlanoDto dto = new PlanoDto();
		dto.setId(this.id);
		dto.setAtivo(this.ativo);
		dto.setNome(this.nome);
		dto.setCredito(this.credito);
		dto.setDescricao(this.descricao);
		dto.setDiasValidade(this.diasValidade);
		dto.setFrequencia(this.frequencia);
		dto.setModalidade(this.modalidade);
		dto.setPreco(this.preco);
		dto.setPublico(this.publico);
		if(this.empresa != null){
			dto.setEmpresa(this.empresa.modelToDto());
		}
		dto.setIdEmpresa(this.idEmpresa);
		dto.setValorEmpresa(this.valorEmpresa);
		return dto;
	}

	@PrePersist
	private void PrePersist(){
		this.ativo = StatusPlanoEnum.ATIVO.getStatus();
		this.modalidade = "AUTO";
		this.publico = false;
	}

	@PreUpdate
	private void PreUpdate(){
		this.publico = false;
		this.modalidade = "AUTO";
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getDescricao() {
		return descricao;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}

	public BigDecimal getPreco() {
		return preco;
	}

	public void setPreco(BigDecimal preco) {
		this.preco = preco;
	}

	public Integer getCredito() {
		return credito;
	}

	public void setCredito(Integer credito) {
		this.credito = credito;
	}

	public Integer getDiasValidade() {
		return diasValidade;
	}

	public void setDiasValidade(Integer diasValidade) {
		this.diasValidade = diasValidade;
	}

	public Integer getAtivo() {
		return ativo;
	}

	public void setAtivo(Integer ativo) {
		this.ativo = ativo;
	}

	public Boolean getPublico() {
		return publico;
	}

	public void setPublico(Boolean publico) {
		this.publico = publico;
	}

	public String getFrequencia() {
		return frequencia;
	}

	public String getModalidade() {
		return modalidade;
	}

	public String getCodigoIugu() {
		return codigoIugu;
	}

	public void setCodigoIugu(String codigoIugu) {
		this.codigoIugu = codigoIugu;
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

	public void setFrequencia(String frequencia) {
		this.frequencia = frequencia;
	}

	public BigDecimal getValorEmpresa() {
		return valorEmpresa;
	}

	public void setValorEmpresa(BigDecimal valorEmpresa) {
		this.valorEmpresa = valorEmpresa;
	}
}