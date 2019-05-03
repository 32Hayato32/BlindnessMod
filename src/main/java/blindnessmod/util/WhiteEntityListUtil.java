package blindnessmod.util;

import blindnessmod.BlindnessMod;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityList;

public class WhiteEntityListUtil {
	public static void Reg(String name) {
		if(!BlindnessMod.WhiteEntityList.contains(name))
			BlindnessMod.WhiteEntityList.add(name);
	}

	public static boolean Check(Entity e) {
		return BlindnessMod.WhiteEntityList.contains(EntityList.getEntityString(e));
	}
	public static boolean Check(String key) {
		return BlindnessMod.WhiteEntityList.contains(key);
	}
}
