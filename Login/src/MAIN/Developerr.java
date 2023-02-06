/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package MAIN;

import java.sql.*;
import java.util.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.*;

import javax.swing.JTable;

/**
 *
 * @author eslam
 */
public class Developerr extends Main {

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

    public void ReturnAssignedBugsTable(JTable TableName, String username) throws SQLException {
        //returns Assignedbugs for a specified developer 
        String z = "'" + username + "'";
        Connection con;
        try {
            con = DriverManager.getConnection("jdbc:sqlserver://localhost:1433;databaseName=ProjectDB;"
                    + "user=Eslam123;password=Eslam123;trustServerCertificate=true");
            Statement stmt = con.createStatement();
            ResultSet rs = stmt.executeQuery("select bug_title,bug_type,priority,bug_level,project,status"
                    + ",bug_date,developer_assigned from bugs where developer_assigned= " + z);
            String bug_title, bug_type, priority, bug_level, project, status, bug_date, developer_assigned;
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

                String[] tbdata = {bug_title, bug_type, priority, bug_level, project, status, bug_date, developer_assigned};

                jTab.addRow(tbdata);
            }
        } catch (Exception ex) {

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

    public String ReturnTesterNameFromProjectName(String projectname) throws SQLException {
        Connection con = DriverManager.getConnection("jdbc:sqlserver://localhost:1433;databaseName=ProjectDB;"
                + "user=Eslam123;password=Eslam123;trustServerCertificate=true");
        Statement smt = con.createStatement();
        ResultSet rs = smt.executeQuery("select reported_by from bugs where project = " + "'" + projectname + "'");
        String[] uname = new String[1];
        while (rs.next()) {
            uname[0] = rs.getString("reported_by");
        }
        return uname[0];

    }

    public void SetStatus(String project, String Status) {
        try {
            String p = "'" + project + "'";
            String S = "'" + Status + "'";
            Connection con = DriverManager.getConnection("jdbc:sqlserver://localhost:1433;databaseName=ProjectDB;"
                    + "user=Eslam123;password=Eslam123;trustServerCertificate=true");
            PreparedStatement ps = con.prepareStatement("update bugs set status= " + S + "where project=" + p);
            ps.executeUpdate();
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(null, "Couldn't change the Status of the project", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    public void SendEmailToTester(String from, String For) throws SQLException {

        String message = "Dear ," + For + "  your bug has been fixed";

        Connection con = DriverManager.getConnection("jdbc:sqlserver://localhost:1433;databaseName=ProjectDB;"
                + "user=Eslam123;password=Eslam123;trustServerCertificate=true");
        PreparedStatement ps = con.prepareStatement("INSERT INTO email VALUES(?,?,?)");
        ps.setString(1, For);
        ps.setString(2, message);
        ps.setString(3, from);
        ps.executeUpdate();

    }

    private static ArrayList<String> ReturnListOfProjects(String DeveloperName) throws Exception {
        String Developer = "'" + DeveloperName + "'";
        Connection con = DriverManager.getConnection("jdbc:sqlserver://localhost:1433;databaseName=ProjectDB;"
                + "user=Eslam123;password=Eslam123;trustServerCertificate=true");
        Statement stmt = con.createStatement();
        ResultSet rs = stmt.executeQuery(" select project from bugs where developer_assigned=" + Developer);
        ArrayList<String> Arr = new ArrayList<>();

        while (rs.next()) {

            Arr.add(rs.getString("project"));

        }

        return Arr;

    }

    public void ReturnPojectsComboBox(JComboBox s) throws Exception {
        //returns a copy of project names into a jCombobox
        ArrayList<String> Arr = new ArrayList<>();
        Arr = (ArrayList) ReturnListOfProjects(RetreiveUsername()).clone();
        String[] a = new String[ReturnListOfProjects(RetreiveUsername()).size()];
        ReturnListOfProjects(RetreiveUsername()).toArray(a);
        DefaultComboBoxModel aa = new DefaultComboBoxModel(a);
        s.setModel(aa);

    }

    public void ReturnTableOfProjects(JTable TableName, String DeveloperName) throws SQLException {
        String Developer = "'" + DeveloperName + "'";
        Connection con;
        try {
            con = DriverManager.getConnection("jdbc:sqlserver://localhost:1433;databaseName=ProjectDB;"
                    + "user=Eslam123;password=Eslam123;trustServerCertificate=true");
            Statement stmt = con.createStatement();
            ResultSet rs = stmt.executeQuery("select project,status from  bugs where developer_assigned= " + Developer);
            String project, status;
            DefaultTableModel jTab = (DefaultTableModel) TableName.getModel();
            //Truncates the jTable before updating it (to avoid data redundancy)
            jTab.setRowCount(0);
            while (rs.next()) {
                project = rs.getString("project");
                status = rs.getString("status");
                String[] tbdata = {project, status};

                jTab.addRow(tbdata);
            }
        } catch (Exception ex) {

        }
    }

}
