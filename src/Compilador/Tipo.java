/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Compilador;

/**
 *
 * @author yesidsanchezer
 */
public enum Tipo {
    NUMERO("^\\d+$"),
    OPERADOR_BINARIO("[*|/|+|-|=]$"),
    PALABRAS_RESERVADAS("(INICIO|FIN|ESCRIBIR|LEER|MIENTRAS|FINMQ|PARA|FINPARA|SI|SINO|CASE|BREAK|DEFAULT|"
            + "ENTERO|CADENA|FLOAT)$"),
    IDENTIFICADORES("^&[A-Za-z]+$"),
    SIMBOLO("[;]$");

    public final String patron;

    Tipo(String s) {
        this.patron = s;

    }
}
