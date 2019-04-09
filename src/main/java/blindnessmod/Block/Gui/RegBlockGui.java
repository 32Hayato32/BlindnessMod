package blindnessmod.Block.Gui;

import java.io.IOException;
import java.math.BigDecimal;

import org.lwjgl.input.Mouse;

import blindnessmod.BlindnessMod;
import blindnessmod.Reference;
import blindnessmod.Block.Containers.ContainerRegBlock;
import blindnessmod.Tileentity.TileRegBlock;
import blindnessmod.util.RegListUtil;
import blindnessmod.util.Handlers.MessageTest;
import blindnessmod.util.Handlers.PacketHandler;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.MathHelper;

public class RegBlockGui extends GuiContainer{

	private static final ResourceLocation TEXTURES = new ResourceLocation(Reference.MODID + ":textures/gui/register_block.png");
	private final InventoryPlayer player;
	private final IInventory tile;
	private final TileRegBlock trb;
	private final RegListUtil RegUtil;
	private final ContainerRegBlock container;
	private int currentIdx;
    private float currentScroll;
    /** True if the scrollbar is being dragged */
    private boolean isScrolling;
    /** True if the left mouse button was held down last time drawScreen was called. */
    private boolean wasClicking;

    private int ButtonX;
    private int ButtonY;
    private boolean isButtonClick;
    private boolean isButton2Click;

    private boolean UnlockFlag;
    private boolean isSelect;
    private int SelectIdx;


	public RegBlockGui(InventoryPlayer p,IInventory t) {
		super(new ContainerRegBlock(p,t));
		this.container = (ContainerRegBlock)this.inventorySlots;
		this.RegUtil = new RegListUtil();
		this.player = p;
		this.tile = t;
		this.trb  =(TileRegBlock)t;
	}

