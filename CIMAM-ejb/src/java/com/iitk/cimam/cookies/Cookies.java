/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.iitk.cimam.cookies;

import javax.servlet.http.Cookie;

/**
 *
 * @author Chirag Sangani
 */
public class Cookies {

    private static String AuthKey = "BTHVJHVyt876tG&768JG";
    private static String AuthName = "auth";

    public static String getAuthName() {
	return AuthName;
    }
    private static int maxAge = 3600;

    public static Cookie getCookie() {
	Cookie cookie = new Cookie(AuthName, AuthKey);
	cookie.setMaxAge(maxAge);
	return cookie;
    }

    public static boolean verifyCookie(Cookie cookie) {
	return cookie.getName().equals(AuthName) && cookie.getValue().equals(AuthKey);
    }
}
