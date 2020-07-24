package com.platform.util;

import com.gao.common.PagerInfo;
import com.gao.common.util.StringUtil;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class FreemarkerUtil {
    private static org.apache.log4j.Logger log = org.apache.log4j.LogManager
                                                   .getLogger(FreemarkerUtil.class);

    /**
     * 将unix时间戳转换为yyyy-MM-dd hh:mm:ss 格式字符串
     * @param unixDate
     * @return
     */
    public static String parseUnixDate(long unixDate) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String date = "";
        try {
            long unixLong = unixDate * 1000;
            date = sdf.format(unixLong);
        } catch (Exception ex) {
            log.error("unix日期转换发生异常");
            log.error(ex);
        }
        return date;
    }

    /**
     * 计算商品单品页面的预订商品的到货时间
     * @param bookDays
     * @param shippingTime
     * @return
     */
    public static String calculateBookShippingTimeForProductDetailPage(Integer bookDays,
                                                                       String shippingTime) {
        if (bookDays == null) {
            bookDays = 0;
        }
        if (StringUtil.isEmpty(shippingTime, true)) {
            return String.valueOf(bookDays);
        }
        String ret = null;
        if (shippingTime.indexOf("-") == -1) {
            ret = String.valueOf(bookDays + Integer.parseInt(shippingTime));
        } else {
            String[] timearr = shippingTime.trim().split("-");
            int min = bookDays + Integer.parseInt(timearr[0]);
            int max = bookDays + Integer.parseInt(timearr[1]);
            ret = min + "-" + max;
        }
        return ret;
    }

    public static int calTotalPage(int rowsCount, int pageSize) {
        int totalPage = (rowsCount + pageSize - 1) / pageSize;
        return totalPage;
    }

    public static boolean isEmptyForProductDetailPage(String str) {
        if (StringUtil.isEmpty(str, true)) {
            return true;
        }
        str = str.replaceAll("(?i)<BR>", "");
        if (StringUtil.isEmpty(str, true)) {
            return true;
        }
        str = str.replaceAll("&nbsp;", "");
        if (StringUtil.isEmpty(str, true)) {
            return true;
        }
        return false;
    }

    public static int getEndNumForProductPage(PagerInfo pager) {
        int index = pager.getPageIndex();
        int size = pager.getPageSize();
        int total = pager.getRowsCount();
        return Math.min(total, index * size);
    }


    /**
     * 取得服务器iP
     * @return
     */
    public static String getLocalhostIp() {
        StringBuffer sb = new StringBuffer();
        try {
            List<String> list = Util.getLocalhostIp();
            if (list != null && list.size() > 0) {
                for (String ip : list) {
                    sb.append("<!--").append(ip).append("-->");
                }
            }
        } catch (Exception e) {
        }
        return sb.toString();
    }

    /**
     * 评论用户模糊显示
     * @param userName
     * @return
     */
    public static String getFormatUserName(String userName) {

        if (!userName.equals("") && userName != null) {
            Pattern p = Pattern.compile("^((13[0-9])|(15[^4,\\D])|(18[0,5-9]))\\d{8}$");
            Matcher m = p.matcher(userName);

            if (m.matches()) { //用户名为手机号码
                userName = userName.replace(userName.substring(2, 7), "****");
                return userName;
            }
            if (userName.contains("@")) { //邮箱
                if (userName.indexOf("@") > 2) {
                    //防 gaga重复字符
                    String font = userName.substring(0, 2);
                    String nuserName = userName.substring(2, userName.indexOf("@"));
                    String end = userName.substring(userName.indexOf("@"), userName.length());
                    userName = font + nuserName.replace(nuserName, "****") + end;
                }
            } else if (userName.length() <= 3) {//中文名
                userName = userName.replace(userName.substring(0, 1), "*");
            } else {//其他
                userName = userName.replace(userName.substring(1, userName.length() - 2), "****");
            }

        }
        return userName;
    }

    public static void main(String[] args) {
        String a = getFormatUserName("13898989889");
        System.out.println(a);
        String s = "rkikbs@163.com";

        System.out.println(getFormatUserName(s));

        Date date = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String time = sdf.format(date);
        System.out.println(time);
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.MONTH, -3);
        System.out.println(sdf.format(calendar.getTime()));
    }
}
