package controller.command;

import comand.Command;
import comand.Find;
import connectAndCommands.DataBaseManager;
import org.junit.Test;
import org.mockito.Mockito;
import view.Viewshka;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

/**
 * Created by 123 on 16.05.2016.
 */
public class FindTest {
    private Viewshka view = Mockito.mock(Viewshka.class);
    private DataBaseManager manager = Mockito.mock(DataBaseManager.class);
    private Command[] commands;// = Mockito.mock(Command.class);


    @Test
    public void TestCanProcessFindString(){
        //given
        Command command;
        command = new Find(manager, view);
        //when
        boolean exit = command.canProcess("find|");
        //than
        assertTrue(exit);
    }
    @Test
    public void TestCantProcessNoRecognizedString(){// Тестируем состояние - True/False как результат введения команды
        //given
        Command command  = new Find(manager, view);
        //when
        boolean exit = command.canProcess("NoRecognized");
        //than
        assertFalse(exit);
    }
//    @Test//(expected = ExitException.class) - интересное условие!
//    public void TestCanProcessThrowFindException(){//Тестируем поведение
//        given
//        Command command  = new Find(manager, view);
//        when
//        try {
//
//            command.process("find|");
//            fail("Expected ExitException");//Если сюда дошли, то выскочила эта надпись
//        } catch (ExitException e){
//            do nothing
//        }
//        Mockito.verify(view).wright("До скорой встречи!");
//    }
}