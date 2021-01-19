package com.ordazzlesubscription;

import java.io.IOException;

import org.json.JSONObject;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RestController;

import com.paypal.http.HttpResponse;
import com.paypal.http.serializer.Json;
import com.paypal.orders.Order;
import com.paypal.orders.OrdersGetRequest;


@Component
@RestController
public class ProcessOrderDetails extends PayPalClient {

	public Order getOrder(String orderId) throws IOException {

		OrdersGetRequest request = new OrdersGetRequest(orderId);
		HttpResponse<Order> response = client().execute(request);
		// TODO: Save the transaction details in Kill Bill Database.
		System.out.println("Full response body:");
		System.out.println(new JSONObject(new Json().serialize(response.result())).toString(4));
		return response.result();
	}
}