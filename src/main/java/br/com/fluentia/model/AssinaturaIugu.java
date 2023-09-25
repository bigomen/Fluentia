package br.com.fluentia.model;

import java.time.LocalDateTime;
import java.util.List;

public class AssinaturaIugu {

    private String customer_id;
    private LocalDateTime expires_at;
    private Boolean only_on_charge_success;
    private Boolean ignore_due_email;
    private List<String> payable_with;
    private Boolean credits_based;
    private Integer price_cents;
    private Integer credits_cycle;
    private Integer credits_min;
    private Boolean two_step;
    private Boolean suspend_on_invoice_expired;
    private Boolean only_charge_on_due_date;

    public String getCustomer_id() {
        return customer_id;
    }

    public void setCustomer_id(String customer_id) {
        this.customer_id = customer_id;
    }

    public LocalDateTime getExpires_at() {
        return expires_at;
    }

    public void setExpires_at(LocalDateTime expires_at) {
        this.expires_at = expires_at;
    }

    public Boolean getOnly_on_charge_success() {
        return only_on_charge_success;
    }

    public void setOnly_on_charge_success(Boolean only_on_charge_success) {
        this.only_on_charge_success = only_on_charge_success;
    }

    public Boolean getIgnore_due_email() {
        return ignore_due_email;
    }

    public void setIgnore_due_email(Boolean ignore_due_email) {
        this.ignore_due_email = ignore_due_email;
    }

    public List<String> getPayable_with() {
        return payable_with;
    }

    public void setPayable_with(List<String> payable_with) {
        this.payable_with = payable_with;
    }

    public Boolean getCredits_based() {
        return credits_based;
    }

    public void setCredits_based(Boolean credits_based) {
        this.credits_based = credits_based;
    }

    public Integer getPrice_cents() {
        return price_cents;
    }

    public void setPrice_cents(Integer price_cents) {
        this.price_cents = price_cents;
    }

    public Integer getCredits_cycle() {
        return credits_cycle;
    }

    public void setCredits_cycle(Integer credits_cycle) {
        this.credits_cycle = credits_cycle;
    }

    public Integer getCredits_min() {
        return credits_min;
    }

    public void setCredits_min(Integer credits_min) {
        this.credits_min = credits_min;
    }

    public Boolean getTwo_step() {
        return two_step;
    }

    public void setTwo_step(Boolean two_step) {
        this.two_step = two_step;
    }

    public Boolean getSuspend_on_invoice_expired() {
        return suspend_on_invoice_expired;
    }

    public void setSuspend_on_invoice_expired(Boolean suspend_on_invoice_expired) {
        this.suspend_on_invoice_expired = suspend_on_invoice_expired;
    }

    public Boolean getOnly_charge_on_due_date() {
        return only_charge_on_due_date;
    }

    public void setOnly_charge_on_due_date(Boolean only_charge_on_due_date) {
        this.only_charge_on_due_date = only_charge_on_due_date;
    }
}
