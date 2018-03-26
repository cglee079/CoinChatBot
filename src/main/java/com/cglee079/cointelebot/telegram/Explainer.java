package com.cglee079.cointelebot.telegram;

import java.util.List;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;

import com.cglee079.cointelebot.constants.ID;
import com.cglee079.cointelebot.constants.SET;
import com.cglee079.cointelebot.model.CoinInfoVo;
import com.cglee079.cointelebot.service.CoinInfoService;

public class Explainer {
	@Autowired
	private CoinInfoService coinInfoService;

	private String helpMsg;
	private String coinname;
	private String version;
	private String priceEx;
	private String targetEx;
	private String numberEx;
	private String rateEx;
	
	@PostConstruct
	public void init() {
	    CoinInfoVo coinInfo = coinInfoService.get(SET.MY_COIN);
	    priceEx = coinInfo.getPriceEx();
	    numberEx  = coinInfo.getNumberEx();
	    targetEx = coinInfo.getTargetEx();
		version = coinInfo.getVersion();
		coinname = coinInfo.getCoinname();
		rateEx = "5%";
		
		helpMsg = "";
        helpMsg += "별도의 시간 알림 주기 설정을 안하셨다면,\n";
        helpMsg += "3시간 주기로 " + coinname + " 가격 알림이 전송됩니다.\n";
        helpMsg += "\n";

        helpMsg += "별도의 일일 알림 주기 설정을 안하셨다면,\n";
        helpMsg += "1일 주기로 거래량, 상한가, 하한가, 종가가 비교되어 전송됩니다.\n";
        helpMsg += "\n";

        helpMsg += "별도의 거래소 설정을 안하셨다면,\n";

        //
        String exchange = "";
        if(SET.ENABLED_UPBIT) {exchange = "업비트";}
        if(SET.ENABLED_BITHUMB) {exchange = "빗썸";}
        if(SET.ENABLED_COINONE) {exchange = "코인원";}
        helpMsg += exchange;
        //

        helpMsg += " 기준의 정보가 전송됩니다.\n";
        helpMsg += "\n";

        helpMsg += "평균단가,코인개수를 설정하시면,\n";
        helpMsg += "원금, 현재금액, 손익금을 확인 하실 수 있습니다.\n";
        helpMsg += "\n";

        helpMsg += "목표가격을 설정하시면,\n";
        helpMsg += "목표가격이 되었을때 알림을 받을 수 있습니다.\n";
        helpMsg += "목표가격을 위한 가격정보는 각 거래소에서 1분 주기로 갱신됩니다.\n";
        helpMsg += "\n";

        helpMsg += "톡을 보내시면 현재 " + coinname + " 가격을 확인 하실 수 있습니다.\n";
        helpMsg += "\n";

        helpMsg += "한국 프리미엄 정보를 확인 하실 수 있습니다.\n";
        helpMsg += "\n";

        if(!(SET.MY_COIN == ID.COIN_BTC)) {
            helpMsg += "비트코인대비 변화량을 확인 하실 수 있습니다.\n";
        }
        
        helpMsg += "\n";
        helpMsg += "국내정보 By ";
        if(SET.ENABLED_COINONE) { helpMsg += "코인원, ";}
        if(SET.ENABLED_BITHUMB) { helpMsg += "빗썸, ";}
        if(SET.ENABLED_UPBIT) { helpMsg += "업비트";}
        helpMsg += "\n";
        //

        helpMsg += "미국정보 By ";
        if(SET.ENABLED_BITTREX) { helpMsg += "Bittrex, ";}
        if(SET.ENABLED_BITFINEX) { helpMsg += "Bitfinex ";}

        helpMsg += "\n";

        helpMsg += "환율정보 By the European Central Bank\n";
        helpMsg += "\n";
        helpMsg += "Developed By CGLEE ( cglee079@gmail.com )\n";
	}
	
	public String getCoinname() {
		return coinname;
	}
	
	public String explainHelp() {
		String msg = "";
		msg += coinname + " 알리미 ver" + version + "\n";
		msg += "\n";
		msg += helpMsg;
		return msg;
	}
	
	public String explainCoinList() {
		String msg = "";
		msg += "링크를 클릭 하시면,\n";
		msg += "해당 코인알리미 봇으로 이동합니다.\n";
		msg += "-----------------------\n";
		List<CoinInfoVo> coinInfos = coinInfoService.list(SET.MY_COIN);
	    CoinInfoVo coinInfo = null;
	    int coinInfosLen = coinInfos.size();
			for(int i =0; i < coinInfosLen; i++) {
	        coinInfo = coinInfos.get(i);
	        msg += coinInfo.getCoinname() + "  :  " + coinInfo.getChatAddr() + "\n";
	    }
		msg += "\n";
		return msg;
	}
	
	public String explainSetDayloop() {
		String msg =  "";
		msg += "일일 알림 주기를 선택해주세요.\n";
		msg += "선택 하신 일일주기로 알림이 전송됩니다.\n";
		return msg;
	}
	
	public String explainSetTimeloop() {
		String msg =  "";
		msg += "시간 알림 주기를 선택해주세요.\n";
		msg += "선택 하신 시간주기로 알림이 전송됩니다.\n";
		return msg;
	}
	
	public String explainSetExchange() {
		String msg =  "";
		msg += "거래중인 거래소를 설정해주세요.\n";
		msg += "모든 정보는 설정 거래소 기준으로 전송됩니다.\n";
		return msg;
	}
	
	public String explainSetTarget() {
		String msg =  "";
		msg += "목표가격을 설정해주세요.\n";
		msg += "목표가격이 되었을 때 알림이 전송됩니다.\n";
		msg += "목표가격을 위한 가격정보는 1분 주기로 갱신됩니다.\n";
		msg += "\n";
		
		msg += "* 목표가격은 숫자 또는 백분율로 입력해주세요.\n";
		msg += "* ex) " + targetEx + "  : 목표가격 " + targetEx + "원\n";
		msg += "* ex) " + rateEx + "    : 현재가 +" + rateEx + "\n";
		msg += "* ex) -" + rateEx + "  : 현재가 -" + rateEx + "\n";
		
		return msg;
	}
	
	public String explainSetPrice() {
		String msg =  "";
		msg += "평균단가를 입력해주세요.\n";
		msg += "평균단가와 코인개수를 입력하시면 손익금은 확인 하실 수 있습니다.\n";
		msg += "\n";
		msg += "* 평균단가는는 숫자로만 입력해주세요.\n";
		msg += "* 0을 입력하시면 초기화됩니다.\n";
		msg += "* ex) " + 0 + " : 초기화\n";
		msg += "* ex) " + priceEx + " : 평균단가 " + priceEx +" 원 설정";
		return msg;
	}
	
	public String explainSetNumber() {
		String msg =  "";
		msg += "코인개수를 입력해주세요.\n";
		msg += "평균단가와 코인개수를 입력하시면 손익금은 확인 하실 수 있습니다.\n";
		msg += "\n";
		msg += "* 코인개수는 숫자로만 입력해주세요.\n";
		msg += "* 0을 입력하시면 초기화됩니다.\n";
		msg += "* ex) " + 0 + " : 초기화\n";
		msg += "* ex) " + numberEx + " : 코인개수 " + numberEx +" 개 설정";
		return msg;
	}
	
	
	public String explainSendMsg() {
		String msg =  "";
		msg += "개발자에게 내용이 전송되어집니다.\n";
		msg += "내용을 전송해주세요.\n";
		return msg;
	}

}
