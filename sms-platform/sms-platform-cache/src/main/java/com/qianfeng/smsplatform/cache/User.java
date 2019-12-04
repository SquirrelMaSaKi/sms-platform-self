package com.qianfeng.smsplatform.cache;

import java.io.Serializable;

/**
 * @author damon
 * @Classname User
 * @Date 2019/12/3 21:42
 * @Description TODO
 */
public class User implements Serializable {
    private static final long serialVersionUID = -2967710007706812401L;
    private int id;
    private String name;
    private String address;

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", address='" + address + '\'' +
                '}';
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

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public User() {
    }

    public User(int id, String name, String address) {
        this.id = id;
        this.name = name;
        this.address = address;
    }
}
