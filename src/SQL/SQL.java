/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package SQL;

import com.mysql.jdbc.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

/**
 *
 * @author samu_
 */
public class SQL {
    static Connection con=null;
    
    public static Connection conexion(){
        
        try{
            String url = "jdbc:mysql://Localhost:3306/compilador";
            Class.forName("com.mysql.jdbc.Driver").newInstance();
            con = (Connection) DriverManager.getConnection(url, "root","rootroot");
            if(con!=null){
//                System.out.println("Se ha establecido una conexión a la base de datos");
            }
        }catch(ClassNotFoundException | IllegalAccessException | InstantiationException | SQLException e){
//            System.out.println("No hay conexión");
        }
        return con;
    }
    
    public static ResultSet execQuery(String Query) throws SQLException{
        ResultSet rs = null;
        Connection con = conexion();
        Statement st = con.createStatement();
        rs = st.executeQuery(Query);
        return rs;
    }
    
    public static ResultSet execQueryPS(PreparedStatement ps)throws SQLException{
        Connection con = conexion();
        ResultSet rs = ps.executeQuery();
        return rs;
    }
    
    public static PreparedStatement getPreparedStatement(String Query)throws SQLException{
        PreparedStatement ps = conexion().prepareStatement(Query);
        return ps;
    }
}
