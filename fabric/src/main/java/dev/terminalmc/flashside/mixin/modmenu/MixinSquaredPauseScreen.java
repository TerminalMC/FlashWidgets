/*
 * Copyright 2024 TerminalMC
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package dev.terminalmc.flashside.mixin.modmenu;

import com.bawnorton.mixinsquared.TargetHandler;
import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import com.terraformersmc.modmenu.gui.widget.UpdateCheckerTexturedButtonWidget;
import dev.terminalmc.flashside.Flashside;
import dev.terminalmc.flashside.config.Config;
import net.minecraft.client.gui.layouts.LayoutElement;
import net.minecraft.client.gui.screens.PauseScreen;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

import java.util.List;

@Mixin(value = PauseScreen.class, priority = 1100)
public class MixinSquaredPauseScreen {

    /**
     * Intercept the ModMenu icon-type button.
     */
    @TargetHandler(
            mixin = "com.terraformersmc.modmenu.mixin.MixinGameMenu",
            name = "onInitWidgets"
    )
    @WrapOperation(
            method = "@MixinSquared:Handler",
            at = @At(
                    value = "INVOKE",
                    target = "Ljava/util/List;add(ILjava/lang/Object;)V"
            )
    )
    private void wrapAdd(List<LayoutElement> instance, int index, Object element, Operation<Void> original) {
        if (
                !Config.options().leftSide 
                && element instanceof UpdateCheckerTexturedButtonWidget button
        ) {
            Flashside.mmButton = button;
        }
        else original.call(instance, index, element);
    }
}
