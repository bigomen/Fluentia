package br.com.fluentia.type;

public enum ModalidadeEnum {
	ONLINE("ONLINE"),
	PRESENCIAL("PRESENCIAL");
	
	private String descricao;
	
	private ModalidadeEnum(String string) {
		// TODO Auto-generated constructor stub
	}

	public String getDescricao() {
		return descricao;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}

	public static String getModalidade(Long i) {
		var key = i.intValue();
		switch (key) {
		case 1:
			return ModalidadeEnum.ONLINE.descricao;
		case 2:
			return ModalidadeEnum.PRESENCIAL.descricao;
			
		default:
			return null;
		}
	}
}
