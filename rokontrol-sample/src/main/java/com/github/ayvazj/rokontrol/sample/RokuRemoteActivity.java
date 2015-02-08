package com.github.ayvazj.rokontrol.sample;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import com.github.ayvazj.rokontrol.RokuSearchResult;



public class RokuRemoteActivity extends ActionBarActivity implements RokuRemoteFragment.OnFragmentInteractionListener {

    private static final String EXTRA_ROKU_SEARCH_RESULT = "RokuRemoteActivity.RokuSearchResult";

    public static Intent newIntent(Context context, RokuSearchResult rsr) {
        Intent intent = new Intent(context, RokuRemoteActivity.class);
        intent.putExtra(EXTRA_ROKU_SEARCH_RESULT, rsr);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        return intent;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_roku_remote);
        if (savedInstanceState == null) {
            RokuSearchResult rsr = getIntent().getParcelableExtra(EXTRA_ROKU_SEARCH_RESULT);
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.container, RokuRemoteFragment.newInstance(rsr))
                    .commit();
        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_roku_remote, menu);
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
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onFragmentInteraction(Uri uri) {

    }
}
