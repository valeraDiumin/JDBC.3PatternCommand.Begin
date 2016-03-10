import java.util.Arrays;

/**
 * Created by 123 on 08.03.2016.
 */
public class inMemoryDataBaseManager implements DataBaseManager {

    public static final String TABLE_NAME = "user12"; // то есть база данных заточена только под "user"
    private DataSet[] data = new DataSet[1000];//each data is object for storing one string
    private int freeIndex = 0; //return dataSetLengths amount of strings data

    @Override
    public DataSet[] getTableData(String tableName) {
        validateTable(tableName);
        return Arrays.copyOf(data, freeIndex); // copy of private DataSet[]
    }

    private void validateTable(String tableName) {
        if (!tableName.equals("user12")){
            throw new  IllegalArgumentException("Wrong name of the table (we work with " + tableName + " table" );
        }
    }

    @Override
    public String[] getTableNames() {
        return new String[] {TABLE_NAME}; // Заточена только под одну таблицу
    }

    @Override
    public void connect(String baseName, String login, String parole) {
        // do nothing
    }

    @Override
    public void clear(String tableName) {
        data = new DataSet[1000]; // записываем поверх массива пустой массив
        freeIndex = 0; // или вообще сказали, что длина массива с данными равна нулю, то есть считаем, что данных нет,
                           // и их не запрашиваем, и поверх записываем новые
    }

    @Override
    public void create(DataSet input, String tableName1) {
        data[freeIndex] = input;
        freeIndex++;

    }

    @Override //responsibility of this method is to work with field data[] in this class and do not work with DataSet newValue
    public void updateFromDataSet(String tableName1, DataSet newValue, int id) {
        for (int index = 0; index < freeIndex; index++) { //iterate to all stored strings
            if (data[index].get("id") == id) { // and if we have found string with the same id
                data[index].updateFrom(newValue); // updating this string with "newvalue" from DataSet object newValue

            }
        }
    }

    @Override
    public int getSize(String tableName) {
       return freeIndex;
    }

    @Override
    public void createNewTable(String tableName) {

    }

    @Override
    public void selectAndPrint() {

    }
}
