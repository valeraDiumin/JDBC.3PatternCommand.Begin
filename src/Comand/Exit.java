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
    public boolean canProcess(String comand) {
        return comand.equals("exit");
    }

    @Override
    public void process(String comand) {
        viewshka.wright("До скорой встречи!");
        System.exit(0);
    }
}
