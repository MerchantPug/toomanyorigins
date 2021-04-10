package io.github.merchantpug.toomanyorigins.component;

import dev.onyxstudios.cca.api.v3.component.Component;

public interface ExtraHealthComponent extends Component {
    int getExtraHealth();
    void setExtraHealth(int value);
    boolean getNewPlayer();
    void setNewPlayer(boolean value);
}
