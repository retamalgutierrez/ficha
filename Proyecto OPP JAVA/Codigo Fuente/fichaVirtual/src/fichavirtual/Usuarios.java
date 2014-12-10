/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package fichavirtual;

import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Font;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;

public class Usuarios {
    int ID;
    String NOMBRE;
    String APELLIDOS;
    String USUARIO;
    String PASS;
    String TIPO;

    public Usuarios(String TIPO) {
        this.TIPO = TIPO;
    }

    public Usuarios(int ID) {
        this.ID = ID;
    }

    public Usuarios(int ID, String NOMBRE, String APELLIDOS, String USUARIO, String PASS, String TIPO) {
        this.ID = ID;
        this.NOMBRE = NOMBRE;
        this.APELLIDOS = APELLIDOS;
        this.USUARIO = USUARIO;
        this.PASS = PASS;
        this.TIPO = TIPO;
    }

    public Usuarios(String NOMBRE, String APELLIDOS, String USUARIO, String PASS, String TIPO) {
        this.NOMBRE = NOMBRE;
        this.APELLIDOS = APELLIDOS;
        this.USUARIO = USUARIO;
        this.PASS = PASS;
        this.TIPO = TIPO;
    }
    
    public Usuarios() {
    }

    public int getID() {
        return ID;
    }

    public String getNOMBRE() {
        return NOMBRE;
    }

    public void setNOMBRE(String NOMBRE) {
        this.NOMBRE = NOMBRE;
    }

    public String getAPELLIDOS() {
        return APELLIDOS;
    }

    public void setAPELLIDOS(String APELLIDOS) {
        this.APELLIDOS = APELLIDOS;
    }

    public String getUSUARIO() {
        return USUARIO;
    }

    public void setUSUARIO(String USUARIO) {
        this.USUARIO = USUARIO;
    }

    public String getPASS() {
        return PASS;
    }

    public void setPASS(String PASS) {
        this.PASS = PASS;
    }

    public String getTIPO() {
        return TIPO;
    }

    public void setTIPO(String TIPO) {
        this.TIPO = TIPO;
    }

    public Usuarios(String USUARIO, String PASS) {
        this.USUARIO = USUARIO;
        this.PASS = PASS;
    }
    
    public ArrayList listaUsuarios(int idUser){
        ArrayList<Usuarios> lista = new ArrayList();
        conexion conn = new conexion();        
        String sql = "SELECT * FROM USUARIOS WHERE ID<>"+idUser+";";
        try {
            conn.conectar();
            PreparedStatement st = (PreparedStatement) conn.getConection().prepareStatement(sql);
            ResultSet rs = st.executeQuery(sql);//S
            while(rs.next()){
                lista.add(new Usuarios(rs.getInt(1), rs.getString(2), rs.getString(3), rs.getString(4), rs.getString(5), rs.getString(6)));
            }
            conn.desconectar();
        } catch (ClassNotFoundException ex) {            
        } catch (SQLException ex) {         
        }                       
        return lista;       
    }
    
