
package MAIN;

import java.sql.*;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author eslam
 */
public abstract class Main {

    public Main() {

    }

    public static void InsertUsername(String username) throws SQLException {
        // Function to insert the Username into the DB
        Connection con = DriverManager.getConnection("jdbc:sqlserver://localhost:1433;databaseName=ProjectDB;"
                + "user=Eslam123;password=Eslam123;trustServerCertificate=true");
        PreparedStatement ps = con.prepareStatement("truncate table username");

        ps.executeUpdate();

        PreparedStatement pss = con.prepareStatement("insert into username values ('" + username + "')");

        pss.executeUpdate();
    }

    public static String RetreiveUsername() throws SQLException {
        // Function to Retreive the Username you logged in with  
        Connection con = DriverManager.getConnection("jdbc:sqlserver://localhost:1433;databaseName=ProjectDB;"
                + "user=Eslam123;password=Eslam123;trustServerCertificate=true");
        Statement stmt = con.createStatement();

        ResultSet rs = stmt.executeQuery("select * from username");
        String[] username = new String[1];
        while (rs.next()) {
            username[0] = rs.getString("username");

        }
        return username[0];
    }

    abstract void ReturnBugTable(JTable TableName);

    public void AddUsers(String role, String username, String password,
            String firstName, String lastName, String Email, String birthDate) {
        // Function to AddUsers Into The Database
        try {
            Connection con = DriverManager.getConnection("jdbc:sqlserver://localhost:1433;databaseName=ProjectDB;user=Eslam123;password=Eslam123;trustServerCertificate=true;");

            if ("".equals(role) || "".equals(username) || "".equals(password) || "".equals(firstName) || "".equals(lastName) || "".equals(Email) || "".equals(birthDate)) {
                JOptionPane.showMessageDialog(null, "Enter All Of Columns", "Error", JOptionPane.ERROR_MESSAGE);
            } else {
                PreparedStatement ps = con.prepareStatement("insert into  users (role,username,password,firstName,lastName,Email,birthDate)  values (?,?,?,?,?,?,?) ");
                ps.setString(1, role);
                ps.setString(2, username);
                ps.setString(3, password);
                ps.setString(4, firstName);
                ps.setString(5, lastName);
                ps.setString(6, Email);
                ps.setString(7, birthDate);
                ps.executeUpdate();
            }

        } catch (Exception ex) {

        }
    }

    public void showUsersTable(JTable jTable1) {
        try {
            Connection con = DriverManager.getConnection("jdbc:sqlserver://localhost:1433;databaseName=ProjectDB;user=Eslam123;password=Eslam123;trustServerCertificate=true;");
            ResultSet rs = con.createStatement().executeQuery("select * from users");
            DefaultTableModel tbModel = (DefaultTableModel) jTable1.getModel();
            tbModel.setRowCount(0);
            while (rs.next()) {
                String Type = rs.getString("role");
                String Username = rs.getString("username");
                String Password = rs.getString("password");
                String First_name = rs.getString("firstName");
                String last_name = rs.getString("lastName");
                String Email = rs.getString("Email");
                String Date_of_birth = rs.getString("birthDate");

                String data[] = {Type, Username, Password, First_name, last_name, Email, Date_of_birth};

                tbModel.addRow(data);
            }

        } catch (SQLException ex) {

        }
    }

    public void removeRow(String c1) {
        //Function to delete a user from the system
        try {
            Connection con = DriverManager.getConnection("jdbc:sqlserver://localhost:1433;databaseName=ProjectDB;user=Eslam123;password=Eslam123;trustServerCertificate=true;");
            PreparedStatement ps = con.prepareStatement("DELETE FROM users WHERE username = '" + c1 + "'");
            ps.executeUpdate();

        } catch (SQLException ex) {

        }
    }

    public void updateUsersTable(String username, String column, String update) {
        try {
            Connection con = DriverManager.getConnection("jdbc:sqlserver://localhost:1433;databaseName=ProjectDB;user=Eslam123;password=Eslam123;trustServerCertificate=true;");
            PreparedStatement ps = con.prepareStatement("update users set " + column + " = '" + update + "' where username='" + username + "'");

            ps.executeUpdate();

        } catch (SQLException ex) {

        }
    }
}
