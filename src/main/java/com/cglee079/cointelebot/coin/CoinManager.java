package com.cglee079.cointelebot.coin;

import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;

import com.cglee079.cointelebot.constants.ID;
import com.cglee079.cointelebot.constants.SET;
import com.cglee079.cointelebot.exception.ServerErrorException;
import com.cglee079.cointelebot.log.Log;

public class CoinManager {
	@Autowired
	private CoinonePooler coinonePooler;

	@Autowired
	private BithumbPooler bithumbPooler;
	
	@Autowired
	private UpbitPooler upbitPooler;
	
	@Autowired
	private BitfinexPooler bitfinexPooler;

	@Autowired
	private BittrexPooler bittrexPooler;
	
	@Autowired
	private ExchangePooler exchangePooler;

	private double exchangeRate = 1080;

	public CoinManager() {
	}
	
	public double getExchangeRate() {
		return exchangeRate;
	}

	public void setExchangeRate(double exchangeRate) {
		this.exchangeRate = exchangeRate;
	}

	///
	@Scheduled(cron = "00 01 00 * * *")
	private void updateExchangeRate() {
		try {
			this.exchangeRate = exchangePooler.usd2krw();
		} catch (ServerErrorException e) {
			Log.i(e.log());
			Log.i(e.getStackTrace());
		}
	}

	public JSONObject getCoin(String coin, String exchange) throws ServerErrorException{
		JSONObject coinObj = null;
		
		if(exchange.equals(ID.EXCHANGE_COINONE) && SET.ENABLED_COINONE) { 
			coinObj = coinonePooler.getCoin(coin);
		} else if(exchange.equals(ID.EXCHANGE_BITHUMB) && SET.ENABLED_BITHUMB){
			coinObj = bithumbPooler.getCoin(coin);
		} else if(exchange.equals(ID.EXCHANGE_UPBIT) && SET.ENABLED_UPBIT){
			coinObj = upbitPooler.getCoin(coin);
		}
		return coinObj;
	}
	
	public JSONObject getCoinWithKimp(String exchange) throws ServerErrorException{
		JSONObject coinKR = this.getCoin(SET.MY_COIN, exchange);
		JSONObject coinUS = null;
		if(SET.ENABLED_BITFINEX) {coinUS = bitfinexPooler.getCoin(SET.MY_COIN);}
		if(SET.ENABLED_BITTREX) {coinUS = bittrexPooler.getCoin(SET.MY_COIN);}
		
		if(coinUS == null) {
			new ServerErrorException("미국 코인정보를 받아 올 수 없습니다");
		}
		
		int coinPriceKR = coinKR.getInt("last");
		int coinPriceUS = (int) (coinUS.getDouble("last") * exchangeRate);
		int gap = coinPriceKR - coinPriceUS;
		double kimp = ((double)gap / (double)coinPriceUS) * 100;
		coinKR.put("kimp", kimp);
		coinKR.put("usd", coinUS.getDouble("last"));
		coinKR.put("usd2krw", coinPriceUS);
		coinKR.put("rate", exchangeRate);
		return coinKR;
	}
	
}

