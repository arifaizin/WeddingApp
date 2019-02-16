package id.hamasah.annisarif.fragment;


import android.Manifest;
import android.app.ProgressDialog;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.MapsInitializer;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.maps.android.clustering.ClusterManager;

import java.util.ArrayList;

import id.hamasah.annisarif.R;
import id.hamasah.annisarif.model.AcaraModel;
import id.hamasah.annisarif.model.MarkerModel;
import id.hamasah.annisarif.model.ResponseModel;
import id.hamasah.annisarif.rest.ApiService;
import id.hamasah.annisarif.rest.RetrofitConfig;
import pub.devrel.easypermissions.AfterPermissionGranted;
import pub.devrel.easypermissions.EasyPermissions;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * A simple {@link Fragment} subclass.
 */
public class MapsFragment extends Fragment implements OnMapReadyCallback {


    private MapView mMapView;

    public MapsFragment() {
        // Required empty public constructor
    }
    private static final int RC_LOCATION = 100;
    private GoogleMap mMap;
    private ClusterManager<MarkerModel> cluster;
    ArrayList<AcaraModel> listData = new ArrayList<>();
    private static final String TAG = "MapsActivity";

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_maps, container, false);
        // Inflate the layout for this fragment
        mMapView = (MapView) rootView.findViewById(R.id.mapView);
        mMapView.onCreate(savedInstanceState);
        mMapView.onResume();
        try {
            MapsInitializer.initialize(getActivity().getApplicationContext());
        } catch (Exception e) {
            e.printStackTrace();
        }

        mMapView.getMapAsync(this);
        AcaraModel data1 = new AcaraModel();
        data1.setNamaacara("Walimah Ungaran");
        data1.setTanggal("28 April 2018");
        data1.setWaktu("10.00-12.00");
        data1.setLokasi("Masjid Agung Al-Mabrur Ungaran");
        data1.setLatitude("-7.135316");
        data1.setLongitude("110.4070666");
        listData.add(data1);

        AcaraModel data2 = new AcaraModel();
        data2.setNamaacara("Walimah Ambarawa");
        data2.setTanggal("29 April 2018");
        data2.setWaktu("09.00-17.00");
        data2.setLokasi("Kupang Tanjungsari 006/011");
        data2.setLatitude("-7.252304");
        data2.setLongitude("110.416031");
        listData.add(data2);

//        ambilDataOnline();

        return rootView;
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        // Add a marker in Sydney and move the camera
//        LatLng sydney = new LatLng(-34, 151);
//        mMap.addMarker(new MarkerOptions().position(sydney).title("Marker in Sydney"));
//        mMap.moveCamera(CameraUpdateFactory.newLatLng(sydney));

        mMap.getUiSettings().setZoomControlsEnabled(true);
        mMap.getUiSettings().setMyLocationButtonEnabled(true);
        locationPermission();

        cluster = new ClusterManager<MarkerModel>(getActivity(), mMap);

        LatLngBounds.Builder builder = new LatLngBounds.Builder();
        for (int i = 0; i < listData.size(); i++) {
            Log.d(TAG, "onResponse: " + listData.get(i).getLatitude() + "," + listData.get(i).getLongitude());
            Double latWisata = Double.valueOf(listData.get(i).getLatitude());
            Double lngWisata = Double.valueOf(listData.get(i).getLongitude());

            LatLng lokasiwisata = new LatLng(latWisata, lngWisata);
            mMap.addMarker(new MarkerOptions().position(lokasiwisata)
                    .title(listData.get(i).getNamaacara())
                    .snippet(listData.get(i).getLokasi())
            );
            mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(lokasiwisata, 13));

            builder.include(lokasiwisata);

            //clustering
            MarkerModel offsetItem = new MarkerModel(lokasiwisata, listData.get(i).getLokasi(), String.valueOf(i));
            cluster.addItem(offsetItem);

        }

        //supaya semua marker terlihat
