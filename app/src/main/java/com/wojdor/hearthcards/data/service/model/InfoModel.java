package com.wojdor.hearthcards.data.service.model;

import com.google.gson.annotations.Expose;

import java.util.List;

public class InfoModel {

    @Expose
    private String patch;
    @Expose
    private List<String> classes;
    @Expose
    private List<String> standard;
    @Expose
    private List<String> locales;

    public String getPatch() {
        return patch;
    }

    public void setPatch(String patch) {
        this.patch = patch;
    }

    public List<String> getClasses() {
        return classes;
    }

    public void setClasses(List<String> classes) {
        this.classes = classes;
    }

    public List<String> getStandard() {
        return standard;
    }

    public void setStandard(List<String> standard) {
        this.standard = standard;
    }

    public List<String> getLocales() {
        return locales;
    }

    public void setLocales(List<String> locales) {
        this.locales = locales;
    }
}
