package Comand;

import connectAndCommands.DataBaseManager;

/**
 * Created by 123 on 27.03.2016.
 */
public class IsConnected implements Command {
    private DataBaseManager dataBaseManager;

    public IsConnected(DataBaseManager dataBaseManager) {

        this.dataBaseManager = dataBaseManager;
    }

    @Override
    public boolean canProcess(String command) {
        return true;
    }

    @Override
    public void process(String command) {
        dataBaseManager.isConnect();
    }
}
