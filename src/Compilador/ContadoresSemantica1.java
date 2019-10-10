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
public class ContadoresSemantica1 {
    public int tDec, tBin, tHex, tOct, tFlot, tCaden, tCarac, tComple, tBool, tNone, tLista, tArr, tTup, tConj,tRango, tDicc, tVariant,numLinea;
    public String asignacion = "";
    public ContadoresSemantica1(int tDec, int tBin, int tHex, int tOct, int tFlot, int tCaden, int tCarac, int tComple, int tBool, int tNone, int tLista, int tArr, int tTup, int tConj, int tRango, int tDicc, int tVariant, int numLinea) {
        this.tDec = tDec;
        this.tBin = tBin;
        this.tHex = tHex;
        this.tOct = tOct;
        this.tFlot = tFlot;
        this.tCaden = tCaden;
        this.tCarac = tCarac;
        this.tComple = tComple;
        this.tBool = tBool;
        this.tNone = tNone;
        this.tLista = tLista;
        this.tArr = tArr;
        this.tTup = tTup;
        this.tConj = tConj;
        this.tRango = tRango;
        this.tDicc = tDicc;
        this.tVariant = tVariant;
        this.numLinea = numLinea;
    }

    public void setAsignacion(String asignacion) {
        this.asignacion = asignacion;
    }

    public int getNumLinea() {
        return numLinea;
    }
    
    
}
