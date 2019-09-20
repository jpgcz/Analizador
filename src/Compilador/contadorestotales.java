/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Compilador;

/**
 *
 * @author Said
 */
public class contadorestotales {
    int error,identificador,comentarios,palabrasreservadas,cedec,cebin,cehex,ceoct,ctexto,cfloat,cncomp,ccar,aritmeticos,monogamo,logico,relacionales,bit,identidad,puntucion,agrupacion,asignacion;

    public contadorestotales(int error, int identificador, int comentarios, int palabrasreservadas, int cedec, int cebin, int cehex, int ceoct, int ctexto, int cfloat, int cncomp, int ccar, int aritmeticos, int monogamo, int logico, int relacionales, int bit, int identidad, int puntucion, int agrupacion, int asignacion) {
        this.error = error;
        this.identificador = identificador;
        this.comentarios = comentarios;
        this.palabrasreservadas = palabrasreservadas;
        this.cedec = cedec;
        this.cebin = cebin;
        this.cehex = cehex;
        this.ceoct = ceoct;
        this.ctexto = ctexto;
        this.cfloat = cfloat;
        this.cncomp = cncomp;
        this.ccar = ccar;
        this.aritmeticos = aritmeticos;
        this.monogamo = monogamo;
        this.logico = logico;
        this.relacionales = relacionales;
        this.bit = bit;
        this.identidad = identidad;
        this.puntucion = puntucion;
        this.agrupacion = agrupacion;
        this.asignacion = asignacion;
    }

    public int getError() {
        return error;
    }

    public void setError(int error) {
        this.error = error;
    }

    public int getIdentificador() {
        return identificador;
    }

    public void setIdentificador(int identificador) {
        this.identificador = identificador;
    }

    public int getComentarios() {
        return comentarios;
    }

    public void setComentarios(int comentarios) {
        this.comentarios = comentarios;
    }

    public int getPalabrasreservadas() {
        return palabrasreservadas;
    }

    public void setPalabrasreservadas(int palabrasreservadas) {
        this.palabrasreservadas = palabrasreservadas;
    }

    public int getCedec() {
        return cedec;
    }

    public void setCedec(int cedec) {
        this.cedec = cedec;
    }

    public int getCebin() {
        return cebin;
    }

    public void setCebin(int cebin) {
        this.cebin = cebin;
    }

    public int getCehex() {
        return cehex;
    }

    public void setCehex(int cehex) {
        this.cehex = cehex;
    }

    public int getCeoct() {
        return ceoct;
    }

    public void setCeoct(int ceoct) {
        this.ceoct = ceoct;
    }

    public int getCtexto() {
        return ctexto;
    }

    public void setCtexto(int ctexto) {
        this.ctexto = ctexto;
    }

    public int getCfloat() {
        return cfloat;
    }

    public void setCfloat(int cfloat) {
        this.cfloat = cfloat;
    }

    public int getCncomp() {
        return cncomp;
    }

    public void setCncomp(int cncomp) {
        this.cncomp = cncomp;
    }

    public int getCcar() {
        return ccar;
    }

    public void setCcar(int ccar) {
        this.ccar = ccar;
    }

    public int getAritmeticos() {
        return aritmeticos;
    }

    public void setAritmeticos(int aritmeticos) {
        this.aritmeticos = aritmeticos;
    }

    public int getMonogamo() {
        return monogamo;
    }

    public void setMonogamo(int monogamo) {
        this.monogamo = monogamo;
    }

    public int getLogico() {
        return logico;
    }

    public void setLogico(int logico) {
        this.logico = logico;
    }

    public int getRelacionales() {
        return relacionales;
    }

    public void setRelacionales(int relacionales) {
        this.relacionales = relacionales;
    }

    public int getBit() {
        return bit;
    }

    public void setBit(int bit) {
        this.bit = bit;
    }

    public int getIdentidad() {
        return identidad;
    }

    public void setIdentidad(int identidad) {
        this.identidad = identidad;
    }

    public int getPuntucion() {
        return puntucion;
    }

    public void setPuntucion(int puntucion) {
        this.puntucion = puntucion;
    }

    public int getAgrupacion() {
        return agrupacion;
    }

    public void setAgrupacion(int agrupacion) {
        this.agrupacion = agrupacion;
    }

    public int getAsignacion() {
        return asignacion;
    }

    public void setAsignacion(int asignacion) {
        this.asignacion = asignacion;
    }
    
    


    }
