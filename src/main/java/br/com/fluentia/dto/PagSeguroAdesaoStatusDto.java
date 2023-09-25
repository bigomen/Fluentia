package br.com.fluentia.dto;

import br.com.fluentia.type.PagSeguroAdesaoStatusEnum;


public class PagSeguroAdesaoStatusDto {
    private PagSeguroAdesaoStatusEnum status;

	public PagSeguroAdesaoStatusEnum getStatus() {
		return status;
	}

	public void setStatus(PagSeguroAdesaoStatusEnum status) {
		this.status = status;
	}
    
    public PagSeguroAdesaoStatusDto(PagSeguroAdesaoStatusEnum status) {
    	this.status = status;
    }
    
}