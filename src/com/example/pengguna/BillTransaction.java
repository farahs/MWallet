package com.example.pengguna;

public class BillTransaction extends Transaction {
	String id_bill;
	String pay_code;
	String flag_elect;
	String elect_acc;
	String flag_water;
	String water_acc;
	String flag_int;
	String int_acc;
	String paid_amount;

	public String getId_bill() {
		return id_bill;
	}

	public void setId_bill(String id_bill) {
		this.id_bill = id_bill;
	}

	public String getPay_code() {
		return pay_code;
	}

	public void setPay_code(String pay_code) {
		this.pay_code = pay_code;
	}

	public String getFlag_elect() {
		return flag_elect;
	}

	public void setFlag_elect(String flag_elect) {
		this.flag_elect = flag_elect;
	}

	public String getElect_acc() {
		return elect_acc;
	}

	public void setElect_acc(String elect_acc) {
		this.elect_acc = elect_acc;
	}

	public String getFlag_water() {
		return flag_water;
	}

	public void setFlag_water(String flag_water) {
		this.flag_water = flag_water;
	}

	public String getWater_acc() {
		return water_acc;
	}

	public void setWater_acc(String water_acc) {
		this.water_acc = water_acc;
	}

	public String getFlag_int() {
		return flag_int;
	}

	public void setFlag_int(String flag_int) {
		this.flag_int = flag_int;
	}

	public String getInt_acc() {
		return int_acc;
	}

	public void setInt_acc(String int_acc) {
		this.int_acc = int_acc;
	}

	public String getPaid_amount() {
		return paid_amount;
	}

	public void setPaid_amount(String paid_amount) {
		this.paid_amount = paid_amount;
	}

	public String getAcc_name() {
		return acc_name;
	}

	public void setAcc_name(String acc_name) {
		this.acc_name = acc_name;
	}

	String acc_name;

	public BillTransaction(String transaction_id, String transaction_type,
			String id_user, String transaction_code, String amount,
			String id_bill, String pay_code, String flag_elect,
			String elect_acc, String flag_water, String water_acc,
			String flag_int, String int_acc, String acc_name) {
		this.setTransaction_id(transaction_id);
		this.setTransaction_type(transaction_type);
		this.setId_user(id_user);
		this.setTransaction_code(transaction_code);
		this.setAmount(amount);
		this.setPaid_amount(amount);
		this.setId_bill(id_bill);
		this.setPay_code(pay_code);
		this.setFlag_elect(flag_elect);
		this.setElect_acc(elect_acc);
		this.setFlag_int(flag_int);
		this.setInt_acc(int_acc);
		this.setWater_acc(water_acc);
		this.setFlag_water(flag_water);
		this.setAcc_name(acc_name);
	}

}
