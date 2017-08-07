package br.com.onesystem.util;

import java.io.Serializable;
import java.util.ResourceBundle;

public class BundleUtil implements Serializable {

    private final ResourceBundle BUNDLE = ResourceBundle.getBundle("ValidationMessages");
    private final ResourceBundle BUNDLE_LABEL = ResourceBundle.getBundle("label_messages");

    public String getMessage(String key) {
        return BUNDLE.getString(key);
    }

    public String getLabel(String key) {
        return BUNDLE_LABEL.getString(key);
    }

}
