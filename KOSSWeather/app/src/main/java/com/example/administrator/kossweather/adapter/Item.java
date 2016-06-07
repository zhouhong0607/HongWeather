package com.example.administrator.kossweather.adapter;

/**
 * Created by Administrator on 2016/6/7.
 */
public class Item
{
    private String txt;
    private String txtColor;

    public  Item()
    {
        this.txtColor="#0dddb8";
    }

    public String getTxtColor() {
        return txtColor;
    }
    public void setTxtColor(String txtColor) {
        this.txtColor = txtColor;
    }

    public String getTxt() {
        return txt;
    }

    public void setTxt(String txt) {
        this.txt = txt;
    }
}
