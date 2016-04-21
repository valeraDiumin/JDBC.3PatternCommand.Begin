package view;

import java.util.Scanner;

public class Console implements Viewshka {
    @Override
    public String read() {
//        try { // У Саши if close application (3л - 3 ч 06 мин и 08 мин) выскакивают
//                     NoSuchElementException  & NullPointerException, имплементим
            Scanner scanner = new Scanner(System.in);
            return scanner.nextLine();
//        } catch (NoSuchElementException e) {
//            return null;
//        }
    }

    @Override
    public void wright(String string) {
        System.out.println(string);
    }
}
