package controller.command;


import comand.Command;
import comand.Exit;
import comand.ExitException;
import org.junit.Test;

import static junit.framework.Assert.fail;
import static junit.framework.TestCase.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class ExitTest {

   private FakeView view = new FakeView();

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
    public void TestCantProcessNoRecognizedString(){
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
            fail("Expected ExitException");
        } catch (ExitException e){
            //do nothing
        }
        //than
        assertEquals("До скорой встречи!\n", view.getContent());
    }

}
