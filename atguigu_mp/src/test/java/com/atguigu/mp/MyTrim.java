package com.atguigu.mp;

import org.junit.Test;

import java.util.Arrays;

public class MyTrim {
    public void myTrim(String s){
        int n = s.length();
        char [] sc = s.toCharArray();
        //修剪前面空格
        for(int i = 0;i < n; i++){
            if(sc[i] == ' '){
                for(int j = i; j < n-1;j ++){
                    sc[j] = sc[j +1];
                }
                i--;
                n--;
            }else break;
        }
        //修剪后面空格
        for(int i = n - 1;i >= 0;i --){
            if(sc[i] == ' '){
                n--;
            }else break;
        }
        //修剪完的数组赋给新数组
        char [] nsc = new char[n];
        for(int i = 0; i < n; i ++){
            nsc[i] = sc[i];
        }
        System.out.println(Arrays.toString(nsc));
    }


    @Test
    public void trimTest(){
        new MyTrim().myTrim("   h h    ");
    }
}
