/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package p1;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javax.swing.JOptionPane;
import javax.swing.RowFilter;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;
import javax.swing.table.TableRowSorter;

import java.awt.Color;
import java.awt.Image;
import java.io.File;
import java.io.FileNotFoundException;
import javax.swing.JFrame;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.graphics.image.PDImageXObject;
import org.apache.pdfbox.pdmodel.font.PDType0Font;

/**
 *
 * @author pc
 */
public class Dashbrd extends javax.swing.JFrame {

        public Integer admin_id;

        /**
         * Creates new form Dashbrd
         */
        public Dashbrd() {
                initComponents();
                this.setLocationRelativeTo(null);
        }
    
    
    // add a function to count the number of demands
        public Integer countDemands() {
                // count the number of demands by adding the nubres on each table
                Integer nb_ReleveN = 0, nb_AttestaionR = 0;
                String Surl, Suser, Spass;
                Surl = "jdbc:mysql://localhost:3306/gl";
                Suser = "root";
                Spass = "";
                try {
                        Connection con = DriverManager.getConnection(Surl, Suser, Spass);
                        Statement st = con.createStatement();
                        ResultSet rs = st.executeQuery("SELECT COUNT(*) FROM demande_rn");
                        rs.next();
                        nb_ReleveN = rs.getInt(1);
                        System.out.println("nb_ReleveN = " + nb_ReleveN);
                        ResultSet rs2 = st.executeQuery("SELECT COUNT(*) FROM demande_ar ");
                        rs2.next();
                        nb_AttestaionR = rs2.getInt(1);
                        System.out.println("nb_AttestaionR = " + nb_AttestaionR);
                        con.close();
                } catch (Exception e) {
                        System.out.println("Error: " + e.getMessage());

                }
                return nb_ReleveN + nb_AttestaionR;
        }

