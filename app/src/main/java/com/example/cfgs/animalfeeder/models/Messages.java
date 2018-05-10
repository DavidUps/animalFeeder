package com.example.cfgs.animalfeeder.models;

import com.google.firebase.storage.StorageReference;

import java.util.HashMap;
import java.util.Map;

public class Messages {
    String name;
    String pet;
    String message;
    String imgProfile;

    public Messages(String name, String pet, String message, String imgProfile) {
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

    public String  getImgProfile() {
        return imgProfile;
    }

    public void setImgProfile(String imgProfile) {
        this.imgProfile = imgProfile;
    }

    public Map<String, Object> toMap() {
        HashMap<String, Object> result = new HashMap<>();
        result.put("name", name);
        result.put("pet", pet);
        result.put("message", message);
        result.put("idProfile", imgProfile);

        return result;
    }
}
