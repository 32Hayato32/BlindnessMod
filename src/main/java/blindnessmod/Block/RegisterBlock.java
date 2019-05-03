package blindnessmod.Block;

import blindnessmod.BlindnessMod;
import blindnessmod.Reference;
import blindnessmod.Tileentity.TileRegBlock;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.TextComponentTranslation;
import net.minecraft.world.World;

public class RegisterBlock extends BlockBase {

	int shipSpawnDetectorID;

	public RegisterBlock(String name) {
		super(name, Material.IRON, BlindnessMod.BlindnessMod);
		setSoundType(SoundType.METAL);
	}

	@Override
	public boolean onBlockActivated(World w, BlockPos pos, IBlockState state, EntityPlayer p,
			EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ) {
		if(w.isRemote) {
			return true;
		}else {
			p.openGui(BlindnessMod.INSTANCE,Reference.GUI_REGISTER_BLOCK,w,pos.getX(),pos.getY(),pos.getZ());
			return true;
		}
	}

	@Override
	public boolean hasTileEntity(IBlockState s) {
		return true;
	}

	private void sendMessage(EntityPlayer p,String str) {
		p.sendMessage(new TextComponentTranslation(str));
	}

	@Override
	public void onBlockClicked(World worldIn, BlockPos pos, EntityPlayer playerIn) {
		// TODO 自動生成されたメソッド・スタブ
		super.onBlockClicked(worldIn, pos, playerIn);
		playerIn.sendMessage(new TextComponentTranslation("Clicked!!"));
	}

	@Override
	public TileEntity createTileEntity(World world, IBlockState state)
	{
		return new TileRegBlock();
	}
}