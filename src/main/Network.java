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
	enum status {
		ONLINE, OFFLINE, FAILED
	}

	public static boolean ifLoggedIn(String testUrl) throws Exception {
		int status_code = 0;
		CloseableHttpClient httpclient = HttpClients.createDefault();
		HttpGet httpGet = new HttpGet(testUrl);
		CloseableHttpResponse test = null;
		try {
			test = httpclient.execute(httpGet);
			status_code = test.getStatusLine().getStatusCode();
		} finally {
			test.close();
			httpGet.abort();
			httpclient.close();
		}
		if (status_code == 204) // the web page will generate a HTTP 204 code
			return true;
		return false;
	}

	public static void postLoginInfo(HashMap<String, String> config) throws ClientProtocolException, IOException {
		MainView.print("Start to get login information");
		String username = config.get("username");
		String passwd = config.get("password");
		String url = config.get("testUrl");

		// read params from config

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
		int start = 0;
		int end = 0;
		try {
			start = content.indexOf("wlanuserip") + 13;
			end = content.indexOf("&locale=");
			wlanuserip = content.substring(start, end);
			// get param wlanuserip

			start = content.indexOf("wlanacip") + 11;
			end = content.indexOf("%26wlanuserip");
			wlanacip = content.substring(start, end);
			// get param walnacip

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
			// get param JSESSIONID
		} catch (Exception e) {
			MainView.print("Error getting login information.");
			return;
		}

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

	public static status checkNetworkStatus() {
		HashMap<String, String> config = Config.loadConfig();
		String testUrl = config.get("testUrl");
		boolean status = false;
		int ar = 3;
		while (true) {
			ar--;
			try {
				status = ifLoggedIn(testUrl);
				break;
			} catch (Exception e1) {
				if (ar <= 0) {
					MainView.print("Connection FAILED.");
					return Network.status.FAILED;
				}
				MainView.print("Connection FAILED. Retry in " + config.get("interval_retry_connection") + " sec.");

				try {

					Thread.sleep(1000 * Integer.parseInt(config.get("interval_retry_connection")));
				} catch (Exception e2) {
					// do nothing
				}

			}
		}

		if (status) {
			MainView.print("Online.");
			return Network.status.ONLINE;

		} else {
			MainView.print("You are offline.");
			return Network.status.OFFLINE;

		}
	}

}
