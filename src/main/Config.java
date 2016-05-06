package main;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.json.JSONObject;

public class Config {

	public static void newConfig() {
		HashMap<String, String> config = new HashMap<String, String>();
		config.put("username", "");
		config.put("password", "");
		config.put("testUrl", "http://www.v2ex.com/generate_204");
		config.put("interval_retry_connection", "20");
		config.put("interval_retry_login", "10");
		config.put("interval_check_status", "300");
		config.put("max_times_retry_login", "5");
		// create a empty config file here
		JSONObject confJSON = new JSONObject(config);
		try {
			File emptyConf = new File(".\\config.json");
			emptyConf.createNewFile();
			BufferedWriter output = new BufferedWriter(new FileWriter(emptyConf));
			try {
				output.write(confJSON.toString());
				output.flush();

			} finally {
				output.close();
			}
		} catch (Exception e) {

		}
	}

	public static HashMap<String, String> readConfig() {
		HashMap<String, String> config = new HashMap<String, String>();

		String confLine = "";
		try {

			String fileLoc = ".\\config.json";
			File confFile = new File(fileLoc);
			InputStreamReader reader = new InputStreamReader(new FileInputStream(confFile));
			BufferedReader br = new BufferedReader(reader);
			try {
				confLine = br.readLine();
			} finally {
				br.close();
			}

		} catch (Exception e) {
			e.printStackTrace();
		} finally {

		}
		JSONObject confJSON = new JSONObject(confLine);
		config.put("username", confJSON.getString("username"));
		config.put("password", confJSON.getString("password"));
		config.put("testUrl", confJSON.getString("testUrl"));
		config.put("interval_retry_connection", confJSON.getString("interval_retry_connection"));
		config.put("interval_retry_login", confJSON.getString("interval_retry_login"));
		config.put("interval_check_status", confJSON.getString("interval_check_status"));
		config.put("max_times_retry_login", confJSON.getString("max_times_retry_login"));
		return config;

	}

	public static HashMap<String, String> loadConfig() {
		boolean confExist = new File(".\\config.json").exists();
		// check if conf file exists here
		if (!confExist) {
			MainView.print("No configurations found. Create a new one.");
			newConfig();
			MainView.print("Configuration file successfully created.");
			String args[] = {};
			LoginView.main(args);
			// display LoginView
			// get usn&pwd from LoginView
		}
		HashMap<String, String> config = readConfig();
		MainView.print("Configurations successfully imported.");
		return config;

	}

	public static void updateConfig(String username, String passwd) {
		HashMap<String, String> config = new HashMap<String, String>();
		config = readConfig();
		config.put("username", username);
		config.put("password", passwd);
		// update JSON here

		JSONObject confJSON = new JSONObject(config);
		try {
			File initConf = new File(".\\config.json");
			BufferedWriter output = new BufferedWriter(new FileWriter(initConf));
			try {
				output.write(confJSON.toString());
				output.flush();
				MainView.print("Account settings successfully updated.");
			} finally {
				output.close();
			}
		} catch (Exception e) {

		}
	}

	public static void updateConfig(String url, String irc, String irl, String ics, String mtrl) {
		HashMap<String, String> config = new HashMap<String, String>();
		config = readConfig();
		config.put("testUrl", url);
		config.put("interval_retry_connection", irc);
		config.put("interval_retry_login", irl);
		config.put("interval_check_status", ics);
		config.put("max_times_retry_login", mtrl);
		// update JSON here
		JSONObject confJSON = new JSONObject(config);
		try {
			File initConf = new File(".\\config.json");
			BufferedWriter output = new BufferedWriter(new FileWriter(initConf));
			try {
				output.write(confJSON.toString());
				output.flush();
				MainView.print("Preference settings successfully updated.");
			} finally {
				output.close();
			}
		} catch (Exception e) {

		}
	}

	public static boolean validate(String url, String irc, String irl, String ics, String mtrl) {
		Pattern pattern;
		String regexurl = "^(https?://)?([\\da-z\\.-]+)\\.([a-z\\.]{2,6})([/\\w \\.-]*)*/?$";

		pattern = Pattern.compile(regexurl);

		boolean urlv = pattern.matcher(url).matches();
		pattern = Pattern.compile("[0-9]*");
		boolean ircv = pattern.matcher(irc).matches();
		boolean irlv = pattern.matcher(irl).matches();
		boolean icsv = pattern.matcher(ics).matches();
		boolean mtrlv = pattern.matcher(mtrl).matches();

		return (urlv && ircv && irlv && icsv && mtrlv);
	}
}
