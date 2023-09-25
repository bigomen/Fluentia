package br.com.fluentia.model;

import br.com.fluentia.dto.AlunoPlanoDto;
import jakarta.persistence.*;

import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Table(name = "aluno_plano")
public class AlunoPlano implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    @Id
    @Column(name = "codigo")
    private String codigo;

    @Column(name = "data_adesao")
    private LocalDateTime dataAdesao;

    @Column(name = "data_cancelamento")
    private LocalDateTime dataCancelamento;

    @Column(name = "data_reativacao")
    private LocalDateTime dataReativacao;

    @Column(name = "data_suspensao")
    private LocalDateTime dataSuspensao;

    @Column(name = "data_troca_adesao")
    private LocalDate dataTrocaAdesao;

    @Column(name = "envia_email")
    private Boolean enviaEmail;

    @Column(name = "motivo_cancelamento")
    private String motivoCancelamento;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "aluno_id", referencedColumnName = "id", updatable = false, insertable = false)
    private Aluno aluno;

    @Column(name = "aluno_id")
    private Long idAluno;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "plano_id", referencedColumnName = "id", updatable = false, insertable = false)
    private Plano plano;

    @Column(name = "plano_id")
    private Long idPlano;

    @Column(name = "plano_id_novo")
    private Long idPlanoNovo;

    public AlunoPlanoDto modelToDto(){
        AlunoPlanoDto dto = new AlunoPlanoDto();
        dto.setDataAdesao(this.dataAdesao);
        dto.setDataCancelamento(this.dataCancelamento);
        dto.setDataReativacao(this.dataReativacao);
        dto.setDataSuspensao(this.dataSuspensao);
        dto.setDataTrocaAdesao(this.dataTrocaAdesao);
        dto.setEnviaEmail(this.enviaEmail);
        dto.setMotivoCancelamento(this.motivoCancelamento);
        dto.setIdPlano(this.idPlano);
        dto.setIdAluno(this.idAluno);
        dto.setIdPlanoNovo(this.idPlanoNovo);
        return dto;
    }

    public String getCodigo() {
        return codigo;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

    public LocalDateTime getDataAdesao() {
        return dataAdesao;
    }

    public void setDataAdesao(LocalDateTime dataAdesao) {
        this.dataAdesao = dataAdesao;
    }

    public LocalDateTime getDataCancelamento() {
        return dataCancelamento;
    }

    public void setDataCancelamento(LocalDateTime dataCancelamento) {
        this.dataCancelamento = dataCancelamento;
    }

    public LocalDateTime getDataReativacao() {
        return dataReativacao;
    }

    public void setDataReativacao(LocalDateTime dataReativacao) {
        this.dataReativacao = dataReativacao;
    }

    public LocalDateTime getDataSuspensao() {
        return dataSuspensao;
    }

    public void setDataSuspensao(LocalDateTime dataSuspensao) {
        this.dataSuspensao = dataSuspensao;
    }

    public LocalDate getDataTrocaAdesao() {
        return dataTrocaAdesao;
    }

    public void setDataTrocaAdesao(LocalDate dataTrocaAdesao) {
        this.dataTrocaAdesao = dataTrocaAdesao;
    }

    public Boolean getEnviaEmail() {
        return enviaEmail;
    }

    public void setEnviaEmail(Boolean enviaEmail) {
        this.enviaEmail = enviaEmail;
    }

    public String getMotivoCancelamento() {
        return motivoCancelamento;
    }

    public void setMotivoCancelamento(String motivoCancelamento) {
        this.motivoCancelamento = motivoCancelamento;
    }

    public Aluno getAluno() {
        return aluno;
    }

    public void setAluno(Aluno aluno) {
        this.aluno = aluno;
    }

    public Long getIdAluno() {
        return idAluno;
    }

    public void setIdAluno(Long idAluno) {
        this.idAluno = idAluno;
    }

    public Plano getPlano() {
        return plano;
    }

    public void setPlano(Plano plano) {
        this.plano = plano;
    }

    public Long getIdPlano() {
        return idPlano;
    }

    public void setIdPlano(Long idPlano) {
        this.idPlano = idPlano;
    }

    public Long getIdPlanoNovo() {
        return idPlanoNovo;
    }

    public void setIdPlanoNovo(Long idPlanoNovo) {
        this.idPlanoNovo = idPlanoNovo;
    }
}
