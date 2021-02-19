package com.fno.community.controller;

public class Solution {
    public static void main(String[] args) {
        Solution s = new Solution();
        s.removeElement(new int[]{4,5},5);
    }
        public int removeElement(int[] nums, int val) {
            int target = nums.length-1;
            for(int i = 0; i <= target; i++){
                if(nums[i]==val){
                    target = swap(nums,i,target,val);
                }
            }
            return target+1;
        }
        public int swap(int[] nums,int x,int y,int val){
            while(nums[y] != val)y--;
            int tmp = nums[x];
            nums[x] = nums[y];
            nums[y] = tmp;
            return y;
        }
}
