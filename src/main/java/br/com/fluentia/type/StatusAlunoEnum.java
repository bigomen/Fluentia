package br.com.fluentia.type;

public enum StatusAlunoEnum {

    ATIVO(1),
    INATIVO(2);

    private final Integer status;

    StatusAlunoEnum(Integer integer){
        this.status = integer;

    }
    public Integer getStatus(){
        return status;
    }
}
