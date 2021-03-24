package com.fno.entity;

import java.math.BigInteger;

public class AddressElements {
    private String start;
    private String end;
    private BigInteger totalOfAddress;

    public String getStart() {
        return start;
    }

    public void setStart(String start) {
        this.start = start;
    }

    public String getEnd() {
        return end;
    }

    public void setEnd(String end) {
        this.end = end;
    }

    public BigInteger getTotalOfAddress() {
        return totalOfAddress;
    }

    public void setTotalOfAddress(BigInteger totalOfAddress) {
        this.totalOfAddress = totalOfAddress;
    }
}