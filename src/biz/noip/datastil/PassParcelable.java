package biz.noip.datastil;

import org.json.JSONObject;

import android.os.Parcel;
import android.os.Parcelable;


public class PassParcelable implements Parcelable{
    String mData;
    
	public PassParcelable() {
		// TODO Auto-generated constructor stub
	}

    public int describeContents() {
        return 0;
    }

    public void writeToParcel(Parcel out, int flags) {
        out.writeString(mData);
    }

    public static final Parcelable.Creator<PassParcelable> CREATOR
            = new Parcelable.Creator<PassParcelable>() {
        public PassParcelable createFromParcel(Parcel in) {
            return new PassParcelable(in);
        }

        public PassParcelable[] newArray(int size) {
            return new PassParcelable[size];
        }
    };
    
    private PassParcelable(Parcel in) {
        mData = in.readString();
    }
}
