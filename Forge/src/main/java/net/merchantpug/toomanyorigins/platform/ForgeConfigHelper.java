package net.merchantpug.toomanyorigins.platform;


import com.google.auto.service.AutoService;
import net.merchantpug.toomanyorigins.platform.services.IConfigHelper;
import net.merchantpug.toomanyorigins.util.TMOConfigs;

@AutoService(IConfigHelper.class)
public class ForgeConfigHelper implements IConfigHelper {

    @Override
    public boolean shouldDragonFireballDamageUndead() {
        return TMOConfigs.SERVER.shouldFireballDamageUndead();
    }

}