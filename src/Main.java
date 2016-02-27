import java.sql.*;

public class Main {
    public static void main(String[] args) throws ClassNotFoundException, SQLException {
        String baseName = "base_russian";
        String login = "postgres";
        String parol = "11111111";
        Connection connection = getConnection(baseName, login, parol);
        Statement statement = null;

//        create table
//        String sql = "CREATE TABLE user1 " +
//                "(ID INT PRIMARY KEY     NOT NULL," +
//                " NAME           TEXT    NOT NULL, " +
//                " SALARY         REAL)";
//        statement.executeUpdate(sql);



//        insert string to table
//        String sq2 = "INSERT INTO user1 (ID,NAME,SALARY) "
//                + "VALUES (117, 'Paul', 20000.00 );";
//        statement.executeUpdate(sq2);

        String sq3 = "UPDATE user1 set SALARY = 25000.00 where ID=1;";
        //update string in table
        updateStringInTable(connection, sq3);

        int StringDeleting = 1;

        //delete string in table
        deleteStringInTable(connection, StringDeleting);

        //select and print all in table
        statement = connection.createStatement();
        String qs11 = "SELECT * FROM user1";
        ResultSet rs1 = statement.executeQuery(qs11);
        //print query
        while ( rs1.next() ) {
            int id = rs1.getInt("id");
            String  name = rs1.getString("name");
            float salary = rs1.getFloat("salary");
            System.out.print( " ID = " + id );
            System.out.print( " NAME = " + name );
            System.out.print( " SALARY = " + salary );
            System.out.println();
        }
        statement.close();
        connection.close();
    }

    protected static void deleteStringInTable(Connection connection, int stringDeleting) throws SQLException {
        String sq333 = "DELETE FROM user1 WHERE ID = " + stringDeleting + ";";
        connection.createStatement().executeUpdate(sq333);
    }

    private static void updateStringInTable(Connection connection, String sq3) throws SQLException {
        Statement statement1 = connection.createStatement();
        statement1.executeUpdate(sq3);
    }

    private static Connection getConnection(String baseName, String login, String parol) throws ClassNotFoundException, SQLException {
        Class.forName("org.postgresql.Driver");
        Connection connection = null;
        connection = DriverManager
                    .getConnection("jdbc:postgresql://localhost:5432/" + baseName + "",
                            login, parol);
        System.out.println("Connecting to database...");
        return connection;
    }
}
