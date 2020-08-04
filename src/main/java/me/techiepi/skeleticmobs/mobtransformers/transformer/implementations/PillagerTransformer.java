package me.techiepi.skeleticmobs.mobtransformers.transformer.implementations;

import com.destroystokyo.paper.entity.RangedEntity;
import me.techiepi.skeleticmobs.mobtransformers.enums.TransformerType;
import me.techiepi.skeleticmobs.mobtransformers.transformer.Transformer;
import org.bukkit.Material;
import org.bukkit.entity.*;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntitySpawnEvent;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;

public class PillagerTransformer extends Transformer implements Listener {
    //TODO: Fix raids

    @Override
    public void start(Entity entity) {
        super.setSkeletonType(TransformerType.PILLAGER);
        super.setName("Pillager Skeleton");
        super.transformEntity(entity);
    }

    @Override
    public void personalizeSkeleton(@NotNull Skeleton skeleton, @NotNull TransformerType type) {
        //TODO: Fix crossbow usage
        ItemStack crossbowStack = new ItemStack(Material.CROSSBOW, 1);
        skeleton.getEquipment().setItem(EquipmentSlot.HAND, crossbowStack);
    }

    @EventHandler
    public void onPillagerSpawn(EntitySpawnEvent event){
        if(event.getEntity() instanceof Pillager){
            start(event.getEntity());
        }
    }

    @Override
    public void onAttack(Player player, Arrow arrow) {
        //Doesn't needed
    }
}
