package Comand;

import View.Viewshka;

/**
 * Created by 123 on 25.03.2016.
 */
public class Exit implements Command {
    Viewshka viewshka;

    public Exit(Viewshka viewshka) {

        this.viewshka = viewshka;
    }

    @Override
    public boolean canProcess(String command) {
        return command.equals("exit");
    }//если команда == нулю, ексепшин не будет вызван

    @Override
    public void process(String command) {
        viewshka.wright("До скорой встречи!");
        System.exit(0);
    }
}
