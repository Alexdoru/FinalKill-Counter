package dev.jeinton.mwutils;

import dev.jeinton.mwutils.event.MwGameIdChangeEvent;
import dev.jeinton.mwutils.util.MinecraftUtils;
import net.minecraft.client.Minecraft;
import net.minecraft.scoreboard.Scoreboard;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent.ClientTickEvent;

public class MwScoreboardParser {
    private static MwScoreboardParser instance;

    private MwScoreboardData mwScoreboardData = new MwScoreboardData(null);

    private String prevGameId = null;

    private MwScoreboardParser() {}

    public static MwScoreboardParser instance() {
        if (instance == null) {
            instance = new MwScoreboardParser();
        }

        return instance;
    }

    // TODO: Use a different event?
    @SubscribeEvent
    public void onTick(ClientTickEvent event) {
        if (event.phase == ClientTickEvent.Phase.START) {
            return;
        }

        mwScoreboardData = new MwScoreboardData(null);
        Minecraft mc = Minecraft.getMinecraft();

        if (mc.theWorld == null || !MinecraftUtils.isHypixel()) {
            return;
        }

        Scoreboard scoreboard = mc.theWorld.getScoreboard();
        if (scoreboard == null) {
            return;
        }

        mwScoreboardData = new MwScoreboardData(scoreboard);

        String gameId = mwScoreboardData.getGameId();

        if (gameId == null) {
            if (prevGameId != null) {
                MinecraftForge.EVENT_BUS.post(new MwGameIdChangeEvent(MwGameIdChangeEvent.EventType.DISCONNECT));
            }

        } else if (!gameId.equals(prevGameId)) {
            MinecraftForge.EVENT_BUS.post(new MwGameIdChangeEvent(MwGameIdChangeEvent.EventType.CONNECT));
        }

        prevGameId = gameId;
    }

    public MwScoreboardData getMwScoreboardData() {
        return mwScoreboardData;
    }
}
