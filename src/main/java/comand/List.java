package comand;

import view.Viewshka;
import connectAndCommands.DataBaseManager;

import java.util.Arrays;

public class List implements Command {
    private DataBaseManager manager;
    private Viewshka viewshka;

    public List(DataBaseManager manager, Viewshka viewshka) {

        this.manager = manager;
        this.viewshka = viewshka;
    }

    @Override
    public boolean canProcess(String command) {
        return command.equals("list");
    }

    @Override
    public void process(String command) {
            String[] tableNames = manager.getTableNames();
            viewshka.wright(Arrays.toString(tableNames));
    }
}
