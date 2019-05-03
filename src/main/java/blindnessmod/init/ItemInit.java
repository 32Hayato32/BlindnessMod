package blindnessmod.init;

import java.util.ArrayList;
import java.util.List;

import blindnessmod.Item.MobCaptureFullItem;
import blindnessmod.Item.MobCaptureItem;
import net.minecraft.item.Item;

public class ItemInit {
	public static final List<Item> ITEMS = new ArrayList<Item>();

	public static final Item MobCaptureEmptyItem = new MobCaptureItem("mob_capture_empty");
	public static final Item MobCaptureFullItem = new MobCaptureFullItem("mob_capture");
}
