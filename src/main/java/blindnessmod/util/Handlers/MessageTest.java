package blindnessmod.util.Handlers;

import io.netty.buffer.ByteBuf;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;

public class MessageTest implements IMessage {

    public int flag,index,x,y,z;

    public MessageTest(){}

    public MessageTest(int _flag,BlockPos pos) {
    	this.flag = _flag;
    	this.index = -1;
    	this.x = pos.getX();
    	this.y = pos.getY();
    	this.z = pos.getZ();
    }

    public MessageTest(int _flag,int _index,BlockPos pos) {
    	this.flag = _flag;
    	this.index = _index;
    	this.x = pos.getX();
    	this.y = pos.getY();
    	this.z = pos.getZ();
    }

    @Override
    public void fromBytes(ByteBuf buf) {
    	this.flag = buf.readInt();
    	this.index = buf.readInt();
    	this.x = buf.readInt();
    	this.y = buf.readInt();
    	this.z = buf.readInt();
    }

    @Override
    public void toBytes(ByteBuf buf) {
    	buf.writeInt(flag);
    	buf.writeInt(index);
    	buf.writeInt(x);
    	buf.writeInt(y);
    	buf.writeInt(z);
    }

}
