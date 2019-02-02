package com.cglee079.coinchatbot.telegram;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;

import org.json.JSONObject;

import com.cglee079.coinchatbot.config.id.Coin;

public class TelegramNotifier {
	@Resource(name = "telegram")
	private Properties properties;

	private Map<Coin, String> botTokens;

	private String defaultUrl = "https://api.telegram.org/bot";

	@PostConstruct
	private void init() {
		botTokens = new HashMap<Coin, String>();

		Coin[] coins = Coin.values();
		for (int i = 0; i < coins.length; i++) {
			botTokens.put(coins[i], properties.getProperty("coin." + coins[i].name().toLowerCase() + ".bot.token"));
		}
	}

	public JSONObject sendMessage(Coin coinId, String userId, String content) throws Exception {

		String sendUrl = defaultUrl;
		sendUrl += botTokens.get(coinId);
		sendUrl += "/sendmessage";
		sendUrl += "?";
		sendUrl += "chat_id=";
		sendUrl += userId;
		sendUrl += "&text=";
		sendUrl += URLEncoder.encode(content, "UTF-8");

		HttpURLConnection connection = null;
		URL url = new URL(sendUrl);
		connection = (HttpURLConnection) url.openConnection();
		connection.setRequestMethod("GET");
		connection.setConnectTimeout(10000);
		connection.setReadTimeout(10000);

		BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream(), "UTF-8"));
		StringBuffer buffer = new StringBuffer();
		String line = null;
		while ((line = reader.readLine()) != null) {
			buffer.append(line).append("\r\n");
		}
		reader.close();

		return new JSONObject(buffer.toString());
	}

}