        // Genere le pdf du Attestation de scolarité
        public void AS_gen(Integer id_d) throws FileNotFoundException {
                String nom = "", cin = "", email = "";
                Integer cne = null;
                System.out.println("id_d = " + id_d);

                try {
                        // get the data from the database using the id
                        Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/gl", "root", "");
                        java.sql.Statement stmt = con.createStatement();
                        ResultSet rs = stmt.executeQuery(
                                        "SELECT student.Nom_complet,student.CNE,student.CIN,student.email FROM demande_ar INNER JOIN student ON demande_ar.user_id = student.CNE where demande_ar.id = '"
                                                        + id_d + "';");
                        // get the data from the result set
                        rs.next();
                        nom = rs.getString("Nom_complet");
                        cne = rs.getInt("CNE");
                        cin = rs.getString("CIN");
                        email = rs.getString("email");
                        // close the connection
                        con.close();
                        System.out.println("nom = " + nom);
                        System.out.println("cne = " + cne);
                        System.out.println("cin = " + cin);
                        System.out.println("email = " + email);
                } catch (Exception e) {
                        System.out.println("Error: " + e.getMessage());
                }
                try {
                        PDDocument doc = new PDDocument();
                        PDPage page = new PDPage();
                        doc.addPage(page);

                        PDPageContentStream contentStream = new PDPageContentStream(doc, page);
                        // add the logo of the school in the top left
                        PDImageXObject pdImage = PDImageXObject.createFromFile(
                                        "C:\\Users\\ezzou\\OneDrive\\Desktop\\java_GL_Project\\p1\\src\\icon\\logo.png",
                                        doc);
                        // resize the image 100 100
                        contentStream.drawImage(pdImage, 25, 625, 150, 150);

                        // add the text
                        contentStream.beginText();
                        contentStream.newLineAtOffset(150, 700);
                        // use a old font
                        PDType0Font font = PDType0Font.load(doc,
                                        new File("C:\\Users\\ezzou\\OneDrive\\Desktop\\java_GL_Project\\Calibri.ttf"));
                        PDType0Font font2 = PDType0Font.load(doc,
                                        new File("C:\\Users\\ezzou\\OneDrive\\Desktop\\java_GL_Project\\Calibrib.ttf"));
                        contentStream.setFont(font2, 15);
                        // add the name to the center
                        contentStream.showText("Ecole Nationale des Sciences Appliquées de Tétouan");
                        contentStream.newLineAtOffset(100, -50);
                        contentStream.showText("Attestation de scolarité");
                        contentStream.newLineAtOffset(-200, -100);
                        contentStream.setFont(font, 15);
                        contentStream.showText(
                                        "Le directeur de l'école national des sciences appliquées de tetouan certifie que :");
                        contentStream.newLineAtOffset(10, -20);
                        contentStream.showText("Nom : ");
                        // add the name of the student en gras
                        contentStream.setFont(font2, 15);
                        contentStream.showText(nom);
                        contentStream.setFont(font, 15);
                        contentStream.newLineAtOffset(0, -20);
                        contentStream.showText("Apoogée : ");
                        contentStream.setFont(font2, 15);
                        contentStream.showText(cne.toString());
                        contentStream.setFont(font, 15);
                        contentStream.newLineAtOffset(0, -20);
                        contentStream.showText("CIN : ");
                        contentStream.setFont(font2, 15);
                        contentStream.showText(cin);
                        contentStream.newLineAtOffset(0, -20);
                        contentStream.setFont(font, 15);
                        contentStream.showText("Est regulierement inscrit pour l'année : ");
                        contentStream.setFont(font2, 15);
                        contentStream.showText("2023/2024");
                        contentStream.setFont(font, 15);
                        contentStream.newLineAtOffset(0, -420);
                        contentStream.setNonStrokingColor(Color.DARK_GRAY);
                        contentStream.showText("Adresse : M'HANNECH || B.P.2222 Tétouan");
                        contentStream.newLineAtOffset(0, -20);
                        contentStream.showText("Tél : 0539968802,FAX:0539994624");
                        contentStream.endText();

                        contentStream.close();
                        doc.save("C:\\Users\\ezzou\\OneDrive\\Desktop\\output\\" + cne.toString() + ".pdf");
                        doc.close();
                        System.out.println("PDF created");
                        // update table to set the traité to 1
                        try {
                                Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/gl", "root",
                                                "");
                                java.sql.Statement stmt = con.createStatement();
                                stmt.executeUpdate("UPDATE demande_ar SET traité = '1' WHERE id = '" + id_d + "';");
                                con.close();
                        } catch (Exception e) {
                                System.out.println("Error: " + e.getMessage());
                        }

                } catch (Exception e) {
                        System.out.println("Error: " + e.getMessage());
                }
        }

        // Attestation de réussite
        public void AR_gen(Integer id_d) {
                String nom = "", cin = "", email = "", niveau = "", niveau_doc = "";
                Integer cne = null;
                System.out.println("id_d = " + id_d);

                try {
                        // get the data from the database using the id
                        Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/gl", "root", "");
                        java.sql.Statement stmt = con.createStatement();
                        ResultSet rs = stmt.executeQuery(
                                        "SELECT student.Nom_complet,student.CNE,student.CIN,student.email,student.niveau,demande_ar.niveau AS niveau_d FROM demande_ar INNER JOIN student ON demande_ar.user_id = student.CNE where demande_ar.id = '"
                                                        + id_d + "';");
                        // get the data from the result set
                        rs.next();
                        nom = rs.getString("Nom_complet");
                        cne = rs.getInt("CNE");
                        cin = rs.getString("CIN");
                        email = rs.getString("email");
                        niveau = rs.getString("niveau");
                        niveau_doc = rs.getString("niveau_d");

                        // if the demanded niveau isnot the same as the niveau of the student
                        if (!niveau.equals(niveau_doc)) {
                                JOptionPane.showMessageDialog(null,
                                                "Erreur : Niveau de la demande n'est pas le même que le niveau de l'étudiant");
                        } else {
                                // close the connection
                                con.close();
                                System.out.println("nom = " + nom);
                                System.out.println("cne = " + cne);
                                System.out.println("cin = " + cin);
                                System.out.println("email = " + email);
                                System.out.println("niveau = " + niveau);
                                try {
                                        PDDocument doc = new PDDocument();
                                        PDPage page = new PDPage();
                                        doc.addPage(page);

                                        PDPageContentStream contentStream = new PDPageContentStream(doc, page);
                                        // add the logo of the school in the top left
                                        PDImageXObject pdImage = PDImageXObject.createFromFile(
                                                        "C:\\Users\\ezzou\\OneDrive\\Desktop\\java_GL_Project\\p1\\src\\icon\\logo.png",
                                                        doc);
                                        // resize the image 100 100
                                        contentStream.drawImage(pdImage, 25, 625, 150, 150);

                                        // add the text
                                        contentStream.beginText();
                                        contentStream.newLineAtOffset(150, 700);
                                        // use a old font
                                        PDType0Font font = PDType0Font.load(doc, new File(
                                                        "C:\\Users\\ezzou\\OneDrive\\Desktop\\java_GL_Project\\Calibri.ttf"));
                                        PDType0Font font2 = PDType0Font.load(doc, new File(
                                                        "C:\\Users\\ezzou\\OneDrive\\Desktop\\java_GL_Project\\Calibrib.ttf"));
                                        contentStream.setFont(font2, 15);
                                        // add the name to the center
                                        contentStream.showText("Ecole Nationale des Sciences Appliquées de Tétouan");
                                        contentStream.newLineAtOffset(100, -50);
                                        contentStream.showText("Attestation de réussite");
                                        contentStream.newLineAtOffset(-200, -100);
                                        contentStream.setFont(font, 15);
                                        contentStream.showText(
                                                        "Le directeur de l'école national des sciences appliquées de tetouan atteste que :");
                                        contentStream.newLineAtOffset(10, -20);
                                        contentStream.showText("Nom : ");
                                        // add the name of the student en gras
                                        contentStream.setFont(font2, 15);
                                        contentStream.showText(nom);
                                        contentStream.setFont(font, 15);
                                        contentStream.newLineAtOffset(0, -20);
                                        contentStream.showText("Apoogée : ");
                                        contentStream.setFont(font2, 15);
                                        contentStream.showText(cne.toString());
                                        // a ete declare admis en :
                                        contentStream.setFont(font, 15);
                                        contentStream.newLineAtOffset(0, -20);
                                        contentStream.showText("A été déclaré admis en : ");
                                        contentStream.setFont(font2, 15);
                                        contentStream.showText(niveau);
                                        contentStream.setFont(font, 15);
                                        contentStream.newLineAtOffset(0, -20);
                                        contentStream.showText("Pour l'année universitaire : ");
                                        contentStream.setFont(font2, 15);
                                        contentStream.showText("2023/2024");
                                        contentStream.setFont(font, 15);
                                        contentStream.newLineAtOffset(0, -420);
                                        contentStream.setNonStrokingColor(Color.DARK_GRAY);
                                        contentStream.showText("Adresse : M'HANNECH || B.P.2222 Tétouan");
                                        contentStream.newLineAtOffset(0, -20);
                                        contentStream.showText("Tél : 0539968802,FAX:0539994624");
                                        contentStream.endText();

                                        contentStream.close();

                                        doc.save("C:\\Users\\ezzou\\OneDrive\\Desktop\\output\\Attestation_de_réussite "
                                                        + cne.toString() + ".pdf");
                                        doc.close();
                                        System.out.println("PDF created");
                                        // update table to set the traité to 1
                                        try {
                                                Connection con2 = DriverManager.getConnection(
                                                                "jdbc:mysql://localhost:3306/gl", "root", "");
                                                java.sql.Statement stmt2 = con2.createStatement();
                                                stmt2.executeUpdate("UPDATE demande_ar SET traité = '1' WHERE id = '"
                                                                + id_d + "';");
                                                con2.close();
                                        } catch (Exception e) {
                                                System.out.println("Error: " + e.getMessage());
                                        }
                                        // close the connection
                                        con.close();
                                        System.out.println("nom = " + nom);
                                        System.out.println("cne = " + cne);
                                        System.out.println("cin = " + cin);
                                        System.out.println("email = " + email);
                                } catch (Exception e) {
                                        System.out.println("Error: " + e.getMessage());
                                }
                        }
                } catch (Exception e) {
                        System.out.println("Error: " + e.getMessage());
                }

        }

        // calcul des commandes non traitées
        public Integer countNtDemands() {
                // count the number of demands by adding the nubres on each table
                Integer nb_ReleveN = 0, nb_AttestaionR = 0;
                String Surl, Suser, Spass;
                Surl = "jdbc:mysql://localhost:3306/gl";
                Suser = "root";
                Spass = "";
                try {
                        Connection con = DriverManager.getConnection(Surl, Suser, Spass);
                        Statement st = con.createStatement();
                        ResultSet rs = st.executeQuery("SELECT COUNT(*) FROM demande_rn where traité = '0'");
                        rs.next();
                        nb_ReleveN = rs.getInt(1);
                        System.out.println("nb_ReleveN = " + nb_ReleveN);
                        ResultSet rs2 = st.executeQuery("SELECT COUNT(*) FROM demande_ar where traité = '0'");
                        rs2.next();
                        nb_AttestaionR = rs2.getInt(1);
                        System.out.println("nb_AttestaionR = " + nb_AttestaionR);
                        con.close();
                } catch (Exception e) {
                        System.out.println("Error: " + e.getMessage());

                }
                return nb_ReleveN + nb_AttestaionR;
        }

        public void fill() {
                try {
                        Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/gl", "root", "");
                        java.sql.Statement stmt = con.createStatement();
                        ResultSet rs = stmt.executeQuery(
                                        "SELECT demande_ar.id,student.Nom_complet,student.email,student.CNE FROM demande_ar INNER JOIN student ON demande_ar.user_id = student.CNE where traité = '0';");
                        DefaultTableModel model = (DefaultTableModel) jTable2.getModel();
                        model.setRowCount(0); // Clear the table before adding rows

                        while (rs.next()) {
                                System.out.println("Row from database: " + rs.getInt("id") + ", "
                                                + rs.getString("Nom_complet") + ", "
                                                + rs.getString("email") + ", Attestation de réussite"); // Debug print
                                model.addRow(new Object[] { rs.getString("id"), rs.getString("Nom_complet"),
                                                rs.getInt("student.CNE"),
                                                rs.getString("email"), "Date", "Attestation de réussite" });
                        }
                        jTable2.revalidate();
                        jTable2.repaint();

                        System.out.println("Table should be updated now.");
                } catch (SQLException e) {
                        e.printStackTrace();
                }
                // now the second table demande_rn
                try {
                        Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/gl", "root", "");
                        java.sql.Statement stmt = con.createStatement();
                        ResultSet rs = stmt.executeQuery(
                                        "SELECT demande_rn.id,student.Nom_complet,student.email,student.CNE FROM demande_rn INNER JOIN student ON demande_rn.user_id = student.CNE where traité = '0';");
                        DefaultTableModel model = (DefaultTableModel) jTable2.getModel();

                        while (rs.next()) {
                                System.out.println("Row from database: " + rs.getInt("id") + ", "
                                                + rs.getString("Nom_complet") + ", "
                                                + rs.getString("email") + ", Relevé de notes"); // Debug print
                                model.addRow(new Object[] { rs.getString("id"), rs.getString("Nom_complet"),
                                                rs.getInt("student.CNE"),
                                                rs.getString("email"), "Date", "Relevé de notes" });
                        }
                        jTable2.revalidate();
                        jTable2.repaint();

                        System.out.println("Table should be updated now.");
                } catch (SQLException e) {
                        e.printStackTrace();
                } catch (Exception e) {
                        // TODO: handle exception
                }
        }

        /**
         * This method is called from within the constructor to initialize the form.
         * WARNING: Do NOT modify this code. The content of this method is always
         * regenerated by the Form Editor.
         */
        @SuppressWarnings("unchecked")
        // <editor-fold defaultstate="collapsed" desc="Generated
        // Code">//GEN-BEGIN:initComponents
        private void initComponents() {

        jScrollPane1 = new javax.swing.JScrollPane();
        jTable1 = new javax.swing.JTable();
        jPanel2 = new javax.swing.JPanel();
        jPanel1 = new javax.swing.JPanel();
        jButton1 = new javax.swing.JButton();
        jScrollPane3 = new javax.swing.JScrollPane();
        jTextPane1 = new javax.swing.JTextPane();
        jPanel3 = new javax.swing.JPanel();
        jLabel2 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jPanel5 = new javax.swing.JPanel();
        jLabel4 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        jPanel4 = new javax.swing.JPanel();
        jLabel3 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        jPanel6 = new javax.swing.JPanel();
        jLabel9 = new javax.swing.JLabel();
        jButton6 = new javax.swing.JButton();
        jSeparator1 = new javax.swing.JSeparator();
        jLabel8 = new javax.swing.JLabel();
        jSeparator5 = new javax.swing.JSeparator();
        jButton7 = new javax.swing.JButton();
        jScrollPane2 = new javax.swing.JScrollPane();
        jTable2 = new javax.swing.JTable();

                jTable1.setModel(new javax.swing.table.DefaultTableModel(
                                new Object[][] {
                                                { null, null, null, null },
                                                { null, null, null, null },
                                                { null, null, null, null },
                                                { null, null, null, null }
                                },
                                new String[] {
                                                "Title 1", "Title 2", "Title 3", "Title 4"
                                }));
                jScrollPane1.setViewportView(jTable1);

                jPanel2.setBackground(new java.awt.Color(204, 204, 204));

                javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
                jPanel2.setLayout(jPanel2Layout);
                jPanel2Layout.setHorizontalGroup(
                                jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                .addGap(0, 958, Short.MAX_VALUE));
                jPanel2Layout.setVerticalGroup(
                                jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                .addGap(0, 544, Short.MAX_VALUE));

                setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

                jButton1.setBackground(new java.awt.Color(51, 153, 255));
                jButton1.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
                jButton1.setForeground(new java.awt.Color(255, 255, 255));
                jButton1.setText("Rechercher");
                jButton1.setBorder(null);
                jButton1.addActionListener(new java.awt.event.ActionListener() {
                        public void actionPerformed(java.awt.event.ActionEvent evt) {
                                jButton1ActionPerformed(evt);
                        }
                });

                jScrollPane3.setViewportView(jTextPane1);

                jPanel3.setBackground(new java.awt.Color(255, 255, 255));

                jLabel2.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
                jLabel2.setText("Nombre total des demandes");

        jLabel5.setFont(new java.awt.Font("Segoe UI", 1, 24)); // NOI18N
        jLabel5.setText(countDemands().toString());

                javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
                jPanel3.setLayout(jPanel3Layout);
                jPanel3Layout.setHorizontalGroup(
                                jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                .addGroup(jPanel3Layout.createSequentialGroup()
                                                                .addContainerGap(39, Short.MAX_VALUE)
                                                                .addGroup(jPanel3Layout.createParallelGroup(
                                                                                javax.swing.GroupLayout.Alignment.LEADING)
                                                                                .addGroup(javax.swing.GroupLayout.Alignment.TRAILING,
                                                                                                jPanel3Layout.createSequentialGroup()
                                                                                                                .addComponent(jLabel2)
                                                                                                                .addGap(14, 14, 14))
                                                                                .addGroup(javax.swing.GroupLayout.Alignment.TRAILING,
                                                                                                jPanel3Layout.createSequentialGroup()
                                                                                                                .addComponent(jLabel5)
                                                                                                                .addGap(98, 98, 98)))));
                jPanel3Layout.setVerticalGroup(
                                jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel3Layout
                                                                .createSequentialGroup()
                                                                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE,
                                                                                Short.MAX_VALUE)
                                                                .addComponent(jLabel5)
                                                                .addGap(26, 26, 26)
                                                                .addComponent(jLabel2)
                                                                .addGap(15, 15, 15)));

                jPanel5.setBackground(new java.awt.Color(255, 255, 255));

                jLabel4.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
                jLabel4.setText("Demandes non traitées");

        jLabel7.setFont(new java.awt.Font("Segoe UI", 1, 24)); // NOI18N
        jLabel7.setText(countNtDemands().toString());

        javax.swing.GroupLayout jPanel5Layout = new javax.swing.GroupLayout(jPanel5);
        jPanel5.setLayout(jPanel5Layout);
        jPanel5Layout.setHorizontalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addContainerGap(39, Short.MAX_VALUE)
                .addComponent(jLabel4)
                .addGap(19, 19, 19))
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addGap(101, 101, 101)
                .addComponent(jLabel7)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel5Layout.setVerticalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel5Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jLabel7)
                .addGap(28, 28, 28)
                .addComponent(jLabel4)
                .addGap(16, 16, 16))
        );

                jPanel4.setBackground(new java.awt.Color(51, 153, 255));

                jLabel3.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
                jLabel3.setForeground(new java.awt.Color(255, 255, 255));
                jLabel3.setText("Demandes traitées");

        jLabel6.setBackground(new java.awt.Color(255, 255, 255));
        jLabel6.setFont(new java.awt.Font("Segoe UI", 1, 24)); // NOI18N
        jLabel6.setForeground(new java.awt.Color(255, 255, 255));
        Integer dm_tr=countDemands()-countNtDemands();
        jLabel6.setText(dm_tr.toString());

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addContainerGap(58, Short.MAX_VALUE)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel4Layout.createSequentialGroup()
                        .addComponent(jLabel6)
                        .addGap(103, 103, 103))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel4Layout.createSequentialGroup()
                        .addComponent(jLabel3)
                        .addGap(53, 53, 53))))
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel4Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jLabel6)
                .addGap(27, 27, 27)
                .addComponent(jLabel3)
                .addGap(14, 14, 14))
        );

                jPanel6.setBackground(new java.awt.Color(245, 250, 255));

                jLabel9.setFont(new java.awt.Font("Arial", 1, 14)); // NOI18N
                jLabel9.setForeground(new java.awt.Color(51, 153, 255));
                jLabel9.setText("Historique");

        jButton6.setBackground(new java.awt.Color(245, 250, 255));
        jButton6.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jButton6.setForeground(new java.awt.Color(0, 51, 51));
        jButton6.setText("Réclamations");
        jButton6.setBorder(null);
        jButton6.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton6ActionPerformed(evt);
                ReclamAdmin r = new ReclamAdmin();
                r.show();
                r.setLocationRelativeTo(null);
                r.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                dispose();


            }
        });

                jSeparator1.setForeground(new java.awt.Color(51, 153, 255));

                jLabel8.setFont(new java.awt.Font("Arial", 1, 14)); // NOI18N
                jLabel8.setForeground(new java.awt.Color(51, 153, 255));
                jLabel8.setText("Espace reclamations");

                jSeparator5.setForeground(new java.awt.Color(51, 153, 255));

        jButton7.setBackground(new java.awt.Color(245, 250, 255));
        jButton7.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jButton7.setForeground(new java.awt.Color(0, 51, 51));
        jButton7.setText("Historique");
        jButton7.setBorder(null);
        jButton7.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton7ActionPerformed(evt);
                Historique h=new Historique();
                h.show();
                h.setLocationRelativeTo(null);
                dispose();
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
                        .addGap(6, 6, 6)
                        .addComponent(jButton7, javax.swing.GroupLayout.PREFERRED_SIZE, 168, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jLabel9)
                    .addComponent(jLabel8)
                    .addComponent(jSeparator5, javax.swing.GroupLayout.PREFERRED_SIZE, 184, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jSeparator1, javax.swing.GroupLayout.PREFERRED_SIZE, 182, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel6Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jButton6, javax.swing.GroupLayout.PREFERRED_SIZE, 167, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel6Layout.setVerticalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel6Layout.createSequentialGroup()
                .addGap(77, 77, 77)
                .addComponent(jLabel9)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jSeparator1, javax.swing.GroupLayout.PREFERRED_SIZE, 3, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(12, 12, 12)
                .addComponent(jButton7, javax.swing.GroupLayout.PREFERRED_SIZE, 38, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jLabel8)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jSeparator5, javax.swing.GroupLayout.PREFERRED_SIZE, 3, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jButton6, javax.swing.GroupLayout.PREFERRED_SIZE, 38, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(528, Short.MAX_VALUE))
        );

        jTable2.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Nom Prenom","CNE", "Email", "Type de document", "Date", "Decision"
            }
        ));
        fill();
                
        jScrollPane2.setViewportView(jTable2);

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addComponent(jPanel6, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                                .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 238, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(26, 26, 26)
                                .addComponent(jButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 94, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                                .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addComponent(jPanel4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addComponent(jPanel5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 28, Short.MAX_VALUE)
                        .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 767, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(21, 21, 21))))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addGap(17, 17, 17)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                    .addComponent(jButton1, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jScrollPane3, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 32, Short.MAX_VALUE))
                .addGap(30, 30, 30)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel5, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(40, 40, 40)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 359, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addComponent(jPanel6, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
        );

                javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
                getContentPane().setLayout(layout);
                layout.setHorizontalGroup(
                                layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                .addGroup(layout.createSequentialGroup()
                                                                .addComponent(jPanel1,
                                                                                javax.swing.GroupLayout.PREFERRED_SIZE,
                                                                                javax.swing.GroupLayout.DEFAULT_SIZE,
                                                                                javax.swing.GroupLayout.PREFERRED_SIZE)
                                                                .addGap(0, 0, Short.MAX_VALUE)));
                layout.setVerticalGroup(
                                layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                .addGroup(layout.createSequentialGroup()
                                                                .addComponent(jPanel1,
                                                                                javax.swing.GroupLayout.PREFERRED_SIZE,
                                                                                622,
                                                                                javax.swing.GroupLayout.PREFERRED_SIZE)
                                                                .addGap(0, 0, Short.MAX_VALUE)));

                pack();
        }// </editor-fold>//GEN-END:initComponents

        private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_jButton1ActionPerformed
                // TODO add your handling code here:
                String recherche = jTextPane1.getText();
                TableRowSorter<TableModel> rowSorter = new TableRowSorter<>(jTable2.getModel());
                jTable2.setRowSorter(rowSorter);
                rowSorter.setRowFilter(RowFilter.regexFilter(recherche));
        }// GEN-LAST:event_jButton1ActionPerformed

        private void jButton6ActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_jButton6ActionPerformed
                // TODO add your handling code here:
        }// GEN-LAST:event_jButton6ActionPerformed

        private void jButton7ActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_jButton7ActionPerformed
                // TODO add your handling code here:
        }// GEN-LAST:event_jButton7ActionPerformed

        private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_jButton2ActionPerformed
                // TODO add your handling code here:

        }// GEN-LAST:event_jButton2ActionPerformed

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
            java.util.logging.Logger.getLogger(Dashbrd.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(Dashbrd.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(Dashbrd.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(Dashbrd.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

                /* Create and display the form */
                java.awt.EventQueue.invokeLater(new Runnable() {
                        public void run() {
                                new Dashbrd().setVisible(true);
                        }
                });
        }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton6;
    private javax.swing.JButton jButton7;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JPanel jPanel6;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JSeparator jSeparator1;
    private javax.swing.JSeparator jSeparator5;
    private javax.swing.JTable jTable1;
    private javax.swing.JTable jTable2;
    private javax.swing.JTextPane jTextPane1;
    // End of variables declaration//GEN-END:variables
}
