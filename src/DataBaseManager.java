import java.sql.*;
import java.util.Arrays;
import java.util.Random;

public class DataBaseManager {
    private Connection connection;
    public static void main(String[] args) throws SQLException {
        Connection connection = null;
        String baseName = "base_russian";
        String login = "postgres";
        String parole = "11111111";

        DataBaseManager manager = new DataBaseManager();
        manager.connect(baseName, login, parole);
        connection = manager.getConnection();


        //list of the tables & print
        String[] listOfAllTables = manager.listOfAllTables();
        System.out.println(Arrays.toString(listOfAllTables));

        String insert = "INSERT INTO user3 (ID,NAME,SALARY) "
                + "VALUES (77787, 'Paul', '20000' );";
        //insert(connection, insert);

        String update = "UPDATE user3 SET salary = ? WHERE id > 3";
        update(connection, update);

        String delete = "DELETE FROM user3 WHERE ID = 7;";
        delete(connection, delete);

        String tableName = "user3";
        String selectAll = "SELECT * FROM " + tableName + "";
        selectAndPrint(connection, selectAll);

        //amount of rows in table for our array with Data - have found.
        manager.getTableData(tableName);


        connection.close();
    }

    protected void getTableData(String tableName) throws SQLException {
        int anInt = getSize(tableName);

        DataSet [] result = new DataSet[anInt];
        Statement statement2 = connection.createStatement();
        ResultSet rs2 = statement2.executeQuery("SELECT * FROM " + tableName + "");
        ResultSetMetaData resultSetMetaData = rs2.getMetaData();

        int count = 0;
        while ( rs2.next() ) { // put to DataSet column name and insist of the table cell
            DataSet dataSet = new DataSet();
            result[count++] = dataSet;
            for (int i = 1; i < resultSetMetaData.getColumnCount(); i++) {
                dataSet.put(resultSetMetaData.getColumnName(i), rs2.getObject(i));
            }
        }

        rs2.close();
        rs2.close();
        statement2.close();
    }

    private int getSize(String tableName) throws SQLException {
        Statement statement2 = connection.createStatement();
        ResultSet rs2 = statement2.executeQuery("SELECT COUNT (*) FROM " + tableName + "");
        rs2.next();
        int anInt = rs2.getInt(1);
        rs2.close();
        return anInt;
    }

    protected static void selectAndPrint(Connection connection, String select) throws SQLException {
        Statement statement2 = connection.createStatement();
        ResultSet rs1 = statement2.executeQuery(select);
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

    protected void connect(String baseName, String login, String parole) {
        try {
            Class.forName("org.postgresql.Driver");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
            System.out.println("Please, add JDBC Driver to the project");
        }
        try {
            connection = DriverManager
                    .getConnection("jdbc:postgresql://localhost:5432/" + baseName + "",
                            login, parole);
        } catch (SQLException e) {
            System.out.println(String.format("Can't get connection for database:%s user:%s", baseName, login));//прикольно!!!!!!!!!!!1
            e.printStackTrace();
        }
        System.out.println("Connecting to database...");
    }

    public Connection getConnection() {
        return connection;
    }

    protected String[] listOfAllTables() {
        String[] listOfTables;
        listOfTables = new String[100];
        try {
            int countOfTables = 0;
            String selectAndPrint1 = "SELECT table_name FROM information_schema.tables WHERE table_schema='public' AND table_type = 'BASE TABLE'";
            Statement statement3 = connection.createStatement();
            ResultSet rs1 = statement3.executeQuery(selectAndPrint1);
            while ( rs1.next() ) {
                listOfTables[countOfTables++] = rs1.getString("table_name");
            }
            listOfTables = Arrays.copyOf(listOfTables, countOfTables, String[].class);//deleting zero Elements ??????????????????????????
            rs1.close();
            statement3.close();
        } catch (SQLException e) {
            System.out.println("Exception from listOfAllTables");
            e.printStackTrace();
            return new String[0];
        }
        return listOfTables;
    }

    protected static void update(Connection connection, String update) throws SQLException {
        PreparedStatement preparedStatement = connection.prepareStatement(update);
        preparedStatement.setString(1, "" + 25000 + new Random().nextInt(10000));
        preparedStatement.executeUpdate();
        preparedStatement.close();
    }

    protected static void delete(Connection connection, String delete) throws SQLException {
        Statement statement = connection.createStatement();
        statement.executeUpdate(delete);
        statement.close();
    }

    protected static void insert(Connection connection, String insert) throws SQLException {
        Statement statement1 = connection.createStatement();
        statement1.executeUpdate(insert);
        statement1.close();
    }
}

