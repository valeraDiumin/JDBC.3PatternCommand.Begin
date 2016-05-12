package comand;

import view.Viewshka;

public class Exit implements Command {
    private Viewshka viewshka;

    public Exit(Viewshka viewshka) {

        this.viewshka = viewshka;
    }

    @Override
    public boolean canProcess(String command) {//
        return command.equals("exit");
    }//если команда == нулю, ексепшин не будет вызван

    @Override
    public void process(String command) {
        viewshka.wright("До скорой встречи!");
        //System.exit(0); вместо такого выхода выбрасываем ексепшин, который ловится классом ExitException extends RuntimeException
        throw new ExitException();
    }
}
