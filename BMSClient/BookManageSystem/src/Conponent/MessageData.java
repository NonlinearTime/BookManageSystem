package Conponent;

import javax.print.attribute.standard.JobMediaSheetsSupported;
import java.io.Serializable;
import java.util.ArrayList;

public class MessageData implements Serializable {
    private int messageType;
    private ArrayList<String> data;
    private ArrayList<ArrayList<String>> dataList;

    public MessageData(int messageType, ArrayList<String> data) {
        this.messageType = messageType;
        this.data = data;
    }

    public MessageData() {
        this.data = new ArrayList<>();
        this.dataList = new ArrayList<>();
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
    public void setData(ArrayList<String> data) {
        this.data = data;
    }
    public ArrayList<ArrayList<String>> getDataList() {
        return dataList;
    }
    public void setDataList(ArrayList<ArrayList<String>> dataList) {
        this.dataList = dataList;
    }

}
