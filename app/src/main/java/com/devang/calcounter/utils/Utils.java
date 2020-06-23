package com.devang.calcounter.utils;

import java.text.DecimalFormat;

public class Utils  {

    public static String numberFormat(int value)
    {
        DecimalFormat decimalFormat=new DecimalFormat("#,###,###");
        String formatted=decimalFormat.format(value);
        return formatted;

    }
}
