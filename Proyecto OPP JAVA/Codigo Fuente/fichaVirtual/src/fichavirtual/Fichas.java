/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package fichavirtual;

import java.awt.Component;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.table.TableCellRenderer;

/**
 *
 * @author Enterprise
 */
public class Fichas extends javax.swing.JFrame {

    /**
     * Creates new form Fichas
     */
    public static int idUsuario;
    public String nombreUsuario;
    
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
    
    ArrayList<Pacientes> listarPacientes = new ArrayList();
    private void editarPaciente(String id)
    {
        Pacientes paciente = new Pacientes();
        int indice = -1;
        for (int i = 0; i < listarPacientes.size(); i++) {
            if(listarPacientes.get(i).getID() == Integer.parseInt(id))
            {
                indice = i;
            }
        }
        if(indice == -1) return;
        paciente = listarPacientes.get(indice);
        editarPacientes panel = new editarPacientes();
        panel.txtId.setText(Integer.toString(paciente.getID()));
        panel.txtRut.setText(paciente.getNOMBRE());
        String [] rut = paciente.getRUT().split("-");
        panel.txtRut.setText(rut[0]);
        panel.txtDigito.setText(rut[1]);;
        panel.txtNombre.setText(paciente.getNOMBRE());
        panel.txtApellidos.setText(paciente.getAPELLIDOS());
        panel.txtEdad.setText(String.valueOf(paciente.getEDAD()));
        panel.txtDireccion.setText(paciente.getDIRECCION());
        panel.txtTelefono.setText(paciente.getTELEFONO());
        panel.txtEspecialista.setText(nombreUsuario);        
        panel.idEspecialista = idUsuario;
        
        JDialog modalEditar = new JDialog(this, "Editar Pacientes");
        modalEditar.add(panel);
        modalEditar.setModal(true);
        modalEditar.pack();
        modalEditar.setVisible(true);
        txtFiltrar2.setText("");
        actualizarTabla(paciente.listaPacientes(idUsuario));
    }
    
    public void fichaUsuario(String id){
        DatosFicha panel = new DatosFicha();
        
        Pacientes paciente = new Pacientes();
        int indice = -1;
        for (int i = 0; i < listarPacientes.size(); i++) {
            if(listarPacientes.get(i).getID() == Integer.parseInt(id))
            {
                indice = i;
            }
        }
        if(indice == -1) return;
        paciente = listarPacientes.get(indice);
        panel.lblRut.setText(paciente.getRUT());
        panel.lblNombre.setText(paciente.getNOMBRE());
        panel.lblApellidos.setText(paciente.getAPELLIDOS());
        panel.lblEdad.setText(String.valueOf(paciente.getEDAD()));
        panel.lblDireccion.setText(paciente.getDIRECCION());
        panel.lblTelefono.setText(paciente.getTELEFONO());
        
        panel.txtEnfermedades.setText(paciente.getENFERMEDADES());
        panel.txtInsumos.setText(paciente.getINSUMOS());
        panel.txtDiagnostico.setText(paciente.getDIAGNOSTICO());
        panel.txtAlcohol.setText(paciente.getALCOHOL());
        panel.txtCigarro.setText(paciente.getCIGARRO());
        String peso = String.valueOf(paciente.getPESO());
        if("0".equals(peso)){
            peso = "";
        }
        panel.txtPeso.setText(peso);
        String estatura = String.valueOf(paciente.getESTATURA());
        if("0".equals(estatura)){
            estatura = "";
        }
        panel.txtEstatura.setText(estatura);
        panel.idPaciente = paciente.getID();
        

        JDialog dialogo = new JDialog(this, "Ficha de Paciente", true);
        dialogo.add(panel);
        dialogo.setModal(true);
        dialogo.pack();
        dialogo.setVisible(true);
        
        txtFiltrar2.setText("");
        actualizarTabla(paciente.listaPacientes(idUsuario));
    }
    
