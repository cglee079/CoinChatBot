package com.cglee079.coinchatbot.util;

import java.text.DecimalFormat;

import com.cglee079.coinchatbot.config.id.Lang;
import com.cglee079.coinchatbot.config.id.Market;

public class NumberFormmater {
	private int digitKRW;
	private int digitUSD;
	private int digitBTC;

	public NumberFormmater(int digitKRW, int digitUSD, int digitBTC) {
		this.digitKRW = digitKRW;
		this.digitUSD = digitUSD;
		this.digitBTC = digitBTC;
	}

	public String toNumberStr(double value, int length) {
		DecimalFormat df = new DecimalFormat("#");
		df.setMinimumFractionDigits(0);
		df.setMaximumFractionDigits(0);
		df.setMinimumIntegerDigits(length);
		return df.format(value);
	}
	
	public String toExchangeRateKRWStr(double i) {
		DecimalFormat df = new DecimalFormat("#,###");
		df.setMinimumFractionDigits(0);
		df.setMaximumFractionDigits(0);
		df.setPositiveSuffix("원");
		df.setNegativeSuffix("원");
		return df.format(i);
	}

	public String toBTCStr(double i) {
		DecimalFormat df = new DecimalFormat("#.#");
		df.setMinimumFractionDigits(digitBTC);
		df.setMaximumFractionDigits(digitBTC);
		df.setPositiveSuffix(" BTC");
		df.setNegativeSuffix(" BTC");
		return df.format(i);
	}

	public String toOnlyBTCMoneyStr(double i, Market marketId) {
		String result = "";

		if (marketId.isKRW()) {
			DecimalFormat df = new DecimalFormat("#,###");
			df.setMinimumFractionDigits(0);
			df.setMaximumFractionDigits(0);
			df.setPositiveSuffix("원");
			result = df.format(i);
		} else if (marketId.isUSD()) {
			DecimalFormat df = new DecimalFormat("#,###");
			df.setMinimumFractionDigits(0);
			df.setMaximumFractionDigits(0);
			df.setPositivePrefix("$");
			result = df.format(i);
		}
		return result;
	}

	public String toInvestAmountStr(double i, Market marketId) {
		String result = "";

		if (marketId.isKRW()) {
			DecimalFormat df = new DecimalFormat("#,###");
			df.setMinimumFractionDigits(0);
			df.setMaximumFractionDigits(0);
			df.setPositiveSuffix("원");
			result = df.format(i);
		} else if (marketId.isUSD()) {
			DecimalFormat df = new DecimalFormat("#,###");
			df.setMinimumFractionDigits(0);
			df.setMaximumFractionDigits(0);
			df.setPositivePrefix("$");
			result = df.format(i);
		}
		return result;
	}

	public String toSignInvestAmountStr(double i, Market marketId) {
		String result = "";

		if (marketId.isKRW()) {
			DecimalFormat df = new DecimalFormat("#,###");
			df.setMinimumFractionDigits(0);
			df.setMaximumFractionDigits(0);
			df.setPositivePrefix("+");
			df.setNegativePrefix("-");
			df.setPositiveSuffix("원");
			df.setNegativeSuffix("원");
			result = df.format(i);
		} else if (marketId.isUSD()) {
			DecimalFormat df = new DecimalFormat("#,###");
			df.setMinimumFractionDigits(0);
			df.setMaximumFractionDigits(0);
			df.setPositivePrefix("+$");
			df.setNegativePrefix("-$");
			result = df.format(i);
		}
		return result;
	}

	public String toMoneyStr(double i, Market marketId) {
		String result = "";
		if (marketId.isKRW()) {
			result = toKRWStr(i);
		}
		if (marketId.isUSD()) {
			result = toUSDStr(i);
		}
		return result;
	}

	public String toSignMoneyStr(double i, Market marketId) {
		String result = "";
		if (marketId.isKRW()) {
			result = toSignKRWStr(i);
		}
		if (marketId.isUSD()) {
			result = toSignUSDStr(i);
		}
		return result;
	}

	public String toKRWStr(double i) {
		DecimalFormat df = new DecimalFormat("#,###.#");
		df.setMinimumFractionDigits(digitKRW);
		df.setMaximumFractionDigits(digitKRW);
		df.setPositiveSuffix("원");
		df.setNegativeSuffix("원");
		return df.format(i);
	}

	public String toSignKRWStr(double i) {
		DecimalFormat df = new DecimalFormat("#,###.#");
		df.setMinimumFractionDigits(digitKRW);
		df.setMaximumFractionDigits(digitKRW);
		df.setPositivePrefix("+");
		df.setNegativePrefix("-");
		df.setPositiveSuffix("원");
		df.setNegativeSuffix("원");
		return df.format(i);
	}

	public String toUSDStr(double d) {
		DecimalFormat df = new DecimalFormat("#.#");
		df.setMinimumFractionDigits(digitUSD);
		df.setMaximumFractionDigits(digitUSD);
		df.setPositivePrefix("$");
		return df.format(d);
	}

	public String toSignUSDStr(double d) {
		DecimalFormat df = new DecimalFormat("#.#");
		df.setMinimumFractionDigits(digitUSD);
		df.setMaximumFractionDigits(digitUSD);
		df.setPositivePrefix("+$");
		df.setNegativePrefix("-$");
		return df.format(d);
	}

	public String toVolumeStr(long i) {
		DecimalFormat df = new DecimalFormat("#,###");
		return df.format(i);
	}

	public String toSignVolumeStr(long i) {
		DecimalFormat df = new DecimalFormat("#,###");
		df.setPositivePrefix("+");
		df.setNegativePrefix("-");
		return df.format(i);
	}

	public String toCoinCntStr(double i, Lang lang) {
		DecimalFormat df = new DecimalFormat("#.##");
		if (lang.equals(Lang.KR)) {
			df.setPositiveSuffix("개");
			df.setNegativeSuffix("개");
		} else if (lang.equals(Lang.US)) {
			df.setPositiveSuffix(" COIN");
			df.setNegativeSuffix(" COIN");
		}

		return df.format(i);
	}

	public String toSignKimpStr(double d) {
		DecimalFormat df = new DecimalFormat("#.##");
		df.setPositivePrefix("+");
		df.setNegativePrefix("-");
		return df.format(d);
	}

	public String toSignPercentStr(double c, double b) {
		String prefix = "";
		double gap = c - b;
		double percent = (gap / b) * 100;
		if (percent > 0) {
			prefix = "+";
		}
		DecimalFormat df = new DecimalFormat("#.##");
		return prefix + df.format(percent) + "%";
	}

	public Double formatPrice(double targetPrice, Market marketId) {
		DecimalFormat df = null;
		if (marketId.isKRW()) {
			df = new DecimalFormat("####.#");
			df.setMinimumFractionDigits(digitKRW);
			df.setMaximumFractionDigits(digitKRW);
		}
		
		if (marketId.isUSD()) {
			df = new DecimalFormat("#.#");
			df.setMinimumFractionDigits(digitUSD);
			df.setMaximumFractionDigits(digitUSD);
		}
		return Double.valueOf(df.format(targetPrice));
	}

}
