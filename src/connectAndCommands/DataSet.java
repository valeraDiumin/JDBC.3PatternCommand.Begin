package connectAndCommands;

import java.util.Arrays;

/**
 * Created by 123 on 28.02.2016.
 */
public class DataSet {

    public void updateFrom(DataSet newValue) {
        this.freeIndex = 0;
        for (int index = 0; index < newValue.freeIndex; index++) { //ходим по колонкам Data строки connectAndCommands.DataSet (пока новая строка не закончится)
            Data newData = newValue.data[index]; // и из новой строки извлекли колонку
            this.put(newData.columnName, newData.value); // и из новой колонки извлекли имя и содержимое и вставили в текущий объект Data
        }
    }
//
//           public interface: 3 Cycles!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!! :(
//    public void updateFrom(connectAndCommands.DataSet newValue) {
//        this.freeIndex = 0;
//        String[] columnNames = newValue.getColumnNames();
//        Object[] columnValues1 = newValue.getColumnValues(); // amending
//        for (int index = 0; index < columnNames.length; index++) { //ходим по колонкам Data строки connectAndCommands.DataSet (пока новая строка не закончится)

//            String columnName = columnNames[index];
//            Object columnValues = columnValues1[index];// amending
//            Object columnValues = newValue.get(columnName);// Cycle*cycle version
//            this.put(columnName, columnValues); // и из новой колонки извлекли имя и содержимое и вставили в текущий объект Data
//        }
//    }

    public Object get(String nameOfColumn) { //по имени колонки возвращаем содержимое колонки (в данной строке)
        for (int i = 0; i < freeIndex; i++) {
            if (data[i].getColumnName().equals(nameOfColumn)){
                return data[i].getColumnValue();
            }
        }
        return null;
    }

    static class Data {
    private String columnName;
    private Object value;

    Data (String columnName, Object value){

        this.columnName = columnName;
        this.value = value;
    }

    public String getColumnName() {
        return columnName;
    }

    public Object getColumnValue() {
        return value;
    }
}
    public Data [] data = new Data[100];//TODO magic number 100
    public int freeIndex = 0;

    public void put(String columnName, Object value) {
        data[freeIndex++] = new Data(columnName, value);
    }
    public Object[] getColumnValues(){
        Object[] result = new Object[freeIndex];
        for (int i = 0; i < freeIndex; i++) {
            result[i] = data[i].getColumnValue();
        }
        return result;
    }
    public String[] getColumnNames(){
        String[] result = new String[freeIndex];
        for (int i = 0; i < freeIndex; i++) {
            result[i] = data[i].getColumnName();
        }
        return result;
    }

    @Override
    public String toString() {
        return "connectAndCommands.DataSet{\n" +
                "columnNames =" + Arrays.toString(getColumnNames()) + "\n" +
                "  getColumnValues =" + Arrays.toString(getColumnValues()) + "\n" +
                '}' + "\n";
    }
}
