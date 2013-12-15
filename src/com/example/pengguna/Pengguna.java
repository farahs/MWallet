package com.example.pengguna;

public class Pengguna {
	private String id;
	private String username;
	private String email;
	private String name;
	private String sex;
	private String age;
	private String pin;
	private float balance;
	
	public Pengguna(String id, String username, String email, String name, String sex, String age, String pin, float balance){
		this.id = id;
		this.username = username;
		this.email = email;
		this.name = name;
		this.sex = sex;
		this.age = age;
		this.pin = pin;
		this.balance = balance;
	}
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getSex() {
		return sex;
	}
	public void setSex(String sex) {
		this.sex = sex;
	}
	public String getAge() {
		return age;
	}
	public void setAge(String age) {
		this.age = age;
	}
	public String getPin() {
		return pin;
	}
	public void setPin(String pin) {
		this.pin = pin;
	}
	public float getBalance() {
		return balance;
	}
	public void setBalance(float balance) {
		this.balance = balance;
	}
}
