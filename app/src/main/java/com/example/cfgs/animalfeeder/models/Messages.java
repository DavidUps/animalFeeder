package com.example.cfgs.animalfeeder.models;

import com.google.firebase.storage.StorageReference;

public class Messages {
    String name;
    String pet;
    String message;
    StorageReference imgProfile;

    public Messages(String name, String pet, String message, StorageReference imgProfile) {
        this.name = name;
        this.pet = pet;
        this.message = message;
        this.imgProfile = imgProfile;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPet() {
        return pet;
    }

    public void setPet(String pet) {
        this.pet = pet;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public StorageReference getImgProfile() {
        return imgProfile;
    }

    public void setImgProfile(StorageReference imgProfile) {
        this.imgProfile = imgProfile;
    }
}
