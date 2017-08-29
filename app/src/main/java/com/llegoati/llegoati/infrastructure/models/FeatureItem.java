package com.llegoati.llegoati.infrastructure.models;

/**
 * Created by Yansel on 1/3/2017.
 */
public class FeatureItem {
    private String name;
    private String[] values;
    private String selectedValue;

    public FeatureItem(String name, String[] values, String selectedValue) {
        this.name = name;
        this.values = values;
        this.selectedValue = selectedValue;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String[] getValues() {
        return values;
    }

    public void setValues(String[] values) {
        this.values = values;
    }

    public String getSelectedValue() {
        return selectedValue;
    }

    public void setSelectedValue(String selectedValue) {
        this.selectedValue = selectedValue;
    }
}
