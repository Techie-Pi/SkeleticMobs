package me.techiepi.skeleticmobs.mobtransformers.transformer;


import me.techiepi.skeleticmobs.Main;
import org.apache.commons.lang.NotImplementedException;
import org.bukkit.NamespacedKey;
import org.bukkit.entity.*;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;
import org.jetbrains.annotations.NotNull;
import me.techiepi.skeleticmobs.mobtransformers.enums.TransformerType;
import org.bukkit.Location;
import org.bukkit.World;

import java.util.Objects;

public abstract class Transformer {
    //TODO: Fix when baby (very complex) & fix when is passenger
    //TODO: Loot Tables

    private TransformerType skeletonType;
    private String name;


    public abstract void start(Entity entity);

    public void transformEntity(@NotNull Entity entity){
        Location entityLocation = entity.getLocation();
        World entityWorld = entity.getWorld();

        entity.remove();
        Skeleton skeleton = summonBaseSkeleton(entityLocation, entityWorld);

        setIdentifier(skeleton, skeletonType);
        setDisplayName(name, skeleton);
        personalizeSkeleton(skeleton, skeletonType);
        setIdentifier(skeleton, skeletonType);
    }


    public abstract void personalizeSkeleton(@NotNull Skeleton skeleton, @NotNull TransformerType type);

    private Skeleton summonBaseSkeleton(@NotNull Location summonLocation, @NotNull World summonWorld){
        return (Skeleton) summonWorld.spawnEntity(summonLocation, EntityType.SKELETON);
    }

    private void setIdentifier(@NotNull Skeleton skeleton, @NotNull TransformerType type){
        NamespacedKey key = new NamespacedKey(Main.getPluginInstance(), "skeleticmob-type");
        skeleton.getPersistentDataContainer().set(key, PersistentDataType.STRING, type.name());
    }

    private void setDisplayName(@NotNull String name, @NotNull Skeleton skeleton) {
        skeleton.setCustomName(name);
        skeleton.setCustomNameVisible(true);
    }

    public void setSkeletonType(@NotNull TransformerType skeletonType){
        this.skeletonType = skeletonType;
    }

    public TransformerType getSkeletonType(){
        return skeletonType;
    }

    public TransformerType TypeOfCustomSkeleton(Skeleton skeleton){
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

    public void setName(@NotNull String name){
        this.name = name;
    }

    public abstract void onAttack(Player player, Arrow arrow);

}
