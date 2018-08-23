package com.wojdor.hearthcards.domain;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.List;

public class VersionInfo implements Parcelable {

    public static final Creator<VersionInfo> CREATOR = new Creator<VersionInfo>() {
        @Override
        public VersionInfo createFromParcel(Parcel in) {
            return new VersionInfo(in);
        }

        @Override
        public VersionInfo[] newArray(int size) {
            return new VersionInfo[size];
        }
    };

    private String version;
    private List<String> classNames;
    private List<String> standardSets;
    private List<String> locales;

    public VersionInfo() {
    }

    protected VersionInfo(Parcel in) {
        version = in.readString();
        classNames = in.createStringArrayList();
        standardSets = in.createStringArrayList();
        locales = in.createStringArrayList();
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public List<String> getClassNames() {
        return classNames;
    }

    public void setClassNames(List<String> classNames) {
        this.classNames = classNames;
    }

    public List<String> getStandardSets() {
        return standardSets;
    }

    public void setStandardSets(List<String> standardSets) {
        this.standardSets = standardSets;
    }

    public List<String> getLocales() {
        return locales;
    }

    public void setLocales(List<String> locales) {
        this.locales = locales;
    }

    @Override
    public boolean equals(Object object) {
        if (object instanceof VersionInfo) {
            return ((VersionInfo) object).getVersion().equals(getVersion());
        }
        return super.equals(object);
    }

    public boolean notEquals(Object object) {
        return !equals(object);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(version);
        dest.writeStringList(classNames);
        dest.writeStringList(standardSets);
        dest.writeStringList(locales);
    }
}
