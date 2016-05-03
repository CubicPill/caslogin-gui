package main;

import java.util.HashMap;

public class Exec {
    public static void main(String args[]) {
        MainView.main(args);
    }

    public static void opsStart() {
        printWithTimeStamp("Program started.");
        // set work directory here
        printWithTimeStamp("Reading configurations...");
        HashMap<String, String> config = new HashMap<String, String>();
        config = Config.loadConfig();
        int times_retry_login = 5;
        String testUrl = "";
        // set trl here
        printWithTimeStamp("Configurations successfully imported.");
        while (true) {
            printWithTimeStamp("Checking network status...");
            boolean ifLoggedIn = true;
            try {
                ifLoggedIn = Network.ifLoggedIn(testUrl);
            } catch (Exception e) {
                printWithTimeStamp("Connection FAILED.");
                continue;
            }
            while (!ifLoggedIn) {
                printWithTimeStamp("You are offline. Starting login...");
                Network.postLoginInfo(config);

            }

        }
    }

    public static void opsCheckNow() {

    }

    public static void opsPause() {

    }

    public static void printWithTimeStamp(String str) {

    }
}