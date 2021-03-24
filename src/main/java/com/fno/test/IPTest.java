package com.fno.test;

import com.fno.entity.AddressElements;
import com.fno.entity.IPElements;
import com.fno.utils.IPUtils;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class IPTest {
    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
        while(true) {
            String str = in.nextLine();
            IPElements ipElements = IPUtils.isIP(str);
            if(ipElements == null){
                AddressElements addressElements = IPUtils.AddressRange(str);
                if(addressElements == null) System.out.println("不合法");
                else{
                    String startStr = addressElements.getStart();
                    String endStr = addressElements.getEnd();
                    BigInteger count = addressElements.getTotalOfAddress();
                    if(startStr.length() <= 32) {
                        int l = 0, r = 8;
                        List<Long> starts = new ArrayList<>();
                        List<Long> ends = new ArrayList<>();
                        while (r <= startStr.length()) {
                            long start = Long.parseLong(startStr.substring(l, r), 2);
                            long end = Long.parseLong(endStr.substring(l, r), 2);
                            l += 8;
                            r += 8;
                            starts.add(start);
                            ends.add(end);
                        }
                        StringBuilder startBuilder = new StringBuilder();
                        StringBuilder endBuilder = new StringBuilder();
                        for (Long item : starts) {
                            startBuilder.append(item + ".");
                        }
                        startBuilder.deleteCharAt(startBuilder.length() - 1);
                        startStr = String.valueOf(startBuilder);
                        for (Long item : ends) {
                            endBuilder.append(item + ".");
                        }
                        endBuilder.deleteCharAt(endBuilder.length() - 1);
                        endStr = String.valueOf(endBuilder);
                        System.out.println("最小地址: " + startStr + "最大地址: " + endStr + "地址总数:" + count);
                    }else{
                        int l = 0, r = 16;
                        List<String> starts = new ArrayList<>();
                        List<String> ends = new ArrayList<>();
                        while (r <= startStr.length()) {
                            long start = Long.parseLong((startStr.substring(l, r)),2);
                            long end = Long.parseLong((endStr.substring(l, r)),2);
                            String startS = Long.toHexString(start);
                            String endS = Long.toHexString(end);
                            l += 16;
                            r += 16;
                            starts.add(startS);
                            ends.add(endS);
                        }
                        StringBuilder startBuilder = new StringBuilder();
                        StringBuilder endBuilder = new StringBuilder();
                        for (String item : starts) {
                            startBuilder.append(item + ":");
                        }
                        startBuilder.deleteCharAt(startBuilder.length() - 1);
                        startStr = String.valueOf(startBuilder);
                        for (String item : ends) {
                            endBuilder.append(item + ":");
                        }
                        endBuilder.deleteCharAt(endBuilder.length() - 1);
                        endStr = String.valueOf(endBuilder);
                        System.out.println("最小地址: " + startStr + "最大地址: " + endStr + "地址总数:" + count);
                    }
                }
            }else{
                System.out.println("2进制串："+ipElements.getBinaryIP());
            }
        }
    }
}