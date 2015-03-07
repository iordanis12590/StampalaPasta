package com.innovationtechnology.iordanis.stampalapasta.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.parse.ParseClassName;
import com.parse.ParseFile;
import com.parse.ParseObject;

/**
 * Created by iordanis on 19/2/2015.
 */
@ParseClassName("Pasta")
public class Pasta extends ParseObject implements Parcelable{

    public Pasta() {

    }

    public String getName() {
        return getString("name");
    }

    public void setName(String name) {
        put("name", name);
    }

    public String getDescription() {
        return getString("description");
    }

    public void setDescription(String description) {
        put("description", description);
    }

    public ParseFile getImageFile() {
        return getParseFile("image");
    }

    public void setImageFile(ParseFile image) {
        put("image", image);
    }

    public ParseFile getShapeFile() { return getParseFile("shape") ; }

    public void setShapeFile(ParseFile shape) { put("shape", shape); }

    public int getCounter() {return getInt("counter"); }

    public void setCounter(int counter) { put("counter", counter); }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(flags);
    }

    public static final Parcelable.Creator<Pasta> CREATOR = new Parcelable.Creator<Pasta>() {

        @Override
        public Pasta createFromParcel(Parcel source) {
            return new Pasta(source);
        }

        @Override
        public Pasta[] newArray(int size) {
            return new Pasta[size];
        }
    };

    private int mData;

    private Pasta(Parcel in){
        mData = in.readInt();
    }

}
