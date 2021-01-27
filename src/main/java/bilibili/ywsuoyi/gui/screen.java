package bilibili.ywsuoyi.gui;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.screen.ingame.HandledScreen;
import net.minecraft.client.sound.PositionedSoundInstance;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.screen.ScreenHandler;
import net.minecraft.screen.slot.Slot;
import net.minecraft.sound.SoundEvents;
import net.minecraft.text.LiteralText;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;
import net.minecraft.util.collection.DefaultedList;
import net.minecraft.util.math.MathHelper;

@Environment(EnvType.CLIENT)
public abstract class screen<T extends ScreenHandler> extends HandledScreen<T> {
    public DefaultedList<button> buttons = DefaultedList.of();
    public DefaultedList<scroll> scrolls = DefaultedList.of();
    public DefaultedList<icon> icons = DefaultedList.of();
    public DefaultedList<percentage> percentages = DefaultedList.of();
    public boolean hasButton = false;
    public boolean hasScroll = false;
    public boolean hasIcon = false;
    public boolean hasPercentage = false;
    public int scrollId = -1;
    public Identifier TEXTURE = new Identifier("ywsuoyi", "textures/gui/tempgui.png");

    public screen(T handler, PlayerInventory inventory, Text title, Identifier texture) {
        super(handler, inventory, title);
        this.TEXTURE = texture;
    }

    public screen(T handler, PlayerInventory inventory, Text title) {
        super(handler, inventory, title);
    }

    @Override
    public void drawBackground(MatrixStack matrices, float delta, int mouseX, int mouseY) {

        assert this.client != null;
        BlockEntity e = ((screenHandler) this.handler).e;
        this.client.getTextureManager().bindTexture(TEXTURE);
        int i = this.x;
        int j = this.y;
        this.drawTexture(matrices, i, j, 0, 0, this.backgroundWidth, 4);
        for (int a = 0; a < this.backgroundHeight - 8; a++) {
            this.drawTexture(matrices, i, a + j + 4, 0, 4, this.backgroundWidth, 1);
        }
        this.drawTexture(matrices, i, j + this.backgroundHeight - 4, 0, 5, this.backgroundWidth, 4);
        renderslot(matrices);
        if (hasPercentage) {
            this.client.getTextureManager().bindTexture(TEXTURE);
            renderPercentage(matrices, e);
        }
        if (hasScroll) {
            this.client.getTextureManager().bindTexture(TEXTURE);
            renderScroll(matrices);
        }
        if (hasButton) {
            this.client.getTextureManager().bindTexture(TEXTURE);
            renderButton(matrices, mouseX, mouseY);
        }
        if (hasIcon) {
            this.client.getTextureManager().bindTexture(TEXTURE);
            for (icon icon : icons)
                this.client.getItemRenderer().renderInGuiWithOverrides(icon.i, icon.x + this.x, icon.y + this.y);
        }


    }

    public void render(MatrixStack matrices, int mouseX, int mouseY, float delta) {
        super.render(matrices, mouseX, mouseY, delta);
        this.drawMouseoverTooltip(matrices, mouseX, mouseY);
    }

    public boolean mouseClicked(double mouseX, double mouseY, int button) {
        this.scrollId = -1;
        if (hasButton)
            for (button b : buttons) {
                b.clicked = false;
                if (b.isclicked(mouseX - this.x, mouseY - this.y)) {
                    assert this.client != null;
                    this.handler.onButtonClick(this.client.player, b.id);
                    MinecraftClient.getInstance().getSoundManager().play(PositionedSoundInstance.master(SoundEvents.UI_BUTTON_CLICK, 1.0F));
                    b.clicked = true;
                    break;
                }
            }
        if (hasScroll) {
            for (int a = 0; a < scrolls.size(); a++) {
                if (scrolls.get(a).isclicked(mouseX - this.x, mouseY - this.y)) {
                    this.scrollId = a;
                    scrolls.get(a).clicked = true;
                    break;
                }
            }
        }
        return super.mouseClicked(mouseX, mouseY, button);
    }

    @Override
    public boolean mouseReleased(double mouseX, double mouseY, int button) {
        for (button b : buttons) {
            b.clicked = false;
        }
        for (scroll b : scrolls) {
            b.clicked = false;
        }
        scrollId = -1;
        return super.mouseReleased(mouseX, mouseY, button);
    }

