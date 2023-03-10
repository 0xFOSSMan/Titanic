package net.minecraft.src;

public class GuiControls extends GuiScreen {

    protected String screenTitle;
    private GuiScreen parentScreen;
    private GameSettings options;
    private int buttonId;

    public GuiControls(GuiScreen guiscreen, GameSettings gamesettings) {
        screenTitle = "Controls";
        buttonId = -1;
        parentScreen = guiscreen;
        options = gamesettings;
    }

    private int func_20080_j() {
        return width / 2 - 155;
    }

    public void initGui() {
        StringTranslate stringtranslate = StringTranslate.func_20162_a();
        int i = func_20080_j();
        for (int j = 0; j < options.keyBindings.length; j++) {
            controlList.add(new GuiSmallButton(j, i + (j % 2) * 160, height / 6 + 24 * (j >> 1), 70, 20, options.getOptionDisplayString(j)));
        }

        controlList.add(new GuiButton(200, width / 2 - 100, height / 6 + 168, stringtranslate.func_20163_a("gui.done")));
        screenTitle = stringtranslate.func_20163_a("controls.title");
    }

    protected void actionPerformed(GuiButton guibutton) {
        for (int i = 0; i < options.keyBindings.length; i++) {
            ((GuiButton) controlList.get(i)).displayString = options.getOptionDisplayString(i);
        }

        if (guibutton.id == 200) {
            mc.displayGuiScreen(parentScreen);
        } else {
            buttonId = guibutton.id;
            guibutton.displayString = (new StringBuilder()).append("> ").append(options.getOptionDisplayString(guibutton.id)).append(" <").toString();
        }
    }

    protected void keyTyped(char c, int i) {
        if (buttonId >= 0) {
            options.setKeyBinding(buttonId, i);
            ((GuiButton) controlList.get(buttonId)).displayString = options.getOptionDisplayString(buttonId);
            buttonId = -1;
        } else {
            super.keyTyped(c, i);
        }
    }

    public void drawScreen(int i, int j, float f) {
        drawDefaultBackground();
        drawCenteredString(fontRenderer, screenTitle, width / 2, 20, 0xffffff);
        int k = func_20080_j();
        for (int l = 0; l < options.keyBindings.length; l++) {
            drawString(fontRenderer, options.func_20102_a(l), k + (l % 2) * 160 + 70 + 6, height / 6 + 24 * (l >> 1) + 7, -1);
        }

        super.drawScreen(i, j, f);
    }
}
