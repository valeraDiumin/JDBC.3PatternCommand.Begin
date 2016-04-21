package comand;

import view.Viewshka;
import connectAndCommands.DataBaseManager;
import connectAndCommands.DataSet;

public class Find implements Command {
    private DataBaseManager manager;
    private Viewshka viewshka;
    private String tableName;
    private Command[] commands;

    public Find(DataBaseManager manager, Viewshka viewshka, String tableName, Command[] commands) {

        this.manager = manager;
        this.viewshka = viewshka;
        this.tableName = tableName;
        this.commands = commands;
    }

    @Override
    public boolean canProcess(String command) {
        return command.startsWith("find|");
    }

    @Override
    public void process(String command) {// не может вызваться массив из Контроллера!
        while (true) {
            if (doFind(command)) {
                changingTable(tableName);
                printTable(tableName);
                viewshka.wright("Продолжить работу? Y/'exit'");
                String read1 = viewshka.read();
                if (read1.equals("exit")) {
                    viewshka.wright("До скорой встречи!");
                    System.exit(0);
                } else if (read1.equals("Y")) {
                    break;
                } else {
                    viewshka.wright("Несуществующая команда!");
                }
            } else {
                break;
            }

        }
    }

    private boolean doFind(String command) {
        viewshka.wright("Выбрана таблица:");
        String[] split = command.split("\\|");
        if (split.length <= 1) {
            viewshka.wright("Не выбрана таблица");
            return false;
        } else {
            String command1 = split[1];
            viewshka.wright(String.format("Таблица '%s'", command1));
            isTableNameRight(command1);
            return true;
        }
    }

    private void isTableNameRight(String command1) {
        String[] tableList = manager.getTableNames();
        while (true) {
            boolean isTableChoiced = false;
            for (int index = 0; index < tableList.length; index++) {
                if (tableList[index].equals(command1)) {
                    tableName = command1;
                    isTableChoiced = true;
                    break;
                }
            }
            if (!isTableChoiced) {
                viewshka.wright("Вы неправильно ввели название таблицы");
            } else {
                break;
            }
            viewshka.wright("Введите название таблицы");
            command1 = viewshka.read();
        }
    }

    private void printTable(String tableName) {
        viewshka.wright("Вы желаете посмотреть содержимое всей таблицы '" + tableName + "' ? Y/N");
        String read = viewshka.read();
        if (read.equals("Y")) {
            printHeader(tableName);
            printRows(tableName);
        }
    }

    private void printHeader(String tableName) {
        String[] tableHead = manager.getTableHead(tableName);
        viewshka.wright("------------------------------------");
        String result = "|";
        for (int index = 0; index < tableHead.length; index++) {
            result += tableHead[index] + "|";
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

    private void changingTable(String tableName) {
        viewshka.wright("Вы желаете изменить содержание таблицы '" + tableName + "' ? Y/N");
        String iWishToChangeTable = viewshka.read(); //TODO если что-то другое кроме Y/N, то поправить и предложить снова ввести.....
        if (iWishToChangeTable.equals("Y")) {

            viewshka.wright(" Вы желаете очистить таблицу '" + tableName + "' перед введением новой информации? Y/N");
            String answer3 = viewshka.read();
            if (answer3.equals("Y")) {
                manager.clear(tableName);
                viewshka.wright(" Все строки таблицы '" + tableName + "' успешно удалены");
            }
            while (true) {
                viewshka.wright("Вы хотите ввести новые данные в таблицу '" + tableName + "' ? Y/N");//TODO цикл для ввода нескольких строк
                String read2 = viewshka.read();
                if (read2.equals("Y")) {
                    viewshka.wright("Пожалуйста, введите данные. Построчно введите id, имя, зарплату ");
                    DataSet dataSet = new DataSet();
                    String id = viewshka.read();
                    dataSet.putNewString("id", id);

                    String name = viewshka.read();
                    dataSet.putNewString("name", name);

                    String salary = viewshka.read();
                    dataSet.putNewString("salary", salary);

                    manager.create(dataSet, tableName);
                    viewshka.wright("Строка данных успешно добавлена в таблицу '" + tableName + "' !");
                } else if (read2.equals("N")) {
                    break;
                } else {
                    viewshka.wright("Неправильная команда");
                }
            }
        }
    }
}
