package id.hamasah.annisarif.model;

import com.google.gson.annotations.SerializedName;

public class AcaraModel {

	@SerializedName("no")
	private int no;

	@SerializedName("latitude")
	private String latitude;

	@SerializedName("waktu")
	private String waktu;

	@SerializedName("namaacara")
	private String namaacara;

	@SerializedName("tanggal")
	private String tanggal;

	@SerializedName("longitude")
	private String longitude;

	@SerializedName("lokasi")
	private String lokasi;

	public String getLokasi() {
		return lokasi;
	}

	public void setLokasi(String lokasi) {
		this.lokasi = lokasi;
	}

	public void setNo(int no){
		this.no = no;
	}

	public int getNo(){
		return no;
	}

	public void setLatitude(String latitude){
		this.latitude = latitude;
	}

	public String getLatitude(){
		return latitude;
	}

	public void setWaktu(String waktu){
		this.waktu = waktu;
	}

	public String getWaktu(){
		return waktu;
	}

	public void setNamaacara(String namaacara){
		this.namaacara = namaacara;
	}

	public String getNamaacara(){
		return namaacara;
	}

	public void setTanggal(String tanggal){
		this.tanggal = tanggal;
	}

	public String getTanggal(){
		return tanggal;
	}

	public void setLongitude(String longitude){
		this.longitude = longitude;
	}

	public String getLongitude(){
		return longitude;
	}

	@Override
 	public String toString(){
		return 
			"AcaraModel{" +
			"no = '" + no + '\'' + 
			",latitude = '" + latitude + '\'' + 
			",waktu = '" + waktu + '\'' + 
			",namaacara = '" + namaacara + '\'' + 
			",tanggal = '" + tanggal + '\'' + 
			",longitude = '" + longitude + '\'' + 
			"}";
		}
}