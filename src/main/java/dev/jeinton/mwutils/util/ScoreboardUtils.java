package dev.jeinton.mwutils.util;

import com.google.common.collect.Iterables;
import com.google.common.collect.Lists;
import net.minecraft.scoreboard.Score;
import net.minecraft.scoreboard.ScoreObjective;
import net.minecraft.scoreboard.ScorePlayerTeam;
import net.minecraft.scoreboard.Scoreboard;
import net.minecraft.util.StringUtils;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

public class ScoreboardUtils {
    public static List<String> getSidebarText(Scoreboard scoreboard) {
        List<String> lines = new ArrayList<>();
        ScoreObjective objective = scoreboard.getObjectiveInDisplaySlot(1);

        if (objective == null) {
            return lines;
        }

        Collection<Score> scores = scoreboard.getSortedScores(objective);

        // TODO: Unnecessary null checks?
        List<Score> list = scores.stream()
                .filter(input -> input != null && input.getPlayerName() != null && !input.getPlayerName().startsWith("#"))
                .collect(Collectors.toList());

        if (list.size() > 15) {
            scores = Lists.newArrayList(Iterables.skip(list, scores.size() - 15));
        } else {
            scores = list;
        }

        for (Score score : scores) {
            ScorePlayerTeam team = scoreboard.getPlayersTeam(score.getPlayerName());
            lines.add(ScorePlayerTeam.formatPlayerName(team, ""));
        }

        Collections.reverse(lines);
        return lines;
    }

    public static List<String> getUnformattedSidebarText(Scoreboard scoreboard) {
        List<String> lines = getSidebarText(scoreboard);
        for (int i = 0; i < lines.size(); i++) {
            lines.set(i, StringUtils.stripControlCodes(lines.get(i)));
        }
        return lines;
    }

    public static List<String> getUnformattedSidebarText(List<String> scores) {
        List<String> lines = new ArrayList<>(scores);
        for (int i = 0; i < lines.size(); i++) {
            lines.set(i, StringUtils.stripControlCodes(lines.get(i)));
        }
        return lines;
    }

    public static String getSidebarTitle(Scoreboard scoreboard) {
        ScoreObjective objective = scoreboard.getObjectiveInDisplaySlot(1);

        if (objective == null) {
            return "";
        }

        return objective.getDisplayName();
    }

    public static String getUnformattedSidebarTitle(Scoreboard scoreboard) {
        return StringUtils.stripControlCodes(getSidebarTitle(scoreboard));
    }
}

