package blindnessmod.Entitys;

import javax.annotation.Nullable;

import blindnessmod.BlindnessMod;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.world.World;

public class TESTEntity extends Entity{

//    public static final DataParameter<Boolean> IS_NAME_CUSTOM = EntityDataManager.<Boolean>createKey(Entity.class,
//            DataSerializers.BOOLEAN);
    public static final AxisAlignedBB ZERO_BB = new AxisAlignedBB(0, 0, 0, 0, 0, 0);
    // TODO: Replace these raw types with something safer
    public double pitch;
    public double yaw;
    public double roll;
    public double prevPitch;
    public double prevYaw;
    public double prevRoll;

	public TESTEntity(World worldIn) {
		super(worldIn);
		BlindnessMod.entList.add(this);
	}

	public TESTEntity(World w, double x, double y, double z, @Nullable EntityPlayer creator,
            int detectorID) {
        this(w);
        posX = x;
        posY = y;
        posZ = z;

	}

	@Override
	protected void entityInit() {
	}

	@Override
	protected void readEntityFromNBT(NBTTagCompound compound) {
	}

	@Override
	protected void writeEntityToNBT(NBTTagCompound compound) {
	}

}
