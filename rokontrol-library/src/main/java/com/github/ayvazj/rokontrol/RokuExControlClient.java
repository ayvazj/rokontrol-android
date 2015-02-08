package com.github.ayvazj.rokontrol;

import com.squareup.okhttp.Callback;
import com.squareup.okhttp.MediaType;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.RequestBody;
import com.squareup.okhttp.Response;

import java.io.IOException;
import java.util.List;

import okio.BufferedSink;

public class RokuExControlClient {

    private OkHttpClient okhttp;
    private RokuSearchResult rokuSearchResult;

    private RokuExControlClient(RokuSearchResult rokuSearchResult) {
        this.rokuSearchResult = rokuSearchResult;
        this.okhttp = new OkHttpClient();
    }

    public static RokuExControlClient connect(RokuSearchResult rokuSearchResult) {
        RokuExControlClient client = new RokuExControlClient(rokuSearchResult);
        return client;
    }

    public void keyPress(RokuKey key, final KeypressCallback listener) {
        String url = String.format("/keypress/%s", key);
        post(url, new HttpCallback() {
            @Override
            public void onError(Request request, IOException e) {
                listener.onError(request, e);
            }

            @Override
            public void onFailure(Response response, Throwable throwable) {
                listener.onFailure(response, throwable);
            }

            @Override
            public void onSuccess(Response response) {
                listener.onSuccess(response);
            }
        });
    }

    public void keyDown(RokuKey key, final KeypressCallback listener) {
        String url = String.format("/keydown/%s", key);
        post(url, new HttpCallback() {
            @Override
            public void onError(Request request, IOException e) {
                listener.onError(request, e);
            }

            @Override
            public void onFailure(Response response, Throwable throwable) {
                listener.onFailure(response, throwable);
            }

            @Override
            public void onSuccess(Response response) {
                listener.onSuccess(response);
            }
        });
    }

    public void keyUp(RokuKey key, final KeypressCallback listener) {
        String url = String.format("/keyup/%s", key);
        post(url, new HttpCallback() {
            @Override
            public void onError(Request request, IOException e) {
                listener.onError(request, e);
            }

            @Override
            public void onFailure(Response response, Throwable throwable) {
                listener.onFailure(response, throwable);
            }

            @Override
            public void onSuccess(Response response) {
                listener.onSuccess(response);
            }
        });
    }

    public void queryApps(final QueryAppsCallback listener) {
        String url = "/query/apps";
        get(url, new HttpCallback() {
            @Override
            public void onError(final Request request, final IOException e) {
                listener.onError(request, e);
            }

            @Override
            public void onFailure(final Response response, final Throwable throwable) {
                listener.onFailure(response, throwable);
            }

            @Override
            public void onSuccess(final Response response) {
                List<RokuAppInfo> appInfo = null;
                try {
                    appInfo = RokuAppInfo.parseXml(RokuExControlClient.this, response.body().string());
                } catch (IOException e) {
                    e.printStackTrace();
                }
                listener.onSuccess(appInfo);
            }
        });
    }

    public String getIconUrl(String appId) {
        String location = this.rokuSearchResult.location;
        // remove trailing slash
        if (location.endsWith("/")) {
            location = location.substring(0, location.length() - 1);
        }
        String url = String.format("/query/icon/%s", appId);
        return url = String.format("%s%s", location, url);
    }

    public void get(String url, HttpCallback cb) {
        call("GET", url, cb);
    }

    public void post(String url, HttpCallback cb) {
        call("POST", url, cb);
    }

    private void call(String method, String url, final HttpCallback cb) {
        String location = this.rokuSearchResult.location;
        // remove trailing slash
        if (location.endsWith("/")) {
            location = location.substring(0, location.length() - 1);
        }
        url = String.format("%s%s", location, url);
        Request request = new Request.Builder().url(url).method(method, method.equals("GET") ? null : new RequestBody() {
            // don't care much about request body
            @Override
            public MediaType contentType() {
                return null;
            }

            @Override
            public void writeTo(BufferedSink sink) throws IOException {

            }
        }).build();

        okhttp.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Request request, IOException e) {
                cb.onError(request, e);
            }

            @Override
            public void onResponse(Response response) throws IOException {
                if (!response.isSuccessful()) {
                    cb.onFailure(response, null);
                    return;
                }
                cb.onSuccess(response);
            }
        });
    }


    public interface HttpCallback {

        public void onError(final Request request, final IOException e);

        /**
         * called when the server response was not 2xx or when an exception was thrown in the process
         *
         * @param response  - in case of server error (4xx, 5xx) this contains the server response
         *                  in case of IO exception this is null
         * @param throwable - contains the exception. in case of server error (4xx, 5xx) this is null
         */
        public void onFailure(final Response response, final Throwable throwable);

        /**
         * contains the server response
         *
         * @param response
         */
        public void onSuccess(final Response response);
    }

    public interface KeypressCallback {

        public void onError(final Request request, final IOException e);

        /**
         * called when the server response was not 2xx or when an exception was thrown in the process
         *
         * @param response  - in case of server error (4xx, 5xx) this contains the server response
         *                  in case of IO exception this is null
         * @param throwable - contains the exception. in case of server error (4xx, 5xx) this is null
         */
        public void onFailure(final Response response, final Throwable throwable);

        /**
         * contains the server response
         *
         * @param response
         */
        public void onSuccess(final Response response);
    }

    public interface QueryAppsCallback {

        public void onError(final Request request, final IOException e);

        /**
         * called when the server response was not 2xx or when an exception was thrown in the process
         *
         * @param response  - in case of server error (4xx, 5xx) this contains the server response
         *                  in case of IO exception this is null
         * @param throwable - contains the exception. in case of server error (4xx, 5xx) this is null
         */
        public void onFailure(final Response response, final Throwable throwable);

        /**
         * contains the server response
         *
         * @param rokuAppInfoList
         */
        public void onSuccess(final List<RokuAppInfo> rokuAppInfoList);
    }
}
