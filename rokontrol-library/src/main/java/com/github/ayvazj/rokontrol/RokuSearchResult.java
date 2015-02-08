package com.github.ayvazj.rokontrol;

import android.os.Parcel;
import android.os.Parcelable;

import org.ray.upnp.ssdp.SSDP;


public class RokuSearchResult implements Parcelable {

    public final RokuSearchResultCode resultCode;
    public final String location;
    public final String uuid;
    public final int maxAge;

    public RokuSearchResult(RokuSearchResultCode resultCode, String uuid, String location, int maxAge) {
        this.resultCode = resultCode;
        this.uuid = uuid;
        this.location = location;
        this.maxAge = maxAge;
    }

    public RokuSearchResult(String responseStr) {
        String startLine = SSDP.parseStartLine(responseStr);
        if (SSDP.SL_OK.equals(startLine)) {
            this.resultCode = RokuSearchResultCode.OK;
        } else {
            this.resultCode = RokuSearchResultCode.ERROR;
        }
        this.uuid = SSDP.parseHeaderValue(responseStr, "USN");
        this.location = SSDP.parseHeaderValue(responseStr, "location");
        String cacheControl = SSDP.parseHeaderValue(responseStr, "Cache-Control");
        this.maxAge = Integer.parseInt(cacheControl.substring(cacheControl.indexOf("=") + 1));
    }


    @Override
    public String toString() {
        return String.format("%s [%s]", this.uuid, location);
    }

    /*
     * Parceable Implementation
     */

    RokuSearchResult(Parcel in) {
        this.resultCode = RokuSearchResultCode.valueOf(in.readString());
        this.location = in.readString();
        this.uuid = in.readString();
        this.maxAge = in.readInt();
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(resultCode.toString());
        dest.writeString(location);
        dest.writeString(uuid);
        dest.writeInt(maxAge);
    }

    @SuppressWarnings("unused")
    public static final Parcelable.Creator<RokuSearchResult> CREATOR = new Parcelable.Creator<RokuSearchResult>() {
        @Override
        public RokuSearchResult createFromParcel(Parcel in) {
            return new RokuSearchResult(in);
        }

        @Override
        public RokuSearchResult[] newArray(int size) {
            return new RokuSearchResult[size];
        }
    };

    /*
     * End Parceable Implementation
     */

    public enum RokuSearchResultCode {
        OK(200),
        ERROR(400);

        private int code;

        RokuSearchResultCode(int code) {
            this.code = code;
        }
    }
}
