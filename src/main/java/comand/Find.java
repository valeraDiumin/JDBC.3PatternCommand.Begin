package comand;

import view.Viewshka;
import connectAndCommands.DataBaseManager;
import connectAndCommands.DataSet;

public class Find implements Command {
    private DataBaseManager manager;
    private Viewshka viewshka;
    private String tableName;
    private Command[] commands;
    IsTableExistInBase isTableExistInBase;
    private String COMMAND_SAMPLE = "find|tableName";

    public Find(DataBaseManager manager, Viewshka viewshka, Command[] commands) {

        this.manager = manager;
        this.viewshka = viewshka;
        this.commands = commands;
        isTableExistInBase = new IsTableExistInBase();

    }

    @Override
    public boolean canProcess(String command) {
        return command.startsWith("find|");
    }

    @Override
    public void process(String command) {// не может вызваться массив из Контроллера!
        String[] strings = command.split("\\|");
        tableName = strings[1];

        try {
            if (command.split("\\|").length != parametersLength()) {
                throw new IllegalArgumentException(String.format("Неверное количество параметров, разделенных '|' , " +
                        "необходимо %s, а введено: %s. Вводите в формате find|tableName", parametersLength(), command.split("\\|").length));
            }
        } catch (Throwable e) {
            System.out.println(e.getMessage());
        }

        try {
            if (isTableExistInBase.isTableNameRight(command, manager)) {
                changingTable(tableName);
                viewshka.wright("Продолжить работу? Y/'exit'");
                String read1 = viewshka.read();
                if (read1.equals("exit")) {
                    viewshka.wright("До скорой встречи!");
                    throw new ExitException();
                } else if (read1.equals("Y")) {
                    viewshka.wright("Введите команду");

                    //TODO и как мы повторим работу с выбранной таблицей? как реализуем это в подсказках?
                } else {
                    viewshka.wright("Несуществующая команда!");
                }
            }
        } catch (IllegalArgumentException e){
            viewshka.wright(e.getMessage());
        }
    }
    private int parametersLength() {
        return COMMAND_SAMPLE.split("\\|").length;
    }
    private void changingTable(String tableName) {
        viewshka.wright("Вы желаете изменить содержание таблицы '" + tableName + "' ? Y/N");
        String iWishToChangeTable = viewshka.read(); //TODO если что-то другое кроме Y/N, то поправить и предложить снова ввести.....
        if (iWishToChangeTable.equals("Y")) {

            viewshka.wright(" Вы желаете очистить таблицу '" + tableName + "' перед введением новой информации? Y/N");
            String answer3 = viewshka.read();
            if (answer3.equals("Y")) {
                manager.clear(tableName);
                viewshka.wright(" Все строки таблицы '" + tableName + "' успешно удалены");
            }
            while (true) {
                viewshka.wright("Вы хотите ввести новые данные в таблицу '" + tableName + "' ? Y/N");//TODO цикл для ввода нескольких строк
                String read2 = viewshka.read();
                if (read2.equals("Y")) {
                    viewshka.wright("Пожалуйста, введите данные. Построчно введите id, имя, зарплату ");
                    DataSet dataSet = new DataSet();
                    String id = viewshka.read();
                    dataSet.putNewString("id", id);

                    String name = viewshka.read();
                    dataSet.putNewString("name", name);

                    String salary = viewshka.read();
                    dataSet.putNewString("salary", salary);

                    manager.createString(dataSet, tableName);
                    viewshka.wright("Строка данных успешно добавлена в таблицу '" + tableName + "' !");
                    // TODO при ошибке базы
                    // и распечатке стектрейса пишет "Строка данных успешно добавлена в таблицу 'user1' !"???????
                    // не хватает "палочки" в начале второй строки данных таблицы
                    // для завершённости ветки find|tableName не хватает (при отказе от добавления строки выпадет, а если я хочу распечатать таблицу?)
                } else if (read2.equals("N")) {
                    break;
                } else {
                    viewshka.wright("Неправильная команда");
                }
            }
        }
    }
}
