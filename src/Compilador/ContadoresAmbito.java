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
public class ContadoresAmbito {
    public int ambito,decimal,binario,octal,hexadecimal, flotante,cadena,caracter,compleja,booleana,none,arreglo,tuplas,lista,rango,conjunto,diccionarios;
    public int ambitos;

    public ContadoresAmbito(int ambito, int decimal, int binario, int octal, int hexadecimal, int flotante, int cadena, int caracter, int compleja, int booleana, int none, int arreglo, int tuplas, int lista, int rango, int conjunto, int diccionarios, int ambitos) {
        this.ambito = ambito;
        this.decimal = decimal;
        this.binario = binario;
        this.octal = octal;
        this.hexadecimal = hexadecimal;
        this.flotante = flotante;
        this.cadena = cadena;
        this.caracter = caracter;
        this.compleja = compleja;
        this.booleana = booleana;
        this.none = none;
        this.arreglo = arreglo;
        this.tuplas = tuplas;
        this.lista = lista;
        this.rango = rango;
        this.conjunto = conjunto;
        this.diccionarios = diccionarios;
        this.ambitos = ambitos;
    }

    @Override
    public String toString(){
        return "Ambito: "+ambito;
    }
}
