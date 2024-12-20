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

package dev.terminalmc.flashside.mixin.flashback;

import com.bawnorton.mixinsquared.TargetHandler;
import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import com.llamalad7.mixinextras.sugar.Local;
import com.moulberry.flashback.screen.FlashbackButton;
import dev.terminalmc.flashside.config.Config;
import net.minecraft.client.gui.components.AbstractWidget;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.screens.TitleScreen;
import net.minecraft.network.chat.Component;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(value = TitleScreen.class, priority = 1400)
public class MixinSquaredTitleScreen {
    /**
     * Optionally shift the Flashback button to the left side of the game menu.
     */
    @TargetHandler(
            mixin = "com.moulberry.flashback.mixin.ui.MixinTitleScreen",
            name = "createOpenSelectReplayScreenButton"
    )
    @WrapOperation(
            method = "@MixinSquared:Handler",
            at = @At(
                    value = "NEW",
                    target = "com/moulberry/flashback/screen/FlashbackButton"
            )
    )
    private FlashbackButton wrapInitFlashbackButton(int x, int y, int width, int height,
                                                    Component component, Button.OnPress onPress,
                                                    Operation<FlashbackButton> original, 
                                                    @Local AbstractWidget widget) {
        if (Config.options().leftSide && Config.options().editTitleScreen) {
            x = widget.getX() - width - 4;
        }
        return original.call(x, y, width, height, component, onPress);
    }
}