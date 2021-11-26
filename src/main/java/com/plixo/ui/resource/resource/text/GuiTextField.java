package com.plixo.ui.resource.resource.text;


import com.plixo.ui.IKeyboard;
import com.plixo.ui.IRenderer;
import com.plixo.util.Util;

import java.util.function.Predicate;

public class GuiTextField {
    private final int id;
    private final IRenderer fontRendererInstance;
    private final IKeyboard keyboard;

    public float xPosition;
    public float yPosition;

    /**
     * The width of this text field.
     */
    private final float width;
    private final float height;

    /**
     * Has the current text being edited on the textbox.
     */
    private String text = "";
    private int maxStringLength = 32;
    private int cursorCounter;

    /**
     * if true the textbox can lose focus by clicking elsewhere on the screen
     */
    private boolean canLoseFocus = true;

    /**
     * If this value is true along with isEnabled, keyTyped will process the keys.
     */
    private boolean isFocused;

    /**
     * If this value is true along with isFocused, keyTyped will process the keys.
     */
    private boolean isEnabled = true;

    /**
     * The current character index that should be used as start of the rendered text.
     */
    private int lineScrollOffset;
    private int cursorPosition;

    /**
     * other selection position, maybe the same as the cursor
     */
    private int selectionEnd;
    private int enabledColor = 14737632;
    private int disabledColor = 7368816;

    /**
     * True if this textbox is visible
     */
    private boolean visible = true;
    private Predicate<String> canInput = s -> true;

    public GuiTextField(int componentId, IRenderer renderer, IKeyboard keyboard, float x, float y, float par5Width, float par6Height) {
        this.id = componentId;
        this.fontRendererInstance = renderer;
        this.keyboard = keyboard;
        this.xPosition = x;
        this.yPosition = y;
        this.width = par5Width;
        this.height = par6Height;
    }

    /**
     * Increments the cursor counter
     */
    public void updateCursorCounter() {
        ++this.cursorCounter;
    }

    /**
     * Sets the text of the textbox
     */
    public void setText(String p_146180_1_) {
        if (this.canInput.test(p_146180_1_)) {
            if (p_146180_1_.length() > this.maxStringLength) {
                this.text = p_146180_1_.substring(0, this.maxStringLength);
            } else {
                this.text = p_146180_1_;
            }

            this.setCursorPositionEnd();
        }
    }

    /**
     * Returns the contents of the textbox
     */
    public String getText() {
        return this.text;
    }

    /**
     * returns the text between the cursor and selectionEnd
     */
    public String getSelectedText() {
        int i = this.cursorPosition < this.selectionEnd ? this.cursorPosition : this.selectionEnd;
        int j = this.cursorPosition < this.selectionEnd ? this.selectionEnd : this.cursorPosition;
        return this.text.substring(i, j);
    }

    public void func_175205_a(Predicate<String> p_175205_1_) {
        this.canInput = p_175205_1_;
    }

    /**
     * replaces selected text, or inserts text at the position on the cursor
     */
    public void writeText(String p_146191_1_) {

        String s = "";
        String s1 = keyboard.filterAllowedCharacters(p_146191_1_);
        int i = this.cursorPosition < this.selectionEnd ? this.cursorPosition : this.selectionEnd;
        int j = this.cursorPosition < this.selectionEnd ? this.selectionEnd : this.cursorPosition;
        int k = this.maxStringLength - this.text.length() - (i - j);
        int l = 0;


        if (this.text.length() > 0) {
            s = s + this.text.substring(0, i);
        }

        if (k < s1.length()) {
            s = s + s1.substring(0, k);
            l = k;
        } else {
            s = s + s1;
            l = s1.length();
        }



        if (this.text.length() > 0 && j < this.text.length()) {
            s = s + this.text.substring(j);
        }


        if (this.canInput.test(s)) {

            this.text = s;
            this.moveCursorBy(i - this.selectionEnd + l);
        }
    }

    /**
     * Deletes the specified number of words starting at the cursor position. Negative numbers will delete words left of
     * the cursor.
     */
    public void deleteWords(int p_146177_1_) {
        if (this.text.length() != 0) {
            if (this.selectionEnd != this.cursorPosition) {
                this.writeText("");
            } else {
                this.deleteFromCursor(this.getNthWordFromCursor(p_146177_1_) - this.cursorPosition);
            }
        }
    }

