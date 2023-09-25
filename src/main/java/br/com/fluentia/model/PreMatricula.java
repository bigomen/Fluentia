package br.com.fluentia.model;

import br.com.fluentia.dto.PreMatriculaDto;
import jakarta.persistence.*;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import java.io.Serial;
import java.io.Serializable;

@Entity
@Table(name = "pre_matricula")
@DynamicInsert
@DynamicUpdate
public class PreMatricula implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
	@Column(name = "id")
    private Long id;

    @Column(unique = true,name = "nome")
    private String nome;

    @Column(unique = true, name = "email")
    private String email;

    @Column(name = "celular")
    private String celular;

	@Column(name = "empresa_id")
	private Long idEmpresa;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "empresa_id", referencedColumnName = "id", updatable = false, insertable = false)
	private Empresa empresa;

    public PreMatriculaDto modelToDto () {
        PreMatriculaDto dto = new PreMatriculaDto();
        dto.setId(this.id);
        dto.setNome(this.nome);
        dto.setEmail(this.email);
        dto.setCelular(this.celular);
		if(this.empresa != null){
			dto.setEmpresa(this.empresa.modelToDto());
		}
		dto.setIdEmpresa(this.idEmpresa);
        return dto;
    }

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getCelular() {
		return celular;
	}

	public void setCelular(String celular) {
		this.celular = celular;
	}

	public Long getIdEmpresa() {
		return idEmpresa;
	}

	public void setIdEmpresa(Long idEmpresa) {
		this.idEmpresa = idEmpresa;
	}

	public Empresa getEmpresa() {
		return empresa;
	}

	public void setEmpresa(Empresa empresa) {
		this.empresa = empresa;
	}
}