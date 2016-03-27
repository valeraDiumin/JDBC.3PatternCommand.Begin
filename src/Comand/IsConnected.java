package Comand;

/**
 * Created by 123 on 27.03.2016.
 */
public class IsConnected implements Command {

    @Override
    public boolean canProcess(String command) {
        return true;
    }

    @Override
    public void process(String command) {

    }
}
