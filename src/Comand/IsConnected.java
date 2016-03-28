package Comand;

import View.Viewshka;
import connectAndCommands.DataBaseManager;

/**
 * Created by 123 on 27.03.2016.
 */
public class IsConnected implements Command {
    private DataBaseManager dataBaseManager;
    private Viewshka viewshka;

    public IsConnected(DataBaseManager dataBaseManager, Viewshka viewshka) {

        this.dataBaseManager = dataBaseManager;
        this.viewshka = viewshka;
    }

    @Override
    public boolean canProcess(String command) {
        return !dataBaseManager.isConnect();
    }

    @Override
    public void process(String command) {
        viewshka.wright("Вы не можете пользоваться базой, пока не подсоединились к ней. \n Введите conect| для начала процедуры подключения");
    }
}
