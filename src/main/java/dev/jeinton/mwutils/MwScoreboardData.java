package dev.jeinton.mwutils;

import dev.jeinton.mwutils.util.MinecraftUtils;
import dev.jeinton.mwutils.util.ScoreboardUtils;
import net.minecraft.client.Minecraft;
import net.minecraft.scoreboard.Scoreboard;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class MwScoreboardData {
    private static final Pattern GAME_ID_PATTERN = Pattern.compile("\\s*\\d+/\\d+/\\d+\\s+([\\d\\w]+)\\s*", Pattern.CASE_INSENSITIVE);
    private static final Pattern MW_TITLE_PATTERN = Pattern.compile("\\s*MEGA\\sWALLS\\s*", Pattern.CASE_INSENSITIVE);
    private static final Pattern PREGAME_LOBBY_PATTERN = Pattern.compile(".+[0-9]+/[0-9]+\\s*");
    private static final Pattern WITHER_ALIVE_PATTERN = Pattern.compile("\\s*\\[.\\].*(?:HP|\u2764|\u2665).*", Pattern.CASE_INSENSITIVE);

    private ArrayList<String> aliveWithers = new ArrayList<>();

    private String gameId = null;

    public MwScoreboardData(Scoreboard scoreboard) {
        if (scoreboard == null) {
            return;
        }

        String title = ScoreboardUtils.getUnformattedSidebarTitle(scoreboard);
        if (!MW_TITLE_PATTERN.matcher(title).matches()) {
            return;
        }

        List<String> scoresColor = ScoreboardUtils.getSidebarText(scoreboard);
        List<String> scoresRaw = ScoreboardUtils.getUnformattedSidebarText(scoresColor);

        if (scoresRaw.size() == 0) {
            return;
        }

        Matcher matcher = GAME_ID_PATTERN.matcher(scoresRaw.get(0));
        if (!matcher.matches()) {
            return;
        }

        gameId = matcher.group(1);

        for (String line: scoresRaw) {
            if (PREGAME_LOBBY_PATTERN.matcher(line).matches()) {
                gameId = null;
                return;
            }
        }

        for (int i = 0; i < scoresRaw.size(); i++) {
            String line = scoresRaw.get(i);

            if (WITHER_ALIVE_PATTERN.matcher(line).matches()) {
                String lineColor = scoresColor.get(i);
                String colorCode = lineColor.split("\u00a7")[1].substring(0, 1);
                aliveWithers.add(colorCode);
            }
        }
    }
    
    public static boolean isitPrepPhase() {
    	
    	Minecraft mc = Minecraft.getMinecraft();

        if (mc.theWorld == null || !MinecraftUtils.isHypixel()) {
            return false;
        }

        Scoreboard scoreboard = mc.theWorld.getScoreboard();
        if (scoreboard == null) {
            return false;
        }
        
        List<String> scoresColor = ScoreboardUtils.getSidebarText(scoreboard);
        List<String> scoresRaw = ScoreboardUtils.getUnformattedSidebarText(scoresColor);

        if (scoresRaw.size() < 2) {
            return false;
        }

        return scoresRaw.get(1).contains("Walls Fall:");
		
    }

    public boolean isWitherAlive(String colorCode) {
        return aliveWithers.contains(colorCode);
    }

    public String getGameId() {
        return gameId;
    }

    public List<String> getAliveWithers() {
        return aliveWithers;
    }
}
