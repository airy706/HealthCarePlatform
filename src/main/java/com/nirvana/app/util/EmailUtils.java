package com.nirvana.app.util;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;

public class EmailUtils {
	public static boolean send_common(String to,String words) throws IOException {

		final String url = "http://api.sendcloud.net/apiv2/mail/send";
		//final String url = "http://I47fqVkdjPP4vbt04myyF6Ko9mrNcp3K.sendcloud.org/apiv2/mail/send";
		final String apiUser = "initial_wu_test_3iFkXV";
		final String apiKey = "pmjU58ix4G5SBcrK";
		final String rcpt_to = to;

		String subject = "密码找回";
		String html = "验证码："+words;

		HttpPost httpPost = new HttpPost(url);
		HttpClient httpClient = HttpClients.createDefault();

		List<NameValuePair> params = new ArrayList<NameValuePair>();
		params.add(new BasicNameValuePair("apiUser", apiUser));
		params.add(new BasicNameValuePair("apiKey", apiKey));
		params.add(new BasicNameValuePair("to", rcpt_to));
		params.add(new BasicNameValuePair("from", "sendcloud@sendcloud.org"));
		params.add(new BasicNameValuePair("fromName", "SendCloud"));
		params.add(new BasicNameValuePair("subject", subject));
		params.add(new BasicNameValuePair("html", html));

		httpPost.setEntity(new UrlEncodedFormEntity(params, "UTF-8"));

		HttpResponse response = httpClient.execute(httpPost);

		// 处理响应
		if (response.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
			// 正常返回, 解析返回数据
			System.out.println(EntityUtils.toString(response.getEntity()));
			httpPost.releaseConnection();
			return true;
		} else {
			System.err.println("error");
			httpPost.releaseConnection();
			return false;
		}
		
	}
}
