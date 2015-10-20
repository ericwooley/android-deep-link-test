package com.example.eriwoole.deep_link_test;

import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.content.Intent;
import android.webkit.WebView;

import com.google.android.gms.appindexing.Action;
import com.google.android.gms.appindexing.AppIndex;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.PendingResult;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.common.api.Status;


public class WebViewer extends AppCompatActivity {
    private GoogleApiClient mClient;
    private static final Uri BASE_APP_URI = Uri.parse("android-app://com.recipe_app/http/recipe-app.com/recipe/");
    private String TAG = "whatever";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_web_viewer);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });


        mClient = new GoogleApiClient.Builder(this).addApi(AppIndex.APP_INDEX_API).build();

        onNewIntent(getIntent());
    }

    @Override
    public void onStart(){
        super.onStart();
        if (true != false) {
            // Connect your client
            mClient.connect();

            // Define a title for your current page, shown in autocompletion UI
            final String TITLE = "Test";
            final Uri APP_URI = BASE_APP_URI.buildUpon().appendPath().build();

            Action viewAction = Action.newAction(Action.TYPE_VIEW, TITLE, APP_URI);

            // Call the App Indexing API view method
            PendingResult<Status> result = AppIndex.AppIndexApi.start(mClient, viewAction);

            result.setResultCallback(new ResultCallback<Status>() {
                @Override
                public void onResult(Status status) {
                    if (status.isSuccess()) {
                        Log.d(TAG, "App Indexing API: Recorded recipe "
                                + TITLE + " view successfully.");
                    } else {
                        Log.e(TAG, "App Indexing API: There was an error recording the recipe view."
                                + status.toString());
                    }
                }
            });
        }
    }
    protected void onNewIntent(Intent intent) {
        String action = intent.getAction();
        String data = intent.getDataString();
        if (Intent.ACTION_VIEW.equals(action) && data != null) {
            String recipeId = data.substring(data.lastIndexOf("/") + 1);
            Log.d("DATA", data);
            WebView wv = (WebView) findViewById(R.id.webView);
            wv.loadUrl(data);
        }
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_web_viewer, menu);
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
