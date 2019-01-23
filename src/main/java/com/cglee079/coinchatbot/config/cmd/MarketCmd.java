package com.cglee079.coinchatbot.config.cmd;

import com.cglee079.coinchatbot.config.id.Lang;
import com.cglee079.coinchatbot.config.id.Market;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
public enum MarketCmd implements CmdEnum{
	NULL(null, "", ""),
	COINONE(Market.COINONE, "코인원", "Coinone"),
	BITHUMB(Market.BITHUMB, "빗썸", "Bithumb"),
	UPBIT(Market.UPBIT, "업비트", "Upbit"),
	COINNEST(Market.COINNEST, "코인네스트", "Coinnest"),
	KORBIT(Market.KORBIT, "코빗", "Korbit"),
	GOPAX(Market.GOPAX, "고팍스", "Gopax"),
	
	BITFINEX(Market.BITFINEX, "비트파이넥스", "Bitfinex"),
	BITTREX(Market.BITTREX, "비트렉스", "Bitrrext"),
	POLONIEX(Market.POLONIEX, "폴로닉스", "Poloniex"),
	BINANCE(Market.BINANCE, "바이낸스", "Binance"),
	HUOBI(Market.HUOBI, "후오비", "Huobi"),
	HADAX(Market.HADAX, "후닥스", "Hadax"),
	OKEX(Market.OKEX, "오케이엑스", "Okex"),
	
	OUT(null, "나가기", "OUT");
	
	@Getter
	private final Market id;
	
	@Getter
	private final String kr;
	
	@Getter
	private final String us;
	
	public static MarketCmd from(Lang lang, String str) {
		return CmdEnum.from(values(), lang, str, MarketCmd.NULL);
	}
	
	public static MarketCmd from(Market marketId) {
		MarketCmd rs = null;
		
		for (MarketCmd b : values()) {
			if (b.getId() == marketId) {
				return b;
			}
		}
		
		return rs;
	}
	
	public String getCmd(Lang lang) {
		return CmdEnum.getCmd(this, lang);
	}
	
	public boolean isKRW() {
		boolean result = false;
		switch(this) {
		case COINONE:
		case BITHUMB:
		case UPBIT:
		case COINNEST:
		case KORBIT:
		case GOPAX:
			result = true; break;
		default :
			result = false; break;
		}
		
		return result;
	}
	
	public boolean isUSD() {
		boolean result = false;
		switch(this) {
		case COINONE:
		case BITHUMB:
		case UPBIT:
		case COINNEST:
		case KORBIT:
		case GOPAX:
			result = false; break;
		default :
			result = true; break;
		}
		
		return result;
	}
	
}
