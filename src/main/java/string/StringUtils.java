package string;

import java.util.regex.Pattern;

public final class StringUtils {

    /**
     * 功能：检查包含空白字符在内的字符系列长度
     * 示例：
     * StringUtil.hasLength(null) -> false
     * StringUtil.hasLength("") -> false
     * StringUtil.hasLength(" ") -> true
     * StringUtil.hasLength("Hello") -> true
     */
    public static boolean hasLength(String src) {
        return src != null && src.length() > 0;
    }

    /**
     * 功能：检查包含空白字符在内的字符系列长度，并处理过滤前后空格
     * 示例：
     * StringUtil.hasText(null) -> false
     * StringUtil.hasText("") -> false
     * StringUtil.hasText(" ") -> false
     * StringUtil.hasText("Hello") -> true
     */
    public static boolean hasText(String src) {
        return src != null && src.trim().length() > 0;
    }

    /**
     * 功能：验证是否为合格邮箱
     * 示例：
     * StringUtil.isEmail(null) -> false
     * StringUtil.isEmail("") -> false
     * StringUtil.isEmail("zhaoluming@") -> false
     * StringUtil.isEmail("zhaoluming@sohu") -> false
     * StringUtil.isEmail("zhaoluming@sohu.com") -> true
     */
    public static boolean isEmail(String email) {
        if (StringUtils.hasLength(email)) {
            String regex = "^[\\w!#$%&'*+/=?^_`{|}~-]+(?:\\.[\\w!#$%&'*+/=?^_`{|}~-]+)*@(?:[\\w](?:[\\w-]*[\\w])?\\.)+[\\w](?:[\\w-]*[\\w])?$";
            return Pattern.matches(regex, email);
        } else {
            return false;
        }
    }

    /**
     * 功能：验证是否为合格手机号
     * 示例：
     * StringUtil.isPhone(null) -> false
     * StringUtil.isPhone("") -> false
     * StringUtil.isPhone("14312039428") -> false
     * StringUtil.isPhone("13900139000") -> true
     */
    public static boolean isPhone(String src) {
        if (StringUtils.hasLength(src)) {
            String regex = "^[1][3,5,7,8][0-9]{9}$";
            return Pattern.matches(regex, src);
        } else {
            return false;
        }
    }

    /**
     * 功能：判断是否全部为字母
     * 示例：
     * StringUtil.isLetter(null) -> false
     * StringUtil.isLetter("") -> false
     * StringUtil.isLetter(".com") -> false
     * StringUtil.isLetter("howSun") -> true
     */
    public static boolean isLetter(String src) {
        if (StringUtils.hasLength(src)) {
            String regex = "[a-zA-Z]+";
            return Pattern.matches(regex, src);
        } else {
            return false;
        }
    }

    /**
     * 功能：隐藏字符串
     * 示例：
     * StringUtil.hidden("13856237928", 3,7) -> "138****7928"
     * 参数1：src，源字符串
     * 参数2：start，从开始的位置隐藏，如果为空，则全部不隐藏，
     * 参数3：end，结束位置，如果为空或为-1，则直到末尾都隐藏，如果超过源字符串长度，则只到末尾
     */
    public static String hidden(String src, Integer start, Integer end) {
        if (StringUtils.hasText(src) && start != null) {
            if (end == null) {
                end = src.length();
            }
            StringBuffer request = new StringBuffer();
            char[] srcs = src.toCharArray();
            for (int i = 0; i < srcs.length; i++) {
                if (i >= start && i <= end) {
                    request.append("*");
                } else {
                    request.append(srcs[i]);
                }
            }
            return request.toString();
        } else {
            return src;
        }
    }
}
