import java.util.Arrays;

/**
 * Created by 123 on 28.02.2016.
 */
public class DataSet {

    public void updateFrom(DataSet newValue) {

    }

    public Object get(String name) { //we have to
//        for (int i = 0; i < amountOfColumnsInIncomingString; i++) {
//            result[i] = ColumnsInData[i].getValue();
//        }
        return 0;
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

    public Object getValue() {
        return value;
    }
}
    public Data [] ColumnsInData = new Data[100];//TODO magic number 100
    public int amountOfColumnsInIncomingString = 0;

    public void put(String columnName, Object value) {
        ColumnsInData[amountOfColumnsInIncomingString++] = new Data(columnName, value);
    }
    public Object[] getValues(){
        Object[] result = new Object[amountOfColumnsInIncomingString];
        for (int i = 0; i < amountOfColumnsInIncomingString; i++) {
            result[i] = ColumnsInData[i].getValue();
        }
        return result;
    }
    public String[] getcolumnNames(){
        String[] result = new String[amountOfColumnsInIncomingString];
        for (int i = 0; i < amountOfColumnsInIncomingString; i++) {
            result[i] = ColumnsInData[i].getColumnName();
        }
        return result;
    }

    @Override
    public String toString() {
        return "DataSet{\n" +
                "columnNames =" + Arrays.toString(getcolumnNames()) + "\n" +
                "  getValues =" + Arrays.toString(getValues()) + "\n" +
                '}' + "\n";
    }
}
