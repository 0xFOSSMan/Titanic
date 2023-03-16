package cc.noxiuam.titanic.client.module.impl.hud;

import cc.noxiuam.titanic.client.module.AbstractModule;
import cc.noxiuam.titanic.client.module.data.anchor.GuiAnchor;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;
import net.minecraft.src.ScaledResolution;
import org.lwjgl.opengl.GL11;

@Setter
@Getter
@Accessors(fluent = true)
public abstract class AbstractMovableModule extends AbstractModule {

    private float x;
    private float y;

    public float defaultX = 0.0f;
    public float defaultY = 0.0f;

    private float width;
    private float height;

    public AbstractMovableModule(String id, String name, boolean enabledByDefault) {
        super(id, name, enabledByDefault);
    }

    public void setPosition(float newX, float newY) {
        this.x = newX;
        this.y = newY;
    }

    public void setSize(float newWidth, float newHeight) {
        this.width = newWidth;
        this.height = newHeight;
    }

    public void scaleAndTranslate() {
        float scaledX = 0.0f;
        float scaledY = 0.0f;
        float scale = /*(Float) this.scale.getValue()*/ 1.0F;
        GL11.glScalef(scale, scale, scale);

        GL11.glTranslatef(scaledX / scale, scaledY / scale, 0.0f);
        GL11.glTranslatef(this.x / scale, this.y / scale, 0.0f);
    }

    public float[] getScaledPoints(ScaledResolution res, boolean useActualTranslation) {
        float x = 0.0f;
        float y = 0.0f;
        float scaledWidth = this.width * this.masterScale();
        float scaledHeight = this.height * this.masterScale();

        x = 2.0f;
        y = (float) (res.getScaledHeight() / 2) - scaledHeight / 2.0f;

        return new float[] {
                (x + (useActualTranslation ? this.x : 0.0f)) / this.masterScale(),
                (y + (useActualTranslation ? this.y : 0.0f)) / this.masterScale()
        };
    }

    public float masterScale() {
        return 1.0F;
    }

}
