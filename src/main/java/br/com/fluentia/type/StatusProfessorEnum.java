package br.com.fluentia.type;

public enum StatusProfessorEnum {

    ATIVO(1),
    INATIVO(2);

    private final Integer integer;

    StatusProfessorEnum(Integer integer){
        this.integer = integer;
    }

    public Integer getStatus(){
        return integer;
    }
}
