package cc.noxiuam.titanic.client.ui.impl.editor;

import cc.noxiuam.titanic.Titanic;
import cc.noxiuam.titanic.client.module.AbstractModule;
import cc.noxiuam.titanic.client.module.impl.hud.AbstractMovableModule;
import cc.noxiuam.titanic.client.ui.impl.GuiScreenWrapper;
import cc.noxiuam.titanic.client.ui.component.type.button.RoundedIconButton;
import cc.noxiuam.titanic.client.ui.impl.editor.component.HUDModuleComponent;
import cc.noxiuam.titanic.client.ui.util.FontUtil;
import cc.noxiuam.titanic.client.ui.util.RenderUtil;
import cc.noxiuam.titanic.client.util.MathUtil;
import cc.noxiuam.titanic.client.util.chat.ChatColor;
import cc.noxiuam.titanic.client.util.sound.SoundUtil;
import net.minecraft.client.Minecraft;
import net.minecraft.src.MathHelper;
import net.minecraft.src.ScaledResolution;
import org.lwjgl.input.Mouse;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

public class HudLayoutEditor extends GuiScreenWrapper {

    private final RoundedIconButton modsButton = new RoundedIconButton(
            "/titanic/pencil.png",
            true,
            17,
            17,
            4,
            4
    );
    private final Minecraft mc = Titanic.getInstance().getBridge().getMinecraftBridge().bridge$getMinecraft();

    private final List<HUDModuleComponent> hudModuleComponents = new CopyOnWriteArrayList<>();
    private final List<AbstractMovableModule> hudModules = new CopyOnWriteArrayList<>();

    private AbstractMovableModule selectedModule;

    private float prevX;
    private float prevY;

    private int mouseX;
    private int mouseY;

    public HudLayoutEditor() {
        for (AbstractModule module : Titanic.getInstance().getModuleManager().getMods()) {
            if (module instanceof AbstractMovableModule) {
                hudModules.add((AbstractMovableModule) module);
                ((AbstractMovableModule) module).setPosition(50, 50);
                hudModuleComponents.add(new HUDModuleComponent((AbstractMovableModule) module));
            }
        }

        this.mouseX = -1;
        this.mouseY = -1;
    }

    @Override
    public void initGui() {
        super.initGui();
        modsButton.size(25, 25);
        modsButton.position(width / 2.0F - 12, height / 2.0F - 12);
    }

    @Override
    public void drawScreen(int x, int y) {

        ScaledResolution scaledResolution = new ScaledResolution(this.mc.displayWidth, this.mc.displayHeight);

        for (HUDModuleComponent component : this.hudModuleComponents) {
            if (component.mouseInside(x, y) && Mouse.isButtonDown(0)) {
                this.dragModule(component.getModule(), x, y, scaledResolution);
            }

            component.draw(x, y);
        }

        if (this.mouseX != -1) {
            if (Mouse.isButtonDown(0) && selectedModule == null) {
                if (this.mouseX != x && this.mouseY != y) {
                    RenderUtil.drawRectWithOutline(
                            this.mouseX,
                            this.mouseY,
                            x,
                            y,
                            0.49f,
                            0xFF00C2FF,
                            0x7000C2FF);
                }
            } else {
                this.mouseX = -1;
                this.mouseY = -1;
            }
        }

        modsButton.draw(x, y);

        String blueText = "HUD";
        String indicator = "You are currently editing the";
        mc.fontRenderer.drawStringWithShadow(blueText + ChatColor.WHITE + ".", width / 2 + mc.fontRenderer.getStringWidth(indicator) / 2 - 4, height / 2 + 21, 0xFF00C2FF);
        FontUtil.drawCenteredString(indicator, width / 2 - mc.fontRenderer.getStringWidth(blueText) / 2, height / 2 + 21, -1);
    }

    @Override
    protected void mouseMovedOrUp(int i, int j, int k) {
        this.selectedModule = null;
    }

    @Override
    protected void mouseClicked(int x, int y, int button) {
        if (this.modsButton.mouseInside(x, y)) {
            SoundUtil.playClick();
            mc.displayGuiScreen(new ModSettingsEditor());
        }

        this.mouseX = x;
        this.mouseY = y;

        this.prevX = x;
        this.prevY = y;

        for (HUDModuleComponent moduleComponent : this.hudModuleComponents) {
            if (moduleComponent.mouseInside(x, y)) {
                this.selectedModule = moduleComponent.getModule();
            }
        }
    }

    private void dragModule(AbstractMovableModule module, int mouseX, int mouseY, ScaledResolution scaledResolution) {
        if (!module.enabled()) {
            return;
        }

        float f4 = (float) mouseX - this.prevX;
        float f5 = (float) mouseY - this.prevY;

        float[] scaledPoints = module.getScaledPoints(scaledResolution, false);
        f4 = this.getXTranslation(module, f4, scaledPoints, (float)((int)(module.width())));
        f5 = this.getYTranslation(module, f5, scaledPoints, (float)((int)(module.height())));

        module.setPosition(f4, f5);
    }

    private float getXTranslation(AbstractModule cBModule, float f, float[] arrf, float f2) {
        if (f + arrf[0] < 3f) {
            f = -arrf[0] + 3f;
        } else if (f + arrf[0] + f2 > (this.width - 3f)) {
            f = (int)((float)this.width - arrf[0] - f2 - 3f);
        }
        return f;
    }

    private float getYTranslation(AbstractModule cBModule, float f, float[] arrf, float f2) {
        if (f + arrf[1] < 2f) {
            f = -arrf[1] + 2f;
        } else if (f + arrf[1] + f2 > (this.height - 2f)) {
            f = (int)((float)this.height - arrf[1] - f2 - 2f);
        }
        return f;
    }

}
