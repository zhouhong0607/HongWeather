package com.example.administrator.kossweather.adapter;

/**
 * Created by Administrator on 2016/6/7.
 */
public class Item
{
    private String txt;
    private String txtColor;
    private static final String[] COLOR_STR = {"#0dddb8","#0bd4c3","#03cdcd","#00b1c5","#04b2d1","#04b2d1","#04b2d1"};
    public  Item()
    {
    this.txtColor=COLOR_STR[(int)Math.random()*(7)];
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
