package controller.command;

import view.Viewshka;

/**
 * Created by 123 on 14.05.2016.
 */
public class FakeView implements Viewshka{

    private String massage = "";

    @Override
    public String read() {
        return null;
    }

    @Override
    public void wright(String massage) {

        this.massage += massage + "\n";
    }

    public String getContent() {
        return massage;
    }
}
