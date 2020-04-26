package com.yc.web.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.yc.bean.Account;
import com.yc.biz.AccountBiz;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;

@Controller
@Api(value = "账户操作接口", tags = { "账户操作接口", "控制层" })
public class AccountController {
	@Autowired
	private AccountBiz accountBiz;

	@RequestMapping(value="/deposit",method=RequestMethod.POST)
	@ApiOperation(value = "存款", notes = "根据账户编号及金额来完成存款操作，注意此时的金额表示要存的金额")
	@ApiImplicitParams({ @ApiImplicitParam(name = "accountid", value = "账户编号", required = true),
			@ApiImplicitParam(name = "balance", value = "操作金额", required = true) })
	public @ResponseBody Account deposit(Account account) {
		Account a = accountBiz.deposite(account);
		return a;
	}

	@RequestMapping(value="/withdraw",method=RequestMethod.POST)
	@ApiOperation(value = "取款", notes = "根据账户编号及金额来完成取款操作，注意此时的金额表示要取的金额")
	@ApiImplicitParams({ @ApiImplicitParam(name = "accountid", value = "账户编号", required = true, dataType = "java.lang.String"),
			@ApiImplicitParam(name = "balance", value = "操作金额", required = true, dataType = "java.lang.Double") })
	public @ResponseBody Account withdraw(Account account) {
		Account a = accountBiz.withdraw(account);
		return a;
	}

	@RequestMapping(value="/transfer",method=RequestMethod.POST)
	public @ResponseBody Account transfer(Account account) {
		Account a = accountBiz.transfer(account);
		return a;
	}

	@RequestMapping(value="/query",method= {RequestMethod.POST,RequestMethod.GET})
	public @ResponseBody Account query(Account account) {
		Account a = accountBiz.find(account.getAccountid());
		return a;
	}

}
