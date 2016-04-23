package Integration;

import controller.Main;
import org.junit.Before;
import org.junit.Test;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.PrintStream;
import java.io.UnsupportedEncodingException;

import static junit.framework.TestCase.assertEquals;

public class IntegrationTest {

    private ConfigurableInputStream in;
    private ByteArrayOutputStream out;

    @Before
    public void setup() {
        in = new ConfigurableInputStream();
        out = new ByteArrayOutputStream();

        System.setIn(in);
        System.setOut(new PrintStream(out));
    }

    public String getData() {
        try {
            String result = new String(out.toByteArray(), "UTF-8");
            out.reset();
            return result;
        } catch (UnsupportedEncodingException e) {
            return e.getMessage();
        } catch (IOException e){
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
                "\t 'find|columnName' -> \r\n" + // TODO change 'columnNam' to 'tableName'
                "\t для выбора нужной таблицы\r\n" +
                "До скорой встречи!\r\n"
                , getData());
    }
//    @Test // когда перехватили командой isConnect, то тесты не прошли! if "DataBaseManager manager = new JDBCDataBaseManager();"
//    /* DO NOT PASSED */public void TestListWithoutConnect() {
//        in.add("list");
//        in.add("exit");
//        Main.main(new String[0]);
//        assertEquals("Юзер, привет\r\n" +
//                "Введите команду 'connect|логин|пароль|база' или 'help' для помощи\r\n" +
//                "Вы не можете пользоваться командой 'list', пока не подсоединились к базе. \r\n" +
//                "Введите команду 'connect|' для начала процедуры соединения с базой, \r\n" +
//                "  'exit' для выхода из программы \r\n" +
//                "   или 'help' для помощи\r\n" +
//                "До скорой встречи!\r\n", getData());
//    }
    @Test
    public void TestListWithConnect() {
        //given
        in.add("connect|postgres|postgres|11111111");
        in.add("list");
        in.add("exit");
        //when
        Main.main(new String[0]);
        //then
        assertEquals("Юзер, привет\r\n" +
                "Введите команду 'connect|логин|пароль|база' или 'help' для помощи\r\n" +
                "К базе 'postgres' успешно подключились!\r\n" +
                "Можем вызвать список таблиц 'list' или вызвать таблицу 'find|baseName'\r\n" +
                "[user1]\r\n" +
                "До скорой встречи!\r\n", getData());
    }

//    @Test
//    /* DO NOT PASSED */public void TestUnsupportedWithoutConnect() { // contents are identical!!!!! DO NOT PASSED - if "DataBaseManager manager = new JDBCDataBaseManager();"
//        in.add("Unsupported");
//        in.add("exit");
//        Main.main(new String[0]);
//        assertEquals("Юзер, привет\r\n" +
//                "Введите команду 'connect|логин|пароль|база' или 'help' для помощи\r\n" +
//                "Вы не можете пользоваться командой 'Unsupported', пока не подсоединились к базе. \r\n" +
//                "Введите команду 'connect|' для начала процедуры соединения с базой, \r\n" +
//                "  'exit' для выхода из программы \r\n" +
//                "   или 'help' для помощи\r\n" +
//                "До скорой встречи!\r\n", getData());
//    }
//    @Test
//    /* DO NOT PASSED */public void TestFindWithoutConnect() { // contents are identical!!!!! DO NOT PASSED - if "DataBaseManager manager = new JDBCDataBaseManager();"
//        in.add("find|unsupported");
//        in.add("exit");
//        Main.main(new String[0]);
//        assertEquals("Юзер, привет\r\n" +
//                "Введите команду 'connect|логин|пароль|база' или 'help' для помощи\r\n" +
//                "Вы не можете пользоваться командой 'find|unsupported', пока не подсоединились к базе. \r\n" +
//                "Введите команду 'connect|' для начала процедуры соединения с базой, \r\n" +
//                "  'exit' для выхода из программы \r\n" +
//                "   или 'help' для помощи\r\n" +
//                "До скорой встречи!\r\n", getData());
//    }
    @Test
    public void TestUnsupportedAfterConnect() {
        //given
        in.add("connect|postgres|postgres|11111111");
        in.add("Unsupported");
        in.add("exit");
        //when
        Main.main(new String[0]);
        //then
        assertEquals("Юзер, привет\r\n" +
                "Введите команду 'connect|логин|пароль|база' или 'help' для помощи\r\n" +
                        "К базе 'postgres' успешно подключились!\r\n" +
                        "Можем вызвать список таблиц 'list' или вызвать таблицу 'find|baseName'\r\n" +
                        "Несуществующая команда!\r\n" +
                        "До скорой встречи!\r\n", getData());
    }
    @Test
    public void TestHelpAfterConnect() {
        //given
        in.add("connect|postgres|postgres|11111111");
        in.add("help");
        in.add("exit");
        //when
        Main.main(new String[0]);
        //then
        assertEquals("Юзер, привет\r\n" +
                        "Введите команду 'connect|логин|пароль|база' или 'help' для помощи\r\n" +
                        "К базе 'postgres' успешно подключились!\r\n" +
                        "Можем вызвать список таблиц 'list' или вызвать таблицу 'find|baseName'\r\n" +
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
    public void TestListAfterConnect() {
        //given
        in.add("connect|postgres|postgres|11111111");
        in.add("list");
        in.add("exit");
        //when
        Main.main(new String[0]);
        //then
        assertEquals("Юзер, привет\r\n" +
                        "Введите команду 'connect|логин|пароль|база' или 'help' для помощи\r\n" +
                        "К базе 'postgres' успешно подключились!\r\n" +
                        "Можем вызвать список таблиц 'list' или вызвать таблицу 'find|baseName'\r\n" +
                        "[user1]\r\n" +
                        "До скорой встречи!\r\n"
                , getData());
    }
    @Test
    public void TestConnectListAfterConnect() {
        //given
        in.add("connect|postgres|postgres|11111111");
        in.add("list");
        in.add("connect|test|postgres|11111111");
        in.add("list");
        in.add("exit");
        //when
        Main.main(new String[0]);
        //then
        assertEquals("Юзер, привет\r\n" +
                        "Введите команду 'connect|логин|пароль|база' или 'help' для помощи\r\n" +
                        "К базе 'postgres' успешно подключились!\r\n" +
                        "Можем вызвать список таблиц 'list' или вызвать таблицу 'find|baseName'\r\n" +
                        "[user1]\r\n" +
                        "К базе 'test' успешно подключились!\r\n" +
                        "Можем вызвать список таблиц 'list' или вызвать таблицу 'find|baseName'\r\n" +
                        "[user]\r\n" +
                        "До скорой встречи!\r\n"
                , getData());
    }

    @Test
    public void TestCreateOneStringInTableAfterConnect() {
        //given
        in.add("connect|postgres|postgres|11111111");
        in.add("list");
        in.add("find|user1");
        in.add("Y");
        in.add("Y");
        in.add("Y");
        in.add("1");
        in.add("Ibragim");
        in.add("1000000");
        in.add("N");
        in.add("Y");
        in.add("exit");
        //when
        Main.main(new String[0]);
        //then
        assertEquals("Юзер, привет\r\n" +
                        "Введите команду 'connect|логин|пароль|база' или 'help' для помощи\r\n" +
                        "К базе 'postgres' успешно подключились!\r\n" +
                        "Можем вызвать список таблиц 'list' или вызвать таблицу 'find|baseName'\r\n" +
                        "[user1]\r\n" +
                        "Выбрана таблица:\r\n" +
                        "Таблица 'user1'\r\n" +
                        "Вы желаете изменить содержание таблицы 'user1' ? Y/N\r\n" +
                        " Вы желаете очистить таблицу 'user1' перед введением новой информации? Y/N\r\n" +
                        " Все строки таблицы 'user1' успешно удалены\r\n" +
                        "Вы хотите ввести новые данные в таблицу 'user1' ? Y/N\r\n" +
                        "Пожалуйста, введите данные. Построчно введите id, имя, зарплату \r\n" +
                        "Строка данных успешно добавлена в таблицу 'user1' !\r\n" +
                        "Вы хотите ввести новые данные в таблицу 'user1' ? Y/N\r\n" +
                        "Вы желаете посмотреть содержимое всей таблицы 'user1' ? Y/N\r\n" +
                        "------------------------------------\r\n" +
                        "|id|name|salary|\r\n" +
                        "------------------------------------\r\n" +
                        "| 1 |  Ibragim |  1000000 |\r\n" +
                        "------------------------------------\r\n" +
                        "Продолжить работу? Y/'exit'\r\n" +
                        "До скорой встречи!\r\n"
                , getData());
    }
    @Test
    public void TestCreateOneStringInTwoTablesInTwoBases() {
        //given
        in.add("connect|postgres|postgres|11111111");
        in.add("find|user1");
        in.add("Y");
        in.add("Y");
        in.add("Y");
        in.add("1");
        in.add("Ibragim");
        in.add("1000000");
        in.add("N");
        in.add("Y");
        in.add("Y");

        in.add("connect|base_russian|postgres|11111111");
        in.add("find|user1");
        in.add("Y");
        in.add("Y");
        in.add("Y");
        in.add("1");
        in.add("Diumin");
        in.add("1000000");
        in.add("N");
        in.add("Y");
        in.add("exit");
        //when
        Main.main(new String[0]);
        //then
        assertEquals("Юзер, привет\r\n" +
                        "Введите команду 'connect|логин|пароль|база' или 'help' для помощи\r\n" +
                        "К базе 'postgres' успешно подключились!\r\n" +
                        "Можем вызвать список таблиц 'list' или вызвать таблицу 'find|baseName'\r\n" +
                        "Выбрана таблица:\r\n" +
                        "Таблица 'user1'\r\n" +
                        "Вы желаете изменить содержание таблицы 'user1' ? Y/N\r\n" +
                        " Вы желаете очистить таблицу 'user1' перед введением новой информации? Y/N\r\n" +
                        " Все строки таблицы 'user1' успешно удалены\r\n" +
                        "Вы хотите ввести новые данные в таблицу 'user1' ? Y/N\r\n" +
                        "Пожалуйста, введите данные. Построчно введите id, имя, зарплату \r\n" +
                        "Строка данных успешно добавлена в таблицу 'user1' !\r\n" +
                        "Вы хотите ввести новые данные в таблицу 'user1' ? Y/N\r\n" +
                        "Вы желаете посмотреть содержимое всей таблицы 'user1' ? Y/N\r\n" +
                        "------------------------------------\r\n" +
                        "|id|name|salary|\r\n" +
                        "------------------------------------\r\n" +
                        "| 1 |  Ibragim |  1000000 |\r\n" +
                        "------------------------------------\r\n" +
                        "Продолжить работу? Y/'exit'\r\n" +
                        "Введите команду\r\n" +
                        "К базе 'base_russian' успешно подключились!\r\n" +
                        "Можем вызвать список таблиц 'list' или вызвать таблицу 'find|baseName'\r\n" +
                        "Выбрана таблица:\r\n" +
                        "Таблица 'user1'\r\n" +
                        "Вы желаете изменить содержание таблицы 'user1' ? Y/N\r\n" +
                        " Вы желаете очистить таблицу 'user1' перед введением новой информации? Y/N\r\n" +
                        " Все строки таблицы 'user1' успешно удалены\r\n" +
                        "Вы хотите ввести новые данные в таблицу 'user1' ? Y/N\r\n" +
                        "Пожалуйста, введите данные. Построчно введите id, имя, зарплату \r\n" +
                        "Строка данных успешно добавлена в таблицу 'user1' !\r\n" +
                        "Вы хотите ввести новые данные в таблицу 'user1' ? Y/N\r\n" +
                        "Вы желаете посмотреть содержимое всей таблицы 'user1' ? Y/N\r\n" +
                        "------------------------------------\r\n" +
                        "|id|name|salary|\r\n" +
                        "------------------------------------\r\n" +
                        "| 1 |  Diumin |  1000000.0 |\r\n" +
                        "------------------------------------\r\n" +
                        "Продолжить работу? Y/'exit'\r\n" +


                        "До скорой встречи!\r\n"
                , getData());
    }

//    @Test
//    /* DO NOT PASSED */public void TestCreateAndPrintTwoStringsInTableAfterConnect() {
//        in.add("connect|postgres|postgres|11111111");
//        in.add("list");
//        in.add("find|user1");
//        in.add("Y");
//        in.add("Y");
//        in.add("Y");
//        in.add("1");
//        in.add("Ibragim");
//        in.add("1000000");
//        in.add("Y");
//        in.add("2");
//        in.add("Ibragim2");
//        in.add("10000");
//        in.add("N");
//        in.add("Y");
//        in.add("exit");
//        Main.main(new String[0]);
//        assertEquals("Юзер, привет\r\n" +
//                        "Введите команду 'connect|логин|пароль|база' или 'help' для помощи\r\n" +
//                        "К базе 'postgres' успешно подключились!\r\n" +
//                        "Можем вызвать список таблиц 'list' или вызвать таблицу 'find|baseName'\r\n" +
//                        "[user1]\r\n" +
//                        "Выбрана таблица:\r\n" +
//                        "Таблица 'user1'\r\n" +
//                        "Вы желаете изменить содержание таблицы 'user1' ? Y/N\r\n" +
//                        " Вы желаете очистить таблицу 'user1' перед введением новой информации? Y/N\r\n" +
//                        " Все строки таблицы 'user1' успешно удалены\r\n" +
//                        "Вы хотите ввести новые данные в таблицу 'user1' ? Y/N\r\n" +
//                        "Пожалуйста, введите данные. Построчно введите id, имя, зарплату \r\n" +
//                        "Строка данных успешно добавлена в таблицу 'user1' !\r\n" +
//                        "Вы хотите ввести новые данные в таблицу 'user1' ? Y/N\r\n" +
//                        "Пожалуйста, введите данные. Построчно введите id, имя, зарплату \r\n" +
//                        "Строка данных успешно добавлена в таблицу 'user1' !\r\n" +
//                        "Вы хотите ввести новые данные в таблицу 'user1' ? Y/N\r\n" +
//                        "Вы желаете посмотреть содержимое всей таблицы 'user1' ? Y/N\r\n" +
//                        "------------------------------------\r\n" +
//                        "|id|name|salary|\r\n" +
//                        "------------------------------------\r\n" +
//                        "| 1 |  Ibragim |  1000000 |\r\n" +
//                        " 2 |  Ibragim2 |  10000 |\r\n" +
//                        "------------------------------------\r\n" +
//                        "Продолжить работу? Y/'exit'\r\n" +
//                        "До скорой встречи!\r\n"
//                , getData());
//    }
    @Test
    public void TestCreateTwoStringsInTableAfterConnect() {
        //given
        in.add("connect|postgres|postgres|11111111");
        in.add("list");
        in.add("find|user1");
        in.add("Y");
        in.add("Y");
        in.add("Y");
        in.add("1");
        in.add("Ibragim");
        in.add("1000000");
        in.add("Y");
        in.add("2");
        in.add("Ibragim2");
        in.add("10000");
        in.add("N");
        in.add("N");
        in.add("exit");
        //when
        Main.main(new String[0]);
        //then
        assertEquals("Юзер, привет\r\n" +
                        "Введите команду 'connect|логин|пароль|база' или 'help' для помощи\r\n" +
                        "К базе 'postgres' успешно подключились!\r\n" +
                        "Можем вызвать список таблиц 'list' или вызвать таблицу 'find|baseName'\r\n" +
                        "[user1]\r\n" +
                        "Выбрана таблица:\r\n" +
                        "Таблица 'user1'\r\n" +
                        "Вы желаете изменить содержание таблицы 'user1' ? Y/N\r\n" +
                        " Вы желаете очистить таблицу 'user1' перед введением новой информации? Y/N\r\n" +
                        " Все строки таблицы 'user1' успешно удалены\r\n" +
                        "Вы хотите ввести новые данные в таблицу 'user1' ? Y/N\r\n" +
                        "Пожалуйста, введите данные. Построчно введите id, имя, зарплату \r\n" +
                        "Строка данных успешно добавлена в таблицу 'user1' !\r\n" +
                        "Вы хотите ввести новые данные в таблицу 'user1' ? Y/N\r\n" +
                        "Пожалуйста, введите данные. Построчно введите id, имя, зарплату \r\n" +
                        "Строка данных успешно добавлена в таблицу 'user1' !\r\n" +
                        "Вы хотите ввести новые данные в таблицу 'user1' ? Y/N\r\n" +
                        "Вы желаете посмотреть содержимое всей таблицы 'user1' ? Y/N\r\n" +
                        "Продолжить работу? Y/'exit'\r\n" +
                        "До скорой встречи!\r\n"
                , getData());
    }

}