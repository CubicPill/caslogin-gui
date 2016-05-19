package main;

import java.util.HashMap;
import java.util.Timer;
import java.util.TimerTask;

public class OPS {
    HashMap<String, String> config = new HashMap<String, String>();
    String testUrl;
    Timer timer;
    boolean ifLoggedIn = true;
    int time_retry_login = 0;

    public OPS() {
        MainView.print("Program started.");
        MainView.print("Reading configurations...");
        config = Config.loadConfig();
        MainView.print("Configurations successfully imported.");
        time_retry_login = Integer.parseInt(config.get("max_times_retry_login"));
        testUrl = config.get("testUrl");
    }

    public void opsStart() {
        MainView.setTitle(true);
        timer = new Timer();
        TimerTask task = new TimerTask() { // set timer task

            @Override
            public void run() {
                boolean online = false;

                MainView.print("Checking network status...");

                switch (Network.checkNetworkStatus()) {
                case ONLINE:
                    online = true;
                    MainView.print("Re-check status in " + config.get("interval_check_status") + " sec.");
                    break;
                case OFFLINE:
                    online = false;
                    break;
                case FAILED:
                    MainView.print("Error checking network status. Please try again later.");
                    MainView.setTitle(false);
                    return;
                default:
                    break;
                }

                while (!online) { // offline. need to login.
                    MainView.print("Starting login...");
                    try {
                        Network.postLoginInfo(config);
                    } catch (Exception e) { // network errors
                        MainView.print("An error occurred.");
                        MainView.setTitle(false);
                        return;
                    }

                    MainView.print("Re-checking network status...");

                    switch (Network.checkNetworkStatus()) {
                    case ONLINE:
                        online = true;
                        break;
                    case OFFLINE:
                        online = false;
                        break;
                    case FAILED:
                        MainView.print("Error checking network status. Please try again later.");
                        MainView.setTitle(false);
                        return;
                    default:
                        break;
                    }
                    if (online) {
                        MainView.print("Login Successful. Current user: " + config.get("username"));
                        resetmtrl();
                        MainView.print("Attempts reseted to  " + config.get("max_times_retry_login"));
                    } else {
                        while (true) {
                            boolean ifRelogin = mtrl();
                            if (ifRelogin) {
                                MainView.print("Login FAILED.");
                                MainView.print(time_retry_login + " attempt(s) remaining. Retry login in "
                                        + config.get("interval_retry_login") + " sec.");
                                try {
                                    Thread.sleep(1000 * Integer.parseInt(config.get("interval_retry_login")));
                                } catch (Exception e) {

                                }
                                opsStart();
                                return;
                            } else {
                                MainView.print("Login FAILED.");
                                MainView.print("Attempts used up. Please check your account information.");
                                MainView.setTitle(false);
                                return;
                            }
                        }
                    }
                }

            } // end timer task
        };
        long delay = 0;
        long intevalPeriod = 300 * 1000;
        timer.scheduleAtFixedRate(task, delay, intevalPeriod);
    }

    public void opsCheckNow() {
        try {
            this.timer.cancel();
        } catch (NullPointerException e) {
            // do nothing
        }
        this.opsStart();
    }

    public boolean mtrl() { // determine if the login attempt will continue
        time_retry_login--;
        if (time_retry_login > 0)
            return true;
        return false;
    }

    public void resetmtrl() { // reset time_retry_login
        time_retry_login = Integer.parseInt(config.get("max_times_retry_login"));

    }
}
