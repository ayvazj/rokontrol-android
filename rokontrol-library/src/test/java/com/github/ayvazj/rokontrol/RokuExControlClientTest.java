package com.github.ayvazj.rokontrol;

import com.android.volley.Request;
import com.android.volley.Response;
import com.github.ayvazj.rokontrol.http.RokuHttpResponse;

import junit.framework.Assert;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.Robolectric;
import org.robolectric.annotation.Config;

import java.io.IOException;
import java.util.List;
import java.util.UUID;

@RunWith(PatchedRobolectricGradleTestRunner.class)
@Config(constants = BuildConfig.class, sdk = 19)
public class RokuExControlClientTest {

    private static final String ROKU_URL = "http://192.168.1.3:8060";

    private RokuExControlClient client;

    @Test
    public void testKeyPress() throws Exception {
        client.keyPress(RokuKey.HOME, new RokuExControlClient.KeypressCallback() {
            @Override
            public void onError(Request request, IOException e) {
                Assert.fail(e.getMessage());
            }

            @Override
            public void onFailure(Response response, Throwable throwable) {
                Assert.fail(throwable.getMessage());
            }

            @Override
            public void onSuccess(RokuHttpResponse response) {
                Assert.assertTrue(response.response.statusCode == 200);
            }
        });
    }

    @Test
    public void testKeyDownUp() throws Exception {
        client.keyDown(RokuKey.HOME, new RokuExControlClient.KeypressCallback() {
            @Override
            public void onError(Request request, IOException e) {
                Assert.fail(e.getMessage());
            }

            @Override
            public void onFailure(Response response, Throwable throwable) {
                Assert.fail(throwable.getMessage());
            }

            @Override
            public void onSuccess(RokuHttpResponse response) {
                Assert.assertTrue(response.response.statusCode == 200);
                client.keyUp(RokuKey.HOME, new RokuExControlClient.KeypressCallback() {
                    @Override
                    public void onError(Request request, IOException e) {
                        Assert.fail(e.getMessage());
                    }

                    @Override
                    public void onFailure(Response response, Throwable throwable) {
                        Assert.fail(throwable.getMessage());
                    }

                    @Override
                    public void onSuccess(RokuHttpResponse response) {
                        Assert.assertTrue(response.response.statusCode == 200);
                    }
                });
            }
        });
    }

    @Test
    public void testDeviceData() throws Exception {
        client.getDeviceData(new RokuExControlClient.DeviceDataCallback() {
            @Override
            public void onError(Request request, IOException e) {
                Assert.fail(e.getMessage());
            }

            @Override
            public void onFailure(Response response, Throwable throwable) {
                Assert.fail(throwable.getMessage());
            }

            @Override
            public void onSuccess(RokuDeviceData rokuDeviceData) {
                Assert.assertNotNull(rokuDeviceData);
                Assert.assertTrue(rokuDeviceData.manufacturer.toUpperCase().contains("ROKU"));

                Assert.assertFalse(rokuDeviceData.serviceList.size() == 0);
            }
        });
    }

    @Test
    public void testQueryApps() throws Exception {
        client.queryApps(new RokuExControlClient.QueryAppsCallback() {
            @Override
            public void onError(Request request, IOException e) {
                Assert.fail(e.getMessage());
            }

            @Override
            public void onFailure(Response response, Throwable throwable) {
                Assert.fail(throwable.getMessage());
            }

            @Override
            public void onSuccess(List<RokuAppInfo> rokuAppInfoList) {
                Assert.assertNotNull(rokuAppInfoList);
                Assert.assertFalse(rokuAppInfoList.size() == 0);
            }
        });
    }

    @Test
    public void testGetIconUrl() throws Exception {
        client.queryApps(new RokuExControlClient.QueryAppsCallback() {
            @Override
            public void onError(Request request, IOException e) {
                Assert.fail(e.getMessage());
            }

            @Override
            public void onFailure(Response response, Throwable throwable) {
                Assert.fail(throwable.getMessage());
            }

            @Override
            public void onSuccess(List<RokuAppInfo> rokuAppInfoList) {
                Assert.assertNotNull(rokuAppInfoList);
                Assert.assertFalse(rokuAppInfoList.size() == 0);

                for (RokuAppInfo app : rokuAppInfoList) {
                    Assert.assertFalse(null == client.getIconUrl(app.id));
                }
            }
        });
    }

    @Test
    public void testQueryDeviceInfo() throws Exception {
        client.queryDeviceInfo(new RokuExControlClient.DeviceInfoCallback() {
            @Override
            public void onError(Request request, IOException e) {
                Assert.fail(e.getMessage());
            }

            @Override
            public void onFailure(Response response, Throwable throwable) {
                Assert.fail(throwable.getMessage());
            }

            @Override
            public void onSuccess(RokuDeviceInfo rokuDeviceInfo) {
                Assert.assertNotNull(rokuDeviceInfo);
                Assert.assertTrue(rokuDeviceInfo.vendorName.toUpperCase().contains("ROKU"));
            }
        });
    }

    @Before
    public void setUp() throws Exception {
        TestActivity activity = Robolectric.setupActivity(TestActivity.class);
        client = RokuExControlClient.connect(activity, new RokuSearchResult(RokuSearchResult.RokuSearchResultCode.OK, UUID.randomUUID().toString(), ROKU_URL, 0));
    }
}