/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Other/File.java to edit this template
 */
package MAIN;

import java.sql.*;
import java.util.ArrayList;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author eslam
 */
public class Admin extends Main {

    public Admin() {

    }

    @Override
    public void ReturnBugTable(JTable TableName) {

        try {
            Connection con = DriverManager.getConnection("jdbc:sqlserver://localhost:1433;databaseName=ProjectDB;user=Eslam123;password=Eslam123;trustServerCertificate=true");
            Statement stmt = con.createStatement();
            ResultSet rs = stmt.executeQuery("select bug_title,bug_type,priority,bug_level,project,status,bug_date,developer_assigned,reported_by from bugs ");
            String bug_title, bug_type, priority, bug_level, project, status, bug_date, developer_assigned, reported_by;
            DefaultTableModel jTab = (DefaultTableModel) TableName.getModel();
            //Truncates the jTable before Updating it
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

    private static ArrayList<String> ReturnListOfUsers() throws Exception {

        Connection con = DriverManager.getConnection("jdbc:sqlserver://localhost:1433;databaseName=ProjectDB;"
                + "user=Eslam123;password=Eslam123;trustServerCertificate=true");
        Statement stmt = con.createStatement();
        ResultSet rs = stmt.executeQuery(" select username from users");
        ArrayList<String> Arr = new ArrayList<>();

        while (rs.next()) {

            Arr.add(rs.getString("username"));

        }

        return Arr;

    }

    public void ReturnUsersComboBox(JComboBox s) throws Exception {
        //function to return the a copy of the userslist into a jComboBox
        ArrayList<String> Arr = new ArrayList<>();
        Arr = (ArrayList) ReturnListOfUsers().clone();
        String[] a = new String[Arr.size()];
        Arr.toArray(a);
        DefaultComboBoxModel aa = new DefaultComboBoxModel(a);
        s.setModel(aa);

    }

}
