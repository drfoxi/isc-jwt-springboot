package com.iscdemo.models.model;

import java.io.Serializable;

public class JwtRequest implements Serializable {

	private static final long serialVersionUID = 5926468583005150707L;

	private String username;
	private String password;
	private int channel;
	private String mac;
	private String ip;

	public JwtRequest()
	{

	}

	public JwtRequest(String username, String password, int channel, String mac, String ip) {
		this.username = username;
		this.password = password;
		this.channel = channel;
		this.mac = mac;
		this.ip = ip;
	}

	public static long getSerialVersionUID() {
		return serialVersionUID;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public int getChannel() {
		return channel;
	}

	public void setChannel(int channel) {
		this.channel = channel;
	}

	public String getMac() {
		return mac;
	}

	public void setMac(String mac) {
		this.mac = mac;
	}

	public String getIp() {
		return ip;
	}

	public void setIp(String ip) {
		this.ip = ip;
	}
}