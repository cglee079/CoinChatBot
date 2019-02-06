package com.cglee079.coinchatbot.config.cmd;

import com.cglee079.coinchatbot.config.id.Lang;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
public enum TimeloopCmd implements CmdEnum{
	NULL(-1, "", ""),
	H1(1, "매 1시간", "1 Hour"),
	H2(2, "매 2시간", "2 Hour"),
	H3(3, "매 3시간", "3 Hour"),
	H4(4, "매 4시간", "4 Hour"),
	H5(5, "매 5시간", "5 Hour"),
	H6(6, "매 6시간", "6 Hour"),
	H7(7, "매 7시간", "7 Hour"),
	H8(8, "매 8시간", "8 Hour"),
	H9(9, "매 9시간", "9 Hour"),
	H10(10, "매 10시간", "10 Hour"),
	H11(11, "매 11시간", "11 Hour"),
	H12(12, "매 12시간", "12 Hour"),
	OFF(0, "끄기", "Stop"),
	OUT(-1, "나가기", "Out");
	
	@Getter
	private final int value;
	
	@Getter
	private final String kr;
	
	@Getter
	private final String us;
	
	public static TimeloopCmd from(Lang lang, String str) {
		return CmdEnum.from(values(), lang, str, TimeloopCmd.NULL);
	}
	
	public String getCmd(Lang lang) {
		return CmdEnum.getCmd(this, lang);
	}
}
