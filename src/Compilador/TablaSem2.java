/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Compilador;

/**
 *
 * @author samu_
 */
public class TablaSem2 {
    public String regla;
    public String topePila;
    public String valorReal;
    public int linea;
    public String estado;
    public int ambito;

    public TablaSem2(String regla, String topePila, String valorReal, int linea, String estado, int ambito) {
        this.regla = regla;
        this.topePila = topePila;
        this.valorReal = valorReal;
        this.linea = linea;
        this.estado = estado;
        this.ambito = ambito;
    }
    
    
}
