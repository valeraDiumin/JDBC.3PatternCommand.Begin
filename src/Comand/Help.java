package Comand;

import View.Viewshka;

/**
 * Created by 123 on 25.03.2016.
 */
public class Help implements Command{
    private Viewshka viewshka;

    public Help(Viewshka viewshka) {

        this.viewshka = viewshka;
    }

    @Override
    public boolean canProcess(String command) {
        return command.equals("help");
    }

    @Override
    public void process(String command) {
        viewshka.wright("Существующие команды :");
        viewshka.wright("\t 'list'");
        viewshka.wright("\t для получения списка всех таблиц");
        viewshka.wright("\t 'help'");
        viewshka.wright("\t для получения помощи");
        viewshka.wright("\t 'exit'");
        viewshka.wright("\t для выхода из программы");
        viewshka.wright("\t 'find|columnName'");
        viewshka.wright("\t для выбора нужной таблицы");
    }
}
