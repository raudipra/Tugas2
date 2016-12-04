package com.example.raudi.tugas2;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.ShareActionProvider;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

/**
 * Created by raudi on 12/4/16.
 */

public class DetailActivity extends ActionBarActivity {

        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_detail);
            if (savedInstanceState == null) {
                getSupportFragmentManager().beginTransaction()
                        .add(R.id.container, new DetailFragment())
                        .commit();
            }
        }


        @Override
        public boolean onCreateOptionsMenu(Menu menu) {
            // Inflate the menu; this adds items to the action bar if it is present.
            getMenuInflater().inflate(R.menu.detail, menu);

            return true;
        }

        @Override
        public boolean onOptionsItemSelected(MenuItem item) {
            // Handle action bar item clicks here. The action bar will
            // automatically handle clicks on the Home/Up button, so long
            // as you specify a parent activity in AndroidManifest.xml.
            int id = item.getItemId();

            //noinspection SimplifiableIfStatement
            if (id == R.id.action_settings) {
                startActivity(new Intent(this, SettingActivity.class));
                return true;
            }

            return super.onOptionsItemSelected(item);
        }

        /**
         * A placeholder fragment containing a simple view.
         */
        public static class DetailFragment extends Fragment {

            private static final String LOG_TAG = DetailFragment.class.getSimpleName();

            private static final String FORECAST_SHARE_HASHTAG = " #SunshineApp";

            private String mDetailForecast;

            public DetailFragment() {
                setHasOptionsMenu(true);
            }

            @Override
            public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                     Bundle savedInstanceState) {
                Intent receivedIntent = getActivity().getIntent();
                View rootView = inflater.inflate(R.layout.fragment_detail, container, false);
                if(receivedIntent!=null && receivedIntent.hasExtra(Intent.EXTRA_TEXT)){
                    mDetailForecast  = receivedIntent.getStringExtra(Intent.EXTRA_TEXT);
                    TextView item = (TextView) rootView.findViewById(R.id.detail_text);
                    item.setText(mDetailForecast);

                }
                return rootView;
            }

            @Override
            public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
                // Inflate the menu; this adds items to the action bar if it is present.
                inflater.inflate(R.menu.detailfragment, menu);

                // Retrieve the share menu item
                MenuItem itemMenu = menu.findItem(R.id.action_share);

                // Get the provider and hold onto it to set/change the share intent.
                ShareActionProvider mShareActionProvider =
                        (ShareActionProvider) MenuItemCompat.getActionProvider(itemMenu);

                // Attach an intent to this ShareActionProvider.  You can update this at any time,
                // like when the user selects a new piece of data they might like to share.
                if (mShareActionProvider != null ) {
                    mShareActionProvider.setShareIntent(createShareForecastIntent());
                } else {
                    Log.d(LOG_TAG, "Share Action Provider is null?");
                }
            }

            private Intent createShareForecastIntent() {
                Intent sIntent = new Intent(Intent.ACTION_SEND);
                sIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_DOCUMENT);
                sIntent.setType("text/plain");
                sIntent.putExtra(Intent.EXTRA_TEXT,
                        mDetailForecast + FORECAST_SHARE_HASHTAG);
                return sIntent;
            }
        }

    }
