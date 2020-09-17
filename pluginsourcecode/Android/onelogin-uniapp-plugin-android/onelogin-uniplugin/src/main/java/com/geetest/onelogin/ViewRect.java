package com.geetest.onelogin;

public class ViewRect {
    public int left;
    public int top;
    public int right;
    public int bottom;
    public int width;
    public int height;

    public ViewRect(int left, int top, int right, int bottom, int width, int height) {
        this.left = left;
        this.top = top;
        this.right = right;
        this.bottom = bottom;
        this.width = width;
        this.height = height;
    }

    @Override
    public String toString() {
        return "ViewRect{" +
                "left=" + left +
                ", top=" + top +
                ", right=" + right +
                ", bottom=" + bottom +
                ", width=" + width +
                ", height=" + height +
                '}';
    }
}
