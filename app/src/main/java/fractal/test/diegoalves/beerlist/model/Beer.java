package fractal.test.diegoalves.beerlist.model;

import android.os.Parcel;
import android.os.Parcelable;

import io.realm.RealmObject;

/**
 * Class that define the model to beer, implements Parcelable to transfer across Activities and
 * extends RealmObject to enables efficiently access to android database
 *
 * Created by diegoalves on 21/04/2017.
 */

public class Beer extends RealmObject implements Parcelable {

    private int id;
    private String name;
    private String tagline;
    private String description;
    private String image_url;

    protected Beer(Parcel in) {
        id = in.readInt();
        name = in.readString();
        tagline = in.readString();
        description = in.readString();
        image_url = in.readString();
    }

    public Beer() {
    }

    public static final Creator<Beer> CREATOR = new Creator<Beer>() {
        @Override
        public Beer createFromParcel(Parcel in) {
            return new Beer(in);
        }

        @Override
        public Beer[] newArray(int size) {
            return new Beer[size];
        }
    };

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getTagline() {
        return tagline;
    }

    public void setTagline(String tagline) {
        this.tagline = tagline;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getImageUrl() {
        return image_url;
    }

    public void setImageUrl(String imageUrl) {
        this.image_url = imageUrl;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(id);
        dest.writeString(name);
        dest.writeString(tagline);
        dest.writeString(description);
        dest.writeString(image_url);
    }

    @Override
    public String toString() {
        return  name;
    }
}
