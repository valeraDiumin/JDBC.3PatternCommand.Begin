import java.sql.*;
import java.util.Random;

public class Main {
    public static void main(String[] args) throws ClassNotFoundException, SQLException {
        Connection connection = null;
        Class.forName("org.postgresql.Driver");
        connection = DriverManager
                .getConnection("jdbc:postgresql://localhost:5432/test",
                        "postgres", "11111111");
        System.out.println("Connecting to database...");

        Statement statement = connection.createStatement();

        String insert = "INSERT INTO user3 (ID,NAME,SALARY) "
                + "VALUES (77786, 'Paul', '20000' );";
       // insert(connection, insert);

        String update = "UPDATE user3 SET salary = ? WHERE id > 3";
        update(connection, update);

        String delete = "DELETE FROM user3 WHERE ID = 7;";
        statement.executeUpdate(delete);

        String selectAndPrint = "SELECT * FROM user3";
        //selectAndPrint(connection, selectAndPrint);

        //list of the tables
        DatabaseMetaData dbmd = connection.getMetaData();
        try (ResultSet tables = dbmd.getTables(null, null, "%", new String[] { "TABLE" })) {
            while (tables.next()) {
                System.out.println(tables.getString("TABLE_NAME"));
            }
        }
        //second approach as Baglay in lection
        Statement statement2 = connection.createStatement();
        String selectAndPrint1 = "SELECT table_name FROM information_schema.tables WHERE table_schema='public' AND table_type = 'BASE TABLE'";
        ResultSet rs1 = statement2.executeQuery(selectAndPrint1);
        while ( rs1.next() ) {
            String id = rs1.getString("table_name");
            System.out.print( " table_name = " + id );
            System.out.println();
        }

        connection.close();
    }

    protected static void update(Connection connection, String update) throws SQLException {
        PreparedStatement preparedStatement = connection.prepareStatement(update);
        preparedStatement.setString(1, "" + 25000 + new Random().nextInt(10000));
        preparedStatement.executeUpdate();
        preparedStatement.close();
    }

    protected static void selectAndPrint(Connection connection, String qs11) throws SQLException {
        Statement statement2 = connection.createStatement();
        ResultSet rs1 = statement2.executeQuery(qs11);
        while ( rs1.next() ) {
            int id = rs1.getInt("id");
            String  name = rs1.getString("name");
            float salary = rs1.getFloat("salary");
            System.out.print( " ID = " + id );
            System.out.print( " NAME = " + name );
            System.out.print( " SALARY = " + salary );
            System.out.println();
        }
        rs1.close();
        statement2.close();
    }

    protected static void insert(Connection connection, String insert) throws SQLException {
        Statement statement1 = connection.createStatement();
        statement1.executeUpdate(insert);
        statement1.close();
    }
}
