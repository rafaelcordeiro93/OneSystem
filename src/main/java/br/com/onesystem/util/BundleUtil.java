package br.com.onesystem.util;

import java.util.ResourceBundle;

public class BundleUtil {

    private final ResourceBundle BUNDLE = ResourceBundle.getBundle("ValidationMessages");

    public String getMessage(String key) {
        return BUNDLE.getString(key);
    }

}
