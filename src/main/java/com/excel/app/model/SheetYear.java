package com.excel.app.model;

public class SheetYear {
    private String year;

    public SheetYear(String year) {
        this.year = year;
    }

    public String getYear() {
        return year;
    }

    public void setYear(String year) {
        this.year = year;
    }

    @Override
    public String toString() {
        return "SheetYear{" +
                "year='" + year + '\'' +
                '}';
    }

    public boolean isSelected(SheetYear sheetYear) {
        return sheetYear.equals(sheetYear);
    }
}
