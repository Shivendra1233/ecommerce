package com.example.ecommerce;

import java.sql.ResultSet;
import static com.example.ecommerce.Login.getEncryptedPassword;

public class Signup {

    public static boolean customerSignUp(String userName, String userEmail, String mobileNumber, String password, String address){

        try {
            String encryptedPassword = getEncryptedPassword(password);
            String register = "INSERT INTO customer(name, email, mobile, password, address) VALUES('" + userName + "', '" + userEmail + "', " + mobileNumber + ", '" + encryptedPassword + "', '" + address + "')";
            DatabaseConnection dbConn = new DatabaseConnection();
            return dbConn.insertUpdate(register);
        } catch (Exception e){
            e.printStackTrace();
        }
        return false;
    }
}