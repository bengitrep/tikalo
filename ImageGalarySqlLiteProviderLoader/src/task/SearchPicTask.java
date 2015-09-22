package task;



import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;


import server.ServerUtilities;

import com.example.imagegalary.GridViewAdapter;
import com.example.imagegalary.ImageItem;
import com.example.imagegalary.MainActivity;


import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;


public class SearchPicTask extends AsyncTask<Void,Void,Void>{

	private Context context;
	private GridViewAdapter gridAdapter;
	private ArrayList<ImageItem> data = new ArrayList<ImageItem>();
	private ArrayList<String> names;

	ProgressDialog dialog; //ok

	String BASE_URL = "http://api.themoviedb.org/3/search/movie";
	String API_KEY = "1c03d5eab262378f33348790137915ab";
	String EXTRA = "append_to_response=releases,trailers";


	public SearchPicTask(Context context, GridViewAdapter gridAdapter, ArrayList<String> names){

		this.context = context;  
		this.gridAdapter =  gridAdapter;
		this.names = names;
	}
	
	@Override
	protected void onPostExecute(Void result) {
		super.onPostExecute(result);	
		((MainActivity)context).onComplete(data);
		///gridAdapter.setData(data);
		///gridAdapter.notifyDataSetChanged();
	}

	@Override
	protected void onPreExecute() {
		super.onPreExecute();
		//this.dialog.setMessage("Loading..");
		//this.dialog.show();
	}
	@Override
	protected Void doInBackground(Void... params) {
		getData();
		return null;
	}


	//http://api.themoviedb.org/3/search/movie ?query=spiderman&api_key=1c03d5eab262378f33348790137915ab
	public void getData(){

		//String encodedQuery= URLEncoder.encode(mQuery, "UTF-8");
		String url = BASE_URL;


		for (int i = 0; i < names.size(); i++) {
			Map<String, String> params = new HashMap<String, String>();
			params.put("query", names.get(i)); 
			params.put("api_key", API_KEY);  
			String urlGet = buildUrlGet(url, params);
			String jsonAsString = ServerUtilities.getMethod2(urlGet); 

			//Log.v("ben", jsonAsString);

			try {
				JSONObject jsonSearchResult = new JSONObject(jsonAsString);
				make(jsonSearchResult, names.get(i));
			} catch (JSONException e) {
				e.printStackTrace();
			}
		}


	}  


	// poster_path - "/rZd0y1X1Gw4t5B3f01Qzj8DYY66.jpg"

	public void make(JSONObject jsonSearchResult, String title){
		JSONArray jsonArray;
		try {
			jsonArray = jsonSearchResult.getJSONArray("results");
			/*
			JSONObject jsonObject = jsonArray.getJSONObject(1);
			Log.v("ben", "after " + jsonObject);
			String posterName = jsonObject.getString("poster_path");
			Log.v("ben", "after " + posterName);
			 */
 
			boolean found = false;
			for (int i = 0; !found &&i < jsonArray.length(); i++) {			
				JSONObject jsonObject = jsonArray.getJSONObject(i);
				String posterName = jsonObject.getString("poster_path");
				if(posterName!=null && !"null".equals(posterName)){
					found = true;
					String id = jsonObject.getString("id");
					
					String overview = jsonObject.getString("overview");
					String release_date = jsonObject.getString("release_date");
				
					ImageItem imgItm = new ImageItem(null, id, title, posterName
							, release_date, "120", overview);
					data.add(imgItm);
					Log.v("ben", "*********");
					Log.v("ben", "title " + title);
					Log.v("ben", "url " + "http://image.tmdb.org/t/p/w300" + posterName);
					Log.v("ben", "id " + id);
				} 
				else{
					Log.v("ben", "null " + posterName);
				}
			}


		}
		catch (JSONException e) {
			e.printStackTrace();
		}
	}


	public String buildUrlGet(String endpoint, Map<String, String> params){
		StringBuilder bodyBuilder = new StringBuilder();
		Iterator<Entry<String, String>> iterator = params.entrySet().iterator();
		while (iterator.hasNext()) {
			Entry<String, String> param = iterator.next();
			bodyBuilder.append(param.getKey()).append('=')
			.append(param.getValue());
			if (iterator.hasNext()) {
				bodyBuilder.append('&');
			}
		}
		String body = bodyBuilder.toString();
		return endpoint + "?" + body;
	}
}

