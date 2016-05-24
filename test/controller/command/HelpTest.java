package controller.command;

import comand.Command;
import comand.Help;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.ArgumentCaptor;
import view.Viewshka;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.internal.verification.VerificationModeFactory.atLeastOnce;

/**
 * Created by 123 on 16.05.2016.
 */
public class HelpTest {
    private Command command;
    private Viewshka view;

    @Before
    public void setup() {
        view = mock(Viewshka.class);
        command = new Help(view);
    }

    @Test
    public void TestCanProcessHelp() {
        //given
        //when
        boolean exit = command.canProcess("help");
        //than
        assertTrue(exit);
    }

    @Test
    public void TestCanProcessHelpNoRecognized() {//State testing
        //given
        //when
        boolean exit = command.canProcess("NoRecognized");
        //than
        assertFalse(exit);
    }

    @Test
    public void TestPrintTableWithOneString() {//Behaviour testing

        command.process("help");

        shouldPrint("[Существующие команды :, \t --------------------------------------------------------------------" +
                "-----------------------, \t 'connect|логин|пароль|база' ->  для подключения к базе данных, " +
                "\t -------------------------------------------------------------------------------------------, " +
                "\t 'list' ->  для получения списка всех таблиц в подключенной базе, " +
                "\t -------------------------------------------------------------------------------------------, " +
                "\t 'help' -> для получения помощи, " +
                "\t -------------------------------------------------------------------------------------------, " +
                "\t 'clear|tableName' -> для удаления всех строк из таблицы, " +
                "\t -------------------------------------------------------------------------------------------, " +
                "\t 'createTable|tableName' -> для создания новой таблицы (с полями id|name|salary), " +
                "\t -------------------------------------------------------------------------------------------, " +
                "\t 'createString|tableName|id|name|salary' -> для создания новых строк в выбранной таблице), " +
                "\t -------------------------------------------------------------------------------------------, " +
                "\t 'dropTable|tableName' -> для удаления таблицы, " +
                "\t -------------------------------------------------------------------------------------------, " +
                "\t 'print|tableName' -> для распечатки таблицы из подключенной базы, " +
                "\t -------------------------------------------------------------------------------------------, " +
                "\t 'find|columnName' -> для выбора нужной таблицы, " +
                "\t -------------------------------------------------------------------------------------------, " +
                "\t 'exit' -> для выхода из программы, " +
                "\t -------------------------------------------------------------------------------------------]");
    }

    private void shouldPrint(String expected) {
        ArgumentCaptor<String> captor = ArgumentCaptor.forClass(String.class);
        verify(view, atLeastOnce()).wright(captor.capture());
        Assert.assertEquals(expected, captor.getAllValues().toString());
    }

}