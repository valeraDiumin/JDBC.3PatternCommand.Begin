package comand;

import connectAndCommands.DataBaseManager;
import view.Viewshka;

public class CreateTable implements Command {
    private DataBaseManager manager;
    private Viewshka viewshka;
    private String COMMAND_SAMPLE = "createtable|test";

    public CreateTable(DataBaseManager manager, Viewshka viewshka) {
        this.manager = manager;
        this.viewshka = viewshka;
    }

    @Override
    public boolean canProcess(String command) {
        return command.startsWith("createTable|");
    }

    @Override
    public void process(String command) {
        try {
            String[] strings = command.split("\\|");
            ifWrongAmountOfParameters(strings);
            String tableName = strings[1];
            createTable(tableName);
        } catch (Throwable e) {
            System.out.println(e.getMessage());
        }
    }

    protected void createTable(String tableName) {
            manager.createNewTable(tableName);
            viewshka.wright(" Таблица '" + tableName + "' успешно создана");
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

