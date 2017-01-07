package com.udacity.gradle.builditbigger;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v4.util.Pair;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;

import com.example.dabour.myapplication.backend.myApi.MyApi;
import com.example.jokeactivity.JokeActivity;
import com.google.api.client.extensions.android.http.AndroidHttp;
import com.google.api.client.extensions.android.json.AndroidJsonFactory;
import com.google.api.client.googleapis.services.AbstractGoogleClientRequest;
import com.google.api.client.googleapis.services.GoogleClientRequestInitializer;

import java.io.IOException;
import java.lang.ref.WeakReference;

/**
 * Created by Ali on 1/4/2017.
 */

public class EndpointsAsyncTask   extends AsyncTask<Pair<Context, String>, Void, String> {
    private static MyApi myApiService = null;
    private Context context;
    WeakReference<Activity> mWeakActivity;
    public interface AsyncResponse {
        void processFinish(String output);
    }
    public AsyncResponse delegate = null;
    public EndpointsAsyncTask(AsyncResponse a,Activity activity){
        this.delegate = a;
        mWeakActivity = new WeakReference<Activity>(activity);

    }

   ProgressBar spinner;
//    @Override
//    protected void onPreExecute() {
//        super.onPreExecute();
//        this.dialog.setVisibility(View.VISIBLE);
//
//    }


    @Override
    protected void onPreExecute(){
        super.onPreExecute();
        Activity activity = mWeakActivity.get();
        Log.v("Test","onProgressUpdate");
        if (activity != null) {
            Log.v("Test","Activity not null");
            spinner = (ProgressBar)activity.findViewById(R.id.progressBar1);
            spinner.setVisibility(View.VISIBLE);
        }


    }

    @Override
    protected String doInBackground(Pair<Context, String>... params) {
        context = params[0].first;
        if(myApiService == null) {  // Only do this once
            MyApi.Builder builder = new MyApi.Builder(AndroidHttp.newCompatibleTransport(),
                    new AndroidJsonFactory(), null)
                    // options for running against local devappserver
                    // - 10.0.2.2 is localhost's IP address in Android emulator
                    // - turn off compression when running against local devappserver
                    .setRootUrl("http://10.0.2.2:8080/_ah/api/")
                    .setGoogleClientRequestInitializer(new GoogleClientRequestInitializer() {
                        @Override
                        public void initialize(AbstractGoogleClientRequest<?> abstractGoogleClientRequest) throws IOException {
                            abstractGoogleClientRequest.setDisableGZipContent(true);
                        }
                    });
            // end options for devappserver

            myApiService = builder.build();
        }

        String name = params[0].second;

        try {
            return myApiService.sayHi(name).execute().getData();
        } catch (IOException e) {
            return e.getMessage();
        }
    }

    @Override
    protected void onPostExecute(String result) {
        //Toast.makeText(context, result, Toast.LENGTH_LONG).show();
        //joke=result;
        delegate.processFinish(result);
        spinner.setVisibility(View.GONE);
        Intent intent = new Intent(context, JokeActivity.class);
        intent.putExtra(JokeActivity.JOKE_KEY,result);
        context.startActivity(intent);
        // Log.v("Test",result + " " + joke);
    }
}