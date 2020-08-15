package bilibili.ywsuoyi.gui;

import net.minecraft.screen.PropertyDelegate;

public class percentage {
    public final int type;
    public final int x;
    public final int y;
    public final int l;
    public final int p;
    public percentage(int x, int y, int l, int type,int p){
        this.type=type;
        this.x=x;
        this.y=y;
        this.l=l;
        this.p=p;
    }
    public boolean isclicked(double x, double y) {
        if (type == 1) {
            return this.x < x && this.y < y && this.x + 15 > x && this.y + this.l > y;
        }
        return false;
    }
}
