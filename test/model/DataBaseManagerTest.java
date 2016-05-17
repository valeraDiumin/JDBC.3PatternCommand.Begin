package model;

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
//    private Viewshka view;

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

        // when createString string in base
        DataSet data = new DataSet();
        data.putNewString("id", 3);
        data.putNewString("name", "Jack Bob");
        data.putNewString("salary", "1000000");
        manager.createString(data, tableName1); // I have to give to method the name of table or hardcode it to method createString
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

        // when createString string in base
        DataSet updateData = new DataSet();
        updateData.putNewString("id", 1);
        updateData.putNewString("name", "Bob");
        updateData.putNewString("salary", "10");
        //putNewString data to connectAndCommands.DataSet object
        manager.createString(updateData, tableName1); // I have to give to method the name of table or hardcode it to method createString

        DataSet updateData2 = new DataSet();
        updateData2.putNewString("id", 3);
        updateData2.putNewString("name", "Jack Bob");
        updateData2.putNewString("salary", "1000000");
        //putNewString data to connectAndCommands.DataSet object
        manager.createString(updateData2, tableName1); // I have to give to method the name of table or hardcode it to method createString

        DataSet updateData1 = new DataSet();
        updateData1.putNewString("id", 3);
        updateData1.putNewString("name", "Jack");
        updateData1.putNewString("salary", "1000"); // to connectAndCommands.DataSet

        // begin with string in table from previous tests
        manager.updateFromDataSet(tableName1, updateData1, 3);// Directly to Postgresql base
        //give data from base and test array of data length (amount of strings in data)

        DataSet[] users = manager.getTableData(tableName1);

        assertEquals(2, users.length);

        //give first string from array with definite date, created by us
        // and test is data same?
        DataSet user0 = users[0];
        DataSet user1 = users[1];

        assertEquals("[id, name, salary]", Arrays.toString(user1.getColumnNames()));
        assertEquals("[1, Bob, 10]", Arrays.toString(user0.getColumnValues()));
        assertEquals("[3, Jack, 1000]", Arrays.toString(user1.getColumnValues()));
    }
    @Test
    public void getSizeTest() { // delete all strings and createString one string
        manager.clear(tableName1);

        // when createString string in base
        DataSet updateData = new DataSet();
        updateData.putNewString("id", 3);
        updateData.putNewString("name", "Jack Bob");
        updateData.putNewString("salary", "1000000");
        //putNewString data to connectAndCommands.DataSet object
        manager.createString(updateData, tableName1); // I have to give to method the name of table or hardcode it to method createString

        DataSet updateData1 = new DataSet();
        updateData1.putNewString("id", 4);
        updateData1.putNewString("name", "Jack Bob0");
        updateData1.putNewString("salary", "1");
        //putNewString data to connectAndCommands.DataSet object
        manager.createString(updateData1, tableName1); // I have to give to method the name of table or hardcode it to method createString

        //give data from base and test array of data length (amount of strings in data)

        DataSet[] users = manager.getTableData(tableName1);

        assertEquals(2, users.length);

        manager.clear(tableName1);
    }
    @Test
    public void getTableHeadTest() {
        manager.clear(tableName1);

        // when createString string in base
        DataSet updateData = new DataSet();
        updateData.putNewString("id", 3);
        updateData.putNewString("name", "Jack Bob");
        updateData.putNewString("salary", "1000000");
        //putNewString data to connectAndCommands.DataSet object
        manager.createString(updateData, tableName1);

        String tableHead = Arrays.toString(manager.getTableHead(tableName1));
        assertEquals("[id, name, salary]", tableHead);

        String format = " %s | ";
        String getStringFormatted = manager.getStringFormatted(updateData,format);
        assertEquals(" id |  name |  salary |", getStringFormatted);
    }
    @Test
    public void getTableValueTest() {
        manager.clear(tableName1);
        DataSet data = new DataSet();
        data.putNewString("id", 3);
        data.putNewString("name", "Jack Bob");
        data.putNewString("salary", "1000000");
        manager.createString(data, tableName1); // I have to give to method the name of table or hardcode it to method createString
        String tableValue = manager.getTableValue(tableName1);

        assertEquals("\r|  3 |  Jack Bob |  1000000 |", tableValue);
    }
//    @Test // сделали тест, аналогичный в лекции 5 25 мин.
//    public void exitInputTest(){
//        Exit exit = new Exit(view);
//        assertTrue(exit.canProcess("exit"));
//        boolean nonCommand = exit.canProcess("exit");
//        assertFalse(nonCommand);
//    }
}
