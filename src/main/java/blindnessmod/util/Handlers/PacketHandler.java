package blindnessmod.util.Handlers;

import net.minecraftforge.fml.common.network.NetworkRegistry;
import net.minecraftforge.fml.common.network.simpleimpl.SimpleNetworkWrapper;
import net.minecraftforge.fml.relauncher.Side;

public class PacketHandler {

	public static final SimpleNetworkWrapper INSTANCE = NetworkRegistry.INSTANCE.newSimpleChannel("TestPacket");

	public static void init() {
		INSTANCE.registerMessage(MessageTestHandler.class, MessageTest.class, 0, Side.SERVER);
		INSTANCE.registerMessage(MessageTestHandler.class, MessageTest.class, 0, Side.CLIENT);
	};

}
