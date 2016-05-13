package comand;

import connectAndCommands.DataBaseManager;
import view.Viewshka;

public class Clear_table implements Command {
    private DataBaseManager manager;
    private Viewshka viewshka;
    private String tableName;
    private Command[] commands;
    private String COMMAND_SAMPLE = "clear|user1";
    private IsTableExistInBase isTableExistInBase;

    public Clear_table(DataBaseManager manager, Viewshka viewshka, String tableName, Command[] commands) {

        this.manager = manager;
        this.viewshka = viewshka;
        this.tableName = tableName;
        this.commands = commands;
        isTableExistInBase = new IsTableExistInBase();
    }

    @Override
    public boolean canProcess(String command) {
        return command.startsWith("clear|");
    }

    @Override
    public void process(String command) {
        String[] strings = command.split("\\|");//
        tableName = strings[1];//
        try {
            validationAmountOfParameters(command);
            if (isTableExistInBase.isTableNameRight(command, manager)) {
                viewshka.wright(String.format("Вы действительно хотите удалить все " +
                        "строки таблицы '%s'? 'Y'/'N'", tableName));
                String read1 = viewshka.read();
                if (read1.equals("N")) {
                    viewshka.wright("Введите следующую команду");
                } else if (read1.equals("Y")) {
                    manager.clear(tableName);
                    viewshka.wright(" Все строки таблицы '" + tableName + "' успешно удалены");
                } else {
                    viewshka.wright("Несуществующая команда!");
                }
            }
        } catch (Throwable e) {
            System.out.println(e.getMessage());
        }
    }

    protected void validationAmountOfParameters(String command) {
        if (command.split("\\|").length != parametersLength()) {
            throw new IllegalArgumentException(String.format("Неверное количество параметров, " +
                    "разделенных '|' , " + "необходимо %s, а введено: %s. " +
                    "Вводите в формате clear|tableName", parametersLength(), command.split("\\|").length));
        }
    }

    private int parametersLength() {
        return COMMAND_SAMPLE.split("\\|").length;
    }
}
