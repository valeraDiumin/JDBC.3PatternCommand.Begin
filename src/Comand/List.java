package Comand;

import View.Viewshka;
import connectAndCommands.DataBaseManager;

import java.util.Arrays;

/**
 * Created by 123 on 27.03.2016.
 */
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
    public void process(String command) { // у нас есть возвращаемое значение. Как его впихнуть?
            String[] tableNames = manager.getTableNames();
            viewshka.wright(Arrays.toString(tableNames));
    }
}
