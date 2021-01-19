package com.ordazzlesubscription;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

import javax.net.ssl.HttpsURLConnection;

import org.apache.tomcat.util.codec.binary.Base64;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RestController;

import com.paypal.http.HttpResponse;
import com.paypal.http.serializer.Json;
import com.paypal.orders.AddressPortable;
import com.paypal.orders.AmountBreakdown;
import com.paypal.orders.AmountWithBreakdown;
import com.paypal.orders.ApplicationContext;
import com.paypal.orders.Item;
import com.paypal.orders.LinkDescription;
import com.paypal.orders.Money;
import com.paypal.orders.Name;
import com.paypal.orders.Order;
import com.paypal.orders.OrderRequest;
import com.paypal.orders.OrdersCreateRequest;
import com.paypal.orders.OrdersGetRequest;
import com.paypal.orders.PurchaseUnitRequest;
import com.paypal.orders.ShippingDetail;

@Component
@RestController
public class PayPalServices extends PayPalClient {

	@Value("${paypal.baseurl}") // paypal.baseurl=https://api.sandbox.paypal.com
	public String baseUrl;

	@Value("${paypal.clientId}")
	public String clientId;

	@Value("${paypal.secret}")
	public String clientSecret;

	@Value("${application.url}") // Changes after deployment
	public String appUrl;

	public Order getOrder(String orderId) throws IOException {

		OrdersGetRequest request = new OrdersGetRequest(orderId);
		HttpResponse<Order> response = client().execute(request);
		// TODO: Save the transaction details in Kill Bill Database.
		System.out.println("Full response body:");
		System.out.println(new JSONObject(new Json().serialize(response.result())).toString(4));
		return response.result();
	}

	public Order createOrder() throws IOException {
		OrdersCreateRequest request = new OrdersCreateRequest();
		request.header("prefer", "return=representation");
		request.requestBody(buildRequestBody());
		System.out.println(request);

		HttpResponse<Order> response = client().execute(request);
		if (response.statusCode() == 201) {
			System.out.println("Status Code: " + response.statusCode());
			System.out.println("Status: " + response.result().status());
			System.out.println("Order ID: " + response.result().id());
			System.out.println("Intent: " + response.result().checkoutPaymentIntent());
			System.out.println("Links: ");
			for (LinkDescription link : response.result().links()) {
				System.out.println("\t" + link.rel() + ": " + link.href() + "\tCall Type: " + link.method());
			}
			System.out.println(
					"Total Amount: " + response.result().purchaseUnits().get(0).amountWithBreakdown().currencyCode()
							+ " " + response.result().purchaseUnits().get(0).amountWithBreakdown().value());
			System.out.println("Full response body:");
			System.out.println(new JSONObject(new Json().serialize(response.result())).toString(4));
		}
		return response.result();
	}

	private OrderRequest buildRequestBody() {
		OrderRequest orderRequest = new OrderRequest();
		orderRequest.checkoutPaymentIntent("CAPTURE");

		ApplicationContext applicationContext = new ApplicationContext().brandName("ETP Group").landingPage("BILLING")
				.shippingPreference("NO_SHIPPING").returnUrl("http://localhost:6006/return")
				.cancelUrl("http://localhost:6006/cancel");
		orderRequest.applicationContext(applicationContext);

		List<PurchaseUnitRequest> purchaseUnitRequests = new ArrayList<PurchaseUnitRequest>();
		PurchaseUnitRequest purchaseUnitRequest = new PurchaseUnitRequest().referenceId("ORDAZZLE")
				.description("Monthly").customId("ETP-PrimeCustomer").softDescriptor("PrimeCustomer")
				.amountWithBreakdown(new AmountWithBreakdown().currencyCode("INR").value("1.00")
						.amountBreakdown(new AmountBreakdown().itemTotal(new Money().currencyCode("INR").value("0.80"))
								.shipping(new Money().currencyCode("INR").value("1.00"))
								.handling(new Money().currencyCode("INR").value("0.10"))
								.taxTotal(new Money().currencyCode("INR").value("0.10"))
								.shippingDiscount(new Money().currencyCode("INR").value("1.00"))))
				.items(new ArrayList<Item>() {
					{
						add(new Item().name("Monthly Plan").description("Pay every 28 days").sku("Ordazzle-01")
								.unitAmount(new Money().currencyCode("INR").value("0.80"))
								.tax(new Money().currencyCode("INR").value("0.10")).quantity("1")
								.category("DIGITAL_GOODS"));
					}
				})
				.shippingDetail(new ShippingDetail().name(new Name().fullName("Bobby Chakravarthy"))
						.addressPortable(new AddressPortable().addressLine1("Street and Lane Here")
								.addressLine2("Floor 6").adminArea2("Bengaluru").adminArea1("KR").postalCode("560100")
								.countryCode("IN")));
		purchaseUnitRequests.add(purchaseUnitRequest);
		orderRequest.purchaseUnits(purchaseUnitRequests);
		return orderRequest;
	}

}