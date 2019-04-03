/*
 * This file is part of Sponge, licensed under the MIT License (MIT).
 *
 * Copyright (c) SpongePowered <https://www.spongepowered.org>
 * Copyright (c) contributors
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 */
package org.spongepowered.mod.mixin.core.forge.common.util;

import net.minecraft.entity.Entity;
import net.minecraft.world.Teleporter;
import net.minecraft.world.World;
import net.minecraftforge.common.util.ITeleporter;
import org.spongepowered.api.world.Location;
import org.spongepowered.api.world.PortalAgent;
import org.spongepowered.api.world.PortalAgentType;
import org.spongepowered.asm.mixin.Implements;
import org.spongepowered.asm.mixin.Interface;
import org.spongepowered.asm.mixin.Intrinsic;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.common.interfaces.world.IMixinITeleporter;
import org.spongepowered.common.registry.type.world.PortalAgentRegistryModule;

@Mixin(value = ITeleporter.class, remap = false)
@Implements(@Interface(iface = IMixinITeleporter.class, prefix = "sponge$"))
public interface MixinITeleporter extends PortalAgent {

    @Shadow void placeEntity(World world, Entity entity, float yaw);

    @Intrinsic
    default void sponge$placeEntity(World world, Entity entity, float yaw) {
        this.placeEntity(world, entity, yaw);
    }

    @Intrinsic
    default boolean sponge$isVanilla()
    {
        return this.getClass().equals(Teleporter.class);
    }

    @Override
    default int getSearchRadius() {
        return 0;
    }

    @Override
    default PortalAgent setSearchRadius(final int radius) {
        return this;
    }

    @Override
    default int getCreationRadius() {
        return 0;
    }

    @Override
    default PortalAgent setCreationRadius(final int radius) {
        return this;
    }

    @Override
    default PortalAgentType getType() {
        return PortalAgentRegistryModule.getInstance().validatePortalAgent((IMixinITeleporter) this);
    }
}