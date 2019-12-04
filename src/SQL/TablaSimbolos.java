/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package SQL;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author samu_
 */
public class TablaSimbolos {
    //Crear un metodo que verifica si existe un id en el mismo ambito

    static String MeterDatos = "INSERT into tablasimbolos (id,tipo,clase,ambito,tarr,ambCreado,valor,noPosicion,llave,listaPertenece,rango,avance) VALUES (?,?,?,?,?,?,?,?,?,?,?,?)";
    static String updateFuncion = "UPDATE tablasimbolos set tarr = ? WHERE id = ? and ambito = ?";
    static String updateFuncType = "UPDATE tablasimbolos set tipo = ?, clase = ?  WHERE id = ? and ambito = ?";
    static String updateConjunDicc = "UPDATE tablasimbolos set tipo = ?, clase = ?, tarr = ?  WHERE id = ? and ambito = ?";
    static String buscarID = "SELECT * FROM tablasimbolos WHERE id = ? and ambito = ?";
    static String contarTipos = "select count(*) from tablasimbolos where tipo= ? and ambito = ?";
    static String contarClases = "select count(*) from tablasimbolos where clase= ? and ambito = ?";
    static String getDatos = "SELECT * FROM tablasimbolos";
    static String contarTiposTodos = "select count(*) from tablasimbolos where tipo= ?";
    static String contarClasesTodos = "select count(*) from tablasimbolos where clase= ?";
    static String getTipo = "SELECT tipo from tablasimbolos where id = ? and ambito = ?";
    static String getClase = "SELECT clase from tablasimbolos where id = ? and ambito = ?";
    static String getTarr = "SELECT tarr from tablasimbolos where id = ? and ambito = ?";

