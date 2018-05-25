package com.cglee079.cointelebot.coin;

import java.util.HashMap;

public class ApiPooler {
	protected static final int MAX_RETRY_CNT = 5;
	protected int retryCnt = 0;
	
	protected HashMap<String, String> coinParam;

	public void setCoinParam(HashMap<String, String> coinParam) {
		this.coinParam = coinParam;
	}
	
}
