package com.github.dig.gui.inventory;

import com.github.dig.gui.GuiRegistry;
import com.github.dig.gui.inventory.action.*;
import com.github.dig.gui.state.ComponentClickState;
import com.github.dig.gui.state.GuiDragState;
import com.google.common.collect.ImmutableSet;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.event.inventory.InventoryDragEvent;

import java.util.Map;
import java.util.Set;
import java.util.function.Consumer;

public class InventoryGuiListener implements Listener {

    private final static Set<InventoryActionHandler> ACTION_HANDLERS = ImmutableSet.of(
            new CollectToCursorHandler(),
            new HotbarSwapHandler(),
            new MoveToOtherInventoryHandler(),
            new StandardClickHandler()
    );

    private final Map<Integer, Consumer<ComponentClickState>> callbacks;
    private final InventoryGui inventoryGui;
    private final GuiRegistry guiRegistry;

    public InventoryGuiListener(Map<Integer, Consumer<ComponentClickState>> callbacks,
                                InventoryGui inventoryGui,
                                GuiRegistry guiRegistry) {
        this.callbacks = callbacks;
        this.inventoryGui = inventoryGui;
        this.guiRegistry = guiRegistry;
    }

    @EventHandler
    public void onClickInventory(InventoryClickEvent event) {
        if (event.getWhoClicked() instanceof Player && inventoryGui.match(event.getInventory())) {
            Player player = (Player) event.getWhoClicked();

            // Action handlers
            for (InventoryActionHandler actionHandler : ACTION_HANDLERS) {
                if (actionHandler.getActions().contains(event.getAction())) {
                    actionHandler.handle(event, inventoryGui);
                }
            }

            // Handle slot callbacks
            int slot = event.getRawSlot();

            if (callbacks.containsKey(slot)) {
                ComponentClickState clickState = new ComponentClickState(
                        (Player) event.getWhoClicked(),
                        event.getCursor(), event.getCurrentItem(),
                        event.getRawSlot(), event.getSlotType(), event.getClick());

                callbacks.get(slot).accept(clickState);
            }
        }
    }

    @EventHandler
    public void onDragInventory(InventoryDragEvent event) {
        if (event.getWhoClicked() instanceof Player && inventoryGui.match(event.getInventory())) {
            Player player = (Player) event.getWhoClicked();
            boolean cancel = false;

            for (int rawSlot : event.getRawSlots()) {
                if (rawSlot < event.getView().getTopInventory().getSize()
                        && !inventoryGui.isControllable(rawSlot)) {
                    cancel = true;
                    break;
                }
            }

            if (cancel) {
                event.setCancelled(true);
            } else {
                inventoryGui.handleDrag(new GuiDragState(player, event.getCursor()));
            }
        }
    }

    @EventHandler
    public void onCloseInventory(InventoryCloseEvent event) {
        if (event.getPlayer() instanceof Player && inventoryGui.match(event.getInventory())) {
            Player player = (Player) event.getPlayer();

            inventoryGui.handleClose(player);
            inventoryGui.removeViewer(player);

            if (inventoryGui.getViewerCount() <= 0 && guiRegistry.isRegistered(inventoryGui)) {
                guiRegistry.unregister(inventoryGui);
            }
        }
    }
}
