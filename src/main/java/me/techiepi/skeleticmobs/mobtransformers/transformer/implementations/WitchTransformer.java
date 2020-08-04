package me.techiepi.skeleticmobs.mobtransformers.transformer.implementations;

import me.techiepi.skeleticmobs.mobtransformers.enums.TransformerType;
import me.techiepi.skeleticmobs.mobtransformers.transformer.Transformer;
import org.bukkit.Color;
import org.bukkit.Material;
import org.bukkit.entity.*;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityShootBowEvent;
import org.bukkit.event.entity.EntitySpawnEvent;
import org.bukkit.event.entity.ProjectileHitEvent;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.LeatherArmorMeta;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.jetbrains.annotations.NotNull;

import java.util.Objects;
import java.util.Random;

public class WitchTransformer extends Transformer implements Listener {
    //TODO: Fix raids

    @Override
    public void start(Entity entity) {
        super.setSkeletonType(TransformerType.WITCH);
        super.setName("Witch Skeleton");
        super.transformEntity(entity);
    }

    @Override
    public void personalizeSkeleton(@NotNull Skeleton skeleton, @NotNull TransformerType type) {
        ItemStack helmetStack = new ItemStack(Material.LEATHER_HELMET, 1);

        LeatherArmorMeta armorMeta = (LeatherArmorMeta) helmetStack.getItemMeta();
        armorMeta.setColor(Color.BLACK);
        helmetStack.setItemMeta(armorMeta);

        skeleton.getEquipment().setItem(EquipmentSlot.HEAD, helmetStack);
    }

    @Override
    public void onAttack(Player player, Arrow arrow) {
        arrow.addCustomEffect(Objects.requireNonNull(createRanPotionEffect()), true);
    }

    @EventHandler
    public void onWitchSpawn(EntitySpawnEvent event){
        if(event.getEntity() instanceof Witch){
            start(event.getEntity());
        }
    }

    @EventHandler
    public void onSkeletonShoot(EntityShootBowEvent event){
        Projectile projectile = (Projectile) event.getProjectile();
        if(projectile.getShooter() instanceof Skeleton){
            if(super.TypeOfCustomSkeleton((Skeleton) projectile.getShooter()) == super.getSkeletonType()){
                onAttack(null, (Arrow) projectile);
            }
            else{
                System.out.println("Not a custom Skeleton");
            }
        }
    }

    private PotionEffect createRanPotionEffect() {
        //TODO: Implement potions as Minecraft does
        // - Implement defense potions
        Random rand = new Random();
        int randomNum = rand.nextInt(3);
        switch(randomNum){
            case 0:
                return new PotionEffect(PotionEffectType.SLOW, 1800, 1);
            case 1:
                return new PotionEffect(PotionEffectType.POISON, 880, 1);
            case 2:
                return new PotionEffect(PotionEffectType.WEAKNESS, 1800, 1);
            case 3:
                return new PotionEffect(PotionEffectType.HARM, 1, 1);
            default:
                return null;
        }
    }
}
