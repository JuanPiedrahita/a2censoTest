package com.bvc.a2censo.model;

public class GUIElement extends AbstractJson {

    private String textColor;
    private String backgroundColor;
    private String textColorOver;
    private String backgroundColorOver;
    private String textColorPress;
    private String backgroundColorPress;

    public GUIElement(String name, String textColor, String backgroundColor, String textColorOver, String backgroundColorOver, String textColorPress, String backgroundColorPress) {
        this.name = name;
        this.textColor = textColor;
        this.backgroundColor = backgroundColor;
        this.textColorOver = textColorOver;
        this.backgroundColorOver = backgroundColorOver;
        this.textColorPress = textColorPress;
        this.backgroundColorPress = backgroundColorPress;
    }

    public String getTextColor() {
        return textColor;
    }

    public String getBackgroundColor() {
        return backgroundColor;
    }

    public String getTextColorOver() {
        return textColorOver;
    }

    public String getBackgroundColorOver() {
        return backgroundColorOver;
    }

    public String getTextColorPress() {
        return textColorPress;
    }

    public String getBackgroundColorPress() {
        return backgroundColorPress;
    }
}
