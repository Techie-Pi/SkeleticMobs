package me.techiepi.skeleticmobs.mobtransformers.transformer.implementations;

import me.techiepi.skeleticmobs.mobtransformers.enums.TransformerType;
import me.techiepi.skeleticmobs.mobtransformers.transformer.Transformer;
import org.bukkit.Color;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.entity.*;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntitySpawnEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.LeatherArmorMeta;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.jetbrains.annotations.NotNull;

import java.util.Random;

public class HuskTransformer extends Transformer implements Listener {

    public void start(Entity entity){
        super.setSkeletonType(TransformerType.HUSK);
        super.setName("Husk Skeleton");
        super.transformEntity(entity);
    }

    @Override
    public void personalizeSkeleton(@NotNull Skeleton skeleton, @NotNull TransformerType type) {
        ItemStack armor = createArmor();
        skeleton.getEquipment().setChestplate(armor);

    }

    @Override
    public void onAttack(Player player, Arrow arrow){
        player.addPotionEffect(new PotionEffect(PotionEffectType.HUNGER, getDurationEffect(player), 0));
    }

    @EventHandler
    public void onHuskSpawn(EntitySpawnEvent event){
        if(event.getEntity() instanceof Husk){
            start(event.getEntity());
        }
    }

    @EventHandler
    public void onHuskAttackPlayer(EntityDamageByEntityEvent event){
        if(event.getEntity() instanceof Player){
            if(event.getDamager() instanceof Arrow){
                Arrow arrow = (Arrow) event.getDamager();
                if(arrow.getShooter() instanceof Skeleton){
                    if(super.TypeOfCustomSkeleton((Skeleton) arrow.getShooter()) == TransformerType.HUSK){
                        onAttack((Player) event.getEntity(), null);
                    }
                    else{
                        System.out.println("Not a custom Skeleton");
                    }
                }
            }
        }
    }


    @NotNull
    private ItemStack createArmor(){
        ItemStack armor;

        ItemStack leatherChestplate = new ItemStack((Material.LEATHER_CHESTPLATE));
        LeatherArmorMeta leatherChestplateMeta = (LeatherArmorMeta) leatherChestplate.getItemMeta();
        leatherChestplateMeta.setColor(Color.GREEN);
        leatherChestplate.setItemMeta(leatherChestplateMeta);


        armor = leatherChestplate;
        return armor;
    }

    private static int getDurationEffect(@NotNull Player player){
        double regionalDifficulty = calculateRegionalDifficulty(player);
        return 7 * ((int) regionalDifficulty * 20);
    }

    private static double calculateRegionalDifficulty(@NotNull Player player){
        World world = player.getWorld();
        //TODO: Calculate regional difficulty as the game does

        Random random = new Random();
        double minVal = 0;
        double maxVal = 0;
        switch(world.getDifficulty()){
            case PEACEFUL:
                minVal = 0;
                maxVal = 0;
                break;
            case EASY:
                minVal = 0.75;
                maxVal = 1.5;
                break;
            case NORMAL:
                minVal = 1.5;
                maxVal = 4;
                break;
            case HARD:
                minVal = 2.25;
                maxVal = 6.75;
                break;
        }
        return minVal + (maxVal - minVal) * random.nextDouble();
    }
}
