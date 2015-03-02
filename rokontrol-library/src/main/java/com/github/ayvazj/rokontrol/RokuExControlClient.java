package com.github.ayvazj.rokontrol;

import android.content.Context;
import android.util.Log;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.github.ayvazj.rokontrol.http.RokuHttpClient;
import com.github.ayvazj.rokontrol.http.RokuHttpError;
import com.github.ayvazj.rokontrol.http.RokuHttpRequest;
import com.github.ayvazj.rokontrol.http.RokuHttpResponse;

import java.io.IOException;
import java.util.List;


public class RokuExControlClient {
    private static final String TAG = "RokuExControlClient";
    private Context context;
    private RokuSearchResult rokuSearchResult;

    private RokuExControlClient(Context context, RokuSearchResult rokuSearchResult) {
        this.context = context;
        this.rokuSearchResult = rokuSearchResult;
    }

    public static RokuExControlClient connect(Context context, RokuSearchResult rokuSearchResult) {
        RokuExControlClient client = new RokuExControlClient(context, rokuSearchResult);
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
            public void onSuccess(RokuHttpResponse response) {
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
            public void onSuccess(RokuHttpResponse response) {
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
            public void onSuccess(RokuHttpResponse response) {
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
            public void onSuccess(final RokuHttpResponse response) {
                List<RokuAppInfo> appInfo = null;
                try {
                    appInfo = RokuAppInfo.parseXml(RokuExControlClient.this, new String(response.response.data, "UTF-8"));
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
        call(Request.Method.GET, url, cb);
    }

    public void post(String url, HttpCallback cb) {
        call(Request.Method.POST, url, cb);
    }

    private void call(int method, String url, final HttpCallback cb) {
        String location = this.rokuSearchResult.location;
        // remove trailing slash
        if (location.endsWith("/")) {
            location = location.substring(0, location.length() - 1);
        }
        url = String.format("%s%s", location, url);

        RokuHttpRequest getRequest = new RokuHttpRequest(method, url,
                new Response.Listener<RokuHttpResponse>() {
                    @Override
                    public void onResponse(RokuHttpResponse response) {
                        // response
                        Log.d(TAG, String.format("Response %s", response.toString()));
                        cb.onSuccess(response);
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        if (error instanceof RokuHttpError) {
                            if (error != null) {
                                Log.d(TAG, String.format("Error.Response %s", error.getMessage() != null ? error.getMessage() : error.toString()));
                            } else {
                                Log.d(TAG, String.format("Error.Response NULL"));
                            }
                        } else {
                            Log.d(TAG, String.format("Error.Response unreachable"));
                        }
                    }
                }
        );
        getRequest.setRetryPolicy(new DefaultRetryPolicy(20 * 1000, 0, 1.0f));
        RokuHttpClient.getInstance(this.context).getRequestQueue().add(getRequest);
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
        public void onSuccess(final RokuHttpResponse response);
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
        public void onSuccess(final RokuHttpResponse response);
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
