/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package fichavirtual;

import com.itextpdf.text.DocumentException;
import java.awt.Component;
import java.awt.Desktop;
import java.awt.Image;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.table.TableCellRenderer;

/**
 *
 * @author Enterprise
 */
public class Administracion extends javax.swing.JFrame {

    public static int idUsuario;
    
    public void validaTexto(java.awt.event.KeyEvent evt)
    {
        char c = evt.getKeyChar();
     
        if((c<'a' || c>'z') && (c<'A' || c>'Z')/* && c=='ñ' && (c<'!' || c>'.')*/
            && c !='á' //Minúsculas             
            && c !='é'            
            && c !='í'            
            && c !='ó'           
            && c !='ú'   
            && c !='Á' //Mayúsculas             
            && c !='É'            
            && c !='Í'            
            && c !='Ó'           
            && c !='Ú' 
            && (c!=(char)evt.VK_SPACE))
        {
            evt.consume();
        }
    }
    
    ArrayList<Usuarios> listaUsuarios = new ArrayList();
        
    private void editarUsuario(String id) throws IOException
    {
        Usuarios user = new Usuarios();
        int indice = -1;
        for (int i = 0; i < listaUsuarios.size(); i++) {
            if(listaUsuarios.get(i).getID() == Integer.parseInt(id))
            {
                indice = i;
            }
        }
        if(indice == -1) return;
        user = listaUsuarios.get(indice);
        editarUsuarios panel = new editarUsuarios();
        panel.txtId.setText(Integer.toString(user.getID()));
        panel.txtNombres.setText(user.getNOMBRE());
        panel.txtApellidos.setText(user.getAPELLIDOS());
        panel.txtUsuario.setText(user.getUSUARIO());
        panel.comboTipo.setSelectedItem(user.getTIPO());
        
        InputStream foto = user.ObtenerFoto(Integer.parseInt(id));
        if(foto != null)
        {
            Image image = ImageIO.read(foto);
            if(image!=null)
            {
                Image scaledImage = image.getScaledInstance(154, 154, Image.SCALE_DEFAULT);
                ImageIcon icon=new ImageIcon(scaledImage);
                panel.btnFoto.setIcon(icon);
                panel.txtFoto.setText(user.ObtenerPath(Integer.parseInt(id)));
            }
        }
        
        JDialog modalEditar = new JDialog(this, "Editar Usuario");
        modalEditar.add(panel);
        modalEditar.setModal(true);
        modalEditar.pack();
        modalEditar.setVisible(true);
        txtFiltrar.setText("");
        actualizarTabla(user.listaUsuarios(idUsuario));
    }
    
    private void inicializarTabla() {
        String[] columnas = new String[]{
            "Id",
            "Nombre",
            "Apellidos",
            "Usuario",
            "Tipo",
            "Password",
            "Editar",
            "Borrar"};

        final Class[] tiposColumnas = new Class[]{
            int.class,
            java.lang.String.class,
            java.lang.String.class,
            java.lang.String.class,
            java.lang.String.class,
            java.lang.String.class,
            JButton.class,
            JButton.class
        };
        
        Usuarios user = new Usuarios();
        listaUsuarios = user.listaUsuarios(idUsuario);
        Object[][] datos = new Object[listaUsuarios.size()][8];
        for (int i = 0; i < listaUsuarios.size(); i++) {
              datos[i][0] = listaUsuarios.get(i).getID();     
              datos[i][1] = listaUsuarios.get(i).getNOMBRE();
              datos[i][2] = listaUsuarios.get(i).getAPELLIDOS();
              datos[i][3] = listaUsuarios.get(i).getUSUARIO();
              datos[i][4] = listaUsuarios.get(i).getTIPO();
              datos[i][5] = listaUsuarios.get(i).getPASS();
              datos[i][6] = new JButton("Editar");
              datos[i][7] = new JButton("Borrar");
        }
        
        jTableEjemplo.setModel(new javax.swing.table.DefaultTableModel(
                datos,
                columnas) {
            Class[] tipos = tiposColumnas;

            @Override
            public Class getColumnClass(int columnIndex) {
                return tipos[columnIndex];
            }

            @Override
            public boolean isCellEditable(int row, int column) {
               return !(this.getColumnClass(column).equals(JButton.class));
            }
        });

        jTableEjemplo.setDefaultRenderer(JButton.class, new TableCellRenderer() {
            @Override
            public Component getTableCellRendererComponent(JTable jtable, Object objeto, boolean estaSeleccionado, boolean tieneElFoco, int fila, int columna) {
                return (Component) objeto;
            }
        });

        jTableEjemplo.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                int fila = jTableEjemplo.rowAtPoint(e.getPoint());
                int columna = jTableEjemplo.columnAtPoint(e.getPoint());
                String idUser = jTableEjemplo.getModel().getValueAt(fila, 0).toString();

                if (jTableEjemplo.getModel().getColumnClass(columna).equals(JButton.class) && columna == 6) 
                {
                    try {
                        editarUsuario(jTableEjemplo.getModel().getValueAt(fila, 0).toString());
                    } catch (IOException ex) {
                        Logger.getLogger(Administracion.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
                else
                {
                    if (jTableEjemplo.getModel().getColumnClass(columna).equals(JButton.class) && columna == 7) {
                        
                        int reply = JOptionPane.showConfirmDialog(
                                null, "Desea borrar el usuario que posee id: "+
                                       jTableEjemplo.getModel().getValueAt(fila, 0).toString(),
                                "Atención!", 
                                JOptionPane.YES_NO_OPTION);
                        if (reply == JOptionPane.YES_OPTION) {
                            Usuarios us = new Usuarios();
                            if(us.borrarUsuario(Integer.parseInt(jTableEjemplo.getModel().getValueAt(fila, 0).toString())))
                            {
                                txtFiltrar.setText("");
                                actualizarTabla(us.listaUsuarios(idUsuario));
                                JOptionPane.showMessageDialog(null, "Usuario id=" + idUser+ " borrado exitosamente");
                            }
                        }    
                    }
                }
            }
        });
    }
    
