package connectAndCommands;

import java.sql.*;
import java.util.Arrays;

public class JDBCDataBaseManager implements DataBaseManager {
    private static String tableName;
    public Connection connection;

    @Override
    public void updateFromDataSet(String tableName1, DataSet updateData1, int id) {
        try {
            String format = "%s = ?,";
            String updatedString = getStringFormatted(updateData1, format);

            String update = " UPDATE " + tableName1 + " SET " + updatedString + " WHERE id = ?";
            PreparedStatement preparedStatement = connection.prepareStatement(update);

            int index = 1;

            for (Object value : updateData1.getColumnValues()) {
                preparedStatement.setObject(index, value);
                index++;
            }
            preparedStatement.setObject(index, id);

            preparedStatement.executeUpdate();
            preparedStatement.close();
        } catch (SQLException e){
            e.printStackTrace();
        }
    }

    @Override
    public String getStringFormatted(DataSet updateData1, String format) {
        String tableNames = "";
        for(String tableNameFromDataSet : updateData1.getColumnNames()) {
            tableNames += String.format(format, tableNameFromDataSet);
        }
        tableNames = tableNames.substring(0, tableNames.length() - 1);
        return tableNames;
    }

    @Override
    public String getStringValue(DataSet input, String formatValue) {
        String StringTableValue = "";
        for (Object value : input.getColumnValues()) {
            StringTableValue += String.format(formatValue, value);
        }
        StringTableValue = StringTableValue.substring(0, StringTableValue.length() - 1);// deleting excess comma
        return StringTableValue;
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
            while ( rs2.next() ) { // putNewString to connectAndCommands.DataSet column name and insist of the table cell
                DataSet dataSet = new DataSet();
                result[count++] = dataSet;
                for (int i = 1; i <= resultSetMetaData.getColumnCount(); i++) {// break insist of the string to columns
                    dataSet.putNewString(resultSetMetaData.getColumnName(i), rs2.getObject(i));
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

    @Override
    public String getTableValue(String tableName) {
        String result = "\r";// это наполнитель для того, чтобы в строке хоть что-то было, иначе "String index out of range: -1"
        String format = " %s | ";
        DataSet[] dataset = getTableData(tableName);
        for (DataSet aDataset : dataset) {
            result += "| " + getStringValue(aDataset, format) + "\n";
        }
        result = result.substring(0, result.length() - 1);
        return result;
    }

    @Override
    public void selectAndPrint(String tableName) {
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
    public void connect(String baseName, String login, String parole) throws SQLException {
        connection = null;
        try {
            Class.forName("org.postgresql.Driver");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
            System.out.println("Please, add JDBC Driver to the project");
        }
//        try {
        connection = DriverManager
                .getConnection("jdbc:postgresql://localhost:5432/" + baseName + "",// TODO попутаны местами название базы/логин/пароль
                        login, parole);
//        } catch (SQLException e) {
//            System.out.println(String.format("Can't get connection for database:%s user:%s", baseName, login));//прикольно!!!!!!!!!!!1
//            e.printStackTrace();
//            connection = null;
//        }
    }

    public boolean isConnect() {
        return connection != null;
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
            stmt.executeUpdate(sql);
            stmt.close();
        } catch ( Exception e ) {
            System.err.println( e.getClass().getName()+": "+ e.getMessage() );
            System.exit(0);
        }
    }
    @Override
    public void dropTable(String tableName) {
        Statement stmt;
        try {
            stmt = connection.createStatement();
            String sql = "DROP TABLE " + tableName;
            stmt.executeUpdate(sql);
            stmt.close();
        } catch ( Exception e ) {
            System.err.println( e.getClass().getName()+": "+ e.getMessage() );
            System.exit(0);
        }
    }

    @Override
    public String[] getTableHead(String tableName) {
        String[] listOfColumns;
        listOfColumns = new String[100];
        try {
            int countOfColumns = 0;
            String selectColumnNames = "SELECT * FROM information_schema.columns WHERE table_schema = 'public' " +
                    // причина, блин, в том, что в запросе PUBLIC было вместо public
                    "AND TABLE_NAME = '" + tableName + "'";//и '" + tableName + "'" должно быть зажато между кавычек БЕЗ ПРОБЕЛОВ!!!
            Statement statement3 = connection.createStatement();
            ResultSet rs1 = statement3.executeQuery(selectColumnNames);
            while ( rs1.next() ) {
                listOfColumns[countOfColumns++] = rs1.getString("column_name");
            }
            listOfColumns = Arrays.copyOf(listOfColumns, countOfColumns, String[].class);//deleting zero Elements ??????????????????????????String[].class????
            rs1.close();
            statement3.close();
        } catch (SQLException e) {
            System.out.println("Exception from getTableHead");
            e.printStackTrace();
            return new String[0];
        }
        return listOfColumns;
    }

    @Override
    public void clear(String tableName){
        try {
            Statement statement = connection.createStatement();
            String clear = "DELETE FROM " + tableName + " WHERE id < 1000000;";
            statement.executeUpdate(clear);
            statement.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void createString(DataSet input, String tableName1){ // TODO !!!!!!!!!!!!!!!!!!!!!!!!!!
        try {
            Statement statement = connection.createStatement();

            // createString string of column names
            String format = "%s,";
            String StringTableNames = getStringFormatted(input, format);

            // createString string of column values
            String formatValue = "'%s',";
            String StringTableValue = getStringValue(input, formatValue);

            statement.executeUpdate("INSERT INTO " + tableName1  + " (" + StringTableNames + " )"
                    + " VALUES (" + StringTableValue + ")");
            statement.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
