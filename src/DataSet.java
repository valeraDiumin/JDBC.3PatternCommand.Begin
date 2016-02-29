import java.sql.ResultSet;

/**
 * Created by 123 on 28.02.2016.
 */
public class DataSet {

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
    public Data [] datas = new Data[100];//TODO magic number 100
    public int index = 0;

    public void put(String columnName, Object value) {
        datas[index++] = new Data(columnName, value);
    }
}
