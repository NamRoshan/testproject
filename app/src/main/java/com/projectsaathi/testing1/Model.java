package com.projectsaathi.testing1;

public class Model {
    private int id;
    private  String name;
    private  String age;
    private  String phone;
    private  byte[] image;

    public String getTotal() {
        return total;
    }

    public void setTotal(String total) {
        this.total = total;
    }

    private  String total;


    public Model(int id, String name, String age, String phone, byte[] image,String total) {
        this.id = id;
        this.name = name;
        this.age = age;
        this.phone = phone;
        this.image = image;
        this.total=total;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAge() {
        return age;
    }

    public void setAge(String age) {
        this.age = age;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public byte[] getImage() {
        return image;
    }

    public void setImage(byte[] image) {
        this.image = image;
    }
}
