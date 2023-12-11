/*
MIT License

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

package net.merchantpug.toomanyorigins.data;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.mojang.serialization.JsonOps;
import net.merchantpug.toomanyorigins.TooManyOrigins;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.packs.resources.Resource;
import net.minecraft.server.packs.resources.ResourceManager;
import net.minecraft.server.packs.resources.SimplePreparableReloadListener;
import net.minecraft.util.GsonHelper;
import net.minecraft.util.profiling.ProfilerFiller;

import java.io.Reader;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

public class LegacyContentManager extends SimplePreparableReloadListener<Set<LegacyContentRecord>> {
    private static final Gson GSON = (new GsonBuilder()).setPrettyPrinting().disableHtmlEscaping().create();

    @Override
    protected Set<LegacyContentRecord> prepare(ResourceManager manager, ProfilerFiller profiler) {
        Set<LegacyContentRecord> enabledSet = new HashSet<>();
        Iterator<Map.Entry<ResourceLocation, Resource>> iterator = manager.listResources("toomanyorigins", (id) -> id.getPath().equals("toomanyorigins/legacy_content.json")).entrySet().iterator();
        Set<String> resourcesHandled = new HashSet<>();
        while (iterator.hasNext()) {
            var next = iterator.next();
            resourcesHandled.clear();
            manager.getResourceStack(next.getKey()).forEach(resource -> {
                if(!resourcesHandled.contains(resource.sourcePackId())) {
                    resourcesHandled.add(resource.sourcePackId());
                    try {
                        Reader reader = resource.openAsReader();
                        JsonElement jsonElement = GsonHelper.fromJson(GSON, reader, JsonElement.class);
                        var dataResult = LegacyContentRecord.CODEC.decode(JsonOps.INSTANCE, jsonElement);
                        dataResult.error().ifPresent(result -> {
                            TooManyOrigins.LOG.info("Error loading legacy_content json file from pack '{}'. (Skipping). {}", resource.sourcePackId(), result.message());
                        });
                        dataResult.resultOrPartial(TooManyOrigins.LOG::error).ifPresent(pair -> {
                            enabledSet.add(pair.getFirst());
                        });
                    } catch (Throwable e) {
                        TooManyOrigins.LOG.error("There was a problem reading TooManyOrigins legacy content file from pack '{}'. (skipping): {}.", resource.sourcePackId(), e.getMessage());
                    }
                }
            });
        }
        return enabledSet;
    }

    @Override
    protected void apply(Set<LegacyContentRecord> prepared, ResourceManager manager, ProfilerFiller profiler) {
        LegacyContentRegistry.disableAll();
        LegacyContentRegistry.setRecord(
                prepared.stream().anyMatch(LegacyContentRecord::dragonFireballEnabled),
                prepared.stream().anyMatch(LegacyContentRecord::witheredCropsEnabled),
                prepared.stream().anyMatch(LegacyContentRecord::zombifyingEnabled));
        if (LegacyContentRegistry.getContentCount() > 0)
            TooManyOrigins.LOG.info("Successfully enabled {} TooManyOrigins legacy content modules.", LegacyContentRegistry.getContentCount());
    }

}