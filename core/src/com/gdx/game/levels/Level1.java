package com.gdx.game.levels;

import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.utils.Array;
import com.gdx.game.GdxGame;
import com.gdx.game.listeners.event.events.DeathEvent;
import com.gdx.game.entities.enemies.Enemy;
import com.gdx.game.entities.Player;
import com.gdx.game.entities.enemies.DemoBoss;
import com.gdx.game.factories.MovementSetFactory;

/**
 * The first level of our game. As a level it is final because there are some
 * overridable methods called in th constructor.
 *
 * @author Armando
 */
public final class Level1 extends Level {

    private TiledMap map;
    private Array<Enemy> enemies = null;

    public Level1(Player p) {
        super(p);
        setName("Level1");
    }

    @Override
    public void start() {
        //////////// MAP ///////////
        map = new TmxMapLoader().load("maps/level1/map.tmx");
        ////////////////////////////

        //////////// ENTITIES ///////////////
        MovementSetFactory mvsf = MovementSetFactory.instanceOf();
        float w = getStage().getWidth();
        float h = getStage().getHeight();
        player.setPosition(new Vector2(15, 15 * (h / w)));
        Vector2 v = player.getPosition().add(5, 5);
        DemoBoss db = new DemoBoss("Wandering Demon", 150, 32 / GdxGame.SCALE, 36 / GdxGame.SCALE, v, mvsf.build("Slow", "Square", false), player);
        enemies = Array.with((Enemy) db);
        /////////////////////////////////////

        addListener(new EndLevel1Listener());
        mapRenderer = new OrthogonalTiledMapRenderer(getMap(), 1f / (getPixelPerTile() * getTilePerMeter()));
        instantiateStaticObjects("walls");
        addActor(player);
        for (Enemy enemy : enemies) {
            addActor(enemy);
        }
    }

    @Override
    public TiledMap getMap() {
        return map;
    }

    @Override
    public Array<Enemy> getEnemies() {
        return enemies;
    }

    @Override
    protected float getTilePerMeter() {
        return 25f;
    }

    @Override
    protected float getPixelPerTile() {
        return 16f / 30f;
    }

    private class EndLevel1Listener extends EndLevelListener {

        @Override
        public void changed(ChangeEvent arg0, Actor arg1) {
            if (arg0 instanceof DeathEvent) {
                dispose();
                fire(new EndLevelEvent(!(arg1 instanceof Player)));
            }
        }
    }
}
