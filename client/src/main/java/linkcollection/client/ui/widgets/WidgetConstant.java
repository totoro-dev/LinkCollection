package linkcollection.client.ui.widgets;

import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;

public final class WidgetConstant {

    public static int VisibleWidth = 500;
    public static int VisibleHeight = 500;

    private static int preWidth = VisibleWidth, preHeight = VisibleHeight;

    public static Color ThemeColor = Color.decode("#15a5e5");
    public static Color BorderColor = Color.decode("#d6d6d6");
    public static Color NormalColor = Color.decode("#f5f5f5");
    public static Color EnterColor = Color.decode("#b3e9ff");
    public static Color SelectedColor = Color.decode("#88ddff");

    public static Color NormalTitleColor = Color.decode("#00aaff");
    public static Color SelectedTitleColor = Color.decode("#bb00ff");

    public static Font TitleFont = new Font(Font.SERIF, Font.PLAIN, 16);
    public static Font LabelFont = new Font(Font.SERIF, Font.PLAIN, 12);
    public static Font LinkFont = new Font(Font.DIALOG, Font.BOLD, 12);

    public static Point ToastLocation = new Point(230, 400);

    public static Border WindowBorder = BorderFactory.createMatteBorder(1, 1, 1, 1, WidgetConstant.BorderColor);
    public static Border NoneBorder = BorderFactory.createMatteBorder(0, 0, 0, 0, WidgetConstant.BorderColor);
    public static Border BottomBorder = BorderFactory.createMatteBorder(0, 0, 1, 0, WidgetConstant.BorderColor);

    /**
     * 绘制5像素半径的边框
     * @param g 画笔
     * @param color 背景色
     * @param width 主体宽度
     */
    public static void drawBorderFiveRadius(Graphics g, Color color, int width) {
        Color origin = g.getColor();
        g.setColor(color);
        for (int i = 0; i < 30; i++) {
            switch (i) {
                case 0:
                    g.drawLine(3, 0, width - 4, 0);
                    break;
                case 1:
                    g.drawLine(2, 1, width - 3, 1);
                    break;
                case 2:
                    g.drawLine(1, 2, width - 2, 2);
                    break;
                case 27:
                    g.drawLine(1, 27, width - 2, 27);
                    break;
                case 28:
                    g.drawLine(2, 28, width - 3, 28);
                    break;
                case 29:
                    g.drawLine(3, 29, width - 4, 29);
                    break;
                default:
                    g.drawLine(0, i, width - 1, i);
                    break;
            }
        }
        g.setColor(origin);
    }

    /**
     * 绘制15像素半径的边框
     * @param g 画笔
     * @param bg 背景色
     * @param borderColor 边框色
     * @param width 主体宽度
     */
    public static void drawBorderFifteenRadius(Graphics g, Color bg, Color borderColor, int width) {
        Color origin = g.getColor();
        for (int i = 0; i < 15; i++) {
            g.setColor(bg);
            switch (i) {
                case 0:
                    g.drawLine(13, i, width - 14, i);
                    g.drawLine(13, 29 - i, width - 14, 29 - i);
                    break;
                case 1:
                    drawRowBorder(g, borderColor,1, width, i, 11);
                    break;
                case 2:
                    drawRowBorder(g, borderColor,1, width, i, 9);
                    break;
                case 3:
                    drawRowBorder(g, borderColor,1, width, i, 7);
                    break;
                case 4:
                    drawRowBorder(g, borderColor,0, width, i, 6);
                    break;
                case 5:
                    drawRowBorder(g, borderColor, 0,width, i, 5);
                    break;
                case 6:
                    drawRowBorder(g, borderColor, 0,width, i, 4);
                    break;
                case 7:
                case 8:
                    drawRowBorder(g, borderColor,0, width, i, 3);
                    break;
                case 9:
                case 10:
                    drawRowBorder(g, borderColor,0, width, i, 2);
                    break;
                case 11:
                case 12:
                case 13:
                case 14:
                    drawRowBorder(g, borderColor, 0,width, i, 1);
                    break;
            }
        }
        g.setColor(borderColor);
        g.drawLine(13, 0, width - 14, 0);
        g.drawLine(13, 29, width - 14, 29);
        g.setColor(origin);
    }

    private static void drawRowBorder(Graphics g, Color borderColor, int borderLength, int width, int i, int gap) {
        g.drawLine(gap, i, width - 1 - gap, i);
        g.drawLine(gap, 29 - i, width - 1 - gap, 29 - i);
        g.setColor(borderColor);
        g.drawLine(gap, i, gap + borderLength, i);
        g.drawLine(gap, 29 - i, gap + borderLength, 29 - i);
        g.drawLine(width - 1 - gap, i, width - 1 - gap - borderLength, i);
        g.drawLine(width - 1 - gap, 29 - i, width - 1 - gap - borderLength, 29 - i);
    }

    /**
     * 设置可见大小
     *
     * @param width  宽度
     * @param height 高度
     */
    public static void setVisibleSize(int width, int height) {
        preWidth = VisibleWidth;
        preHeight = VisibleHeight;
        VisibleWidth = width;
        VisibleHeight = height;
    }

    /**
     * 将可见大小回退到前一个视图的大小。
     */
    public static void rollBackVisibleSize() {
        VisibleWidth = preWidth;
        VisibleHeight = preHeight;
    }

    /**
     * 获取当前显示视图的起始位置
     *
     * @return
     */
    public static Point getStartLocation(JFrame parent) {
        return new Point(parent.getX(), parent.getY());
    }

    /**
     * 获取当前可见视图能位于屏幕中间位置的起始点
     *
     * @return
     */
    public static Point getCenterLocation() {
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        int width = (int) screenSize.getWidth();
        int height = (int) screenSize.getHeight();
        return new Point((width - VisibleWidth) / 2, (height - VisibleHeight) / 2);
    }

    /**
     * 重置Toast的显示位置，与要显示的字符个数{@param textLength}有关
     *
     * @param textLength 字符个数
     */
    public static void reSetToastLocation(JFrame parent, int textLength) {
        Point start = getStartLocation(parent);
        int x = start.x + WidgetConstant.VisibleWidth / 2 - 10 * textLength;
        int y = start.y + WidgetConstant.VisibleHeight - 50;
        WidgetConstant.ToastLocation = new Point(x, y);
    }

}
