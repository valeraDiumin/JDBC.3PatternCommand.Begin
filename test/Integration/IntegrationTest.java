package Integration;

import controller.Main;
import org.junit.BeforeClass;
import org.junit.Test;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.io.UnsupportedEncodingException;

import static junit.framework.TestCase.assertEquals;

public class IntegrationTest {

    private static ConfigurableInputStream in;
    private static ByteArrayOutputStream out;

    @BeforeClass
    public static void setup() {
        in = new ConfigurableInputStream();
        out = new ByteArrayOutputStream();///////

        System.setIn(in);
        System.setOut(new PrintStream(out));
    }

    @Test
    public void TestHelp() {
        //given
        in.add("help");
        in.add("exit");
        //when
        Main.main(new String[0]);
        //then
        assertEquals("Юзер, привет\r\n" +
                "Введите команду 'connect|логин|пароль|база' или 'help' для помощи\r\n" +
                "Существующие команды :\r\n" +
                "\t 'connect|логин|пароль|база' -> \r\n" +
                "\t для подключения к базе данных\r\n" +
                "\t 'list' -> \r\n" +
                "\t для получения списка всех таблиц\r\n" +
                "\t 'help' -> \r\n" +
                "\t для получения помощи\r\n" +
                "\t 'exit' -> \r\n" +
                "\t для выхода из программы\r\n" +
                "\t 'find|columnName' -> \r\n" +
                "\t для выбора нужной таблицы\r\n" +
                "До скорой встречи!\r\n", getData());
    }

    public String getData() {
        try {
            return new String(out.toByteArray(), "UTF-8");
        } catch (UnsupportedEncodingException e) {
            return e.getMessage();
        }
    }
}