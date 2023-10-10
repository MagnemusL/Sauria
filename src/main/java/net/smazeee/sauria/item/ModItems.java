package net.smazeee.sauria.item;

import net.minecraft.world.item.Item;
import net.minecraftforge.common.ForgeSpawnEggItem;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;
import net.smazeee.sauria.Sauria;
import net.smazeee.sauria.entity.ModEntityTypes;

public class ModItems {
    public static final DeferredRegister<Item> ITEMS =
            DeferredRegister.create(ForgeRegistries.ITEMS, Sauria.MODID);


    public static final RegistryObject<Item> AIGIALOSAURUS_TAIL = ITEMS.register("aigialosaurus_tail",
            () -> new Item(new Item.Properties().stacksTo(1)));

    public static final RegistryObject<Item> AIGIALOSAURUS_SPAWN_EGG = ITEMS.register("aigialosaurus_spawn_egg",
            () -> new ForgeSpawnEggItem(ModEntityTypes.AIGIALOSAURUS, 0x22b341, 0x19732e, new Item.Properties().stacksTo(1)));


    public static void register(IEventBus eventBus) {
        ITEMS.register(eventBus);
    }
}
