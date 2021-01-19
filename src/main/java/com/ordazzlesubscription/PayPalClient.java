package com.ordazzlesubscription;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.nio.charset.StandardCharsets;

import javax.net.ssl.HttpsURLConnection;

import org.apache.tomcat.util.codec.binary.Base64;
import org.json.JSONObject;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RestController;

import com.paypal.core.PayPalEnvironment;
import com.paypal.core.PayPalHttpClient;

@Component
@RestController
public class PayPalClient {

	public final String clientId = "AW_Tg41yMgbot0LSsNAFiK_b1CLJx9rea8Bx0cxC_P-JdqpJn3mxBtEyT5m6sVqCQGZCChMFxfAPPlOd";
	public final String clientSecret = "EMwI86fWi8nD_o0arXNoHC2cKt6hpAjhzj8Ea2RQQljU7LGRjT7agdYOk0j66muh-jYvIxHsh1kP91Uv";
	public final String baseUrl = "https://api.sandbox.paypal.com";

	private PayPalEnvironment paypalEnv = new PayPalEnvironment.Sandbox(clientId, clientSecret);

	PayPalHttpClient client = new PayPalHttpClient(paypalEnv);

	public PayPalHttpClient client() {
		return this.client;
	}

	public String createToken() {
		try {
			String url = baseUrl + "/v1/oauth2/token";
			URL obj = new URL(url);
			HttpsURLConnection con = (HttpsURLConnection) obj.openConnection();
			con.setRequestMethod("POST");
			con.setRequestProperty("accept", "application/json");
			con.setRequestProperty("accept-language", "en_US");
			con.setRequestProperty("content-type", "application/x-www-form-urlencoded");
			String auth = clientId + ":" + clientSecret;
			byte[] encodedAuth = Base64.encodeBase64(auth.getBytes(StandardCharsets.UTF_8));
			con.setRequestProperty("authorization", "Basic" + encodedAuth);
			String body = "grant_type=client_credentials";
			// Send request
			con.setDoOutput(true);
			DataOutputStream wr = new DataOutputStream(con.getOutputStream());
			wr.writeBytes(body);
			wr.flush();
			wr.close();

			BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
			String inputLine;
			StringBuffer response = new StringBuffer();
			while ((inputLine = in.readLine()) != null) {
				response.append(inputLine);
			}
			in.close();
			String access_token = new JSONObject(response).getString("access_token");
			return access_token;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	public String getToken() {
		try {
			String url = baseUrl + "/v1/oauth2/token";
			URL obj = new URL(url);
			HttpsURLConnection con = (HttpsURLConnection) obj.openConnection();
			con.setRequestMethod("POST");
			con.setRequestProperty("accept", "application/json");
			con.setRequestProperty("accept-language", "en_US");
			con.setRequestProperty("content-type", "application/x-www-form-urlencoded");
			String auth = clientId + ":" + clientSecret;
			byte[] encodedAuth = Base64.encodeBase64(auth.getBytes(StandardCharsets.UTF_8));
			String authHeaderValue = "Basic " + new String(encodedAuth);
			con.setRequestProperty("Authorization", authHeaderValue);
			String body = "grant_type=client_credentials";
			con.setDoOutput(true);

			DataOutputStream wr = new DataOutputStream(con.getOutputStream());
			wr.writeBytes(body);
			wr.flush();
			wr.close();

			BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
			String inputLine;
			StringBuffer response = new StringBuffer();
			while ((inputLine = in.readLine()) != null) {
				response.append(inputLine);
			}
			in.close();
			String access_token = new JSONObject(response.toString()).getString("access_token");
			System.out.println("access_token : " + access_token);
			return access_token;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "PAYPAL-Bearer-Token:A21AALnxm3Nor3uNAZP5buG_BISRaz0rY6LIxmSWMnF-DQqoTvkCiJmbqb7dZ26Gah3fAXouQPfU9I78HoxCqE1oZ0pw5TAQA";
	}

}

/*
 * Detailed code for Implementing the above sdk function.
 * 
 * @author : bobby-chakravarthy-b
 * 
 * @Bean public void getAccessToken() {
 * 
 * String user =
 * "AW_Tg41yMgbot0LSsNAFiK_b1CLJx9rea8Bx0cxC_P-JdqpJn3mxBtEyT5m6sVqCQGZCChMFxfAPPlOd";
 * String password =
 * "EMwI86fWi8nD_o0arXNoHC2cKt6hpAjhzj8Ea2RQQljU7LGRjT7agdYOk0j66muh-jYvIxHsh1kP91Uv";
 * 
 * try { String url = "https://api.sandbox.paypal.com/v1/oauth2/token"; URL obj
 * = new URL(url); HttpsURLConnection con = (HttpsURLConnection)
 * obj.openConnection(); con.setRequestMethod("POST");
 * con.setRequestProperty("accept", "application/json");
 * con.setRequestProperty("accept-language", "en_US");
 * con.setRequestProperty("content-type", "application/x-www-form-urlencoded");
 * String auth = user + ":" + password; byte[] encodedAuth =
 * Base64.encodeBase64(auth.getBytes(StandardCharsets.UTF_8)); String
 * authHeaderValue = "Basic " + new String(encodedAuth);
 * con.setRequestProperty("Authorization", authHeaderValue); String body =
 * "grant_type=client_credentials";
 * 
 * // Send request con.setDoOutput(true); DataOutputStream wr = new
 * DataOutputStream(con.getOutputStream()); wr.writeBytes(body); wr.flush();
 * wr.close();
 * 
 * BufferedReader in = new BufferedReader( new
 * InputStreamReader(con.getInputStream())); String inputLine; StringBuffer
 * response = new StringBuffer(); while ((inputLine = in.readLine()) != null) {
 * response.append(inputLine); } in.close();
 * 
 * String jsonString = response.toString(); JSONObject obj1 = new
 * JSONObject(jsonString); String access_token = obj1.getString("access_token");
 * 
 * System.out.println(access_token);
 * 
 * // Print the response System.out.println(response.toString()); } catch
 * (Exception e) { e.printStackTrace(); }
 * 
 * }
 */