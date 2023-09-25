package br.com.fluentia.model;

import java.util.List;

public class PlanoIugu{

    private String id;
    private String name;
    private String identifier;
    private Integer interval;
    private String interval_type;
    private Integer value_cents;
    private List<String> payable_with;
    private Integer billing_days;
    private Integer max_cycles;
    private Integer invoice_max_installments;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getIdentifier() {
        return identifier;
    }

    public void setIdentifier(String identifier) {
        this.identifier = identifier;
    }

    public Integer getInterval() {
        return interval;
    }

    public void setInterval(Integer interval) {
        this.interval = interval;
    }

    public String getInterval_type() {
        return interval_type;
    }

    public void setInterval_type(String interval_type) {
        this.interval_type = interval_type;
    }

    public Integer getValue_cents() {
        return value_cents;
    }

    public void setValue_cents(Integer value_cents) {
        this.value_cents = value_cents;
    }

    public List<String> getPayable_with() {
        return payable_with;
    }

    public void setPayable_with(List<String> payable_with) {
        this.payable_with = payable_with;
    }

    public Integer getBilling_days() {
        return billing_days;
    }

    public void setBilling_days(Integer billing_days) {
        this.billing_days = billing_days;
    }

    public Integer getMax_cycles() {
        return max_cycles;
    }

    public void setMax_cycles(Integer max_cycles) {
        this.max_cycles = max_cycles;
    }

    public Integer getInvoice_max_installments() {
        return invoice_max_installments;
    }

    public void setInvoice_max_installments(Integer invoice_max_installments) {
        this.invoice_max_installments = invoice_max_installments;
    }
}
