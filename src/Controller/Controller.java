package Controller;

import View.Viewshka;
import connectAndCommands.DataBaseManager;
import connectAndCommands.DataSet;

import java.util.Arrays;

public class Controller {
    DataBaseManager manager;
    Viewshka controller;
    String tableName;


    public Controller(DataBaseManager manager, Viewshka controller) {
        this.manager = manager;
        this.controller = controller;
    }

    public void run() {//TODO да везде надо обвязать команду хелпом и несуществующая команда
        connectToDataBase();
        while (true) {
            while (true) {
                controller.wright("Если вы хотите посмотреть список таблиц, введите 'list' или 'help' для помощи");
                String read = controller.read();//TODO если таблица только одна, в неё заходим
                if (read.equals("list")) {
                    tableListOut();
                    break;
                } else if (read.equals("help")) {
                    doHelp();
                } else {
                    controller.wright("Несуществующая команда!");
                }
            }
            this.tableName = selectionTable();
            changingTable(tableName);
            printAllTable(tableName);
            controller.wright("Если вы хотите закончить работу с базой, введите 'S',продолжить работу - 'Y', или 'help' для помощи");
            String read1 = controller.read();
            if (read1.equals("S")) {
                controller.wright("До скорой встречи!");
                System.exit(0);
            } else if (read1.equals("help")) {
                doHelp();
            }

        }
    }

    private void doHelp() {// TODO можно сделать с параметрами для распечатки вариантов команд
        controller.wright("Существующие команды :");
        controller.wright("\t 'list'");
        controller.wright("\t для получения списка всех таблиц");
        controller.wright("\t 'help'");
        controller.wright("\t для получения помощи");
    }

    private void tableListOut() {
        String[] tableNames = manager.getTableNames();
        String massage = Arrays.toString(tableNames);
        controller.wright(massage);
    }

    private void printAllTable(String tableName) {
        controller.wright("Вы желаете посмотреть содержимое всей таблицы '" + tableName + "' ? Y/N");
        String read = controller.read();
        if (read.equals("Y")) {

            //создаём отдельные методы на распечатку заголовков колонок и содержимого в базах данных
            // распечатку делаем с маской - разделителем и с линиями
            printHead(tableName);
            printTable(tableName);
        }
    }

    private void printTable(String tableName) {
        String tableValue = manager.getTableValue(tableName);
        controller.wright(tableValue);
        controller.wright("------------------------------------");
    }

    private void printHead(String tableName) {
        String tableHead = manager.getTableHead(tableName);
        controller.wright("------------------------------------");
        controller.wright(tableHead);
        controller.wright("------------------------------------");
    }

    private void changingTable(String tableName) {
        controller.wright("Вы желаете изменить содержание таблицы '" + tableName + "' ? Y/N");
        String iWishToChangeTable = controller.read(); //TODO если что-то другое кроме Y/N, то поправить и предложить снова ввести.....
        if (iWishToChangeTable.equals("Y")) {

            controller.wright(" Вы желаете очистить таблицу '" + tableName + "' перед введением новой информации? Y/N");
            String answer3 = controller.read();
            if (answer3.equals("Y")) {
                manager.clear(tableName);
                controller.wright(" Все строки таблицы '" + tableName + "' успешно удалены");
            }
            while (true) {
                controller.wright("Вы хотите ввести новые данные в таблицу '" + tableName + "' ? Y/N");//TODO цикл для ввода нескольких строк
                String read2 = controller.read();
                if (read2.equals("Y")) {
                    controller.wright("Пожалуйста, введите данные. Построчно введите id, имя, зарплату ");
                    DataSet dataSet = new DataSet();
                    String id = controller.read();
                    dataSet.put("id", id);

                    String name = controller.read();
                    dataSet.put("name", name);

                    String salary = controller.read();
                    dataSet.put("salary", salary);

                    manager.create(dataSet, tableName);
                    controller.wright("Строка данных успешно добавлена в таблицу '" + tableName + "' !");
                } else if (read2.equals("N")) {
                    break;
                } else {
                    controller.wright("Неправильная команда");
                }
            }
        }
    }

    private String selectionTable() {
        controller.wright("С какой таблицей Вы желаете работать? Пожалуйста, введите название таблицы");
        String tableName = controller.read();//TODO если выбрана несуществующая таблица, надо это указать и предложить опять
        controller.wright("Таблица '" + tableName + "' выбрана для работы");
        return tableName;
    }

    private void connectToDataBase() {
        controller.wright("Юзер, привет");

        //connection block
        String baseName;
        while (true) {
            try {
                controller.wright("Пожалуйста, введите логин, пароль и имя базы в формате логин|пароль|база ");
                String s = controller.read();
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

        controller.wright("Вы успешно подсоединились к базе данных " + baseName + " !");
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
        controller.wright("Неудача по причине: \n" + connectMassage);
        controller.wright("Пожалуйста, повторите попытку");
    }
}

