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
	private CoinnestPooler coinnestPooler;
	
	@Autowired
	private KorbitPooler korbitPooler;
	
	@Autowired
	private BitfinexPooler bitfinexPooler;

	@Autowired
	private BittrexPooler bittrexPooler;
	
	@Autowired
	private PoloniexPooler poloniexPooler;
	
	@Autowired
	private ExchangePooler exchangePooler;

	private double exchangeRate = 1064;

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
		} else if(exchange.equals(ID.EXCHANGE_COINNEST) && SET.ENABLED_COINNEST){
			coinObj = coinnestPooler.getCoin(coin);
		}	else if(exchange.equals(ID.EXCHANGE_KORBIT) && SET.ENABLED_KORBIT){
			coinObj = korbitPooler.getCoin(coin);
		}
		return coinObj;
	}
	
	public JSONObject getCoinWithKimp(String coin, String exchange) throws ServerErrorException{
		JSONObject coinKR = this.getCoin(coin, exchange);
		JSONObject coinUS = null;
		if(SET.ENABLED_BITFINEX) {coinUS = bitfinexPooler.getCoin(coin);}
		if(SET.ENABLED_BITTREX) {coinUS = bittrexPooler.getCoin(coin);}
		if(SET.ENABLED_POLONIEX) {coinUS = poloniexPooler.getCoin(coin);}
		
		if(coinUS == null) {
			new ServerErrorException("미국 코인정보를 받아 올 수 없습니다");
		}
		
		double coinPriceKR = coinKR.getDouble("last");
		double coinPriceUS = (coinUS.getDouble("last") * exchangeRate);
		double gap = coinPriceKR - coinPriceUS;
		double kimp = ((double)gap / (double)coinPriceUS) * 100;
		coinKR.put("kimp", kimp);
		coinKR.put("usd", coinUS.getDouble("last"));
		coinKR.put("usd2krw", coinPriceUS);
		coinKR.put("rate", exchangeRate);
		return coinKR;
	}
	
}

