package main;

import java.io.IOException;
import java.util.HashMap;
import java.util.Timer;
import java.util.TimerTask;

import org.apache.http.client.ClientProtocolException;

public class OPS {
    HashMap<String, String> config = new HashMap<String, String>();
    int times_retry_login = 5;
    String testUrl = "http://www.v2ex.com/generate_204";
    Timer timer;

    public OPS() {

        MainView.print("Program started.");
        // set work directory here
        MainView.print("Reading configurations...");
        config = Config.loadConfig();

        // set trl here

    }

    public void opsStart() {
        timer = new Timer();
        TimerTask task = new TimerTask() {
            @Override
            public void run() {
                boolean ifLoggedIn = true;

                MainView.print("Checking network status...");

                try {
                    ifLoggedIn = Network.ifLoggedIn(testUrl);
                } catch (Exception e) {
                    e.printStackTrace();
                    MainView.print("Connection FAILED.");
                }
                if (ifLoggedIn)
                    MainView.print("Online.");
                else {
                    while (!ifLoggedIn) {
                        MainView.print("You are offline. Starting login...");
                        try {
                            Network.postLoginInfo(config);
                        } catch (ClientProtocolException e) {
                            // TODO Auto-generated catch block
                            e.printStackTrace();
                        } catch (IOException e) {
                            // TODO Auto-generated catch block
                            e.printStackTrace();
                        }
                    }

                }

            }
        };
        long delay = 0;
        long intevalPeriod = 300 * 1000;
        timer.scheduleAtFixedRate(task, delay, intevalPeriod);
    }

    public void opsCheckNow() {
        this.timer.cancel();
        this.opsStart();

    }

    public void opsPause() {
        try {
            this.timer.cancel();
        } catch (NullPointerException e) {

        }

    }

}
