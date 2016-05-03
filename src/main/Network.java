package main;

import java.util.HashMap;

import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;

public class Network {
    private static String testUrlg = "http://www.v2ex.com/generate_204";

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

    public static void postLoginInfo(HashMap<String, String> config){
        Exec.printWithTimeStamp('Start to get login information');
        String username="";
        String passwd="";
        // read params from config here
        
        String wlanacip="";
        String wlanuserip="";
        String url="";
        // first find param wlanacip & wlanuserip in response body of testUrl (it will be the login page)

        url="http://weblogin.sustc.edu.cn/cas/login?service=http%3A%2F%2Fenet.10000.gd.cn%3A10001%2Fsz%2Fsz112%2Findex.jsp%3Fwlanacip%3D"+wlanacip+"%26wlanuserip%3D"+wlanuserip;
        String lt="";
        String execution="";
        String _eventId="";
        String JSESSIONID_SUSTC="";
        // get param lt, execution, _eventid, JSESSIONID_SUSTC here
        Exec.printWithTimeStamp('Login information acquired.');

        url="http://weblogin.sustc.edu.cn/cas/login;jsessionid="+JSESSIONID_SUSTC+"?service=http://enet.10000.gd.cn%3A10001%2Fsz%2Fsz112%2Findex.jsp%3Fwlanacip%3D"+wlanacip+"%26wlanuserip%3D"+wlanuserip;
        String data="username="+username+"&password="+passwd+"&lt="+lt+"&execution="+execution+"&_eventId="+_eventId+"&submit=LOGIN";

        // post login info here
        
    }

}
