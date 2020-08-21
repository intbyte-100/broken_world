package com.intbyte.bw.gameAPI.utils;

public class GroupNotFoundException extends  RuntimeException{
    private String nameGroup;

    public GroupNotFoundException(String nameGroup){
        this.nameGroup = nameGroup;
    }
    @Override
    public String getMessage() {
        return  "id group \""+ nameGroup +"\" is not found";
    }
}
