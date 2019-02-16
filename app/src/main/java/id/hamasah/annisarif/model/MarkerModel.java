package id.hamasah.annisarif.model;

import com.google.android.gms.maps.model.LatLng;
import com.google.maps.android.clustering.ClusterItem;

/**
 * Created by idn on 11/3/2017.
 */

public class MarkerModel implements ClusterItem {
    private final LatLng mPosition;
    private String mTitle;
    private String mSnippet;

    public MarkerModel(LatLng mPosition) {
        this.mPosition = mPosition;
    }

    public MarkerModel(LatLng mPosition, String mTitle, String mSnippet) {
        this.mPosition = mPosition;
        this.mTitle = mTitle;
        this.mSnippet = mSnippet;
    }


//    public MarkerModel(double lat, double lng, String title, String snippet) {
//        mPosition = new LatLng(lat, lng);
//        mTitle = title;
//        mSnippet = snippet;
//    }

    @Override
    public LatLng getPosition() {
        return mPosition;
    }

    @Override
    public String getTitle() {
        return mTitle;
    }

    @Override
    public String getSnippet() {
        return mSnippet;
    }
}
