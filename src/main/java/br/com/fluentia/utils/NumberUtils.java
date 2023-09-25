package br.com.fluentia.utils;

import org.apache.commons.lang3.StringUtils;

import java.text.DecimalFormat;

public class NumberUtils {

    /**
     * Formata um número conforme o padrão informado
     * @param numero
     * @param pattern
     * @return
     */
    public static String formataNumero(double numero, String pattern){
        DecimalFormat format = new DecimalFormat(pattern);
        return format.format(numero);
    }

    /**
     * Formata um número com duas casas decimais
     * @param numero
     * @return
     */
    public static String formataNumero(double numero){
        String valor = formataNumero(numero, "#,##0.00");
        String numeroFormatado = valor.replaceAll(",",".");
        return numeroFormatado;
    }

    public static String removeCaracterNaoNumerico(String valor) {
        if (StringUtils.isBlank(valor)) {
            return valor;
        }
        return valor.replaceAll("\\D", "");
    }
}
