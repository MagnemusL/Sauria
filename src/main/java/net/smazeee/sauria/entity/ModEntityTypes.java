package net.smazeee.sauria.entity;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;
import net.smazeee.sauria.Sauria;
import net.smazeee.sauria.entity.custom.AigialosaurusEntity;

public class ModEntityTypes {
    public static final DeferredRegister<EntityType<?>> ENTITY_TYPES =
            DeferredRegister.create(ForgeRegistries.ENTITY_TYPES, Sauria.MODID);

    public static final RegistryObject<EntityType<AigialosaurusEntity>> AIGIALOSAURUS =
            ENTITY_TYPES.register("aigialosaurus",
                    () -> EntityType.Builder.of(AigialosaurusEntity::new, MobCategory.MONSTER)
                            .sized(0.4F, 1.5F)
                            .build(new ResourceLocation(Sauria.MODID, "aigialosaurus").toString()));

    public static void register(IEventBus eventBus) {
        ENTITY_TYPES.register(eventBus);
    }
}
