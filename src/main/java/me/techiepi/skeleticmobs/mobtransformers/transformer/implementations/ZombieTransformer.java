package me.techiepi.skeleticmobs.mobtransformers.transformer.implementations;

import me.techiepi.skeleticmobs.mobtransformers.enums.TransformerType;
import me.techiepi.skeleticmobs.mobtransformers.transformer.Transformer;
import org.bukkit.Color;
import org.bukkit.Material;
import org.bukkit.OfflinePlayer;
import org.bukkit.SkullType;
import org.bukkit.block.Skull;
import org.bukkit.entity.*;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntitySpawnEvent;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.LeatherArmorMeta;
import org.bukkit.inventory.meta.SkullMeta;
import org.jetbrains.annotations.NotNull;

public class ZombieTransformer extends Transformer implements Listener {

    @Override
    public void start(Entity entity) {
        super.setSkeletonType(TransformerType.ZOMBIE);
        super.setName("Zombie Skeleton");
        super.transformEntity(entity);
    }

    @Override
    public void personalizeSkeleton(@NotNull Skeleton skeleton, @NotNull TransformerType type) {
        skeleton.getEquipment().setItem(EquipmentSlot.HAND, new ItemStack(Material.BONE, 1));
        skeleton.getEquipment().setChestplate(createArmor());

    }

    @EventHandler
    public void onZombieSpawn(EntitySpawnEvent event){
        if(event.getEntity() instanceof Zombie){
            start(event.getEntity());
        }
    }

    private ItemStack createArmor() {
        ItemStack greenChestplate = new ItemStack(Material.LEATHER_CHESTPLATE, 1);

        LeatherArmorMeta leatherArmorMeta = (LeatherArmorMeta) greenChestplate.getItemMeta();
        leatherArmorMeta.setColor(Color.GREEN);

        greenChestplate.setItemMeta(leatherArmorMeta);
        return greenChestplate;
    }

    @Override
    public void onAttack(Player player, Arrow arrow) {
        //Nothing
    }
}
