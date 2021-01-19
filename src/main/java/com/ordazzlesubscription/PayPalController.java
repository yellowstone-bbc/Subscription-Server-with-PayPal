package com.ordazzlesubscription;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.google.gson.Gson;
import com.paypal.http.exceptions.HttpException;
import com.paypal.orders.Order;

@RestController
public class PayPalController {

	@Autowired
	PayPalServices PayPalServices;

	@Autowired
	CapturePayment CapturePayment;

	@PostMapping("/createOrder")
	public String creteOrder() {
		String jsonResponse = "";
		try {
			Order newOrder = PayPalServices.createOrder();
			jsonResponse = new Gson().toJson(newOrder, Order.class);
		} catch (HttpException e) {
			System.out.println(e.getLocalizedMessage());
		} catch (Exception e) {
			e.printStackTrace();
		}
		return jsonResponse;
	}

	@GetMapping("/capture/{orderId}")
	public String capturePayment(@PathVariable("orderId") String orderId) {
		String jsonResponse = "";
		try {
			String response = CapturePayment.captureOrder(orderId);
			jsonResponse = new Gson().toJson(response);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return jsonResponse;
	}

	@GetMapping("/getOrderDetails/{orderId}")
	public String getOrderDetails(@PathVariable("orderId") String orderId) {
		String jsonResponse = "";
		try {
			Order getOrder = PayPalServices.getOrder(orderId);
			jsonResponse = new Gson().toJson(getOrder, Order.class);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return jsonResponse;
	}

	@RequestMapping(value = "/cancel")
	public String cancelPage() {
		return "Cancel Page";
	}

	@RequestMapping(value = "/return")
	public String returnPage() {
		return "Return Page";
	}
	
	@GetMapping("/token")
	public String getOrderDetails() {
		String token = new PayPalClient().getToken();
		return token;
	}
}
