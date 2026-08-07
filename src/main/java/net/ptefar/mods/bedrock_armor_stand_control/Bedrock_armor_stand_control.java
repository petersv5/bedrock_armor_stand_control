package net.ptefar.mods.bedrock_armor_stand_control;

import org.geysermc.geyser.api.GeyserApi;
import org.geysermc.geyser.api.connection.GeyserConnection;
//import org.geysermc.geyser.api.util.Rotations;
import org.geysermc.cumulus.form.CustomForm;
import org.geysermc.cumulus.component.SliderComponent;
import org.geysermc.cumulus.component.ToggleComponent;

import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.command.v2.CommandRegistrationCallback;
import net.fabricmc.fabric.api.event.player.UseEntityCallback;
import net.minecraft.commands.Commands;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.decoration.ArmorStand;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.phys.AABB;
import net.minecraft.core.Rotations;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.item.ItemStack;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;

import java.util.UUID;
import java.util.List;

import net.ptefar.mods.bedrock_armor_stand_control.mixin.ArmorStandAccessor;

public class Bedrock_armor_stand_control implements ModInitializer {

    @Override
    public void onInitialize() {
        // Register the command
//        CommandRegistrationCallback.EVENT.register((dispatcher, registryAccess, environment) -> {
//            dispatcher.register(Commands.literal("basc")
//                .requires(source -> {
//                    if (!source.isPlayer()) {
//                        return false;
//                    }
//                    try {
//                        ServerPlayer player = source.getPlayer();
//                        if (player == null) return false;
//                        
//                        GeyserConnection connection = GeyserApi.api().connectionByUuid(player.getUUID());
//                        return connection != null;
//                    } catch (Exception e) {
//                        return false;
//                    }
//                })
//                .executes(context -> {
//                    if (context.getSource().getEntity() instanceof ServerPlayer player) {
//                        ArmorStand closest = getClosestArmorStand(player);
//                        if (closest != null) {
//                            openArmorStandForm(player, closest);
//                        } else {
//                            context.getSource().sendFailure(Component.literal("No nearby Armor Stands."));
//                        }
//                    }
//                    return 1;
//                }));
//        });

        // Register shift-click on the armor stand
        UseEntityCallback.EVENT.register((player, level, hand, entity, entityHitResult) -> {
            if (!level.isClientSide() && entity instanceof ArmorStand armorStand && player instanceof ServerPlayer serverPlayer) {
                if (serverPlayer.isShiftKeyDown() && hand == InteractionHand.MAIN_HAND) {
                    if (isBedrockPlayer(serverPlayer)) {
                        openArmorStandForm(serverPlayer, armorStand);
                        return InteractionResult.SUCCESS;
                    }
                }
            }
            return InteractionResult.PASS;
        });
    }

    // Verify that the player is a Bedrock player
    private boolean isBedrockPlayer(ServerPlayer player) {
        return GeyserApi.api().connectionByUuid(player.getUUID()) != null;
    }

//    // Find the nearest armor stand (only used if activated by command)
//    private ArmorStand getClosestArmorStand(ServerPlayer player) {
//        AABB box = player.getBoundingBox().inflate(5.0);
//        List<ArmorStand> entities = player.level().getEntitiesOfClass(ArmorStand.class, box);
//        ArmorStand closest = null;
//        double minDistance = Double.MAX_VALUE;
//        for (ArmorStand stand : entities) {
//            double dist = player.distanceToSqr(stand);
//            if (dist < minDistance) {
//                minDistance = dist;
//                closest = stand;
//            }
//        }
//        return closest;
//    }

