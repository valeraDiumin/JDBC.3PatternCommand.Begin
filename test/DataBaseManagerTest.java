import connectAndCommands.DataBaseManager;
import connectAndCommands.DataSet;
import org.junit.Before;
import org.junit.Test;

import java.sql.SQLException;
import java.util.Arrays;

import static junit.framework.TestCase.assertEquals;

public abstract class DataBaseManagerTest {

    protected DataBaseManager manager;
    protected String tableName1;

    protected abstract DataBaseManager getDataBaseManager();

    @Before
    public void setup() {
        manager = getDataBaseManager();
        try {
            manager.connect("postgres", "postgres", "11111111");
        } catch (SQLException e) {
            e.printStackTrace();
        }
        tableName1 = "user1";
//        manager.createNewTable(tableName1);
    }

    @Test
    public void listOfAllTablesTest() {
        String[] listOfAllTables = manager.getTableNames();
        assertEquals("[user1]", Arrays.toString(listOfAllTables));
    }

    @Test
    public void getTableDataTest() {
        manager.clear(tableName1);

        // when create string in base
        DataSet data = new DataSet();
        data.put("id", 3);
        data.put("name", "Jack Bob");
        data.put("salary", "1000000");
        manager.create(data, tableName1); // I have to give to method the name of table or hardcode it to method create
        DataSet[] users = manager.getTableData(tableName1);

        assertEquals(1, users.length);
        DataSet user = users[0];

        assertEquals("[id, name, salary]", Arrays.toString(user.getColumnNames()));
        assertEquals("[3, Jack Bob, 1000000]", Arrays.toString(user.getColumnValues()));

    }
    @Test
    public void updateTest() {

        //        give table from base and prepare it for test
        manager.clear(tableName1);

        // when create string in base
        DataSet updateData = new DataSet();
        updateData.put("id", 1);
        updateData.put("name", "Bob");
        updateData.put("salary", "10");
        //put data to connectAndCommands.DataSet object
        manager.create(updateData, tableName1); // I have to give to method the name of table or hardcode it to method create

        DataSet updateData2 = new DataSet();
        updateData2.put("id", 3);
        updateData2.put("name", "Jack Bob");
        updateData2.put("salary", "1000000");
        //put data to connectAndCommands.DataSet object
        manager.create(updateData2, tableName1); // I have to give to method the name of table or hardcode it to method create

        DataSet updateData1 = new DataSet();
        updateData1.put("id", 3);
        updateData1.put("name", "Jack");
        updateData1.put("salary", "1000"); // to connectAndCommands.DataSet

        // begin with string in table from previous tests
        manager.updateFromDataSet(tableName1, updateData1, 3);// Directly to Postgresql base
        //give data from base and test array of data length (amount of strings in data)

        DataSet[] users = manager.getTableData(tableName1);

        assertEquals(2, users.length);

        //give first string from array with definite date, created by us
        // and test is data same?
        DataSet user = users[1];

        assertEquals("[id, name, salary]", Arrays.toString(user.getColumnNames()));
        assertEquals("[3, Jack, 1000]", Arrays.toString(user.getColumnValues()));
    }
    @Test
    public void getSizeTest() { // delete all strings and create one string
        manager.clear(tableName1);

        // when create string in base
        DataSet updateData = new DataSet();
        updateData.put("id", 3);
        updateData.put("name", "Jack Bob");
        updateData.put("salary", "1000000");
        //put data to connectAndCommands.DataSet object
        manager.create(updateData, tableName1); // I have to give to method the name of table or hardcode it to method create

        DataSet updateData1 = new DataSet();
        updateData1.put("id", 4);
        updateData1.put("name", "Jack Bob0");
        updateData1.put("salary", "1");
        //put data to connectAndCommands.DataSet object
        manager.create(updateData1, tableName1); // I have to give to method the name of table or hardcode it to method create

        //give data from base and test array of data length (amount of strings in data)

        DataSet[] users = manager.getTableData(tableName1);

        assertEquals(2, users.length);

        manager.clear(tableName1);
    }
    @Test
    public void getTableHeadTest() {
        manager.clear(tableName1);

        // when create string in base
        DataSet updateData = new DataSet();
        updateData.put("id", 3);
        updateData.put("name", "Jack Bob");
        updateData.put("salary", "1000000");
        //put data to connectAndCommands.DataSet object
        manager.create(updateData, tableName1);

        String tableHead = Arrays.toString(manager.getTableHead(tableName1));
        assertEquals("[id, name, salary]", tableHead);
    }
    @Test
    public void getTableValueTest() {
        manager.clear(tableName1);
        DataSet data = new DataSet();
        data.put("id", 3);
        data.put("name", "Jack Bob");
        data.put("salary", "1000000");
        manager.create(data, tableName1); // I have to give to method the name of table or hardcode it to method create
        String tableValue = manager.getTableValue(tableName1);

        assertEquals("| 3 |  Jack Bob |  1000000 |", tableValue);
    }
}
