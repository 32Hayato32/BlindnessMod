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

	public RegisterBlock(String name) {
		super(name, Material.IRON, BlindnessMod.BlindnessMod);
		setSoundType(SoundType.METAL);
	}

	@Override
	public boolean onBlockActivated(World w, BlockPos pos, IBlockState state, EntityPlayer p,
			EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ) {
//		if(!w.isRemote) {
//			p.sendMessage(new TextComponentTranslation("Activated!!"));
//			if(w.getBlockState(pos.down()).getBlock() == Blocks.IRON_BLOCK) {
//			//手に持っているアイテムの情報出力
////				ItemBlock ib = (ItemBlock)p.getHeldItem(hand).getItem();
////				sendMessage(p,"---------------DEBUG----------------");
////				sendMessage(p,Block.getStateId(ib.getBlock().getStateFromMeta(p.getHeldItem(hand).getMetadata()))+"");
////				sendMessage(p,"------------------------------------");
//				ItemStack heldI = p.getHeldItem(hand);
//				int id = Item.getIdFromItem(heldI.getItem());
//				int meta = heldI.getMetadata();
//				samplemod.samplemod.WhiteBlockList.add(id + ":" + meta);
//			}else {
//			//下にあるブロックの情報出力
//				sendMessage(p,"---------------DEBUG----------------");
//				IBlockState s = w.getBlockState(pos.down());
//				sendMessage(p,"Block : " + s.getBlock().getLocalizedName() +"");
//				sendMessage(p,"Item : " + Item.getIdFromItem(s.getBlock().getItem(null, null, s).getItem()));
//				sendMessage(p,"Meta : " + s.getBlock().getItem(null, null, s).getMetadata());
//				sendMessage(p," damageDroppedID : " + s.getBlock().getStateFromMeta(s.getBlock().getItem(null, null, s).getMetadata()));
//				sendMessage(p,"------------------------------------");
//			}
//		}

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