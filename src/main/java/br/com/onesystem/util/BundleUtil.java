package br.com.onesystem.util;

import java.io.Serializable;
import java.util.Locale;
import java.util.ResourceBundle;

public class BundleUtil implements Serializable {

    private ResourceBundle BUNDLE = ResourceBundle.getBundle("ValidationMessages");
    private ResourceBundle BUNDLE_LABEL = ResourceBundle.getBundle("label_messages");

    public String getMessage(String key) {
        return BUNDLE.getString(key);
    }

    public String getLabel(String key) {
        return BUNDLE_LABEL.getString(key);
    }

    public ResourceBundle getBUNDLE() {
        return BUNDLE;
    }

    public ResourceBundle getBUNDLE_LABEL() {
        return BUNDLE_LABEL;
    }

    public BundleUtil setLocaleLabel(Locale locale) {
        BUNDLE_LABEL = ResourceBundle.getBundle("label_messages", locale);
        return this;
    }

    public BundleUtil setLocaleMessage(Locale locale) {
        BUNDLE = ResourceBundle.getBundle("ValidationMessages", locale);
        return this;
    }

}
