package com.xuanluan.mc.utils;

import java.io.IOException;

/**
 * @author Xuan Luan
 * @createdAt 9/8/2022
 */
public class ServiceSDKUtils {

    public static void openHomePage(String service) throws IOException {
        Runtime rt = Runtime.getRuntime();
        rt.exec("rundll32 url.dll,FileProtocolHandler " + service);
    }
}
