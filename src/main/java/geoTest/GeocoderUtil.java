package geoTest;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;

import com.alibaba.fastjson.JSONObject;
import com.google.maps.GeoApiContext;
import com.google.maps.GeocodingApi;
import com.google.maps.GeocodingApiRequest;
import com.google.maps.errors.ApiException;
import com.google.maps.model.GeocodingResult;

public class GeocoderUtil {
	public static Double[] getCoords(String address,String language){
		if(language.equals("cn")){
			String url="http://api.map.baidu.com/geocoder/v2/?address="+address+"&output=json&ak=Ogpye209UaanWC2EyfDHs2UifIFKdI5k";
			String json=loadJson(url);
			System.out.println(json);
			JSONObject jsonObject=(JSONObject) JSONObject.parse(json);
			if(jsonObject.get("status").toString().equals("0")){
				Double lng=jsonObject.getJSONObject("result").getJSONObject("location").getDouble("lng");
				Double lat=jsonObject.getJSONObject("result").getJSONObject("location").getDouble("lat");
				Double[] coords=new Double[2];
				coords[0]=lng;
				coords[1]=lat;
				return coords;
			}
			return null;
		}
		// Replace the API key below with a valid API key.
		GeoApiContext context = new GeoApiContext().setApiKey("AIzaSyBO96jWHhwGp7IPB_9ZFFmovQq_8fXGIJo");
		GeocodingResult[] results;
		try {
			results = GeocodingApi.geocode(context,
			    "1600 Amphitheatre Parkway Mountain View, CA 94043").await();
			System.out.println(results[0].formattedAddress);
			Double lng=results[0].geometry.location.lng;
			Double lat=results[0].geometry.location.lat;
			Double[] coords=new Double[2];
			coords[0]=lng;
			coords[1]=lat;
			return coords;
		} catch (ApiException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} catch (InterruptedException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
		return null;
	}
	public static String loadJson(String url){
		StringBuilder json=new StringBuilder();
		try {
			URL url_d=new URL(url);
			URLConnection conn=url_d.openConnection();
			BufferedReader in=new BufferedReader(new InputStreamReader(conn.getInputStream()));
			String inputLine=null;
			while((inputLine=in.readLine())!=null){
				json.append(inputLine);
			}
			in.close();
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return json.toString();
	}
}
