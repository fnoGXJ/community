package com.fno.utils;

import com.fno.entity.AddressElements;
import com.fno.entity.IPElements;

import java.math.BigInteger;
import java.util.Arrays;
import java.util.regex.Pattern;

public class IPUtils {
    private static final String IPV4 = "^(25[0-5]|2[0-4][0-9]|[0-1]{1}[0-9]{2}|[1-9]{1}[0-9]{1}|[1-9])\\.(25[0-5]|2[0-4][0-9]|[0-1]{1}[0-9]{2}|[1-9]{1}[0-9]{1}|[1-9]|0)\\.(25[0-5]|2[0-4][0-9]|[0-1]{1}[0-9]{2}|[1-9]{1}[0-9]{1}|[1-9]|0)\\.(25[0-5]|2[0-4][0-9]|[0-1]{1}[0-9]{2}|[1-9]{1}[0-9]{1}|[0-9])$";
    private static final String IPV6 = "^\\s*((([0-9A-Fa-f]{1,4}:){7}([0-9A-Fa-f]{1,4}|:))|(([0-9A-Fa-f]{1,4}:){6}(:[0-9A-Fa-f]{1,4}|((25[0-5]|2[0-4]\\d|1\\d\\d|[1-9]?\\d)(\\.(25[0-5]|2[0-4]\\d|1\\d\\d|[1-9]?\\d)){3})|:))|(([0-9A-Fa-f]{1,4}:){5}(((:[0-9A-Fa-f]{1,4}){1,2})|:((25[0-5]|2[0-4]\\d|1\\d\\d|[1-9]?\\d)(\\.(25[0-5]|2[0-4]\\d|1\\d\\d|[1-9]?\\d)){3})|:))|(([0-9A-Fa-f]{1,4}:){4}(((:[0-9A-Fa-f]{1,4}){1,3})|((:[0-9A-Fa-f]{1,4})?:((25[0-5]|2[0-4]\\d|1\\d\\d|[1-9]?\\d)(\\.(25[0-5]|2[0-4]\\d|1\\d\\d|[1-9]?\\d)){3}))|:))|(([0-9A-Fa-f]{1,4}:){3}(((:[0-9A-Fa-f]{1,4}){1,4})|((:[0-9A-Fa-f]{1,4}){0,2}:((25[0-5]|2[0-4]\\d|1\\d\\d|[1-9]?\\d)(\\.(25[0-5]|2[0-4]\\d|1\\d\\d|[1-9]?\\d)){3}))|:))|(([0-9A-Fa-f]{1,4}:){2}(((:[0-9A-Fa-f]{1,4}){1,5})|((:[0-9A-Fa-f]{1,4}){0,3}:((25[0-5]|2[0-4]\\d|1\\d\\d|[1-9]?\\d)(\\.(25[0-5]|2[0-4]\\d|1\\d\\d|[1-9]?\\d)){3}))|:))|(([0-9A-Fa-f]{1,4}:){1}(((:[0-9A-Fa-f]{1,4}){1,6})|((:[0-9A-Fa-f]{1,4}){0,4}:((25[0-5]|2[0-4]\\d|1\\d\\d|[1-9]?\\d)(\\.(25[0-5]|2[0-4]\\d|1\\d\\d|[1-9]?\\d)){3}))|:))|(:(((:[0-9A-Fa-f]{1,4}){1,7})|((:[0-9A-Fa-f]{1,4}){0,5}:((25[0-5]|2[0-4]\\d|1\\d\\d|[1-9]?\\d)(\\.(25[0-5]|2[0-4]\\d|1\\d\\d|[1-9]?\\d)){3}))|:)))(%.+)?\\s*$";

    public static IPElements isIP(String ipStr){
        IPElements ipElements = new IPElements();
        if(isIPv4(ipStr)){
            System.out.println("我是ipv4地址~");
            StringBuilder stringBuilder = ipv4ToBinary(ipStr);
            ipElements.setBinaryIP(stringBuilder.toString());
            return ipElements;
        }else{
            if(isIPv6(ipStr)){
                System.out.println("我是ipv6地址~");
                StringBuilder stringBuilder = ipv6ToBinary(ipStr);
                ipElements.setBinaryIP(stringBuilder.toString());
                return ipElements;
            }
            return null;
        }
    }

    public static boolean isIPv4(String ipStr){
        return Pattern.matches(IPV4,ipStr);
    }

    public static boolean isIPv6(String ipStr){
        return Pattern.matches(IPV6,ipStr);
    }