    /**
     * delete the selected text, otherwsie deletes characters from either side of the cursor. params: delete num
     */
    public void deleteFromCursor(int p_146175_1_) {
        if (this.text.length() != 0) {
            if (this.selectionEnd != this.cursorPosition) {
                this.writeText("");
            } else {
                boolean flag = p_146175_1_ < 0;
                int i = flag ? this.cursorPosition + p_146175_1_ : this.cursorPosition;
                int j = flag ? this.cursorPosition : this.cursorPosition + p_146175_1_;
                String s = "";

                if (i >= 0) {
                    s = this.text.substring(0, i);
                }

                if (j < this.text.length()) {
                    s = s + this.text.substring(j);
                }

                if (this.canInput.test(s)) {
                    this.text = s;

                    if (flag) {
                        this.moveCursorBy(p_146175_1_);
                    }

                }
            }
        }
    }

    public int getId() {
        return this.id;
    }

    /**
     * see @getNthNextWordFromPos() params: N, position
     */
    public int getNthWordFromCursor(int p_146187_1_) {
        return this.getNthWordFromPos(p_146187_1_, this.getCursorPosition());
    }

    /**
     * gets the position of the nth word. N may be negative, then it looks backwards. params: N, position
     */
    public int getNthWordFromPos(int p_146183_1_, int p_146183_2_) {
        return this.func_146197_a(p_146183_1_, p_146183_2_, true);
    }

    public int func_146197_a(int p_146197_1_, int p_146197_2_, boolean p_146197_3_) {
        int i = p_146197_2_;
        boolean flag = p_146197_1_ < 0;
        int j = Math.abs(p_146197_1_);

        for (int k = 0; k < j; ++k) {
            if (!flag) {
                int l = this.text.length();
                i = this.text.indexOf(32, i);

                if (i == -1) {
                    i = l;
                } else {
                    while (p_146197_3_ && i < l && this.text.charAt(i) == 32) {
                        ++i;
                    }
                }
            } else {
                while (p_146197_3_ && i > 0 && this.text.charAt(i - 1) == 32) {
                    --i;
                }

                while (i > 0 && this.text.charAt(i - 1) != 32) {
                    --i;
                }
            }
        }

        return i;
    }

    /**
     * Moves the text cursor by a specified number of characters and clears the selection
     */
    public void moveCursorBy(int p_146182_1_) {
        this.setCursorPosition(this.selectionEnd + p_146182_1_);
    }

    /**
     * sets the position of the cursor to the provided index
     */
    public void setCursorPosition(int p_146190_1_) {
        this.cursorPosition = p_146190_1_;
        int i = this.text.length();
        this.cursorPosition = Util.clamp(this.cursorPosition, i, 0);
        this.setSelectionPos(this.cursorPosition);
    }

    /**
     * sets the cursors position to the beginning
     */
    public void setCursorPositionZero() {
        this.setCursorPosition(0);
    }

    /**
     * sets the cursors position to after the text
     */
    public void setCursorPositionEnd() {
        this.setCursorPosition(this.text.length());
    }