	@Override
	public void initGui() {
		super.initGui();
		this.xSize = 180;
		this.ySize = 199;
		this.guiLeft = (this.width - this.xSize) / 2;
		this.guiTop = (this.height - this.ySize) / 2;
		this.ButtonX = 106;
		this.ButtonY = 92;
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
        int k = i + 159;
        int l = j + 9;
        int i1 = k + 14;
        int j1 = l + 74;

        //Button func//
        if(!this.wasClicking && flag) {
        	//Scroll//
        	if(mouseX >= k && mouseY >= l && mouseX < i1 && mouseY < j1)
        		this.ScrollFunc();
        	//RegButton//
        	if(!this.isSelect && !this.UnlockFlag && mouseX >= i + this.ButtonX && mouseY >= j + this.ButtonY && mouseX < i + this.ButtonX + 31 && mouseY < j + this.ButtonY + 15)
        		this.RegButtonFunc();
        	//Select//
        	if(mouseX >= i + 9 && mouseY >= j + 9 && mouseX < i + 9 + 18 * 8 && mouseY < j + 9 + 18 * 4)
        		this.SelectFunc(mouseX,mouseY,i,j);
        	//DisConutButton//
        	if(mouseX >= i + this.ButtonX && mouseY >= j + this.ButtonY + 15 && mouseX < i + this.ButtonX + 31 && mouseY < j + this.ButtonY + 30)
        		this.DisCntButtonFunc();
        }
		if(this.isSelect) {
			this.ButtonX = 145;
			this.ButtonY = 83;
		}else {
			this.ButtonX = 106;
			this.ButtonY = 92;
		}
      //END Button func//

        if (!flag)
        {
            this.isScrolling = false;
            this.isButtonClick = false;
            this.isButton2Click = false;
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

		int i = this.guiLeft + 159;
        int j = this.guiTop + 9;
        int k = j + 74;
		this.drawTexturedModalRect(x, y, 0, 0, this.xSize, this.ySize);
		this.drawTexturedModalRect( i, j + (int)((float)(k - j - 17) * this.currentScroll),isScrolling?12:0, this.ySize, 12, 15);
		int RegListCount = BlindnessMod.RegBlockList.size();

		int idxb = RegListCount / 8;
		if((RegListCount % 8) > 0) idxb++;
		if(idxb > 4) idxb -= 4;
		this.currentIdx = (int)Math.floor((idxb / 100.0f) * (this.currentScroll * 100));

		for(int cnt = currentIdx * 8;cnt < RegListCount;cnt++) {
			ItemStack item = RegUtil.getItem(cnt);
			int diff = cnt - (currentIdx * 8);
			int idxy = (diff / 8);
			int idxx = diff - (8*idxy);
			if(idxy > 3 )break;
			int count = RegUtil.getCount(item);
			ItemRender(item,count,9 + (18*idxx),9 + (18*idxy));
		}
		this.CheckStates();
		this.drawSelectionBox();
		this.drawButton();
		this.drawArrow();
		this.drawInputSlot();
		this.drawInfo();
	}

	/////////////////////////////↓自作関数↓//////////////////////////////

	private void ItemRender(ItemStack itemstack,int count, int _x,int _y) {
		this.mc.getTextureManager().bindTexture(TEXTURES);
		GlStateManager.disableLighting();
//        GlStateManager.color(1F, 1F, 1F); //Forge: Reset color in case Items change it.
//        GlStateManager.enableBlend(); //Forge: Make sure blend is enabled else tabs show a white border.
        int l = this.guiLeft + _x,i1 = this.guiTop + _y;
        this.drawTexturedModalRect(l, i1, 7, 116, 18, 18);
        this.zLevel = 100.0F;
        this.itemRender.zLevel = 50.0F;
        l = l + 1;
        i1 = i1 + 1;
        String cnt = count + "";
        if(count > 1000) {
        	BigDecimal bd = new BigDecimal(count / 1000.0f);
        	bd = bd.setScale(1, BigDecimal.ROUND_DOWN);
        	cnt =  bd.doubleValue() + "k";
        }
        GlStateManager.enableLighting();
        GlStateManager.enableRescaleNormal();
        this.itemRender.renderItemAndEffectIntoGUI(itemstack, l, i1);
        this.itemRender.renderItemOverlayIntoGUI(this.fontRenderer, itemstack, l, i1, cnt + "");

        GlStateManager.disableLighting();
        this.itemRender.zLevel = 0.0F;
        this.zLevel = 0.0F;
	}

	private boolean hasItem(int idx) {
		if(BlindnessMod.RegBlockList.size() == 0) return false;
		if(BlindnessMod.RegBlockList.keySet().toArray().length > idx)
			return true;
		return false;
	}

	private void drawSelectionBox() {
			if(this.isSelect) {
				this.mc.getTextureManager().bindTexture(TEXTURES);
				this.zLevel = 300;
				int _y = this.SelectIdx / 8;
				int _x = this.SelectIdx - (8 * _y);
				_x = this.guiLeft + (18 *_x) + 6;
				_y = this.guiTop + (18 * _y) + 6;
				this.drawTexturedModalRect(_x,_y,  110, 199, 24,24);
				this.zLevel = 0;
			}
	}

	private void drawButton() {
		this.mc.getTextureManager().bindTexture(TEXTURES);
		if(this.isSelect) {
			this.drawTexturedModalRect(this.guiLeft + this.ButtonX,this.guiTop + this.ButtonY,isButtonClick || !this.UnlockFlag? 71:40, 214, 31, 15);
			this.drawTexturedModalRect(this.guiLeft + this.ButtonX,this.guiTop + this.ButtonY + 15,isButton2Click?71:40, 229, 31,15);
			if(!this.UnlockFlag)this.drawTexturedModalRect(this.guiLeft + this.ButtonX,this.guiTop + this.ButtonY,0, 214, 31, 15);
		}else {
			this.drawTexturedModalRect(this.guiLeft + this.ButtonX,this.guiTop + this.ButtonY,isButtonClick? 71:40, 199, 31, 15);
		}
	}

	private void drawArrow() {
		this.mc.getTextureManager().bindTexture(TEXTURES);
		if(!this.isSelect)this.drawTexturedModalRect(this.guiLeft + 78,this.guiTop + 92,135, 199, 31, 15);
	}

	private void drawInputSlot() {
		this.mc.getTextureManager().bindTexture(TEXTURES);
		this.drawTexturedModalRect(this.isSelect?this.guiLeft + 4:this.guiLeft + 54, this.guiTop + 90, 7, 116, 18,18);
		this.drawString(this.fontRenderer, "いん", this.isSelect?this.guiLeft + 5:this.guiLeft + 55, this.guiTop + 82,0xffffff);
		int pos = this.isSelect?5:55;
		this.container.inventorySlots.get(0).xPos = pos;
	}

	private void drawInfo() {
		if(this.isSelect) {
			if(this.hasItem(this.SelectIdx + (this.currentIdx *8))) {
				ItemStack item = RegUtil.getItem(this.SelectIdx + (this.currentIdx *8));
				this.ItemRender(item, RegUtil.getCount(item), 38/*54*/, 85);
				int count = RegUtil.getCount(item);
				String cnt = String.format("%,d", count);
				String str = "は現在 " + cnt + " 個！";
				this.drawString(fontRenderer,str,this.guiLeft + 60,this.guiTop + 90, 0xffffff);
				int GCount = this.RegUtil.getGoalValue(item);
				if(count >= GCount) {
					str = "ノルマ達成！";
				}else {
					str = "ノルマまであと" + String.format("%,d",this.RegUtil.getLeftValue(item)) + "個！";
				}
				int width = fontRenderer.getStringWidth(str);
				this.drawString(fontRenderer,str,(this.guiLeft + 92) - width / 2,this.guiTop + 104, 0xffffff);
			}
		}
	}

	private void CheckStates() {
		if(this.isSelect) {
			if(this.hasItem(this.SelectIdx + (this.currentIdx *8))) {
				ItemStack item = this.RegUtil.getItem(this.SelectIdx + (this.currentIdx *8));
				int GoalCount = this.RegUtil.getGoalValue(item);
				this.UnlockFlag =  this.RegUtil.getCount(item) >= GoalCount;
			}
		}else {this.UnlockFlag = false;}
	}

	//Button func//
	private void ScrollFunc() {
		this.isScrolling = true;
        this.isSelect = false;
	}

	private void RegButtonFunc() {
		this.isButtonClick = true;
    	TileRegBlock t = (TileRegBlock)this.tile;
    	if(t.Reg()) {
    		t.setFlag(1);
    		PacketHandler.INSTANCE.sendToServer(new MessageTest(1,t.getPos()));
    		Minecraft.getMinecraft().renderGlobal.loadRenderers();
    	}
	}

	private void SelectFunc(int mouseX,int mouseY,int i,int j){
		int mx = mouseX - (i + 9);
		int my = mouseY - (j + 9);
		int idx = mx / 18;
		int idy = my / 18;
		if(this.hasItem((idx + idy*8) + (this.currentIdx * 8))) {
			if(this.SelectIdx == (idx + idy*8)) {
				this.isSelect = !this.isSelect;
			}else {
				this.isSelect = true;
				this.SelectIdx = (idx + idy*8);
			}
		}
	}

	private void DisCntButtonFunc() {
		this.isButton2Click = true;
    	TileRegBlock t = (TileRegBlock)this.tile;
    	if(t.addOsonae(this.SelectIdx + (this.currentIdx *8))) {
    		t.setFlag(2);
    		PacketHandler.INSTANCE.sendToServer(new MessageTest(2,t.getPos()));
    	}
	}

	 //END Button func//

	///////////////////////////////////////////////////////////////////////////

}