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
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import javax.swing.JOptionPane;

/**
 *
 */
public class Pacientes {
    private int ID;
    private String RUT;
    private String NOMBRE;
    private String TELEFONO;
    private String APELLIDOS;
    private int EDAD;
    private String DIRECCION;
    private String ENFERMEDADES;
    private String INSUMOS;
    private String DIAGNOSTICO;
    private String ALCOHOL;
    private String CIGARRO;
    private int PESO;
    private int ESTATURA;
    private String USER;
    private int ESPECIALISTA;

    public Pacientes() {
    }

    public Pacientes(int ID, String RUT, String NOMBRE, String APELLIDOS, int EDAD, String DIRECCION, String TELEFONO, String ENFERMEDADES, String INSUMOS, String DIAGNOSTICO, String ALCOHOL, String CIGARRO, int PESO, int ESTATURA, int ESPECIALISTA) {
        this.ID = ID;
        this.RUT = RUT;
        this.NOMBRE = NOMBRE;
        this.APELLIDOS = APELLIDOS;
        this.EDAD = EDAD;
        this.DIRECCION = DIRECCION;
        this.TELEFONO = TELEFONO;
        this.ENFERMEDADES = ENFERMEDADES;
        this.INSUMOS = INSUMOS;
        this.DIAGNOSTICO = DIAGNOSTICO;
        this.ALCOHOL = ALCOHOL;
        this.CIGARRO = CIGARRO;
        this.PESO = PESO;
        this.ESTATURA = ESTATURA;
        this.ESPECIALISTA = ESPECIALISTA;
    }

    public Pacientes(int ID, String RUT, String NOMBRE, String APELLIDOS, int EDAD, String DIRECCION, String TELEFONO, String ENFERMEDADES, String INSUMOS, String DIAGNOSTICO, String ALCOHOL, String CIGARRO, int PESO, int ESTATURA, String USER) {
        this.ID = ID;
        this.RUT = RUT;
        this.NOMBRE = NOMBRE;
        this.APELLIDOS = APELLIDOS;
        this.EDAD = EDAD;
        this.DIRECCION = DIRECCION;
        this.TELEFONO = TELEFONO;
        this.ENFERMEDADES = ENFERMEDADES;
        this.INSUMOS = INSUMOS;
        this.DIAGNOSTICO = DIAGNOSTICO;
        this.ALCOHOL = ALCOHOL;
        this.CIGARRO = CIGARRO;
        this.PESO = PESO;
        this.ESTATURA = ESTATURA;
        this.USER = USER;
    }

    public int getID() {
        return ID;
    }
    
    public String getRUT() {
        return RUT;
    }

