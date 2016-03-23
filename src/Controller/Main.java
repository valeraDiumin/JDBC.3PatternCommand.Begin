package Controller;

import View.Console;
import View.Viewshka;
import connectAndCommands.DataBaseManager;
import connectAndCommands.inMemoryDataBaseManager;

/**
 * Created by 123 on 16.03.2016.
 */
public class Main {


    public static void main(String[] args) {// создали менеджер и вьюху, которые используем, потом Контроллер, куда их уложили в конструктор,
//        DataBaseManager manager = new JDBCDataBaseManager();
        DataBaseManager manager = new inMemoryDataBaseManager();
        Viewshka viewshka = new Console();

        Controller controller = new Controller(manager, viewshka);
        controller.run(); // запускаем ран, и вуаля!!!
    } // TODO если в таблице new inMemoryDataBaseManager() нет данных, пишет ексепшин!!!
}
