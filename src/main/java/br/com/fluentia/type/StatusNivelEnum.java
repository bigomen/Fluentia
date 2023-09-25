package br.com.fluentia.type;

public enum StatusNivelEnum {
    ATIVO(1),
    INATIVO(2);

    private final Integer status;

    StatusNivelEnum(Integer integer){
        this.status = integer;
    }

    public Integer getStatus(){return status;};
}
