package com.github.dig.gui.inventory;

import com.github.dig.gui.Gui;
import com.github.dig.gui.state.ComponentClickState;
import com.github.dig.gui.state.GuiDragState;
import com.github.dig.gui.state.GuiItemChangeState;
import lombok.NonNull;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.bukkit.inventory.Inventory;

import java.util.HashSet;
import java.util.Set;

public abstract class InventoryGui implements Gui {

    protected final Inventory inventory;
    protected final Set<Integer> controllableSlots;
    private final Set<Player> viewers;

    public InventoryGui(@NonNull Inventory inventory) {
        this.inventory = inventory;
        this.controllableSlots = new HashSet<>();
        this.viewers = new HashSet<>();
    }

    @Override
    public void showTo(Player player) {
        if (!viewers.contains(player)) {
            player.openInventory(inventory);
            viewers.add(player);
        }
    }

    @Override
    public void removeViewer(Player player) {
        viewers.remove(player);
    }

    @Override
    public boolean isViewer(Player player) {
        return viewers.contains(player);
    }

    @Override
    public Listener createListener() {
        return new InventoryGuiListener(this);
    }

    public boolean isControllable(int slot) {
        return controllableSlots.contains(slot);
    }

    public boolean match(@NonNull Inventory inventory) {
        return this.inventory == inventory;
    }

    public abstract void handleItemChange(GuiItemChangeState state);

    public abstract void handleComponentClick(ComponentClickState state);

    public abstract void handleDrag(GuiDragState state);

    protected abstract int slotOf(int x, int y);

}
