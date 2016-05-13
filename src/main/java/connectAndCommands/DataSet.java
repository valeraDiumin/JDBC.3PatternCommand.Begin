package connectAndCommands;

public class DataSet {

    public void updateFrom(DataSet newValue) {
        this.freeIndex = 0;
        for (int index = 0; index < newValue.freeIndex; index++) {
            Data newData = newValue.data[index];
            this.putNewString(newData.columnName, newData.value);
        }
    }
//
//           public interface: 3 Cycles!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!! :(
//    public void updateFrom(connectAndCommands.DataSet newValue) {
//        this.freeIndex = 0;
//        String[] columnNames = newValue.getColumnNames();
//        Object[] columnValues1 = newValue.getColumnValues();
//        for (int index = 0; index < columnNames.length; index++) {

//            String columnName = columnNames[index];
//            Object columnValues = columnValues1[index];// amending
//            Object columnValues = newValue.get(columnName);
//            this.putNewString(columnName, columnValues);
//        }
//    }

    public Object get(String nameOfColumn) {
        Object result = null;
        for (int i = 0; i < freeIndex; i++) {
            if (data[i].getColumnName().equals(nameOfColumn)){
                result = data[i].getColumnValue();
                break;
            }
        }
        return result;
    }

    /* default */ static class Data {
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

    public void putNewString(String columnName, Object value) {
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

}
