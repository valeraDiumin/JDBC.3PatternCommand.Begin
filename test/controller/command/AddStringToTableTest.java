package controller.command;

import comand.AddStringToTable;
import comand.Command;
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
public class AddStringToTableTest {
    private DataBaseManager manager;
    private Command command;
    private Viewshka view;

    @Before
    public void setup() {
        manager = mock(DataBaseManager.class);
        view = mock(Viewshka.class);
        command = new AddStringToTable(manager, view);
    }

    @Test
    public void TestCanProcessAddStringToTableWithParameter() {
        //given
        //when
        boolean process = command.canProcess("createString|");
        //than
        assertTrue(process);
    }

    @Test
    public void TestCanProcessAddStringToTableWithoutParameter() {
        //given
        //when
        boolean process = command.canProcess("createString");
        //than
        assertFalse(process);
    }

    @Test
    public void TestCantProcessNoRecognizedString() {// Тестируем состояние - True/False как результат введения команды
        //given
        //when
        boolean process = command.canProcess("NoRecognized");
        //than
        assertFalse(process);
    }


    @Test
    public void TestAddStringToTableWithWrongParameters() {//TODO it is nessesery to test validation!!!!!!!!
//        when(manager.getTableData("user1")).thenReturn(dataSet);

        boolean process = command.canProcess("createString|user1");


        assertTrue(process);
    }

    private void shouldPrint(String expected) {
        ArgumentCaptor<String> captor = ArgumentCaptor.forClass(String.class);
        verify(view, atLeastOnce()).wright(captor.capture());
        Assert.assertEquals(expected, captor.getAllValues().toString());
    }
}