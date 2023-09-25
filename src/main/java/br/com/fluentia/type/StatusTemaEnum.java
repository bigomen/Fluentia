package br.com.fluentia.type;

public enum StatusTemaEnum {

    ATIVO(1),
    INATIVO(2);

    private Integer status;

    StatusTemaEnum(Integer integer){
        this.status = integer;
    }

    public Integer getStatus(){
        return status;
    }
}
