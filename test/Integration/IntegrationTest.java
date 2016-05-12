package Integration;

import controller.Main;
import org.junit.Before;
import org.junit.Test;

import java.io.ByteArrayOutputStream;
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
        }
//        catch (IOException e) {
//            return e.getMessage();
//        }
    }

    @Test
    public void TestExit() {
        //given
        in.add("exit");
        //when
        Main.main(new String[0]);
        String expected = "Юзер, привет\n" +
                "Для входа в базу данных введи команду 'connect|логин|пароль|база' или 'help' для помощи\n" +
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
                "Для входа в базу данных введи команду 'connect|логин|пароль|база' или 'help' для помощи\n" +
                "Существующие команды :\n" +
                "\t -------------------------------------------------------------------------------------------\n" +
                "\t 'connect|логин|пароль|база' ->  для подключения к базе данных\n" +
                "\t -------------------------------------------------------------------------------------------\n" +
                "\t 'list' ->  для получения списка всех таблиц в подключенной базе\n" +
                "\t -------------------------------------------------------------------------------------------\n" +
                "\t 'help' -> для получения помощи\n" +
                "\t -------------------------------------------------------------------------------------------\n" +
                "\t 'clear|tableName' -> для удаления всех строк из таблицы\n" +
                "\t -------------------------------------------------------------------------------------------\n" +
                "\t 'createTable|tableName' -> для создания новой таблицы (с полями id|name|salary)\n" +
                "\t -------------------------------------------------------------------------------------------\n" +
                "\t 'createString|tableName|id|name|salary' -> для создания новых строк в выбранной таблице)\n" +
                "\t -------------------------------------------------------------------------------------------\n" +
                "\t 'dropTable|tableName' -> для удаления таблицы\n" +
                "\t -------------------------------------------------------------------------------------------\n" +
                "\t 'print|tableName' -> для распечатки таблицы из подключенной базы\n" +
                "\t -------------------------------------------------------------------------------------------\n" +
                "\t 'find|columnName' -> для выбора нужной таблицы\n" +
                "\t -------------------------------------------------------------------------------------------\n" +
                "\t 'exit' -> для выхода из программы\n" +
                "\t -------------------------------------------------------------------------------------------\n" +
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
                "Для входа в базу данных введи команду 'connect|логин|пароль|база' или 'help' для помощи\n" +
                "Вы не можете пользоваться командой 'list', пока не подсоединились к базе. \n" +
                "Введите команду 'connect|' для начала процедуры соединения с базой, \n" +
                "  'exit' для выхода из программы \n" +
                "   или 'help' для помощи\n" +
                "До скорой встречи!\n", getData());
    }

    @Test
    public void TestUnsupportedWithoutConnect() { // contents are identical!!!!! DO NOT PASSED - if "DataBaseManager manager = new JDBCDataBaseManager();"
        in.add("Unsupported");
        in.add("exit");
        Main.main(new String[0]);
        assertEquals("Юзер, привет\n" +
                "Для входа в базу данных введи команду 'connect|логин|пароль|база' или 'help' для помощи\n" +
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
                "Для входа в базу данных введи команду 'connect|логин|пароль|база' или 'help' для помощи\n" +
                "Вы не можете пользоваться командой 'find|unsupported', пока не подсоединились к базе. \n" +
                "Введите команду 'connect|' для начала процедуры соединения с базой, \n" +
                "  'exit' для выхода из программы \n" +
                "   или 'help' для помощи\n" +
                "До скорой встречи!\n", getData());
    }
    @Test
    public void TestFindWithConnect() { // contents are identical!!!!! DO NOT PASSED - if "DataBaseManager manager = new JDBCDataBaseManager();"
        in.add("connect|postgres|postgres|11111111");
        in.add("find|unsupported");
        in.add("exit");
        Main.main(new String[0]);
        assertEquals("Юзер, привет\n" +
                "Для входа в базу данных введи команду 'connect|логин|пароль|база' или 'help' для помощи\n" +
                "Вы не можете пользоваться командой 'find|unsupported', пока не подсоединились к базе. \n" +
                "Введите команду 'connect|' для начала процедуры соединения с базой, \n" +
                "  'exit' для выхода из программы \n" +
                "   или 'help' для помощи\n" +
                "До скорой встречи!\n", getData());
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
                        "Для входа в базу данных введи команду 'connect|логин|пароль|база' или 'help' для помощи\n" +
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
        in.add("Y");
        in.add("print|user1");
        in.add("exit");
        //when
        Main.main(new String[0]);
        //then
        assertEquals("Юзер, привет\n" +
                        "Для входа в базу данных введи команду 'connect|логин|пароль|база' или 'help' для помощи\n" +
                        "К базе 'postgres' успешно подключились!\n" +
                        "Можем вызвать список таблиц 'list' или вызвать таблицу 'find|baseName'\n" +
                        "[user1]\n" +
                        "Вы желаете изменить содержание таблицы 'user1' ? Y/N\n" +
                        " Вы желаете очистить таблицу 'user1' перед введением новой информации? Y/N\n" +
                        " Все строки таблицы 'user1' успешно удалены\n" +
                        "Вы хотите ввести новые данные в таблицу 'user1' ? Y/N\n" +
                        "Пожалуйста, введите данные. Построчно введите id, имя, зарплату \n" +
                        "Строка данных успешно добавлена в таблицу 'user1' !\n" +
                        "Вы хотите ввести новые данные в таблицу 'user1' ? Y/N\n" +
                        "Продолжить работу? Y/'exit'\n" +
                        "Введите команду\n" +
                        "Несуществующая команда!\n" +
                        "------------------------------------\n" +
                        "|id|name|salary|\n" +
                        "------------------------------------\n" +
                        "| 1 |  Ibragim |  1000000 |\n" +
                        "------------------------------------\n" +
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
                "Для входа в базу данных введи команду 'connect|логин|пароль|база' или 'help' для помощи\n" +
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
        in.add("createString|user1|1");
        in.add("createString|user1|1|Stas");
        in.add("createString|user1|1|Stas|1000");
        in.add("createString|user1|1|Stas|1000|2");
        in.add("createString|user1|1|Stas|1000|2|Petr");
        in.add("createString|user1|2|Stas|1000|3|Petr|2500");
        in.add("print|user1");
        in.add("clear|user1");
        in.add("Y");
        in.add("exit");
        Main.main(new String[0]);
        assertEquals("Юзер, привет\n" +
                "Для входа в базу данных введи команду 'connect|логин|пароль|база' или 'help' для помощи\n" +
                "К базе 'postgres' успешно подключились!\n" +
                "Можем вызвать список таблиц 'list' или вызвать таблицу 'find|baseName'\n" +
                "Вы действительно хотите удалить все строки таблицы 'user1'? 'Y'/'N'\n" +
                "Несуществующая команда!\n" +
                "Неверное количество параметров, разделенных '|', введено: 4\n" +
                "Строка в таблице 'user1'со значениями id = '1', name = 'Stas', salary = '1000' успешно создана!\n" +
                "Неверное количество параметров, разделенных '|', введено: 6\n" +
                "Неверное количество параметров, разделенных '|', введено: 7\n" +
                "Строка в таблице 'user1'со значениями id = '2', name = 'Stas', salary = '1000' успешно создана!\n" +
                "Строка в таблице 'user1'со значениями id = '3', name = 'Petr', salary = '2500' успешно создана!\n" +
                "------------------------------------\n" +
                "|id|name|salary|\n" +
                "------------------------------------\n" +
                "| 1 |  Stas |  1000 |\n" +
                " 2 |  Stas |  1000 |\n" +
                " 3 |  Petr |  2500 |\n" +
                "------------------------------------\n" +
                "Вы действительно хотите удалить все строки таблицы 'user1'? 'Y'/'N'\n" +
                " Все строки таблицы 'user1' успешно удалены\n" +
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
                "Для входа в базу данных введи команду 'connect|логин|пароль|база' или 'help' для помощи\n" +
                "К базе 'postgres' успешно подключились!\n" +
                "Можем вызвать список таблиц 'list' или вызвать таблицу 'find|baseName'\n" +
                "Несуществующая команда!\n" +
                "Вы ввели название несуществующей таблицы\n" +
                "До скорой встречи!\n", getData());
    }
    @Test
    public void TestCreateAndDropTable() {
        in.add("connect|postgres|postgres|11111111");
        in.add("list");
        in.add("dropTable|user1|gfh");
        in.add("dropTable|user1");
        in.add("N");
        in.add("dropTable|user1");
        in.add("No");
        in.add("dropTable|user1");
        in.add("Y");
        in.add("list");
        in.add("createTable|test|ddd");
        in.add("createTable|test");
        in.add("list");
        in.add("dropTable|test");
        in.add("Y");
        in.add("list");
        in.add("createTable|user1");
        in.add("exit");
        Main.main(new String[0]);
        assertEquals("Юзер, привет\n" +
                "Для входа в базу данных введи команду 'connect|логин|пароль|база' или 'help' для помощи\n" +
                "К базе 'postgres' успешно подключились!\n" +
                "Можем вызвать список таблиц 'list' или вызвать таблицу 'find|baseName'\n" +
                "[user1]\n" +
                "Неверное количество параметров, разделенных '|' , необходимо 2, а введено: 3\n" +
                "Вы действительно хотите удалить таблицу 'user1'? 'Y'/'N'\n" +
                "Введите следующую команду\n" +
                "Вы действительно хотите удалить таблицу 'user1'? 'Y'/'N'\n" +
                "Несуществующая команда!\n" +
                "Вы действительно хотите удалить таблицу 'user1'? 'Y'/'N'\n" +
                "Таблица 'user1' успешно удалена\n" +
                "[]\n" +
                "Неверное количество параметров, разделенных '|' , необходимо 2, а введено: 3\n" +
                " Таблица 'test' успешно создана\n" +
                "[test]\n" +
                "Вы действительно хотите удалить таблицу 'test'? 'Y'/'N'\n" +
                "Таблица 'test' успешно удалена\n" +
                "[]\n" +
                " Таблица 'user1' успешно создана\n" +
                "До скорой встречи!\n", getData());
    }
    @Test
    public void TestClearStrings() {
        in.add("connect|postgres|postgres|11111111");

        in.add("dropTable|user1");
        in.add("Y");
        in.add("createTable|user1");

        in.add("print|user1");
        in.add("createString|user1|2|Stas|1000|3|Petr|2500");
        in.add("print|user1");
        in.add("clear|user1");
        in.add("N");
        in.add("clear|user1");
        in.add("Y");
        in.add("print|user1");
        in.add("exit");

        Main.main(new String[0]);
        assertEquals("Юзер, привет\n" +
                "Для входа в базу данных введи команду 'connect|логин|пароль|база' или 'help' для помощи\n" +
                "К базе 'postgres' успешно подключились!\n" +
                "Можем вызвать список таблиц 'list' или вызвать таблицу 'find|baseName'\n" +
                "Вы действительно хотите удалить таблицу 'user1'? 'Y'/'N'\n" +
                "Таблица 'user1' успешно удалена\n" +
                " Таблица 'user1' успешно создана\n" +
                "------------------------------------\n" +
                "|id|name|salary|\n" +
                "------------------------------------\n" +
                "\n" +
                "------------------------------------\n" +
                "Строка в таблице 'user1'со значениями id = '2', name = 'Stas', salary = '1000' успешно создана!\n" +
                "Строка в таблице 'user1'со значениями id = '3', name = 'Petr', salary = '2500' успешно создана!\n" +
                "------------------------------------\n" +
                "|id|name|salary|\n" +
                "------------------------------------\n" +
                "| 2 |  Stas |  1000 |\n" +
                " 3 |  Petr |  2500 |\n" +
                "------------------------------------\n" +
                "Вы действительно хотите удалить все строки таблицы 'user1'? 'Y'/'N'\n" +
                "Введите следующую команду\n" +
                "Вы действительно хотите удалить все строки таблицы 'user1'? 'Y'/'N'\n" +
                " Все строки таблицы 'user1' успешно удалены\n" +
                "------------------------------------\n" +
                "|id|name|salary|\n" +
                "------------------------------------\n" +
                "\n" +
                "------------------------------------\n" +
                "До скорой встречи!\n", getData());
    }
    @Test
    public void TestClearStringsExceptions() {
        in.add("connect|postgres|postgres|11111111");
        in.add("clear|dd|dd|dd");
        in.add("clear|dd");
        in.add("clear|user1");
        in.add("Y");
        in.add("exit");

        Main.main(new String[0]);
        assertEquals("Юзер, привет\n" +
                "Для входа в базу данных введи команду 'connect|логин|пароль|база' или 'help' для помощи\n" +
                "К базе 'postgres' успешно подключились!\n" +
                "Можем вызвать список таблиц 'list' или вызвать таблицу 'find|baseName'\n" +
                "Неверное количество параметров, разделенных '|' , необходимо 2, а введено: 4. Вводите в формате clear|tableName\n" +
                "Вы ввели название несуществующей таблицы\n" +
                "Вы ввели название несуществующей таблицы\n" +
                "Вы действительно хотите удалить все строки таблицы 'user1'? 'Y'/'N'\n" +
                " Все строки таблицы 'user1' успешно удалены\n" +
                "До скорой встречи!\n", getData());
    }
}