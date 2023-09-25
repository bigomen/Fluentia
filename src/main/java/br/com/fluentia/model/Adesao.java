package br.com.fluentia.model;

import jakarta.persistence.*;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import java.util.Calendar;

@Entity
@Table(name = "aluno_plano")
@DynamicInsert
@DynamicUpdate
public class Adesao {

    @Id
    @Column(name = "codigo", nullable = false)
    private String codigo;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "aluno_id", referencedColumnName = "id")
    private Aluno aluno;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "plano_id", referencedColumnName = "id")
    private Plano plano;

    @Column(name = "data_adesao", nullable = false, updatable = false)
    @Temporal(TemporalType.TIMESTAMP)
    private Calendar dataAdesao;

    @Column(name = "data_cancelamento")
    @Temporal(TemporalType.TIMESTAMP)
    private Calendar dataCancelamento;

    @Column(name = "data_suspensao")
    @Temporal(TemporalType.TIMESTAMP)
    private Calendar dataSuspensao;

    @Column(name = "data_reativacao")
    @Temporal(TemporalType.TIMESTAMP)
    private Calendar dataReativacao;

    @Column(name = "data_troca_adesao")
    @Temporal(TemporalType.TIMESTAMP)
    private Calendar dataTroca;

    @Column(name = "envia_email")
    private Boolean enviaEmail;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "plano_id_novo", referencedColumnName = "id")
    private Plano planoNovo;

    @Column(name = "motivo_cancelamento")
    private String motivoCancelamento;

    public Adesao(String codigo, Aluno aluno, Plano plano) {
        this.codigo = codigo;
        this.aluno = aluno;
        this.plano = plano;
    }

    public Adesao() {}

    @PrePersist
    private void preInsert(){
        this.dataAdesao = Calendar.getInstance();
    }

    public void cancelarAdesao(String motivo){
        this.dataCancelamento = Calendar.getInstance();
        this.motivoCancelamento = motivo;
    }

    public boolean isCancelado(){
        return this.dataCancelamento != null;
    }

    public void suspenderAdesao(){
        this.dataReativacao = null;
        this.dataSuspensao = Calendar.getInstance();
    }

    public boolean isSuspenso(){
        return this.dataSuspensao != null;
    }

    public void reativarAdesao(){
        if(this.dataCancelamento != null){
            throw new RuntimeException("Plano cancelado não pode ser reativado!");
        }
        this.dataReativacao = Calendar.getInstance();
    }

	public String getCodigo() {
		return codigo;
	}

	public void setCodigo(String codigo) {
		this.codigo = codigo;
	}

	public Aluno getAluno() {
		return aluno;
	}

	public void setAluno(Aluno aluno) {
		this.aluno = aluno;
	}

	public Plano getPlano() {
		return plano;
	}

	public void setPlano(Plano plano) {
		this.plano = plano;
	}

	public Calendar getDataAdesao() {
		return dataAdesao;
	}

	public void setDataAdesao(Calendar dataAdesao) {
		this.dataAdesao = dataAdesao;
	}

	public Calendar getDataCancelamento() {
		return dataCancelamento;
	}

	public void setDataCancelamento(Calendar dataCancelamento) {
		this.dataCancelamento = dataCancelamento;
	}

	public Calendar getDataSuspensao() {
		return dataSuspensao;
	}

	public void setDataSuspensao(Calendar dataSuspensao) {
		this.dataSuspensao = dataSuspensao;
	}

	public Calendar getDataReativacao() {
		return dataReativacao;
	}

	public void setDataReativacao(Calendar dataReativacao) {
		this.dataReativacao = dataReativacao;
	}

	public Calendar getDataTroca() {
		return dataTroca;
	}

	public void setDataTroca(Calendar dataTroca) {
		this.dataTroca = dataTroca;
	}

	public Boolean getEnviaEmail() {
		return enviaEmail;
	}

	public void setEnviaEmail(Boolean enviaEmail) {
		this.enviaEmail = enviaEmail;
	}

	public Plano getPlanoNovo() {
		return planoNovo;
	}

	public void setPlanoNovo(Plano planoNovo) {
		this.planoNovo = planoNovo;
	}

	public String getMotivoCancelamento() {
		return motivoCancelamento;
	}

	public void setMotivoCancelamento(String motivoCancelamento) {
		this.motivoCancelamento = motivoCancelamento;
	}
    
    
    
    
}