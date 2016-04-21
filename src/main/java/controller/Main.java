package controller;

import connectAndCommands.DataBaseManager;
import connectAndCommands.inMemoryDataBaseManager;
import view.Console;
import view.Viewshka;

public class Main {


    public static void main(String[] args) {
//        DataBaseManager manager = new JDBCDataBaseManager();
        DataBaseManager manager = new inMemoryDataBaseManager();
        Viewshka viewshka = new Console();

        Controller controller = new Controller(manager, viewshka);
        controller.run();
    }
}