    public void setRUT(String RUT) {
        this.RUT = RUT;
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

    public int getEDAD() {
        return EDAD;
    }

    public void setEDAD(int EDAD) {
        this.EDAD = EDAD;
    }

    public String getDIRECCION() {
        return DIRECCION;
    }

    public void setDIRECCION(String DIRECCION) {
        this.DIRECCION = DIRECCION;
    }

    public String getTELEFONO() {
        return TELEFONO;
    }

    public void setTELEFONO(String TELEFONO) {
        this.TELEFONO = TELEFONO;
    }

    public String getENFERMEDADES() {
        return ENFERMEDADES;
    }

    public void setENFERMEDADES(String ENFERMEDADES) {
        this.ENFERMEDADES = ENFERMEDADES;
    }

    public String getINSUMOS() {
        return INSUMOS;
    }

    public void setINSUMOS(String INSUMOS) {
        this.INSUMOS = INSUMOS;
    }

    public String getDIAGNOSTICO() {
        return DIAGNOSTICO;
    }

    public void setDIAGNOSTICO(String DIAGNOSTICO) {
        this.DIAGNOSTICO = DIAGNOSTICO;
    }

    public String getALCOHOL() {
        return ALCOHOL;
    }

    public void setALCOHOL(String ALCOHOL) {
        this.ALCOHOL = ALCOHOL;
    }

    public String getCIGARRO() {
        return CIGARRO;
    }

    public void setCIGARRO(String CIGARRO) {
        this.CIGARRO = CIGARRO;
    }

    public int getPESO() {
        return PESO;
    }

    public void setPESO(int PESO) {
        this.PESO = PESO;
    }

    public int getESTATURA() {
        return ESTATURA;
    }

    public void setESTATURA(int ESTATURA) {
        this.ESTATURA = ESTATURA;
    }

    public String getUSER() {
        return USER;
    }

    public void setUSER(String USER) {
        this.USER = USER;
    }

    public int getESPECIALISTA() {
        return ESPECIALISTA;
    }

    public void setESPECIALISTA(int ESPECIALISTA) {
        this.ESPECIALISTA = ESPECIALISTA;
    }
    
    public ArrayList filtrarPacientes(String filtro, int idUsuario){
        ArrayList<Pacientes> Lista = new ArrayList();
        conexion conn = new conexion();
        String sql ="SELECT PACIENTES.ID, PACIENTES.RUT, PACIENTES.NOMBRE, PACIENTES.APELLIDOS, PACIENTES.EDAD, PACIENTES.DIRECCION, PACIENTES.TELEFONO, PACIENTES.ENFERMEDADES, PACIENTES.INSUMOS, PACIENTES.DIAGNOSTICO, PACIENTES.ALCOHOL, PACIENTES.CIGARRO, PACIENTES.PESO, PACIENTES.ESTATURA, PACIENTES.ESPECIALISTA FROM PACIENTES WHERE PACIENTES.ESPECIALISTA = "+idUsuario+" AND (PACIENTES.NOMBRE LIKE '%"+filtro+"%' or PACIENTES.APELLIDOS LIKE '%"+filtro+"%') ORDER BY ID; ";
        try {
            conn.conectar();
            PreparedStatement st = (PreparedStatement) conn.getConection().prepareStatement(sql);
            ResultSet rs = st.executeQuery(sql);
            while(rs.next()){
               
                Lista.add(new Pacientes(rs.getInt(1), rs.getString(2), rs.getString(3), rs.getString(4), rs.getInt(5), rs.getString(6), rs.getString(7), rs.getString(8), rs.getString(9), rs.getString(10), rs.getString(11), rs.getString(12), rs.getInt(13), rs.getInt(14), rs.getInt(15)));
            }
            conn.desconectar();
        } catch (SQLException ex) {
        } catch (ClassNotFoundException ex) {
        }
        return Lista;
    }
     
    public ArrayList listaPacientes(int idUsuario){
        ArrayList<Pacientes> Lista = new ArrayList();
        conexion conn = new conexion();
        String sql ="SELECT PACIENTES.ID, PACIENTES.RUT, PACIENTES.NOMBRE, PACIENTES.APELLIDOS, PACIENTES.EDAD, PACIENTES.DIRECCION, PACIENTES.TELEFONO, PACIENTES.ENFERMEDADES, PACIENTES.INSUMOS, PACIENTES.DIAGNOSTICO, PACIENTES.ALCOHOL, PACIENTES.CIGARRO, PACIENTES.PESO, PACIENTES.ESTATURA, PACIENTES.ESPECIALISTA FROM PACIENTES WHERE PACIENTES.ESPECIALISTA = "+idUsuario+" ORDER BY ID; ";
        try {
            conn.conectar();
            PreparedStatement st = (PreparedStatement) conn.getConection().prepareStatement(sql);
            ResultSet rs = st.executeQuery(sql);
            while(rs.next()){
               
                Lista.add(new Pacientes(rs.getInt(1), rs.getString(2), rs.getString(3), rs.getString(4), rs.getInt(5), rs.getString(6), rs.getString(7), rs.getString(8), rs.getString(9), rs.getString(10), rs.getString(11), rs.getString(12), rs.getInt(13), rs.getInt(14), rs.getInt(15)));
            }
            conn.desconectar();
        } catch (SQLException ex) {
        } catch (ClassNotFoundException ex) {
        }
        return Lista;
    }
    
    public ArrayList listaPacientes(){
        ArrayList<Pacientes> Lista = new ArrayList();
        conexion conn = new conexion();
        String sql ="SELECT PACIENTES.ID, PACIENTES.RUT, PACIENTES.NOMBRE, PACIENTES.APELLIDOS, PACIENTES.EDAD, PACIENTES.DIRECCION, PACIENTES.TELEFONO, PACIENTES.ENFERMEDADES, PACIENTES.INSUMOS, PACIENTES.DIAGNOSTICO, PACIENTES.ALCOHOL, PACIENTES.CIGARRO, PACIENTES.PESO, PACIENTES.ESTATURA, USUARIOS.NOMBRE FROM PACIENTES, USUARIOS WHERE PACIENTES.ID = USUARIOS.ID ORDER BY ID; ";
       
        try {
            conn.conectar();
            PreparedStatement st = (PreparedStatement) conn.getConection().prepareStatement(sql);
            ResultSet rs = st.executeQuery(sql);
            while(rs.next()){
               
                Lista.add(new Pacientes(rs.getInt(1), rs.getString(2), rs.getString(3), rs.getString(4), rs.getInt(5), rs.getString(6), rs.getString(7), rs.getString(8), rs.getString(9), rs.getString(10), rs.getString(11), rs.getString(12), rs.getInt(13), rs.getInt(14), rs.getString(15)));
            }
            conn.desconectar();
        } catch (SQLException ex) {
        } catch (ClassNotFoundException ex) {
        }
        return Lista;
    }
    
    public boolean ExistePacientes(){
        boolean existen = false;
        conexion conn = new conexion();
        String sql ="SELECT COUNT(*) FROM PACIENTES ";
       
        try {
            conn.conectar();
            PreparedStatement st = (PreparedStatement) conn.getConection().prepareStatement(sql);
            ResultSet rs = st.executeQuery(sql);
            while(rs.next()){
               if(rs.getInt(1)>0){
               existen= true;
               }
                
            }
            conn.desconectar();
        } catch (SQLException ex) {
        } catch (ClassNotFoundException ex) {
        }
        return existen;
    }
    
    public boolean crearpdf(String RUT, String path) throws FileNotFoundException, DocumentException{
        conexion conn = new conexion();
        boolean ok=false;
        String sql ="SELECT * FROM PACIENTES WHERE RUT='"+RUT+"'";
        try {
            conn.conectar();
            PreparedStatement st = (PreparedStatement) conn.getConection().prepareStatement(sql);
            ResultSet rs = st.executeQuery(sql);
            while(rs.next()){
                FileOutputStream archivo = new FileOutputStream(path+".pdf");
                Document documento = new Document();
                PdfWriter.getInstance(documento, archivo);
                documento.open();
                Font fuente = new Font(Font.FontFamily.COURIER);
                Font base = new Font();
                base.setStyle(Font.BOLD);
                fuente.setStyle(Font.BOLD | Font.UNDERLINE);
                
                String predeterminado = "<nada>";
                String enfermedades = predeterminado;
                if(rs.getString(8)!=null && !"".equals(rs.getString(8)))
                {
                    enfermedades = rs.getString(8);
                }
                String alcohol = predeterminado;
                if(rs.getString(11)!=null && !"".equals(rs.getString(11)))
                {
                    alcohol = rs.getString(11);
                }
                String cigarro = predeterminado;
                if(rs.getString(12)!=null && !"".equals(rs.getString(12)))
                {
                    cigarro = rs.getString(12);
                }
                String peso = predeterminado;
                if(rs.getString(13)!=null && !"".equals(rs.getString(13)) && !"0".equals(rs.getString(13)))
                {
                    peso = rs.getString(13) + " KG.";
                }
                String estatura = predeterminado;
                if(rs.getString(14)!=null && !"".equals(rs.getString(14)) && !"0".equals(rs.getString(14)))
                {
                    estatura = rs.getString(14) + " CM.";
                }
                String diagnostico = predeterminado;
                if(rs.getString(10)!=null && !"".equals(rs.getString(10)))
                {
                    diagnostico = rs.getString(10);
                }


                fuente.setSize(20);
                documento.add(new Paragraph("FICHA PACIENTE",fuente));
                documento.add(new Paragraph("NOMBRE PACIENTE:",base));
                documento.add(new Paragraph(rs.getString(3)+" "+rs.getString(4)));
                documento.add(new Paragraph("RUT: ",base));
                documento.add(new Paragraph(rs.getString(2)));
                documento.add(new Paragraph("EDAD: "+rs.getString(5)+"      DIRECCIÓN: "+rs.getString(6)+"      TELEFONO "+rs.getString(7)));
                documento.add(new Paragraph("ENFERMEDADES PREVIAS: ",base));
                documento.add(new Paragraph(enfermedades));
                documento.add(new Paragraph("ALCOHOL: "+alcohol+"      CIGARRO: "+cigarro+"        PESO: "+peso+"     ESTATURA: "+estatura));
                fuente.setSize(15);
                documento.add(new Paragraph("DIAGNOSTICO",base));
                documento.add(new Paragraph(diagnostico));
                documento.close();
                
            }
            ok=true;
            conn.desconectar();
        } catch (SQLException ex) {
            
        } catch (ClassNotFoundException ex) {
        }
         return ok; 
   }
    
    
    public boolean crearpdf2(String path) throws FileNotFoundException, DocumentException{
        conexion conn = new conexion();
        boolean ok=false;
        String sql ="SELECT * FROM PACIENTES";
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
            documento.add(new Paragraph("LISTA PACIENTES",fuente));
            documento.add(new Paragraph("          "));
            
            PdfPTable table = new PdfPTable(5); 
            table.setWidthPercentage(100f);
            table.addCell(new Phrase("Rut",base));
            table.addCell(new Phrase("Nombre",base));
            table.addCell(new Phrase("Apellido",base));
            table.addCell(new Phrase("Edad",base));
            table.addCell(new Phrase("Direccion",base));
           
            while(rs.next()){
  
            table.addCell(rs.getString(2));
            table.addCell(rs.getString(3));
            table.addCell(rs.getString(4));
            table.addCell(rs.getString(5));
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
    
    
    
    
    
    
    
    
    
    
    public boolean validaRut(String rutSinDigito, String digito)
    {
        double suma = 0;
        double multi = 2;
        for (int i = rutSinDigito.length() - 1; i >= 0; i--)
        {
            suma = suma + Integer.parseInt(String.valueOf(rutSinDigito.charAt(i))) * multi;

            if (multi == 7)
            {
                multi = 1;
            }
            multi++;
        }
        String numero = Double.toString(11 - (suma % 11));
        String number = numero.substring(0, numero.length()-2);
        numero = number.toUpperCase();
        if (numero.equals(digito.toUpperCase()))
        {
            return true;
        }
        else
        {
            if ("10".equals(numero) && "K".equals(digito.toUpperCase()))
            {
                return true;
            }
            else if ("11".equals(numero) && "0".equals(digito.toUpperCase()))
            {
                return true;
            }
            else
            {
                return false;
            }
        }
    }
   
    public boolean registrarPaciente(){
        boolean ok=false;
        conexion conn = new conexion();
        String sql = "insert into PACIENTES (RUT, NOMBRE, APELLIDOS, EDAD, DIRECCION, TELEFONO, ESPECIALISTA) values(?,?,?,?,?,?,?);";
        
        try{
            conn.conectar();
            PreparedStatement st = (PreparedStatement) conn.getConection().prepareStatement(sql);
            st.setString(1, RUT);
            st.setString(2, NOMBRE);
            st.setString(3, APELLIDOS);
            st.setInt(4, EDAD);
            st.setString(5, DIRECCION);
            st.setString(6, TELEFONO);
            st.setInt(7, ESPECIALISTA);
            st.executeUpdate();//Insert, Update, Delete
            ok=true;
            conn.desconectar();
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, ex);
        } catch (ClassNotFoundException ex) {
        }
        
        return ok;
    } 
     
    public boolean actualizarPaciente(int ID, String RUT, String NOMBRE, String APELLIDOS, int EDAD, String DIRECCION, String TELEFONO, int ESPECIALISTA){
        boolean ok=false;
        conexion conn = new conexion();
        String sql = "update PACIENTES set NOMBRE='"+NOMBRE+"', APELLIDOS='"+APELLIDOS+"', EDAD='"+EDAD+"', DIRECCION='"+DIRECCION+"', TELEFONO='"+TELEFONO+"', ESPECIALISTA='"+ESPECIALISTA+"' where ID="+ID;
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
    
    public boolean actualizarFicha(int ID, String ENFERMEDADES, String INSUMOS, String DIAGNOSTICO, String ALCOHOL, String CIGARRO, int PESO, int ESTATURA){
        boolean ok=false;
        conexion conn = new conexion();
        String sql = "update PACIENTES set ENFERMEDADES='"+ENFERMEDADES+"', INSUMOS='"+INSUMOS+"', DIAGNOSTICO='"+DIAGNOSTICO+"', ALCOHOL='"+ALCOHOL+"', CIGARRO='"+CIGARRO+"', PESO='"+PESO+"', ESTATURA='"+ESTATURA+"' where ID="+ID;
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
     
    public boolean BorrarPaciente(int ID){
        boolean ok=false;
        conexion conn = new conexion();
        String sql = "DELETE FROM PACIENTES WHERE ID="+ID;
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
    
    public int ObtenerEspecialista(int ID){
        int indice = -1;
        conexion conn = new conexion();
        String sql ="SELECT ESPECIALISTA FROM PACIENTES WHERE ID = "+ID+";";
        try {
            conn.conectar();
            PreparedStatement st = (PreparedStatement) conn.getConection().prepareStatement(sql);
            ResultSet rs = st.executeQuery(sql);
            if(rs.next()){
                indice = rs.getInt(1);
            }
            conn.desconectar();
        } catch (SQLException ex) {
        } catch (ClassNotFoundException ex) {
        }
        return indice;
    }
}
