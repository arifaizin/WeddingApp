package id.hamasah.annisarif;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.provider.CalendarContract;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.GregorianCalendar;

import id.hamasah.annisarif.model.AcaraModel;

/**
 * Created by idn on 3/8/2018.
 */

public class AcaraAdapter extends RecyclerView.Adapter<AcaraAdapter.MyViewHolder> {
    private Context context;
    private ArrayList<AcaraModel> listData;

    //generate constructor
    public AcaraAdapter(Context context, ArrayList<AcaraModel> listData) {
        this.context = context;
        this.listData = listData;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        //menghubungkan dengan movie_item
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_acara, parent, false);
        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, final int position) {
        //ngapain
        //set data
        holder.tvAcara.setText(listData.get(position).getNamaacara());
        holder.tvTanggal.setText(listData.get(position).getTanggal());
        holder.tvWaktu.setText(listData.get(position).getWaktu());
        holder.tvLokasi.setText(listData.get(position).getLokasi());
//        Glide.with(context)
//                .load("https://drive.google.com/thumbnail?id="+listData.get(position).getGambarWisata()+"&sz=w500-h500")
//                .placeholder(R.drawable.no_image_found)
//                .error(R.drawable.no_image_found)
//                .into(holder.ivItemGambar);

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                Intent pindah = new Intent(context, DetailWisataActivity.class);
//                Bundle data = new Bundle();
//                data.putString(Konstanta.DATA_ID, listData.get(position).getIdWisata());
//                data.putString(Konstanta.DATA_NAMA, listData.get(position).getNamaWisata());
//                data.putString(Konstanta.DATA_GAMBAR, listData.get(position).getGambarWisata());
//                data.putString(Konstanta.DATA_DESKRIPSI, listData.get(position).getDeksripsiWisata());
//                data.putString(Konstanta.DATA_ALAMAT, listData.get(position).getAlamatWisata());
//                data.putString(Konstanta.DATA_LAT, listData.get(position).getLatitudeWisata());
//                data.putString(Konstanta.DATA_LNG, listData.get(position).getLongitudeWisata());
//                data.putString(Konstanta.DATA_KUNJUNGAN, listData.get(position).getJumlah_kunjungan());
//                pindah.putExtras(data);
//                context.startActivity(pindah);
                Intent intent = new Intent(Intent.ACTION_INSERT);
                intent.setType("vnd.android.cursor.item/event");
                intent.putExtra(CalendarContract.Events.TITLE, "Walimah Annis & Arif");
                intent.putExtra(CalendarContract.Events.EVENT_LOCATION, listData.get(position).getLokasi());
                intent.putExtra(CalendarContract.Events.DESCRIPTION, listData.get(position).getTanggal()+", "+listData.get(position).getWaktu());

// Setting dates
                GregorianCalendar calDate;
                if (position == 0){
                    calDate = new GregorianCalendar(2018, 04, 28);
                    intent.putExtra(CalendarContract.EXTRA_EVENT_BEGIN_TIME,
                            calDate.getTimeInMillis());
                    intent.putExtra(CalendarContract.EXTRA_EVENT_END_TIME,
                            calDate.getTimeInMillis());
                } else if (position ==1){
                    calDate = new GregorianCalendar(2018, 04, 29);
                    intent.putExtra(CalendarContract.EXTRA_EVENT_BEGIN_TIME,
                            calDate.getTimeInMillis());
                    intent.putExtra(CalendarContract.EXTRA_EVENT_END_TIME,
                            calDate.getTimeInMillis());
                }



// make it a full day event
                intent.putExtra(CalendarContract.EXTRA_EVENT_ALL_DAY, true);

// make it a recurring Event
                intent.putExtra(CalendarContract.Events.RRULE, "FREQ=WEEKLY;COUNT=11;WKST=SU;BYDAY=TU,TH");

// Making it private and shown as busy
                intent.putExtra(CalendarContract.Events.ACCESS_LEVEL, CalendarContract.Events.ACCESS_PRIVATE);
                intent.putExtra(CalendarContract.Events.AVAILABILITY, CalendarContract.Events.AVAILABILITY_BUSY);
                context.startActivity(intent);
            }
        });

        holder.btnMap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Uri gmmIntentUri = Uri.parse("google.navigation:q="+listData.get(position).getLatitude()+","+listData.get(position).getLongitude());
                Intent mapIntent = new Intent(Intent.ACTION_VIEW, gmmIntentUri);
                mapIntent.setPackage("com.google.android.apps.maps");
                if (mapIntent.resolveActivity(context.getPackageManager()) != null) {
                    context.startActivity(mapIntent);
                }
            }
        });

    }

    @Override
    public int getItemCount() {
        //jumlah list
        return listData.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        //menyambungkan dengan komponen yg di dalam item
        TextView tvAcara, tvTanggal, tvWaktu, tvLokasi;
        TextView btnMap;

        public MyViewHolder(View itemView) {
            super(itemView);
            tvAcara = (TextView) itemView.findViewById(R.id.tv_acara);
            tvTanggal = (TextView) itemView.findViewById(R.id.tv_tanggal);
            tvWaktu = (TextView) itemView.findViewById(R.id.tv_waktu);
            tvLokasi = (TextView) itemView.findViewById(R.id.tv_lokasi);
            btnMap = (TextView) itemView.findViewById(R.id.buttonMap);
        }
    }
}
