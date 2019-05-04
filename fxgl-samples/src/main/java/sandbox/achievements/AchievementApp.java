/*
 * FXGL - JavaFX Game Library. The MIT License (MIT).
 * Copyright (c) AlmasB (almaslvl@gmail.com).
 * See LICENSE for details.
 */

package sandbox.achievements;

import com.almasb.fxgl.app.ApplicationMode;
import com.almasb.fxgl.app.GameApplication;
import com.almasb.fxgl.app.GameSettings;
import com.almasb.fxgl.dsl.FXGL;
import com.almasb.fxgl.io.FS;
import com.almasb.fxgl.saving.DataFile;
import javafx.scene.input.KeyCode;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import static com.almasb.fxgl.dsl.FXGL.*;

/**
 *
 *
 * @author Almas Baimagambetov (AlmasB) (almaslvl@gmail.com)
 */
public class AchievementApp extends GameApplication {

    @Override
    protected void initSettings(GameSettings settings) {
        settings.setApplicationMode(ApplicationMode.DEBUG);
        settings.setAchievementStores(Arrays.asList(new AchievementStore1(), new AchievementStore2()));
    }

    @Override
    protected void initGameVars(Map<String, Object> vars) {
        vars.put("pixelsMoved", 0);
        vars.put("enemiesKilled", 0);
    }

    @Override
    protected void initInput() {
        onKeyDown(KeyCode.Q, "save", () -> {
            var data = saveState();

            FS.writeDataTask(data, "data.dat").run();
        });

        onKeyDown(KeyCode.E, "Load", () -> {
            FS.<DataFile>readDataTask("data.dat")
                    .onSuccess(data -> loadState(data))
                    .run();
        });

        onKeyDown(KeyCode.A, "Dec", () -> {
            inc("enemiesKilled", -1);
        });

        onKeyDown(KeyCode.D, "Inc", () -> {
            inc("enemiesKilled", +1);
        });
    }

    @Override
    protected DataFile saveState() {
        var map = new HashMap<String, Integer>();

        map.put("pixelsMoved", geti("pixelsMoved"));
        map.put("enemiesKilled", geti("enemiesKilled"));

        return new DataFile(map);
    }

    @Override
    protected void loadState(DataFile dataFile) {
        Map<String, Integer> map = (Map<String, Integer>) dataFile.getData();

        set("pixelsMoved", map.get("pixelsMoved"));
        set("enemiesKilled", map.get("enemiesKilled"));
    }

    public static void main(String[] args) {
        launch(args);
    }
}
