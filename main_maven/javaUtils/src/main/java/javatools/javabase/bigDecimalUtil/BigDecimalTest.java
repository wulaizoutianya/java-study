package javatools.javabase.bigDecimalUtil;

import java.math.BigDecimal;

public class BigDecimalTest {

	public static void main(String[] args) {
		BigDecimal num1 = new BigDecimal(0.005);
        BigDecimal num2 = new BigDecimal(1000000);
        BigDecimal num3 = new BigDecimal(-1000000);
        //尽量用字符串的形式初始化
        BigDecimal num12 = new BigDecimal("30");
        BigDecimal num22 = new BigDecimal("41");
        BigDecimal num32 = new BigDecimal("23");

        BigDecimal result5 = num2.divide(num1,20,BigDecimal.ROUND_HALF_UP);
        BigDecimal result52 = num22.divide(num12,2,BigDecimal.ROUND_HALF_UP);
        System.out.println(result5 + " _ " + result52);
        System.out.println((int)Math.ceil(new BigDecimal(600).divide(new BigDecimal(501),1,BigDecimal.ROUND_CEILING).doubleValue()));
	}
	
}
