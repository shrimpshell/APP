package com.example.hsinhwang.shrimpshell.Classes;

import java.sql.Blob;

public class Customer {
    private String CustomerID, Name, Email, Password, Gender, Phone, Address, Birthday;
    private int discount;
    private int IdCustomer;
    private Blob CustomerPic;

    public Customer(int IdCustomer, String customerID, String name, String email,
                    String password, String gender, String birthday, String phone, String address) {
        super();
        this.IdCustomer = IdCustomer;
        this.CustomerID = customerID;
        this.Name = name;
        this.Email = email;
        this.Gender = gender;
        this.Birthday = birthday;
        this.Phone = phone;
        this.Address = address;

    }

    public Customer(int IdCustomer, String CustomerID, String Name, String Email, String Gender,
                    String Birthday, String Phone, String Address){
        super();
        this.IdCustomer = IdCustomer;
        this.CustomerID = CustomerID;
        this.Name = Name;
        this.Email = Email;
        this.Gender = Gender;
        this.Birthday = Birthday;
        this.Phone = Phone;
        this.Address = Address;
    }

    public Customer(int IdCustomer, String Name, String Email, String Password, String Birthday,
                    String Phone, String Address){
        super();
        this.IdCustomer = IdCustomer;
        this.Name = Name;
        this.Email = Email;
        this.Password = Password;
        this.Birthday = Birthday;
        this.Phone = Phone;
        this.Address = Address;
    }

    public Customer (int IdCustomer, String customerID, String name, String email,
                     String password, String gender, String birthday, String phone, String address, int discount, Blob customerPic){
        super();
        this.IdCustomer = IdCustomer;
        this.CustomerID = customerID;
        this.Name = name;
        this.Email = email;
        this.Password = password;
        this.Gender = gender;
        this.Birthday = birthday;
        this.Phone = phone;
        this.Address = address;
        this.discount = discount;
        this.CustomerPic = customerPic;
    }

    public Customer (String name, String email, String birthday, String phone, String address) {
        this.Name = name;
        this.Email = email;
        this.Birthday = birthday;
        this.Phone = phone;
        this.Address = address;
    }

    public Customer(int IdCustomer, String Password, String Phone, String Address) {
        this.IdCustomer = IdCustomer;
        this.Password = Password;
        this.Phone = Phone;
        this.Address = Address;
    }

    public String getCustomerID() {
        return CustomerID;
    }
    public void setCustomerID(String customerID) {
        CustomerID = customerID;
    }
    public String getName() {
        return Name;
    }
    public void setName(String name) {
        Name = name;
    }
    public String getEmail() {
        return Email;
    }
    public void setEmail(String email) {
        Email = email;
    }
    public String getPassword() {
        return Password;
    }
    public void setPassword(String password) {
        Password = password;
    }
    public String getGender() {
        return Gender;
    }
    public void setGender(String gender) {
        Gender = gender;
    }
    public String getPhone() {
        return Phone;
    }
    public void setPhone(String phone) {
        Phone = phone;
    }
    public String getAddress() {
        return Address;
    }
    public void setAddress(String address) {
        Address = address;
    }
    public int getDiscount() {
        return discount;
    }
    public void setDiscount(int discount) {
        this.discount = discount;
    }
    public Blob getCustomerPic() {
        return CustomerPic;
    }
    public void setCustomerPic(Blob customerPic) {
        CustomerPic = customerPic;
    }
    public int getIdCustomer() {
        return IdCustomer;
    }
    public void setIdCustomer(int idCustomer) {
        this.IdCustomer = idCustomer;
    }
    public String getBirthday() {
        return Birthday;
    }
    public void setBirthday(String birthday) {
        Birthday = birthday;
    }
}