    private void actualizarTabla(ArrayList<Usuarios> lista) {

        String[] columnas = new String[]{
            "Id",
            "Nombre",
            "Apellidos",
            "Usuario",
            "Tipo",
            "Password",
            "Editar",
            "Borrar"};

        final Class[] tiposColumnas = new Class[]{
            int.class,
            java.lang.String.class,
            java.lang.String.class,
            java.lang.String.class,
            java.lang.String.class,
            java.lang.String.class,
            JButton.class,
            JButton.class
        };
        
        Usuarios user = new Usuarios();
        listaUsuarios = lista;
        Object[][] datos = new Object[listaUsuarios.size()][8];
        for (int i = 0; i < listaUsuarios.size(); i++) {
              datos[i][0] = listaUsuarios.get(i).getID();     
              datos[i][1] = listaUsuarios.get(i).getNOMBRE();
              datos[i][2] = listaUsuarios.get(i).getAPELLIDOS();
              datos[i][3] = listaUsuarios.get(i).getUSUARIO();
              datos[i][4] = listaUsuarios.get(i).getTIPO();
              datos[i][5] = listaUsuarios.get(i).getPASS();
              datos[i][6] = new JButton("Editar");
              datos[i][7] = new JButton("Borrar");
        }
        
        jTableEjemplo.setModel(new javax.swing.table.DefaultTableModel(
                datos,
                columnas) {
            
            Class[] tipos = tiposColumnas;

            @Override
            public Class getColumnClass(int columnIndex) {
                return tipos[columnIndex];
            }

            @Override
            public boolean isCellEditable(int row, int column) {
                return !(this.getColumnClass(column).equals(JButton.class));
            }
        });

        jTableEjemplo.setDefaultRenderer(JButton.class, new TableCellRenderer() {
            @Override
            public Component getTableCellRendererComponent(JTable jtable, Object objeto, boolean estaSeleccionado, boolean tieneElFoco, int fila, int columna) {
                return (Component) objeto;
            }
        });
    }
   
