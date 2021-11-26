package com.plixo.ui;

public interface IKeyboard {

    public boolean isKeyDown(int key);

    public boolean isKeyComboCtrlX(int key);
    public boolean isKeyComboCtrlV(int key);
    public boolean isKeyComboCtrlC(int key);
    public boolean isKeyComboCtrlA(int key);

    public boolean isCtrlKeyDown();
    public  boolean isAltKeyDown();
    public boolean isShiftKeyDown();

    public String getClipboardString();
    public void setClipboardString(String copyText);

    public boolean isAllowedTextCharacter(char character);
    public String filterAllowedCharacters(String input);

    public String getKeyName(int key);


    public int ESCAPE();
    public int RETURN();
    public int BACK();

}
