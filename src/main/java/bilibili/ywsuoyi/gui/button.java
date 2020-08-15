package bilibili.ywsuoyi.gui;

import net.minecraft.client.util.math.MatrixStack;

public class button {
    public final int x;
    public final int y;
    public final int w;
    public final int h;
    public final int u;
    public final int v;
    public int hoverU;
    public int hoverV;
    public int clickU;
    public int clickV;
    public final int id;
    public boolean enable;
    public boolean clicked=false;

    public button(int x, int y, int w, int h, int u, int v, int id) {
        this.x = x;
        this.y = y;
        this.h = h;
        this.w = w;
        this.u = u;
        this.v = v;
        this.hoverU=this.u;
        this.hoverV=this.v+this.h+this.h;
        this.clickU=this.u;
        this.clickV=this.v+this.h;
        this.id = id;
        enable = true;
    }

    public boolean isclicked(double x, double y) {

        return enable && this.x <= x && this.y <= y && this.x + this.w >= x && this.y + this.h >= y;
    }

    public void setHoverUV(int u, int v){
        this.hoverU=u;
        this.hoverV=v;

    }
    public void setClickUV(int u, int v){
        this.clickU=u;
        this.clickV=v;

    }
}
