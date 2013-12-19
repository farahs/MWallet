package com.example.pengguna;

public class Transaction {
	private String transaction_id;
	private String transaction_type;
	private String id_user;
	private String transaction_code;
	private String amount;
	public String getTransaction_id() {
		return transaction_id;
	}
	public void setTransaction_id(String transaction_id) {
		this.transaction_id = transaction_id;
	}
	public String getTransaction_type() {
		return transaction_type;
	}
	public void setTransaction_type(String transaction_type) {
		this.transaction_type = transaction_type;
	}
	public String getId_user() {
		return id_user;
	}
	public void setId_user(String id_user) {
		this.id_user = id_user;
	}
	public String getTransaction_code() {
		return transaction_code;
	}
	public void setTransaction_code(String transaction_code) {
		this.transaction_code = transaction_code;
	}
	public String getAmount() {
		return amount;
	}
	public void setAmount(String amount) {
		this.amount = amount;
	}
	
	
}