    /**
     * Call this method from your GuiScreen to process the keys into the textbox
     */
    public boolean textboxKeyTyped(char character, int key) {
        if (!this.isFocused) {
            return false;
        } else if (keyboard.isKeyComboCtrlA(key)) {
            this.setCursorPositionEnd();
            this.setSelectionPos(0);
            return true;
        } else if (keyboard.isKeyComboCtrlC(key)) {
            keyboard.setClipboardString(this.getSelectedText());
            return true;
        } else if (keyboard.isKeyComboCtrlV(key)) {
            if (this.isEnabled) {
                this.writeText(keyboard.getClipboardString());
            }

            return true;
        } else if (keyboard.isKeyComboCtrlX(key)) {
            keyboard.setClipboardString(this.getSelectedText());

            if (this.isEnabled) {
                this.writeText("");
            }

            return true;
        } else {

            switch (key) {
                case 14:
                    if (keyboard.isCtrlKeyDown()) {
                        if (this.isEnabled) {
                            this.deleteWords(-1);
                        }
                    } else if (this.isEnabled) {
                        this.deleteFromCursor(-1);
                    }

                    return true;

                case 199:
                    if (keyboard.isShiftKeyDown()) {
                        this.setSelectionPos(0);
                    } else {
                        this.setCursorPositionZero();
                    }

                    return true;

                case 203:
                    if (keyboard.isShiftKeyDown()) {
                        if (keyboard.isCtrlKeyDown()) {
                            this.setSelectionPos(this.getNthWordFromPos(-1, this.getSelectionEnd()));
                        } else {
                            this.setSelectionPos(this.getSelectionEnd() - 1);
                        }
                    } else if (keyboard.isCtrlKeyDown()) {
                        this.setCursorPosition(this.getNthWordFromCursor(-1));
                    } else {
                        this.moveCursorBy(-1);
                    }

                    return true;

                case 205:
                    if (keyboard.isShiftKeyDown()) {
                        if (keyboard.isCtrlKeyDown()) {
                            this.setSelectionPos(this.getNthWordFromPos(1, this.getSelectionEnd()));
                        } else {
                            this.setSelectionPos(this.getSelectionEnd() + 1);
                        }
                    } else if (keyboard.isCtrlKeyDown()) {
                        this.setCursorPosition(this.getNthWordFromCursor(1));
                    } else {
                        this.moveCursorBy(1);
                    }

                    return true;

                case 207:
                    if (keyboard.isShiftKeyDown()) {
                        this.setSelectionPos(this.text.length());
                    } else {
                        this.setCursorPositionEnd();
                    }

                    return true;

                case 211:
                    if (keyboard.isCtrlKeyDown()) {
                        if (this.isEnabled) {
                            this.deleteWords(1);
                        }
                    } else if (this.isEnabled) {
                        this.deleteFromCursor(1);
                    }

                    return true;

                default:
                    if (keyboard.isAllowedTextCharacter(character)) {
                        if (this.isEnabled) {
                            this.writeText(Character.toString(character));
                        }

                        return true;
                    } else {
                        return false;
                    }
            }
        }
    }

    /**
     * Args: x, y, buttonClicked
     */
    public void mouseClicked(int p_146192_1_, int p_146192_2_, int p_146192_3_) {
        boolean flag = p_146192_1_ >= this.xPosition && p_146192_1_ < this.xPosition + this.width && p_146192_2_ >= this.yPosition && p_146192_2_ < this.yPosition + this.height;

        if (this.canLoseFocus) {
            this.setFocused(flag);
        }

        if (this.isFocused && flag && p_146192_3_ == 0) {
            float i = p_146192_1_ - this.xPosition;


            String s = this.fontRendererInstance.trimStringToWidth(this.text.substring(this.lineScrollOffset), this.getWidth(), false);
            this.setCursorPosition(this.fontRendererInstance.trimStringToWidth(s, i, false).length() + this.lineScrollOffset);
        }
    }

    /**
     * Draws the textbox
     */
    public void drawTextBox() {
        if (this.getVisible()) {
            /*
            if (this.getEnableBackgroundDrawing()) {
                fontRendererInstance.drawRect(this.xPosition - 1, this.yPosition - 1, this.xPosition + this.width + 1, this.yPosition + this.height + 1, -6250336);
                fontRendererInstance.drawRect(this.xPosition, this.yPosition, this.xPosition + this.width, this.yPosition + this.height, -16777216);
            }

             */

            int i = this.isEnabled ? this.enabledColor : this.disabledColor;
            int j = this.cursorPosition - this.lineScrollOffset;
            int k = this.selectionEnd - this.lineScrollOffset;
            final String substring = this.text.substring(this.lineScrollOffset);
            String s = this.fontRendererInstance.trimStringToWidth(substring, this.getWidth(), false);
            boolean flag = j >= 0 && j <= s.length();
            boolean flag1 = this.isFocused && this.cursorCounter / 6 % 2 == 0 && flag;
            float l = this.xPosition;
            float i1 = this.yPosition + (this.height) / 2 + (0);
            float j1 = l;


            if (k > s.length()) {
                k = s.length();
            }

            if (s.length() > 0) {
                String s1 = flag ? s.substring(0, j) : s;
                this.fontRendererInstance.drawStringWithShadow(s1, l, i1, i);
                j1 += fontRendererInstance.getStringWidth(s1);
            }

            boolean flag2 = this.cursorPosition < this.text.length() || this.text.length() >= this.getMaxStringLength();
            float k1 = j1;

            if (!flag) {
                k1 = j > 0 ? l + this.width : l;
            } else if (flag2) {
                k1 = j1 - 1;
                --j1;
            }

            if (s.length() > 0 && flag && j < s.length()) {
                this.fontRendererInstance.drawStringWithShadow(s.substring(j), j1, i1, i);
                j1 += fontRendererInstance.getStringWidth(s.substring(j));
            }
            float wuff = height*0.2f;

            if (flag1) {
                if (flag2) {
                    fontRendererInstance.drawRect(k1, yPosition+wuff, k1 + 2, yPosition+height-wuff, -3092272);
                } else {
                    this.fontRendererInstance.drawStringWithShadow("_", k1, i1, i);
                }
            }

            if (k != j) {
                float l1 = l + this.fontRendererInstance.getStringWidth(s.substring(0, k));
                this.drawCursorVertical(k1, yPosition+wuff, l1 - 1, yPosition+height-wuff);
            }
        }
    }

