package com.example.finalyrera;

import android.os.Parcel;
import android.os.Parcelable;

public class Incident implements Parcelable {
    private String id;
    private String incidentType;
    private String location;
    private String description;
    private int intensity;
    private String photoUrl;

    public Incident() {
    }

    public Incident(String id, String incidentType, String location, String description, int intensity, String photoUrl) {
        this.id = id;
        this.incidentType = incidentType;
        this.location = location;
        this.description = description;
        this.intensity = intensity;
        this.photoUrl = photoUrl;
    }

    protected Incident(Parcel in) {
        id = in.readString();
        incidentType = in.readString();
        location = in.readString();
        description = in.readString();
        intensity = in.readInt();
        photoUrl = in.readString();
    }

    public static final Creator<Incident> CREATOR = new Creator<Incident>() {
        @Override
        public Incident createFromParcel(Parcel in) {
            return new Incident(in);
        }

        @Override
        public Incident[] newArray(int size) {
            return new Incident[size];
        }
    };

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(id);
        dest.writeString(incidentType);
        dest.writeString(location);
        dest.writeString(description);
        dest.writeInt(intensity);
        dest.writeString(photoUrl);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    // Getters and setters
    public String getId() { return id; }
    public void setId(String id) { this.id = id; }

    public String getIncidentType() { return incidentType; }
    public void setIncidentType(String incidentType) { this.incidentType = incidentType; }

    public String getLocation() { return location; }
    public void setLocation(String location) { this.location = location; }

    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }

    public int getIntensity() { return intensity; }
    public void setIntensity(int intensity) { this.intensity = intensity; }

    public String getPhotoUrl() { return photoUrl; }
    public void setPhotoUrl(String photoUrl) { this.photoUrl = photoUrl; }

    @Override
    public String toString() {
        return "Incident{" +
                "id='" + id + '\'' +
                ", incidentType='" + incidentType + '\'' +
                ", location='" + location + '\'' +
                ", description='" + description + '\'' +
                ", intensity=" + intensity +
                ", photoUrl='" + photoUrl + '\'' +
                '}';
    }
}
