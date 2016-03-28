package Controller;

import Comand.*;
import View.Viewshka;
import connectAndCommands.DataBaseManager;

public class Controller {
    private Command[] commands;
    DataBaseManager manager;
    Viewshka viewshka;
    String tableName;

    public Controller(DataBaseManager manager, Viewshka viewshka) {
        this.manager = manager;
        this.viewshka = viewshka;
        this.commands = new Command[]{
                new Connect(manager, viewshka),
                new Help(viewshka),
                new Exit(viewshka),
                new IsConnected(manager, viewshka),// глотает команды, так как принимает любую команду, и через брейк в начало цикла до себя же
                new List(manager, viewshka),
                new Find(manager, viewshka, tableName, commands),
                new Unsupported(viewshka)
        };
    }

    public void run() {
        while (true) {
            viewshka.wright("Введите команду или 'help' для помощи");
            String command = viewshka.read();
            for (int index = 0; index < commands.length; index++) {
                if (commands[index].canProcess(command)) {
                    commands[index].process(command);
                    break;
                }
            }
        }
    }
}

