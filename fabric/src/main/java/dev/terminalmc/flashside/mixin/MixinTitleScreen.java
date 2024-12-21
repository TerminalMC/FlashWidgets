package dev.terminalmc.flashside.mixin;

import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import com.llamalad7.mixinextras.sugar.Local;
import dev.terminalmc.flashside.Flashside;
import dev.terminalmc.flashside.config.Config;
import net.minecraft.client.gui.components.AbstractWidget;
import net.minecraft.client.gui.components.events.GuiEventListener;
import net.minecraft.client.gui.screens.TitleScreen;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(TitleScreen.class)
public class MixinTitleScreen {
    @Inject(
            method = "createNormalMenuOptions",
            at = @At("HEAD")
    )
    private void onCreateNormalMenuOptions(int y, int rowHeight, CallbackInfo ci) {
        Flashside.fbTitleScreenX = -1;
        Flashside.fbTitleScreenY = -1;
        Flashside.mmTitleScreenY = -1;
        Flashside.mmTitleScreenIcon = false;
    }

    /**
     * Calculate the positions for the Flashback and ModMenu buttons.
     */
    @WrapOperation(
            method = "createNormalMenuOptions",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/client/gui/screens/TitleScreen;addRenderableWidget(Lnet/minecraft/client/gui/components/events/GuiEventListener;)Lnet/minecraft/client/gui/components/events/GuiEventListener;",
                    ordinal = 0
            )
    )
    private GuiEventListener wrapAddRenderableWidget(TitleScreen instance, 
                                                     GuiEventListener guiEventListener, 
                                                     Operation<GuiEventListener> original, 
                                                     @Local(ordinal = 1, argsOnly = true) int rowHeight) {
        if (guiEventListener instanceof AbstractWidget widget && Config.options().editTitleScreen) {
            // Flashback button position
            if (Config.options().leftSide) Flashside.fbTitleScreenX = widget.getX() - widget.getHeight() - 4;
            Flashside.fbTitleScreenY = widget.getY() + rowHeight * Config.options().startRowTitleScreen;
            // ModMenu button position
            if (!Config.options().leftSide) Flashside.mmTitleScreenY = Flashside.fbTitleScreenY + rowHeight;
        }
        return original.call(instance, guiEventListener);
    }
}
