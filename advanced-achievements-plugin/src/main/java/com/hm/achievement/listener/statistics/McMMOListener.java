package com.hm.achievement.listener.statistics;

import com.gmail.nossr50.datatypes.skills.PrimarySkillType;
import com.gmail.nossr50.events.experience.McMMOPlayerLevelUpEvent;
import com.gmail.nossr50.events.players.McMMOPlayerProfileLoadEvent;
import com.hm.achievement.AdvancedAchievements;
import com.hm.achievement.category.MultipleAchievements;
import com.hm.achievement.config.AchievementMap;
import com.hm.achievement.db.CacheManager;
import org.bukkit.Bukkit;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.plugin.java.JavaPlugin;

import javax.inject.Inject;
import javax.inject.Named;
import javax.inject.Singleton;
import java.util.HashSet;
import java.util.Set;

/**
 * Listener class to deal with mcMMO achievements.
 *
 * @author Yurinan
 * @since 2021/12/15 23:45
 */

@Singleton
public class McMMOListener extends AbstractListener {

    @Inject
    public McMMOListener(@Named("main") YamlConfiguration mainConfig, AchievementMap achievementMap, CacheManager cacheManager) {
        super(MultipleAchievements.MCMMO, mainConfig, achievementMap, cacheManager);
    }

    @EventHandler(priority = EventPriority.MONITOR, ignoreCancelled = true)
    public void onMcMMO(McMMOPlayerLevelUpEvent event) {
        Player player = event.getPlayer().getPlayer();
        if (player == null) {
            return;
        }

        String jobName = event.getSkill().getName().toLowerCase();
        if (!player.hasPermission(category.toChildPermName(jobName))) {
            return;
        }

        Set<String> subcategories = new HashSet<>();

        addMatchingSubcategories(subcategories, jobName);
        increaseStatisticAndAwardAchievementsIfAvailable(player, subcategories, event.getSkillLevel());
    }

    @EventHandler(priority = EventPriority.MONITOR, ignoreCancelled = true)
    public void onPlayerJoin(McMMOPlayerProfileLoadEvent event) {
        Player player = event.getPlayer().getPlayer();
        if (player == null) {
            return;
        }

        if (event.isAsynchronous()) {
            Bukkit.getScheduler().runTask(JavaPlugin.getPlugin(AdvancedAchievements.class), () -> {
                for (PrimarySkillType skill : PrimarySkillType.values()) {
                    String skillName = skill.getName().toLowerCase();
                    if (!player.hasPermission(category.toChildPermName(skillName))) {
                        continue;
                    }

                    Set<String> subcategories = new HashSet<>();

                    addMatchingSubcategories(subcategories, skillName);
                    increaseStatisticAndAwardAchievementsIfAvailable(player, subcategories,
                            event.getProfile().getSkillLevel(skill));
                }
            });
        } else {
            for (PrimarySkillType skill : PrimarySkillType.values()) {
                String skillName = skill.getName().toLowerCase();
                if (!player.hasPermission(category.toChildPermName(skillName))) {
                    continue;
                }

                Set<String> subcategories = new HashSet<>();

                addMatchingSubcategories(subcategories, skillName);
                increaseStatisticAndAwardAchievementsIfAvailable(player, subcategories,
                        event.getProfile().getSkillLevel(skill));
            }
        }
    }

}
