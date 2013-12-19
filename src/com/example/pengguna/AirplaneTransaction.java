package com.example.pengguna;

public class AirplaneTransaction extends Transaction {
	private String id_plane;
	private String company;
	private String total_ticket;
	private String date;
	private String time;
	private String depart_port;
	private String dest_port;

	public String getDepart_port() {
		return depart_port;
	}

	public void setDepart_port(String depart_port) {
		this.depart_port = depart_port;
	}

	public String getDest_port() {
		return dest_port;
	}

	public void setDest_port(String dest_port) {
		this.dest_port = dest_port;
	}

	public AirplaneTransaction(String transaction_id, String transaction_type,
			String id_user, String transaction_code, String amount,
			String id_plane, String company, String total_ticket, String date,
			String time, String depart_port, String dest_port) {
		this.setTransaction_id(transaction_id);
		this.setTransaction_type(transaction_type);
		this.setId_user(id_user);
		this.setTransaction_code(transaction_code);
		this.setAmount(amount);
		this.setId_plane(id_plane);
		this.setCompany(company);
		this.setTotal_ticket(total_ticket);
		this.setDate(date);
		this.setTime(time);
		this.setDepart_port(depart_port);
		this.setDest_port(dest_port);
	}

	public String getId_plane() {
		return id_plane;
	}

	public void setId_plane(String id_plane) {
		this.id_plane = id_plane;
	}

	public String getCompany() {
		return company;
	}

	public void setCompany(String company) {
		this.company = company;
	}

	public String getTotal_ticket() {
		return total_ticket;
	}

	public void setTotal_ticket(String total_ticket) {
		this.total_ticket = total_ticket;
	}

	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
	}

	public String getTime() {
		return time;
	}

	public void setTime(String time) {
		this.time = time;
	}
}
