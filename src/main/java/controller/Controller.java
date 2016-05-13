package controller;

import comand.*;
import view.Viewshka;
import connectAndCommands.DataBaseManager;

public class Controller {
    private Command[] commands;
    Viewshka viewshka;
    String tableName;

    public Controller(DataBaseManager manager, Viewshka viewshka) {
        this.viewshka = viewshka;
        this.commands = new Command[]{
                new Help(viewshka),
                new Exit(viewshka),
                new Connect(manager, viewshka),
                new IsConnected(manager, viewshka),
                new List(manager, viewshka),
                new Find(manager, viewshka, commands),
                new CreateTable(manager, viewshka),
                new DeleteTable(manager, viewshka),
                new Print(manager, viewshka, tableName, commands),
                new AddStringToTable(manager, viewshka, tableName, commands),
                new Clear_table(manager, viewshka, tableName, commands),
                new Unsupported(viewshka),
        }; //TODO Вы не можете пользоваться командой 'Connect', пока не подсоединились к базе.
    }

    public void run() {
        try {
            runCommands();
        } catch (ExitException e) {
//            do nothing
        }
    }

    protected void runCommands() {
        try {
            greeting(viewshka);
            do {
                String command = viewshka.read();
                if (command == null) {
                    new Exit(viewshka).process("");
                }
                for (Command command1 : commands) {//
                    if (command1.canProcess(command)) {
                        command1.process(command);
                        break;
                    }
                }
            } while (true);
        } catch (IllegalArgumentException e){// если будут проблемы с ексепшинами, они разбираются в самом конце 3 лекции
            viewshka.wright(e.getMessage());
        }
    }

    protected static void greeting(Viewshka viewshka) {
        viewshka.wright("Юзер, привет");
        viewshka.wright("Для входа в базу данных введи команду 'connect|логин|пароль|база' или 'help' для помощи");
    }
}

