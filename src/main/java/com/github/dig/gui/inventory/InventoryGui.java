package com.github.dig.gui.inventory;

import com.github.dig.gui.Gui;
import com.github.dig.gui.GuiRegistry;
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
    protected final Set<Integer> controllableSlots = new HashSet<>();
    private final Set<Player> viewers = new HashSet<>();

    public InventoryGui(@NonNull Inventory inventory) {
        this.inventory = inventory;
    }

    @Override
    public void handleOpen(Player player) {
        render();
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
    public int getViewerCount() {
        return viewers.size();
    }

    @Override
    public Listener createListener(GuiRegistry guiRegistry) {
        return new InventoryGuiListener(this, guiRegistry);
    }

    public boolean isControllable(int slot) {
        return controllableSlots.contains(slot);
    }

    public boolean match(@NonNull Inventory inventory) {
        return this.inventory == inventory;
    }

    public void render() {}

    public void handleItemChange(GuiItemChangeState state) {}

    public void handleComponentClick(ComponentClickState state) {}

    public void handleDrag(GuiDragState state) {}

    protected abstract int slotOf(int x, int y);
}
