import java.sql.*;

public class Main {
    public static void main(String[] args) throws ClassNotFoundException, SQLException {
        Connection connection = null;
            Class.forName("org.postgresql.Driver");
            connection = DriverManager
                    .getConnection("jdbc:postgresql://localhost:5432/base_russian",
                            "postgres", "11111111");
            System.out.println("Connecting to database...");

        Statement statement = connection.createStatement();

//        create table
//        String sql = "CREATE TABLE user1 " +
//                "(ID INT PRIMARY KEY     NOT NULL," +
//                " NAME           TEXT    NOT NULL, " +
//                " SALARY         REAL)";
//        statement.executeUpdate(sql);



//        insert string to table
        String sq2 = "INSERT INTO user1 (ID,NAME,SALARY) "
                + "VALUES (117, 'Paul', 20000.00 );";
        statement.executeUpdate(sq2);

        //update string in table
        String sq3 = "UPDATE user1 set SALARY = 25000.00 where ID=1;";
        statement.executeUpdate(sq3);

        //delete string in table
        String sq333 = "DELETE FROM user1 WHERE ID = 6;";
        statement.executeUpdate(sq333);

        //select and print all in table
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
}
