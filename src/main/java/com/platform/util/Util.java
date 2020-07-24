package com.platform.util;

import com.gao.common.util.StringUtil;

import javax.servlet.http.HttpServletRequest;
import java.net.Inet6Address;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.text.SimpleDateFormat;
import java.util.*;

public class Util {
    /**
     * 取得当前时间的UNIX TIME
     * @return
     */
    public static Integer getUnixTimestamp() {
        return (int) (System.currentTimeMillis() / 1000L);
    }

    public static List<String> getLocalhostIp() {
        List<String> ips = new ArrayList<String>();
        try {
            Enumeration<NetworkInterface> nis = NetworkInterface.getNetworkInterfaces();
            InetAddress ia = null;
            while (nis.hasMoreElements()) {
                NetworkInterface ni = (NetworkInterface) nis.nextElement();
                Enumeration<InetAddress> ias = ni.getInetAddresses();
                while (ias.hasMoreElements()) {
                    ia = ias.nextElement();
                    if (ia instanceof Inet6Address)
                        continue;
                    if ("127.0.0.1".equals(ia.getHostAddress()))
                        continue;
                    ips.add(ia.getHostAddress());
                }
            }
        } catch (SocketException e) {
            throw new RuntimeException(e);
        }
        return ips;
    }

    public static String getIpAddr(HttpServletRequest request) {
        String ip = request.getHeader("x-forwarded-for");
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("Proxy-Client-IP");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("WL-Proxy-Client-IP");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getRemoteAddr();
        }
        return ip;
    }

    public static String clientIp(HttpServletRequest request) {
        String ip = request.getHeader("X-Forwarded-For");
        //如果客户端经过多级反向代理，则X-Forwarded-For的值并不止一个，而是一串IP值，
        //取X-Forwarded-For中第一个非unknown的有效IP字符串即为用户真实IP
        if (!StringUtil.isEmpty(ip) && ip.contains(",")) {
            String[] tokens = ip.split(",");
            for (String s : tokens) {
                if (StringUtil.isIp(s.trim())) {
                    ip = s.trim();
                    break;
                }
            }
        }
        if (StringUtil.isIp(ip))
            return ip;
        ip = request.getHeader("Proxy-Client-IP");
        if (StringUtil.isIp(ip))
            return ip;
        ip = request.getHeader("WL-Proxy-Client-IP");
        if (StringUtil.isIp(ip))
            return ip;
        return request.getRemoteAddr();
    }

    public static String getOrderSn(String referId, String type) {

        StringBuffer orderSn = new StringBuffer();
        if ("1".equals(type)) {
            orderSn.append("SN");
        } else {
        	orderSn.append("SN");
        }
        orderSn.append(new SimpleDateFormat("yyMMddHHmmssSSS").format(new Date()));
        if (referId != null && String.valueOf(referId).length() >= 2) {
            String id = String.valueOf(referId);
            orderSn.append(id.substring(id.length() - 2));
        } else {
            orderSn.append(new Random().nextInt(10));
            orderSn.append(new Random().nextInt(10));
        }

        return orderSn.toString();
    }

    
    public static void main(String[] a) {
        List<String> lstAttrOptionId = Arrays.asList("2024".split(","));
        System.out.println(lstAttrOptionId.contains("2024"));
        for (String item : lstAttrOptionId) {
            System.out.println(item);
        }
        System.out.println(Util.getOrderSn(null,"1"));
    }
}
