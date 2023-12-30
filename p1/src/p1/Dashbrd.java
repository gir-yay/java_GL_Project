/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package p1;


import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Document;
import com.itextpdf.text.Element;
import com.itextpdf.text.FontFactory;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.Rectangle;
import com.itextpdf.text.pdf.BaseFont;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;



import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import javax.swing.JOptionPane;
import javax.swing.RowFilter;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;
import javax.swing.table.TableRowSorter;

import java.awt.Color;
import java.awt.Desktop;
import java.awt.Font;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.text.DecimalFormat;
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

        private Historique HistoriqueInstance;
        private ReclamAdmin ReclamInstance;

        public void setHistoriqueInstance(Historique HistoriqueInstance) {
                this.HistoriqueInstance = HistoriqueInstance;
        }

        public void setReclamInstance(ReclamAdmin ReclamInstance) {
                this.ReclamInstance = ReclamInstance;
        }

        public Integer admin_id;

        /**
         * Creates new form Dashbrd
         */
        public Dashbrd() {
                initComponents();
                setTitle("Acceuil de scolarité");
                this.setLocationRelativeTo(null);
        }
        public void open(String path){
                try {
                    File file = new File(path);
                    Desktop.getDesktop().open(file);
                } catch (Exception e) {
                    JOptionPane.showMessageDialog(null, "Erreur");
                }
            }

        public void actualiser() {
                // clear it first then fill()
                DefaultTableModel model = (DefaultTableModel) jTable2.getModel();
                model.setRowCount(0); // Clear the table before adding rows
                fill();
                //update the number of demands
                jLabel5.setText(countDemands().toString());
                //update the number of demands non traitées
                jLabel7.setText(countNtDemands().toString());
                //update the number of demands traitées
                Integer tmp=countDemands()-countNtDemands();
                jLabel6.setText(tmp.toString());
        }

        // add a function to count the number of demands
        public Integer countDemands() {
                // count the number of demands by adding the nubres on each table
                Integer nbdemande = 0;
                String Surl, Suser, Spass;
                Surl = "jdbc:mysql://localhost:3306/gl";
                Suser = "root";
                Spass = "";
                try {
                        Connection con = DriverManager.getConnection(Surl, Suser, Spass);
                        Statement st = con.createStatement();
                        ResultSet rs = st.executeQuery("SELECT COUNT(*) FROM demande_ar");
                        rs.next();
                        nbdemande = rs.getInt(1);
                        ResultSet rs2 = st.executeQuery("SELECT COUNT(*) FROM demande_as");
                        rs2.next();
                        nbdemande += rs2.getInt(1);
                        ResultSet rs3 = st.executeQuery("SELECT COUNT(*) FROM demande_rn");
                        rs3.next();
                        nbdemande += rs3.getInt(1);
                        ResultSet rs4 = st.executeQuery("SELECT COUNT(*) FROM stage");
                        rs4.next();
                        nbdemande += rs4.getInt(1);
                        // close the connection
                        con.close();
                } catch (Exception e) {
                        System.out.println("count demande Error: " + e.getMessage());
                }
                return nbdemande;
        }

        // calcul des commandes non traitées
        public Integer countNtDemands() {
                // count the number of demands by adding the nubres on each table
                Integer nbdemandeNt = 0;
                String Surl, Suser, Spass;
                Surl = "jdbc:mysql://localhost:3306/gl";
                Suser = "root";
                Spass = "";
                try {
                        Connection con = DriverManager.getConnection(Surl, Suser, Spass);
                        Statement st = con.createStatement();
                        ResultSet rs = st.executeQuery("SELECT COUNT(*) FROM demande_ar where traité = '0';");
                        rs.next();
                        nbdemandeNt = rs.getInt(1);
                        ResultSet rs2 = st.executeQuery("SELECT COUNT(*) FROM demande_as where traite = '0';");
                        rs2.next();
                        nbdemandeNt += rs2.getInt(1);
                        ResultSet rs3 = st.executeQuery("SELECT COUNT(*) FROM demande_rn where traité = '0';");
                        rs3.next();
                        nbdemandeNt += rs3.getInt(1);
                        ResultSet rs4 = st.executeQuery("SELECT COUNT(*) FROM stage where traité = '0';");
                        rs4.next();
                        nbdemandeNt += rs4.getInt(1);
                        // close the connection
                        con.close();
                } catch (Exception e) {
                        System.out.println("Error: " + e.getMessage());
                }
                return nbdemandeNt;
        }

        // Genere le pdf du Attestation de scolarité
        public void AS_gen(Integer id_d) throws FileNotFoundException {
                String nom = "", cin = "", email = "";
                Integer cne = null;
                try {
                        // get the data from the database using the id
                        Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/gl", "root", "");
                        java.sql.Statement stmt = con.createStatement();
                        ResultSet rs = stmt.executeQuery(
                                        "SELECT student.Nom_complet,student.CNE,student.CIN,student.email FROM demande_as INNER JOIN student ON demande_as.user_id = student.CNE where demande_as.id = '"
                                                        + id_d + "';");
                        // get the data from the result set
                        rs.next();
                        nom = rs.getString("Nom_complet");
                        cne = rs.getInt("CNE");
                        cin = rs.getString("CIN");
                        email = rs.getString("email");
                        
                        // close the connection
                        con.close();
                } catch (Exception e) {
                        System.out.println("Error: " + e.getMessage());
                        actualiser();
                }
                try {
                        PDDocument doc = new PDDocument();
                        PDPage page = new PDPage();
                        doc.addPage(page);

                        PDPageContentStream contentStream = new PDPageContentStream(doc, page);
                        // add the logo of the school in the top left
                        PDImageXObject pdImage = PDImageXObject.createFromFile(
                                        "img/logo.png",
                                        doc);
                        // resize the image 100 100
                        contentStream.drawImage(pdImage, 25, 625, 120, 120);

                        // add the text
                        contentStream.beginText();
                        contentStream.newLineAtOffset(120, 700);
                        // use a old font
                        PDType0Font font = PDType0Font.load(doc,
                                        new File("fonts/Calibri.ttf"));
                        PDType0Font font2 = PDType0Font.load(doc,
                                        new File("fonts/Calibrib.ttf"));
                        contentStream.setFont(font2, 12);
                        // add the name to the center
                        contentStream.showText("Ecole Nationale des Sciences Appliquées de Tétouan");
                        contentStream.newLineAtOffset(100, -50);
                        contentStream.showText("Attestation de scolarité");
                        contentStream.newLineAtOffset(-200, -100);
                        contentStream.setFont(font, 12);
                        contentStream.showText(
                                        "Le directeur de l'école national des sciences appliquées de tetouan certifie que :");
                        contentStream.newLineAtOffset(10, -20);
                        contentStream.showText("Nom : ");
                        // add the name of the student en gras
                        contentStream.setFont(font2, 12);
                        contentStream.showText(nom);
                        contentStream.setFont(font, 12);
                        contentStream.newLineAtOffset(0, -20);
                        contentStream.showText("Apoogée : ");
                        contentStream.setFont(font2, 12);
                        contentStream.showText(cne.toString());
                        contentStream.setFont(font, 12);
                        contentStream.newLineAtOffset(0, -20);
                        contentStream.showText("CIN : ");
                        contentStream.setFont(font2, 12);
                        contentStream.showText(cin);
                        contentStream.newLineAtOffset(0, -20);
                        contentStream.setFont(font, 12);
                        contentStream.showText("Est regulierement inscrit pour l'année : ");
                        contentStream.setFont(font2, 12);
                        contentStream.showText("2023/2024");
                        contentStream.setFont(font, 12);
                        contentStream.newLineAtOffset(0, -420);
                        contentStream.setNonStrokingColor(Color.DARK_GRAY);
                        contentStream.showText("Adresse : M'HANNECH || B.P.2222 Tétouan");
                        contentStream.newLineAtOffset(0, -20);
                        contentStream.showText("Tél : 0539968802,FAX:0539994624");
                        contentStream.endText();

                        contentStream.close();
                        doc.save("pdf/Attestation_de_scolarité "
                                        + cne.toString() + ".pdf");
                        doc.close();
                        
                        System.out.println("Attestation de scolarité created");
                        
                        // update table to set the traité to 1
                        try {
                                Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/gl", "root",
                                                "");
                                java.sql.Statement stmt = con.createStatement();
                                stmt.executeUpdate("UPDATE demande_as SET traite = 1, statuts= 1 WHERE id = '"
                                                + id_d + "';");
                                con.close();
                                // actualiser la table
                                actualiser();
                        } catch (Exception e) {
                                System.out.println("Error: " + e.getMessage());
                                actualiser();
                        }

                } catch (Exception e) {
                        System.out.println("Error: " + e.getMessage());
                        actualiser();

                }
        }

        // Attestation de réussite
        public void AR_gen(Integer id_d) {
                String nom = "", cin = "", email = "", niveau = "", niveau_doc = "";
                Integer cne = null;
                try {
                        // get the data from the database using the id
                        Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/gl", "root", "");
                        java.sql.Statement stmt = con.createStatement();
                        ResultSet rs = stmt.executeQuery(
                                        "SELECT student.Nom_complet,student.CNE,student.CIN,student.email,student.major,demande_ar.niveau AS niveau_d FROM demande_ar INNER JOIN student ON demande_ar.user_id = student.CNE where demande_ar.id = '"
                                                        + id_d + "';");
                        // get the data from the result set
                        rs.next();
                        nom = rs.getString("Nom_complet");
                        cne = rs.getInt("CNE");
                        cin = rs.getString("CIN");
                        email = rs.getString("email");
                        niveau = rs.getString("major");
                        niveau_doc = rs.getString("niveau_d");

                        // if the demanded niveau isnot the same as the niveau of the student
                        if (!niveau.equals(niveau_doc)) {
                                JOptionPane.showMessageDialog(null,
                                                "Erreur : Niveau de la demande n'est pas le même que le niveau de l'étudiant");
                                actualiser();
                        } else {
                                // close the connection
                                con.close();
                                try {
                                        PDDocument doc = new PDDocument();
                                        PDPage page = new PDPage();
                                        doc.addPage(page);

                                        PDPageContentStream contentStream = new PDPageContentStream(doc, page);
                                        // add the logo of the school in the top left
                                        PDImageXObject pdImage = PDImageXObject.createFromFile(
                                                        "img/logo.png",
                                                        doc);
                                        // resize the image 100 100
                                        contentStream.drawImage(pdImage, 25, 625, 120, 120);

                                        // add the text
                                        contentStream.beginText();
                                        contentStream.newLineAtOffset(120, 700);
                                        // use a old font
                                        PDType0Font font = PDType0Font.load(doc, new File(
                                                        "fonts/Calibri.ttf"));
                                        PDType0Font font2 = PDType0Font.load(doc, new File(
                                                        "fonts/Calibrib.ttf"));
                                        contentStream.setFont(font2, 12);
                                        // add the name to the center
                                        contentStream.showText("Ecole Nationale des Sciences Appliquées de Tétouan");
                                        contentStream.newLineAtOffset(100, -50);
                                        contentStream.showText("Attestation de réussite");
                                        contentStream.newLineAtOffset(-200, -100);
                                        contentStream.setFont(font, 12);
                                        contentStream.showText(
                                                        "Le directeur de l'école national des sciences appliquées de tetouan atteste que :");
                                        contentStream.newLineAtOffset(10, -20);
                                        contentStream.showText("Nom : ");
                                        // add the name of the student en gras
                                        contentStream.setFont(font2, 12);
                                        contentStream.showText(nom);
                                        contentStream.setFont(font, 12);
                                        contentStream.newLineAtOffset(0, -20);
                                        contentStream.showText("Apoogée : ");
                                        contentStream.setFont(font2, 12);
                                        contentStream.showText(cne.toString());
                                        // a ete declare admis en :
                                        contentStream.setFont(font, 12);
                                        contentStream.newLineAtOffset(0, -20);
                                        contentStream.showText("A été déclaré admis en : ");
                                        contentStream.setFont(font2, 12);
                                        contentStream.showText(niveau);
                                        contentStream.setFont(font, 12);
                                        contentStream.newLineAtOffset(0, -20);
                                        contentStream.showText("Pour l'année universitaire : ");
                                        contentStream.setFont(font2, 12);
                                        contentStream.showText("2023/2024");
                                        contentStream.setFont(font, 12);
                                        contentStream.newLineAtOffset(0, -420);
                                        contentStream.setNonStrokingColor(Color.DARK_GRAY);
                                        contentStream.showText("Adresse : M'HANNECH || B.P.2222 Tétouan");
                                        contentStream.newLineAtOffset(0, -20);
                                        contentStream.showText("Tél : 0539968802,FAX:0539994624");
                                        contentStream.endText();

                                        contentStream.close();

                                        doc.save("pdf/Attestation_de_réussite "
                                                        + cne.toString() + ".pdf");
                                        doc.close();
                                        System.out.println("Attetatation de réussite created");
                                        
                                        
                                        
                                        // update table to set the traité to 1
                                        try {
                                                Connection con2 = DriverManager.getConnection(
                                                                "jdbc:mysql://localhost:3306/gl", "root", "");
                                                java.sql.Statement stmt2 = con2.createStatement();
                                                stmt2.executeUpdate("UPDATE demande_ar SET traité = '1' ,statuts='1' WHERE id = '"
                                                                + id_d + "';");
                                                con2.close();
                                        } catch (Exception e) {
                                                System.out.println("Error: " + e.getMessage());
                                        }
                                        // close the connection
                                        con.close();
                                        System.out.println("Attestation de réussite created");
                                        actualiser();
                                       

                                } catch (Exception e) {
                                        System.out.println("Error: " + e.getMessage());
                                }
                        }
                } catch (Exception e) {
                        System.out.println("Error: " + e.getMessage());
                        actualiser();
                }

        }
        // Creation du relevé de notes
        
        /**
         * @param id_d
         */
        
        public void ReleveNotesGenerator(Integer id_d) {
        double sommeNotes = 0;
        int nombreDeModules = 0; 
        String nom = "" , niveau="", email="";
        Integer cne = null;
        float cellHeight = 50f;
        
        
        
        try {
            Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/gl", "root", "");
            Statement stmt = conn.createStatement();
            Statement stmt1 = conn.createStatement();

            // Récupérer les informations de l'étudiant à partir de la table demande_rn
            ResultSet rsStudent = stmt.executeQuery("SELECT student.Nom_complet, student.CNE ,student.email, demande_rn.niveau FROM demande_rn INNER JOIN student ON demande_rn.user_id = student.CNE WHERE demande_rn.id = " 
                         + id_d + " AND demande_rn.traité = '0' LIMIT 1");

            if (rsStudent.next()) {
                nom = rsStudent.getString("Nom_complet");
                cne = rsStudent.getInt("CNE");
                niveau = rsStudent.getString("niveau");
                email = rsStudent.getString("email");

                // Générer le document PDF
                Document doc = new Document();
                PdfWriter.getInstance(doc, new FileOutputStream("pdf/Relevé_de_notes_" + cne.toString() + ".pdf"));
                doc.open();
               PdfPTable titleTable = new PdfPTable(1);
               titleTable.setWidthPercentage(100);

               PdfPCell titleCell = new PdfPCell();

               PdfPTable innerTable = new PdfPTable(1);
               PdfPCell cell1 = new PdfPCell(new Phrase("Université Abdelmalek Essaadi"));
               cell1.setBorder(Rectangle.NO_BORDER);
               innerTable.addCell(cell1);
               titleCell.addElement(innerTable);
               titleCell.setBorder(Rectangle.BOX);
               titleTable.addCell(titleCell);
               doc.add(titleTable);

             
                
                Paragraph title2 = new Paragraph("Ecole Nationale des Sciences Appliquéées Tétouan   ");
                title2.setAlignment(Element.ALIGN_LEFT);
                doc.add(title2);
                doc.add(new Paragraph("\n\n"));
                PdfPTable titreTable = new PdfPTable(1);
                                titleTable.setWidthPercentage(100);
                                PdfPCell titreCell = new PdfPCell(new Phrase("RELEVE DE NOTES ET RESULTATS", FontFactory.getFont("Times New Roman", 14,Font.BOLD)));
                                titreCell.setBorder(Rectangle.LEFT | Rectangle.TOP | Rectangle.RIGHT | Rectangle.BOTTOM);
                                titreCell.setBackgroundColor(BaseColor.LIGHT_GRAY);  
                                titreCell.setHorizontalAlignment(Element.ALIGN_CENTER);
                                titreCell.setFixedHeight(30f);
                                titreTable.addCell(titreCell);
                                doc.add(titreTable);
                
                // Ajouter les informations de l'étudiant
                doc.add(new Paragraph(nom));
                doc.add(new Paragraph("N° Etudiant : " + cne));
                doc.add(new Paragraph("inscrit en :  " + niveau));
                doc.add(new Paragraph(" a obtenu les notes suivantes : "));
                doc.add(new Paragraph("\n"));

                // Créer le tableau PDF
                PdfPTable pdfTable = new PdfPTable(4);
                pdfTable.setWidthPercentage(100);

                // Ajouter les en-têtes du tableau
                String[] headers = {"Module", "Note", "Résultat", "Pts jury"};
                for (String header : headers) {
                    PdfPCell cell = new PdfPCell(new Phrase(header, FontFactory.getFont("Times New Roman", 12)));
                    cell.setHorizontalAlignment(Element.ALIGN_CENTER);
                    cell.setBackgroundColor(BaseColor.LIGHT_GRAY);
                    cell.setFixedHeight(30f);
                    pdfTable.addCell(cell);
                }

                // Remplir le tableau avec les données de la base de données
                ResultSet rsNotes = stmt1.executeQuery("SELECT * FROM notes_" + niveau + " WHERE user_id = " + cne);
                while (rsNotes.next()) {
                    String module = rsNotes.getString("module");
                    String note = rsNotes.getString("note");
                    String resultat = rsNotes.getString("Résultat");
                    String ptsJury = rsNotes.getString("pts jury");

                    PdfPCell cellModule = new PdfPCell(new Phrase(module));
                    cellModule.setHorizontalAlignment(PdfPCell.ALIGN_LEFT);
                    cellModule.setFixedHeight(30f);
                    pdfTable.addCell(cellModule);

                    PdfPCell cellNote = new PdfPCell(new Phrase(note));
                    cellNote.setHorizontalAlignment(PdfPCell.ALIGN_LEFT);
                    cellNote.setFixedHeight(30f);
                    pdfTable.addCell(cellNote);

                    PdfPCell cellResultat = new PdfPCell(new Phrase(resultat));
                    cellResultat.setHorizontalAlignment(PdfPCell.ALIGN_LEFT);
                    cellResultat.setFixedHeight(30f);
                    pdfTable.addCell(cellResultat);

                    PdfPCell cellPtsJury = new PdfPCell(new Phrase(ptsJury));
                    cellPtsJury.setHorizontalAlignment(PdfPCell.ALIGN_LEFT);
                    cellPtsJury.setFixedHeight(30f);
                    pdfTable.addCell(cellPtsJury);
    
                    //calculer la somme des notes et incrémenter le compteur
                    double noteModule = Double.parseDouble(note);
                   sommeNotes += noteModule;
                   nombreDeModules++;
                }

                // Ajouter le tableau au document
                doc.add(pdfTable);
                // Calculer la moyenne
                    double moyenne = (nombreDeModules > 0) ? sommeNotes / nombreDeModules : 0;
                    // Créez un objet DecimalFormat avec le modèle spécifiant deux chiffres après la virgule
                    DecimalFormat df = new DecimalFormat("#.####");

                   // Utilisez la méthode format pour formater le nombre
                        String moyenneFormatee = df.format(moyenne);

                // Ajouter la moyenne au document
                doc.add(new Paragraph("\nRésultat d'admission : " + moyenneFormatee ,FontFactory.getFont("Times New Roman", 16, Font.BOLD)));

                doc.close();
                
               
                                                            
               

                // Mettre à jour la table demande_rn pour marquer la demande comme traitée
                stmt.executeUpdate("UPDATE demande_rn SET traité = '1' ,statuts='1' WHERE id = " + id_d);
            } else {
                System.out.println("Aucun enregistrement trouvé pour l'ID de demande fourni.");
            }
        } catch (Exception e) {
            System.out.println("Une erreur s'est produite : " + e.getMessage());
        }
    }

    


        // Creation de l'attestaion de stage
        /**
         * @param id_d
         */
        public void Astage_gen(Integer id_d) {
                Integer cne = null;
                try {
                        // get the data from the database using the id
                        Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/gl", "root", "");
                        java.sql.Statement stmt = con.createStatement();
                        // get the data for the attestation de stage from stage table and student table
                        ResultSet rs = stmt.executeQuery(
                                        "SELECT * FROM stage INNER JOIN student ON stage.user_id = student.CNE where stage.id = '"+ id_d + "';");
                        // get the data from the result set
                        rs.next();
                        String nom = rs.getString("Nom_complet");
                        cne = rs.getInt("CNE");
                        String entreprise = rs.getString("nom_entreprise");
                        String date_debut = rs.getString("debut_stage");
                        String date_fin = rs.getString("fin_stage");
                        String major = rs.getString("major");
                        String email = rs.getString("email");
                        
                        String file_path;

                        // start writing the pdf
                        try {
                                PDDocument doc = new PDDocument();
                                PDPage page = new PDPage();
                                doc.addPage(page);

                                PDPageContentStream contentStream = new PDPageContentStream(doc, page);
                                // add the logo of the school in the top left
                                PDImageXObject pdImage = PDImageXObject.createFromFile(
                                                "img/logo.png",
                                                doc);
                                // resize the image 100 100
                                contentStream.drawImage(pdImage, 25, 650, 150, 150);

                                // add the text
                                contentStream.beginText();
                                contentStream.newLineAtOffset(150, 720);
                                // use a old font
                                PDType0Font font = PDType0Font.load(doc, new File(
                                                "fonts/Calibri.ttf"));
                                PDType0Font font2 = PDType0Font.load(doc, new File(
                                                "fonts/Calibrib.ttf"));
                                contentStream.setFont(font2, 15);
                                // add the name to the center
                                contentStream.showText("Ecole Nationale des Sciences Appliquées de Tétouan");
                                contentStream.newLineAtOffset(100, -40);
                                contentStream.showText("Convention de stage");
                                contentStream.newLineAtOffset(-225, -40);
                                contentStream.setFont(font, 12);
                                contentStream.showText(
                                                "L Ecole Nationale des Sciences Appliquées, Université Abdelmalek Essaâdi -");
                                contentStream.newLineAtOffset(0, -20);
                                contentStream.showText("Tétouan B.P. 2222,Mhannech II, Tétouan, Maroc");
                                contentStream.newLineAtOffset(0, -20);
                                contentStream.showText(
                                                "Tél. +212 5 39 68 80 27 ; Fax. +212 39 99 46 24. Web: ensa-tetouan.ac.ma ");
                                contentStream.newLineAtOffset(0, -20);
                                contentStream.setFont(font, 12);
                                contentStream.showText(" Représenté par le Professeur");
                                // change to bold
                                contentStream.setFont(font2, 15);
                                contentStream.showText(" Kamal REKLAOUI");
                                // change to normal
                                contentStream.setFont(font, 12);
                                contentStream.showText(",en qualité de Directeur");
                                contentStream.newLineAtOffset(0, -20);
                                contentStream.showText("Ci-après, dénommé l'Etablissement");
                                contentStream.newLineAtOffset(0, -20);
                                contentStream.showText("Et");
                                contentStream.newLineAtOffset(0, -20);
                                contentStream.showText("La société : ");
                                // change to bold
                                contentStream.setFont(font2, 15);
                                contentStream.showText(entreprise);
                                // change to normal
                                contentStream.newLineAtOffset(0, -20);
                                contentStream.setFont(font, 12);
                                contentStream.showText("Adresse:");
                                // change to bold
                                contentStream.setFont(font2, 15);
                                contentStream.showText(rs.getString("secteur"));
                                // change to normal
                                contentStream.newLineAtOffset(0, -20);
                                contentStream.setFont(font, 12);
                                contentStream.showText("Telephone:");
                                // change to bold
                                contentStream.setFont(font2, 15);
                                contentStream.showText(rs.getString("tel_entreprise"));
                                // change to normal
                                // make a tabulation after the telephone
                                contentStream.showText("            ");
                                contentStream.setFont(font, 12);
                                contentStream.showText("Email:");
                                // change to bold
                                contentStream.setFont(font2, 15);
                                contentStream.showText(rs.getString("email_entreprise"));
                                // change to normal
                                contentStream.newLineAtOffset(0, -20);
                                contentStream.setFont(font, 12);
                                contentStream.showText("Représentée par Monsieur :");
                                // change to bold
                                contentStream.setFont(font2, 15);
                                contentStream.showText(rs.getString("encadrant_entreprise"));
                                // change to normal
                                contentStream.newLineAtOffset(0, -20);
                                contentStream.setFont(font, 12);
                                contentStream.showText("en qualité Fondateur");
                                contentStream.newLineAtOffset(0, -20);
                                contentStream.showText("Ci-après, dénommée L'ENTREPRISE");
                                contentStream.newLineAtOffset(0, -20);
                                // change to bold
                                contentStream.setFont(font2, 12);
                                contentStream.showText("Article 1: Engagement ");
                                contentStream.newLineAtOffset(0, -20);
                                contentStream.showText("L'ENTREPRISE");
                                // change to normal
                                contentStream.showText("accepte de recevoir à titre de stagiaire");
                                // change to bold
                                contentStream.setFont(font2, 12);
                                contentStream.showText(nom);
                                // change to normal
                                contentStream.showText(" étudiant de la filière du Cycle Ingénieur ");
                                // change to bold
                                // new line
                                contentStream.newLineAtOffset(0, -20);
                                contentStream.setFont(font2, 12);
                                contentStream.showText(major);
                                // change to normal
                                contentStream.setFont(font, 12);
                                contentStream.showText(
                                                " de l'ENSA de Tétouan, Université Abdelmalek Essaâdi (Tétouan), pour une ");
                                contentStream.newLineAtOffset(0, -20);
                                // new line
                                contentStream.showText("période allant du  ");
                                // change to bold
                                contentStream.setFont(font2, 12);
                                contentStream.showText(date_debut);
                                // change to normal
                                contentStream.setFont(font, 12);
                                contentStream.showText(" au ");
                                // change to bold
                                contentStream.setFont(font2, 12);
                                contentStream.showText(date_fin);
                                // new line in bold
                                contentStream.newLineAtOffset(0, -20);
                                contentStream.setFont(font2, 12);
                                contentStream.showText(
                                                "En aucun cas, cette convention ne pourra autoriser les étudiants à s'absenter durant la période des");
                                contentStream.newLineAtOffset(0, -20);
                                contentStream.showText("contrôles ou des enseignements.");
                                contentStream.newLineAtOffset(0, -20);
                                contentStream.showText("Article2: Objet ");
                                contentStream.newLineAtOffset(0, -20);
                                contentStream.setFont(font, 12);
                                contentStream.showText(
                                                "Le stage aura pour objet essentiel d'assurer l'application pratique de l'enseignement donné par");
                                contentStream.newLineAtOffset(0, -20);
                                // bold
                                contentStream.setFont(font2, 12);
                                contentStream.showText("L'etablissement");
                                contentStream.setFont(font, 12);
                                contentStream.showText(
                                                ", et ce, en organisant des visites sur les installations et en réalisant des études ");
                                contentStream.newLineAtOffset(0, -20);
                                contentStream.showText("proposées par ");
                                contentStream.setFont(font2, 12);
                                contentStream.showText("L'ENTREPRISE");
                                contentStream.newLineAtOffset(0, -20);
                                contentStream.showText("Article 3: Encadrement et suivi");
                                contentStream.newLineAtOffset(0, -20);
                                contentStream.setFont(font, 12);
                                contentStream.showText(
                                                "Pour accompagner le Stagiaire durant son stage, et ainsi instaurer une véritable collaboration");
                                contentStream.newLineAtOffset(0, -20);
                                contentStream.showText(
                                                "L'ENTREPRISE/Stagiaire/Etablissement, L'ENTREPRISE désigne Mme/Mr encadrant(e) et parrain(e),");
                                contentStream.newLineAtOffset(0, -20);
                                contentStream.showText(
                                                "pour superviser et assurer la qualité du travail fourni par le Stagiaire.");
                                contentStream.newLineAtOffset(0, -20);
                                contentStream.showText("L'Etablissement désigne ");
                                contentStream.setFont(font2, 12);
                                contentStream.showText(rs.getString("encadrant_ensa"));
                                contentStream.setFont(font, 12);
                                contentStream.showText("en tant que tuteur qui procurera une assistancepédagogique.");
                                contentStream.newLineAtOffset(0, -20);
                                contentStream.setFont(font2, 12);
                                contentStream.showText("Article 4: Programme:");
                                contentStream.newLineAtOffset(0, -20);
                                contentStream.setFont(font, 12);
                                contentStream.showText("Le thème du stage est: << ");
                                contentStream.setFont(font2, 12);
                                contentStream.showText(rs.getString("sujet_stage"));
                                contentStream.setFont(font, 12);
                                contentStream.showText(">>");
                                contentStream.newLineAtOffset(0, -20);
                                contentStream.showText(
                                                "Ce programme a été défini conjointement par l'Etablissement, L'ENTREPRISE et le Stagiaire.");
                                // start a new page if the text is too long
                                contentStream.endText();
                                contentStream.close();
                                PDPage secondPage = new PDPage();
                                doc.addPage(secondPage);
                                contentStream = new PDPageContentStream(doc, secondPage);
                                contentStream.beginText();
                                contentStream.setFont(font, 12);
                                contentStream.newLineAtOffset(25, 750);

                                contentStream.newLineAtOffset(0, -20);
                                contentStream.newLineAtOffset(0, -20);
                                contentStream.showText(
                                                "Le contenu de ce programme doit permettre au Stagiaire une réflexion en relation avec les enseignements");
                                contentStream.newLineAtOffset(0, -20);
                                contentStream.showText(
                                                "ou le projet de fin d'études qui s'inscrit dans le programme de formation de l'Etablissement");
                                contentStream.newLineAtOffset(0, -20);
                                contentStream.setFont(font2, 12);
                                contentStream.showText("Article 5: Durée du stage");
                                contentStream.newLineAtOffset(0, -20);
                                contentStream.setFont(font, 12);
                                contentStream.showText(
                                                "Au cours du stage, l'étudiant ne pourra prétendre à aucun salaire de la part de L'ENTREPRISE.");
                                contentStream.newLineAtOffset(0, -20);
                                contentStream.showText(
                                                "Cependant, si l'ENTREPRISE et l'étudiant le conviennent, ce dernier pourra recevoir une indemnité");
                                contentStream.newLineAtOffset(0, -20);
                                contentStream.showText(
                                                "forfaitaire de la part de l'ENTREPRISE des frais occasionnés par la mission confiée à l'étudiant.");
                                contentStream.newLineAtOffset(0, -20);
                                contentStream.setFont(font2, 12);
                                contentStream.showText("Article 6: Règlement");
                                contentStream.setFont(font, 12);
                                contentStream.newLineAtOffset(0, -20);
                                contentStream.setFont(font, 12);
                                contentStream.showText(
                                                "Pendant la durée du stage, le Stagiaire reste placé sous la responsabilité de ");
                                contentStream.setFont(font2, 12);
                                contentStream.showText("l'Etablissement.");
                                contentStream.setFont(font, 12);
                                contentStream.newLineAtOffset(0, -20);
                                contentStream.showText(
                                                "Cependant, l'étudiant est tenu d'informer l'école dans un délai de 24h sur toute modification portant");
                                contentStream.newLineAtOffset(0, -20);
                                contentStream.showText(
                                                "sur la convention déjà signée, sinon il en assumera toute sa responsabilité sur son non-respect de la");
                                contentStream.newLineAtOffset(0, -20);
                                contentStream.showText(
                                                "convention signée par l'école.");
                                contentStream.newLineAtOffset(0, -20);
                                contentStream.setFont(font2, 12);
                                contentStream.showText("Article 7: Confidentialité");
                                contentStream.newLineAtOffset(0, -20);
                                contentStream.setFont(font, 12);
                                contentStream.showText(
                                                "Le Stagiaire et l'ensemble des acteurs liés à son travail (l'administration de l'Etablissement, le parrain");
                                contentStream.newLineAtOffset(0, -20);
                                contentStream.showText(
                                                "pédagogique ...) sont tenus au secret professionnel. Ils s'engagent à ne pas diffuser les informations");
                                contentStream.newLineAtOffset(0, -20);
                                contentStream.showText(
                                                "recueillies à des fins de publications, conférences, communications, sans raccord préalable de");
                                contentStream.newLineAtOffset(0, -20);
                                contentStream.showText(
                                                "L'ENTREPRISE. Cette obligation demeure valable après l'expiration du stage");
                                contentStream.newLineAtOffset(0, -20);
                                contentStream.setFont(font2, 12);
                                contentStream.showText("Article 8: Assurance accident de travail");
                                contentStream.setFont(font, 12);
                                contentStream.newLineAtOffset(0, -20);
                                contentStream.showText(
                                                "Le stagiaire devra obligatoirement souscrire une assurance couvrant la Responsabilité Civile et Accident de");
                                contentStream.newLineAtOffset(0, -20);
                                contentStream.showText(
                                                "Travail, durant les stages et trajets effectués. En cas d'accident de travail survenant durant la période du");
                                contentStream.newLineAtOffset(0, -20);
                                contentStream.showText(
                                                "stage, L'ENTREPRISE s'engage à faire parvenir immédiatement à l'Etablissement toutes les informations");
                                contentStream.newLineAtOffset(0, -20);
                                contentStream.showText("indispensables à la déclaration dudit accident.");
                                contentStream.newLineAtOffset(0, -20);
                                contentStream.setFont(font2, 12);
                                contentStream.showText("Article 9: Evaluation de L'ENTREPRISE");
                                contentStream.setFont(font, 12);
                                contentStream.newLineAtOffset(0, -20);
                                contentStream.showText(
                                                "Le stage accompli, le parrain établira un rapport d'appréciations générales sur le travail effectué et le");
                                contentStream.newLineAtOffset(0, -20);
                                contentStream.showText(
                                                "comportement du Stagiaire durant son séjour chez L'ENTREPRISE.");
                                contentStream.newLineAtOffset(0, -20);
                                contentStream.showText(
                                                "L'ENTREPRISE remettra au Stagiaire une attestation indiquant la nature et la durée des travaux");
                                contentStream.newLineAtOffset(0, -20);
                                contentStream.showText(
                                                "effectués.");
                                contentStream.newLineAtOffset(0, -20);
                                contentStream.setFont(font2, 12);
                                contentStream.showText("Article 10: Rapport de stage");
                                contentStream.setFont(font, 12);
                                contentStream.newLineAtOffset(0, -20);
                                contentStream.showText(
                                                "A l'issue de chaque stage, le Stagiaire rédigera un rapport de stage faisant état de ses travaux et de son vécu");
                                contentStream.newLineAtOffset(0, -20);
                                contentStream.showText(
                                                "au sein de L'ENTREPRISE. Ce rapport sera communiqué à L'ENTREPRISE et restera strictement confidentiel.");
                                contentStream.newLineAtOffset(0, -20);
                                contentStream.showText(
                                                "                               Faite à Tétouan,. en deux exemplaires, le ");
                                contentStream.setFont(font2, 12);
                                DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd");
                                LocalDateTime now = LocalDateTime.now();
                                contentStream.showText(dtf.format(now));
                                contentStream.setFont(font, 12);
                                contentStream.newLineAtOffset(0, -20);
                                contentStream.showText("Nom et signature du Stagiaire");
                                contentStream.showText(
                                                "                                         Nom et signature du représentant de L'ENTREPRISE");
                                contentStream.newLineAtOffset(0, -70);
                                contentStream.showText("Signature et cachet de L'Etablissement");
                                contentStream.showText(
                                                "=                                        Signature et cachet de L'ENTREPRISE");
                                contentStream.endText();
                                contentStream.close();

                                doc.save("pdf/Attestation_de_stage"
                                                + cne.toString() + ".pdf");
                                doc.close();
                                
                               

                                

                                // update table to set the traité to 1
                                try {
                                        Connection con2 = DriverManager.getConnection(
                                                        "jdbc:mysql://localhost:3306/gl", "root", "");
                                        java.sql.Statement stmt2 = con2.createStatement();
                                        stmt2.executeUpdate("UPDATE stage SET traité = '1' ,statuts = '1' WHERE id = '"
                                                        + id_d + "';");
                                        con2.close();
                                } catch (Exception e) {
                                        System.out.println("Error: " + e.getMessage());
                                }
                                // close the connection
                                con.close();
                                System.out.println("Attestation de stage created");
                                actualiser();

                        } catch (Exception e) {
                                // TODO: handle exception
                                System.out.println("Error: " + e.getMessage());
                                actualiser();
                        }

                } catch (Exception e) {
                        System.out.println("Error: " + e.getMessage());
                        actualiser();
                }

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
                                model.addRow(new Object[] { rs.getString("id"), rs.getString("Nom_complet"),
                                                rs.getInt("student.CNE"),
                                                rs.getString("email"), "Attestation de réussite" });
                        }
                        jTable2.revalidate();
                        jTable2.repaint();
                        System.out.println("Added Attestation de réussite to the table.");
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
                                model.addRow(new Object[] { rs.getString("id"), rs.getString("Nom_complet"),
                                                rs.getInt("student.CNE"),
                                                rs.getString("email"), "Relevé de notes" });
                        }
                        jTable2.revalidate();
                        jTable2.repaint();
                        System.out.println("Added Relevé de notes to the table.");
                } catch (SQLException e) {
                        e.printStackTrace();
                }
                // Attestation de stage table
                try {
                        Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/gl", "root", "");
                        java.sql.Statement stmt = con.createStatement();
                        ResultSet rs = stmt.executeQuery(
                                        "SELECT stage.id,student.Nom_complet,student.email,student.CNE FROM stage INNER JOIN student ON stage.user_id = student.CNE where traité = '0';");
                        DefaultTableModel model = (DefaultTableModel) jTable2.getModel();

                        while (rs.next()) {
                                model.addRow(new Object[] { rs.getString("id"), rs.getString("Nom_complet"),
                                                rs.getInt("student.CNE"),
                                                rs.getString("email"), "Attestation de stage", });
                        }
                        jTable2.revalidate();
                        jTable2.repaint();

                        System.out.println("Added Attestation de stage to the table.");
                } catch (SQLException e) {
                        e.printStackTrace();
                }
                // demande_as table
                try {
                        Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/gl", "root", "");
                        java.sql.Statement stmt = con.createStatement();
                        ResultSet rs = stmt.executeQuery(
                                        "SELECT demande_as.id,student.Nom_complet,student.email,student.CNE FROM demande_as INNER JOIN student ON demande_as.user_id = student.CNE where traite = '0';");
                        DefaultTableModel model = (DefaultTableModel) jTable2.getModel();

                        while (rs.next()) {
                                model.addRow(new Object[] { rs.getString("id"), rs.getString("Nom_complet"),
                                                rs.getInt("student.CNE"),
                                                rs.getString("email"), "Attestation de scolarité" });
                        }
                        jTable2.revalidate();
                        jTable2.repaint();

                        System.out.println("Added Attestation de scolarité to the table.");
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
                jButton2 = new javax.swing.JButton();
                jButton3 = new javax.swing.JButton();
                jButton4 = new javax.swing.JButton();

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
                Integer nb = countDemands();
                jLabel5.setText(nb.toString());

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
                Integer nb2 = countNtDemands();
                jLabel7.setText(nb2.toString());

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
                                                                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE,
                                                                                Short.MAX_VALUE)));
                jPanel5Layout.setVerticalGroup(
                                jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel5Layout
                                                                .createSequentialGroup()
                                                                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE,
                                                                                Short.MAX_VALUE)
                                                                .addComponent(jLabel7)
                                                                .addGap(28, 28, 28)
                                                                .addComponent(jLabel4)
                                                                .addGap(16, 16, 16)));

                jPanel4.setBackground(new java.awt.Color(51, 153, 255));

                jLabel3.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
                jLabel3.setForeground(new java.awt.Color(255, 255, 255));
                jLabel3.setText("Demandes traitées");

                jLabel6.setBackground(new java.awt.Color(255, 255, 255));
                jLabel6.setFont(new java.awt.Font("Segoe UI", 1, 24)); // NOI18N
                jLabel6.setForeground(new java.awt.Color(255, 255, 255));
                Integer nb3 = nb - nb2;
                jLabel6.setText(nb3.toString());

                javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
                jPanel4.setLayout(jPanel4Layout);
                jPanel4Layout.setHorizontalGroup(
                                jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                .addGroup(jPanel4Layout.createSequentialGroup()
                                                                .addContainerGap(64, Short.MAX_VALUE)
                                                                .addGroup(jPanel4Layout.createParallelGroup(
                                                                                javax.swing.GroupLayout.Alignment.LEADING)
                                                                                .addGroup(javax.swing.GroupLayout.Alignment.TRAILING,
                                                                                                jPanel4Layout.createSequentialGroup()
                                                                                                                .addComponent(jLabel6)
                                                                                                                .addGap(103, 103,
                                                                                                                                103))
                                                                                .addGroup(javax.swing.GroupLayout.Alignment.TRAILING,
                                                                                                jPanel4Layout.createSequentialGroup()
                                                                                                                .addComponent(jLabel3)
                                                                                                                .addGap(53, 53, 53)))));
                jPanel4Layout.setVerticalGroup(
                                jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel4Layout
                                                                .createSequentialGroup()
                                                                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE,
                                                                                Short.MAX_VALUE)
                                                                .addComponent(jLabel6)
                                                                .addGap(27, 27, 27)
                                                                .addComponent(jLabel3)
                                                                .addGap(14, 14, 14)));

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
                        }
                });

                javax.swing.GroupLayout jPanel6Layout = new javax.swing.GroupLayout(jPanel6);
                jPanel6.setLayout(jPanel6Layout);
                jPanel6Layout.setHorizontalGroup(
                                jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                .addGroup(jPanel6Layout.createSequentialGroup()
                                                                .addContainerGap()
                                                                .addGroup(jPanel6Layout.createParallelGroup(
                                                                                javax.swing.GroupLayout.Alignment.LEADING)
                                                                                .addGroup(jPanel6Layout
                                                                                                .createSequentialGroup()
                                                                                                .addGap(6, 6, 6)
                                                                                                .addComponent(jButton7,
                                                                                                                javax.swing.GroupLayout.PREFERRED_SIZE,
                                                                                                                168,
                                                                                                                javax.swing.GroupLayout.PREFERRED_SIZE))
                                                                                .addComponent(jLabel9)
                                                                                .addComponent(jLabel8)
                                                                                .addComponent(jSeparator5,
                                                                                                javax.swing.GroupLayout.PREFERRED_SIZE,
                                                                                                184,
                                                                                                javax.swing.GroupLayout.PREFERRED_SIZE)
                                                                                .addComponent(jSeparator1,
                                                                                                javax.swing.GroupLayout.PREFERRED_SIZE,
                                                                                                182,
                                                                                                javax.swing.GroupLayout.PREFERRED_SIZE))
                                                                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE,
                                                                                Short.MAX_VALUE))
                                                .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel6Layout
                                                                .createSequentialGroup()
                                                                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE,
                                                                                Short.MAX_VALUE)
                                                                .addComponent(jButton6,
                                                                                javax.swing.GroupLayout.PREFERRED_SIZE,
                                                                                167,
                                                                                javax.swing.GroupLayout.PREFERRED_SIZE)
                                                                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE,
                                                                                Short.MAX_VALUE)));
                jPanel6Layout.setVerticalGroup(
                                jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                .addGroup(jPanel6Layout.createSequentialGroup()
                                                                .addGap(77, 77, 77)
                                                                .addComponent(jLabel9)
                                                                .addPreferredGap(
                                                                                javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                                                .addComponent(jSeparator1,
                                                                                javax.swing.GroupLayout.PREFERRED_SIZE,
                                                                                3,
                                                                                javax.swing.GroupLayout.PREFERRED_SIZE)
                                                                .addGap(12, 12, 12)
                                                                .addComponent(jButton7,
                                                                                javax.swing.GroupLayout.PREFERRED_SIZE,
                                                                                38,
                                                                                javax.swing.GroupLayout.PREFERRED_SIZE)
                                                                .addPreferredGap(
                                                                                javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                                                .addComponent(jLabel8)
                                                                .addPreferredGap(
                                                                                javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                                                .addComponent(jSeparator5,
                                                                                javax.swing.GroupLayout.PREFERRED_SIZE,
                                                                                3,
                                                                                javax.swing.GroupLayout.PREFERRED_SIZE)
                                                                .addGap(18, 18, 18)
                                                                .addComponent(jButton6,
                                                                                javax.swing.GroupLayout.PREFERRED_SIZE,
                                                                                38,
                                                                                javax.swing.GroupLayout.PREFERRED_SIZE)
                                                                .addContainerGap(528, Short.MAX_VALUE)));

                jTable2.setModel(new javax.swing.table.DefaultTableModel(
                                new Object[][] {
                                },
                                new String[] {
                                                "ID", "Nom Prenom", "CNE", "Email", "Type de document"
                                }));
                fill();
                jScrollPane2.setViewportView(jTable2);
                jTable2.setAutoCreateRowSorter(true);

                jButton2.setBackground(new java.awt.Color(0, 153, 0));
                jButton2.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
                jButton2.setForeground(new java.awt.Color(255, 255, 255));
                jButton2.setText("Accepter");
                jButton2.addActionListener(new java.awt.event.ActionListener() {
                        public void actionPerformed(java.awt.event.ActionEvent evt) {
                                jButton2ActionPerformed(evt);
                        }
                });

                jButton3.setBackground(new java.awt.Color(255, 0, 0));
                jButton3.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
                jButton3.setForeground(new java.awt.Color(255, 255, 255));
                jButton3.setText("Refuser");
                jButton3.addActionListener(new java.awt.event.ActionListener() {
                        public void actionPerformed(java.awt.event.ActionEvent evt) {
                                jButton3ActionPerformed(evt);
                        }
                });

                jButton4.setBackground(new java.awt.Color(0, 51, 255));
                jButton4.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
                jButton4.setForeground(new java.awt.Color(255, 255, 255));
                jButton4.setText("Télécharger");
                jButton4.addActionListener(new java.awt.event.ActionListener() {
                        public void actionPerformed(java.awt.event.ActionEvent evt) {
                                jButton4ActionPerformed(evt);
                        }
                });

                javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
                jPanel1.setLayout(jPanel1Layout);
                jPanel1Layout.setHorizontalGroup(
                                jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                .addGroup(jPanel1Layout.createSequentialGroup()
                                                                .addComponent(jPanel6,
                                                                                javax.swing.GroupLayout.PREFERRED_SIZE,
                                                                                javax.swing.GroupLayout.DEFAULT_SIZE,
                                                                                javax.swing.GroupLayout.PREFERRED_SIZE)
                                                                .addGroup(jPanel1Layout.createParallelGroup(
                                                                                javax.swing.GroupLayout.Alignment.LEADING)
                                                                                .addGroup(jPanel1Layout
                                                                                                .createSequentialGroup()
                                                                                                .addPreferredGap(
                                                                                                                javax.swing.LayoutStyle.ComponentPlacement.RELATED,
                                                                                                                28,
                                                                                                                Short.MAX_VALUE)
                                                                                                .addGroup(jPanel1Layout
                                                                                                                .createParallelGroup(
                                                                                                                                javax.swing.GroupLayout.Alignment.LEADING)
                                                                                                                .addGroup(jPanel1Layout
                                                                                                                                .createSequentialGroup()
                                                                                                                                .addComponent(jScrollPane3,
                                                                                                                                                javax.swing.GroupLayout.PREFERRED_SIZE,
                                                                                                                                                238,
                                                                                                                                                javax.swing.GroupLayout.PREFERRED_SIZE)
                                                                                                                                .addGap(26, 26, 26)
                                                                                                                                .addComponent(jButton1,
                                                                                                                                                javax.swing.GroupLayout.PREFERRED_SIZE,
                                                                                                                                                94,
                                                                                                                                                javax.swing.GroupLayout.PREFERRED_SIZE))
                                                                                                                .addGroup(jPanel1Layout
                                                                                                                                .createSequentialGroup()
                                                                                                                                .addComponent(jPanel3,
                                                                                                                                                javax.swing.GroupLayout.PREFERRED_SIZE,
                                                                                                                                                javax.swing.GroupLayout.DEFAULT_SIZE,
                                                                                                                                                javax.swing.GroupLayout.PREFERRED_SIZE)
                                                                                                                                .addGap(18, 18, 18)
                                                                                                                                .addComponent(jPanel4,
                                                                                                                                                javax.swing.GroupLayout.PREFERRED_SIZE,
                                                                                                                                                javax.swing.GroupLayout.DEFAULT_SIZE,
                                                                                                                                                javax.swing.GroupLayout.PREFERRED_SIZE)
                                                                                                                                .addGap(18, 18, 18)
                                                                                                                                .addComponent(jPanel5,
                                                                                                                                                javax.swing.GroupLayout.PREFERRED_SIZE,
                                                                                                                                                javax.swing.GroupLayout.DEFAULT_SIZE,
                                                                                                                                                javax.swing.GroupLayout.PREFERRED_SIZE))
                                                                                                                .addComponent(jScrollPane2,
                                                                                                                                javax.swing.GroupLayout.PREFERRED_SIZE,
                                                                                                                                767,
                                                                                                                                javax.swing.GroupLayout.PREFERRED_SIZE))
                                                                                                .addContainerGap(21,
                                                                                                                Short.MAX_VALUE))
                                                                                .addGroup(jPanel1Layout
                                                                                                .createSequentialGroup()
                                                                                                .addPreferredGap(
                                                                                                                javax.swing.LayoutStyle.ComponentPlacement.RELATED,
                                                                                                                javax.swing.GroupLayout.DEFAULT_SIZE,
                                                                                                                Short.MAX_VALUE)
                                                                                                .addComponent(jButton2,
                                                                                                                javax.swing.GroupLayout.PREFERRED_SIZE,
                                                                                                                88,
                                                                                                                javax.swing.GroupLayout.PREFERRED_SIZE)
                                                                                                .addPreferredGap(
                                                                                                                javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                                                                                .addComponent(jButton3,
                                                                                                                javax.swing.GroupLayout.PREFERRED_SIZE,
                                                                                                                84,
                                                                                                                javax.swing.GroupLayout.PREFERRED_SIZE)
                                                                                                .addPreferredGap(
                                                                                                                javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                                                                                .addComponent(jButton4)
                                                                                                .addGap(35, 35, 35)))));
                jPanel1Layout.setVerticalGroup(
                                jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout
                                                                .createSequentialGroup()
                                                                .addGap(17, 17, 17)
                                                                .addGroup(jPanel1Layout.createParallelGroup(
                                                                                javax.swing.GroupLayout.Alignment.TRAILING,
                                                                                false)
                                                                                .addComponent(jButton1,
                                                                                                javax.swing.GroupLayout.Alignment.LEADING,
                                                                                                javax.swing.GroupLayout.DEFAULT_SIZE,
                                                                                                javax.swing.GroupLayout.DEFAULT_SIZE,
                                                                                                Short.MAX_VALUE)
                                                                                .addComponent(jScrollPane3,
                                                                                                javax.swing.GroupLayout.Alignment.LEADING,
                                                                                                javax.swing.GroupLayout.DEFAULT_SIZE,
                                                                                                32, Short.MAX_VALUE))
                                                                .addGap(30, 30, 30)
                                                                .addGroup(jPanel1Layout.createParallelGroup(
                                                                                javax.swing.GroupLayout.Alignment.LEADING,
                                                                                false)
                                                                                .addComponent(jPanel3,
                                                                                                javax.swing.GroupLayout.PREFERRED_SIZE,
                                                                                                0, Short.MAX_VALUE)
                                                                                .addComponent(jPanel5,
                                                                                                javax.swing.GroupLayout.PREFERRED_SIZE,
                                                                                                89,
                                                                                                javax.swing.GroupLayout.PREFERRED_SIZE)
                                                                                .addComponent(jPanel4,
                                                                                                javax.swing.GroupLayout.PREFERRED_SIZE,
                                                                                                89,
                                                                                                javax.swing.GroupLayout.PREFERRED_SIZE))
                                                                .addGap(36, 36, 36)
                                                                .addGroup(jPanel1Layout.createParallelGroup(
                                                                                javax.swing.GroupLayout.Alignment.LEADING,
                                                                                false)
                                                                                .addComponent(jButton3,
                                                                                                javax.swing.GroupLayout.DEFAULT_SIZE,
                                                                                                31, Short.MAX_VALUE)
                                                                                .addComponent(jButton2,
                                                                                                javax.swing.GroupLayout.DEFAULT_SIZE,
                                                                                                javax.swing.GroupLayout.DEFAULT_SIZE,
                                                                                                Short.MAX_VALUE)
                                                                                .addComponent(jButton4,
                                                                                                javax.swing.GroupLayout.DEFAULT_SIZE,
                                                                                                javax.swing.GroupLayout.DEFAULT_SIZE,
                                                                                                Short.MAX_VALUE))
                                                                .addGap(18, 18, 18)
                                                                .addComponent(jScrollPane2,
                                                                                javax.swing.GroupLayout.PREFERRED_SIZE,
                                                                                359,
                                                                                javax.swing.GroupLayout.PREFERRED_SIZE)
                                                                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE,
                                                                                Short.MAX_VALUE))
                                                .addGroup(jPanel1Layout.createSequentialGroup()
                                                                .addComponent(jPanel6,
                                                                                javax.swing.GroupLayout.PREFERRED_SIZE,
                                                                                javax.swing.GroupLayout.DEFAULT_SIZE,
                                                                                javax.swing.GroupLayout.PREFERRED_SIZE)
                                                                .addGap(0, 0, Short.MAX_VALUE)));

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

        private void jButton3ActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_jButton3ActionPerformed
                //Refuser la demande 
                //ask the user for motif de refus as a pop up 
                
                String motifDeRefus = JOptionPane.showInputDialog(this, "Please enter the reason for refusal:");
                //get the mail of the student from the selected row
                int viewRow = jTable2.getSelectedRow();
                String type= jTable2.getModel().getValueAt(viewRow, 4).toString();
                Integer id_d = Integer.parseInt(jTable2.getModel().getValueAt(viewRow, 0).toString());
                if(viewRow < 0){
                        System.out.println("No row selected");
                }else{
                        String email = jTable2.getModel().getValueAt(viewRow, 3).toString();
                        //send the mail to the student
                        SendMail.send_refus(email,  motifDeRefus , "Refus de demande");
                        
                }
                //update the table to set the traité to 1 and statuts to 0 
                try {
                        Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/gl", "root", "");
                        java.sql.Statement stmt = con.createStatement();
                        switch (type) {
                                case "Attestation de scolarité":
                                        stmt.executeUpdate("UPDATE demande_as SET traite = 1 , statuts = 0 WHERE id = '"
                                                        + id_d + "';");
                                        break;
                                case "Attestation de réussite":
                                        stmt.executeUpdate("UPDATE demande_ar SET traité = '1', statuts = '0' WHERE id = '"
                                                        + id_d + "';");
                                        break;
                                case "Relevé de notes":
                                        stmt.executeUpdate("UPDATE demande_rn SET traité = '1', statuts = '0' WHERE id = '"
                                                        + id_d + "';");
                                        break;
                                case "Attestation de stage":
                                        stmt.executeUpdate("UPDATE stage SET traité = '1', statuts = '0' WHERE id = '"
                                                        + id_d + "';");
                                        break;
                                default:
                                        JOptionPane.showMessageDialog(null, "Erreur doc type");
                        }

                        con.close();
                } catch (Exception e) {
                        System.out.println("Error: " + e.getMessage());
                }
                // actualiser the table
                actualiser();


        }// GEN-LAST:event_jButton3ActionPerformed

        private void jButton4ActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_jButton4ActionPerformed
                //Telecharger le document
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


        }// GEN-LAST:event_jButton4ActionPerformed

        private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_jButton1ActionPerformed
                // TODO add your handling code here:
                String recherche = jTextPane1.getText();
                TableRowSorter<TableModel> rowSorter = new TableRowSorter<>(jTable2.getModel());
                jTable2.setRowSorter(rowSorter);
                rowSorter.setRowFilter(RowFilter.regexFilter(recherche));
        }// GEN-LAST:event_jButton1ActionPerformed

        private void jButton6ActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_jButton6ActionPerformed
                // go the reclamations page
                this.setVisible(false);
                new ReclamAdmin().setVisible(true);
                //set position to default 
                this.setLocationRelativeTo(null);
                

        }// GEN-LAST:event_jButton6ActionPerformed

        private void jButton7ActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_jButton7ActionPerformed
                // go to the historique page
                this.setVisible(false);
                new Historique().setVisible(true);

        }// GEN-LAST:event_jButton7ActionPerformed

        private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_jButton2ActionPerformed
                //accepter la demande
                int viewRow = jTable2.getSelectedRow();
                if (viewRow < 0) {
                        System.out.println("No row selected");
                } else {
                        // get the type of the document
                        String type = jTable2.getModel().getValueAt(viewRow, 4).toString();
                        Integer id_column = Integer
                                        .parseInt(jTable2.getModel().getValueAt(viewRow, 0).toString());
                         String email = jTable2.getModel().getValueAt(viewRow, 3).toString();
                        Integer cne = Integer.parseInt(jTable2.getModel().getValueAt(viewRow, 2).toString());
                        
                        String file_path;

                        // switch case to know which fonction to call
                        switch (type) {
                                case "Attestation de scolarité":
                                        // call the function to accept the demand
                                        System.out.println("Attestation de scolarité");
                                        try {
                                                AS_gen(id_column);
                        file_path = "pdf/Attestation_de_scolarité " + cne.toString() + ".pdf";
     SendMail.send_email(email,file_path , "envoi d'Attestation de scolarité", "Attestation_de_scolarité");
                                       
                                        } catch (FileNotFoundException e) {
                                                // TODO Auto-generated catch block
                                                e.printStackTrace();
                                        }
                                        break;
                                case "Attestation de réussite":
                                        // call the function to accept the demand
                                        AR_gen(id_column);
                                        System.out.println("Attestation de réussite");
                                        file_path = "pdf/Attestation_de_réussite " + cne.toString() + ".pdf";
                                        SendMail.send_email(email,file_path , "envoi d'Attestation de réussite", "Attestation de réussite");
                                        break;
                                case "Relevé de notes":
                                        //call the function to accept the demand
                                       ReleveNotesGenerator(id_column);
                                        System.out.println("Relevé de notes crée");
               SendMail.send_email(email,"pdf/Relevé_de_notes_" + cne.toString() + ".pdf" , "envoi du relevé de notes", "Relevé de notes");

                                        
                                        
                                        break;
                                case "Attestation de stage":
                                        // call the function to accept the demand
                                        Astage_gen(id_column);
                                         file_path = "pdf/Attestation_de_stage" + cne.toString() + ".pdf";
                                SendMail.send_email(email,file_path , "envoi d'Attestation de stage", "Attestation de stage");
                                        break;
                                default:
                                        JOptionPane.showMessageDialog(null, "Erreur");
                        }
                }
                                    actualiser();

        }// GEN-LAST:event_jButton2ActionPerformed

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
                        for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager
                                        .getInstalledLookAndFeels()) {
                                if ("Nimbus".equals(info.getName())) {
                                        javax.swing.UIManager.setLookAndFeel(info.getClassName());
                                        break;
                                }
                        }
                } catch (ClassNotFoundException ex) {
                        java.util.logging.Logger.getLogger(Dashbrd.class.getName()).log(java.util.logging.Level.SEVERE,
                                        null, ex);
                } catch (InstantiationException ex) {
                        java.util.logging.Logger.getLogger(Dashbrd.class.getName()).log(java.util.logging.Level.SEVERE,
                                        null, ex);
                } catch (IllegalAccessException ex) {
                        java.util.logging.Logger.getLogger(Dashbrd.class.getName()).log(java.util.logging.Level.SEVERE,
                                        null, ex);
                } catch (javax.swing.UnsupportedLookAndFeelException ex) {
                        java.util.logging.Logger.getLogger(Dashbrd.class.getName()).log(java.util.logging.Level.SEVERE,
                                        null, ex);
                }
                // </editor-fold>

                /* Create and display the form */
                java.awt.EventQueue.invokeLater(new Runnable() {
                        public void run() {
                                new Dashbrd().setVisible(true);
                        }
                });
        }

        // Variables declaration - do not modify//GEN-BEGIN:variables
        private javax.swing.JButton jButton1;
        private javax.swing.JButton jButton2;
        private javax.swing.JButton jButton3;
        private javax.swing.JButton jButton4;
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
