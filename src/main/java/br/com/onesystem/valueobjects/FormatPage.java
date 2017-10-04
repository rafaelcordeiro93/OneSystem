/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.onesystem.valueobjects;

/**
 *
 * @author Rafael
 */
public enum FormatPage {
    FORMAT_A4((double) 2),
    FORMAT_A5((double) 425.19684996);

    private double height;

    private FormatPage(double height) {
        this.height = height;
    }

    public double getHeight() {
        return height;
    }

}
