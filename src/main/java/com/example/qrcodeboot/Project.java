package com.example.qrcodeboot;

import jakarta.persistence.*;

@Entity
@Table(name = "qrs")
public class Project {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;
    @Column(name = "qr")

    private String qr;


    public Project() {
    }

    public Project(int id, String qr) {
        this.id = id;
        this.qr = qr;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getQr() {
        return qr;
    }

    public void setQr(String qr) {
        this.qr = qr;
    }
}