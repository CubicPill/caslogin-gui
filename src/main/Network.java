package main;

import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;

public class Network {
    private static String testUrl = "http://www.v2ex.com/generate_204";

    public static boolean ifLoggedIn() throws Exception {
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

}
