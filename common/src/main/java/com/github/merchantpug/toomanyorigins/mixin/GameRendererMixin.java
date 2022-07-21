/* MIT License

Copyright (c) 2021 apace100

Permission is hereby granted, free of charge, to any person obtaining a copy
of this software and associated documentation files (the "Software"), to deal
in the Software without restriction, including without limitation the rights
to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
copies of the Software, and to permit persons to whom the Software is
furnished to do so, subject to the following conditions:

The above copyright notice and this permission notice shall be included in all
copies or substantial portions of the Software.

THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
SOFTWARE.
*/

package com.github.merchantpug.toomanyorigins.mixin;

import io.github.apace100.origins.component.OriginComponent;
import com.github.merchantpug.toomanyorigins.power.PreventShaderTogglePower;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.render.GameRenderer;
import net.minecraft.entity.Entity;
import net.minecraft.resource.ResourceManager;
import net.minecraft.util.Identifier;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Environment(EnvType.CLIENT)
@Mixin(GameRenderer.class)
public abstract class GameRendererMixin {
    @Shadow @Final private MinecraftClient client;

    @Shadow @Final private ResourceManager resourceContainer;

    @Unique
    private Identifier apugli$currentlyLoadedShader;

    @Inject(at = @At("TAIL"), method = "onCameraEntitySet")
    private void setCurrentlyLoadedShader(Entity entity, CallbackInfo ci) {
        OriginComponent.withPower(client.getCameraEntity(), PreventShaderTogglePower.class, null, shaderPower -> {
            Identifier shaderLoc = shaderPower.getShaderLocation();
            if(this.resourceContainer.containsResource(shaderLoc)) {
                apugli$currentlyLoadedShader = shaderLoc;
            }
        });
    }

    @Inject(at = @At("HEAD"), method = "render")
    private void cacheCurrentlyLoadedShader(float tickDelta, long startTime, boolean tick, CallbackInfo ci) {
        OriginComponent.withPower(client.getCameraEntity(), PreventShaderTogglePower.class, null, shaderPower -> {
            Identifier shaderLoc = shaderPower.getShaderLocation();
            if(apugli$currentlyLoadedShader != shaderLoc) {
                if(this.resourceContainer.containsResource(shaderLoc)) {
                    apugli$currentlyLoadedShader = shaderLoc;
                }
            }
        });
        if(!OriginComponent.hasPower(client.getCameraEntity(), PreventShaderTogglePower.class) && apugli$currentlyLoadedShader != null) {
            apugli$currentlyLoadedShader = null;
        }
    }

    @Inject(at = @At("HEAD"), method = "toggleShadersEnabled", cancellable = true)
    private void disableShaderToggle(CallbackInfo ci) {
        OriginComponent.withPower(client.getCameraEntity(), PreventShaderTogglePower.class, null, shaderPower -> {
            Identifier shaderLoc = shaderPower.getShaderLocation();
            if(apugli$currentlyLoadedShader == shaderLoc) {
                ci.cancel();
            }
        });
    }
}
