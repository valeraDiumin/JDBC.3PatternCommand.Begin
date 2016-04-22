package comand;

import view.Viewshka;
import connectAndCommands.DataBaseManager;

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
        viewshka.wright(String.format("Вы не можете пользоваться командой '%s', пока не подсоединились к базе. \n" +
                "Введите команду 'connect|' для начала процедуры соединения с базой, \n " +
                " 'exit' для выхода из программы \n" +
                "   или 'help' для помощи", command));
    }
}