    public boolean mouseDragged(double mouseX, double mouseY, int button, double deltaX, double deltaY) {
        if (this.scrollId != -1) {
            scroll s = scrolls.get(this.scrollId);
            double i = mouseY - this.y - s.y - 7.5;
            s.scrollAmount = i / (s.h - 15);
            s.scrollAmount = MathHelper.clamp(s.scrollAmount, 0.0F, 1.0F);
            return true;
        } else {
            return super.mouseDragged(mouseX, mouseY, button, deltaX, deltaY);
        }
    }

    public boolean mouseScrolled(double mouseX, double mouseY, double amount) {
        if (hasScroll) {
            if (scrolls.size() == 1) {
                this.scrollId = 0;
            } else {
                for (int a = 0; a < scrolls.size(); a++) {
                    if (scrolls.get(a).isclicked(mouseX - this.x, mouseY - this.y)) {
                        this.scrollId = a;
                        break;
                    }
                }
            }
        }
        if (this.scrollId != -1) {
            scroll s = scrolls.get(this.scrollId);
            s.scrollAmount += amount / 10;
            s.scrollAmount = MathHelper.clamp(s.scrollAmount, 0.0F, 1.0F);
        }
        scrollId = -1;
        return true;
    }

    @Override
    public void drawMouseoverTooltip(MatrixStack matrices, int x, int y) {
        for (percentage p : percentages) {
            if (p.isclicked(x - this.x, y - this.y)) {
                String a = String.valueOf(getPvar(p.p, ((screenHandler) this.handler).e));
                this.renderTooltip(matrices, new LiteralText("§b" + a + "%"), x, y);
            }
        }
        super.drawMouseoverTooltip(matrices, x, y);
    }

    public void addItemIcon(ItemStack itemStack, int x, int y) {
        this.icons.add(new icon(x, y, itemStack));
        hasIcon = true;
    }

    public void addButton(int x, int y, int w, int h, int u, int v, int id) {
        buttons.add(id, new button(x, y, w, h, u, v, id));
        hasButton = true;
    }

    public void addScroll(int x, int y, int h) {
        scrolls.add(new scroll(x, y, h));
        hasScroll = true;
    }

    public void addTank(int x, int y, int length, int index) {
        this.percentages.add(new percentage(x, y, length, 1, index));
        this.hasPercentage = true;
    }

    public void addProgressArrow(int x, int y, int index) {
        this.percentages.add(new percentage(x, y, 0, 2, index));
        this.hasPercentage = true;
    }

    public void addProgressBar(int x, int y, int length, int index) {
        this.percentages.add(new percentage(x, y, length, 3, index));
        this.hasPercentage = true;
    }

    public void addBurningBar(int x, int y, int index) {
        this.percentages.add(new percentage(x, y, 0, 4, index));
        this.hasPercentage = true;
    }

    public void renderScroll(MatrixStack matrices) {
        for (scroll s : scrolls) {
            int i = this.x;
            int j = this.y;
            this.drawTexture(matrices, i + s.x, j + s.y, 200, 0, 14, 1);
            for (int hi = 1; hi <= s.h; hi++) {
                this.drawTexture(matrices, i + s.x, j + s.y + hi, 200, 1, 14, 1);
            }
            this.drawTexture(matrices, i + s.x, j + s.y + s.h + 1, 200, 2, 14, 1);
            this.drawTexture(matrices, i + s.x + 1, j + s.y + 1 + (int) (s.scrollAmount * (s.h - 15)), 176 + ((!s.enable || s.clicked) ? 12 : 0), 0, 12, 15);
        }

    }

    public void renderButton(MatrixStack matrices, int x, int y) {
        for (button b : buttons) {
            if (b.clicked || !b.enable) {
                drawbutton(matrices, b, 92);
            } else if (b.isclicked(x - this.x, y - this.y)) {
                drawbutton(matrices, b, 93);
            } else {
                drawbutton(matrices, b, 91);
            }
        }
    }

