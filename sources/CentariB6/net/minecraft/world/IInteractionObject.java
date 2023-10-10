package net.minecraft.world;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.world.IWorldNameable;

public interface IInteractionObject extends IWorldNameable {
   String getGuiID();

   Container createContainer(InventoryPlayer var1, EntityPlayer var2);
}
