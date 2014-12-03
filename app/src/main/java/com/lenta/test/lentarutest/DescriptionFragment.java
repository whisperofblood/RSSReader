package com.lenta.test.lentarutest;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;


public class DescriptionFragment extends Fragment {

    private ImageLoader imageLoader;
    private DisplayImageOptions options;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        imageLoader = ImageLoader.getInstance();
        options = new DisplayImageOptions.Builder().cacheInMemory(true)
                .cacheOnDisc(true).resetViewBeforeLoading(true)
                .showImageForEmptyUri(R.drawable.image_13)
                .showImageOnFail(R.drawable.image_13)
                .showImageOnLoading(R.drawable.image_13).build();
    }

    @Override
    public View onCreateView(LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_description, container, false);

        Bundle bundle = this.getArguments();
        if (bundle != null) {
            TextView descTX = (TextView) rootView.findViewById(R.id.newsText);
            descTX.setText(bundle.getString("description"));

            ImageView imageView = (ImageView) rootView.findViewById(R.id.newsImage);
            imageLoader.displayImage(bundle.getString("imgUrl"), imageView, options);
        }

        return rootView;
    }
}
