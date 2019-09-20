/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Compilador;

/**
 *
 * @author said
 */
public class LE {
    int No;
    int linea;
    String desc,lexico,tipoerror;

    public int getNo() {
        return No;
    }

    public void setNo(int No) {
        this.No = No;
    }

    public int getLinea() {
        return linea;
    }

    public void setLinea(int linea) {
        this.linea = linea;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public String getLexico() {
        return lexico;
    }

    public void setLexico(String lexico) {
        this.lexico = lexico;
    }

    public String getTipoerror() {
        return tipoerror;
    }

    public void setTipoerror(String tipoerror) {
        this.tipoerror = tipoerror;
    }

        public LE( String desc, String lexico, String tipoerror,int No, int linea) {
            this.No = No;
            this.linea = linea;
            this.desc = desc;
            this.lexico = lexico;
            this.tipoerror = tipoerror;
            
        

       

        
        }
}
