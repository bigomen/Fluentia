package br.com.fluentia.dto;

public class PaginacaoDto {

    private Integer sortOrder;
    private String sortField;
    private Object filters;
    private Object globalFilter;
    private Integer rows;
    private Integer first;

    public Integer getSortOrder() {
        return sortOrder;
    }

    public void setSortOrder(Integer sortOrder) {
        this.sortOrder = sortOrder;
    }

    public String getSortField() {
        return sortField;
    }

    public void setSortField(String sortField) {
        this.sortField = sortField;
    }

    public Object getFilters() {
        return filters;
    }

    public void setFilters(Object filters) {
        this.filters = filters;
    }

    public Object getGlobalFilter() {
        return globalFilter;
    }

    public void setGlobalFilter(Object globalFilter) {
        this.globalFilter = globalFilter;
    }

    public Integer getRows() {
        return rows;
    }

    public void setRows(Integer rows) {
        this.rows = rows;
    }

    public Integer getFirst() {
        return first;
    }

    public void setFirst(Integer first) {
        this.first = first;
    }
}
