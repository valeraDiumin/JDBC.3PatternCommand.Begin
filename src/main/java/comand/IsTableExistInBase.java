package comand;

import connectAndCommands.DataBaseManager;

public class IsTableExistInBase {

    public boolean isTableNameRight(String command, DataBaseManager manager) {
        String[] comands = command.split("\\|");
        Boolean tableExist = false;
        String[] tableList = manager.getTableNames();
        for (String aTableList : tableList) {
            if (aTableList.equals(comands[1])) {
                tableExist = true;
                break;
            }
            System.out.println(("Вы ввели название несуществующей таблицы"));
        }
        return tableExist;
    }
}
