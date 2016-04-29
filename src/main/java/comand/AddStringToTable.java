package comand;

import connectAndCommands.DataBaseManager;
import connectAndCommands.DataSet;
import view.Viewshka;

/**
 * Created by 123 on 29.04.2016.
 */
public class AddStringToTable implements Command {
    private static final String COMMAND_SAMPLE = "createString|user1|12|John|1000";
    private DataBaseManager manager;
    private Viewshka viewshka;
    private DataSet dataSet;

    public AddStringToTable(DataBaseManager manager, Viewshka viewshka, String tableName, Command[] commands) {
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
            ifWrongAmountOfParameters(strings);

            creatingStringInTable(strings);

        } catch (Throwable e) {
            System.out.println(e.getMessage());
        }
    }

    protected void creatingStringInTable(String[] strings) {
        DataSet dataSet = new DataSet();
        String id = strings[2];
        dataSet.putNewString("id", id);
        String name = strings[3];
        dataSet.putNewString("name", name);
        String salary = strings[4];
        dataSet.putNewString("salary", salary);
        String tableName = strings[1];
        manager.createString(dataSet, tableName);
        viewshka.wright(String.format("Строка в таблице '%s'со значениями id = '%s', name = '%s', salary = '%s'" +
                " успешно создана!", tableName, id, name, salary));
    }

    protected void ifWrongAmountOfParameters(String[] strings) {
        if (strings.length != parametersLength()) {
            throw new IllegalArgumentException(String.format("Неверное количество параметров, разделенных '|' , " +
                    "необходимо %s, а введено: %s", parametersLength(), strings.length));
        }
    }

    private int parametersLength() {
        return COMMAND_SAMPLE.split("\\|").length;
    }
}
