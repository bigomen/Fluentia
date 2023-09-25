package br.com.fluentia.dto;

import br.com.fluentia.model.AlunoPlano;

import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalDateTime;

public class AlunoPlanoDto implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    private AlunoDto aluno;
    private Long idAluno;
    private PlanoDto plano;
    private Long idPlano;
    private Long idPlanoNovo;
    private LocalDateTime dataAdesao;
    private LocalDateTime dataCancelamento;
    private LocalDateTime dataReativacao;
    private LocalDateTime dataSuspensao;
    private LocalDate dataTrocaAdesao;
    private Boolean enviaEmail;
    private String motivoCancelamento;


    public AlunoPlano dtoToModel(){
        AlunoPlano model = new AlunoPlano();
        model.setDataAdesao(this.dataAdesao);
        model.setDataCancelamento(this.dataCancelamento);
        model.setDataReativacao(this.dataReativacao);
        model.setDataSuspensao(this.dataSuspensao);
        model.setDataTrocaAdesao(this.dataTrocaAdesao);
        model.setEnviaEmail(this.enviaEmail);
        model.setMotivoCancelamento(this.motivoCancelamento);
        model.setIdPlano(this.idPlano);
        model.setIdAluno(this.idAluno);
        model.setIdPlanoNovo(this.idPlanoNovo);
        return model;
    }

    public AlunoDto getAluno() {
        return aluno;
    }

    public void setAluno(AlunoDto aluno) {
        this.aluno = aluno;
    }

    public Long getIdAluno() {
        return idAluno;
    }

    public void setIdAluno(Long idAluno) {
        this.idAluno = idAluno;
    }

    public PlanoDto getPlano() {
        return plano;
    }

    public void setPlano(PlanoDto plano) {
        this.plano = plano;
    }

    public Long getIdPlano() {
        return idPlano;
    }

    public void setIdPlano(Long idPlano) {
        this.idPlano = idPlano;
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

    public Long getIdPlanoNovo() {
        return idPlanoNovo;
    }

    public void setIdPlanoNovo(Long idPlanoNovo) {
        this.idPlanoNovo = idPlanoNovo;
    }
}
