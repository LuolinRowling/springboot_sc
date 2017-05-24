package com.pku.system.util;


import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Array {
    //求两个数组的差集
     public static String[] minus(String[] shortArray, String[] longArray) {
         List<String> shortList = new ArrayList<String>();
         List<String> longList = new ArrayList<String>();
         Collections.addAll(shortList, shortArray);
         Collections.addAll(longList, longArray);

         longList.removeAll(shortList);
         String[] result = (String[]) longList.toArray(new String[0]);

         return result;

     }
}
