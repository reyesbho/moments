package com.astra.moments.util;

public enum EstatusEnum {
    BACKLOG("BACKLOG"),
    DONE("DONE"),
    CANCELED("CANCELED"),
    INCOMPLETE("INCOMPLETE");

    private String value;
    EstatusEnum(String value){
        this.value = value;
    }

    public String getValue() {
        return value;
    }

    public static EstatusEnum getStatusEnum(String status){
        EstatusEnum estatus = null;
        switch (status) {
            default:
            case "BACKLOG":
                estatus = BACKLOG;
                break;
            case "DONE":
                estatus = DONE;
                break;
            case "CANCELED":
                estatus = CANCELED;
                break;
            case "INCOMPLETE":
                estatus = INCOMPLETE;
                break;
        }
        return estatus;
    }
}
