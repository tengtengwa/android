package com.example.calculatorofttw;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 报表工具类
 * @author ZYWANG 2009-7-28
 */
final class ReportUtil {


    /**
     * 格式化数字
     * @param obj 数字对象
     * @param format 格式化字符串
     * @return
     * @author ZYWANG 2009-8-26
     */

/*
    public static String formatNumber(Object obj, String format) {
        if (obj == null)
            return "";

        String s = String.valueOf(obj);
        if (format == null || "".equals(format.trim())) {
            format = "#.00";
        }
        try {
            if (obj instanceof Double || obj instanceof Float) {
                if (format.contains("%")) {
                    NumberFormat numberFormat = NumberFormat.getPercentInstance();
                    s = numberFormat.format(obj);
                } else {
                    DecimalFormat decimalFormat = new DecimalFormat(format);
                    s = decimalFormat.format(obj);
                }
            } else {
                NumberFormat numberFormat = NumberFormat.getInstance();
                s = numberFormat.format(obj);
            }
        } catch (Exception e) {
        }
        return s;
    }*/



    /**
     * 计算字符串四则运算表达式
     * @param string
     * @return
     * @author ZYWANG & TTW
     */
    public static String ComputeString(String string) {
        String regexCheck = "[\\(\\)\\d\\+\\-\\*/\\.]*";

        if (!Pattern.matches(regexCheck, string))    //检测是否是合法的字符
            return string;

        Matcher matcher = null;         //创建匹配器对象
        String temp = "";
        int index = -1;
        String regex = "\\([\\d\\.\\+\\-\\*/]+\\)";// 提取括号表达式
        string = string.replaceAll("\\s", "");// 去除空格
        try {
            Pattern pattern = Pattern.compile(regex);       // 把规则编译成模式对象
            // 循环计算所有括号里的表达式
            while (pattern.matcher(string).find()) {        // 通过find方法就是查找有没有满足条件的子串
                matcher = pattern.matcher(string);
                while (matcher.find()) {
                    temp = matcher.group();
                    index = string.indexOf(temp);
                    string = string.substring(0, index)
                            + computeStirngNoBracket(temp)
                            + string.substring(index + temp.length());
                }
            }
            // 最后计算总的表达式结果
            string = computeStirngNoBracket(string);
        } catch (NumberFormatException e) {
            return e.getMessage();
        }


        string = removeEnd0(string);
        if(isResultLegal(string)){

        }else{
            string = "错误";
        }

        return string;


    }

    //输出结果非法检测
    private static boolean isResultLegal(String string) {
        boolean isLegal = true;
        if("".equals(string)) {

        }else {
            for(int i = 0; i < string.length(); i++){
                if((string.charAt(i) >= '0' && string.charAt(i) <= '9')
                        || string.charAt(i) == '.' || string.charAt(i) == '-' || string.charAt(i) == 'E'){
                    continue;
                }else{
                    isLegal = false;
                    break;
                }
            }

            //负号非法检测
            if(string.indexOf('-') == 0){   //是负数
                if(string.indexOf('-', 1) != -1){
                    isLegal = false;
                }
            }else{
                if(string.length() <= 6){
                    if(string.indexOf('-') != -1){
                        isLegal = false;
                    }
                }else{
                    if(string.indexOf('-', string.length() - 3) == string.length() - 2){
                        if(string.indexOf('E') != string.length() - 3){
                            isLegal = false;
                        }
                    }

                }
            }
        }

        return isLegal;
    }


    //浮点数去除多余的尾零
    private static String removeEnd0(String string) {
        if(string.indexOf('.') != -1){  //是浮点数
            String s1 = string.substring(0, string.indexOf('.'));
            String s2 = string.substring(string.indexOf('.') + 1);
            int i;
            for(i = s2.length() - 1; i >= 0;){
                if(s2.charAt(i) != '0'){
                    break;
                }
                i--;
            }
            String s3 = s2.substring(0, i + 1);

            if(s3.length() == 0){
                string = s1;
            }else{
                string = s1 + "." + s3;
            }
        }
        return string;
    }

