package com.github.ayvazj.rokontrol.http;


import com.android.volley.NetworkResponse;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.toolbox.HttpHeaderParser;

public class RokuHttpRequest extends Request {
    private Response.Listener listener;

    public RokuHttpRequest(int requestMethod, String url, Response.Listener responseListener, Response.ErrorListener errorListener) {
        super(requestMethod, url, errorListener); // Call parent constructor
        this.listener = responseListener;
    }

    // Same as JsonObjectRequest#parseNetworkResponse
    @Override
    protected Response parseNetworkResponse(NetworkResponse response) {
        if (response.statusCode == RokuHttp.OK) {
            return Response.success(new RokuHttpResponse(response), HttpHeaderParser.parseCacheHeaders(response));
        } else {
            return Response.error(new RokuHttpError(response));
        }
    }

    @Override
    public int compareTo(Request other) {
        return 0;
    }

    @Override
    protected void deliverResponse(Object response) {
        if (listener != null)
            listener.onResponse(response);
    }
}

