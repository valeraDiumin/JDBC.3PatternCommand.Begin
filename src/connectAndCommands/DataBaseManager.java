package connectAndCommands;

import java.sql.SQLException;

/**
 * Created by 123 on 07.03.2016.
 */
public interface DataBaseManager {
    void updateFromDataSet(String tableName1, DataSet updateData1, int id);

    void clear(String tableName);

    void create(DataSet input, String tableName1);

    void createNewTable(String tableName);

    DataSet[] getTableData(String tableName);

    int getSize(String tableName);

    void connect(String baseName, String login, String parole) throws SQLException;

    String[] getTableNames();

    void selectAndPrint(String tableName);
}
