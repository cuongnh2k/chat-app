package com.vn.chat.common.utils;

public class StringUtils {

    public static boolean validatorPassword(String pass){
        return pass.matches("^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[\\W])[A-Za-z\\d\\W]{8,50}$");
    }

    public static boolean validatorEmail(String email){
        return email.matches("^\\w+(\\.\\w+)*@\\w+(\\.\\w{2,3})+$");
    }
}