    public boolean crearpdf(String path) throws FileNotFoundException, DocumentException{
        conexion conn = new conexion();
        boolean ok=false;
        String sql ="SELECT * FROM USUARIOS";
        try {
            conn.conectar();
            PreparedStatement st = (PreparedStatement) conn.getConection().prepareStatement(sql);
            ResultSet rs = st.executeQuery(sql);
           
            FileOutputStream archivo = new FileOutputStream(path+".pdf");
            Document documento = new Document();
            PdfWriter.getInstance(documento, archivo);
            documento.open();
            Font fuente = new Font(Font.FontFamily.COURIER);
            Font base = new Font();
            base.setStyle(Font.BOLD);
            fuente.setStyle(Font.BOLD | Font.UNDERLINE);
            fuente.setSize(20);
            documento.add(new Paragraph("LISTA USUARIOS",fuente));
            documento.add(new Paragraph("          "));
            
            PdfPTable table = new PdfPTable(4); 
            table.setWidthPercentage(100f);
            table.addCell(new Phrase("Nombre",base));
            table.addCell(new Phrase("Apellido",base));
            table.addCell(new Phrase("Usuario",base));
            table.addCell(new Phrase("Tipo",base));
           
            while(rs.next()){
  
            table.addCell(rs.getString(2));
            table.addCell(rs.getString(3));
            table.addCell(rs.getString(4));
            table.addCell(rs.getString(6));
           
           
            }
             documento.add(table);
             documento.close();
            
            ok=true;
            conn.desconectar();
        } catch (SQLException ex) {
        } catch (ClassNotFoundException ex) {
        }
         return ok; 
   }
    
    
    public ArrayList filtrarUsuarios(String filtro, int idUser){
        ArrayList<Usuarios> lista = new ArrayList();
        conexion conn = new conexion();        
        String sql = "SELECT * FROM USUARIOS WHERE ID<>"+idUser+" AND (NOMBRE LIKE '%"+filtro+"%' or APELLIDOS LIKE '%"+filtro+"%');";
        try {
            conn.conectar();
            PreparedStatement st = (PreparedStatement) conn.getConection().prepareStatement(sql);
            ResultSet rs = st.executeQuery(sql);//S
            while(rs.next()){
                lista.add(new Usuarios(rs.getInt(1), rs.getString(2), rs.getString(3), rs.getString(4), rs.getString(5), rs.getString(6)));
            }
            conn.desconectar();
        } catch (ClassNotFoundException ex) {            
        } catch (SQLException ex) {         
        }                       
        return lista;       
    }
    
    public boolean ValidarUsuario(String USUARIO, String PASS){
        boolean ok=false;
        conexion conn = new conexion();
        String sql = "SELECT USUARIO, PASS FROM USUARIOS WHERE USUARIO='"+USUARIO+"' AND PASS='"+PASS+"'";
        try{
            conn.conectar();
            PreparedStatement st = (PreparedStatement) conn.getConection().prepareStatement(sql);
            ResultSet rs = st.executeQuery(sql);
            if(rs.next()){
                ok=true;
            }
            conn.desconectar();
        } catch (SQLException ex) {
        } catch (ClassNotFoundException ex) {
        }
        
        return ok;
    }
    
