package com.intbyte.bw.gameAPI.utils;

public class IdNotFoundException extends RuntimeException{

    private String id;

    public IdNotFoundException(String id){
        this.id = id;
    }
    @Override
    public String getMessage() {
        return  "id \""+id+"\" is not found";
    }
}
