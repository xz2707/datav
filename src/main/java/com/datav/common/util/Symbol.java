package com.datav.common.util;

/**
 *
 */
public class Symbol {

    public static final String Empty = "";
    public static final String SPACE = " ";//空格
    public static final String MINUS_LINE = "-";//中横线,减号
    public static final String ENTER = "\n";
    public static final String EmptyZero = "{0:0}";
    public static final String FENHAO = ";";//分号
    public static final String FENHAO_REG = ";|；";//分号
    public static final String MAOHAO_REG = ":|：";//冒号
    public static final String MAOHAO_1_REG = ":|：|=|_";//冒号
    public static final String DOUHAO = ",";
    public static final String DOUHAO_REG = ",|，";
    public static final String XIEGANG_REG = "/";
    public static final String SHUXIAN_REG = "\\|";
    public static final String XIAHUAXIAN_REG = "_";
    public static final String JINGHAO_REG = "\\#";
    public static final String DENGHAO = "=";
    public static final String AT_REG = "@";

    /**
     *
     * @param str
     * @return
     */
    public static boolean isNullOrEmpty(String str) {
        return null == str || str.trim().isEmpty();
    }
}
