package me.techiepi.skeleticmobs.mobtransformers.transformer.implementations;

import me.techiepi.skeleticmobs.mobtransformers.enums.TransformerType;
import me.techiepi.skeleticmobs.mobtransformers.transformer.Transformer;
import org.bukkit.Color;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.*;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntitySpawnEvent;
import org.bukkit.event.entity.ProjectileHitEvent;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.LeatherArmorMeta;
import org.jetbrains.annotations.NotNull;

public class CreeperTransformer extends Transformer implements Listener {

    @Override
    public void start(Entity entity) {
        super.setSkeletonType(TransformerType.CREEPER);
        super.setName("Creeper Skeleton");
        super.transformEntity(entity);
    }

    @Override
    public void onAttack(Player player, Arrow arrow) {
        Location arrowPos = arrow.getWorld().getBlockAt(arrow.getLocation()).getLocation();
        TNTPrimed tnt = (TNTPrimed) arrowPos.getWorld().spawnEntity(arrowPos, EntityType.PRIMED_TNT);
        tnt.setFuseTicks(20);
        arrow.remove();
    }

    @Override
    public void personalizeSkeleton(@NotNull Skeleton skeleton, @NotNull TransformerType type) {
        ItemStack[] armor = createArmor();
        skeleton.getEquipment().setItem(EquipmentSlot.CHEST, armor[0]);
        skeleton.getEquipment().setItem(EquipmentSlot.HEAD, armor[1]);
    }

    @EventHandler
    public void onArrowCollision(ProjectileHitEvent event){
        if(event.getEntity().getShooter() instanceof Skeleton){
            if(super.TypeOfCustomSkeleton((Skeleton) event.getEntity().getShooter()) == super.getSkeletonType()){
                onAttack(null, (Arrow) event.getEntity());
            }
            else{
                System.out.println("Not a custom Skeleton");
            }
        }
    }

    @EventHandler
    public void onCreeperSpawn(EntitySpawnEvent event){
        if(event.getEntity() instanceof Creeper){
            start(event.getEntity());
        }
    }

    private ItemStack[] createArmor(){
        ItemStack[] armor = new ItemStack[2];

        ItemStack leatherChestplate = new ItemStack((Material.LEATHER_CHESTPLATE));
        LeatherArmorMeta leatherChestplateMeta = (LeatherArmorMeta) leatherChestplate.getItemMeta();
        leatherChestplateMeta.setColor(Color.GREEN);
        leatherChestplate.setItemMeta(leatherChestplateMeta);

        ItemStack leatherHelmet = new ItemStack(Material.LEATHER_HELMET);
        LeatherArmorMeta leatherHelmetMeta = (LeatherArmorMeta) leatherHelmet.getItemMeta();
        leatherHelmetMeta.setColor(Color.GREEN);
        leatherHelmet.setItemMeta(leatherHelmetMeta);


        armor[0] = leatherChestplate;
        armor[1] = leatherHelmet;
        return armor;
    }


}
