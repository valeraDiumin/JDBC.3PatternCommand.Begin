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
        out = new ByteArrayOutputStream();

        System.setIn(in);
        System.setOut(new PrintStream(out));
    }

    public String getData() {
        try {
            return new String(out.toByteArray(), "UTF-8");
        } catch (UnsupportedEncodingException e) {
            return e.getMessage();
        }
    }

    @Test
    public void TestExit() {
        //given
        in.add("exit");
        //when
        Main.main(new String[0]);
        //then
        assertEquals("Юзер, привет\r\n" +
                "Введите команду 'connect|логин|пароль|база' или 'help' для помощи\r\n" +
                "До скорой встречи!\r\n", getData());
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
                "До скорой встречи!\r\n"
                , getData());
    }
    @Test
    public void TestListWithoutConnect() {// content are identical!!!!! DO NOT PASSED - if "DataBaseManager manager = new JDBCDataBaseManager();"
        //given
        in.add("list");
        in.add("exit");
        //when
        Main.main(new String[0]);
        //then
        assertEquals("Юзер, привет\r\n" +
                "Введите команду 'connect|логин|пароль|база' или 'help' для помощи\r\n" +
                "Вы не можете пользоваться командой 'list', пока не подсоединились к базе. \r\n" +
                "Введите команду 'connect|' для начала процедуры соединения с базой, \r\n" +
                "  'exit' для выхода из программы \r\n" +
                "   или 'help' для помощи\r\n" +
                "До скорой встречи!\r\n", getData());
    }
    @Test
    public void TestUnsupported() {
        //given
        in.add("Unsupported");
        in.add("exit");
        //when
        Main.main(new String[0]);
        //then
        assertEquals("Юзер, привет\r\n" +  // content are identical!!!!! DO NOT PASSED
                "Введите команду 'connect|логин|пароль|база' или 'help' для помощи\r\n" +
                "Вы не можете пользоваться базой, пока не подсоединились к ней. \r\n" +
                "Введите команду 'connect|' для начала процедуры соединения с базой, \r\n" +
                "  'exit' для выхода из программы \r\n" +
                "   или 'help' для помощи\r\n" +
                "До скорой встречи!\r\n", getData());
    }

    @Test
    public void TestConnectHelp() {
        //given
        in.add("connect|postgres|postgres|11111111");
//        in.add("help"); да он его просто игнорирует! такой вопрос разбирается на 1-45 лекции 3.
        in.add("exit");
        //when
        Main.main(new String[0]);
        //then
        assertEquals("Юзер, привет\r\n" +
                        "Введите команду 'connect|логин|пароль|база' или 'help' для помощи\r\n" +
                        "К базе '11111111' успешно подключились!\r\n" +
                        "Можем вызвать список таблиц 'list' или вызвать таблицу 'find|baseName'\r\n" +
                        "До скорой встречи!\r\n"
                , getData());
    }
    @Test
    public void TestConnectList() {
        //given
        in.add("connect|postgres|postgres|11111111");
//        in.add("list");
        in.add("exit");
        //when
        Main.main(new String[0]);
        //then
        assertEquals("Юзер, привет\r\n" +
                        "Введите команду 'connect|логин|пароль|база' или 'help' для помощи\r\n" +
                        "К базе '11111111' успешно подключились!\r\n" +
                        "Можем вызвать список таблиц 'list' или вызвать таблицу 'find|baseName'\r\n" +
                        "До скорой встречи!\r\n"
                , getData());
    }

}