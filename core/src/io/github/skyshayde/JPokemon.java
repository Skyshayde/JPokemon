package io.github.skyshayde;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapRenderer;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;

public class JPokemon extends Game {
    SpriteBatch batch;
    TiledMap tiledMap;
    OrthographicCamera camera;
    TiledMapRenderer tiledMapRenderer;
    Texture npcSheet;
    Player player;

    @Override
    public void create() {
        batch = new SpriteBatch();

        camera = new OrthographicCamera();
        camera.setToOrtho(false, 15 * 32, 15 * 32);
        camera.update();

        npcSheet = new Texture(Gdx.files.internal("boy_run.png"));
        TextureRegion[][] tmp = TextureRegion.split(npcSheet, npcSheet.getWidth() / 4, npcSheet.getHeight() / 4);
        player = new Player(camera);

        int index = 0;
        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 4; j++) {
                player.frames[index++] = tmp[i][j];
            }
        }


        tiledMap = new TmxMapLoader().load("test.tmx");
        tiledMapRenderer = new OrthogonalTiledMapRenderer(tiledMap);
    }

    @Override
    public void render() {
        Gdx.gl.glClearColor(1, 0, 0, 1);
        Gdx.gl.glBlendFunc(GL20.GL_SRC_ALPHA, GL20.GL_ONE_MINUS_SRC_ALPHA);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        camera.update();

        tiledMapRenderer.setView(camera);
        tiledMapRenderer.render();
        batch.begin();
        batch.draw(player.frames[player.currFrame+player.walkState], 8 * 32f, 8 * 32f);
        batch.end();

        if (Gdx.input.isKeyPressed(Input.Keys.LEFT))
            player.move(1);
        if (Gdx.input.isKeyPressed(Input.Keys.RIGHT))
            player.move(2);
        if (Gdx.input.isKeyPressed(Input.Keys.UP))
            player.move(3);
        if (Gdx.input.isKeyPressed(Input.Keys.DOWN))
            player.move(4);
        if (player.x != 0) {
            int move = player.x > 0 ? 1 : -1;
            move = move * 2;
            camera.translate(move, 0);
            player.x = player.x + move * -1;
            player.walkState = player.walkState > 2 ? player.walkState = 0 : player.walkState + 1;
        }
        if (player.y != 0) {
            int move = player.y > 0 ? 1 : -1;
            move = move * 2;
            camera.translate(0, move);
            player.y = player.y + move * -1;
            player.walkState = player.walkState > 2 ? player.walkState = 0 : player.walkState + 1;
        }
    }

    @Override
    public void dispose() {

    }
}
