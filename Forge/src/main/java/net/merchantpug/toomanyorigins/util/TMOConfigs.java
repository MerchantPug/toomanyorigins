package net.merchantpug.toomanyorigins.util;

import net.minecraftforge.common.ForgeConfigSpec;
import org.apache.commons.lang3.tuple.Pair;

// TODO: Introduce an in-house config library.
public class TMOConfigs {

    public static class Server {

        private final ForgeConfigSpec.BooleanValue shouldFireballDamageUndead;

        public Server(ForgeConfigSpec.Builder builder) {
            this.shouldFireballDamageUndead = builder
                    .translation("toomanyorigins.config.shouldFireballDamageUndead")
                    .define("shouldFireballDamageUndead", true);
        }

        public boolean shouldFireballDamageUndead() {
            return shouldFireballDamageUndead.get();
        }

    }

    public static final ForgeConfigSpec SERVER_SPECS;

    public static final Server SERVER;

    static {
        Pair<Server, ForgeConfigSpec> server = new ForgeConfigSpec.Builder().configure(Server::new);
        SERVER_SPECS = server.getRight();
        SERVER = server.getLeft();
    }

}
