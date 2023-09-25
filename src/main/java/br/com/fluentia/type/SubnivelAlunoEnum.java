package br.com.fluentia.type;

public enum SubnivelAlunoEnum {

    E1A21("Essentials 1 - A2.1"),
    E1A22("Essentials 1 - A2.2"),
    E1A23("Essentials 1 - A2.3"),
    E2A24("Essentials 2 - A2.4"),
    E2A25("Essentials 2 - A2.5"),
    E2A26("Essentials 2 - A2.6"),
    I1B11("Inter 1 - B1.1"),
    I1B12("Inter 1 - B1.2"),
    I1B13("Inter 1 - B1.3"),
    I2B14("Inter 2 - B1.4"),
    I2B15("Inter 2 - B1.5"),
    I2B16("Inter 2 - B1.6"),
    P1B21("Pro 1 - B2.1"),
    P1B22("Pro 1 - B2.2"),
    P1B23("Pro 1 - B2.3"),
    P2B24("Pro 2 - B2.4"),
    P2B25("Pro 2 - B2.5"),
    P2B26("Pro 2 - B2.6"),
    MC11("Master - C1.1"),
    MC12("Master - C1.2");

    private String descricao;
    
    
    SubnivelAlunoEnum(String descricao) {
		this.descricao = descricao;
	}


	public String getDescricao() {
		return descricao;
	}


	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}
    
    

}