    // Build the cumulus form and set up the response handling
    private void openArmorStandForm(ServerPlayer player, ArmorStand stand) {
        GeyserConnection connection = GeyserApi.api().connectionByUuid(player.getUUID());
        if (connection == null) return;

        // Get rotations in degrees
        Rotations headRot = stand.getHeadPose();
        Rotations bodyRot =stand.getBodyPose();
        Rotations leftArmRot = stand.getLeftArmPose();
        Rotations rightArmRot = stand.getRightArmPose();
        Rotations leftLegRot = stand.getLeftLegPose();
        Rotations rightLegRot = stand.getRightLegPose();

        CustomForm.Builder form = CustomForm.builder()
            .title("Bedrock Armor Stand Control")
            
            .label("--- Rotations (-179 - 180) ---")
            .slider("Overall", -179, 180, 1, stand.getYRot())

            .slider("Head X", -179, 180, 1, headRot.x())
            .slider("Head Y", -179, 180, 1, headRot.y())
            .slider("Head Z", -179, 180, 1, headRot.z())
            
            .slider("Body X", -179, 180, 1, bodyRot.x())
            .slider("Body Y", -179, 180, 1, bodyRot.y())
            .slider("Body Z", -179, 180, 1, bodyRot.z())
            
            .slider("Left Arm X", -179, 180, 1, leftArmRot.x())
            .slider("Left Arm Y", -179, 180, 1, leftArmRot.y())
            .slider("Left Arm Z", -179, 180, 1, leftArmRot.z())
            
            .slider("Right Arm X", -179, 180, 1, rightArmRot.x())
            .slider("Right Arm Y", -179, 180, 1, rightArmRot.y())
            .slider("Right Arm Z", -179, 180, 1, rightArmRot.z())
            
            .slider("Left Leg X", -179, 180, 1, leftLegRot.x())
            .slider("Left Leg Y", -179, 180, 1, leftLegRot.y())
            .slider("Left Leg Z", -179, 180, 1, leftLegRot.z())
            
            .slider("Right Leg X", -179, 180, 1, rightLegRot.x())
            .slider("Right Leg Y", -179, 180, 1, rightLegRot.y())
            .slider("Right Leg Z", -179, 180, 1, rightLegRot.z())

            .label("--- Nudge position ---")
            .slider("Pos X", -4, 4, 0.1f, 0)
            .slider("Pos Y", -4, 4, 0.1f, 0)
            .slider("Pos Z", -4, 4, 0.1f, 0)

            // Property settings
            .label("--- Properties ---")
            .toggle("Show name tag", stand.isCustomNameVisible())
            .toggle("Show bass plate", stand.showBasePlate())
            .toggle("Show arms", stand.showArms())
            .toggle("Gravity", !stand.isNoGravity())
            .toggle("Invisible", stand.isInvisible())
            .toggle("Small", stand.isSmall())
            .toggle("Lock", ((ArmorStandAccessor)stand).getDisabledSlots() != 0)
            .slider("Scale", 0.01f, 4, 0.01f, (float)(stand.getAttribute(Attributes.SCALE).getBaseValue()))
            
            // Actions (executed on submit)
            .label("--- One time actions ---")
            .toggle("Swap: Mainhand <-> Offhand", false)
            .toggle("Swap: Mainhand <-> Head", false);

        form.validResultHandler(response -> {
            // Extract rotations
            float mainY = response.next();
            float hX = response.next(); float hY = response.next(); float hZ = response.next();
            float bX = response.next(); float bY = response.next(); float bZ = response.next();
            float laX = response.next(); float laY = response.next(); float laZ = response.next();
            float raX = response.next(); float raY = response.next(); float raZ = response.next();
            float llX = response.next(); float llY = response.next(); float llZ = response.next();
            float rlX = response.next(); float rlY = response.next(); float rlZ = response.next();

            // Extract position nudge
            float dX = response.next(); float dY = response.next(); float dZ = response.next();

            // Extract booleans for properties
            boolean showName = response.next();
            boolean showBasePlate = response.next();
            boolean showArms = response.next();
            boolean hasGravity = response.next();
            boolean isInvisible = response.next();
            boolean isSmall = response.next();
            boolean isLocked = response.next();
            float scale = response.next();

            // Extract booleans for actions
            boolean swapHand = response.next();
            boolean swapHead = response.next();

            // Apply changes in the server thread
            player.level().getServer().execute(() -> {
                if (!stand.isAlive()) return;

                stand.setYRot(mainY);
                stand.setHeadPose(new Rotations(hX, hY, hZ));
                stand.setBodyPose(new Rotations(bX, bY, bZ));
                stand.setLeftArmPose(new Rotations(laX, laY, laZ));
                stand.setRightArmPose(new Rotations(raX, raY, raZ));
                stand.setLeftLegPose(new Rotations(llX, llY, llZ));
                stand.setRightLegPose(new Rotations(rlX, rlY, rlZ));

                stand.setPos(stand.getX() + dX, stand.getY() + dY, stand.getZ() + dZ);

                stand.setCustomNameVisible(showName);
                stand.setNoBasePlate(!showBasePlate);
                
                // Handle arms via bitwise flags
                byte b = stand.getEntityData().get(ArmorStand.DATA_CLIENT_FLAGS);
                if (showArms) b = (byte)(b | 4); else b = (byte)(b & ~4);
                if (isSmall) b = (byte)(b | 1); else b = (byte)(b & ~1);
                stand.getEntityData().set(ArmorStand.DATA_CLIENT_FLAGS, b);

                stand.setNoGravity(!hasGravity);
                stand.setInvisible(isInvisible);

                // Lock armor stand by disabling all slots (bitmask)
                if (isLocked) {
                    ((ArmorStandAccessor)stand).setDisabledSlots(0x3f3f3f); // Lock all slots
                } else {
                    ((ArmorStandAccessor)stand).setDisabledSlots(0x0); // Unlock all slots
                }

                stand.getAttribute(Attributes.SCALE).setBaseValue(scale);

                // Execute any reqeusted actions
                if (swapHand) {
                   ItemStack main = stand.getMainHandItem();
                   ItemStack off = stand.getOffhandItem();
                   stand.setItemInHand(InteractionHand.MAIN_HAND, off);
                   stand.setItemInHand(InteractionHand.OFF_HAND, main);
                }
                if (swapHead) {ItemStack main = stand.getMainHandItem();
                   ItemStack head = stand.getItemBySlot(net.minecraft.world.entity.EquipmentSlot.HEAD);
                   stand.setItemInHand(InteractionHand.MAIN_HAND, head);
                   stand.setItemSlot(net.minecraft.world.entity.EquipmentSlot.HEAD, main);
                }
                player.sendSystemMessage(Component.literal("Armor stand updated!"));
            });
        });
        connection.sendForm(form);
    }}
