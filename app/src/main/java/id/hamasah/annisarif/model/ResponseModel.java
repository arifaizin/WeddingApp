package id.hamasah.annisarif.model;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class ResponseModel {

	@SerializedName("Sheet1")
	private ArrayList<AcaraModel> sheet1 = new ArrayList<>();

	public void setSheet1(ArrayList<AcaraModel> sheet1){
		this.sheet1 = sheet1;
	}

	public ArrayList<AcaraModel> getSheet1(){
		return sheet1;
	}

	@Override
 	public String toString(){
		return 
			"ResponseModel{" +
			"sheet1 = '" + sheet1 + '\'' + 
			"}";
		}
}