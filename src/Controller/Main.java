package Controller;

import View.Console;
import View.Viewshka;
import connectAndCommands.DataBaseManager;
import connectAndCommands.JDBCDataBaseManager;
import connectAndCommands.inMemoryDataBaseManager;

/**
 * Created by 123 on 16.03.2016.
 */
public class Main {


    public static void main(String[] args) {// создали менеджер и вьюху, которые используем, потом Контроллер, куда их уложили в конструктор,
        DataBaseManager jdbcDataBaseManager = new JDBCDataBaseManager();
        DataBaseManager inMemoryDataBaseManager = new inMemoryDataBaseManager();
        Viewshka viewshka = new Console();

        Controller controller = new Controller(jdbcDataBaseManager, viewshka);
//        Controller controller = new Controller(jdbcDataBaseManager, viewshka);
        controller.run(); // запускаем ран, и вуаля!!!
    }
}
