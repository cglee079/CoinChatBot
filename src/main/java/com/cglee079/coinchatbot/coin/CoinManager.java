package com.cglee079.coinchatbot.coin;

import javax.annotation.PostConstruct;

import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;

import com.cglee079.coinchatbot.config.id.Coin;
import com.cglee079.coinchatbot.config.id.Market;
import com.cglee079.coinchatbot.exception.ServerErrorException;
import com.cglee079.coinchatbot.log.Log;
import com.cglee079.coinchatbot.model.TimelyInfoVo;
import com.cglee079.coinchatbot.service.CoinMarketConfigService;

public class CoinManager {
	@Autowired
	private CoinMarketConfigService coinMarketConfigService;
	
	//KRW 거래소
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
	private GopaxPooler gopaxPooler;
	
	@Autowired
	private CashierestPooler cashierestPooler;
	
	
	/// USD 거래소
	@Autowired
	private BitfinexPooler bitfinexPooler;

	@Autowired
	private BittrexPooler bittrexPooler;
	
	@Autowired
	private PoloniexPooler poloniexPooler;
	
	@Autowired
	private BinancePooler binancePooler;
	
	@Autowired
	private HuobiPooler huobiPooler;
	
	@Autowired
	private HadaxPooler hadaxPooler;
	
	@Autowired
	private OkexPooler okexPooler;
	
	@Autowired
	private ExchangePooler exchangePooler;

	/* DB에서 각 거래소의 RestAPI Coin 파라미터값을 가져옴. */
	@PostConstruct
	public void init() {
		coinonePooler.setCoinParam(coinMarketConfigService.getMarketParams(Market.COINONE));
		bithumbPooler.setCoinParam(coinMarketConfigService.getMarketParams(Market.BITHUMB));
		upbitPooler.setCoinParam(coinMarketConfigService.getMarketParams(Market.UPBIT));
		coinnestPooler.setCoinParam(coinMarketConfigService.getMarketParams(Market.COINNEST));
		korbitPooler.setCoinParam(coinMarketConfigService.getMarketParams(Market.KORBIT));
		gopaxPooler.setCoinParam(coinMarketConfigService.getMarketParams(Market.GOPAX));
		cashierestPooler.setCoinParam(coinMarketConfigService.getMarketParams(Market.CASHIEREST));
		
		bitfinexPooler.setCoinParam(coinMarketConfigService.getMarketParams(Market.BITFINEX));
		bittrexPooler.setCoinParam(coinMarketConfigService.getMarketParams(Market.BITTREX));
		poloniexPooler.setCoinParam(coinMarketConfigService.getMarketParams(Market.POLONIEX));
		binancePooler.setCoinParam(coinMarketConfigService.getMarketParams(Market.BINANCE));
		huobiPooler.setCoinParam(coinMarketConfigService.getMarketParams(Market.HUOBI));
		hadaxPooler.setCoinParam(coinMarketConfigService.getMarketParams(Market.HADAX));
		okexPooler.setCoinParam(coinMarketConfigService.getMarketParams(Market.OKEX));
		updateExchangeRate();
	}
	
	private double exchangeRate = 1068;

	public double getExchangeRate() {
		return exchangeRate;
	}

	public void setExchangeRate(double exchangeRate) {
		this.exchangeRate = exchangeRate;
	}

	/* 환율 정보 갱신 */
	@Scheduled(cron = "00 01 00 * * *")
	private void updateExchangeRate() {
		try {
			this.exchangeRate = exchangePooler.usd2krw();
		} catch (ServerErrorException e) {
			Log.i(e.log());
			e.printStackTrace();
		}
	}

	/* 거래소로부터 코인 정보 가져옴 */
	public JSONObject getCoin(Coin coin, Market marketId) throws ServerErrorException{
		JSONObject coinObj = null;
		
		switch(marketId) {
		case COINONE 	: coinObj = coinonePooler.getCoin(coin); break;
		case BITHUMB 	: coinObj = bithumbPooler.getCoin(coin); break;
		case UPBIT 		: coinObj = upbitPooler.getCoin(coin); break;
		case COINNEST	: coinObj = coinnestPooler.getCoin(coin); break;
		case KORBIT 	: coinObj = korbitPooler.getCoin(coin); break;
		case GOPAX 		: coinObj = gopaxPooler.getCoin(coin); break;
		case CASHIEREST : coinObj = cashierestPooler.getCoin(coin); break;
			
		case BITFINEX 	: coinObj = bitfinexPooler.getCoin(coin); break;
		case BITTREX 	: coinObj = bittrexPooler.getCoin(coin); break;
		case POLONIEX 	: coinObj = poloniexPooler.getCoin(coin); break;
		case BINANCE 	: coinObj = binancePooler.getCoin(coin); break;
		case HUOBI 		: coinObj = huobiPooler.getCoin(coin); break;
		case HADAX 		: 
			if(coin == Coin.BTC) { coinObj = huobiPooler.getCoin(coin); } 
			else { coinObj = hadaxPooler.getCoin(coin); }
			break;
		case OKEX 		: coinObj = okexPooler.getCoin(coin); break;

		default:
			break;
		}
		
		return coinObj;
	}
	
	/* 코인 정보 중 현재가격 가져옴  */
	public Double getCoinLast(Coin myCoinId, Market marketId, boolean isInBtc) {
		try {
			double last = -1;
			JSONObject coinObj = null;
			coinObj = this.getCoin(myCoinId, marketId);
			
			last = coinObj.getDouble("last");
			if(isInBtc) {
				last = this.getMoney(coinObj, marketId).getDouble("last");
			}
			return last;
		} catch (ServerErrorException e) {
			Log.i(e.log());
			e.printStackTrace();
			return -1.0;
		}
	}
	
	/* 기축이 비트코인인 코인에 대하여, KRW or USD로 바꾼 가격을 가져옴 */
	public JSONObject getMoney(JSONObject coinObj, Market marketId){
		JSONObject btcObj;
		try {
			btcObj = this.getCoin(Coin.BTC, marketId);
		} catch (ServerErrorException e) {
			Log.i(e.log());
			e.printStackTrace();
			return null;
		}
		
		double last = coinObj.getDouble("last") * btcObj.getDouble("last");
		double first = coinObj.getDouble("first") * btcObj.getDouble("last");
		double high = coinObj.getDouble("high") * btcObj.getDouble("last");
		double low = coinObj.getDouble("low") * btcObj.getDouble("last");
		
		JSONObject coinKRW = new JSONObject();
		coinKRW.put("last", last);
		coinKRW.put("first", first);
		coinKRW.put("high", high);
		coinKRW.put("low", low);
		
		return coinKRW;
	}
	
	public JSONObject getMoney(TimelyInfoVo timelyInfo, Market marketId){
		JSONObject coinObj = new JSONObject();
		coinObj.put("last", timelyInfo.getLast());
		coinObj.put("first", 0);
		coinObj.put("high", timelyInfo.getHigh());
		coinObj.put("low", timelyInfo.getLow());
		return this.getMoney(coinObj, marketId);
	}
}

