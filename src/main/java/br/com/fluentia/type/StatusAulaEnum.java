package br.com.fluentia.type;

public enum StatusAulaEnum {

    ATIVO(1),
    INATIVO(2);

    private final Integer status;

    StatusAulaEnum(Integer integer){
        this.status = integer;
    }

    public Integer getStatus(){return status;};
}
