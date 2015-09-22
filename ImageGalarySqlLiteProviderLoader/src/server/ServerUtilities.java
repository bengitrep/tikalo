/*
 * Copyright 2012 Google Inc.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package server;


import android.content.Context;
import android.util.Log;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Random;



/**
 * 
 * @author ben shlomi
 *
 */
public final class ServerUtilities {

	//request.setHeader("User-Agent", "Mozilla/5.0 (Linux; U; Android 2.3; en-us; SAMSUNG-SGH-I717 Build/GINGERBREAD) AppleWebKit/533.1 (KHTML, like Gecko) Version/4.0 Mobile Safari/533.1");
	//request.setHeader("User-Agent", "Mozilla/5.0 (Linux; U; Android 2.3; en-us; SAMSUNG-SGH-I717 Build/GINGERBREAD) AppleWebKit/533.1 (KHTML, like Gecko) Version/4.0 Mobile Safari/533.1");
	//request.setHeader("User-Agent", "Mozilla/5.0 (Linux; U; Android 2.3; en-us; SAMSUNG-SGH-I717 Build/GINGERBREAD) AppleWebKit/533.1 (KHTML, like Gecko) Version/4.0 Mobile Safari/533.1");

	///////////////////Get Method (1) slower....////////////////////////////////////
	public static String getMethod1(String urlString){
		String result = "";
		URL url;
		HttpURLConnection connection = null;
		try {
			url = new URL(urlString);
			connection = (HttpURLConnection)url.openConnection();
			InputStream in = connection.getInputStream();
			InputStreamReader rd = new InputStreamReader(in);
			int data = rd.read();
			while ((data = rd.read()) != -1) {
				result += (char) data;
			}
			Log.v("ben" , "GET1 Response Success Message: ---> " + result);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				connection.disconnect();
				connection = null;
			} catch (Exception e) {
				e.printStackTrace(); //If you want further info on failure...
			}
		} 
		return result;
	}
	///////////////////////////////////////////////////////////////////////


	///////////////////Get Method (2) faster....////////////////////////////////////
	public static String getMethod2(String urlString){
		String result = "";
		URL url;
		HttpURLConnection connection = null;

		try {
			url = new URL(urlString);
			connection = (HttpURLConnection)url.openConnection();
			InputStream in = connection.getInputStream();
			BufferedReader rd = new BufferedReader(new InputStreamReader(in));
			String line = "";
			while ((line = rd.readLine()) != null) {
				result += line;  
			} 
			//Log.v("ben" , "GET2 Response Success Message: ---> " + result);
		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		finally {
			try {
				connection.disconnect();
				connection = null;
			} catch (Exception e) {
				e.printStackTrace(); //If you want further info on failure...
			}
		} 
		return result;
	}
	///////////////////////////////////////////////////////////////////////



}
