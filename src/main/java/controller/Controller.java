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
                new Connect(manager, viewshka),
                new Help(viewshka),
                new Exit(viewshka),
                new IsConnected(manager, viewshka),
                new List(manager, viewshka),
                new Find(manager, viewshka, tableName, commands),
                new Unsupported(viewshka)
        };
    }

    public void run() {
        try {
            doWork();
        } catch (ExitException e) {
            //do nothing
        }
    }

    protected void doWork() {
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
    }

    protected static void greeting(Viewshka viewshka) {
        viewshka.wright("Юзер, привет");
        viewshka.wright("Введите команду 'connect|логин|пароль|база' или 'help' для помощи");
    }
}

