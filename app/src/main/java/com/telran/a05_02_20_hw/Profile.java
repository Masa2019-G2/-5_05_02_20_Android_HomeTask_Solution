package com.telran.a05_02_20_hw;

import androidx.annotation.NonNull;

public class Profile {
    String name;
    String lastName;
    String phone;
    String address;

    public Profile() {
    }

    public Profile(String name,
                   String lastName,
                   String phone,
                   String address) {
        this.name = name;
        this.lastName = lastName;
        this.phone = phone;
        this.address = address;
    }

    @Override
    public String toString(){
        return name + "," + lastName + "," + phone + "," + address;
    }

    public static Profile of(@NonNull String str){
        String[] arr = str.split(",");
        return new Profile(arr[0],arr[1],arr[2],arr[3]);
    }
}
