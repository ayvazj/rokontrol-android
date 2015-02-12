package com.github.ayvazj.rokontrol.http;


import com.android.volley.NetworkResponse;

public class RokuHttpResponse {
    public final NetworkResponse response;

    public RokuHttpResponse(NetworkResponse response) {
        this.response = response;
    }
}
