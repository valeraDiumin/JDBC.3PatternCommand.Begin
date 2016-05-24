package connectAndCommands;

import java.util.Arrays;

public class inMemoryDataBaseManager implements DataBaseManager {
    // TODO It is possible to createString multitable class with some tables.
    public static final String TABLE_NAME = "user1"; // то есть база данных заточена только под "user"
    private DataSet[] data;//each data is object for storing one string

    {
        data = new DataSet[1000];
    }

    private int freeIndex = 0; //return dataSetLengths amount of strings data

    @Override
    public DataSet[] getTableData(String tableName) {
        validateTable(tableName);
        return Arrays.copyOf(data, freeIndex); // copy of private connectAndCommands.DataSet[]
    }

    private void validateTable(String tableName) {
        if (!tableName.equals("user1")) {
            throw new IllegalArgumentException("Wrong name of the table (we work with " + tableName + " table");
        }
    }

    @Override
    public String[] getTableNames() {
        return new String[]{TABLE_NAME}; // Заточена только под одну таблицу
    }

    @Override
    public void connect(String baseName, String login, String parole) {
        // do nothing
    }

    @Override
    public void clear(String tableName) {
        data = new DataSet[1000];
        freeIndex = 0;
    }

    @Override
    public void createString(DataSet input, String tableName1) {//TODO вносит всё подряд без проверки на одинаковость id
        data[freeIndex] = input;
        freeIndex++;

    }

    @Override
    public String getStringValue(DataSet input, String formatValue) {
        return new String("это из Инмемори getStringValue()");
    }

    @Override
    public boolean isConnect() {
        return true;
    }

    @Override
    //responsibility of this method is to work with field data[] in this class and do not work with connectAndCommands.DataSet newValue
    public void updateFromDataSet(String tableName1, DataSet newValue, int id) {
        for (int index = 0; index < freeIndex; index++) { //iterate to all stored strings
            if (((Integer) data[index].get("id")) == id) { // and if we have found string with the same id
                data[index].updateFrom(newValue); // updating this string with "newvalue" from connectAndCommands.DataSet object newValue

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
    public void selectAndPrint(String tableName) {

    }

    @Override
    public void dropTable(String tableName) {
        freeIndex = 0;
    }

    @Override
    public String[] getTableHead(String tableName) {//Саша вообще захардкодил 3 колонки и возвращает в Контроллер массив строк
        String[] strings = {"id", "name", "salary"};
        return strings;
    }

    @Override
    public String getTableValue(String tableName) {
        String format = " %s | ";
        String result1 = "\r";// это наполнитель для того, чтобы в строке хоть что-то было, иначе "String index out of range: -1"
        DataSet[] getNextStringDataSet = getTableData(tableName);
        for (int i = 0; i < freeIndex; i++) {
            result1 += "| " + getValueFormatted(getNextStringDataSet[i], format) + "\n";
        }
        result1 = result1.substring(0, result1.length() - 1);
        return result1;
    }

    @Override
    public String getStringFormatted(DataSet updateData1, String format) {//заходит в массив имён колонок в DataSet, проходит по ним, добавляет знак, отрезает кончик
        String tableNames = "";
        for (String tableNameFromDataSet : updateData1.getColumnNames()) {
            tableNames += String.format(format, tableNameFromDataSet);
        }
        tableNames = tableNames.substring(0, tableNames.length() - 1);
        return tableNames;
    }

    public String getValueFormatted(DataSet updateData1, String format) {//заходит в массив имён колонок в DataSet, проходит по ним, добавляет знак, отрезает кончик
        String tableValue = "";
        for (int i = 0; i < updateData1.freeIndex; i++) {
            tableValue += String.format(format, updateData1.getColumnValues()[i]);//TODO только для строк!
        }
        tableValue = tableValue.substring(0, tableValue.length() - 1);
        return tableValue;
    }
}
