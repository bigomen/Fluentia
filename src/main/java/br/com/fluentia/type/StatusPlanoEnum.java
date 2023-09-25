package br.com.fluentia.type;

public enum StatusPlanoEnum{

	ATIVO(1),
	INATIVO(2);

	private final Integer status;

	StatusPlanoEnum(Integer integer){
		this.status = integer;
	}

	public Integer getStatus(){
		return status;
	}
}