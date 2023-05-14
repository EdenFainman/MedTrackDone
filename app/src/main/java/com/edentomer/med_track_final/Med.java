package com.edentomer.med_track_final;

public class Med {


    String itembarcode, itemimage,itemname,itemstock, itemdose,itemleaf;

    public Med(String itembarcode, String itemimage, String itemname, String itemstock, String itemdose,String itemleaf) {
        this.itembarcode = itembarcode;
        this.itemimage = itemimage;
        this.itemname = itemname;
        this.itemstock = itemstock;
        this.itemdose = itemdose;
        this.itemleaf = itemleaf;

    }

    public String getItemleaf() {
        return itemleaf;
    }

    public void setItemleaf(String itemleaf) {
        this.itemleaf = itemleaf;
    }

    public String getItemdose() {
        return itemdose;
    }

    public void setItemdose(String itemdose) {
        this.itemdose = itemdose;
    }

    public String getItembarcode() {
        return itembarcode;
    }

    public void setItembarcode(String itembarcode) {
        this.itembarcode = itembarcode;
    }

    public String getItemimage() {
        return itemimage;
    }

    public void setItemimage(String itemimage) {
        this.itemimage = itemimage;
    }

    public String getItemname() {
        return itemname;
    }

    public void setItemname(String itemname) {
        this.itemname = itemname;
    }

    public String getItemstock() {
        return itemstock;
    }

    public void setItemstock(String itemstock) {
        this.itemstock = itemstock;
    }

    public Med(String itembarcode, String itemimage, String itemname, String itemstock) {
        this.itembarcode = itembarcode;
        this.itemimage = itemimage;
        this.itemname = itemname;
        this.itemstock = itemstock;
    }
}
