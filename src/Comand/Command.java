package Comand;

/**
 * Created by 123 on 25.03.2016.
 */
public interface Command {
    boolean canProcess(String comand);

    void process(String comand);
}
