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
        this.commands = new Command[]{new Connect(manager, Viewshka), new Exit(viewshka), new Help(viewshka), new List(manager, viewshka),
                new Find(manager, viewshka, tableName, commands), new Unsupported(viewshka)};
    }

    public void run() {
        connectToDataBase();
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

