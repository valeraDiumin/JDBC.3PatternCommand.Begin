package javaRunWinControlApl;

import java.io.BufferedReader;
import java.io.InputStreamReader;

public class Main {
    public static void main(String[] args) throws Exception {
        String cmd = "ping.exe ukr.net";
        Runtime run = Runtime.getRuntime();
        Process pr = run.exec(cmd);
        pr.waitFor();
        BufferedReader buf = new BufferedReader(new InputStreamReader(pr.getInputStream()));
        String line;
        while ((line = buf.readLine())!=null)
        {
            System.out.println(line);//祭
        }

    }
}
