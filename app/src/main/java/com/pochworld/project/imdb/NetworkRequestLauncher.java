package com.pochworld.project.imdb;

import android.os.AsyncTask;

import java.io.IOException;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class NetworkRequestLauncher extends AsyncTask<String, Void, Response> {

    private OkHttpClient client;

    public NetworkRequestLauncher() {
        this.client = new OkHttpClient();
    }

    @Override
    protected Response doInBackground(String... urls) {
        Response response = null;

        Request request = new Request.Builder()
                .url(urls[0])
                .build();

        try {
            response = this.client.newCall(request).execute();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return response;
    }
}