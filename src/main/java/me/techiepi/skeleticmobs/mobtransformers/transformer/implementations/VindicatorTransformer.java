package me.techiepi.skeleticmobs.mobtransformers.transformer.implementations;

import me.techiepi.skeleticmobs.mobtransformers.enums.TransformerType;
import me.techiepi.skeleticmobs.mobtransformers.transformer.Transformer;
import org.bukkit.Color;
import org.bukkit.Material;
import org.bukkit.entity.*;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntitySpawnEvent;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.LeatherArmorMeta;
import org.jetbrains.annotations.NotNull;

import java.util.Objects;

public class VindicatorTransformer extends Transformer implements Listener {
    //TODO: Fix raids

    @Override
    public void start(Entity entity) {
        super.setSkeletonType(TransformerType.VINDICATOR);
        super.setName("Vindicator Skeleton");
        super.transformEntity(entity);
    }

    @Override
    public void personalizeSkeleton(@NotNull Skeleton skeleton, @NotNull TransformerType type) {
        ItemStack axeStack = new ItemStack(Material.IRON_AXE, 1);
        ItemStack helmetStack = new ItemStack(Material.LEATHER_HELMET, 1);

        LeatherArmorMeta armorMeta = (LeatherArmorMeta) helmetStack.getItemMeta();
        armorMeta.setColor(Color.GRAY);
        helmetStack.setItemMeta(armorMeta);

        skeleton.getEquipment().setItem(EquipmentSlot.HAND, axeStack);
        skeleton.getEquipment().setItem(EquipmentSlot.HEAD, helmetStack);
    }

    @Override
    public void onAttack(Player player, Arrow arrow) {
        //Doesn't needed
    }

    @EventHandler
    public void onPillagerSpawn(EntitySpawnEvent event){
        if(event.getEntity() instanceof Vindicator){
            start(event.getEntity());
        }
    }
}
