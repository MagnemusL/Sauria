package net.smazeee.sauria.event;

import net.minecraftforge.event.entity.EntityAttributeCreationEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.smazeee.sauria.Sauria;
import net.smazeee.sauria.entity.ModEntityTypes;
import net.smazeee.sauria.entity.custom.AigialosaurusEntity;

public class ModEvents {

    @Mod.EventBusSubscriber(modid = Sauria.MODID)
    public static class ForgeEvents {

    }

    @Mod.EventBusSubscriber(modid = Sauria.MODID, bus = Mod.EventBusSubscriber.Bus.MOD)
    public static class ModEventBusEvents {
        @SubscribeEvent
        public static void entityAttributeEvent(EntityAttributeCreationEvent event) {
            event.put(ModEntityTypes.AIGIALOSAURUS.get(), AigialosaurusEntity.setAttributes());
        }
    }
}