    public static void registrarFuncion(String id, String amb, String AmbitoCreado) {
        try {
            PreparedStatement ps = SQL.getPreparedStatement(MeterDatos);
            ps.setString(1, id); //id
            ps.setString(2, "None");//tipo
            ps.setString(3, "Funcion"); //clase
            ps.setString(4, amb); //Ambito
            ps.setString(5, ""); // tarr
            ps.setString(6, AmbitoCreado); //ambCreado
            ps.setString(7, ""); // valor
            ps.setString(8, ""); //noPosicion
            ps.setString(9, ""); //llave
            ps.setString(10, ""); //listaPertenece
            ps.setString(11, ""); //rango
            ps.setString(12, ""); //avance
            ps.executeUpdate();
            ps.close();
            SQL.conexion().close();
        } catch (SQLException ex) {
            Logger.getLogger(TablaSimbolos.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public static void registrarProcedimiento(String id, String amb, String AmbitoCreado) {
        try {
            PreparedStatement ps = SQL.getPreparedStatement(MeterDatos);
            ps.setString(1, id); //id
            ps.setString(2, "None");//tipo
            ps.setString(3, "Procedimiento"); //clase
            ps.setString(4, amb); //Ambito
            ps.setString(5, ""); // tarr
            ps.setString(6, AmbitoCreado); //ambCreado
            ps.setString(7, ""); // valor
            ps.setString(8, ""); //noPosicion
            ps.setString(9, ""); //llave
            ps.setString(10, ""); //listaPertenece
            ps.setString(11, ""); //rango
            ps.setString(12, ""); //avance
            ps.executeUpdate();
            ps.close();
            SQL.conexion().close();
        } catch (SQLException ex) {
            Logger.getLogger(TablaSimbolos.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public static void actualizarFuncion(String id, String amb, String tarr) {
        try {
            PreparedStatement ps = SQL.getPreparedStatement(updateFuncion);
            ps.setString(1, tarr);
            ps.setString(2, id);
            ps.setString(3, amb);
            ps.executeUpdate();
            ps.close();
            SQL.conexion().close();
        } catch (SQLException ex) {
            Logger.getLogger(TablaSimbolos.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public static void actualizarFuncTipoClase(String id, String amb, String tipo){
        try {
            PreparedStatement ps = SQL.getPreparedStatement(updateFuncType);
            ps.setString(1, tipo);
            ps.setString(2, "Funcion");
            ps.setString(3, id);
            ps.setString(4, amb);
            ps.executeUpdate();
            ps.close();
            SQL.conexion().close();
        } catch (SQLException ex) {
            Logger.getLogger(TablaSimbolos.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public static void registrarParamFunc(String id, String amb, String listaPertenece, String noPosicion) {
        try {
            PreparedStatement ps = SQL.getPreparedStatement(MeterDatos);
            ps.setString(1, id); //id
            ps.setString(2, "None");//tipo
            ps.setString(3, "Parametro"); //clase
            ps.setString(4, amb); //Ambito
            ps.setString(5, ""); // tarr
            ps.setString(6, ""); //ambCreado
            ps.setString(7, ""); // valor
            ps.setString(8, noPosicion); //noPosicion
            ps.setString(9, ""); //llave
            ps.setString(10, listaPertenece); //listaPertenece
            ps.setString(11, ""); //rango
            ps.setString(12, ""); //avance
            ps.executeUpdate();
            ps.close();
            SQL.conexion().close();
        } catch (SQLException ex) {
            Logger.getLogger(TablaSimbolos.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public static void registrarVariable(String id, String tipo, String amb) {
        try {
            PreparedStatement ps = SQL.getPreparedStatement(MeterDatos);
            ps.setString(1, id); //id
            ps.setString(2, tipo);//tipo
            ps.setString(3, "Variable"); //clase
            ps.setString(4, amb); //Ambito
            ps.setString(5, ""); // tarr
            ps.setString(6, ""); //ambCreado
            ps.setString(7, ""); // valor
            ps.setString(8, ""); //noPosicion
            ps.setString(9, ""); //llave
            ps.setString(10, ""); //listaPertenece
            ps.setString(11, ""); //rango
            ps.setString(12, ""); //avance
            ps.executeUpdate();
            ps.close();
            SQL.conexion().close();
        } catch (SQLException ex) {
            Logger.getLogger(TablaSimbolos.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public static void registrarRango(String id, String amb, String rango1, String rango2, String avance) {
        try {
            PreparedStatement ps = SQL.getPreparedStatement(MeterDatos);
            ps.setString(1, id); //id
            ps.setString(2, "Struct");//tipo
            ps.setString(3, "Rango"); //clase
            ps.setString(4, amb); //Ambito
            ps.setString(5, ""); // tarr
            ps.setString(6, ""); //ambCreado
            ps.setString(7, ""); // valor
            ps.setString(8, ""); //noPosicion
            ps.setString(9, ""); //llave
            ps.setString(10, ""); //listaPertenece
            ps.setString(11, rango1 + "," + rango2); //rango
            ps.setString(12, avance); //avance
            ps.executeUpdate();
            ps.close();
            SQL.conexion().close();
        } catch (SQLException ex) {
            Logger.getLogger(TablaSimbolos.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public static void registrarTupla(String id, String amb, String ambCreado) {
        try {
            PreparedStatement ps = SQL.getPreparedStatement(MeterDatos);
            ps.setString(1, id); //id
            ps.setString(2, "Struct");//tipo
            ps.setString(3, "Tupla"); //clase
            ps.setString(4, amb); //Ambito
            ps.setString(5, ""); // tarr
            ps.setString(6, ambCreado); //ambCreado
            ps.setString(7, ""); // valor
            ps.setString(8, ""); //noPosicion
            ps.setString(9, ""); //llave
            ps.setString(10, ""); //listaPertenece
            ps.setString(11, ""); //rango
            ps.setString(12, ""); //avance
            ps.executeUpdate();
            ps.close();
            SQL.conexion().close();
        } catch (SQLException ex) {
            Logger.getLogger(TablaSimbolos.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public static void registrarDatoTupla(String tipo, String amb, String noPosicion, String listaPertenece) {
        try {
            PreparedStatement ps = SQL.getPreparedStatement(MeterDatos);
            ps.setString(1, ""); //id
            ps.setString(2, tipo);//tipo
            ps.setString(3, "DatoTupla"); //clase
            ps.setString(4, amb); //Ambito
            ps.setString(5, ""); // tarr
            ps.setString(6, ""); //ambCreado
            ps.setString(7, ""); // valor
            ps.setString(8, noPosicion); //noPosicion
            ps.setString(9, ""); //llave
            ps.setString(10, listaPertenece); //listaPertenece
            ps.setString(11, ""); //rango
            ps.setString(12, ""); //avance
            ps.executeUpdate();
            ps.close();
            SQL.conexion().close();
        } catch (SQLException ex) {
            Logger.getLogger(TablaSimbolos.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public static void registrarIdNoDefinido(String id, String amb, String ambitoCreado) {
        try {
            PreparedStatement ps = SQL.getPreparedStatement(MeterDatos);
            ps.setString(1, id); //id
            ps.setString(2, "");//tipo
            ps.setString(3, ""); //clase
            ps.setString(4, amb); //Ambito
            ps.setString(5, ""); // tarr
            ps.setString(6, ambitoCreado); //ambCreado
            ps.setString(7, ""); // valor
            ps.setString(8, ""); //noPosicion
            ps.setString(9, ""); //llave
            ps.setString(10, ""); //listaPertenece
            ps.setString(11, ""); //rango
            ps.setString(12, ""); //avance
            ps.executeUpdate();
            ps.close();
            SQL.conexion().close();
        } catch (SQLException ex) {
            Logger.getLogger(TablaSimbolos.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public static void actualizarConjunDicc(String clase, String tarr, String id, String amb) {
        try {
            PreparedStatement ps = SQL.getPreparedStatement(updateConjunDicc);
            ps.setString(1, "Struct");
            ps.setString(2, clase);
            ps.setString(3, tarr);
            ps.setString(4, id);
            ps.setString(5, amb);
            ps.executeUpdate();
            ps.close();
            SQL.conexion().close();
        } catch (SQLException ex) {
            Logger.getLogger(TablaSimbolos.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public static void registrarDatoConjun(String tipo, String amb, String noPosicion, String listaPertenece) {
        try {
            PreparedStatement ps = SQL.getPreparedStatement(MeterDatos);
            ps.setString(1, ""); //id
            ps.setString(2, tipo);//tipo
            ps.setString(3, "datoConj"); //clase
            ps.setString(4, amb); //Ambito
            ps.setString(5, ""); // tarr
            ps.setString(6, ""); //ambCreado
            ps.setString(7, ""); // valor
            ps.setString(8, noPosicion); //noPosicion
            ps.setString(9, ""); //llave
            ps.setString(10, listaPertenece); //listaPertenece
            ps.setString(11, ""); //rango
            ps.setString(12, ""); //avance
            ps.executeUpdate();
            ps.close();
            SQL.conexion().close();
        } catch (SQLException ex) {
            Logger.getLogger(TablaSimbolos.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public static void registrarDatoDicc(String tipo, String amb, String noPosicion, String listaPertenece, String llave, String valor) {
        try {
            PreparedStatement ps = SQL.getPreparedStatement(MeterDatos);
            ps.setString(1, ""); //id
            ps.setString(2, tipo);//tipo
            ps.setString(3, "datoConj"); //clase
            ps.setString(4, amb); //Ambito
            ps.setString(5, ""); // tarr
            ps.setString(6, ""); //ambCreado
            ps.setString(7, valor); // valor
            ps.setString(8, noPosicion); //noPosicion
            ps.setString(9, llave); //llave
            ps.setString(10, listaPertenece); //listaPertenece
            ps.setString(11, ""); //rango
            ps.setString(12, ""); //avance
            ps.executeUpdate();
            ps.close();
            SQL.conexion().close();
        } catch (SQLException ex) {
            Logger.getLogger(TablaSimbolos.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public static void registrarArr(String id, String amb, String tarr, String rango) {
        try {
            PreparedStatement ps = SQL.getPreparedStatement(MeterDatos);
            ps.setString(1, id); //id
            ps.setString(2, "Struct");//tipo
            ps.setString(3, "Arreglo"); //clase
            ps.setString(4, amb); //Ambito
            ps.setString(5, tarr); // tarr
            ps.setString(6, ""); //ambCreado
            ps.setString(7, ""); // valor
            ps.setString(8, ""); //noPosicion
            ps.setString(9, ""); //llave
            ps.setString(10, ""); //listaPertenece
            ps.setString(11, rango); //rango
            ps.setString(12, ""); //avance
            ps.executeUpdate();
            ps.close();
            SQL.conexion().close();
        } catch (SQLException ex) {
            Logger.getLogger(TablaSimbolos.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public static boolean buscar(String id, String amb) {
        boolean b = false;
        try {
            PreparedStatement ps = SQL.getPreparedStatement(buscarID);
            ps.setString(1, id);
            ps.setString(2, amb);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                b = true;
            }
            rs.close();
            ps.close();
        } catch (SQLException ex) {
            Logger.getLogger(TablaSimbolos.class.getName()).log(Level.SEVERE, null, ex);
        }
        return b;
    }

    public static void borrarTabla() {
        try {
            PreparedStatement ps = SQL.getPreparedStatement("DELETE FROM tablasimbolos");
            ps.executeUpdate();
            ps.close();
            SQL.conexion().close();
        } catch (SQLException ex) {
            Logger.getLogger(TablaSimbolos.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public static int contarTipos(String tipo, String amb) {
        int cont = 0;
        try {
            PreparedStatement ps = SQL.getPreparedStatement(contarTipos);
            ps.setString(1, tipo);
            ps.setString(2, amb);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                cont = rs.getInt(1);
            }
            rs.close();
            ps.close();
        } catch (SQLException ex) {
            Logger.getLogger(TablaSimbolos.class.getName()).log(Level.SEVERE, null, ex);
        }
        return cont;
    }

    public static int contarClases(String clase, String amb) {
        int cont = 0;
        try {
            PreparedStatement ps = SQL.getPreparedStatement(contarClases);
            ps.setString(1, clase);
            ps.setString(2, amb);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                cont = rs.getInt(1);
            }
            SQL.conexion().close();
        } catch (SQLException ex) {
            Logger.getLogger(TablaSimbolos.class.getName()).log(Level.SEVERE, null, ex);
        }
        return cont;
    }

    public static int contarTiposTodos(String tipo) {
        int cont = 0;
        try {
            PreparedStatement ps = SQL.getPreparedStatement(contarTiposTodos);
            ps.setString(1, tipo);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                cont = rs.getInt(1);
            }
            rs.close();
            ps.close();
            SQL.conexion().close();
        } catch (SQLException ex) {
            Logger.getLogger(TablaSimbolos.class.getName()).log(Level.SEVERE, null, ex);
        }
        return cont;
    }

    public static int contarClasesTodos(String clase) {
        int cont = 0;
        try {
            PreparedStatement ps = SQL.getPreparedStatement(contarClasesTodos);
            ps.setString(1, clase);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                cont = rs.getInt(1);
            }
            rs.close();
            ps.close();
            SQL.conexion().close();
        } catch (SQLException ex) {
            Logger.getLogger(TablaSimbolos.class.getName()).log(Level.SEVERE, null, ex);
        }
        return cont;
    }

    public static LinkedList<String[]> obtenerDatos() {
        String[] datos = new String[12];
        LinkedList<String[]> listaDatos = new LinkedList();
        for (int i = 0; i < datos.length; i++) {
            datos[i] = "";
        }
        try {
            ResultSet rs = SQL.execQuery(getDatos);
            while (rs.next()) {
                for (int i = 0; i < datos.length; i++) {
                    datos[i] = rs.getString(i + 1);
                }
                listaDatos.add(datos.clone());
            }
            rs.close();
            SQL.conexion().close();
            return listaDatos;
        } catch (SQLException ex) {
            Logger.getLogger(TablaSimbolos.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    public static String ObtenerTipo(String id, String ambito) {
        String tipo = null;
        try {
            PreparedStatement ps = SQL.getPreparedStatement(getTipo);
            ps.setString(1, id);
            ps.setString(2, ambito);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                tipo = rs.getString(1);
            }
            rs.close();
            ps.close();
        } catch (SQLException ex) {
            Logger.getLogger(TablaSimbolos.class.getName()).log(Level.SEVERE, null, ex);
        }
        return tipo;
    }
    
    public static String ObtenerClase(String id, String ambito) {
        String tipo = null;
        try {
            PreparedStatement ps = SQL.getPreparedStatement(getClase);
            ps.setString(1, id);
            ps.setString(2, ambito);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                tipo = rs.getString(1);
            }
            rs.close();
            ps.close();
            SQL.conexion().close();
        } catch (SQLException ex) {
            Logger.getLogger(TablaSimbolos.class.getName()).log(Level.SEVERE, null, ex);
        }
        return tipo;
    }
    
    public static int ObtenerNumParametros(String id, String ambito){
        int num = -1;
        try {
            PreparedStatement ps = SQL.getPreparedStatement(getTarr);
            ps.setString(1, id);
            ps.setString(2, ambito);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                num = rs.getInt(1);
            }
            rs.close();
            ps.close();
            SQL.conexion().close();
        } catch (SQLException ex) {
            Logger.getLogger(TablaSimbolos.class.getName()).log(Level.SEVERE, null, ex);
        }
        return num;
    }
    
}
