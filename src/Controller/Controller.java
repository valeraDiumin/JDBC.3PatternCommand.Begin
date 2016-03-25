package Controller;

import Comand.Command;
import Comand.Exit;
import View.Viewshka;
import connectAndCommands.DataBaseManager;
import connectAndCommands.DataSet;

import java.util.Arrays;

public class Controller {
    private Command[] commands;
    DataBaseManager manager;
    Viewshka viewshka;
    String tableName;


    public Controller(DataBaseManager manager, Viewshka viewshka) {
        this.manager = manager;
        this.viewshka = viewshka;
        this.commands = new Command[]{new Exit(viewshka)};
    }

    public void run() {//TODO да везде надо обвязать команду хелпом и несуществующая команда
        connectToDataBase();
        while (true) {
            while (true) {
                viewshka.wright("Введите команду или 'help' для помощи");
                String command = viewshka.read();//TODO если таблица только одна, в неё заходим сразу

                if (command.equals("list")) { //TODO постараться стандартные условия после ввода команды вывести в метод
                    viewshka.wright(Arrays.toString(tableListOut()));
                } else if (command.equals("help")) {
                    doHelp();
                } else if (commands[0].canProcess(command)) {
                    commands[0].process(command);
                } else if (command.startsWith("find|")) {
                    while (true) {
                        doFind(command);
                        changingTable(tableName);
                        printTable(tableName);
                        viewshka.wright("Продолжить работу? Y/N или 'help' для помощи");
                        String read1 = viewshka.read();
                        if (read1.equals("N")) {
                            viewshka.wright("До скорой встречи!");
                            System.exit(0);
                        } else if (read1.equals("help")) {
                            doHelp();
                        } else if (read1.equals("Y")) {
                            break;
                        } else {
                            viewshka.wright("Несуществующая команда!");
                        }
                    }
                } else {
                    viewshka.wright("Несуществующая команда!");
                }
            }
        }
    }

    private void doFind(String command) {
//        viewshka.wright("Список доступных таблиц:");
//        viewshka.wright(Arrays.toString(tableListOut()));
        viewshka.wright("Ваш выбор:");
        String[] split = command.split("\\|");
        String command1 = split[1];
        viewshka.wright(command1);
        isTableNameRight(command1);
    }

    private void isTableNameRight(String command1) {
        String[] tableList = tableListOut();
        while (true) {
            boolean isTableChoiced = false;
            for (int index = 0; index < tableList.length; index++) {
                if (tableList[index].equals(command1)) {
                    tableName = command1;
                    isTableChoiced = true;
                    break;
                }
            }
            if (isTableChoiced == false) {
                viewshka.wright("Вы неправильно ввели название таблицы");
            } else {
                break;
            }
            viewshka.wright("Список доступных таблиц:");
            viewshka.wright(Arrays.toString(tableListOut()));
            viewshka.wright("Введите название таблицы");
            command1 = viewshka.read();
        }
    }

    private void doHelp() {// TODO можно сделать с параметрами для распечатки вариантов команд
        viewshka.wright("Существующие команды :");
        viewshka.wright("\t 'list'");
        viewshka.wright("\t для получения списка всех таблиц");
        viewshka.wright("\t 'help'");
        viewshka.wright("\t для получения помощи");
        viewshka.wright("\t 'exit'");
        viewshka.wright("\t для выхода из программы");
        viewshka.wright("\t 'find|columnName'");
        viewshka.wright("\t для выбора нужной таблицы");
    }

    private String[] tableListOut() {
        String[] tableNames = manager.getTableNames();
        return tableNames;
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
                    dataSet.put("id", id);

                    String name = viewshka.read();
                    dataSet.put("name", name);

                    String salary = viewshka.read();
                    dataSet.put("salary", salary);

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

    private void connectToDataBase() {
        viewshka.wright("Юзер, привет");

        //connection block
        String baseName;
        while (true) {
            try {
                viewshka.wright("Пожалуйста, введите логин, пароль и имя базы в формате логин|пароль|база ");
                String s = viewshka.read();
                String[] strings = s.split("\\|");
                if (strings.length != 3) {
                    throw new IllegalArgumentException("Неверное количество параметров, разделенных '|' , необходимо 3, а введено: " + strings.length);
                }
                String login = strings[0];
                String parole = strings[1];
                baseName = strings[2];

                manager.connect(login, parole, baseName);
                break;
            } catch (Exception e) {
                connectError(e);
            }
        }

        viewshka.wright("Вы успешно подсоединились к базе данных " + baseName + " !");
    }

    private void connectError(Exception e) {
        String connectMassage =
                e.getClass().getSimpleName() + " : " +
                        e.getMessage(); // инфа полезная разработчику, но не юзеру!
        Throwable cause = e.getCause();
        if (e.getCause() != null) { // если вызвать нулевой e.getCause(), выскочит ексепшин!!!
            connectMassage += "\n" +
                    cause.getClass().getSimpleName() + ":  " + e.getCause().getMessage();
        }
        viewshka.wright("Неудача по причине: \n" + connectMassage);
        viewshka.wright("Пожалуйста, повторите попытку");
    }
}

