package comand;

import connectAndCommands.DataBaseManager;
import view.Viewshka;

/**
 * Created by 123 on 29.04.2016.
 */
public class DeleteTable implements Command {
    private DataBaseManager manager;
    private Viewshka viewshka;
    private String COMMAND_SAMPLE = "delete table|test";

    public DeleteTable(DataBaseManager manager, Viewshka viewshka) {
        this.manager = manager;
        this.viewshka = viewshka;
    }

    @Override
    public boolean canProcess(String command) {
        return command.startsWith("dropTable|");
    }

    @Override
    public void process(String command) {
        try {
            String[] strings = command.split("\\|");
            validationAmountOfParameters(strings);
            String tableName = strings[1];
            deleteTable(tableName);

        } catch (Throwable e) {
            System.out.println(e.getMessage());
        }
    }

    protected void deleteTable(String tableName) {
        viewshka.wright(String.format("Вы действительно хотите удалить таблицу '%s'? 'Y'/'N'", tableName));
        String read1 = viewshka.read();
        if (read1.equals("N")) {
            viewshka.wright("Введите следующую команду");
        } else if (read1.equals("Y")) {
            manager.dropTable(tableName);
            viewshka.wright("Таблица '" + tableName + "' успешно удалена");
        } else {
            viewshka.wright("Несуществующая команда!");
        }

    }


    protected void validationAmountOfParameters(String[] strings) {
        if (strings.length != parametersLength()) {
            throw new IllegalArgumentException(String.format("Неверное количество параметров, разделенных '|' , " +
                    "необходимо %s, а введено: %s", parametersLength(), strings.length));
        }
    }

    private int parametersLength() {
        return COMMAND_SAMPLE.split("\\|").length;
    }
}

