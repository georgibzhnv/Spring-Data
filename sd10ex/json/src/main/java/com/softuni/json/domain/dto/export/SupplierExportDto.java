package com.softuni.json.domain.dto.export;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class SupplierExportDto {
    @Expose
    private long id;
    @Expose
    private String name;
    @Expose
    private int count;

    public SupplierExportDto() {
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }
}
