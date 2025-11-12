package com.imo.etl_imo.util;

import java.math.BigDecimal;
import java.math.RoundingMode;

public class NumberFormatter {
    
    public static double formatNumber(double number, int scale){
        return new BigDecimal(String.valueOf(number))
                    .setScale(scale, RoundingMode.HALF_UP)
                    .doubleValue();
    }
    
    public static BigDecimal formatNumberToBigDecimal(double number, int scale){
        return new BigDecimal(String.valueOf(number))
                    .setScale(scale, RoundingMode.HALF_UP);
    }
}