    public Administracion() {
        initComponents();
    }
    
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        pestaniasAdmin = new javax.swing.JTabbedPane();
        jPanel2 = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTableEjemplo = new javax.swing.JTable();
        Ingresar = new javax.swing.JButton();
        btnFiltrar = new javax.swing.JButton();
        txtFiltrar = new javax.swing.JTextField();
        jLabel1 = new javax.swing.JLabel();
        jPanel1 = new javax.swing.JPanel();
        jButton2 = new javax.swing.JButton();
        jButton3 = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowOpened(java.awt.event.WindowEvent evt) {
                formWindowOpened(evt);
            }
        });

        jTableEjemplo.setModel(new javax.swing.table.DefaultTableModel(

        ));
        jScrollPane1.setViewportView(jTableEjemplo);

        Ingresar.setText("Crear nuevo usuario");
        Ingresar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                IngresarActionPerformed(evt);
            }
        });

        btnFiltrar.setText("Filtrar");
        btnFiltrar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnFiltrarActionPerformed(evt);
            }
        });

        txtFiltrar.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtFiltrarKeyTyped(evt);
            }
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtFiltrarKeyReleased(evt);
            }
        });

        jLabel1.setText("Filtrar por nombre o por apellidos");

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane1)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addComponent(Ingresar, javax.swing.GroupLayout.PREFERRED_SIZE, 196, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 284, Short.MAX_VALUE)
                        .addComponent(jLabel1)
                        .addGap(18, 18, 18)
                        .addComponent(txtFiltrar, javax.swing.GroupLayout.PREFERRED_SIZE, 437, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(btnFiltrar, javax.swing.GroupLayout.PREFERRED_SIZE, 217, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap())
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtFiltrar, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnFiltrar)
                    .addComponent(jLabel1))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 424, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(Ingresar, javax.swing.GroupLayout.PREFERRED_SIZE, 51, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        pestaniasAdmin.addTab("Gestionar Usuarios", jPanel2);

        jButton2.setText("Lista de Usuarios Registrados");
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });

        jButton3.setText("Lista de Pacientes Registrados");
        jButton3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton3ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addContainerGap(355, Short.MAX_VALUE)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jButton3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jButton2, javax.swing.GroupLayout.PREFERRED_SIZE, 452, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(345, 345, 345))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(155, 155, 155)
                .addComponent(jButton2, javax.swing.GroupLayout.PREFERRED_SIZE, 77, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(71, 71, 71)
                .addComponent(jButton3, javax.swing.GroupLayout.PREFERRED_SIZE, 77, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(157, Short.MAX_VALUE))
        );

        pestaniasAdmin.addTab("Generar Informes", jPanel1);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(pestaniasAdmin)
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(pestaniasAdmin)
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void IngresarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_IngresarActionPerformed
        // TODO add your handling code here:
        CrearUsuarios panel = new CrearUsuarios();

        JDialog dialogo = new JDialog(this, "Crear Usuarios", true);
        dialogo.add(panel);
        dialogo.setModal(true);
        dialogo.pack();
        dialogo.setVisible(true);

        Usuarios user = new Usuarios();
        txtFiltrar.setText("");
        actualizarTabla(user.listaUsuarios(idUsuario));

    }//GEN-LAST:event_IngresarActionPerformed

    private void btnFiltrarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnFiltrarActionPerformed
        // TODO add your handling code here:
        Usuarios user = new Usuarios();
        actualizarTabla(user.filtrarUsuarios(txtFiltrar.getText(),idUsuario));
    }//GEN-LAST:event_btnFiltrarActionPerformed

    private void txtFiltrarKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtFiltrarKeyReleased
        Usuarios user = new Usuarios();
        actualizarTabla(user.filtrarUsuarios(txtFiltrar.getText(),idUsuario));
    }//GEN-LAST:event_txtFiltrarKeyReleased

    private void txtFiltrarKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtFiltrarKeyTyped
        validaTexto(evt);
    }//GEN-LAST:event_txtFiltrarKeyTyped

    private void formWindowOpened(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowOpened
        // TODO add your handling code here:
        super.setTitle("Bienvenido Administrador");
        inicializarTabla();
    }//GEN-LAST:event_formWindowOpened

    
     private String guardarArchivo() {
        String guardaTxt = null;
        JFileChooser file=new JFileChooser();
        file.showSaveDialog(this);
        File guarda =file.getSelectedFile();
        if(guarda != null)
        {
            guardaTxt = guarda.getPath();
        }
        return guardaTxt;
    }
    
    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
        // TODO add your handling code here:
        
        try {
            Usuarios us = new Usuarios();
            String pathArchivo = guardarArchivo();
            
         
            if(pathArchivo!=null)
            {
                boolean pdf = us.crearpdf(pathArchivo);
                if(pdf==true){
                JOptionPane.showMessageDialog(null,"PDF Creado Exitosamente");
                File archivo = new File(pathArchivo+".pdf");
                Desktop.getDesktop().open(archivo);
                }
                else{
                    JOptionPane.showMessageDialog(null,"ERROR");
                }
            }
            
        } catch (FileNotFoundException ex) {
            Logger.getLogger(Administracion.class.getName()).log(Level.SEVERE, null, ex);
        } catch (DocumentException ex) {
            Logger.getLogger(Administracion.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(Administracion.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        
        
        
        
        
        
        
        
        
    }//GEN-LAST:event_jButton2ActionPerformed

    private void jButton3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton3ActionPerformed
        // TODO add your handling code here:
        try {
            Pacientes pac = new Pacientes();
            String pathArchivo = guardarArchivo();
            
            if(pathArchivo!=null)
            {
                if (pac.ExistePacientes())
                {
                    boolean pdf = pac.crearpdf2(pathArchivo);

                    if(pdf==true){
                    JOptionPane.showMessageDialog(null,"PDF Creado Exitosamente");
                    File archivo = new File(pathArchivo+".pdf");
                    Desktop.getDesktop().open(archivo);
                    }
                    else{
                        JOptionPane.showMessageDialog(null,"ERROR");
                    }
                }else{
                    JOptionPane.showMessageDialog(null, "Error, no existen pacientes registrados");
                }
            }
            
        } catch (FileNotFoundException ex) {
            Logger.getLogger(DatosFicha.class.getName()).log(Level.SEVERE, null, ex);
        } catch (DocumentException ex) {
            Logger.getLogger(DatosFicha.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(DatosFicha.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        
    }//GEN-LAST:event_jButton3ActionPerformed

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(Administracion.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(Administracion.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(Administracion.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(Administracion.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new Administracion().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton Ingresar;
    private javax.swing.JButton btnFiltrar;
    private javax.swing.JButton jButton2;
    private javax.swing.JButton jButton3;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTable jTableEjemplo;
    private javax.swing.JTabbedPane pestaniasAdmin;
    private javax.swing.JTextField txtFiltrar;
    // End of variables declaration//GEN-END:variables
}
