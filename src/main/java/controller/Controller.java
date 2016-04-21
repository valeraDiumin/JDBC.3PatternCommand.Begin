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
        greeting(viewshka);
        do {
            String command = viewshka.read();
            for (int index = 0; index < commands.length; index++) {
                if (commands[index].canProcess(command)) {
                    commands[index].process(command);
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

