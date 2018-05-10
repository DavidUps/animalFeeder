package com.example.cfgs.animalfeeder.models;

import java.util.HashMap;
import java.util.Map;

public class MessageUpdate {
    String name;
    String pet;
    String message;
    String idProfile;

    public MessageUpdate(String name, String pet, String message, String idProfile) {
        this.name = name;
        this.pet = pet;
        this.message = message;
        this.idProfile = idProfile;
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

    public String getIdProfile() {
        return idProfile;
    }

    public void setIdProfile(String idProfile) {
        this.idProfile = idProfile;
    }

    public Map<String, Object> toMap() {
        HashMap<String, Object> result = new HashMap<>();
        result.put("name", name);
        result.put("pet", pet);
        result.put("message", message);
        result.put("idProfile", idProfile);

        return result;
    }
}
