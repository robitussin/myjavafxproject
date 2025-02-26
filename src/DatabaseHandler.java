import java.sql.*;

public class DatabaseHandler {

    private static DatabaseHandler handler = null;
    private static Statement stmt = null;
    private static PreparedStatement pstatement = null;

    public static DatabaseHandler getInstance() {
        if (handler == null) {
            handler = new DatabaseHandler();
        }
        return handler;
    }

    public static Connection getDBConnection() {
        Connection connection = null;

        String dburl = "jdbc:mysql://localhost:3306/librarydb";
        String userName = "root";
        String password = "password";

        try {
            connection = DriverManager.getConnection(dburl, userName, password);

        } catch (Exception e) {
            e.printStackTrace();
        }

        return connection;
    }

    public ResultSet execQuery(String query) {

        ResultSet result;

        try {
            stmt = getDBConnection().createStatement();
            result = stmt.executeQuery(query);
        } catch (SQLException ex) {
            System.out.println("Exception at execQuery:dataHandler" + ex.getLocalizedMessage());
            return null;
        } finally {
        }

        return result;
    }

    public static boolean validateLogin(String username, String password) {

        getInstance();
        String query = "SELECT * FROM admin WHERE UserName = '" + username + "' AND Password = '" + password + "'";

        System.out.println(query);

        ResultSet result = handler.execQuery(query);
        try {
            if (result.next()) {
                return true;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return false;
    }

    public static boolean changePassword(String username, String password) {

        try {
            String query = "UPDATE admin SET Password = '" + password + "' WHERE Username = '" + username + "'";

            System.out.println(query);

            pstatement = getDBConnection().prepareStatement(query);
            int res = pstatement.executeUpdate();

            if (res > 0) {
                return true;
            }
        } catch (Exception e) {
            // TODO: handle exception
        }
        return false;
    }

    public static boolean addUser(User user) {

        try {
            pstatement = getDBConnection().prepareStatement("INSERT INTO `admin` (Username, Password, AccountStatus) VALUES (?,?,?)");
            pstatement.setString(1, user.getUsername());
            pstatement.setString(2, user.getPassword());
            pstatement.setString(3, user.getAccountstatus());
            return pstatement.executeUpdate() > 0;

        } catch (SQLException e) {
            e.printStackTrace();

        }

        return false;
    }

    public static ResultSet displayusers() {

        ResultSet result = null;

        try {
            String query = "SELECT * FROM admin";
            result = handler.execQuery(query);

        } catch (Exception e) {
            // TODO: handle exception
        }

        return result;
    }

    public static boolean deleteUser(User user) {
        try {
            String deleteStatement = "DELETE FROM `admin` WHERE Username = ?";
            pstatement = getDBConnection().prepareStatement(deleteStatement);
            pstatement.setString(1, user.getUsername());
            int res = pstatement.executeUpdate();
            if (res > 0) {
                return true;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public static boolean updateUser(User user) {
        try {
            String updateStatement = "UPDATE `admin` SET Username = ?, Password = ?, AccountStatus = ? WHERE Username = ?";
            pstatement = getDBConnection().prepareStatement(updateStatement);
            pstatement.setString(1, user.getUsername());
            pstatement.setString(2, user.getPassword());
            pstatement.setString(3, user.getAccountstatus());
            pstatement.setString(4, user.getUsername());
            int res = pstatement.executeUpdate();
            if (res > 0) {
                return true;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public static ResultSet displaytransactions() {

        ResultSet result = null;

        try {
            String query = "SELECT * FROM transactions";
            result = handler.execQuery(query);

        } catch (Exception e) {
            // TODO: handle exception
        }

        return result;
    }

    public static ResultSet countTransactions() {

        ResultSet result = null;

        try {
            String query = "SELECT COUNT(amount) as transactioncount FROM transactions";
            result = handler.execQuery(query);

        } catch (Exception e) {
            // TODO: handle exception
        }

        return result;
    }

    public static ResultSet getMinTransaction() {

        ResultSet result = null;

        try {
            String query = "SELECT MIN(amount) as minimum FROM transactions";
            result = handler.execQuery(query);

        } catch (Exception e) {
            // TODO: handle exception
        }

        return result;
    }

    public static ResultSet getMaxTransaction() {

        ResultSet result = null;

        try {
            String query = "SELECT MAX(amount) as maximum FROM transactions";
            result = handler.execQuery(query);

        } catch (Exception e) {
            // TODO: handle exception
        }

        return result;
    }

    public static ResultSet getSumTransaction() {

        ResultSet result = null;

        try {
            String query = "SELECT SUM(amount) as transactionsum FROM transactions";
            result = handler.execQuery(query);

        } catch (Exception e) {
            // TODO: handle exception
        }

        return result;
    }

    public static ResultSet getAverageTransaction() {

            ResultSet result = null;

            try {
                String query = "SELECT AVG(amount) as transactionaverage FROM transactions";
                result = handler.execQuery(query);

            } catch (Exception e) {
                // TODO: handle exception
            }

            return result;
        }
}
