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
        this.isConnected = new IsConnected();
    }

    @Override
    public boolean canProcess(String command) {
        return !isConnected.canProcess(command);
    }

    @Override
    public void process(String command) {

    }
}
