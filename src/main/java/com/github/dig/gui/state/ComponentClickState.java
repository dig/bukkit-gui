package com.github.dig.gui.state;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.inventory.ItemStack;

@Data
@AllArgsConstructor
public class ComponentClickState implements GuiState {

    private final Player player;

    private final ItemStack cursor;

    private final ItemStack currentItem;

    private final int slot;

    private final InventoryType.SlotType slotType;

    private final ClickType clickType;

}
