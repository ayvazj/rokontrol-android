package com.github.ayvazj.rokontrol.sample;

import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

import com.github.ayvazj.rokontrol.RokuSearchResult;
import com.github.ayvazj.rokontrol.RokuWifiDiscovery;



import java.util.ArrayList;
import java.util.List;


public class MainActivity extends ActionBarActivity {

    private Button searchButton;
    private ListView listView;
    private ArrayAdapter<RokuSearchResult> adapter;
    private ArrayList<RokuSearchResult> listData;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        listData = new ArrayList<RokuSearchResult>();

        listView = (ListView) findViewById(R.id.list_view);
        adapter = new ArrayAdapter<RokuSearchResult>(this,
                android.R.layout.simple_list_item_1, android.R.id.text1, listData);
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if (listData != null && listData.size() > position) {
                    RokuSearchResult rsr = listData.get(position);
                    startActivity(RokuRemoteActivity.newIntent(MainActivity.this, rsr));
                }
            }
        });
        searchButton = (Button) findViewById(R.id.search_button);
        searchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                RokuWifiDiscovery rokuDiscovery = new RokuWifiDiscovery(MainActivity.this);
                rokuDiscovery.searchForDevicesOnNetwork(new RokuWifiDiscovery.OnSearchForDevicesOnNetworkCompleted() {
                    @Override
                    public void onSearchForDevicesOnNetworkComplete(List<RokuSearchResult> results) {
                        listData.clear();
                        listData.addAll(results);
                        adapter.notifyDataSetChanged();
                    }
                });
            }
        });
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
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
}
