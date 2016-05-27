package controller.command;

import comand.Command;
import comand.Print;
import connectAndCommands.DataBaseManager;
import connectAndCommands.DataSet;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.ArgumentCaptor;
import view.Viewshka;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.*;
import static org.mockito.internal.verification.VerificationModeFactory.atLeastOnce;


public class PrintTest {
    private DataBaseManager manager;
    private Command command;
    private Viewshka view;

    @Before
    public void setup() {
        manager = mock(DataBaseManager.class);
        view = mock(Viewshka.class);
        command = new Print(manager, view);
    }

    @Test
    public void TestCanProcessFindWithParameter() {
        //given
        //when
        boolean exit = command.canProcess("print|user1");
        //than
        assertTrue(exit);
    }

    @Test
    public void TestCanProcessFindWithoutParameter() {
        //given
        //when
        boolean exit = command.canProcess("print");
        //than
        assertFalse(exit);
    }

    @Test
    public void TestCantProcessNoRecognizedString() {// Тестируем состояние - True/False как результат введения команды
        //given
        //when
        boolean exit = command.canProcess("NoRecognized");
        //than
        assertFalse(exit);
    }

    @Test
    public void TestPrintEmptyTableWithHead() {
        when(manager.getTableHead("user1")).thenReturn(new String[]{"id","name","salary"});

        command.process("print|user1");

            shouldPrint("[------------------------------------, " +
                        "|id|name|salary|, " +
                        "------------------------------------]");
    }

    @Test
    public void TestPrintEmptyTableWithoutHead() {

        when(manager.getTableData("user1")).thenReturn(new DataSet[0]);

        command.process("print|user1");

        shouldPrint("[------------------------------------]");
    }
    @Test
    public void testPrintTableData() {
        // given
        when(manager.getTableHead("user1")).thenReturn(new String[]{"id","name","salary"});//строка даёт данные в принт! принт точно повторяет!
        DataSet dataSet1 = new DataSet();
        dataSet1.putNewString("id", 3);
        dataSet1.putNewString("name", "Jack Bob");
        dataSet1.putNewString("salary", "10000000000000");
        manager.createString(dataSet1, "user1");


        DataSet[] data = new DataSet[] {dataSet1};
        when(manager.getTableData("user1"))
                .thenReturn(data);

        command.process("print|user1");

        // then
        shouldPrint("[------------------------------------, |id|name|salary|, ------------------------------------, \r" +
                "| null, ------------------------------------]");
    }

    @Test
    public void TestPrintTableWithOneString() {//TODO! There no value of column strings in the test!
        when(manager.getTableHead("user1")).thenReturn(new String[]{"id","name","salary"});//строка даёт данные в принт! принт точно повторяет!

        DataSet dataSet1 = new DataSet();
        dataSet1.putNewString("id", 3);
        dataSet1.putNewString("name", "Jack Bob");
        dataSet1.putNewString("salary", "10000000000000");
        manager.createString(dataSet1, "user1");

        DataSet dataSet2 = new DataSet();
        dataSet2.putNewString("id", 4);
        manager.createString(dataSet2, "user1");

        DataSet[] dataSet = new DataSet[]{dataSet1};//, dataSet2};
        when(manager.getTableData("user1")).thenReturn(dataSet);

        command.process("print|user1");


        shouldPrint("[------------------------------------, |id|name|salary|, ------------------------------------, \r" +
                "| null, ------------------------------------]");//TODO I dont know???????????????????
    }

    private void shouldPrint(String expected) {
        ArgumentCaptor<String> captor = ArgumentCaptor.forClass(String.class);
        verify(view, atLeastOnce()).wright(captor.capture());
        Assert.assertEquals(expected, captor.getAllValues().toString());
    }

}