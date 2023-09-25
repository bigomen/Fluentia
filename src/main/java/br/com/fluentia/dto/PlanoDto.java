package br.com.fluentia.dto;

import br.com.fluentia.exception.BusinessException;
import br.com.fluentia.model.Plano;
import br.com.fluentia.model.PlanoIugu;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public class PlanoDto {

	private Long id;

	@NotEmpty(message = "Nome")
	private String nome;
	private String descricao;

	@NotEmpty(message = "Frequência")
	private String frequencia;

	private String modalidade;

	@NotNull(message = "Preço")
	private BigDecimal preco;

	@NotNull(message = "Crédito")
	private Integer credito;

	@NotNull(message = "Dias Válidade")
	private Integer diasValidade;
	private Integer ativo;

	private Boolean publico = false;

	private String codigoIugu;
	private EmpresaDto empresa;

	@NotNull(message = "Empresa")
	private Long idEmpresa;

	@NotNull(message = "Valor empresa")
	private BigDecimal valorEmpresa;

	public PlanoDto() {
	}

	public Plano dtoToModel () {
		Plano model = new Plano();
		model.setId(this.id);
		model.setAtivo(this.ativo);
		model.setCredito(this.credito);
		model.setDescricao(this.descricao);
		model.setDiasValidade(this.diasValidade);
		model.setNome(this.nome);
		model.setPreco(this.preco);
		model.setPublico(this.publico);
		model.setEmpresa(null);
		model.setIdEmpresa(this.idEmpresa);
		model.setCodigoIugu(this.codigoIugu);
		model.setFrequencia(this.frequencia);
		model.setValorEmpresa(this.valorEmpresa);
		return model;
	}

	public PlanoIugu dtoToIugu(){
		PlanoIugu iugu = new PlanoIugu();
		iugu.setName(this.nome);
		if(this.preco.scale() > 2) throw new BusinessException("Valor invalido");
		iugu.setValue_cents(this.preco.movePointRight(2).intValueExact());
		iugu.setIdentifier(this.nome.replace(" ", ""));
		iugu.setInterval_type(this.frequencia);
		iugu.setBilling_days(this.diasValidade);
		iugu.setPayable_with(Collections.singletonList("all"));
		iugu.setInterval(1);
		iugu.setMax_cycles(0);
		return iugu;
	}

	public Map dtoToMapIugu(){
		Map iuguRequest = new HashMap();
		iuguRequest.put("id", this.codigoIugu);
		iuguRequest.put("name", this.nome);
		iuguRequest.put("value_cents", this.preco.intValueExact());
		iuguRequest.put("interval_type", this.frequencia);
		iuguRequest.put("billing_days", this.diasValidade);
		iuguRequest.put("payable_with", "all");
		iuguRequest.put("interval", 1);
		iuguRequest.put("max_cycles", 0);
		return iuguRequest;
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
	public String getFrequencia() {
		return frequencia;
	}
	public void setFrequencia(String frequencia) {
		this.frequencia = frequencia;
	}
	public String getModalidade() {
		return modalidade;
	}
	public void setModalidade(String modalidade) {
		this.modalidade = modalidade;
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

	public String getCodigoIugu() {
		return codigoIugu;
	}

	public void setCodigoIugu(String codigoIugu) {
		this.codigoIugu = codigoIugu;
	}

	public EmpresaDto getEmpresa() {
		return empresa;
	}

	public void setEmpresa(EmpresaDto empresa) {
		this.empresa = empresa;
	}

	public Long getIdEmpresa() {
		return idEmpresa;
	}

	public void setIdEmpresa(Long idEmpresa) {
		this.idEmpresa = idEmpresa;
	}

	public BigDecimal getValorEmpresa() {
		return valorEmpresa;
	}

	public void setValorEmpresa(BigDecimal valorEmpresa) {
		this.valorEmpresa = valorEmpresa;
	}
}
