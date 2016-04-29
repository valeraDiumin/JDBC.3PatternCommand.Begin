package comand;

import connectAndCommands.DataBaseManager;
import view.Viewshka;

public class Clear_table implements Command {
    private DataBaseManager manager;
    private Viewshka viewshka;
    private String tableName;
    private Command[] commands;

    public Clear_table(DataBaseManager manager, Viewshka viewshka, String tableName, Command[] commands) {

        this.manager = manager;
        this.viewshka = viewshka;
        this.tableName = tableName;
        this.commands = commands;
    }

    @Override
    public boolean canProcess(String command) {
        return command.startsWith("clear|");
    }

    @Override
    public void process(String command) {
        if (doFind(command)) {
            viewshka.wright(String.format("Вы действительно хотите очистить таблицу '%s'? 'Y'/'N'", tableName));
            String read1 = viewshka.read();
            if (read1.equals("N")) {
                viewshka.wright("Введите следующую команду");
            } else if (read1.equals("Y")) {
//                viewshka.wright("Введите команду");
                manager.clear(tableName);
                viewshka.wright(" Все строки таблицы '" + tableName + "' успешно удалены");
            } else {
                viewshka.wright("Несуществующая команда!");
            }
        }
    }

    private boolean doFind(String command) {
        viewshka.wright("Вы собираетесь удалить содержимое таблицы:");
        String[] split = command.split("\\|");
        if (split.length <= 1) {
            viewshka.wright("Таблица не выбрана");
            return false;
        } else {
            String command1 = split[1];
            viewshka.wright(String.format(" '%s'", command1));
            isTableNameRight(command1);
            return true;
        }
    }

    private void isTableNameRight(String command1) {
        String[] tableList = manager.getTableNames();
        while (true) {
            boolean isTableChoiced = false;
            for (String aTableList : tableList) {
                if (aTableList.equals(command1)) {
                    tableName = command1;
                    isTableChoiced = true;
                    break;
                }
            }
            if (!isTableChoiced) {
                viewshka.wright("Вы ввели название несуществующей таблицы");//(String.format("Вы ввели название несуществующей таблицы '%s'", tableName));
            } else {
                break;
            }
            viewshka.wright("Пожалуйста, введите название существующей таблицы или 'exit' для выхода из программы");
            command1 = viewshka.read();
            if (command1.equals("exit")) {
                throw new ExitException();
            }
        }
    }
}
