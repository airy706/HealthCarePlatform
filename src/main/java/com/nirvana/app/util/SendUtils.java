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

import com.taobao.api.DefaultTaobaoClient;
import com.taobao.api.TaobaoClient;
import com.taobao.api.request.AlibabaAliqinFcSmsNumSendRequest;
import com.taobao.api.response.AlibabaAliqinFcSmsNumSendResponse;

public class SendUtils {
	//发送邮件
	public static boolean send_email(String to,String words) throws IOException {

		final String url = "http://api.sendcloud.net/apiv2/mail/send";
		//final String url = "http://I47fqVkdjPP4vbt04myyF6Ko9mrNcp3K.sendcloud.org/apiv2/mail/send";
		final String apiUser = "initial_wu_test_3iFkXV";
		final String apiKey = "pmjU58ix4G5SBcrK";
		final String rcpt_to = to;

		String subject = "密码找回";
		String html = "验证码："+words;
//相当于一个请求（url）
		HttpPost httpPost = new HttpPost(url);
//http客户端
		HttpClient httpClient = HttpClients.createDefault();
//请求里设置实体，实体内容为NameValuePair,包括账户，密码，发送方，接收方，验证码等
		List<NameValuePair> params = new ArrayList<NameValuePair>();
		params.add(new BasicNameValuePair("apiUser", apiUser));
		params.add(new BasicNameValuePair("apiKey", apiKey));
		params.add(new BasicNameValuePair("to", rcpt_to));
		params.add(new BasicNameValuePair("from", "sendcloud@sendcloud.org"));
		params.add(new BasicNameValuePair("fromName", "SendCloud"));
		params.add(new BasicNameValuePair("subject", subject));
		params.add(new BasicNameValuePair("html", html));

		httpPost.setEntity(new UrlEncodedFormEntity(params, "UTF-8"));
//Http客户端执行请求，返回响应
		HttpResponse response = httpClient.execute(httpPost);

		// 处理响应
		if (response.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
			// 正常返回, 解析返回数据
			System.out.println(EntityUtils.toString(response.getEntity()));
			//关闭这次请求连接
			httpPost.releaseConnection();
			return true;
		} else {
			System.err.println("error");
			httpPost.releaseConnection();
			return false;
		}
		
	}
//发送短信
	public static void send_tel(String usertel, String words) {
		//官网的URL
		String url="http://gw.api.taobao.com/router/rest";
		//成为开发者，创建应用后系统自动生成 
		String appkey="23669124";
		String secret="556385a1418752c5501a3165c0fa8284";
		//短信模板的内容
		String json="{\"code\":\""+words+"\"}";
		//客户端
		TaobaoClient client = new DefaultTaobaoClient(url, appkey, secret);
		//请求
		AlibabaAliqinFcSmsNumSendRequest req = new AlibabaAliqinFcSmsNumSendRequest();
		//请求设置参数
		req.setSmsType("normal");
		req.setSmsFreeSignName("关爱平台100");
		req.setSmsParamString(json);
		req.setRecNum(usertel);
		req.setSmsTemplateCode("SMS_53900206");
		try{
			//执行请求
			AlibabaAliqinFcSmsNumSendResponse rsp = client.execute(req);
			System.out.println(rsp.getBody());
			} catch (Exception e) {
				// TODO: handle exception
				System.err.println("error");
			}
	}
}
