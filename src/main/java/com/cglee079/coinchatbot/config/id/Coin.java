package com.cglee079.coinchatbot.config.id;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
public enum Coin {
	COINSEEKER("모든코인알리미", "Only Korean Support"),
	ADA("에이다", "Cardano"),
	BCH("비트코인캐시", "Bitcoin Cash"), 
	BTC("비트코인", "Bitcoin"), 
	BTG("비트코인 골드", "Bitcoin Gold"), 
	CNN("CNN", "Content Neutrality Networ"), 
	ELF("엘프", "Aelf"), 
	EOS("이오스", "Eos"),
	ETC("이더리움 클래식", "Ethereum Classic"), 
	ETH("이더리움", "Ethereum"), 
	GTO("기프토", "Gifto"), 
	ICX("아이콘", "Icon"),
	IOTA("아이오타", "MIOTA"),
	KNC("카이버 네트워크", "Kyber Network"),
	LTC("라이트코인", "Lite Coin"), 
	MITH("미스릴", "Mithril"), 
	MCO("모나코", "Monaco"), 
	NEO("네오", "Neo"),
	NXT("엔엑스티", "Nxt"),
	OMG("오미세고", "OmiseGO"), 
	QTUM("퀀텀", "Qtum"),
	RDD("레드코인", "ReddCoin"),
	SC("시아코인", "Sia Coin"), 
	SNT("스테이터스네트워크토큰", "Status network token"), 
	STORM("스톰", "Storm"), 
	TRX("트론", "Tron"),
	VEN("비체인", "VeChain"), 
	XLM("스텔라루멘", "Stellar"), 
	XRP("리플", "Ripple"), 
	XVG("버지", "Verge"), 
	ZIL("질리카", "Zilliqa"),
	NPXS("펀디엑스", "Pundi X"),
	CAP("캡", "Cap"),
	DASH("대쉬", "Dash"),
	BSV("비트코인에스브이", "Bitcoin SV"),
	WAVES("웨이브", "Waves");
	
	
	@Getter
	private final String kr;
	
	@Getter
	private final String us;
}
