package com.company;

import java.io.Serializable;
import java.util.ArrayList;

public class RentDetail implements Serializable {
    private double fineMount;
    private int rentNum;
    private ArrayList<String> books;
    public RentDetail() {
        books = new ArrayList<>();
    }

    public void setBooks(ArrayList<String> books) {
        this.books = books;
    }

    public void setRentNum(int rentNum) {
        this.rentNum = rentNum;
    }

    public void setFineMount(double fineMount) {
        this.fineMount = fineMount;
    }

    public double getFineMount() {
        return fineMount;
    }

    public int getRentNum() {
        return rentNum;
    }

    public ArrayList<String> getBooks() {
        return books;
    }
}