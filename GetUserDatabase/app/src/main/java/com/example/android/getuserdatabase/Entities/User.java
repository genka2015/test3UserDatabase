package com.example.android.getuserdatabase.Entities;

import android.os.Parcel;
import android.os.Parcelable;


public class User implements Parcelable{

    private String name;
    private String address;
    private String email;
    private String gender;
    private String phone;
    private String cell;
    private String dob;
    private String nat;
    private String registered;
    private String image;
    private long id;

    public User(){

    }

    public static final Creator<User> CREATOR = new Creator<User>() {
        public User createFromParcel(Parcel in) {
            return new User(in);
        }

        public User[] newArray(int size) {
            return new User[size];

        }
    };

    public User(String n, String a, String e, String gen, String p, String c, String d, String nationality, String reg, String img){
        name = n;
        address = a;
        email = e;
        gender = gen;
        phone = p;
        cell = c;
        dob = d;
        nat = nationality;
        registered = reg;
        image = img;
    }

    private User(Parcel in) {
        name = in.readString();
        address = in.readString();
        email = in.readString();
        gender = in.readString();
        phone = in.readString();
        cell = in.readString();
        dob = in.readString();
        nat = in.readString();
        registered = in.readString();
        image = in.readString();
    }

    public String getGender() {
        return gender;
    }

    public String getPhone() {
        return phone;
    }

    public String getCell() {
        return cell;
    }

    public String getDob() {
        return dob;
    }

    public String getNat() {
        return nat;
    }

    public String getRegistered() {
        return registered;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public void setCell(String cell) {
        this.cell = cell;
    }

    public void setDob(String dob) {
        this.dob = dob;
    }

    public void setNat(String nat) {
        this.nat = nat;
    }

    public void setRegistered(String registered) {
        this.registered = registered;
    }

    public void setName(String name){
        this.name = name;
    }

    public void setAddress(String address){
        this.address = address;
    }

    public void setEmail(String email){
        this.email = email;
    }

    public String getName(){
        return name;
    }

    public String getAddress(){
        return address;
    }

    public String getEmail(){
        return email;
    }

    public void setImage(String image){
        this.image = image;
    }

    public String getImage(){
        return image;
    }

    @Override
    public int describeContents() {
        return 0;
    }


    @Override
    public void writeToParcel(Parcel dest, int flags) {

        dest.writeString(name);
        dest.writeString(address);
        dest.writeString(email);
        dest.writeString(gender);
        dest.writeString(phone);
        dest.writeString(cell);
        dest.writeString(dob);
        dest.writeString(nat);
        dest.writeString(registered);
        dest.writeString(image);
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getId() {
        return id;
    }
}
