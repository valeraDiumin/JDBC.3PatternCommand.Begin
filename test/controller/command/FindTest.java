package controller.command;

import comand.Command;
import comand.Find;
import connectAndCommands.DataBaseManager;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.ArgumentCaptor;
import view.Viewshka;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.*;

/**
 * Created by 123 on 16.05.2016.
 */
public class FindTest {
    private DataBaseManager manager;
    private Command command;
    private Viewshka view;

    @Before
    public void setup() {
        manager = mock(DataBaseManager.class);
        view = mock(Viewshka.class);
        command = new Find(manager, view);
    }

    @Test
    public void TestCanProcessFindWithParameter() {
        //given
        //when
        boolean exit = command.canProcess("find|user1");
        //than
        assertTrue(exit);
    }

    @Test
    public void TestCanProcessFindWithoutParameter() {
        //given
        //when
        boolean exit = command.canProcess("find");
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


//    @Test
//    public void Test() {
//        given
//        when
//        when(manager.getTableHead("user1")).thenReturn(new String[]{"id", "name", "salary"});
//
//        DataSet dataSet1 = new DataSet();
//        dataSet1.putNewString("id", 3);
//        dataSet1.putNewString("name", "Jack Bob");
//        dataSet1.putNewString("salary", "1000000");
//        manager.createString(dataSet1, "user1");
//
//
//        DataSet dataSet2 = new DataSet();
//        dataSet2.putNewString("id", 4);
//        dataSet2.putNewString("name", "Jack Bob0");
//        dataSet2.putNewString("salary", "1");
//        manager.createString(dataSet2, "user1");
//
//        DataSet[] dataSet = new DataSet[]{dataSet1, dataSet2};
//        when(manager.getTableData("user1")).thenReturn(dataSet);
//
//        command.process("print|user1");
//
//
//        shouldPrint("     j");
//    }
//    @Test
//    public void testPrintEmptyTableData() {
//         given
//        when(manager.getTableHead("user1"))
//                .thenReturn(new String[]{"id", "name", "password"});
//
//        when(manager.getTableData("user")).thenReturn(new DataSet[0]);
//
//         when
//        command.process("find|user");
//
//         then
//        shouldPrint("[--------------------, " +
//                "|id|name|password|, " +
//                "--------------------, " +
//                "--------------------]");
//    }
//

    private void shouldPrint(String expected) {
        ArgumentCaptor<String> captor = ArgumentCaptor.forClass(String.class);
        verify(view, atLeastOnce()).wright(captor.capture());
        Assert.assertEquals(expected, captor.getAllValues().toString());
    }
}