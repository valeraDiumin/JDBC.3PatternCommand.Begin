import java.sql.*;
import java.util.Arrays;
import java.util.Random;

public class DataBaseManager {
    private static String tableName;
    public DataBaseManager manager;
    public Connection connection;


    public static void main(String[] args) throws SQLException {
        Connection connection = null;
        String baseName = "postgres";
        String login = "postgres";
        String parole = "11111111";

        DataBaseManager manager = new DataBaseManager();
        manager.connect(baseName, login, parole);
        connection = manager.getConnection();

        tableName = "user2";
//         manager.createNewTable(tableName);

        //list of the tables & print
        String[] listOfAllTables = manager.listOfAllTables();
        System.out.println(Arrays.toString(listOfAllTables));

//        String insert = "INSERT INTO " + tableName + " (id, name, salary) "
//                + "VALUES (3, 'Paul', '20000000' );";
//        insert(connection, insert, tableName);
//

//        String update = "UPDATE user3 SET salary = ? WHERE id > 3";
        //  update(connection, update);

        String delete = "DELETE FROM " + tableName + " WHERE ID = 3;";
        delete(connection, delete);

        manager.clearTable(tableName);

        DataSet data = new DataSet();
        data.put("ID", 2);
        data.put("NAME", "Jack Bobo");
        data.put("SALARY", 1000000);
        manager.create(data);

        String selectAll = "SELECT * FROM " + tableName + "";
        manager.selectAndPrint(selectAll);
//
        //amount of rows in table for our array with Data - have found.
//        DataSet[] result = manager.getTableData(tableName);
      //  System.out.println(Arrays.toString(result));
        System.out.println(manager.getSize(tableName));

        connection.close();
    }
    public void clearTable(String tableName){
        try {
            Statement statement = connection.createStatement();
            String clear = "DELETE FROM " + tableName + " ;";
            statement.executeUpdate(clear);
            System.out.println("clearing have done");
            statement.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    public void create(DataSet input){ // TODO !!!!!!!!!!!!!!!!!!!!!!!!!!
        try {
            Statement statement = connection.createStatement();

            // create string of column names
            String StringTableNames = "";
            for (String name : input.getcolumnNames()) {
                StringTableNames += name + ",";
            }
            StringTableNames = StringTableNames.substring(0, StringTableNames.length() - 1);
            System.out.println("tableNames " + StringTableNames);

            // create string of column values
            String StringTableValue = "";

            for (Object value : input.getValues()) {
                StringTableValue += "'" + value + "'" + ",";
            }
            StringTableValue = StringTableValue.substring(0, StringTableValue.length() - 1);// deleting excess comma
            System.out.println("tableValue " + StringTableValue);

            statement.executeUpdate("INSERT INTO " + tableName + " (" + StringTableNames + " )"
                    + " VALUES (" + StringTableValue + ")");
            statement.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    private void createNewTable(String tableName) {
        Statement stmt;
        try {
            stmt = connection.createStatement();
            String sql = "CREATE TABLE " + tableName +
                    " (ID INT PRIMARY KEY     NOT NULL," +
                    " NAME           TEXT    NOT NULL, " +
                    " SALARY         REAL)";
            System.out.println("Table " + tableName + " created successfully");
            stmt.executeUpdate(sql);
            stmt.close();
        } catch ( Exception e ) {
            System.err.println( e.getClass().getName()+": "+ e.getMessage() );
            System.exit(0);
        }

    }

    protected DataSet[] getTableData(String tableName) {
        try {

            int anInt = getSize(tableName);

            DataSet [] result = new DataSet[anInt];
            Statement statement2 = connection.createStatement();
            ResultSet rs2 = statement2.executeQuery("SELECT * FROM " + tableName + "");
            ResultSetMetaData resultSetMetaData = rs2.getMetaData();

            int count = 0;
            while ( rs2.next() ) { // put to DataSet column name and insist of the table cell
                DataSet dataSet = new DataSet();
                result[count++] = dataSet;
                for (int i = 1; i <= resultSetMetaData.getColumnCount(); i++) {// break insist of the string to columns
                    dataSet.put(resultSetMetaData.getColumnName(i), rs2.getObject(i));
                }
            }

            rs2.close();
            rs2.close();
            statement2.close();
            return result;
        } catch (SQLException e) {
            e.printStackTrace();
            return new DataSet[0];
        }
    }

    public int getSize(String tableName) {

        Statement statement2 = null;
        int anInt = 0;
        try {
            statement2 = connection.createStatement();
            ResultSet rs2 = statement2.executeQuery("SELECT COUNT (*) FROM " + tableName + "");
            rs2.next();
            anInt = rs2.getInt(1);
            rs2.close();
        } catch (SQLException e) {
            System.out.println(" Exception in method getSize ");
            e.printStackTrace();
        }
        return anInt;
    }

    protected void selectAndPrint(String select) throws SQLException {

        try {
            Statement statement2 = connection.createStatement();
            ResultSet rs1 = statement2.executeQuery(select);

            while (rs1.next()) {
                int id = rs1.getInt("id");
                String name = rs1.getString("name");
                float salary = rs1.getFloat("salary");
                System.out.print(" ID = " + id);
                System.out.print(" NAME = " + name);
                System.out.print(" SALARY = " + salary);
                System.out.println();
            }
            rs1.close();
            statement2.close();
        } catch (Exception e) {
            System.out.println("Exception in method selectAndPrint ");
            e.printStackTrace();
        }
    }

    protected void connect(String baseName, String login, String parole) {
        connection = null;
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
            connection = null;
        }
        System.out.println("Connecting to database...");
    }

    public Connection getConnection() {
        return connection;
    }

    public String[] listOfAllTables() {
//        Connection connection1 = manager.getConnection();
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
            listOfTables = Arrays.copyOf(listOfTables, countOfTables, String[].class);//deleting zero Elements ??????????????????????????String[].class????
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
        try {
            Statement statement = connection.createStatement();
            statement.executeUpdate(delete);
            statement.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    protected static void insert(Connection connection, String insert, String tableName) throws SQLException {
        try {
            Statement statement1 = connection.createStatement();
            statement1.executeUpdate(insert);
            System.out.println("insertion to table" + tableName + " have done successfully");
            statement1.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}

