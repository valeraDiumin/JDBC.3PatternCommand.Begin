import org.junit.Before;
import org.junit.Test;

import java.util.Arrays;

import static junit.framework.TestCase.assertEquals;


/**
 * Created by 123 on 04.03.2016.
 */
public class DataBaseManagerTest {

    private DataBaseManager dataBaseManager;
    private String tableName;

    @Before
    public void setup() {
        dataBaseManager = new DataBaseManager();
        dataBaseManager.connect("postgres", "postgres", "11111111");
        dataBaseManager.getConnection();
        tableName = "user2";
    }

    @Test
    public void listOfAllTablesTest() {
        String[] listOfAllTables = dataBaseManager.listOfAllTables();
        assertEquals("[user1, user2]", Arrays.toString(listOfAllTables));
    }

    @Test
    public void getTableDataTest() {
//        give table from base and prepare it for test
//        dataBaseManager.clearTable(tableName);

        // when create string in base
        DataSet data = new DataSet();
        data.put("ID", 2);
        data.put("NAME", "Jack Bobo");
        data.put("SALARY", 1000000);
        System.out.println("Data putted to DataSet object successfully");
//        dataBaseManager.create(input);
        System.out.println("In Postgresql base have made string successfully");

        //give data from base and test array of data length (amount of strings in data)
        DataSet[] users = dataBaseManager.getTableData(tableName);

        System.out.println(users.length);

        assertEquals(1, users.length);
        System.out.println("Content of array with data have tested successfully");

        //give first string from array with definite date, created by us
        // and test is data same?
        DataSet user = users[0];
        System.out.println(Arrays.toString(user.getcolumnNames()));
        System.out.println(Arrays.toString(user.getValues()));
        assertEquals("[ID,NAME,SALARY]", Arrays.toString(user.getcolumnNames()));
        assertEquals("['2','Jack Bobo','1000000']", Arrays.toString(user.getValues()));

    }
    @Test
    public void getSizeTest() {
        int size = dataBaseManager.getSize(tableName);
        assertEquals("1", "" + size);
    }
}
