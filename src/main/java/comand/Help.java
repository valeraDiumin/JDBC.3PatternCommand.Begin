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
        viewshka.wright("\t -------------------------------------------------------------------------------------------");
        viewshka.wright("\t 'connect|логин|пароль|база' ->  для подключения к базе данных");
        viewshka.wright("\t -------------------------------------------------------------------------------------------");
        viewshka.wright("\t 'list' ->  для получения списка всех таблиц в подключенной базе");
        viewshka.wright("\t -------------------------------------------------------------------------------------------");
        viewshka.wright("\t 'help' -> для получения помощи");
        viewshka.wright("\t -------------------------------------------------------------------------------------------");
        viewshka.wright("\t 'clear|tableName' -> для удаления всех строк из таблицы");
        viewshka.wright("\t -------------------------------------------------------------------------------------------");
        viewshka.wright("\t 'createTable|tableName' -> для создания новой таблицы (с полями id|name|salary)");
        viewshka.wright("\t -------------------------------------------------------------------------------------------");
        viewshka.wright("\t 'createString|tableName|id|name|salary' -> для создания новых строк в выбранной таблице)");
        viewshka.wright("\t -------------------------------------------------------------------------------------------");
        viewshka.wright("\t 'dropTable|tableName' -> для удаления таблицы");
        viewshka.wright("\t -------------------------------------------------------------------------------------------");
        viewshka.wright("\t 'print|tableName' -> для распечатки таблицы из подключенной базы");
        viewshka.wright("\t -------------------------------------------------------------------------------------------");
        viewshka.wright("\t 'find|columnName' -> для выбора нужной таблицы");
        viewshka.wright("\t -------------------------------------------------------------------------------------------");
        viewshka.wright("\t 'exit' -> для выхода из программы");
        viewshka.wright("\t -------------------------------------------------------------------------------------------");
    }

}