    /**
     * draws the vertical line cursor in the textbox
     */
    private void drawCursorVertical(float x, float y, float x2, float y2) {
        if (x < x2) {
            float i = x;
            x = x2;
            x2 = i;
        }

        if (y < y2) {
            float j = y;
            y = y2;
            y2 = j;
        }

        if (x2 > this.xPosition + this.width) {
            x2 = this.xPosition + this.width;
        }

        if (x > this.xPosition + this.width) {
            x = this.xPosition + this.width;
        }

        fontRendererInstance.drawRect(x, y2, x2, y, 0x55FFFFFF);
    }

    public void setMaxStringLength(int p_146203_1_) {
        this.maxStringLength = p_146203_1_;

        if (this.text.length() > p_146203_1_) {
            this.text = this.text.substring(0, p_146203_1_);
        }
    }

    /**
     * returns the maximum number of character that can be contained in this textbox
     */
    public int getMaxStringLength() {
        return this.maxStringLength;
    }

    /**
     * returns the current position of the cursor
     */
    public int getCursorPosition() {
        return this.cursorPosition;
    }




    /**
     * Sets the text colour for this textbox (disabled text will not use this colour)
     */
    public void setTextColor(int p_146193_1_) {
        this.enabledColor = p_146193_1_;
    }

    public void setDisabledTextColour(int p_146204_1_) {
        this.disabledColor = p_146204_1_;
    }

    /**
     * Sets focus to this gui element
     */
    public void setFocused(boolean state) {
        if (state && !this.isFocused) {
            this.cursorCounter = 0;
        }

        this.isFocused = state;
    }

    /**
     * Getter for the focused field
     */
    public boolean isFocused() {
        return this.isFocused;
    }

    public void setEnabled(boolean p_146184_1_) {
        this.isEnabled = p_146184_1_;
    }

    /**
     * the side of the selection that is not the cursor, may be the same as the cursor
     */
    public int getSelectionEnd() {
        return this.selectionEnd;
    }

    /**
     * returns the width of the textbox depending on if background drawing is enabled
     */
    public float getWidth() {
        return this.width;
    }

    /**
     * Sets the position of the selection anchor (i.e. position the selection was started at)
     */
    public void setSelectionPos(int p_146199_1_) {
        int i = this.text.length();

        if (p_146199_1_ > i) {
            p_146199_1_ = i;
        }

        if (p_146199_1_ < 0) {
            p_146199_1_ = 0;
        }

        this.selectionEnd = p_146199_1_;


        if (this.lineScrollOffset > i) {
            this.lineScrollOffset = i;
        }

        float j = this.getWidth();
        String s = this.fontRendererInstance.trimStringToWidth(this.text.substring(this.lineScrollOffset), j, false);
        int k = s.length() + this.lineScrollOffset;

        if (p_146199_1_ == this.lineScrollOffset) {
            this.lineScrollOffset -= this.fontRendererInstance.trimStringToWidth(this.text, j, true).length();
        }

        if (p_146199_1_ > k) {
            this.lineScrollOffset += p_146199_1_ - k;
        } else if (p_146199_1_ <= this.lineScrollOffset) {
            this.lineScrollOffset -= this.lineScrollOffset - p_146199_1_;
        }

        this.lineScrollOffset = Util.clamp(this.lineScrollOffset, i,0);

    }

    /**
     * if true the textbox can lose focus by clicking elsewhere on the screen
     */
    public void setCanLoseFocus(boolean p_146205_1_) {
        this.canLoseFocus = p_146205_1_;
    }

    /**
     * returns true if this textbox is visible
     */
    public boolean getVisible() {
        return this.visible;
    }

    /**
     * Sets whether or not this textbox is visible
     */
    public void setVisible(boolean p_146189_1_) {
        this.visible = p_146189_1_;
    }
}


