package com.fno.community.controller;

import com.sun.corba.se.impl.resolver.SplitLocalResolverImpl;
import org.elasticsearch.common.logging.JsonThrowablePatternConverter;

import java.util.Arrays;

public class Sort {
    public static void main(String[] args) {
        int[] nums = new int[]{123,321,1,23,21,4,21,321,3,12,3,21,32,1,3,21,23,2,3,2,3,3,3,31,3,21,23,-2413,32,-423,434,23,42,43,2,532};
        guibingSort(nums);
        print(nums);
    }
    public static void guibingSort(int[] nums){
        guibingSort(nums,0,nums.length-1);
    }
    private static void guibingSort(int[] nums, int left, int right){
        if(left >= right) return;
        int mid = (left + right) / 2;
        guibingSort(nums,left,mid);
        guibingSort(nums,mid+1,right);
        merge(nums,left,right,mid);
    }
    private static void merge(int[] nums, int left, int right, int mid){
        int tmpLen = right - left + 1;
        int[] tmp = new int[tmpLen];
        int i = 0;
        int x = left, y = right, z = mid+1;
        while(left <= mid && z <= right){
            if(nums[left] > nums[z]) tmp[i++] = nums[z++];
            else tmp[i++] = nums[left++];
        }
        while(left <= mid) tmp[i++] = nums[left++];
        while(z <= right) tmp[i++] = nums[z++];
        for(int j = x; j <= y; j++){
            nums[j] = tmp[j-x];
        }
    }
    private static void print(int[] nums){
        for(int num : nums) System.out.print(num+" ");
    }
}
