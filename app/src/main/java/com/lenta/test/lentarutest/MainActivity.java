package com.lenta.test.lentarutest;

import android.support.v4.app.Fragment;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.RelativeLayout;

import com.lenta.test.lentarutest.model.NewsInstance;
import com.lenta.test.lentarutest.model.NewsInstances;
import com.lenta.test.lentarutest.ui.ListItemAdapter;
import com.lenta.test.lentarutest.util.NetManager;
import com.lenta.test.lentarutest.util.NoInternetConnectionException;
import com.lenta.test.lentarutest.util.ServerAnswerListener;
import com.nostra13.universalimageloader.cache.memory.impl.WeakMemoryCache;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.ImageScaleType;
import com.nostra13.universalimageloader.core.display.FadeInBitmapDisplayer;


public class MainActivity extends ActionBarActivity implements ServerAnswerListener {

    private static NewsInstances newsInstances;
    private static ListItemAdapter itemAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initUIM();
        initList();
    }

    @Override
    public void onError(String value) {
    }

    @Override
    public void onLoaded(NetManager caller, boolean ok) {
        RelativeLayout rl = (RelativeLayout) findViewById(R.id.splashScreen);
        rl.setVisibility(View.GONE);
        itemAdapter = new ListItemAdapter(this, newsInstances.getNewsInstanceSet());

        getSupportFragmentManager().beginTransaction()
                .replace(R.id.container, new PlaceholderFragment())
                .commit();
    }

    public static class PlaceholderFragment extends Fragment {

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {

            View rootView = inflater.inflate(R.layout.fragment_rsslist_main, container, false);
            ListView list = (ListView)rootView.findViewById(android.R.id.list);
            list.setAdapter(itemAdapter);

            list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    //Log.d("Pressed", String.valueOf(view.getTag()));

                    NewsInstance newsInst = newsInstances.getNewsInst(position);

                    android.support.v4.app.FragmentTransaction ft = getFragmentManager().beginTransaction();
                    DescriptionFragment descriptionFR = new DescriptionFragment();
                    Bundle bundle = new Bundle();
                    bundle.putString("description", newsInst.getDescription());
                    bundle.putString("imgUrl", newsInst.getImg_url());
                    descriptionFR.setArguments(bundle);
                    ft.replace(R.id.container, descriptionFR, "NewFragmentTag");
                    ft.addToBackStack(null);
                    ft.commit();
                }
            });

            return rootView;
        }
    }

    private void initList() {
        newsInstances = new NewsInstances();
        try {
            newsInstances.sendDataByGet(this);
            newsInstances.setServerListener(this);
        } catch (NoInternetConnectionException e) {
            e.printStackTrace();
        }
    }

    private void initUIM() {

        DisplayImageOptions defaultOptions = new DisplayImageOptions.Builder()
                .cacheOnDisc(true).cacheInMemory(true)
                .imageScaleType(ImageScaleType.EXACTLY)
                .displayer(new FadeInBitmapDisplayer(300)).build();

        ImageLoaderConfiguration config = new ImageLoaderConfiguration.Builder(
                getApplicationContext())
                .defaultDisplayImageOptions(defaultOptions)
                .memoryCache(new WeakMemoryCache())
                .discCacheSize(100 * 1024 * 1024).build();

        ImageLoader.getInstance().init(config);

    }
}
