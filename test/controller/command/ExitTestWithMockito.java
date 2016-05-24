package controller.command;

import comand.Command;
import comand.Exit;
import comand.ExitException;
import org.junit.Test;
import view.Viewshka;

import static junit.framework.Assert.fail;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

/**
 * Created by 123 on 16.05.2016.
 */
public class ExitTestWithMockito {
    private Viewshka view = mock(Viewshka.class);

    @Test
    public void TestCanProcessExitString(){
        //given
        Command command  = new Exit(view);
        //when
        boolean exit = command.canProcess("exit");
        //than
        assertTrue(exit);
    }
    @Test
    public void TestCantProcessNoRecognizedString(){// Тестируем состояние - True/False как результат введения команды
        //given
        Command command  = new Exit(view);
        //when
        boolean exit = command.canProcess("NoRecognized");
        //than
        assertFalse(exit);
    }
    @Test//(expected = ExitException.class) - интересное условие!
    public void TestCanProcessThrowExitException(){//Тестируем поведение
        //given
        Command command  = new Exit(view);
        //when
        try {

            command.process("exit");
            fail("Expected ExitException");//Если сюда дошли, то выскочила эта надпись
        } catch (ExitException e){
            //do nothing
        }
        verify(view).wright("До скорой встречи!");
    }

}
