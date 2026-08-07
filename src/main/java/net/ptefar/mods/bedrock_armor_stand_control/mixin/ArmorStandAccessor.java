package net.ptefar.mods.bedrock_armor_stand_control.mixin;

import net.minecraft.world.entity.decoration.ArmorStand;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

@Mixin(ArmorStand.class)
public interface ArmorStandAccessor {
    
    // Mojmap field mapping for the disabledSlots bitmask
    @Accessor("disabledSlots")
    void setDisabledSlots(int disabledSlots);
    
    @Accessor("disabledSlots")
    int getDisabledSlots();
}
