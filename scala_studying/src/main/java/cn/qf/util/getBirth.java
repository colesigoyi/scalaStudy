package cn.qf.util;/**
 * Description：<br/>
 * Copyright (c) ,2019 , Xuefengtao <br/>
 * This program is protected by copyright laws. <br/>
 * Date： 2019年11月07日
 *
 * @author 陶雪峰
 * @version : 1.0
 */


/**
 * @ program: scala_studying
 * @ author:  TaoXueFeng
 * @ create: 2019-11-07 19:51
 * @ desc:  
 **/

public class getBirth {
    private final static int[] dayArr = new int[] { 20, 19, 21, 20, 21, 22, 23,
            23, 23, 24, 23, 22 };
    private final static String[] constellationArr = new String[] { "摩羯座",
            "水瓶座", "双鱼座", "白羊座", "金牛座", "双子座", "巨蟹座", "狮子座", "处女座", "天秤座",
            "天蝎座", "射手座", "摩羯座" };

    /**
     * Java通过生日计算星座
     *
     * @param month
     * @param day
     * @return
     */
    public static String getConstellation(int month, int day) {
        return day < dayArr[month - 1] ? constellationArr[month - 1]
                : constellationArr[month];
    }

    /**
     * 通过生日计算属相
     *
     * @param year
     * @return
     */
    public static String getYear(int year) {
        if (year < 1900) {
            return "未知";
        }
        int start = 1900;
        String[] years = new String[] { "鼠", "牛", "虎", "兔", "龙", "蛇", "马", "羊",
                "猴", "鸡", "狗", "猪" };
        return years[(year - start) % years.length];
    }
}