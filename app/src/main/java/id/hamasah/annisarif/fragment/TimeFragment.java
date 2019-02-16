package id.hamasah.annisarif.fragment;


import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import java.util.ArrayList;

import cn.iwgang.countdownview.CountdownView;
import id.hamasah.annisarif.AcaraAdapter;
import id.hamasah.annisarif.R;
import id.hamasah.annisarif.model.AcaraModel;
import id.hamasah.annisarif.model.ResponseModel;
import id.hamasah.annisarif.rest.ApiService;
import id.hamasah.annisarif.rest.RetrofitConfig;
import jp.wasabeef.recyclerview.adapters.SlideInBottomAnimationAdapter;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * A simple {@link Fragment} subclass.
 */
public class TimeFragment extends Fragment {


    private RecyclerView recycler;
    ArrayList<AcaraModel> listData = new ArrayList<>();
    private static final String TAG = "TimeFragment";

    public TimeFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_time, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        //data dummy
        CountdownView mCvCountdownView = view.findViewById(R.id.cv_countdownViewTest1);
        mCvCountdownView.start(995550000); // Millisecond

// or
//        for (int time=0; time<1000; time++) {
//            mCvCountdownView.updateShow(time);
//        }
            listData = new ArrayList<>();
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


        //data online
//        ambilDataOnline();

        //setup recyclerview
        recycler = (RecyclerView) view.findViewById(R.id.recycler);
        recycler.setLayoutManager(new LinearLayoutManager(getActivity()));
//        recycler.setAdapter(new WisataAdapter(getActivity(), listData));
        SlideInBottomAnimationAdapter anim = new SlideInBottomAnimationAdapter(new AcaraAdapter(getActivity(), listData));
        anim.setFirstOnly(false);
        recycler.setAdapter(anim);

    }

    private void ambilDataOnline() {
        //jika ada JsonObjectnya dulu
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
//                             Log.d(TAG, "onResponseMessage: "+ response.body().getSuccess());
                             if (response.isSuccessful()) {

//                                 if (response.body().getSuccess().toString().equals("true")){
                                 listData = response.body().getSheet1();
                                 for (int i = 0; i < listData.size(); i++) {
                                     Log.d(TAG, "onResponse: " + listData.get(i).getNamaacara());
                                 }

                                 recycler.setAdapter(new AcaraAdapter(getActivity(), listData));
//                                 }

                             } else {
                                 Toast.makeText(getActivity(), "Response Not Successful", Toast.LENGTH_SHORT).show();
                             }
                         }

                         @Override
                         public void onFailure(Call<ResponseModel> call, Throwable t) {
                             Toast.makeText(getActivity(), "Response Failure : " + t.getMessage(), Toast.LENGTH_SHORT).show();
                         }
                     }
        );

    }

}
