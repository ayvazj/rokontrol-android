Rokontrol
========

Roku external remote client for Android.

Download
--------

Download [the latest AAR][2] or grab via Maven:
```xml
<dependency>
  <groupId>com.github.ayvazj.android.rokontrol</groupId>
  <artifactId>rokontrol-library</artifactId>
  <version>0.2.0</version>
</dependency>
```
or Gradle:
```groovy
compile 'com.github.ayvazj.android.rokontrol:rokontrol-library:0.2.0@aar'
```

Usage
-----

    RokuWifiDiscovery rokuDiscovery = new RokuWifiDiscovery(MainActivity.this);
    rokuDiscovery.searchForDevicesOnNetwork(new RokuWifiDiscovery.OnSearchForDevicesOnNetworkCompleted() {
        @Override
        public void onSearchForDevicesOnNetworkComplete(List<RokuSearchResult> results) {
            RokuExControlClient rokuClient = RokuExControlClient.connect(MainActivity.this, results.get(0));
            rokuClient.keyPress(RokuKey.HOME, new RokuExControlClient.KeypressCallback() {
                @Override
                public void onError(Request request, IOException e) {
                    // invalid request
                }

                @Override
                public void onFailure(Response response, Throwable throwable) {
                    // roku returned an error
                }

                @Override
                public void onSuccess(RokuHttpResponse response) {
                    // request processed correctly
                }
            });
        }
    });


License
=======

    Copyright 2015 James Ayvaz

    Licensed under the Apache License, Version 2.0 (the "License");
    you may not use this file except in compliance with the License.
    You may obtain a copy of the License at

       http://www.apache.org/licenses/LICENSE-2.0

    Unless required by applicable law or agreed to in writing, software
    distributed under the License is distributed on an "AS IS" BASIS,
    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
    See the License for the specific language governing permissions and
    limitations under the License.

[2]: https://dl.bintray.com/ayvazj/maven/com/github/ayvazj/android/rokontrol/rokontrol-library/0.2.0/#rokontrol-library-0.2.0.aar
