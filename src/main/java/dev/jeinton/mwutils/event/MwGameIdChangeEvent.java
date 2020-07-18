package dev.jeinton.mwutils.event;

import net.minecraftforge.fml.common.eventhandler.Event;

public class MwGameIdChangeEvent extends Event {
    public enum EventType {
        CONNECT,
        DISCONNECT
    }

    public EventType type;

    public MwGameIdChangeEvent(EventType type) {
        this.type = type;
    }
}
