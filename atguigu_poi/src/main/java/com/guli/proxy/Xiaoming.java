package com.guli.proxy;

import lombok.Data;

@Data
public class Xiaoming implements Person{

    private String name;

    private String sex;

    @Override
    public void findLove() {
        System.err.println("找一个高富帅...");
    }


}
