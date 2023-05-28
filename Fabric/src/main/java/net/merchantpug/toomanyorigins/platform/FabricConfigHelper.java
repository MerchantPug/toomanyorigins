package net.merchantpug.toomanyorigins.platform;

import com.google.auto.service.AutoService;
import net.merchantpug.toomanyorigins.platform.services.IConfigHelper;
import net.merchantpug.toomanyorigins.util.TooManyOriginsConfig;

@AutoService(IConfigHelper.class)
public class FabricConfigHelper implements IConfigHelper {

    @Override
    public boolean shouldDragonFireballDamageUndead() {
        return TooManyOriginsConfig.shouldFireballDamageUndead;
    }

}