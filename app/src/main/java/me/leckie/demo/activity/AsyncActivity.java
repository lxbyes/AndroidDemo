package me.leckie.demo.activity;

import android.app.Activity;
import android.os.AsyncTask;
import android.os.Bundle;
import android.widget.ListView;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import me.leckie.demo.R;
import me.leckie.demo.adapter.NewsAdapter;
import me.leckie.demo.bean.NewsBean;

public class AsyncActivity extends Activity {

    private ListView mListView;

    private final static String URL = "http://www.imooc.com/api/teacher?type=4&num=30";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_async);

        initViews();

        new NewsAsyncTask().execute(URL);
    }

    private void initViews() {
        mListView = (ListView) findViewById(R.id.lv);
    }

    private List<NewsBean> getJsonData(String url) {
        List<NewsBean> list = new ArrayList<>();
        try {

            String jsonStr = readStream(new URL(url).openStream());
            JSONObject jsonObject = new JSONObject(jsonStr);
            JSONArray jsonArray = jsonObject.getJSONArray("data");
            for (int i = 0; i < jsonArray.length(); i++) {
                jsonObject = jsonArray.getJSONObject(i);
                NewsBean news = new NewsBean();
                news.setNewsIconUrl(jsonObject.getString("picSmall"));
                news.setNewsTitle(jsonObject.getString("name"));
                news.setNewsContent(jsonObject.getString("description"));
                list.add(news);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return list;
    }

    private String readStream(InputStream is) {
        InputStreamReader isr;
        String result = "";
        try {
            String line = "";
            isr = new InputStreamReader(is, "utf-8");
            BufferedReader br = new BufferedReader(isr);
            while ((line = br.readLine()) != null) {
                result += line;
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return result;
    }

    class NewsAsyncTask extends AsyncTask<String, Void, List<NewsBean>> {

        @Override
        protected List<NewsBean> doInBackground(String... params) {
            return getJsonData(params[0]);
        }

        @Override
        protected void onPostExecute(List<NewsBean> newsBeans) {
            super.onPostExecute(newsBeans);
            NewsAdapter adapter = new NewsAdapter(AsyncActivity.this, newsBeans);
            mListView.setAdapter(adapter);
        }
    }

}

