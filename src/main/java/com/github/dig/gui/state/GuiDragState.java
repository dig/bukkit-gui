package com.github.dig.gui.state;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

@Data
@AllArgsConstructor
public class GuiDragState implements GuiState {

    private final Player player;

    private final ItemStack cursor;
}
