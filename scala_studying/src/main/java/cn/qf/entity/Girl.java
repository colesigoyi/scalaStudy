package cn.qf.entity;


import java.io.Serializable;

/**
 * @ program: scala_studying
 * @ author:  TaoXueFeng
 * @ create: 2019-11-05 16:50
 * @ desc:  
 **/

public class Girl implements Serializable {
    private String name;
    private Double faceValue ;
    private int age;
    private Double height;
    private Double weight;

    public Girl() {
    }

    public Girl(String name, Double faceValue, int age, Double height, Double weight) {
        this.name = name;
        this.faceValue = faceValue;
        this.age = age;
        this.height = height;
        this.weight = weight;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Double getFaceValue() {
        return faceValue;
    }

    public void setFaceValue(Double faceValue) {
        this.faceValue = faceValue;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public Double getHeight() {
        return height;
    }

    public void setHeight(Double height) {
        this.height = height;
    }

    public Double getWeight() {
        return weight;
    }

    public void setWeight(Double weight) {
        this.weight = weight;
    }

    @Override
    public String toString() {
        return "Girl->{" +
                "name='" + name + '\'' +
                ", faceValue=" + faceValue +
                ", age=" + age +
                ", height=" + height +
                ", weight=" + weight +
                '}';
    }
}
