package com.astra.moments.util;

public enum EstatusPagoEnum {
    PENDIENTE("PENDIENTE"),
    PAGADO("PAGADO");

    private String value;
    EstatusPagoEnum(String value){
        this.value = value;
    }

    public String getValue() {
        return value;
    }

    public static EstatusPagoEnum getStatusEnum(String status){
        EstatusPagoEnum estatus = null;
        switch (status) {
            default:
            case "PENDIENTE":
                estatus = PENDIENTE;
                break;
            case "PAGADO":
                estatus = PAGADO;
                break;
        }
        return estatus;
    }
}
