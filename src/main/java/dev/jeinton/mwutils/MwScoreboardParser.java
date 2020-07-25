package dev.jeinton.mwutils;

import dev.jeinton.mwutils.event.MwGameIdChangeEvent;
import dev.jeinton.mwutils.util.MinecraftUtils;
import net.minecraft.client.Minecraft;
import net.minecraft.scoreboard.Scoreboard;
import net.minecraftforge.client.event.RenderGameOverlayEvent;
import net.minecraftforge.client.event.RenderGameOverlayEvent.ElementType;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

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

    @SubscribeEvent
    public void onRenderGameOverlay(RenderGameOverlayEvent event) {
        if (event.type != ElementType.TEXT) {
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
