package com.llegoati.llegoati.models;

/**
 * Created by Richard on 5/17/2017.
 */

public class FullSeller extends PartialSeller {

    String mImage;


    public FullSeller(String id, String name, String lastName, String email, String phone, String sex, Double acumulatedPoint, String specification, String image, Boolean vip, String token) {
        super(id, name, lastName, email, phone, sex, acumulatedPoint, specification, image, vip, token,null);
    }


}
