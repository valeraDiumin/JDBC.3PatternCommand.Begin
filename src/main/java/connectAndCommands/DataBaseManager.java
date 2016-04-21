package connectAndCommands;

import java.sql.SQLException;

public interface DataBaseManager {
    boolean isConnect();

    void updateFromDataSet(String tableName1, DataSet updateData1, int id);

    String getStringFormatted(DataSet updateData1, String format);

    void clear(String tableName);

    void create(DataSet input, String tableName1);

    String getStringValue(DataSet input, String formatValue);

    void createNewTable(String tableName);

    DataSet[] getTableData(String tableName);

    int getSize(String tableName);

    void connect(String baseName, String login, String parole) throws SQLException;

    String[] getTableNames();

    void selectAndPrint(String tableName);

    String[] getTableHead(String tableName);

    String getTableValue(String tableName);
}
