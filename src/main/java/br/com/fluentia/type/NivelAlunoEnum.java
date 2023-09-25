package br.com.fluentia.type;

public enum NivelAlunoEnum {

    E1("Essentials 1 ", 15L),
    E2("Essentials 2", 16L),
    I1("Inter 1", 24L),
    I2("Inter 2", 18L),
    P1("Pro 1", 25L),
    P2("Pro 2", 20L),
    M("Master", 21L);

	
	
    private String descricao;
    private Long id;

    NivelAlunoEnum(String descricao, long id) {
		this.descricao = descricao;
		this.id = id;
	}

	public static NivelAlunoEnum getById(Long id) {
        for (NivelAlunoEnum nivel : values()) {
            if (nivel.getId().equals(id)) {
                return nivel;
            }
        }
        throw new IllegalArgumentException("ID de nível de aluno inválido: " + id);
    }

	public String getDescricao() {
		return descricao;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}
	
	
	
}