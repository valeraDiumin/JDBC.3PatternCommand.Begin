package Integration;

import java.io.IOException;
import java.io.OutputStream;

public class LogOutputStream extends OutputStream{
    private String log;// это наша консоль

    @Override
    public void write(int b) throws IOException {

        log += String.valueOf((char) b);// в log впечаталось то, что отправили побайтно на консоль
    }

    public String getData() {
        return log;
    }
}
