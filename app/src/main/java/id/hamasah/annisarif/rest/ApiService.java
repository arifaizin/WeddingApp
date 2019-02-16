package id.hamasah.annisarif.rest;

import id.hamasah.annisarif.model.ResponseModel;
import retrofit2.Call;
import retrofit2.http.GET;

/**
 * Created by idn on 8/12/2017.
 */

public interface ApiService {
    @GET("macros/s/AKfycbzxIDNrFY3SwCTsTWWYWGHkI3tdde2nr3PsvYZpP9G7u96eiCY/exec?sheet=Sheet1")
//    Call<ArrayList<WisataModel>> ambilDataWisata();
    Call<ResponseModel> ambilDataWisata();

//    @Multipart
//    @POST("wisata/create_wisata.php")
//    Call<ResponseBody> CREATE_WISATA(@Part MultipartBody.Part file,
//                                     @Part("nama_wisata") RequestBody nama_wisata,
//                                     @Part("gambar_wisata") RequestBody gambar_wisata,
//                                     @Part("deksripsi_wisata") RequestBody deksripsi_wisata,
//                                     @Part("event_wisata") RequestBody event_wisata,
//                                     @Part("latitude_wisata") RequestBody latitude_wisata,
//                                     @Part("longitude_wisata") RequestBody longitude_wisata,
//                                     @Part("alamat_wisata") RequestBody alamat_wisata);
//
//    @FormUrlEncoded
//    @POST("user/kunjungan.php")
//    Call<ResponseBody> KUNJUNGAN(
//            @Field("id_user") String id_user,
//            @Field("id_wisata") String id_wisata
//    );

}
