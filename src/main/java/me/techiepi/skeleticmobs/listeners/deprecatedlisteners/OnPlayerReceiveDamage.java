package me.techiepi.skeleticmobs.listeners.deprecatedlisteners;

import me.techiepi.skeleticmobs.Main;
import me.techiepi.skeleticmobs.mobtransformers.transformer.implementations.HuskTransformer;
import me.techiepi.skeleticmobs.mobtransformers.enums.TransformerType;
import org.bukkit.NamespacedKey;
import org.bukkit.entity.Arrow;
import org.bukkit.entity.Player;
import org.bukkit.entity.Skeleton;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;

import java.util.Objects;

public class OnPlayerReceiveDamage implements Listener {

    @EventHandler
    @Deprecated
    public void onPlayerTakesDamage(EntityDamageByEntityEvent event){
        System.out.println("Something taked damage");
        System.out.println(event.getEntityType());
        System.out.println(event.getDamager().getType());
        if(event.getEntity() instanceof Player){
            if(event.getDamager() instanceof Arrow){
                Arrow arrow = (Arrow) event.getDamager();
                if(arrow.getShooter() instanceof Skeleton){
                    callEvents(event);
                }
            }
        }
    }

    @Deprecated
    private void callEvents(EntityDamageByEntityEvent event){
        System.out.println("Call Events");
        Arrow arrow = (Arrow) event.getDamager();
        Skeleton skeleton = (Skeleton) arrow.getShooter();

        NamespacedKey key = new NamespacedKey(Main.getPluginInstance(), "skeleticmob-type");
        PersistentDataContainer container = Objects.requireNonNull(skeleton).getPersistentDataContainer();

        if(container.has(key, PersistentDataType.STRING)){
            String value = container.get(key, PersistentDataType.STRING);
            System.out.println(value);

            if(value != null){
                TransformerType type = TransformerType.valueOf(value);
                switch(type){
                    case HUSK:
                        System.out.println("Case husk");
                        HuskTransformer huskSkeleton = new HuskTransformer();
                        System.out.println(event.getEntity());
                        huskSkeleton.onAttack((Player) event.getEntity(), (Arrow) event.getDamager());
                        break;
                    case CREEPER:
                        break;
                }
            }
        }


    }
}
