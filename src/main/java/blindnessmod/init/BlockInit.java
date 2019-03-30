package blindnessmod.init;

import java.util.ArrayList;
import java.util.List;

import blindnessmod.Block.RegisterBlock;
import net.minecraft.block.Block;

public class BlockInit {
	public static final List<Block> BLOCKS = new ArrayList<Block>();

	public static final Block RegisterBlock = new RegisterBlock("register_block");
}
