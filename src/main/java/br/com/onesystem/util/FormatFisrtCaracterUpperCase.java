/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.onesystem.util;

import com.sun.xml.ws.util.StringUtils;

/**
 *
 * @author Rauber
 */
public class FormatFisrtCaracterUpperCase {

    public static String format(String str) {
        return formatString(str);
    }

    public static String formatAcronym(String str) {
        if(str == null){
            return str;
        }
        StringBuilder valueReturn = new StringBuilder();
        for (int i = 0; i < str.length(); i++) {
            char b = str.charAt(i);
            if (Character.isSpaceChar(b)) {
                continue;
            } else {
                valueReturn.append(b);
            }
        }
        return valueReturn.toString().toUpperCase();
    }

    private static String formatString(String str) {
        if (str == null) {
            return str;
        }
        str = str.toLowerCase();
        str = str.trim();

        if (str.length() == 1) {
            return str;
        }
        String[] parts = str.trim().split(" ");
        str = "";
        for (int i = 0; i < parts.length; i++) {
            if (parts[i].equals("de") || parts[i].equals("da") || parts[i].equals("do")) {
                continue;
            }
            parts[i] = StringUtils.capitalize(parts[i]);
            str += parts[i] + " ";
        }
        return dropTwoSpace(str);
    }

    private static String dropTwoSpace(String value) {
        StringBuilder valueReturn = new StringBuilder("");
        for (int i = 0; i < value.length(); i++) {
            char b = value.charAt(i);
            if (Character.isSpaceChar(b) && (i + 1) != value.length() && Character.isSpaceChar(value.charAt(i + 1))) {
                continue;
            } else {
                valueReturn.append(b);
            }
        }
        return valueReturn.toString().trim();
    }
}
