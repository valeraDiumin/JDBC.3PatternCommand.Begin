import org.junit.Before;
import org.junit.Test;

import java.util.Arrays;

import static junit.framework.TestCase.assertEquals;

public abstract class DataBaseManagerTest {

    private DataBaseManager manager;
    private String tableName1;

    protected abstract DataBaseManager getDataBaseManager();

    @Before
    public void setup() {
        manager = getDataBaseManager();
        manager.connect("postgres", "postgres", "11111111");
        tableName1 = "user12";
    }

    @Test
    public void listOfAllTablesTest() {
        String[] listOfAllTables = manager.getTableNames();
        assertEquals("[user1, user2, user10, user11, user12, user]", Arrays.toString(listOfAllTables));
    }

    @Test
    public void getTableDataTest() {
//        give table from base and prepare it for test
        manager.clear(tableName1);

        // when create string in base
        DataSet data = new DataSet();
        data.put("id", 3);
        data.put("NAME", "Jack Bob");
        data.put("SALARY", "1000000");
        //put data to DataSet object
        manager.create(data, tableName1); // I have to give to method the name of table or hardcode it to method create

        //give data from base and test array of data length (amount of strings in data)
        DataSet[] users = manager.getTableData(tableName1);


        assertEquals(1, users.length);

        //give first string from array with definite date, created by us
        // and test is data same?
        DataSet user = users[0];

        assertEquals("[id, name, salary]", Arrays.toString(user.getcolumnNames()));
        assertEquals("[3, Jack Bob, 1000000]", Arrays.toString(user.getValues()));

    }
    @Test
    public void getSizeTest() {
        int size = manager.getSize(tableName1);
        assertEquals("1", "" + size);
    }
    @Test
    public void updateTest() {

        //        give table from base and prepare it for test
        manager.clear(tableName1);

        // when create string in base
        DataSet updateData = new DataSet();
        updateData.put("id", 3);
        updateData.put("NAME", "Jack Bob");
        updateData.put("SALARY", "1000000");
        //put data to DataSet object
        manager.create(updateData, tableName1); // I have to give to method the name of table or hardcode it to method create

        DataSet updateData1 = new DataSet();
//        updateData1.put("id", 3);
        updateData1.put("NAME", "Jack");
        updateData1.put("SALARY", "1000"); // to DataSet

        // begin with string in table from previous tests
        manager.updateFromDataSet(tableName1, updateData1, 3);// Directly to Postgresql base
        //give data from base and test array of data length (amount of strings in data)

        DataSet[] users = manager.getTableData(tableName1);

        assertEquals(1, users.length);

        //give first string from array with definite date, created by us
        // and test is data same?
        DataSet user = users[0];

        assertEquals("[id, name, salary]", Arrays.toString(user.getcolumnNames()));
        assertEquals("[3, Jack, 1000]", Arrays.toString(user.getValues()));
    }
}
