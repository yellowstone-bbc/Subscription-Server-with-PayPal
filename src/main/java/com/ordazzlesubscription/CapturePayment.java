package com.ordazzlesubscription;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;

import javax.net.ssl.HttpsURLConnection;

import org.springframework.stereotype.Component;

@Component
public class CapturePayment extends PayPalClient {
	public String captureOrder(String orderId) {
		try {
			String bearerToken = getToken();
			String url = "https://api.sandbox.paypal.com/v2/checkout/orders/" + orderId + "/capture";
			URL obj = new URL(url);
			HttpsURLConnection con = (HttpsURLConnection) obj.openConnection();
			con.setRequestMethod("POST");
			con.setRequestProperty("content-type", "application/json");
			con.setRequestProperty("authorization", "Bearer " + bearerToken);
			BufferedReader in = new BufferedReader(
				new InputStreamReader(con.getInputStream()));
			String inputLine;
			StringBuffer response = new StringBuffer();
			while ((inputLine = in.readLine()) != null) {
				response.append(inputLine);
			}
			in.close();
			System.out.println(response.toString());
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "Error Occured";
	}
}