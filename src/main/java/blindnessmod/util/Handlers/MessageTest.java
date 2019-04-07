package blindnessmod.util.Handlers;

import io.netty.buffer.ByteBuf;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;

public class MessageTest implements IMessage {

    public int flag,x,y,z;

    public MessageTest(){}

    public MessageTest(int _flag,BlockPos pos) {
    	this.flag = _flag;
    	this.x = pos.getX();
    	this.y = pos.getY();
    	this.z = pos.getZ();
    }

    @Override//IMessageのメソッド。ByteBufからデータを読み取る。
    public void fromBytes(ByteBuf buf) {
    	this.flag = buf.readInt();
    	this.x = buf.readInt();
    	this.y = buf.readInt();
    	this.z = buf.readInt();
    }

    @Override//IMessageのメソッド。ByteBufにデータを書き込む。
    public void toBytes(ByteBuf buf) {
    	buf.writeInt(flag);
    	buf.writeInt(x);
    	buf.writeInt(y);
    	buf.writeInt(z);
    }

}
