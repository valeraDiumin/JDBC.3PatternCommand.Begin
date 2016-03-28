package Comand;

import View.Viewshka;
import connectAndCommands.DataBaseManager;

/**
 * Created by 123 on 27.03.2016.
 */
public class Connect implements Command {
    private DataBaseManager manager;
    private Viewshka viewshka;
    private IsConnected isConnected;

    public Connect(DataBaseManager manager, Viewshka viewshka) {

        this.manager = manager;
        this.viewshka = viewshka;
        this.isConnected = new IsConnected(manager, viewshka);
    }

    @Override
    public boolean canProcess(String command) {
        return command.startsWith("connect|");
    }

    @Override
    public void process(String command) {
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

