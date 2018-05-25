package com.cglee079.cointelebot.coin;

import org.json.JSONObject;

import com.cglee079.cointelebot.exception.ServerErrorException;

public class ExchangePooler extends ApiPooler{
	public double usd2krw() throws ServerErrorException {
		String url = "http://api.fixer.io/latest?base=USD&symbols=KRW";
		HttpClient httpClient = new HttpClient();
		String response;
		try {
			response = httpClient.get(url);
			
			retryCnt = 0;
			return new JSONObject(response).getJSONObject("rates").getDouble("KRW");
		} catch (Exception e) {
			retryCnt++;
			if(retryCnt < MAX_RETRY_CNT) {
				return this.usd2krw();
			} else {
				retryCnt = 0;
				throw new ServerErrorException("환율정보 서버 에러");
			}
		}
	
	}
}
