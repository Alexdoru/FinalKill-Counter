package dev.jeinton.mwutils;

import dev.jeinton.mwutils.util.ScoreboardUtils;
import net.minecraft.scoreboard.Scoreboard;

import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class MwScoreboardData {
    private final Pattern GAME_ID_PATTERN = Pattern.compile("\\s*\\d+/\\d+/\\d+\\s+([\\d\\w]+)\\s*", Pattern.CASE_INSENSITIVE);
    private final Pattern MW_TITLE_PATTERN = Pattern.compile("\\s*MEGA\\sWALLS\\s*", Pattern.CASE_INSENSITIVE);
    private final Pattern PREGAME_LOBBY_PATTERN = Pattern.compile(".+[0-9]+/[0-9]+\\s*");

    private String gameId = null;

    public MwScoreboardData(Scoreboard scoreboard) {
        if (scoreboard == null) {
            return;
        }

        String title = ScoreboardUtils.getUnformattedSidebarTitle(scoreboard);
        if (!MW_TITLE_PATTERN.matcher(title).matches()) {
            return;
        }

        List<String> scores = ScoreboardUtils.getUnformattedSidebarText(scoreboard);

        if (scores.size() == 0) {
            return;
        }

        Matcher matcher = GAME_ID_PATTERN.matcher(scores.get(0));
        if (!matcher.matches()) {
            return;
        }

        gameId = matcher.group(1);

        for (String line: scores) {
            if (PREGAME_LOBBY_PATTERN.matcher(line).matches()) {
                gameId = null;
                return;
            }
        }
    }

    public String getGameId() {
        return gameId;
    }
}
