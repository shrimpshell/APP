package com.example.hsinhwang.shrimpshell.Classes;

public class LogIn {
    private static boolean isLogInCustomer = true;
    private static boolean isLogInEmployee = true;

    public static boolean CustomerLogIn(){
        return isLogInCustomer;
    }

    public static boolean EmployeeLogIn() {
        return isLogInEmployee;
    }
}
