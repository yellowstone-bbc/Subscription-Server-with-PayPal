package com.ordazzlesubscription;

import java.util.Date;
import java.util.List;

public class CreateOrderResponse {
	
	public class Payee{
	    public String getEmail_address() {
			return email_address;
		}
		public void setEmail_address(String email_address) {
			this.email_address = email_address;
		}
		public String getMerchant_id() {
			return merchant_id;
		}
		public void setMerchant_id(String merchant_id) {
			this.merchant_id = merchant_id;
		}
		
		public String email_address;
	    public String merchant_id;
	}

	public class TaxTotal{
	    public String getValue() {
			return value;
		}
		public void setValue(String value) {
			this.value = value;
		}
		public String getCurrency_code() {
			return currency_code;
		}
		public void setCurrency_code(String currency_code) {
			this.currency_code = currency_code;
		}
		public String value;
	    public String currency_code;
	}

	public class Shipping{
	    public String getValue() {
			return value;
		}
		public void setValue(String value) {
			this.value = value;
		}
		public String getCurrency_code() {
			return currency_code;
		}
		public void setCurrency_code(String currency_code) {
			this.currency_code = currency_code;
		}
		public Address getAddress() {
			return address;
		}
		public void setAddress(Address address) {
			this.address = address;
		}
		public Name getName() {
			return name;
		}
		public void setName(Name name) {
			this.name = name;
		}
		public String value;
	    public String currency_code;
	    public Address address;
	    public Name name;
	}

	public class ShippingDiscount{
	    public String value;
	    public String currency_code;
	}

	public class Handling{
	    public String value;
	    public String currency_code;
	}

	public class ItemTotal{
	    public String value;
	    public String currency_code;
	}

	public class Breakdown{
	    public TaxTotal tax_total;
	    public Shipping shipping;
	    public ShippingDiscount shipping_discount;
	    public Handling handling;
	    public ItemTotal item_total;
	}

	public class Amount{
	    public Breakdown breakdown;
	    public String value;
	    public String currency_code;
	}

	public class Address{
	    public String country_code;
	    public String admin_area_1;
	    public String address_line_1;
	    public String admin_area_2;
	    public String address_line_2;
	    public String postal_code;
	}

	public class Name{
	    public String full_name;
	}

	public class Tax{
	    public String value;
	    public String currency_code;
	}

	public class UnitAmount{
	    public String value;
	    public String currency_code;
	}

	public class Item{
	    public String quantity;
	    public String name;
	    public String description;
	    public Tax tax;
	    public UnitAmount unit_amount;
	    public String category;
	    public String sku;
	}

	public class PurchaseUnit{
	    public Payee payee;
	    public Amount amount;
	    public String reference_id;
	    public Shipping shipping;
	    public String soft_descriptor;
	    public String custom_id;
	    public String description;
	    public List<Item> items;
	}

	public class Link{
	    public String method;
	    public String rel;
	    public String href;
	}

	public class Root{
	    public Date create_time;
	    public List<PurchaseUnit> purchase_units;
	    public List<Link> links;
	    public String id;
	    public String intent;
	    public String status;
	}



}