    public boolean registrarUsuario(File file,String path){ 
        boolean ok=false;
        conexion conn = new conexion();
        String sql = "INSERT INTO USUARIOS(ID ,NOMBRE ,APELLIDOS ,USUARIO ,PASS ,TIPO ,FOTO, PATH) VALUES (?,?,?,?,?,?,?,?);";
        try{
            conn.conectar();
            PreparedStatement st = (PreparedStatement) conn.getConection().prepareStatement(sql);
            st.setInt(1, ID);
            st.setString(2, NOMBRE);
            st.setString(3, APELLIDOS);
            st.setString(4, USUARIO);
            st.setString(5, PASS);
            st.setString(6, TIPO);
            if(file != null)
            {
                FileInputStream fis = new FileInputStream ( file );
                st.setBinaryStream (7, fis, (int) file.length());
            }
            else
            {
                st.setBinaryStream(7,null);
            }
            st.setString(8, path);
            
            st.executeUpdate();//Insert, Update, Delete
            ok=true;
            conn.desconectar();
        } catch (SQLException | ClassNotFoundException ex) {
            JOptionPane.showMessageDialog(null, ex);
        } catch (FileNotFoundException ex) {
            Logger.getLogger(Usuarios.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return ok;
    }
    
    public InputStream ObtenerFoto(int ID){
        InputStream imgStream = null;
        String tipo = "";
        conexion conn = new conexion();
        String sql = "SELECT FOTO FROM USUARIOS WHERE ID="+ID+"";
        try{
            conn.conectar();
            PreparedStatement st = (PreparedStatement) conn.getConection().prepareStatement(sql);
            ResultSet rs = st.executeQuery(sql);
            if(rs.next()){
                imgStream = rs.getBinaryStream(1);
            }
            conn.desconectar();
        } catch (SQLException | ClassNotFoundException ex) {
            JOptionPane.showMessageDialog(null, ex);
        }
        catch(Exception ex){
            JOptionPane.showMessageDialog(null, "ERROR|"+ex);
        }
        
        return imgStream;
    }
    
    public String ObtenerPath(int ID){
        String path = "";
        conexion conn = new conexion();
        String sql = "SELECT PATH FROM USUARIOS WHERE ID="+ID+"";
        try{
            conn.conectar();
            PreparedStatement st = (PreparedStatement) conn.getConection().prepareStatement(sql);
            ResultSet rs = st.executeQuery(sql);
            if(rs.next()){
                path = rs.getString(1);
            }
            conn.desconectar();
        } catch (SQLException | ClassNotFoundException ex) {
            JOptionPane.showMessageDialog(null, ex);
        }
        
        return path;
    }
    
    public String ValidarTipoUsuario(String USUARIO){
        String tipo = "";
        conexion conn = new conexion();
        String sql = "SELECT TIPO FROM USUARIOS WHERE USUARIO='"+USUARIO+"'";
        try{
            conn.conectar();
            PreparedStatement st = (PreparedStatement) conn.getConection().prepareStatement(sql);
            ResultSet rs = st.executeQuery(sql);
            if(rs.next()){
                tipo= rs.getString(1);
            }
            conn.desconectar();
        } catch (SQLException ex) {
        } catch (ClassNotFoundException ex) {
        }
        
        return tipo;
    }
    
    public boolean borrarUsuario(int Id){
        boolean ok=false;
        conexion conn = new conexion();
        String sql = "DELETE FROM USUARIOS WHERE ID="+Id;
        try{
            conn.conectar();
            PreparedStatement st = (PreparedStatement) conn.getConection().prepareStatement(sql);
            st.executeUpdate();//Insert, Update, Delete
            ok=true;
            conn.desconectar();
        } catch (SQLException ex) {
        } catch (ClassNotFoundException ex) {
        }
        
        return ok;
    }
    
    public boolean ActualizarUsuario(int ID, String NOMBRE, String APELLIDOS, String USUARIO, String PASS, String TIPO, File file, String path) throws FileNotFoundException{
        boolean ok=false;
        conexion conn = new conexion();
        if(file != null){
            
        }
        
        if(file!=null)
        {
            String sqlFoto = "UPDATE USUARIOS SET FOTO=?, PATH=? WHERE ID="+ID;
            try{
                conn.conectar();
                PreparedStatement st = (PreparedStatement) conn.getConection().prepareStatement(sqlFoto);
                FileInputStream fis = new FileInputStream ( file );
                st.setBinaryStream (1, fis, (int) file.length());
                st.setString(2, path);
                st.executeUpdate();
                ok=true;
                conn.desconectar();
            } catch (SQLException ex) {
                JOptionPane.showMessageDialog(null, ex);
            } catch (ClassNotFoundException ex) {
            }
        }
        
        String sqlDatos = "UPDATE USUARIOS SET NOMBRE='"+NOMBRE+"', APELLIDOS='"+APELLIDOS+"', USUARIO='"+USUARIO+"', PASS='"+PASS+"', TIPO='"+TIPO+"' WHERE ID="+ID;
        try{
            conn.conectar();
            PreparedStatement st = (PreparedStatement) conn.getConection().prepareStatement(sqlDatos);
            st.executeUpdate();//Insert, Update, Delete
            ok=true;
            conn.desconectar();
        } catch (SQLException ex) {
        } catch (ClassNotFoundException ex) {
        }
        
        
        return ok;
    }
    
    public ArrayList<String> ListaUsuariosId(String User){
        ArrayList<String> lista = new ArrayList<String>();
        conexion conn = new conexion();        
        String sql = "SELECT ID,NOMBRE,APELLIDOS FROM USUARIOS WHERE USUARIO='"+User+"'";
        try {
            conn.conectar();
            PreparedStatement st = (PreparedStatement) conn.getConection().prepareStatement(sql);
            ResultSet rs = st.executeQuery(sql);
            while(rs.next()){
                lista.add(String.valueOf(rs.getInt(1)));
                lista.add(rs.getString(2)+" "+rs.getString(3));
            }
            conn.desconectar();
        } catch (ClassNotFoundException ex) {            
        } catch (SQLException ex) {         
        }                       
        return lista;       
    }
}
