import org.junit.Before;
import org.junit.Test;

import java.util.Arrays;

import static junit.framework.TestCase.assertEquals;


/**
 * Created by 123 on 04.03.2016.
 */
public class DataBaseManagerTest {

    private DataBaseManager dataBaseManager;
    private String tableName1;
    DataSet data;

    @Before
    public void setup() {
        dataBaseManager = new DataBaseManager();
        dataBaseManager.connect("postgres", "postgres", "11111111");
        dataBaseManager.getConnection();
        tableName1 = "user12";
    }

    @Test
    public void listOfAllTablesTest() {
        String[] listOfAllTables = dataBaseManager.listOfAllTables();
        assertEquals("[user1, user2, user10, user11, user12, user]", Arrays.toString(listOfAllTables));
    }

    @Test
    public void getTableDataTest() {
//        give table from base and prepare it for test
        dataBaseManager.clearTable(tableName1);

        // when create string in base
        DataSet data = new DataSet();
        data.put("id", 3);
        data.put("NAME", "Jack Bob");
        data.put("SALARY", "1000000");
        //put data to DataSet object
        dataBaseManager.create(data, tableName1); // I have to give to method the name of table or hardcode it to method create

        //give data from base and test array of data length (amount of strings in data)
        DataSet[] users = dataBaseManager.getTableData(tableName1);


        assertEquals(1, users.length);

        //give first string from array with definite date, created by us
        // and test is data same?
        DataSet user = users[0];

        assertEquals("[id, name, salary]", Arrays.toString(user.getcolumnNames()));
        assertEquals("[3, Jack Bob, 1000000]", Arrays.toString(user.getValues()));

    }
    @Test
    public void getSizeTest() {
        int size = dataBaseManager.getSize(tableName1);
        assertEquals("1", "" + size);
    }
    @Test
    public void updateTest() {

        // begin with string in table from previous tests
        dataBaseManager.updateFromDataSet(tableName1, "3", "1000");
        //give data from base and test array of data length (amount of strings in data)
        DataSet[] users = dataBaseManager.getTableData(tableName1);

        assertEquals(1, users.length);
        System.out.println("assertEquals with amount strings in table " + tableName1 + " have done successfully");

        //give first string from array with definite date, created by us
        // and test is data same?
        DataSet user = users[0];

        assertEquals("[id, name, salary]", Arrays.toString(user.getcolumnNames()));
        assertEquals("[3, Jack Bob, 1000]", Arrays.toString(user.getValues()));

    }
}
