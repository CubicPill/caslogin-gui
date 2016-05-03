package main;

import java.util.HashMap;

import com.sun.javafx.collections.MappingChange.Map;

//import net.sf.json.JSONObject;
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
        // create a config file here

    }

    public static HashMap<String, String> readConfig() {
        HashMap<String, String> config = new HashMap<String, String>();
        config.put("username", "");
        config.put("password", "");
        config.put("testUrl", "");
        config.put("interval_retry_connection", "");
        config.put("interval_retry_login", "");
        config.put("interval_check_status", "");
        config.put("max_times_retry_login", "");
        // read config from JSON
        return config;

    }

    public static HashMap<String, String> loadConfig() {
        boolean confExist = true;
        // check if conf file exists here
        if (!confExist) {
            newConfig();

            // display LoginView
            // get usn&pwd from LoginView
        }
        return readConfig();

    }

    public static void updateConfig(String username, String passwd) {
        HashMap<String, String> config = new HashMap<String, String>();
        config.put("username", username);
        config.put("password", passwd);
        config.put("testUrl", "");
        config.put("interval_retry_connection", "");
        config.put("interval_retry_login", "");
        config.put("interval_check_status", "");
        config.put("max_times_retry_login", "");
        // update JSON here

    }

    public static void updateConfig(String url, String irc, String irl, String ics, String mtrl) {
        HashMap<String, String> config = new HashMap<String, String>();
        config.put("username", "");
        config.put("password", "");
        config.put("testUrl", url);
        config.put("interval_retry_connection", irc);
        config.put("interval_retry_login", irl);
        config.put("interval_check_status", ics);
        config.put("max_times_retry_login", mtrl);
        // update JSON here

    }
}
