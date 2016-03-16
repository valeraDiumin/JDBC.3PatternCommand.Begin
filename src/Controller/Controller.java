package Controller;

import View.Console;
import View.Viewshka;
import connectAndCommands.DataSet;
import connectAndCommands.JDBCDataBaseManager;
import connectAndCommands.inMemoryDataBaseManager;

import java.sql.SQLException;
import java.util.Arrays;

/**
 * Created by 123 on 15.03.2016.
 */
public class Controller {
    public static void main(String[] args) {
        JDBCDataBaseManager jdbcDataBaseManager = new JDBCDataBaseManager();
        inMemoryDataBaseManager inMemoryDataBaseManager = new inMemoryDataBaseManager();
        DataSet dataSet = new DataSet();
        Viewshka controller = new Console();

        controller.wright("Юзер, привет");

        //connection block
        String baseName;
        while (true) {
            controller.wright("Пожалуйста, введите логин, пароль и имя базы в формате логин|пароль|база ");
            String s = controller.read();
            String[] strings = s.split("\\|");
            String login = strings[0];
            String parole = strings[1];
            baseName = strings[2];
            try {
                jdbcDataBaseManager.connect(login, parole, baseName);
                break;
            } catch (SQLException e) {
                controller.wright("Подключение к базе данных " + baseName + " не состоялось :(");
            }
        }
        controller.wright("Вы успешно подсоединились к базе данных " + baseName + " !");


        //preparing to changing block
        controller.wright("С какой таблицей Вы желаете работать? Пожалуйста, введите название таблицы");
        String tableName = controller.read();
        controller.wright("Таблица '" + tableName + "' выбрана для работы");


        //changing block
        controller.wright("Вы желаете изменить содержание таблицы '" + tableName + "' ? Y/N");
        String iWishToChangeTable = controller.read();
        if (iWishToChangeTable.equals("Y")) {

            controller.wright(" Вы желаете очистить таблицу '" + tableName + "' перед введением новой информации? Y/N");
            String answer3 = controller.read();
            if (answer3.equals("Y")) {
                jdbcDataBaseManager.clear(tableName);
                controller.wright(" Все строки таблицы '" + tableName + "' успешно удалены");
            }
            controller.wright("Вы хотите ввести новые данные в таблицу '" + tableName + "' ? Y/N");
            String iWishToInputNewData = controller.read();
            if (iWishToInputNewData.equals("Y")) {
                controller.wright("Пожалуйста, введите данные. Построчно введите id, имя, зарплату ");
                String id = controller.read();
                dataSet.put("id", id);

                String name = controller.read();
                dataSet.put("name", name);

                String salary = controller.read();
                dataSet.put("salary", salary);

                jdbcDataBaseManager.create(dataSet, tableName);
                controller.wright("Строка данных успешно добавлена в таблицу '" + tableName + "' !");
            }
        }

        controller.wright("Вы желаете посмотреть содержимое всей таблицы '" + tableName + "' ? Y/N");
        String read = controller.read();
        if (read.equals("Y")) {
            DataSet[] dataSet1 = jdbcDataBaseManager.getTableData(tableName);

            for (int strings = 0; strings < dataSet1.length; strings++) {//проходим по всем строкам

                System.out.println(Arrays.toString(dataSet1[strings].getColumnNames()));
                System.out.println(Arrays.toString(dataSet1[strings].getColumnValues()));
                System.out.println("");

            }
            controller.wright("Содержимое таблицы '" + tableName + "' показано");
        }
    }
}

