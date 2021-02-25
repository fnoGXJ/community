package com.fno.community.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Solution {
    public static void main(String[] args) {
        Solution s = new Solution();
        s.findNthDigit(1000000000);
    }
    public int findNthDigit(int n) {
        int init = 9, mul = 1,now = 0;
        while(n - init*mul > 0){
            n -= init*mul;
            now += init;
            init *=10;
            mul++;
        }
        int bit = n % mul;
        int num = now + (bit == 0 ? n/mul : n/mul+1);
        if(bit == 0) return num%10;
        else{
            while(bit < mul){
                num/=10;
                bit++;
            }
        }
        return num%10;
    }
}

