package com.platform.util;

public class DomainUrlUtil {
    protected static org.apache.log4j.Logger log = org.apache.log4j.LogManager
                                                     .getLogger(DomainUrlUtil.class);
    public static String                     staticURL;
    public static String                     dynamicURL;
    public static String                     appURL;

    public static String getStaticURL() {
        return staticURL;
    }

    public static void setStaticURL(String staticURL) {
        DomainUrlUtil.staticURL = staticURL;
    }

    public static String getDynamicURL() {
        return dynamicURL;
    }

    public static void setDynamicURL(String dynamicURL) {
        DomainUrlUtil.dynamicURL = dynamicURL;
    }

    public static String getAppURL() {
        return appURL;
    }

    public static void setAppURL(String appURL) {
        DomainUrlUtil.appURL = appURL;
    }
}
