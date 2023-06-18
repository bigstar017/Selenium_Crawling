package com.example.dto;

public class COORDI {

    String COORDI_ID;
    String TOP_ID;
    String BOTTOM_ID;
    String SHOES_ID;
    String OUTER_ID;
    String STYLE_CATEGORY;
    String TOP_CATEGORY;
    String BOTTOM_CATEGORY;
    String SHOES_CATEGORY;
    String OUTER_CATEGORY;

    public COORDI() {

    }

    public COORDI(String COORDI_ID, String TOP_ID, String BOTTOM_ID, String SHOES_ID, String OUTER_ID, String STYLE_CATEGORY, String TOP_CATEGORY, String BOTTOM_CATEGORY, String SHOES_CATEGORY, String OUTER_CATEGORY) {
        this.COORDI_ID = COORDI_ID;
        this.TOP_ID = TOP_ID;
        this.BOTTOM_ID = BOTTOM_ID;
        this.SHOES_ID = SHOES_ID;
        this.OUTER_ID = OUTER_ID;
        this.STYLE_CATEGORY = STYLE_CATEGORY;
        this.TOP_CATEGORY = TOP_CATEGORY;
        this.BOTTOM_CATEGORY = BOTTOM_CATEGORY;
        this.SHOES_CATEGORY = SHOES_CATEGORY;
        this.OUTER_CATEGORY = OUTER_CATEGORY;
    }

    public String getCOORDI_ID() {
        return COORDI_ID;
    }

    public void setCOORDI_ID(String COORDI_ID) {
        this.COORDI_ID = COORDI_ID;
    }

    public String getTOP_ID() {
        return TOP_ID;
    }

    public void setTOP_ID(String TOP_ID) {
        this.TOP_ID = TOP_ID;
    }

    public String getBOTTOM_ID() {
        return BOTTOM_ID;
    }

    public void setBOTTOM_ID(String BOTTOM_ID) {
        this.BOTTOM_ID = BOTTOM_ID;
    }

    public String getSHOES_ID() {
        return SHOES_ID;
    }

    public void setSHOES_ID(String SHOES_ID) {
        this.SHOES_ID = SHOES_ID;
    }

    public String getOUTER_ID() {
        return OUTER_ID;
    }

    public void setOUTER_ID(String OUTER_ID) {
        this.OUTER_ID = OUTER_ID;
    }

    public String getSTYLE_CATEGORY() {
        return STYLE_CATEGORY;
    }

    public void setSTYLE_CATEGORY(String STYLE_CATEGORY) {
        this.STYLE_CATEGORY = STYLE_CATEGORY;
    }

    public String getTOP_CATEGORY() {
        return TOP_CATEGORY;
    }

    public void setTOP_CATEGORY(String TOP_CATEGORY) {
        this.TOP_CATEGORY = TOP_CATEGORY;
    }

    public String getBOTTOM_CATEGORY() {
        return BOTTOM_CATEGORY;
    }

    public void setBOTTOM_CATEGORY(String BOTTOM_CATEGORY) {
        this.BOTTOM_CATEGORY = BOTTOM_CATEGORY;
    }

    public String getSHOES_CATEGORY() {
        return SHOES_CATEGORY;
    }

    public void setSHOES_CATEGORY(String SHOES_CATEGORY) {
        this.SHOES_CATEGORY = SHOES_CATEGORY;
    }

    public String getOUTER_CATEGORY() {
        return OUTER_CATEGORY;
    }

    public void setOUTER_CATEGORY(String OUTER_CATEGORY) {
        this.OUTER_CATEGORY = OUTER_CATEGORY;
    }

    @Override
    public String toString() {
        return "COORDI{" +
                "COORDI_ID='" + COORDI_ID + '\'' +
                ", TOP_ID='" + TOP_ID + '\'' +
                ", BOTTOM_ID='" + BOTTOM_ID + '\'' +
                ", SHOES_ID='" + SHOES_ID + '\'' +
                ", OUTER_ID='" + OUTER_ID + '\'' +
                ", STYLE_CATEGORY='" + STYLE_CATEGORY + '\'' +
                ", TOP_CATEGORY='" + TOP_CATEGORY + '\'' +
                ", BOTTOM_CATEGORY='" + BOTTOM_CATEGORY + '\'' +
                ", SHOES_CATEGORY='" + SHOES_CATEGORY + '\'' +
                ", OUTER_CATEGORY='" + OUTER_CATEGORY + '\'' +
                '}';
    }
}
