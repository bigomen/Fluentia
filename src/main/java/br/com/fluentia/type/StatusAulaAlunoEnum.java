package br.com.fluentia.type;

public enum StatusAulaAlunoEnum {
	DISPONIVEL("DISPONIVEL"),
	AGENDADA("AGENDADA");
	
	private final String descricao;
	
	StatusAulaAlunoEnum(String string) {
		this.descricao = string;
	}

	public String getDescricao() {
		return descricao;
	}

}