    private void inicializarTabla() {
        Pacientes paciente = new Pacientes();
        listarPacientes = paciente.listaPacientes(idUsuario);
        actualizarTabla(listarPacientes);

        jTableEjemplo2.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                int fila = jTableEjemplo2.rowAtPoint(e.getPoint());
                int columna = jTableEjemplo2.columnAtPoint(e.getPoint());
                String idPaciente = jTableEjemplo2.getModel().getValueAt(fila, 0).toString();

                if (jTableEjemplo2.getModel().getColumnClass(columna).equals(JButton.class) && columna == 7) 
                {
                    editarPaciente(jTableEjemplo2.getModel().getValueAt(fila, 0).toString());
                }
                else if (jTableEjemplo2.getModel().getColumnClass(columna).equals(JButton.class) && columna == 8) 
                {  
                    int reply = JOptionPane.showConfirmDialog(
                            null, "Desea borrar el usuario que posee id: "+
                                   jTableEjemplo2.getModel().getValueAt(fila, 0).toString(),
                            "Atención!", 
                            JOptionPane.YES_NO_OPTION);
                    if (reply == JOptionPane.YES_OPTION) {
                        Pacientes paciente = new Pacientes();
                        if(paciente.BorrarPaciente(Integer.parseInt(jTableEjemplo2.getModel().getValueAt(fila, 0).toString())))
                        {
                            txtFiltrar2.setText("");
                            actualizarTabla(paciente.listaPacientes(idUsuario));
                            JOptionPane.showMessageDialog(null, "Paciente id = " + idPaciente+ " borrado exitosamente");
                        }
                    }   
                }
                else if (jTableEjemplo2.getModel().getColumnClass(columna).equals(JButton.class) && columna == 9) 
                {  
                    fichaUsuario(jTableEjemplo2.getModel().getValueAt(fila, 0).toString());
                }
            }
        });
    }
    
    private void actualizarTabla(ArrayList<Pacientes> lista) {
        String[] columnas = new String[]{
            "Id",
            "Rut",
            "Nombre",
            "Apellidos",
            "Edad",
            "Direccion",
            "Telefono",
            "Editar",
            "Borrar",
            "Gestionar Fichas"};

        final Class[] tiposColumnas = new Class[]{
            int.class,
            java.lang.String.class,
            java.lang.String.class,
            int.class,
            java.lang.String.class,
            java.lang.String.class,
            java.lang.String.class,
            JButton.class,
            JButton.class,
            JButton.class
        };
        
        Object[][] datos = new Object[lista.size()][10];
        listarPacientes = lista;
        for (int i = 0; i < listarPacientes.size(); i++) {
              datos[i][0] = listarPacientes.get(i).getID();     
              datos[i][1] = listarPacientes.get(i).getRUT();
              datos[i][2] = listarPacientes.get(i).getNOMBRE();
              datos[i][3] = listarPacientes.get(i).getAPELLIDOS();
              datos[i][4] = listarPacientes.get(i).getEDAD();
              datos[i][5] = listarPacientes.get(i).getDIRECCION();
              datos[i][6] = listarPacientes.get(i).getTELEFONO();
              datos[i][7] = new JButton("Editar");
              datos[i][8] = new JButton("Borrar");
              datos[i][9] = new JButton("Gestionar Ficha");
        }
        

        jTableEjemplo2.setModel(new javax.swing.table.DefaultTableModel(
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

        jTableEjemplo2.setDefaultRenderer(JButton.class, new TableCellRenderer() {
            @Override
            public Component getTableCellRendererComponent(JTable jtable, Object objeto, boolean estaSeleccionado, boolean tieneElFoco, int fila, int columna) {
                return (Component) objeto;
            }
        });

    }
    
    public Fichas() {
        initComponents();
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        pestaniasAdmin2 = new javax.swing.JTabbedPane();
        jPanel4 = new javax.swing.JPanel();
        jScrollPane3 = new javax.swing.JScrollPane();
        jTableEjemplo2 = new javax.swing.JTable();
        Ingresar2 = new javax.swing.JButton();
        btnFiltrar2 = new javax.swing.JButton();
        txtFiltrar2 = new javax.swing.JTextField();
        jLabel3 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowOpened(java.awt.event.WindowEvent evt) {
                formWindowOpened(evt);
            }
        });

        jTableEjemplo2.setModel(new javax.swing.table.DefaultTableModel(

        ));
        jScrollPane3.setViewportView(jTableEjemplo2);

        Ingresar2.setText("Crear nuevo paciente");
        Ingresar2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                Ingresar2ActionPerformed(evt);
            }
        });

        btnFiltrar2.setText("Filtrar");
        btnFiltrar2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnFiltrar2ActionPerformed(evt);
            }
        });

        txtFiltrar2.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtFiltrar2KeyTyped(evt);
            }
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtFiltrar2KeyReleased(evt);
            }
        });

        jLabel3.setText("Filtrar por nombre o por apellidos");

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane3)
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addComponent(Ingresar2, javax.swing.GroupLayout.PREFERRED_SIZE, 196, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel4Layout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 368, Short.MAX_VALUE)
                        .addComponent(jLabel3)
                        .addGap(18, 18, 18)
                        .addComponent(txtFiltrar2, javax.swing.GroupLayout.PREFERRED_SIZE, 437, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(btnFiltrar2, javax.swing.GroupLayout.PREFERRED_SIZE, 217, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap())
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel4Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtFiltrar2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnFiltrar2)
                    .addComponent(jLabel3))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane3, javax.swing.GroupLayout.DEFAULT_SIZE, 453, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(Ingresar2, javax.swing.GroupLayout.PREFERRED_SIZE, 51, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        pestaniasAdmin2.addTab("Gestionar Pacientes", jPanel4);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(pestaniasAdmin2)
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(pestaniasAdmin2)
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void Ingresar2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_Ingresar2ActionPerformed
        // TODO add your handling code here:
        CrearPacientes panel = new CrearPacientes();
        panel.idUsuario = idUsuario;
        JDialog dialogo = new JDialog(this, "Crear Pacientes", true);
        dialogo.add(panel);
        dialogo.setModal(true);
        dialogo.pack();
        dialogo.setVisible(true);

        Pacientes paciente = new Pacientes();
        txtFiltrar2.setText("");
        actualizarTabla(paciente.listaPacientes(idUsuario));
    }//GEN-LAST:event_Ingresar2ActionPerformed

    private void btnFiltrar2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnFiltrar2ActionPerformed
        // TODO add your handling code here:
        
    }//GEN-LAST:event_btnFiltrar2ActionPerformed

    private void txtFiltrar2KeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtFiltrar2KeyTyped
        validaTexto(evt);
    }//GEN-LAST:event_txtFiltrar2KeyTyped

    private void txtFiltrar2KeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtFiltrar2KeyReleased
        //filtro de pacientes
        Pacientes paciente = new Pacientes();
        actualizarTabla(paciente.filtrarPacientes(txtFiltrar2.getText(),idUsuario));
    }//GEN-LAST:event_txtFiltrar2KeyReleased

    private void formWindowOpened(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowOpened
        // TODO add your handling code here:
        inicializarTabla();
        super.setTitle("Bienvenido(a) "+nombreUsuario);
    }//GEN-LAST:event_formWindowOpened

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
            java.util.logging.Logger.getLogger(Fichas.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(Fichas.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(Fichas.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(Fichas.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new Fichas().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton Ingresar2;
    private javax.swing.JButton btnFiltrar2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JTable jTableEjemplo2;
    private javax.swing.JTabbedPane pestaniasAdmin2;
    private javax.swing.JTextField txtFiltrar2;
    // End of variables declaration//GEN-END:variables
}