    public void drawbutton(MatrixStack matrices, button b, int v) {
        int a, aa;
        int i = this.x;
        int j = this.y;
        for (a = 0; a < b.w; a++)
            for (aa = 0; aa < b.h; aa++) this.drawTexture(matrices, i + b.x + a, j + b.y + aa, 1, v, 1, 1);

        for (a = 0; a < b.w - 1; a++) {
            this.drawTexture(matrices, i + b.x + a, j + b.y, 0, v, 1, 1);
            this.drawTexture(matrices, i + b.x + a + 1, j + b.y + b.h - 1, 2, v, 1, 1);
        }
        for (a = 0; a < b.h - 1; a++) {
            this.drawTexture(matrices, i + b.x, j + b.y + a, 0, v, 1, 1);
            this.drawTexture(matrices, i + b.x + b.w - 1, j + b.y + a + 1, 2, v, 1, 1);
        }
    }

    public void renderslot(MatrixStack matrices) {
        int i = this.x;
        int j = this.y;
        for (int a = 0; a < this.handler.slots.size(); a++) {
            if (((screenHandler) this.handler).autorender.get(a) == 1) {
                Slot s = this.handler.slots.get(a);
                this.drawTexture(matrices, i + s.x - 1, j + s.y - 1, 0, 9, 18, 18);
            } else if (((screenHandler) this.handler).autorender.get(a) == 2) {
                Slot s = this.handler.slots.get(a);
                this.drawTexture(matrices, i + s.x - 1, j + s.y - 1, 0, 27, 26, 26);
            }
        }
    }

    public void renderPercentage(MatrixStack matrices, BlockEntity e) {
        int i = this.x;
        int j = this.y;
        for (percentage p : percentages) {
            switch (p.type) {
                case 1: {//tank
                    int a;
                    this.drawTexture(matrices, i + p.x, j + p.y, 0, 83, 15, 1);
                    for (a = 1; a <= p.l; a++)
                        this.drawTexture(matrices, i + p.x, j + p.y + a, 0, 84, 15, 1);
                    this.drawTexture(matrices, i + p.x, j + p.y + p.l + 1, 0, 85, 15, 1);
                    for (a = 1; a < getPvar(p.p, e) / 100f * p.l; a += 2)
                        this.drawTexture(matrices, i + p.x + 1, j + p.y + p.l - a, 15, 83, 13, 1);
                    for (a = 0; a < getPvar(p.p, e) / 100f * p.l; a += 2)
                        this.drawTexture(matrices, i + p.x + 1, j + p.y + p.l - a, 15, 84, 13, 1);
                    break;
                }
                case 2: {//arrow
                    this.drawTexture(matrices, i + p.x, j + p.y, 22, 67, 22, 16);
                    this.drawTexture(matrices, i + p.x, j + p.y, 0, 67, (int) (getPvar(p.p, e) / 100f * 22), 16);
                    break;
                }
                case 3: {//bar
                    this.drawTexture(matrices, i + p.x, j + p.y, 0, 87, 1, 4);
                    for (int a = 1; a <= p.l; a++)
                        this.drawTexture(matrices, i + p.x + a, j + p.y, 1, 87, 1, 4);
                    this.drawTexture(matrices, i + p.x + p.l + 1, j + p.y, 2, 87, 1, 4);
                    for (int a = 1; a <= getPvar(p.p, e) / 100f * p.l; a++)
                        this.drawTexture(matrices, i + p.x + a, j + p.y + 1, 3, 88, 1, 2);
                    break;
                }
                case 4: {//burn
                    int a = (int) (getPvar(p.p, e) / 100f * 14);
                    this.drawTexture(matrices, i + p.x, j + p.y, 14, 53, 14, 14);
                    this.drawTexture(matrices, i + p.x, j + p.y + 14 - a, 0, 53 + 14 - a, 14, a);
                    break;
                }
            }
        }
    }

    public void setButtonEnable(int id, boolean enable) {
        buttons.get(id).enable = enable;
    }

    public void setScrollEnable(int id, boolean enable) {
        scrolls.get(id).enable = enable;
    }

    public int getPvar(int index, BlockEntity blockEntity) {
        int e = getBlockEntityVar(index, blockEntity);
        return MathHelper.clamp(e, 0, 100);
    }

    public abstract int getBlockEntityVar(int index, BlockEntity blockEntity);
}
