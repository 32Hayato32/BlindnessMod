package blindnessmod.util;

import blindnessmod.BlindnessMod;

public class SpecialWhiteListUtil {

	public static boolean hasBlock(String key) {
		return BlindnessMod.SpecialWhiteBlockList.indexOf(key) > -1;
	}

	public static void Reg(String key) {
		if(!hasBlock(key)) {
			BlindnessMod.SpecialWhiteBlockList.add(key);
		}
	}
}
