package main;

import java.io.IOException;
import java.util.HashMap;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;

public class Network {

    public static boolean ifLoggedIn(String testUrl) throws Exception {
        int status_code = 0;
        CloseableHttpClient httpclient = HttpClients.createDefault();
        HttpGet httpGet = new HttpGet(testUrl);

        try {
            CloseableHttpResponse test = httpclient.execute(httpGet);
            try {
                status_code = test.getStatusLine().getStatusCode();

            } finally {
                test.close();
            }
        } finally {

            httpclient.close();

        }
        if (status_code == 204)
            return true;
        return false;
    }

    public static void postLoginInfo(HashMap<String, String> config) throws ClientProtocolException, IOException {
        MainView.print("Start to get login information");
        String username = config.get("username");
        String passwd = config.get("password");
        String url = config.get("testUrl");
        int irc = Integer.parseInt(config.get("interval_retry_connection"));
        int irl = Integer.parseInt(config.get("interval_retry_login"));
        int ics = Integer.parseInt(config.get("interval_check_status"));
        int mtlr = Integer.parseInt(config.get("max_times_retry_login"));
        // read params from config here

        String wlanacip = "";
        String wlanuserip = "";
        // first find param wlanacip & wlanuserip in response body of testUrl (it will be the login page)
        CloseableHttpClient httpclient = HttpClients.createDefault();
        HttpGet httpget = new HttpGet(url);
        HttpResponse response = httpclient.execute(httpget);
        HttpEntity entity = response.getEntity();
        String content = EntityUtils.toString(entity);
        int start = content.indexOf("wlanuserip") + 13;
        int end = content.indexOf("&locale=");
        if (start != 12)
            wlanuserip = content.substring(start, end);
        start = content.indexOf("wlanacip") + 11;
        end = content.indexOf("%26wlanuserip");
        if (start != 10)
            wlanacip = content.substring(start, end);
        

        // MainView.print(wlanuserip);
        
        url = "http://weblogin.sustc.edu.cn/cas/login?service=http%3A%2F%2Fenet.10000.gd.cn%3A10001%2Fsz%2Fsz112%2Findex.jsp%3Fwlanacip%3D"
                + wlanacip + "%26wlanuserip%3D" + wlanuserip;
        String lt = "";
        String execution = "";
        String _eventId = "";
        String JSESSIONID_SUSTC = "";
        // get param lt, execution, _eventid, JSESSIONID_SUSTC here
        MainView.print("Login information acquired.");

        url = "http://weblogin.sustc.edu.cn/cas/login;jsessionid=" + JSESSIONID_SUSTC
                + "?service=http://enet.10000.gd.cn%3A10001%2Fsz%2Fsz112%2Findex.jsp%3Fwlanacip%3D" + wlanacip
                + "%26wlanuserip%3D" + wlanuserip;
        String data = "username=" + username + "&password=" + passwd + "&lt=" + lt + "&execution=" + execution
                + "&_eventId=" + _eventId + "&submit=LOGIN";

        // post login info here

    }

}
