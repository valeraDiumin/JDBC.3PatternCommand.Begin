package comand;

import connectAndCommands.DataBaseManager;
import connectAndCommands.DataSet;
import view.Viewshka;

public class AddStringToTable implements Command {
    private static final String COMMAND_SAMPLE = "createString|user1|12|John|1000|14|Dodo|500000";
    private DataBaseManager manager;
    private Viewshka viewshka;
    private DataSet dataSet;

    public AddStringToTable(DataBaseManager manager, Viewshka viewshka) {
        this.manager = manager;
        this.viewshka = viewshka;
    }

    @Override
    public boolean canProcess(String command) {
        return command.startsWith("createString|");
    }

    @Override
    public void process(String command) {
        try {
            String[] strings = command.split("\\|");
            validationAmountOfParameters(strings);

            creatingStringInTable(strings);
        } catch (Throwable e) {
            System.out.println(e.getMessage());
        }
    }

    protected void creatingStringInTable(String[] strings) {
        for (int i = 0; i<((strings.length-2)/3); i++) {
            DataSet dataSet = new DataSet();
            String id = strings[(i*3)+2];
//            System.out.println(id);
            dataSet.putNewString("id", id);
            String name = strings[(i*3)+3];
//            System.out.println(name);
            dataSet.putNewString("name", name);
            String salary = strings[(i*3)+4];
//            System.out.println(salary);
            dataSet.putNewString("salary", salary);
            String tableName = strings[1];
            manager.createString(dataSet, tableName);
            viewshka.wright(String.format("Строка в таблице '%s'со значениями id = '%s', " +
                    "name = '%s', salary = '%s'" + " успешно создана!", tableName, id, name, salary));
        }
    }

    protected void validationAmountOfParameters(String[] strings) {
        if (((strings.length-2)%3) != 0) {
            throw new IllegalArgumentException(String.format("Неверное количество параметров, разделенных '|', " +
                    "введено: %s", strings.length));
        }
    }
}
