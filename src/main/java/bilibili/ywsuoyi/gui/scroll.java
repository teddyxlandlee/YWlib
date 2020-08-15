package bilibili.ywsuoyi.gui;

public class scroll {
    public final int x;
    public final int y;
    public final int w;
    public final int h;
    public double scrollAmount = 0;
    public boolean enable = true;
    public boolean clicked=false;

    public scroll(int x, int y, int h) {
        this.x = x;
        this.y = y;
        this.h = Math.max(h, 16);
        this.w = 12;
    }

    public boolean isclicked(double x, double y) {
        return enable&&this.x <= x && this.y <= y && this.x + this.w >= x && this.y + this.h >= y;
    }
}
