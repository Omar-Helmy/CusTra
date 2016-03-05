package com.hatly.hatly;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

/**
 * Created by Omar on 23/02/2016.
 */
public class ExploreFragment extends android.support.v4.app.Fragment {

    private ListView listView;
    private Context context;
    private ExploreListAdapter exploreListAdapter;


    public void setContext(Context context) {
        this.context = context;
        exploreListAdapter = new ExploreListAdapter(context);
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        // Inflate the layout for this fragment
        View fragmentLayout = inflater.inflate(R.layout.fragment_explore, container, false);
        listView = (ListView) fragmentLayout.findViewById(R.id.explore_list_view);
        listView.setAdapter(exploreListAdapter);



        return fragmentLayout;

    }

    //////////////////////List Adapter////////////////////////////////
    private class ExploreListAdapter extends BaseAdapter {

        private Context context;
        //private LayoutInflater inflater;

        private TextView username;
        private ImageView productImage;

        public ExploreListAdapter(Context c) {
            context = c;
            //inflater=(LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        }

        public int getCount() {
            return 20;
        }

        public Object getItem(int position) {
            return null;
        }

        public long getItemId(int position) {
            return position;
        }

        // create a new ImageView for each item referenced by the Adapter
        public View getView(int position, View convertView, ViewGroup parent) {

            if (convertView == null) {
                // if it's not recycled, initialize some attributes
                LayoutInflater inflater = LayoutInflater.from(context);
                convertView = inflater.inflate(R.layout.explore_list_item, parent, false);
                username = (TextView) convertView.findViewById(R.id.text_username);
                productImage = (ImageView) convertView.findViewById(R.id.image_content);

            } else {
                // recycle the already inflated view
                username = (TextView) convertView.findViewById(R.id.text_username);
                productImage = (ImageView) convertView.findViewById(R.id.image_content);
            }

            username.setText("Omar Helmy");

            return convertView;
        }

    }

}

