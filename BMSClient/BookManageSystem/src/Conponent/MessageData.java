package Conponent;

import com.company.RentDetail;

import java.io.Serializable;
import java.util.ArrayList;

public class MessageData implements Serializable {
    private int messageType;
    private ArrayList<String> data;
    private ArrayList<ArrayList<String>> dataList;
    private ArrayList<RentDetail> rentDetails;

    public MessageData(int messageType, ArrayList<String> data) {
        this();
        this.messageType = messageType;
        this.data = data;
    }

    public MessageData() {
        this.data = new ArrayList<>();
        this.dataList = new ArrayList<>();
        this.rentDetails = new ArrayList<>();
    }

    public int getMessageType() {
        return messageType;
    }
    public void setMessageType(int messageType) {
        this.messageType = messageType;
    }
    public ArrayList<String> getData() {
        return data;
    }
    public ArrayList<RentDetail> getRentDetails() {return rentDetails;}
    public void setData(ArrayList<String> data) {
        this.data = data;
    }
    public ArrayList<ArrayList<String>> getDataList() {
        return dataList;
    }
    public void setDataList(ArrayList<ArrayList<String>> dataList) {
        this.dataList = dataList;
    }
    public void setRentDetail(ArrayList<RentDetail> rentDetails) {this.rentDetails = rentDetails;}

}
