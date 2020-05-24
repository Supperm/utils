package number;

import java.math.BigDecimal;
import java.util.regex.Pattern;

import string.StringUtils;

public class NumberUtils {

	/**
     * 功能：判断字符串参数是否为整数
     * 示例：
     * NumberUtil.isNUmber("abc") -> false
     * NumberUtil.isNUmber("5.6") -> false
     * NumberUtil.isNUmber("1234") -> true
     */
    public static boolean isNumber(String src) {
        if (StringUtils.hasLength(src)) {
            String regex = "^[0-9]*$";
            return Pattern.matches(regex, src);
        } else {
            return false;
        }
    }

    /**
     * 功能：判断字符串参数是否全为实数
     * 示例：
     * NumberUtil.isNUmber("abc") -> false
     * NumberUtil.isNUmber("5.6") -> true
     * NumberUtil.isNUmber("1234") -> true
     */
    public static boolean isReal(String src) {
        if (StringUtils.hasLength(src)) {
            String regex = "^\\-?([1-9][0-9]*|0)(\\.[0-9]+)?$";
            return Pattern.matches(regex, src);
        } else {
            return false;
        }
    }

    /**
     * 功能：提供精确的加法运算。
     * @param v1 被加数
     * @param v2 加数
     * @return 两个参数的和
     */
    public static double add(double v1, double v2) {
        BigDecimal bigDecimal1 = new BigDecimal(Double.toString(v1));
        BigDecimal bigDecimal2 = new BigDecimal(Double.toString(v2));
        return bigDecimal1.add(bigDecimal2).doubleValue();
    }

    /**
     * 提供精确的减法运算。
     * @param v1 被减数
     * @param v2 减数
     * @return 两个参数的差
     */
    public static double sub(double v1, double v2) {
        BigDecimal bigDecimal1 = new BigDecimal(Double.toString(v1));
        BigDecimal bigDecimal2 = new BigDecimal(Double.toString(v2));
        return bigDecimal1.subtract(bigDecimal2).doubleValue();
    }

    /**
     * 提供精确的乘法运算。
     * @param v1 被乘数
     * @param v2 乘数
     * @return 两个参数的积
     */
    public static double mul(double v1, double v2) {
        BigDecimal bigDecimal1 = new BigDecimal(Double.toString(v1));
        BigDecimal bigDecimal2 = new BigDecimal(Double.toString(v2));
        return bigDecimal1.multiply(bigDecimal2).doubleValue();
    }

    /**
     * 提供（相对）精确的除法运算，当发生除不尽的情况时，精确到
     * 小数点以后10位，以后的数字四舍五入。
     * @param v1 被除数
     * @param v2 除数
     * @return 两个参数的商
     */
    public static double div(double v1, double v2) {
        return div(v1, v2, 10);
    }

    /**
     * 提供（相对）精确的除法运算。当发生除不尽的情况时，由scale参数指
     * 定精度，以后的数字四舍五入。
     * @param v1 被除数
     * @param v2 除数
     * @param scale 表示表示需要精确到小数点以后几位。
     * @return 两个参数的商
     */
    public static double div(double v1, double v2, int scale) {
        if (scale < 0) {
            throw new IllegalArgumentException("小数位数必须为正整数或零");
        }
        BigDecimal bigDecimal1 = new BigDecimal(Double.toString(v1));
        BigDecimal b2 = new BigDecimal(Double.toString(v2));
        return bigDecimal1.divide(b2, scale, BigDecimal.ROUND_HALF_UP).doubleValue();
    }

    /**
     * 提供精确的小数位四舍五入处理。
     * @param v 需要四舍五入的数字
     * @param scale 小数点后保留几位
     * @return 四舍五入后的结果
     */
    public static double round(double v, int scale) {
        if (scale < 0) {
            throw new IllegalArgumentException("小数位数必须为正整数或零");
        }
        //将double类型转换为字符串类型
        BigDecimal bigDecimal = new BigDecimal(Double.toString(v));
        //将除数设置为1
        BigDecimal one = new BigDecimal("1");
        //返回传入数值除1并按照需要保留的位数返回
        return bigDecimal.divide(one, scale, BigDecimal.ROUND_HALF_UP).doubleValue();

    }
}
