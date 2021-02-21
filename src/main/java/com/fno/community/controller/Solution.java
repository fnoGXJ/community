package com.fno.community.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Solution {
    public static void main(String[] args) {
        Solution s = new Solution();
        System.out.println(s.maximumScore(new int[]{1, 2, 3}, new int[]{3, 2, 1}));
    }
    public int maximumScore(int[] nums, int[] multipliers) {
        int m = multipliers.length;
        int[][] dp = new int[m+1][m+1];
        int max = 0;
        for(int i = 0; i <= m; i++){
            for(int j = 0; j <= m-i; j++){
                if(i == 0 && j == 0) continue;
                if(i == 0) dp[i][j] = dp[0][j-1] + multipliers[i+j-1]*nums[nums.length-j];
                else if(j == 0) dp[i][j] = dp[i-1][0] + multipliers[i+j-1]*nums[i-1];
                else{
                    dp[i][j] = Math.max(dp[i-1][j]+multipliers[i+j-1]*nums[i-1],dp[i][j-1]+multipliers[i+j-1]*nums[nums.length-j]);
                }
                if(i+j == m) max = Math.max(max,dp[i][j]);
            }
        }
        return max;
    }
}

