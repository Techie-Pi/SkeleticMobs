package me.techiepi.skeleticmobs.listeners.deprecatedlisteners;

import me.techiepi.skeleticmobs.Main;
import me.techiepi.skeleticmobs.mobtransformers.transformer.implementations.CreeperTransformer;
import me.techiepi.skeleticmobs.mobtransformers.transformer.implementations.HuskTransformer;
import me.techiepi.skeleticmobs.mobtransformers.enums.TransformerType;
import org.bukkit.NamespacedKey;
import org.bukkit.entity.*;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntitySpawnEvent;
import org.bukkit.event.entity.ProjectileHitEvent;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;
import org.jetbrains.annotations.Nullable;

import java.util.Objects;

public class MainListener implements Listener {

    @EventHandler
    public void onEntitySpawn(EntitySpawnEvent event){
        transformEntityIfNeeded(event.getEntity());
    }

    @EventHandler
    public void onPlayerReceiveDamage(EntityDamageByEntityEvent event){
        if(event.getDamager() instanceof Arrow){
            if(event.getEntity() instanceof Player){
                Arrow arrow = (Arrow) event.getDamager();
                if(arrow.getShooter() instanceof Skeleton){
                    if(arrow.getShooter() != null){
                        if(event.getEntity() instanceof Player){
                            CallEvent((Player) event.getEntity(), (Skeleton) arrow.getShooter(), arrow);
                        }
                    }
                }
            }
        }
    }

    @EventHandler
    public void ProjectileHit(ProjectileHitEvent event){
        if(event.getEntity().getShooter() instanceof Skeleton){
            if(event.getHitEntity() instanceof Player){
                CallEvent((Player) event.getHitEntity(), (Skeleton) event.getEntity().getShooter(), (Arrow) event.getEntity());
            }
            else{
                CallEvent(null, (Skeleton) event.getEntity().getShooter(), (Arrow) event.getEntity());
            }

            event.getEntity().remove();
        }
    }

    private void transformEntityIfNeeded(Entity entity) {
        switch(entity.getType()){
            case HUSK:
                HuskTransformer huskTransformer = new HuskTransformer();
                huskTransformer.start(entity);
                break;
            case CREEPER:
                CreeperTransformer creeperTransformer = new CreeperTransformer();
                creeperTransformer.start(entity);
                break;
            default:
                break;
        }
    }


    private void CallEvent(@Nullable Player player, @Nullable Skeleton skeleton, @Nullable Arrow arrow) {
        TransformerType typeSkeleton = TypeOfCustomSkeleton(skeleton);
        if(typeSkeleton != null){
            switch(typeSkeleton){
                case HUSK:
                    HuskTransformer huskTransformer = new HuskTransformer();
                    if (arrow != null && player != null) {
                        huskTransformer.onAttack(player, arrow);
                    }
                    else {
                        System.out.println(arrow);
                        System.out.println(player);
                    }
                    break;

                case CREEPER:
                    CreeperTransformer creeperTransformer = new CreeperTransformer();
                    if(arrow != null) {
                        creeperTransformer.onAttack(null, arrow);
                    }
                    else {
                        throw new NullPointerException();
                    }
                    break;
            }
        }
        else{
            System.out.println("Not a custom skeleton");
        }
    }

    private TransformerType TypeOfCustomSkeleton(Skeleton skeleton){
        NamespacedKey key = new NamespacedKey(Main.getPluginInstance(), "skeleticmob-type");
        PersistentDataContainer container = Objects.requireNonNull(skeleton).getPersistentDataContainer();

        if(container.has(key, PersistentDataType.STRING)) {
            String value = container.get(key, PersistentDataType.STRING);
            if(value != null){
                return TransformerType.valueOf(value);
            }
            else{
                return null;
            }
        }
        else{
            return null;
        }
    }

}
