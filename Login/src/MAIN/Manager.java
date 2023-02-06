/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package MAIN;

import java.sql.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.*;

/**
 *
 * @author eslam
 */
public class Manager extends Main {

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

    public void DevelopersPerformance(JTable TableName) throws SQLException {
        Connection con;
        try {
            con = DriverManager.getConnection("jdbc:sqlserver://localhost:1433;databaseName=ProjectDB;"
                    + "user=Eslam123;password=Eslam123;trustServerCertificate=true");
            Statement stmt = con.createStatement();
            ResultSet rs = stmt.executeQuery("select count(project) as Projects_Assigned,status as Project_Name,developer_assigned as Developer_Name\n"
                    + "from users \n"
                    + "inner join bugs\n"
                    + "on users.username=bugs.developer_assigned\n"
                    + "group by developer_assigned,status");
            String Projects_Assigned, Project_Name, Developer_Name;
            DefaultTableModel jTab = (DefaultTableModel) TableName.getModel();
            //Truncates the jTable before updating it (to avoid data redundancy)
            jTab.setRowCount(0);
            while (rs.next()) {
                Projects_Assigned = rs.getString("Projects_Assigned");
                Project_Name = rs.getString("Project_Name");
                Developer_Name = rs.getString("Developer_Name");

                String[] tbdata = {Projects_Assigned, Project_Name, Developer_Name};

                jTab.addRow(tbdata);
            }
        } catch (Exception ex) {

        }

    }

    public void TesterPerformance(JTable TableName) throws SQLException {
        Connection con;
        try {
            con = DriverManager.getConnection("jdbc:sqlserver://localhost:1433;databaseName=ProjectDB;"
                    + "user=Eslam123;password=Eslam123;trustServerCertificate=true");
            Statement stmt = con.createStatement();
            ResultSet rs = stmt.executeQuery("select count(project) as Projects_Submitted,status as Status,reported_by as Tester_Name\n"
                    + "from users \n"
                    + "inner join bugs\n"
                    + "on users.username=bugs.developer_assigned\n"
                    + "group by status,reported_by");
            String Projects_Submitted, Status, Tester_Name;
            DefaultTableModel jTab = (DefaultTableModel) TableName.getModel();
            //Truncates the jTable before updating it (to avoid data redundancy)
            jTab.setRowCount(0);
            while (rs.next()) {
                Projects_Submitted = rs.getString("Projects_Submitted");
                Status = rs.getString("Status");
                Tester_Name = rs.getString("Tester_Name");

                String[] tbdata = {Projects_Submitted, Status, Tester_Name};

                jTab.addRow(tbdata);
            }
        } catch (Exception ex) {

        }

    }

}
