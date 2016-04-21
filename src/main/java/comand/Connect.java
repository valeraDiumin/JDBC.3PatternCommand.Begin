package comand;

import view.Viewshka;
import connectAndCommands.DataBaseManager;

public class Connect implements Command {
    private DataBaseManager manager;
    private Viewshka viewshka;
    private IsConnected isConnected;

    public Connect(DataBaseManager manager, Viewshka viewshka) {

        this.manager = manager;
        this.viewshka = viewshka;
        this.isConnected = new IsConnected(manager, viewshka);
    }

    @Override
    public boolean canProcess(String command) {
        return command.startsWith("connect|");
    }

    @Override
    public void process(String command) {
            try {

                String[] strings = command.split("\\|");
                if (strings.length != 4) {//TODO magic numbers
                    throw new IllegalArgumentException("Неверное количество параметров, разделенных '|' , " +
                            "необходимо 4, а введено: " + strings.length);
                }

                String login = strings[1];
                String parole = strings[2];
                String baseName = strings[3];

                manager.connect(login, parole, baseName);
                viewshka.wright(String.format("К базе '%s' успешно подключились!", baseName));
                viewshka.wright("Можем вызвать список таблиц 'list' или вызвать таблицу 'find|baseName'");
            } catch (Throwable e) {
                connectError(e);
            }
        }

    private void connectError(Throwable e) {
        String connectMassage =
                e.getClass().getSimpleName() + " : " +
                        e.getMessage(); // инфа полезная разработчику, но не юзеру!
        Throwable cause = e.getCause();
//        if (e.getCause() != null) { // если вызвать нулевой e.getCause(), выскочит ексепшин!!!
//            connectMassage += "\n" +
//                    cause.getClass().getSimpleName() + ":  " + e.getCause().getMessage();
//        }
        viewshka.wright("Неудача по причине: \n" + connectMassage);
        viewshka.wright("Пожалуйста, повторите попытку");
    }
}

