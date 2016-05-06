package main;

import java.io.IOException;
import java.util.HashMap;
import java.util.Timer;
import java.util.TimerTask;

import org.apache.http.client.ClientProtocolException;

public class OPS {
	HashMap<String, String> config = new HashMap<String, String>();
	int times_retry_login = 5;
	String testUrl;
	Timer timer;
	boolean ifLoggedIn = true;
	int time_retry_login = 0;

	public OPS() {

		MainView.print("Program started.");
		// set work directory here
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

				while (!online) {
					MainView.print("Starting login...");
					try {
						Network.postLoginInfo(config);
					} catch (Exception e) {
						MainView.print("An error occurred.");
						return;
					}

					try {
						ifLoggedIn = Network.ifLoggedIn(testUrl);
					} catch (Exception e) {
						// change this
					}

					if (ifLoggedIn) {
						MainView.print("Login Successful. Current user: " + config.get("username"));
						resetmtrl();
					} else {
						while (true) {
							boolean ifRelogin = mtrl();
							if (ifRelogin) {
								MainView.print("Login FAILED.");
								MainView.print(time_retry_login + " attempt(s) remaining. Retry login in "
										+ config.get("interval_retry_login") + " sec.");
								try {
									Thread.sleep(1000 * Integer.parseInt(config.get("interval_retry_login")));
								} catch (NumberFormatException e) {
									// TODO Auto-generated catch block
									e.printStackTrace();
								} catch (InterruptedException e) {
									// TODO Auto-generated catch block
									e.printStackTrace();
								}
								opsStart();
								return;
							} else {
								MainView.print("Login FAILED.");
								MainView.print("Attempts used up. The program must quit in 20 seconds.");
								try {
									Thread.sleep(20 * 1000);
								} catch (InterruptedException e) {
									// TODO Auto-generated catch block
									e.printStackTrace();
								}
								System.exit(0);
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

		}
		this.opsStart();

	}

	public boolean mtrl() {
		time_retry_login--;
		if (time_retry_login > 0)
			return true;
		return false;
	}

	public void resetmtrl() {
		time_retry_login = Integer.parseInt(config.get("max_times_retry_login"));

	}
}
