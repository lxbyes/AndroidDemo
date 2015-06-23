package me.leckie.demo.util;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Handler;
import android.os.Message;
import android.util.LruCache;
import android.widget.ImageView;
import android.widget.ListView;

import java.io.BufferedInputStream;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashSet;
import java.util.Set;

import me.leckie.demo.adapter.NewsAdapter;

/**
 * @author leckie
 * @date 6/23/15
 */
public class ImageLoader {

    private ListView mListView;

    private Set<NewsImageAsyncTask> mTasks;

    private ImageView mImageView;

    private LruCache<String, Bitmap> mCache;

    private String mUrl;

    public ImageLoader(ListView listView) {
        mListView = listView;
        mTasks = new HashSet<>();

        int maxMemory = (int) Runtime.getRuntime().maxMemory();
        int cacheSize = maxMemory / 4;
        mCache = new LruCache<String, Bitmap>(cacheSize) {
            @Override
            protected int sizeOf(String key, Bitmap value) {
                // 每次缓存时调用
                return value.getByteCount();
            }
        };
    }

    /**
     * add bitmap to cache<p/>
     *
     * @param key
     * @param value
     */
    public void addBitmap2Cache(String key, Bitmap value) {
        if (getBitmapFromUrl(key) == null) {
            mCache.put(key, value);
        }
    }

    /**
     * get bitmap from cache<p/>
     *
     * @param key
     * @return
     */
    public Bitmap getBitmapFromCache(String key) {
        return mCache.get(key);
    }

    private Handler mHandler = new Handler() {

        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            if (mImageView.getTag().equals(mUrl)) {
                mImageView.setImageBitmap((Bitmap) msg.obj);
            }
        }
    };

    public void showImageByThread(ImageView imageView, final String url) {
        mImageView = imageView;
        mUrl = url;
        new Thread() {
            @Override
            public void run() {
                super.run();
                Bitmap bitmap = getBitmapFromUrl(url);
                Message message = Message.obtain();
                message.obj = bitmap;
                mHandler.sendMessage(message);
            }
        }.start();
    }

    public Bitmap getBitmapFromUrl(String url) {
        Bitmap bitmap = null;
        InputStream is = null;
        HttpURLConnection con = null;
        try {
            URL u = new URL(url);
            con = (HttpURLConnection) u.openConnection();
            is = new BufferedInputStream(con.getInputStream());
            bitmap = BitmapFactory.decodeStream(is);
            return bitmap;
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                is.close();
                con.disconnect();
            } catch (Exception e) {
            }
        }
        return null;
    }

    public void showImageByAsyncTask(ImageView imageView, String url) {
        Bitmap bitmap = getBitmapFromUrl(url);
        if (bitmap == null) {
            new NewsImageAsyncTask(imageView, url).execute(url);
        } else {
            imageView.setImageBitmap(bitmap);
        }
    }

    public void loadImages(int start, int end) {
        for (int i = start; i < end; i++) {
            String url = NewsAdapter.URLS[i];

            Bitmap bitmap = getBitmapFromCache(url);
            if (bitmap == null) {
                NewsImageAsyncTask task = new NewsImageAsyncTask(url);
                task.execute(url);
                mTasks.add(task);
            } else {
                ImageView imageView = (ImageView) mListView.findViewWithTag(url);
                imageView.setImageBitmap(bitmap);
            }
        }
    }

    public void cancelAllTasks() {
        if (mTasks != null) {
            for (NewsImageAsyncTask task : mTasks) {
                task.cancel(false);
            }
        }
    }

    class NewsImageAsyncTask extends AsyncTask<String, Void, Bitmap> {

        private ImageView mImageView;

        private String mUrl;

        public NewsImageAsyncTask(ImageView imageView, String url) {
            mImageView = imageView;
            mUrl = url;
        }

        public NewsImageAsyncTask(String url) {
            mUrl = url;
        }

        @Override
        protected Bitmap doInBackground(String... params) {
            String url = params[0];
            Bitmap bitmap = getBitmapFromUrl(url);
            if (bitmap != null) {
                addBitmap2Cache(url, bitmap);
            }
            return bitmap;
        }

        @Override
        protected void onPostExecute(Bitmap bitmap) {
            super.onPostExecute(bitmap);
            if (mImageView == null) {
                mImageView = (ImageView) mListView.findViewWithTag(mUrl);
            }

            if (mImageView != null && bitmap != null && mImageView.getTag().equals(mUrl)) {
                mImageView.setImageBitmap(bitmap);
            }

            mTasks.remove(this);
        }
    }
}
