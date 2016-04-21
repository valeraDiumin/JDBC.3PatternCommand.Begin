package view;

import java.util.Scanner;

public class Console implements Viewshka {
    @Override
    public String read() {
        Scanner scanner = new Scanner(System.in);
        return scanner.nextLine();
    }

    @Override
    public void wright(String string) {
        System.out.println(string);
    }
}