//                                 int width = getResources().getDisplayMetrics().widthPixels;
//                                 int height = getResources().getDisplayMetrics().heightPixels;
        DisplayMetrics displaymetrics = new DisplayMetrics();
        getActivity().getWindowManager().getDefaultDisplay().getMetrics(displaymetrics);
        int height = displaymetrics.heightPixels;
        int width = displaymetrics.widthPixels;
        int padding = (int) (width * 0.40); // offset from edges of the map 10% of screen

        LatLngBounds bounds = builder.build();
        mMap.animateCamera(CameraUpdateFactory.newLatLngBounds(bounds, width, height, padding));

//        LatLngBounds.Builder builder = new LatLngBounds.Builder();
////        for (int i = 0; i < listData.size(); i++) {
////            Log.d(TAG, "onResponse: " + listData.get(i).getLatitudeWisata() + "," + listData.get(i).getLongitudeWisata());
////            Double latWisata = Double.valueOf(listData.get(i).getLatitudeWisata());
////            Double lngWisata = Double.valueOf(listData.get(i).getLongitudeWisata());
//
////            LatLng lokasiwisata = new LatLng(latWisata, lngWisata);
//        LatLng lokasi1 = new LatLng(-7.1350047, 110.4095773);
//        LatLng lokasi2 = new LatLng(-7.252304, 110.416031);
////                                         mMap.addMarker(new MarkerOptions().position(lokasi1)
////                                                 .title(listData.get(i).getNamaWisata())
////                                                 .snippet(String.valueOf(i))
////                                         );
//        mMap.addMarker(new MarkerOptions().position(lokasi1)
//                .title("Walimah Ungaran")
//                .snippet("Masjid Agung Ungaran")
//        ).showInfoWindow();
//
//        mMap.addMarker(new MarkerOptions().position(lokasi2)
//                .title("Walimah Ambarawa")
//                .snippet("Kupang Tanjungsari 006/011")
//        );
//
//
//
//        mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(lokasi1, 13));
//
////            builder.include(lokasiwisata);
//        builder.include(lokasi1);
//        builder.include(lokasi2);
//
//        //clustering
//        MarkerModel offsetItem = new MarkerModel(lokasi1, "Walimah Ungaran", "Masjid Agung Ungaran");
//        MarkerModel offsetItem2 = new MarkerModel(lokasi2, "Walimah Ambarawa", "Kupang Tanjungsari 006/011");
//        cluster.addItem(offsetItem);
//        cluster.addItem(offsetItem2);
////            cluster.addItem(offsetItem);
////        }
//
//        //supaya semua marker terlihat
//        int width = getResources().getDisplayMetrics().widthPixels;
//        int height = getResources().getDisplayMetrics().heightPixels;
//        int padding = (int) (width * 0.40); // offset from edges of the map 10% of screen
//
//        LatLngBounds bounds = builder.build();
//        mMap.animateCamera(CameraUpdateFactory.newLatLngBounds(bounds, width, height, padding));


    }

    @AfterPermissionGranted(RC_LOCATION)
    private void locationPermission() {
        String[] perms = {Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION};
        if (EasyPermissions.hasPermissions(getActivity(), perms)) {
            // Already have permission, do the thing
            // ...
            if (ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                // TODO: Consider calling
                //    ActivityCompat#requestPermissions
                // here to request the missing permissions, and then overriding
                //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                //                                          int[] grantResults)
                // to handle the case where the user grants the permission. See the documentation
                // for ActivityCompat#requestPermissions for more details.
                return;
            }
            mMap.setMyLocationEnabled(true);

        } else {
            // Do not have permissions, request them now
            EasyPermissions.requestPermissions(getActivity(), "Minta Ijin",
                    RC_LOCATION, perms);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        // Forward results to EasyPermissions
        EasyPermissions.onRequestPermissionsResult(requestCode, permissions, grantResults, getActivity());
        if (ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
        mMap.setMyLocationEnabled(true);

    }


    private void ambilDataOnline() {
        final ProgressDialog progress = new ProgressDialog(getActivity());
        progress.setTitle("Loading");
        progress.setMessage("Mohon Bersabar");
        progress.show();

        ApiService api = RetrofitConfig.getApiService();
        Call<ResponseModel> call = api.ambilDataWisata();
        call.enqueue(new Callback<ResponseModel>() {
                         @Override
                         public void onResponse(Call<ResponseModel> call, Response<ResponseModel> response) {
                             progress.hide();
//                             Log.d(TAG, "onResponseMessage: " + response.body().getSuccess());
                             if (response.isSuccessful()) {

//                                 if (response.body().getSuccess().toString().equals("true")) {
                                 listData = response.body().getSheet1();

                                 LatLngBounds.Builder builder = new LatLngBounds.Builder();
                                 for (int i = 0; i < listData.size(); i++) {
                                     Log.d(TAG, "onResponse: " + listData.get(i).getLatitude() + "," + listData.get(i).getLongitude());
                                     Double latWisata = Double.valueOf(listData.get(i).getLatitude());
                                     Double lngWisata = Double.valueOf(listData.get(i).getLongitude());

                                     LatLng lokasiwisata = new LatLng(latWisata, lngWisata);
                                     mMap.addMarker(new MarkerOptions().position(lokasiwisata)
                                             .title(listData.get(i).getNamaacara())
                                             .snippet(listData.get(i).getLokasi())
                                     );
                                     mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(lokasiwisata, 13));

                                     builder.include(lokasiwisata);

                                     //clustering
                                     MarkerModel offsetItem = new MarkerModel(lokasiwisata, listData.get(i).getLokasi(), String.valueOf(i));
                                     cluster.addItem(offsetItem);

                                 }

                                 //supaya semua marker terlihat
//                                 int width = getResources().getDisplayMetrics().widthPixels;
//                                 int height = getResources().getDisplayMetrics().heightPixels;
                                 DisplayMetrics displaymetrics = new DisplayMetrics();
                                 getActivity().getWindowManager().getDefaultDisplay().getMetrics(displaymetrics);
                                 int height = displaymetrics.heightPixels;
                                 int width = displaymetrics.widthPixels;
                                 int padding = (int) (width * 0.40); // offset from edges of the map 10% of screen

                                 LatLngBounds bounds = builder.build();
                                 mMap.animateCamera(CameraUpdateFactory.newLatLngBounds(bounds, width, height, padding));

                                 //custom info window


//                                 mMap.setInfoWindowAdapter(new GoogleMap.InfoWindowAdapter() {
//                                     @Override
//                                     public View getInfoWindow(Marker marker) {
//                                         View infoview = getLayoutInflater().inflate(R.layout.item_acara, null);
//                                         TextView infoNama = (TextView) infoview.findViewById(R.id.tv_acara);
//                                         TextView infoTgl = (TextView) infoview.findViewById(R.id.tv_tanggal);
//                                         TextView infoWaktu = (TextView) infoview.findViewById(R.id.tv_waktu);
//                                         TextView infoLokasi = (TextView) infoview.findViewById(R.id.tv_lokasi);
//                                         infoNama.setText(marker.getTitle());
//                                         infoLokasi.setText(marker.getSnippet());
////                                         infoTgl.setText(listData.get(finalI).getTanggal());
////                                         infoWaktu.setText(listData.get(finalI).getWaktu());
////                                         infoLokasi.setText(listData.get(finalI).getLokasi());
//                                         return infoview;
//                                     }
//
//                                     @Override
//                                     public View getInfoContents(Marker marker) {
//                                         return null;
//                                     }
//                                 });

                             } else {
                                 Toast.makeText(getActivity(), "Response Not Successful ", Toast.LENGTH_SHORT).show();
                             }
                         }

                         @Override
                         public void onFailure(Call<ResponseModel> call, Throwable t) {
                             Toast.makeText(getActivity(), "Response Failure : " + t.getMessage(), Toast.LENGTH_SHORT).show();
                         }
                     }
        );
    }
    @Override
    public void onResume() {
        super.onResume();
        mMapView.onResume();
    }

    @Override
    public void onPause() {
        super.onPause();
        mMapView.onPause();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mMapView.onDestroy();
    }

    @Override
    public void onLowMemory() {
        super.onLowMemory();
        mMapView.onLowMemory();
    }
}
