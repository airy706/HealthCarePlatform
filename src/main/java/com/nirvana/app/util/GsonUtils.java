package com.nirvana.app.util;

import org.apache.http.client.HttpClient;
import org.apache.http.impl.client.HttpClients;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

public class GsonUtils {
	private static GsonBuilder gb;

	static {
		gb = new GsonBuilder();
	}

	public static Gson getDateFormatGson() {
		gb.setDateFormat("yyyy-MM-dd HH:mm:ss");
		return gb.create();
	}

	public static String toJson(Object o) {
		return gb.create().toJson(o);
	}

}
