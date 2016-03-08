import java.sql.*;
import java.util.Arrays;

public class JDBCDataBaseManager implements DataBaseManager {
    private static String tableName;
    public Connection connection;

    @Override
    public void updateFromDataSet(String tableName1, DataSet updateData1, int id) {
        try {
            String format = "%s = ?,";
            String tableNames = getStringFormatted(updateData1, format);

            String update = "UPDATE " + tableName1 + " SET " + tableNames + " WHERE id = ?";
            PreparedStatement preparedStatement = connection.prepareStatement(update);

            int index = 1;

            for (Object value : updateData1.getValues()) {
                preparedStatement.setObject(index, value);
                index++;
            }
            preparedStatement.setObject(index, id);

            preparedStatement.executeUpdate();

            System.out.println("updating have done successfully");
            preparedStatement.close();
        } catch (SQLException e){
            e.printStackTrace();
        }
    }

    private String getStringFormatted(DataSet updateData1, String format) {
        String tableNames = "";
        for(String tableNameFromDataSet : updateData1.getcolumnNames()) {
            tableNames += String.format(format, tableNameFromDataSet);
        }
        tableNames = tableNames.substring(0, tableNames.length() - 1);
        return tableNames;
    }

    @Override
    public void clear(String tableName){
        try {
            Statement statement = connection.createStatement();
            String clear = "DELETE FROM " + tableName + " WHERE id < 100;";
            statement.executeUpdate(clear);
            System.out.println("clearing have done");
            statement.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void create(DataSet input, String tableName1){ // TODO !!!!!!!!!!!!!!!!!!!!!!!!!!
        try {
            Statement statement = connection.createStatement();

            // create string of column names
            String format = "%s,";
            String StringTableNames = getStringFormatted(input, format);

            // create string of column values
            String formatValue = "'%s',";
            String StringTableValue = getStringValue(input, formatValue);

            statement.executeUpdate("INSERT INTO " + tableName1  + " (" + StringTableNames + " )"
                    + " VALUES (" + StringTableValue + ")");
            statement.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private String getStringValue(DataSet input, String formatValue) {
        String StringTableValue = "";
        for (Object value : input.getValues()) {
            StringTableValue += String.format(formatValue, value);
        }
        StringTableValue = StringTableValue.substring(0, StringTableValue.length() - 1);// deleting excess comma
        return StringTableValue;
    }

    @Override
    public void createNewTable(String tableName) {
        Statement stmt;
        try {
            stmt = connection.createStatement();
            String sql = "CREATE TABLE " + tableName +
                    " (ID INT PRIMARY KEY     NOT NULL," +
                    " NAME           TEXT    NOT NULL, " +
                    " SALARY         TEXT )";
            System.out.println("Table " + tableName + " created successfully");
            stmt.executeUpdate(sql);
            stmt.close();
        } catch ( Exception e ) {
            System.err.println( e.getClass().getName()+": "+ e.getMessage() );
            System.exit(0);
        }

    }

    @Override
    public DataSet[] getTableData(String tableName) {
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

    @Override
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

    @Override
    public void selectAndPrint() {

        try {
            Statement statement2 = connection.createStatement();
            String select = "SELECT * FROM " + tableName + "";

            ResultSet rs1 = statement2.executeQuery(select);

            while (rs1.next()) {
                int id = rs1.getInt("id");
                String name = rs1.getString("name");
                int salary = rs1.getInt("salary");
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

    @Override
    public void connect(String baseName, String login, String parole) {
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

    @Override
    public String[] getTableNames() {
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
            System.out.println("Exception from getTableNames");
            e.printStackTrace();
            return new String[0];
        }
        return listOfTables;
    }

    public static void delete(Connection connection, String delete) {
        try {
            Statement statement = connection.createStatement();
            statement.executeUpdate(delete);
            System.out.println("deleting from table" + tableName + " have done successfully");
            statement.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void insert(Connection connection, String insert, String tableName) {
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
