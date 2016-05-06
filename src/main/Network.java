package main;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
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
			httpGet.abort();
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

		// read params from config here

		String wlanacip = "";
		String wlanuserip = "";

		String lt = "";
		String execution = "";
		String _eventId = "";
		String JSESSIONID_SUSTC = "";

		// setting the proxy for the traffic to be captured by fiddler

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

		start = content.indexOf("name=\"lt\"") + 17;
		end = start + 50;
		lt = content.substring(start, end);
		end = lt.indexOf("\" />");
		lt = lt.substring(0, end);
		// get param lt

		start = content.indexOf("name=\"execution\"") + 24;
		end = start + 10;
		execution = content.substring(start, end);
		end = execution.indexOf("\" />");
		execution = execution.substring(0, end);
		// get param execution

		start = content.indexOf("name=\"_eventId\"") + 23;
		end = start + 10;
		_eventId = content.substring(start, end);
		end = _eventId.indexOf("\" />");
		_eventId = _eventId.substring(0, end);
		// get param _eventId

		JSESSIONID_SUSTC = response.getFirstHeader("Set-Cookie").getValue();
		end = JSESSIONID_SUSTC.indexOf("; Path=/cas/;");
		JSESSIONID_SUSTC = JSESSIONID_SUSTC.substring(11, end);

		// get param lt, execution, _eventid, JSESSIONID_SUSTC here
		MainView.print("Login information acquired.");

		url = "http://weblogin.sustc.edu.cn/cas/login;jsessionid=" + JSESSIONID_SUSTC
				+ "?service=http://enet.10000.gd.cn%3A10001%2Fsz%2Fsz112%2Findex.jsp%3Fwlanacip%3D" + wlanacip
				+ "%26wlanuserip%3D" + wlanuserip;

		HttpPost post = new HttpPost(url);
		post.setHeader("Host", "weblogin.sustc.edu.cn");
		post.setHeader("Cache-Control", "max-age=0");
		post.setHeader("Accept", "text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,*/*;q=0.8");
		post.setHeader("Origin", "http://weblogin.sustc.edu.cn");
		post.setHeader("Upgrade-Insecure-Requests", "1");
		post.setHeader("User-Agent",
				"Mozilla/5.0 (Windows NT 10.0; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/49.0.2623.112 Safari/537.36");
		post.setHeader("Content-Type", "application/x-www-form-urlencoded");
		post.setHeader("DNT", "1");
		post.setHeader("Referer", url);
		post.setHeader("Accept-Encoding", "'gzip, deflate, sdch");
		post.setHeader("Accept-Lanuguage", "en-US,en;q=0.8,zh-CN;q=0.6,zh;q=0.4");

		List<NameValuePair> params = new ArrayList<NameValuePair>();
		params.add(new BasicNameValuePair("username", username));
		params.add(new BasicNameValuePair("password", passwd));
		params.add(new BasicNameValuePair("lt", lt));
		params.add(new BasicNameValuePair("execution", execution));
		params.add(new BasicNameValuePair("_eventId", _eventId));
		params.add(new BasicNameValuePair("submit", "LOGIN"));

		post.setEntity(new UrlEncodedFormEntity(params));
		response = httpclient.execute(post);

		MainView.print("Login information posted to the CAS server.");

		if (response.getStatusLine().getStatusCode() == 302) {
			String locationURL = response.getLastHeader("Location").getValue();
			httpget = new HttpGet(locationURL);
			response = httpclient.execute(httpget);
		}
	}

}
