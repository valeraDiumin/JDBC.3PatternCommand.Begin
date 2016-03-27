package Comand;

import View.Viewshka;

/**
 * Created by 123 on 27.03.2016.
 */
public class Unsupported implements Command {
    private Viewshka viewshka;

    public Unsupported(Viewshka viewshka) {
        this.viewshka = viewshka;
    }

    @Override
    public boolean canProcess(String command) {
        return true;
    }

    @Override
    public void process(String command) {
        viewshka.wright("Несуществующая команда!");
    }
}
