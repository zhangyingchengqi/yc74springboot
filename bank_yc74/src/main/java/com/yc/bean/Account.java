package com.yc.bean;

import java.io.Serializable;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel(  value="账户",description="账户实体信息类" )
public class Account implements Serializable {
	@ApiModelProperty(value="账户编号")
	private String accountid;
	@ApiModelProperty(value="操作金额",dataType="java.lang.Double")
	private double balance;
	@ApiModelProperty(value="转账账户编号")
	private String inaccountid;

	public String getInaccountid() {
		return inaccountid;
	}

	public void setInaccountid(String inaccountid) {
		this.inaccountid = inaccountid;
	}

	public String getAccountid() {
		return accountid;
	}

	public void setAccountid(String accountid) {
		this.accountid = accountid;
	}

	public double getBalance() {
		return balance;
	}

	public void setBalance(double balance) {
		this.balance = balance;
	}

	@Override
	public String toString() {
		return "Account [accountid=" + accountid + ", balance=" + balance + "]";
	}

}
