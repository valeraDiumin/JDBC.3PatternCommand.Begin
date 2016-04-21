package comand;

import view.Viewshka;

public class Help implements Command {
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
        viewshka.wright("\t 'connect|логин|пароль|база' -> ");
        viewshka.wright("\t для подключения к базе данных");
        viewshka.wright("\t 'list' -> ");
        viewshka.wright("\t для получения списка всех таблиц");
        viewshka.wright("\t 'help' -> ");
        viewshka.wright("\t для получения помощи");
        viewshka.wright("\t 'exit' -> ");
        viewshka.wright("\t для выхода из программы");
        viewshka.wright("\t 'find|columnName' -> ");
        viewshka.wright("\t для выбора нужной таблицы");
    }
}
