/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package MAIN;

import java.awt.Image;

import java.io.*;
import java.sql.*;
import java.util.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.*;

import javax.swing.JTable;

/**
 *
 * @author eslam
 */
public class Testerr extends Main {

    public void insertBug(String bugName, String bugType, String bugPriority, String bugLevel, String projectName,
            String status, String bugdate, String developer_assigned, String reported_by) throws Exception {

        Connection con = DriverManager.getConnection("jdbc:sqlserver://localhost:1433;databaseName=ProjectDB;"
                + "user=Eslam123;password=Eslam123;trustServerCertificate=true");
        try {

            if ("".equals(bugName) || "".equals(bugType) || "".equals(bugPriority)
                    || "".equals(bugLevel) || "".equals(projectName) || "".equals(status)
                    || "".equals(bugdate) || "".equals(developer_assigned) || "".equals(reported_by)) {
                JOptionPane.showMessageDialog(null, "Enter All Of Columns", "Error", JOptionPane.ERROR_MESSAGE);
            }

            PreparedStatement ps = con.prepareStatement("insert into  bugs (bug_title,bug_type,priority,bug_level,project"
                    + ",status,bug_date,developer_assigned,reported_by) \n" + "values (?,?,?,?,?,?,?,?,?) ");
            ps.setString(1, bugName);
            ps.setString(2, bugType);
            ps.setString(3, bugPriority);
            ps.setString(4, bugLevel);
            ps.setString(5, projectName);
            ps.setString(6, status);
            ps.setString(7, bugdate);
            ps.setString(8, developer_assigned);
            ps.setString(9, reported_by);
            ps.executeUpdate();

        } catch (Exception ex) {

        }

    }

    public void SendEmailToDeveloper(String from, String For) throws SQLException {

        String message = "Dear, " + For + " please fix this bug ";

        Connection con = DriverManager.getConnection("jdbc:sqlserver://localhost:1433;databaseName=ProjectDB;"
                + "user=Eslam123;password=Eslam123;trustServerCertificate=true");
        PreparedStatement ps = con.prepareStatement("INSERT INTO email VALUES(?,?,?)");
        ps.setString(1, For);
        ps.setString(2, message);
        ps.setString(3, from);
        ps.executeUpdate();

    }

    private static ArrayList<String> ReturnListOfDevelopers() throws Exception {

        Connection con = DriverManager.getConnection("jdbc:sqlserver://localhost:1433;databaseName=ProjectDB;"
                + "user=Eslam123;password=Eslam123;trustServerCertificate=true");
        Statement stmt = con.createStatement();
        ResultSet rs = stmt.executeQuery(" select username from users where role='Developer'");
        ArrayList<String> Arr = new ArrayList<>();

        while (rs.next()) {

            Arr.add(rs.getString("username"));

        }

        return Arr;
    }

    public void ReturnDevelopersComboBox(JComboBox s) throws Exception {

        ArrayList<String> Arr = new ArrayList<>();
        Arr = (ArrayList) ReturnListOfDevelopers().clone();
        String[] a = new String[Arr.size()];
        Arr.toArray(a);
        DefaultComboBoxModel xx = new DefaultComboBoxModel(a);
        s.setModel(xx);

    }

    public void InsertScreenShot(JLabel lbl_img, String filename, byte[] person_image, String ProjectName) {

        try {
            JFileChooser chooser = new JFileChooser();
            chooser.showOpenDialog(null);
            File f = chooser.getSelectedFile();
            filename = f.getAbsolutePath();
            ImageIcon imageIcon = new ImageIcon(new ImageIcon(filename).getImage().getScaledInstance(lbl_img.getWidth(), lbl_img.getHeight(), Image.SCALE_SMOOTH));
            lbl_img.setIcon(imageIcon);
            File image = new File(filename);
            FileInputStream fis = new FileInputStream(image);
            ByteArrayOutputStream bos = new ByteArrayOutputStream();
            byte[] buf = new byte[1024];
            for (int readNum; (readNum = fis.read(buf)) != -1;) {
                bos.write(buf, 0, readNum);
            }
            person_image = bos.toByteArray();
            Connection con = DriverManager.getConnection("jdbc:sqlserver://localhost:1433;databaseName=ProjectDB;"
                    + "user=Eslam123;password=Eslam123;trustServerCertificate=true");
            PreparedStatement ps = con.prepareStatement("insert into ScreenShots values (?,?) ");
            ps.setString(1, ProjectName);
            ps.setBytes(2, person_image);
            ps.executeUpdate();

        } catch (Exception ex) {
            JOptionPane.showMessageDialog(null, "Insert A Valid Image", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    public void ReturnEmailTable(JTable Table, String username) {
        String z = "'" + username + "'";
        Connection con;
        try {
            con = DriverManager.getConnection("jdbc:sqlserver://localhost:1433;databaseName=ProjectDB;"
                    + "user=Eslam123;password=Eslam123;trustServerCertificate=true");
            Statement stmt = con.createStatement();
            ResultSet rs = stmt.executeQuery("select message,[from] from email where [for] = " + z);
            String message, from;
            DefaultTableModel jTab = (DefaultTableModel) Table.getModel();
            //Truncates the jTable before updating it (to avoid data redundancy)
            jTab.setRowCount(0);
            while (rs.next()) {
                message = rs.getString("message");
                from = rs.getString("from");
                String[] tbdata = {message, from};
                jTab.addRow(tbdata);
            }
        } catch (Exception ex) {

        }
    }

    @Override
    public void ReturnBugTable(JTable TableName) {

        try {
            Connection con = DriverManager.getConnection("jdbc:sqlserver://localhost:1433;databaseName=ProjectDB;user=Eslam123;password=Eslam123;trustServerCertificate=true");
            Statement stmt = con.createStatement();
            ResultSet rs = stmt.executeQuery("select bug_title,bug_type,priority,bug_level,project,status,bug_date,developer_assigned,reported_by from bugs ");
            String bug_title, bug_type, priority, bug_level, project, status, bug_date, developer_assigned, reported_by;
            DefaultTableModel jTab = (DefaultTableModel) TableName.getModel();
            //Truncates the jTable before updating it (to avoid data redundancy)
            jTab.setRowCount(0);
            while (rs.next()) {
                bug_title = rs.getString("bug_title");
                bug_type = rs.getString("bug_type");
                priority = rs.getString("priority");
                bug_level = rs.getString("bug_level");
                project = rs.getString("project");
                status = rs.getString("status");
                bug_date = rs.getString("bug_date");
                developer_assigned = rs.getString("developer_assigned");
                reported_by = rs.getString("reported_by");
                String[] tbdata = {bug_title, bug_type, priority, bug_level, project, status, bug_date, developer_assigned, reported_by};

                jTab.addRow(tbdata);

            }

        } catch (SQLException ex) {

        }

    }

}
