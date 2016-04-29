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
            result = result.replaceAll("\r", "");
            return result;
        } catch (UnsupportedEncodingException e) {
            return e.getMessage();
        } catch (IOException e) {
            return e.getMessage();
        }
    }

    @Test
    public void TestExit() {
        //given
        in.add("exit");
        //when
        Main.main(new String[0]);
        String expected = "Юзер, привет\n" +
                "Введите команду 'connect|логин|пароль|база' или 'help' для помощи\n" +
                "До скорой встречи!\n";
        //then
        assertEquals(expected, getData());
    }

    @Test
    public void TestHelp() {
        //given
        in.add("help");
        in.add("exit");
        //when
        Main.main(new String[0]);
        //then   // TODO change 'columnNam' to 'tableName'
        String beginningString = "Юзер, привет\n" +
                "Введите команду 'connect|логин|пароль|база' или 'help' для помощи\n" +
                "Существующие команды :\n" +
                "\t 'connect|логин|пароль|база' -> \n" +
                "\t для подключения к базе данных\n" +
                "\t 'list' -> \n" +
                "\t для получения списка всех таблиц\n" +
                "\t 'help' -> \n" +
                "\t для получения помощи\n" +
                "\t 'exit' -> \n" +
                "\t для выхода из программы\n" +
                "\t 'find|columnName' -> \n" +
                "\t для выбора нужной таблицы\n" +
                "До скорой встречи!\n";
        assertEquals(beginningString
                , getData());
    }

    @Test
    public void TestListWithoutConnect() {
        in.add("list");
        in.add("exit");
        Main.main(new String[0]);
        assertEquals("Юзер, привет\n" +
                "Введите команду 'connect|логин|пароль|база' или 'help' для помощи\n" +
                "Вы не можете пользоваться командой 'list', пока не подсоединились к базе. \n" +
                "Введите команду 'connect|' для начала процедуры соединения с базой, \n" +
                "  'exit' для выхода из программы \n" +
                "   или 'help' для помощи\n" +
                "До скорой встречи!\n", getData());
    }

    @Test
    public void TestListWithConnect() {
        //given
        in.add("connect|postgres|postgres|11111111");
        in.add("list");
        in.add("exit");
        //when
        Main.main(new String[0]);
        //then
        assertEquals("Юзер, привет\n" +
                "Введите команду 'connect|логин|пароль|база' или 'help' для помощи\n" +
                "К базе 'postgres' успешно подключились!\n" +
                "Можем вызвать список таблиц 'list' или вызвать таблицу 'find|baseName'\n" +
                "[user1]\n" +
                "До скорой встречи!\n", getData());
    }

    @Test
    public void TestUnsupportedWithoutConnect() { // contents are identical!!!!! DO NOT PASSED - if "DataBaseManager manager = new JDBCDataBaseManager();"
        in.add("Unsupported");
        in.add("exit");
        Main.main(new String[0]);
        assertEquals("Юзер, привет\n" +
                "Введите команду 'connect|логин|пароль|база' или 'help' для помощи\n" +
                "Вы не можете пользоваться командой 'Unsupported', пока не подсоединились к базе. \n" +
                "Введите команду 'connect|' для начала процедуры соединения с базой, \n" +
                "  'exit' для выхода из программы \n" +
                "   или 'help' для помощи\n" +
                "До скорой встречи!\n", getData());
    }

    @Test
    public void TestFindWithoutConnect() { // contents are identical!!!!! DO NOT PASSED - if "DataBaseManager manager = new JDBCDataBaseManager();"
        in.add("find|unsupported");
        in.add("exit");
        Main.main(new String[0]);
        assertEquals("Юзер, привет\n" +
                "Введите команду 'connect|логин|пароль|база' или 'help' для помощи\n" +
                "Вы не можете пользоваться командой 'find|unsupported', пока не подсоединились к базе. \n" +
                "Введите команду 'connect|' для начала процедуры соединения с базой, \n" +
                "  'exit' для выхода из программы \n" +
                "   или 'help' для помощи\n" +
                "До скорой встречи!\n", getData());
    }

    @Test
    public void TestUnsupportedAfterConnect() {
        //given
        in.add("connect|postgres|postgres|11111111");
        in.add("Unsupported");
        in.add("exit");
        //when
        Main.main(new String[0]);
        //then
        assertEquals("Юзер, привет\n" +
                "Введите команду 'connect|логин|пароль|база' или 'help' для помощи\n" +
                "К базе 'postgres' успешно подключились!\n" +
                "Можем вызвать список таблиц 'list' или вызвать таблицу 'find|baseName'\n" +
                "Несуществующая команда!\n" +
                "До скорой встречи!\n", getData());
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
        assertEquals("Юзер, привет\n" +
                        "Введите команду 'connect|логин|пароль|база' или 'help' для помощи\n" +
                        "К базе 'postgres' успешно подключились!\n" +
                        "Можем вызвать список таблиц 'list' или вызвать таблицу 'find|baseName'\n" +
                        "Существующие команды :\n" +
                        "\t 'connect|логин|пароль|база' -> \n" +
                        "\t для подключения к базе данных\n" +
                        "\t 'list' -> \n" +
                        "\t для получения списка всех таблиц\n" +
                        "\t 'help' -> \n" +
                        "\t для получения помощи\n" +
                        "\t 'exit' -> \n" +
                        "\t для выхода из программы\n" +
                        "\t 'find|columnName' -> \n" +
                        "\t для выбора нужной таблицы\n" +
                        "До скорой встречи!\n"
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
        assertEquals("Юзер, привет\n" +
                        "Введите команду 'connect|логин|пароль|база' или 'help' для помощи\n" +
                        "К базе 'postgres' успешно подключились!\n" +
                        "Можем вызвать список таблиц 'list' или вызвать таблицу 'find|baseName'\n" +
                        "[user1]\n" +
                        "До скорой встречи!\n"
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
        assertEquals("Юзер, привет\n" +
                        "Введите команду 'connect|логин|пароль|база' или 'help' для помощи\n" +
                        "К базе 'postgres' успешно подключились!\n" +
                        "Можем вызвать список таблиц 'list' или вызвать таблицу 'find|baseName'\n" +
                        "[user1]\n" +
                        "К базе 'test' успешно подключились!\n" +
                        "Можем вызвать список таблиц 'list' или вызвать таблицу 'find|baseName'\n" +
                        "[user]\n" +
                        "До скорой встречи!\n"
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
        assertEquals("Юзер, привет\n" +
                        "Введите команду 'connect|логин|пароль|база' или 'help' для помощи\n" +
                        "К базе 'postgres' успешно подключились!\n" +
                        "Можем вызвать список таблиц 'list' или вызвать таблицу 'find|baseName'\n" +
                        "[user1]\n" +
                        "Выбрана таблица:\n" +
                        "Таблица 'user1'\n" +
                        "Вы желаете изменить содержание таблицы 'user1' ? Y/N\n" +
                        " Вы желаете очистить таблицу 'user1' перед введением новой информации? Y/N\n" +
                        " Все строки таблицы 'user1' успешно удалены\n" +
                        "Вы хотите ввести новые данные в таблицу 'user1' ? Y/N\n" +
                        "Пожалуйста, введите данные. Построчно введите id, имя, зарплату \n" +
                        "Строка данных успешно добавлена в таблицу 'user1' !\n" +
                        "Вы хотите ввести новые данные в таблицу 'user1' ? Y/N\n" +
                        "Вы желаете посмотреть содержимое всей таблицы 'user1' ? Y/N\n" +
                        "------------------------------------\n" +
                        "|id|name|salary|\n" +
                        "------------------------------------\n" +
                        "| 1 |  Ibragim |  1000000 |\n" +
                        "------------------------------------\n" +
                        "Продолжить работу? Y/'exit'\n" +
                        "До скорой встречи!\n"
                , getData());
    }

    @Test
    public void TestCreateOneStringInTwoTablesInTwoBases() { //тесты, где создаём и распечатываем
        // по 1 строке таблицы, не "падают" при изменении текста команды exit
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
        assertEquals("Юзер, привет\n" +
                        "Введите команду 'connect|логин|пароль|база' или 'help' для помощи\n" +
                        "К базе 'postgres' успешно подключились!\n" +
                        "Можем вызвать список таблиц 'list' или вызвать таблицу 'find|baseName'\n" +
                        "Выбрана таблица:\n" +
                        "Таблица 'user1'\n" +
                        "Вы желаете изменить содержание таблицы 'user1' ? Y/N\n" +
                        " Вы желаете очистить таблицу 'user1' перед введением новой информации? Y/N\n" +
                        " Все строки таблицы 'user1' успешно удалены\n" +
                        "Вы хотите ввести новые данные в таблицу 'user1' ? Y/N\n" +
                        "Пожалуйста, введите данные. Построчно введите id, имя, зарплату \n" +
                        "Строка данных успешно добавлена в таблицу 'user1' !\n" +
                        "Вы хотите ввести новые данные в таблицу 'user1' ? Y/N\n" +
                        "Вы желаете посмотреть содержимое всей таблицы 'user1' ? Y/N\n" +
                        "------------------------------------\n" +
                        "|id|name|salary|\n" +
                        "------------------------------------\n" +
                        "| 1 |  Ibragim |  1000000 |\n" +
                        "------------------------------------\n" +
                        "Продолжить работу? Y/'exit'\n" +
                        "Введите команду\n" +
                        "К базе 'base_russian' успешно подключились!\n" +
                        "Можем вызвать список таблиц 'list' или вызвать таблицу 'find|baseName'\n" +
                        "Выбрана таблица:\n" +
                        "Таблица 'user1'\n" +
                        "Вы желаете изменить содержание таблицы 'user1' ? Y/N\n" +
                        " Вы желаете очистить таблицу 'user1' перед введением новой информации? Y/N\n" +
                        " Все строки таблицы 'user1' успешно удалены\n" +
                        "Вы хотите ввести новые данные в таблицу 'user1' ? Y/N\n" +
                        "Пожалуйста, введите данные. Построчно введите id, имя, зарплату \n" +
                        "Строка данных успешно добавлена в таблицу 'user1' !\n" +
                        "Вы хотите ввести новые данные в таблицу 'user1' ? Y/N\n" +
                        "Вы желаете посмотреть содержимое всей таблицы 'user1' ? Y/N\n" +
                        "------------------------------------\n" +
                        "|id|name|salary|\n" +
                        "------------------------------------\n" +
                        "| 1 |  Diumin |  1000000.0 |\n" +
                        "------------------------------------\n" +
                        "Продолжить работу? Y/'exit'\n" +
                        "До скорой встречи!\n"
                , getData());
    }

    @Test
    public void TestCreateAndPrintTwoStringsInTableAfterConnect() {
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
        in.add("Y");
        in.add("exit");
        Main.main(new String[0]);
        assertEquals("Юзер, привет\n" +
                        "Введите команду 'connect|логин|пароль|база' или 'help' для помощи\n" +
                        "К базе 'postgres' успешно подключились!\n" +
                        "Можем вызвать список таблиц 'list' или вызвать таблицу 'find|baseName'\n" +
                        "[user1]\n" +
                        "Выбрана таблица:\n" +
                        "Таблица 'user1'\n" +
                        "Вы желаете изменить содержание таблицы 'user1' ? Y/N\n" +
                        " Вы желаете очистить таблицу 'user1' перед введением новой информации? Y/N\n" +
                        " Все строки таблицы 'user1' успешно удалены\n" +
                        "Вы хотите ввести новые данные в таблицу 'user1' ? Y/N\n" +
                        "Пожалуйста, введите данные. Построчно введите id, имя, зарплату \n" +
                        "Строка данных успешно добавлена в таблицу 'user1' !\n" +
                        "Вы хотите ввести новые данные в таблицу 'user1' ? Y/N\n" +
                        "Пожалуйста, введите данные. Построчно введите id, имя, зарплату \n" +
                        "Строка данных успешно добавлена в таблицу 'user1' !\n" +
                        "Вы хотите ввести новые данные в таблицу 'user1' ? Y/N\n" +
                        "Вы желаете посмотреть содержимое всей таблицы 'user1' ? Y/N\n" +
                        "------------------------------------\n" +
                        "|id|name|salary|\n" +
                        "------------------------------------\n" +
                        "| 1 |  Ibragim |  1000000 |\n" +
                        " 2 |  Ibragim2 |  10000 |\n" +
                        "------------------------------------\n" +
                        "Продолжить работу? Y/'exit'\n" +
                        "До скорой встречи!\n"
                , getData());
    }

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
        assertEquals("Юзер, привет\n" +
                        "Введите команду 'connect|логин|пароль|база' или 'help' для помощи\n" +
                        "К базе 'postgres' успешно подключились!\n" +
                        "Можем вызвать список таблиц 'list' или вызвать таблицу 'find|baseName'\n" +
                        "[user1]\n" +
                        "Выбрана таблица:\n" +
                        "Таблица 'user1'\n" +
                        "Вы желаете изменить содержание таблицы 'user1' ? Y/N\n" +
                        " Вы желаете очистить таблицу 'user1' перед введением новой информации? Y/N\n" +
                        " Все строки таблицы 'user1' успешно удалены\n" +
                        "Вы хотите ввести новые данные в таблицу 'user1' ? Y/N\n" +
                        "Пожалуйста, введите данные. Построчно введите id, имя, зарплату \n" +
                        "Строка данных успешно добавлена в таблицу 'user1' !\n" +
                        "Вы хотите ввести новые данные в таблицу 'user1' ? Y/N\n" +
                        "Пожалуйста, введите данные. Построчно введите id, имя, зарплату \n" +
                        "Строка данных успешно добавлена в таблицу 'user1' !\n" +
                        "Вы хотите ввести новые данные в таблицу 'user1' ? Y/N\n" +
                        "Вы желаете посмотреть содержимое всей таблицы 'user1' ? Y/N\n" +
                        "Продолжить работу? Y/'exit'\n" +
                        "До скорой встречи!\n"
                , getData());
    }

    @Test
    public void TestConnectWithError() {
        in.add("connect|postgres|post");
        in.add("connect|postgres|postgres|111");
        in.add("exit");
        Main.main(new String[0]);
        assertEquals("Юзер, привет\n" +
                "Введите команду 'connect|логин|пароль|база' или 'help' для помощи\n" +
                "Неудача по причине: \n" +
                "IllegalArgumentException : Неверное количество параметров, разделенных '|' , необходимо 4, а введено: 3\n" +
                "Пожалуйста, повторите попытку\n" +
                "Неудача по причине: \n" +
                "PSQLException : Ошибка при попытке подсоединения.\n" +
                "IOException:  Неверная последовательность UTF-8: байт 2 из 2 не подходит к маске 10xxxxxx: -64\n" +
                "Пожалуйста, повторите попытку\n" +
                "До скорой встречи!\n", getData());
    }

    @Test
    public void TestClearPrintCreatePrintCommands() {
        in.add("connect|postgres|postgres|11111111");
        in.add("clear|user1");
        in.add("N");
        in.add("clear|user1");
        in.add("clear|usr1");
        in.add("clear1");
        in.add("clear|user1");
        in.add("F");
        in.add("clear|user1");
        in.add("Y");
        in.add("print|user1");
        in.add("Y");
        in.add("createString|user14|java|1111111");
        in.add("createString|user1|5|javac|1000000");
        in.add("print|user1");
        in.add("exit");
        Main.main(new String[0]);
        assertEquals("Юзер, привет\n" +
                "Введите команду 'connect|логин|пароль|база' или 'help' для помощи\n" +
                "К базе 'postgres' успешно подключились!\n" +
                "Можем вызвать список таблиц 'list' или вызвать таблицу 'find|baseName'\n" +
                "Вы собираетесь удалить содержимое таблицы:\n" +
                " 'user1'\n" +
                "Вы действительно хотите очистить таблицу 'user1'? 'Y'/'N'\n" +
                "Введите следующую команду\n" +
                "Вы собираетесь удалить содержимое таблицы:\n" +
                " 'user1'\n" +
                "Вы действительно хотите очистить таблицу 'user1'? 'Y'/'N'\n" +
                "Несуществующая команда!\n" +
                "Несуществующая команда!\n" +
                "Вы собираетесь удалить содержимое таблицы:\n" +
                " 'user1'\n" +
                "Вы действительно хотите очистить таблицу 'user1'? 'Y'/'N'\n" +
                "Несуществующая команда!\n" +
                "Вы собираетесь удалить содержимое таблицы:\n" +
                " 'user1'\n" +
                "Вы действительно хотите очистить таблицу 'user1'? 'Y'/'N'\n" +
                " Все строки таблицы 'user1' успешно удалены\n" +
                "------------------------------------\n" +
                "|id|name|salary|\n" +
                "------------------------------------\n" +
                "\n" +
                "------------------------------------\n" +
                "Несуществующая команда!\n" +
                "Неверное количество параметров, разделенных '|' , необходимо 5, а введено: 4\n" +
                "Строка в таблице 'user1'со значениями id = '5', name = 'javac', salary = '1000000' успешно создана!\n" +
                "------------------------------------\n" +
                "|id|name|salary|\n" +
                "------------------------------------\n" +
                "| 5 |  javac |  1000000 |\n" +
                "------------------------------------\n" +
                "До скорой встречи!\n", getData());
    }

    @Test
    public void TestClearError() {
        in.add("connect|postgres|postgres|11111111");
        in.add("cleer|dd|");
        in.add("clear|user");
        in.add("exit");
        Main.main(new String[0]);
        assertEquals("Юзер, привет\n" +
                "Введите команду 'connect|логин|пароль|база' или 'help' для помощи\n" +
                "К базе 'postgres' успешно подключились!\n" +
                "Можем вызвать список таблиц 'list' или вызвать таблицу 'find|baseName'\n" +
                "Несуществующая команда!\n" +
                "Вы собираетесь удалить содержимое таблицы:\n" +
                " 'user'\n" +
                "Вы ввели название несуществующей таблицы\n" +
                "Пожалуйста, введите название существующей таблицы или 'exit' для выхода из программы\n", getData());
    }
    @Test
    public void TestCreateAndDropTable() {
        in.add("connect|postgres|postgres|11111111");
        in.add("list");
        in.add("createTable|test");
        in.add("list");
        in.add("dropTable|test");
        in.add("Y");
        in.add("list");
        in.add("exit");
        Main.main(new String[0]);
        assertEquals("Юзер, привет\n" +
                "Введите команду 'connect|логин|пароль|база' или 'help' для помощи\n" +
                "К базе 'postgres' успешно подключились!\n" +
                "Можем вызвать список таблиц 'list' или вызвать таблицу 'find|baseName'\n" +
                "[user1]\n" +
                " Таблица 'test' успешно создана\n" +
                "[user1, test]\n" +
                "Вы действительно хотите удалить таблицу 'test'? 'Y'/'N'\n" +
                "Таблица 'test' успешно удалена\n" +
                "[user1]\n" +
                "До скорой встречи!\n", getData());
    }
}