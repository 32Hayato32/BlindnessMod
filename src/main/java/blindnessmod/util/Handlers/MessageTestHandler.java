package blindnessmod.util.Handlers;

import blindnessmod.Tileentity.TileRegBlock;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;

public class MessageTestHandler implements IMessageHandler<MessageTest, IMessage> {

	@Override
	public IMessage onMessage(MessageTest message, MessageContext ctx) {
        EntityPlayerMP player = ctx.getServerHandler().player;
        player.getServerWorld().addScheduledTask(() -> {
            World world = player.world;
            TileRegBlock te = (TileRegBlock) world.getTileEntity(new BlockPos(message.x,message.y,message.z));
            System.out.println(message.x);
            te.setFlag(message.flag);
            te.markDirty();
        });
		return null;
	}

}
