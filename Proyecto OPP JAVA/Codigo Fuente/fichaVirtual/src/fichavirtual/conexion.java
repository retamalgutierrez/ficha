/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package fichavirtual;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;


/**
 *
 * @author Enterprise
 */
public class conexion {
    private String url="jdbc:mysql://localhost:3306/ficha?zeroDateTimeBehavior=convertToNull";
    private String driver="com.mysql.jdbc.Driver";
    private String user="root";
    private String pass="123456789";
    private Connection conexion;

    public conexion() {
        
    }
    
    public void conectar() throws ClassNotFoundException, SQLException {
        Class.forName(driver);
        conexion = DriverManager.getConnection(url, user, pass);
    }
    
    public void desconectar() throws SQLException {
        conexion.close();
    }
    
    public Connection getConection(){
        return conexion;
    }
}
