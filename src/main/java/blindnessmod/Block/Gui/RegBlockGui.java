package blindnessmod.Block.Gui;

import java.io.IOException;

import org.lwjgl.input.Mouse;

import blindnessmod.Reference;
import blindnessmod.Block.Containers.ContainerRegBlock;
import blindnessmod.Tileentity.TileRegBlock;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.MathHelper;

public class RegBlockGui extends GuiContainer{

	private static final ResourceLocation TEXTURES = new ResourceLocation(Reference.MODID + ":textures/gui/register_block.png");
	private final InventoryPlayer player;
	private final TileRegBlock tile;

    private float currentScroll;
    /** True if the scrollbar is being dragged */
    private boolean isScrolling;
    /** True if the left mouse button was held down last time drawScreen was called. */
    private boolean wasClicking;


	public RegBlockGui(InventoryPlayer p,TileRegBlock t) {
		super(new ContainerRegBlock(p,t));
		this.player = p;
		this.tile = t;
	}

	@Override
	public void initGui() {
		super.initGui();
		this.xSize = 176;
		this.ySize = 202;
		this.guiLeft = (this.width - this.xSize) / 2;
		this.guiTop = (this.height - this.ySize) / 2;
	}

	@Override
	public void handleMouseInput() throws IOException {
		// TODO 自動生成されたメソッド・スタブ
		super.handleMouseInput();
	}

	@Override
	public void drawScreen(int mouseX, int mouseY, float partialTicks) {
		this.drawDefaultBackground();
        boolean flag = Mouse.isButtonDown(0);
        int i = this.guiLeft;
        int j = this.guiTop;
        int k = i + 155;
        int l = j + 9;
        int i1 = k + 14;
        int j1 = l + 77;

        if (!this.wasClicking && flag && mouseX >= k && mouseY >= l && mouseX < i1 && mouseY < j1)
        {
            this.isScrolling = true;
        }

        if (!flag)
        {
            this.isScrolling = false;
        }
        this.wasClicking = flag;
        if (this.isScrolling)
        {
            this.currentScroll = ((float)(mouseY - l) - 7.5F) / ((float)(j1 - l) - 15.0F);
            this.currentScroll = MathHelper.clamp(this.currentScroll, 0.0F, 1.0F);
        }

		super.drawScreen(mouseX, mouseY, partialTicks);
	}

	@Override
	protected void drawGuiContainerBackgroundLayer(float arg0, int arg1, int arg2) {
		GlStateManager.color(1.0f, 1.0f, 1.0f, 1.0f);
		RenderHelper.enableGUIStandardItemLighting();
		this.mc.getTextureManager().bindTexture(TEXTURES);
		int x = this.guiLeft;
		int y = this.guiTop;

		int i = this.guiLeft + 155;
        int j = this.guiTop + 9;
        int k = j + 77;
		this.drawTexturedModalRect(x, y, 0, 0, this.xSize, this.ySize);
		this.drawTexturedModalRect( i, j + (int)((float)(k - j - 17) * this.currentScroll),isScrolling?12:0, this.ySize, 12, 15);
		ItemRender(9,9);
		ItemRender(18,9);
	}

	private void ItemRender(int _x,int _y) {
		GlStateManager.disableLighting();
        GlStateManager.color(1F, 1F, 1F); //Forge: Reset color in case Items change it.
        GlStateManager.enableBlend(); //Forge: Make sure blend is enabled else tabs show a white border.
        //int l = this.guiLeft + 9,i1 = this.guiTop + 9;
        int l = this.guiLeft + _x,i1 = this.guiTop + _y;
        this.drawTexturedModalRect(l, i1, 7, 119, 18, 18);
        this.zLevel = 100.0F;
        this.itemRender.zLevel = 100.0F;
        l = l + 1;
        i1 = i1 + 1;
        GlStateManager.enableLighting();
        GlStateManager.enableRescaleNormal();
        ItemStack itemstack = new ItemStack(Item.getItemFromBlock(Blocks.CRAFTING_TABLE),1);
        this.itemRender.renderItemAndEffectIntoGUI(itemstack, l, i1);
        this.itemRender.renderItemOverlays(this.fontRenderer, itemstack, l, i1);
        GlStateManager.disableLighting();
        this.itemRender.zLevel = 0.0F;
        this.zLevel = 0.0F;
	}
}