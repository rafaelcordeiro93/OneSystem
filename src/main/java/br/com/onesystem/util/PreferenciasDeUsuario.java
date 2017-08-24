package br.com.onesystem.util;

import br.com.onesystem.valueobjects.TipoCorMenu;
import javax.annotation.PostConstruct;
import java.util.Map;
import java.util.HashMap;
import java.io.Serializable;
import javax.enterprise.context.SessionScoped;  
import javax.inject.Named;

@Named(value = "preferenciasDeUsuario") 
@SessionScoped 
public class PreferenciasDeUsuario implements Serializable {

    private Map<String, String> themeColors;

    private String theme = "blue";

    private String layout = "dark"; 
  
    private boolean overlayMenu; 

    private TipoCorMenu corMenu = TipoCorMenu.CINZA;

    private boolean orientationRTL;
 
    @PostConstruct
    public void init() {
        themeColors = new HashMap<String, String>();
        themeColors.put("turquoise", "#47c5d4");
        themeColors.put("blue", "#3192e1");
        themeColors.put("clean", "#2d6891");
        themeColors.put("orange", "#ff9c59");
        themeColors.put("purple", "#985edb");
        themeColors.put("pink", "#e42a7b");
        themeColors.put("purple", "#985edb");
        themeColors.put("green", "#5ea980");
        themeColors.put("black", "#545b61");
    }

    public String getTheme() {
        return theme;
    }

    public void setTheme(String theme) {
        this.theme = theme;
    }

    public String getLayout() {
        return this.layout;
    }

    public void setLayout(String layout) { 
        this.layout = layout;
    }
 
    public String getCorMenuCSS() {
        return corMenu.equals(TipoCorMenu.BRANCO) ? "layout-sidebar-white" : corMenu.equals(TipoCorMenu.CINZA) ? "" : "layout-sidebar-dark";
    }

    public boolean isOverlayMenu() {
        return this.overlayMenu;
    }
    
    public void setOverlayMenu(boolean value) {
        this.overlayMenu = value;
    }

    public Map getThemeColors() {
        return this.themeColors;
    }

    public boolean isOrientationRTL() {
        return orientationRTL;
    }

    public void setOrientationRTL(boolean orientationRTL) {
        this.orientationRTL = orientationRTL;
    }

    public TipoCorMenu getCorMenu() {
        return corMenu;
    }

    public void setCorMenu(TipoCorMenu corMenu) {
        this.corMenu = corMenu;
    }
}
