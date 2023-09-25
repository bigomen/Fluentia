package br.com.fluentia.type;

public enum StatusTipoAulaEnum {

    ATIVO(1),
    INATIVO(2);

    private final Integer status;

    StatusTipoAulaEnum(Integer integer){
        this.status = integer;
    }

    public Integer getStatus(){
        return status;
    }
}
