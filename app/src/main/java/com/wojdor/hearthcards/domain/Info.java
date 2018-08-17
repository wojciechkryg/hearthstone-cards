package com.wojdor.hearthcards.domain;

import java.util.List;

public class Info {

    private String version;
    private List<String> classNames;
    private List<String> standardSets;
    private List<String> locales;

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public List<String> getClassNames() {
        return classNames;
    }

    public void setClassNames(List<String> classNames) {
        this.classNames = classNames;
    }

    public List<String> getStandardSets() {
        return standardSets;
    }

    public void setStandardSets(List<String> standardSets) {
        this.standardSets = standardSets;
    }

    public List<String> getLocales() {
        return locales;
    }

    public void setLocales(List<String> locales) {
        this.locales = locales;
    }
}
