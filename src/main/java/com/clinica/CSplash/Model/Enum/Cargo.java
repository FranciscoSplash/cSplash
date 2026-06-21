package com.clinica.CSplash.Model.Enum;

public enum Cargo {
    ADMIN("ROLE_ADMIN"),
    USUARIO("ROLE_USER");


    private String role;

    Cargo(String role){
        this.role=role;
    }

    public String getRole(){
        return this.role;
    }
}