    /**
     * 计算不包含括号的表达式
     * @param string
     * @return
     * @author ZYWANG 2009-8-31
     */
    private static String computeStirngNoBracket(String string) {

        //去括号
        while (string.contains("(") || string.contains(")")){
            StringBuilder sb = new StringBuilder(string);
            sb.deleteCharAt(sb.indexOf("("));
            sb.deleteCharAt(sb.indexOf(")"));
            string = sb.toString();
        }

        while (string.contains("+-")){
            StringBuilder sb = new StringBuilder(string);
            sb.deleteCharAt(sb.indexOf("+"));
            string = sb.toString();
        }

        while (string.contains("--")) {
            StringBuilder sb = new StringBuilder(string);
            int index = sb.indexOf("--");
            sb.deleteCharAt(index);
            sb.deleteCharAt(index);
            string = sb.toString();
        }

        if(string.indexOf("+") == 0) {
            StringBuilder sb = new StringBuilder(string);
            sb.deleteCharAt(0);
            string = sb.toString();
        }

        //将负号移到最前面
        if (string.indexOf('-') != -1 && (string.indexOf('*') != -1 || string.indexOf('/') != -1)) {
            StringBuilder sb = new StringBuilder(string);
            sb.deleteCharAt(sb.indexOf("-"));
            sb.insert(0, '-');
            if(sb.indexOf("-", 1) != -1){       //式子中有两个负号
                sb.deleteCharAt(0);
                sb.deleteCharAt(sb.indexOf("-"));
            }
            string = sb.toString();
        }



        if(!string.contains("+") && !string.contains("-") && !string.contains("*") && !string.contains("/")){
            return string;
        }else{

            string = string.replaceAll("(^\\()|(\\)$)", "");
            String regexMultiAndDivision = "[\\d\\.]+(\\*|\\/)[\\d\\.]+";
            String regexAdditionAndSubtraction = "(^\\-)?[\\d\\.]+(\\+|\\-)[\\d\\.]+";

            String temp = "";
            int index = -1;

            // 解析乘除法
            Pattern pattern = Pattern.compile(regexMultiAndDivision);
            Matcher matcher = null;
            while (pattern.matcher(string).find()) {
                matcher = pattern.matcher(string);
                if (matcher.find()) {
                    temp = matcher.group();
                    index = string.indexOf(temp);
                    string = string.substring(0, index) + doMultiAndDivision(temp)
                            + string.substring(index + temp.length());
                }
            }

            // 解析加减法
            pattern = Pattern.compile(regexAdditionAndSubtraction);
            while (pattern.matcher(string).find()) {
                matcher = pattern.matcher(string);
                if (matcher.find()) {
                    temp = matcher.group();
                    index = string.indexOf(temp);
                    if (temp.startsWith("-")) {
                        string = string.substring(0, index)
                                + doNegativeOperation(temp)
                                + string.substring(index + temp.length());
                    } else {
                        string = string.substring(0, index)
                                + doAdditionAndSubtraction(temp)
                                + string.substring(index + temp.length());
                    }
                }
            }

            return string;
        }
    }

    /**
     * 执行乘除法
     * @param string
     * @return
     * @author ZYWANG 2009-8-31
     */
    private static String doMultiAndDivision(String string) {
        String value = "";
//        double d1 = 0;
//        double d2 = 0;
        String[] temp = null;
        if (string.contains("*")) {
            temp = string.split("\\*");
        } else {
            temp = string.split("/");
        }

        if (temp.length < 2)
            return string;

//        d1 = Double.valueOf(temp[0]);
//        d2 = Double.valueOf(temp[1]);
        if (string.contains("*")) {

            BigDecimal bd1 = new BigDecimal(temp[0]);
            BigDecimal bd2 = new BigDecimal(temp[1]);
            value = bd1.multiply(bd2).toString();

//          value = String.valueOf(d1 * d2);
        } else {

            BigDecimal bd1 = new BigDecimal(temp[0]);
            BigDecimal bd2 = new BigDecimal(temp[1]);
            value = bd1.divide(bd2, 15, BigDecimal.ROUND_HALF_UP).toString();


//          value = String.valueOf(d1 / d2);
        }

        return value;
    }

    /**
     * 执行加减法
     * @param string
     * @return
     * @author ZYWANG 2009-8-31
     */
    private static String doAdditionAndSubtraction(String string) {
        double d1 = 0;
        double d2 = 0;
        String[] temp = null;
        String value = "";
        if (string.contains("+")) {
            temp = string.split("\\+");
        } else {
            temp = string.split("\\-");
        }

        if (temp.length < 2)
            return string;

        d1 = Double.valueOf(temp[0]);
        d2 = Double.valueOf(temp[1]);
        if (string.contains("+")) {
            value = String.valueOf(d1 + d2);
        } else {
            value = String.valueOf(d1 - d2);
        }

        return value;
    }

    /**
     * 执行负数运算
     * @param string
     * @return
     * @author ZYWANG 2010-11-8
     */
    private static String doNegativeOperation(String string) {
        String temp = string.substring(1);
        if (temp.contains("+")) {
            temp = temp.replace("+", "-");
        } else {
            temp = temp.replace("-", "+");
        }
        temp = doAdditionAndSubtraction(temp);
        if (temp.startsWith("-")) {
            temp = temp.substring(1);
        } else {
            temp = "-" + temp;
        }
        return temp;
    }

}