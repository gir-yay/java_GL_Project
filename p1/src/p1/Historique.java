/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package p1;

import java.awt.Desktop;
import java.io.File;
import java.io.FileNotFoundException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.swing.JOptionPane;
import javax.swing.RowFilter;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;
import javax.swing.table.TableRowSorter;

/**
 *
 * @author pc
 */
public class Historique extends javax.swing.JFrame {

    /**
     * Creates new form Historique
     */

    public Historique() {
        initComponents();
        setTitle("Historique");
        this.setLocationRelativeTo(null);
    }

    private Dashbrd DashboardInstance;

    public void setDashboardInstance(Dashbrd DashboardInstance) {
        this.DashboardInstance = DashboardInstance;
    }

    public void actualiser() {
        // clear it first then fill()
        DefaultTableModel model = (DefaultTableModel) jTable2.getModel();
        model.setRowCount(0); // Clear the table before adding rows
        fill();
    }
    public void open(String path){
        try {
            File file = new File(path);
            Desktop.getDesktop().open(file);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Erreur");
        }
    }

    // used to fill the table
    public void fill() {
        try {
            Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/gl", "root", "");
            java.sql.Statement stmt = con.createStatement();
            ResultSet rs = stmt.executeQuery(
                    "SELECT demande_ar.id,student.Nom_complet,student.email,student.CNE,demande_ar.statuts FROM demande_ar INNER JOIN student ON demande_ar.user_id = student.CNE where traité = '1';");
            DefaultTableModel model = (DefaultTableModel) jTable2.getModel();
            model.setRowCount(0); // Clear the table before adding rows
            while (rs.next()) {
                if(rs.getString("statuts").equals("1")){
                    //then status = Accepter 
                    model.addRow(new Object[] { rs.getString("id"), rs.getString("Nom_complet"),
                        rs.getInt("student.CNE"),
                        rs.getString("email"), "Attestation de réussite","Accepter" });
                }else{
                model.addRow(new Object[] { rs.getString("id"), rs.getString("Nom_complet"),
                        rs.getInt("student.CNE"),
                        rs.getString("email"), "Attestation de réussite","Refuser" });
            }
            jTable2.revalidate();
            jTable2.repaint();
        }
        
        } catch (SQLException e) {
            e.printStackTrace();
        }
        // now the second table demande_rn
        try {
            Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/gl", "root", "");
            java.sql.Statement stmt = con.createStatement();
            ResultSet rs = stmt.executeQuery(
                    "SELECT demande_rn.id,student.Nom_complet,student.email,student.CNE,demande_rn.statuts FROM demande_rn INNER JOIN student ON demande_rn.user_id = student.CNE where traité = '1';");
            DefaultTableModel model = (DefaultTableModel) jTable2.getModel();

            while (rs.next()) {
                if (rs.getString("statuts").equals("1")) {
                    // then status = Accepter
                    model.addRow(new Object[] { rs.getString("id"), rs.getString("Nom_complet"),
                            rs.getInt("student.CNE"),
                            rs.getString("email"), "Relevé de notes", "Accepter" });
                } else {
                    model.addRow(new Object[] { rs.getString("id"), rs.getString("Nom_complet"),
                            rs.getInt("student.CNE"),
                            rs.getString("email"), "Relevé de notes", "Refuser" });
                }
            }
            jTable2.revalidate();
            jTable2.repaint();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        // Attestation de stage table
        try {
            Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/gl", "root", "");
            java.sql.Statement stmt = con.createStatement();
            ResultSet rs = stmt.executeQuery(
                    "SELECT stage.id,student.Nom_complet,student.email,student.CNE,stage.statuts FROM stage INNER JOIN student ON stage.user_id = student.CNE where traité = '1';");
            DefaultTableModel model = (DefaultTableModel) jTable2.getModel();
            String status="";

            while (rs.next()) {
                if (rs.getString("statuts").equals("1")) {
                        //then status = Accepter 
                        status="Accepter";
                    } else {
                        status="Refuser";
                }
                model.addRow(new Object[] { rs.getString("id"), rs.getString("Nom_complet"),
                        rs.getInt("student.CNE"),
                        rs.getString("email"), "Attestation de stage",status });
            }
            jTable2.revalidate();
            jTable2.repaint();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        // demande_as table
        try {
            Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/gl", "root", "");
            java.sql.Statement stmt = con.createStatement();
            ResultSet rs = stmt.executeQuery(
                    "SELECT demande_as.id,student.Nom_complet,student.email,student.CNE,demande_as.statuts FROM demande_as INNER JOIN student ON demande_as.user_id = student.CNE where traite = '1';");
            DefaultTableModel model = (DefaultTableModel) jTable2.getModel();

            while (rs.next()) {
                if(rs.getString("statuts").equals("1")){
                    //then status = Accepter 
                    model.addRow(new Object[] { rs.getString("id"), rs.getString("Nom_complet"),
                        rs.getInt("student.CNE"),
                        rs.getString("email"), "Attestation de scolarité","Accepter" });
                }else{
                model.addRow(new Object[] { rs.getString("id"), rs.getString("Nom_complet"),
                        rs.getInt("student.CNE"),
                        rs.getString("email"), "Attestation de scolarité" ,"Refuser"});
            }
            jTable2.revalidate();
            jTable2.repaint(); 
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated
    // <editor-fold defaultstate="collapsed" desc="Generated
    // <editor-fold defaultstate="collapsed" desc="Generated
    // Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jScrollPane3 = new javax.swing.JScrollPane();
        jTextPane1 = new javax.swing.JTextPane();
        jButton1 = new javax.swing.JButton();
        jScrollPane2 = new javax.swing.JScrollPane();
        jTable2 = new javax.swing.JTable();
        jButton3 = new javax.swing.JButton();
        jLabel2 = new javax.swing.JLabel();
        jPanel6 = new javax.swing.JPanel();
        jSeparator2 = new javax.swing.JSeparator();
        jLabel8 = new javax.swing.JLabel();
        jButton5 = new javax.swing.JButton();
        jLabel12 = new javax.swing.JLabel();
        jButton2 = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jScrollPane3.setViewportView(jTextPane1);

        jButton1.setBackground(new java.awt.Color(51, 153, 255));
        jButton1.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jButton1.setForeground(new java.awt.Color(255, 255, 255));
        jButton1.setText("Rechercher");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        jTable2.setModel(new javax.swing.table.DefaultTableModel(
                new Object[][] {
                },
                new String[] {
                        "ID", "Nom Prenom","CNE", "Email", "Type de document", "Status"
                }));
        fill();
        jScrollPane2.setViewportView(jTable2);
        
        jTable2.setAutoCreateRowSorter(true);

        jButton3.setBackground(new java.awt.Color(51, 51, 255));
        jButton3.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jButton3.setForeground(new java.awt.Color(255, 255, 255));
        jButton3.setText("Télécharger");
        jButton3.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jButton3MouseClicked(evt);
            }
        });
        jButton3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton3ActionPerformed(evt);
            }
        });

        jLabel2.setFont(new java.awt.Font("Arial", 1, 24)); // NOI18N
        jLabel2.setText("Historique");

        jPanel6.setBackground(new java.awt.Color(245, 250, 255));

        jSeparator2.setForeground(new java.awt.Color(51, 153, 255));

        jLabel8.setFont(new java.awt.Font("Arial", 1, 14)); // NOI18N
        jLabel8.setForeground(new java.awt.Color(51, 153, 255));
        jLabel8.setText("Espace reclamations");

        jButton5.setBackground(new java.awt.Color(245, 250, 255));
        jButton5.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jButton5.setForeground(new java.awt.Color(0, 51, 51));
        jButton5.setText("Reclamations");
        jButton5.setBorder(null);
        jButton5.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton5ActionPerformed(evt);
            }
        });

        jLabel12.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icon/left-arrow.png"))); // NOI18N
        jLabel12.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jLabel12MouseClicked(evt);
            }
        });

        javax.swing.GroupLayout jPanel6Layout = new javax.swing.GroupLayout(jPanel6);
        jPanel6.setLayout(jPanel6Layout);
        jPanel6Layout.setHorizontalGroup(
                jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(jPanel6Layout.createSequentialGroup()
                                .addContainerGap()
                                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addGroup(jPanel6Layout.createSequentialGroup()
                                                .addGroup(jPanel6Layout
                                                        .createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                        .addComponent(jLabel12, javax.swing.GroupLayout.PREFERRED_SIZE,
                                                                40, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                        .addComponent(jLabel8))
                                                .addGap(0, 0, Short.MAX_VALUE))
                                        .addGroup(jPanel6Layout.createSequentialGroup()
                                                .addGroup(jPanel6Layout
                                                        .createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                        .addComponent(jSeparator2,
                                                                javax.swing.GroupLayout.PREFERRED_SIZE, 176,
                                                                javax.swing.GroupLayout.PREFERRED_SIZE)
                                                        .addComponent(jButton5, javax.swing.GroupLayout.PREFERRED_SIZE,
                                                                176, javax.swing.GroupLayout.PREFERRED_SIZE))
                                                .addContainerGap(32, Short.MAX_VALUE)))));
        jPanel6Layout.setVerticalGroup(
                jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(jPanel6Layout.createSequentialGroup()
                                .addGap(17, 17, 17)
                                .addComponent(jLabel12)
                                .addGap(57, 57, 57)
                                .addComponent(jLabel8)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(jSeparator2, javax.swing.GroupLayout.PREFERRED_SIZE, 3,
                                        javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addComponent(jButton5, javax.swing.GroupLayout.PREFERRED_SIZE, 38,
                                        javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)));

        jButton2.setBackground(new java.awt.Color(0, 153, 51));
        jButton2.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jButton2.setForeground(new java.awt.Color(255, 255, 255));
        jButton2.setText("Renvoyer");
        jButton2.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jButton2MouseClicked(evt);
            }
        });
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
                layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(layout.createSequentialGroup()
                                .addComponent(jPanel6, javax.swing.GroupLayout.PREFERRED_SIZE,
                                        javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                        .addGroup(layout.createSequentialGroup()
                                                .addGap(29, 29, 29)
                                                .addGroup(layout
                                                        .createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                        .addGroup(layout.createSequentialGroup()
                                                                .addGap(0, 0, Short.MAX_VALUE)
                                                                .addComponent(jButton3)
                                                                .addPreferredGap(
                                                                        javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                                                .addComponent(jButton2,
                                                                        javax.swing.GroupLayout.PREFERRED_SIZE, 91,
                                                                        javax.swing.GroupLayout.PREFERRED_SIZE))
                                                        .addGroup(layout.createSequentialGroup()
                                                                .addComponent(jScrollPane2,
                                                                        javax.swing.GroupLayout.PREFERRED_SIZE, 676,
                                                                        javax.swing.GroupLayout.PREFERRED_SIZE)
                                                                .addGap(0, 0, Short.MAX_VALUE))))
                                        .addGroup(layout.createSequentialGroup()
                                                .addGap(43, 43, 43)
                                                .addComponent(jLabel2)
                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED,
                                                        javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                                .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 238,
                                                        javax.swing.GroupLayout.PREFERRED_SIZE)
                                                .addGap(28, 28, 28)
                                                .addComponent(jButton1)))
                                .addGap(46, 46, 46)));
        layout.setVerticalGroup(
                layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(layout.createSequentialGroup()
                                .addGap(24, 24, 24)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addComponent(jButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 32,
                                                javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addGroup(layout.createSequentialGroup()
                                                .addGroup(layout
                                                        .createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                        .addComponent(jScrollPane3,
                                                                javax.swing.GroupLayout.PREFERRED_SIZE, 32,
                                                                javax.swing.GroupLayout.PREFERRED_SIZE)
                                                        .addComponent(jLabel2))
                                                .addGap(43, 43, 43)
                                                .addGroup(layout
                                                        .createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                                        .addComponent(jButton3, javax.swing.GroupLayout.PREFERRED_SIZE,
                                                                32, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                        .addComponent(jButton2, javax.swing.GroupLayout.PREFERRED_SIZE,
                                                                32, javax.swing.GroupLayout.PREFERRED_SIZE))
                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 339,
                                                        javax.swing.GroupLayout.PREFERRED_SIZE)))
                                .addContainerGap(24, Short.MAX_VALUE))
                        .addComponent(jPanel6, javax.swing.GroupLayout.DEFAULT_SIZE,
                                javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE));

        pack();
    }// </editor-fold>//GEN-END:initComponents

    /**
     * @param evt
     */
    private void jButton3MouseClicked(java.awt.event.MouseEvent evt) {// GEN-FIRST:event_jButton3MouseClicked
        // get the type of doc from the table
        String type = jTable2.getValueAt(jTable2.getSelectedRow(), 4).toString();
        // get the cne from the table
        Integer cne = Integer.parseInt(jTable2.getValueAt(jTable2.getSelectedRow(), 2).toString());
        Integer id = Integer.parseInt(jTable2.getValueAt(jTable2.getSelectedRow(), 0).toString());
        String file_path;
        Dashbrd dash= new Dashbrd();
        if (type.equals("Attestation de scolarité")) {
            file_path = "pdf/Attestation_de_scolarité " + cne.toString() + ".pdf";
            try {
                if (Files.exists(Paths.get(file_path))) {
                    // open the file
                    open(file_path);
                } else {
                    //create it then open it 
                    dash.AS_gen(id);
                    open(file_path);
                }
            } catch (Exception e) {
                JOptionPane.showMessageDialog(null, "Erreur");
            }
        } else if (type.equals("Attestation de réussite")) {
            file_path = "pdf/Attestation_de_réussite " + cne.toString() + ".pdf";
            try {
                if (Files.exists(Paths.get(file_path))) {
                    // open the file
                    open(file_path);
                } else {
                    //create it then open it
                    dash.AR_gen(id);
                    open(file_path);
                }
            } catch (Exception e) {
                JOptionPane.showMessageDialog(null, "Erreur");
            }
        } else if (type.equals("Relevé de notes")) {
            file_path = "pdf/Relevé_de_notes_" + cne.toString() + ".pdf";
            try {
                if (Files.exists(Paths.get(file_path))) {
                    // open the file
                    open(file_path);
                } else {
                    //create it then open it
                    dash.ReleveNotesGenerator(id);
                    open(file_path);
                }
            } catch (Exception e) {
                JOptionPane.showMessageDialog(null, "Erreur");
            }
        } else if (type.equals("Attestation de stage")) {
            file_path = "pdf/Attestation_de_stage" + cne.toString() + ".pdf";
            try {
                if (Files.exists(Paths.get(file_path))) {
                    open(file_path);
                } else {
                    //create it then open it
                    dash.Astage_gen(id);
                    open(file_path);
                }
            } catch (Exception e) {
                JOptionPane.showMessageDialog(null, "Erreur");
            }
            
        } else {
            JOptionPane.showMessageDialog(null, "Erreur de type de document");
        }

    }// GEN-LAST:event_jButton3MouseClicked

    private void jLabel12MouseClicked(java.awt.event.MouseEvent evt) {// GEN-FIRST:event_jLabel12MouseClicked
        Dashbrd firstScreen = new Dashbrd();

        // Set the reference to the current instance (Loginstudent1)
        firstScreen.setHistoriqueInstance(this);

        // Show the first screen
        firstScreen.setVisible(true);

        // Close the current instance (Loginstudent1)
        this.dispose();
    }// GEN-LAST:event_jLabel12MouseClicked

    private void jButton5ActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_jButton5ActionPerformed
        // TODO add your handling code here:
        this.setVisible(false);
        new ReclamAdmin().setVisible(true);
        // set position to default
        this.setLocationRelativeTo(null);
    }// GEN-LAST:event_jButton5ActionPerformed

    private void jButton2MouseClicked(java.awt.event.MouseEvent evt) {// GEN-FIRST:event_jButton2MouseClicked
        // renvoyer le document
        // get the type of doc from the table
        String type = jTable2.getValueAt(jTable2.getSelectedRow(), 4).toString();
        // get the mail from the table
        String mail = jTable2.getValueAt(jTable2.getSelectedRow(), 3).toString();

        String file_path;
        // get the cne from the table
        Integer cne = Integer.parseInt(jTable2.getValueAt(jTable2.getSelectedRow(), 2).toString());
        // check if file exists
        switch (type) {
            case "Attestation de scolarité":

                file_path = "pdf/Attestation_de_scolarité " + cne.toString() + ".pdf";
                if (Files.exists(Paths.get(file_path))) {
                      SendMail.send_email(mail, file_path, "renvoi d'attestation de scolarité", "Attestation de scolarité");
                }else{
                    JOptionPane.showMessageDialog(null, "Erreur File not found,Generate it first");
                }
                break;
            case "Attestation de réussite":

                file_path = "pdf/Attestation_de_réussite " + cne.toString() + ".pdf";
                if(Files.exists(Paths.get(file_path))){
                SendMail.send_email(mail, file_path, "renvoi d'attestation de réussite", "Attestation de réussite");
                }else{
                    JOptionPane.showMessageDialog(null, "Erreur File not found,Generate it first");
                }
                break;
            case "Relevé de notes":

                file_path = "pdf/Relevé_de_notes_" + cne.toString() + ".pdf";
                if(Files.exists(Paths.get(file_path))){
                SendMail.send_email(mail, file_path, "renvoi de relevé de notes", "Relevé de notes");
                }else{
                    JOptionPane.showMessageDialog(null, "Erreur File not found,Generate it first");
                }
                break;
            case "Attestation de stage":
                file_path = "pdf/Attestation_de_stage" + cne.toString() + ".pdf";
                if(Files.exists(Paths.get(file_path))){
                SendMail.send_email(mail, file_path, "renvoi d'attestation de stage", "Attestation de stage");
                }else{
                    JOptionPane.showMessageDialog(null, "Erreur File not found,Generate it first");
                }
                break;
            default:
                JOptionPane.showMessageDialog(null, "Erreur");
        }

    }// GEN-LAST:event_jButton2MouseClicked

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_jButton2ActionPerformed
        // TODO add your handling code here:
    }// GEN-LAST:event_jButton2ActionPerformed

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_jButton1ActionPerformed
        String recherche = jTextPane1.getText();
        TableRowSorter<TableModel> rowSorter = new TableRowSorter<>(jTable2.getModel());
        jTable2.setRowSorter(rowSorter);
        rowSorter.setRowFilter(RowFilter.regexFilter(recherche));
        // if the field is empty call actualiser()
        if (recherche.equals("")) {
            actualiser();
        }
    }// GEN-LAST:event_jButton1ActionPerformed

    private void jButton3ActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_jButton3ActionPerformed
        // TODO add your handling code here:
    }// GEN-LAST:event_jButton3ActionPerformed

    private void jButton5ActionPerformed() {// GEN-FIRST:event_jButton5ActionPerformed
        // TODO add your handling code here:
    }// GEN-LAST:event_jButton5ActionPerformed

    private void jButton6ActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_jButton6ActionPerformed
        // TODO add your handling code here:
    }// GEN-LAST:event_jButton6ActionPerformed

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        // <editor-fold defaultstate="collapsed" desc=" Look and feel setting code
        // (optional) ">
        /*
         * If Nimbus (introduced in Java SE 6) is not available, stay with the default
         * look and feel.
         * For details see
         * http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(Historique.class.getName()).log(java.util.logging.Level.SEVERE, null,
                    ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(Historique.class.getName()).log(java.util.logging.Level.SEVERE, null,
                    ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(Historique.class.getName()).log(java.util.logging.Level.SEVERE, null,
                    ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(Historique.class.getName()).log(java.util.logging.Level.SEVERE, null,
                    ex);
        }
        // </editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new Historique().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JButton jButton3;
    private javax.swing.JButton jButton5;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JPanel jPanel6;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JSeparator jSeparator2;
    private javax.swing.JTable jTable2;
    private javax.swing.JTextPane jTextPane1;
    // End of variables declaration//GEN-END:variables
}
