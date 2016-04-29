package comand;

import connectAndCommands.DataBaseManager;
import view.Viewshka;

/**
 * Created by 123 on 29.04.2016.
 */
public class Print implements Command {
    private String COMMAND_SAMPLE = "print|user1";
    private DataBaseManager manager;
    private Viewshka viewshka;

    public Print(DataBaseManager manager, Viewshka viewshka, String tableName, Command[] commands) {

        this.manager = manager;
        this.viewshka = viewshka;
    }

    @Override
    public boolean canProcess(String command) {
        return command.startsWith("print|");
    }

    @Override
    public void process(String command) {
        String[] commands = command.split("\\|");
        try {
            if (commands.length != parametersLength()) {
                throw new IllegalArgumentException(String.format("Неверное количество параметров, разделенных '|' , " +
                        "необходимо %s, а введено: %s", parametersLength(), commands.length));
            }

            String tableName = commands[1];
            printHeader(tableName);
            printRows(tableName);

        } catch (Throwable e) {
            System.out.println(e.getMessage());
        }
    }

    private int parametersLength() {
        return COMMAND_SAMPLE.split("\\|").length;
    }

    private void printHeader(String tableName) {
        String[] tableHead = manager.getTableHead(tableName);
        viewshka.wright("------------------------------------");
        String result = "|";
        for (String aTableHead : tableHead) {
            result += aTableHead + "|";
        }
        result.substring(0, result.length() - 1);
        viewshka.wright(result);
        viewshka.wright("------------------------------------");
    }

    private void printRows(String tableName) {// Возможно, этот вариант лучше, чем показанный во 2 лекции: преобразование
        // кода идёт не заходя в класс DataSet, а в DataBaseManager формируем строку отчёта (может, и распечатывать лучше там же)
        String tableValue = manager.getTableValue(tableName);
        viewshka.wright(tableValue);
        viewshka.wright("------------------------------------");
    }
}