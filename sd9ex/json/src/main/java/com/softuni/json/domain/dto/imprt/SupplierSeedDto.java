package com.softuni.json.domain.dto.imprt;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class SupplierSeedDto {
    @Expose
    private String name;
    @Expose
    @SerializedName("is_importer")
    private boolean isImporter;

    public SupplierSeedDto() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean isImporter() {
        return isImporter;
    }

    public void setImporter(boolean importer) {
        isImporter = importer;
    }
}