    public static AddressElements AddressRange(String ipStr) {
        AddressElements addressElements = new AddressElements();
        if(isAddressRange(ipStr)){
            int n = ipStr.length(), i = n - 1;
            while(ipStr.charAt(i) != '/'){
                i--;
            }
            String newIpStr = ipStr.substring(0,i);
            int subnetMask = Integer.parseInt(ipStr.substring(i+1,n));
            if(isIPv4(newIpStr)){
                if(subnetMask >= 0 && subnetMask <= 32) {
                    newIpStr = ipv4ToBinary(newIpStr).toString();
                    char[] ipchar = newIpStr.toCharArray();
                    char[] start;
                    char[] end;
                    char[] count = new char[32-subnetMask];
                    int k = 0;
                    while (k < subnetMask) {
                        int c = newIpStr.charAt(k);
                        ipchar[k] = (char)((c & 1)+'0');
                        if(k<32-subnetMask){
                            count[k] = '1';
                        }
                        k++;
                    }
                    start = Arrays.copyOfRange(ipchar, 0, ipchar.length);
                    end = Arrays.copyOfRange(ipchar, 0, ipchar.length);
                    Arrays.fill(start, k, ipchar.length, '0');
                    Arrays.fill(end, k, ipchar.length, '1');
                    String countstr = String.valueOf(count);
                    BigInteger countLong = new BigInteger(countstr,2);
                    addressElements.setStart(String.valueOf(start));
                    addressElements.setEnd(String.valueOf(end));
                    addressElements.setTotalOfAddress(countLong);
                    return addressElements;
                }
            }else if(isIPv6(newIpStr)) {
                if (subnetMask >= 0 && subnetMask <= 128) {
                    newIpStr = ipv6ToBinary(newIpStr).toString();
                    char[] ipchar = newIpStr.toCharArray();
                    char[] start;
                    char[] end;
                    char[] count = new char[128-subnetMask];
                    int k = 0;
                    while (k < subnetMask) {
                        int c = newIpStr.charAt(k);
                        ipchar[k] = (char)((c & 1)+'0');
                        k++;
                    }
                    int j = 0;
                    while(j<128-subnetMask){
                        count[j] = '1';
                        j++;
                    }
                    start = Arrays.copyOfRange(ipchar, 0, ipchar.length);
                    end = Arrays.copyOfRange(ipchar, 0, ipchar.length);
                    Arrays.fill(start, k, ipchar.length, '0');
                    Arrays.fill(end, k, ipchar.length, '1');
                    String countstr = String.valueOf(count);
                    BigInteger countLong = new BigInteger(countstr,2);
                    addressElements.setStart(String.valueOf(start));
                    addressElements.setEnd(String.valueOf(end));
                    addressElements.setTotalOfAddress(countLong);
                    return addressElements;
                }
            }
        }
        return null;
    }

    public static boolean isAddressRange(String ipStr){
        int n = ipStr.length(), i = n - 1;
        while(i >= 0 && ipStr.charAt(i) != '/'){
            if(ipStr.charAt(i) == '.' || ipStr.charAt(i) == ':') return false;
            i--;
        }
        if(i < 0) return false;
        return true;
    }
    private static StringBuilder ipv4ToBinary(String newIpStr){
        String[] strs = newIpStr.split("\\.");
        StringBuilder ip = new StringBuilder();
        for (String str : strs) {
            ip.append(String.format("%08d", Integer.parseInt(Integer.toBinaryString(Integer.parseInt(str)))));
        }
        return ip;
    }
    private static StringBuilder ipv6ToBinary(String newIpStr){
        String[] strs = newIpStr.split("\\:");
        StringBuilder ip = new StringBuilder();
        if(strs.length == 0){
            int k = 128;
            while(k>0){
                ip.append("0");
                k--;
            }
        }
        if(strs.length>0 && strs.length < 8){
            String[] newStrs = new String[8];
            int x = newStrs.length-1, y = strs.length-1;
            int diff = x-strs.length;
            while(y >= 0){
                if(diff >= 0&&"".equals(strs[y])){
                    newStrs[x--] = strs[y];
                    diff--;
                }else{
                    newStrs[x--] = strs[y--];
                }
            }
            strs = newStrs;
        }
        for (String str : strs) {
            int[] cs = new int[4];
            int index = 3;
            for(int k = str.length()-1; k >=0 ; k--){
                char c = str.charAt(k);
                if(c >= '0' && c <= '9') c = (char) (c - '0');
                else if(c >= 'a' && c <= 'f') c = (char) (c - 'a' + 10);
                else if(c >= 'A' && c <= 'F') c = (char) (c - 'A' + 10);
                cs[index--] = c;
            }
            for(int c : cs){
                ip.append(String.format("%04d", Integer.parseInt(Integer.toBinaryString(c))));
            }
        }
        return ip;
    }
}