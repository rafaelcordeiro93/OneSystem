/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.onesystem.services;

import br.com.onesystem.util.MD5Util;

/**
 *
 * @author Rafael-Pc
 */
public class WebServiceGravatar {

    private String urlGravatar = "http://www.gravatar.com/avatar/";
    private String imagemDefault = "?d=mm";

    public String getImagem(String email) {
        return urlGravatar + new MD5Util().md5Hex(email) + imagemDefault;
    }

}
