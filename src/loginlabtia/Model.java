/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package loginlabtia;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Map;

/**
 *
 * @author LabTIA-40
 */
public class Model {

    Connection connection;

    public String validasi;

    public String getValidasi() {
        return validasi;
    }

    public void setValidasi(String validasi) {
        this.validasi = validasi;
    }

    public boolean isConnected() throws SQLException {
        Connect();
        if (connection == null) {
            return false;
        }
        Disconnect();
        return true;
    }

    public void Connect() {
        connection = Connector();
    }

    public void Disconnect() throws SQLException {
        connection.close();
    }

    public static Connection Connector() {
        try {
            String server = "//10.5.10.5:3306";
            String db_name = "db_login_tia";
            Class.forName("com.mysql.jdbc.Driver").newInstance();
            String dbURL = "jdbc:mysql:" + server + "/" + db_name;
            Connection conn = DriverManager.getConnection(dbURL, "doelmi", "doelmi");
            return conn;
        } catch (ClassNotFoundException | IllegalAccessException | InstantiationException | SQLException ex) {
            System.out.println(ex);
            return null;
        }
    }

    private boolean insertLogData(String username, String no_pc) throws SQLException {
        Connect();
        String query = "INSERT INTO log(username, no_pc) VALUES (?, ?)";
        PreparedStatement preparedStatement = null;
        try {
            preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, username);
            preparedStatement.setString(2, no_pc);
            preparedStatement.executeUpdate();
            return true;
        } catch (SQLException e) {

        } finally {
            preparedStatement.close();
        }
        Disconnect();
        return false;
    }

    public boolean register(String username, String password, String nama, String nim) throws SQLException {
        Connect();
        String query = "INSERT INTO `user`(`username`, `password`, `Nama`, `NIM`, `Aktif`) VALUES (?, ?, ?, ?, 0)";
        PreparedStatement preparedStatement = null;
        try {
            preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, username);
            preparedStatement.setString(2, password);
            preparedStatement.setString(3, nama);
            preparedStatement.setString(4, nim);
            preparedStatement.executeUpdate();
            return true;
        } catch (SQLException e) {

        } finally {
            preparedStatement.close();
        }
        Disconnect();
        return false;
    }

    public boolean login(String username, String password, String no_pc) throws SQLException {
        Connect();
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;

        String query = "SELECT `username`, `password`, `Aktif` FROM `user` WHERE `username`= ? AND `password` = ?";
        try {
            preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, username);
            preparedStatement.setString(2, password);
            resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                insertLogData(username, no_pc);
                setValidasi(resultSet.getString("Aktif"));
                return true;
            }
        } catch (SQLException e) {

        } finally {
            preparedStatement.close();
            resultSet.close();
        }
        Disconnect();
        return false;
    }

    public String getPCName() {
        String hostname = "Unknown";

        try {
            InetAddress addr;
            addr = InetAddress.getLocalHost();
            hostname = addr.getHostName();
        } catch (UnknownHostException ex) {
            System.out.println("Hostname can not be resolved");
        }

        return hostname;
    }
}
