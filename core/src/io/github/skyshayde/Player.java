package io.github.skyshayde;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

/**
 * Created by skysh on 11/29/2017.
 */
public class Player {

    TextureRegion[] frames;
    int x = 0;
    int y = 0;
    int currFrame = 0;
    int walkState = 0;
    OrthographicCamera camera;

    public Player(OrthographicCamera c) {
        frames = new TextureRegion[16];
        camera = c;
    }

    private int dirToSprite(int dir) {
        switch (dir) {
            case 1:
                return 5;
            case 2:
                return 9;
            case 3:
                return 13;
            case 4:
                return 0;
        }
        return 0;
    }

    public void move(int dir) {
        if (x != 0 || y != 0) {
            return;
        }
        switch (dir) {
            case 1:
                // left - 2
                if (currFrame == 4) {
                    x = x - 32;
                    break;
                }
                currFrame = 4;
                break;
            case 2:
                // right - 3
                if (currFrame == 8) {
                    x = x + 32;
                    break;
                }
                currFrame = 8;
                break;
            case 3:
                // up - 4
                if (currFrame == 12) {
                    y = y + 32;
                    break;
                }
                currFrame = 12;
                break;
            case 4:
                // down - 1
                if (currFrame == 0) {
                    y = y - 32;
                    break;
                }
                currFrame = 0;
                break;
        }
    }

    public void drawAndMove(SpriteBatch batch) {
        batch.begin();
        batch.draw(this.frames[this.currFrame+this.walkState], 8 * 32f, 8 * 32f);
        batch.end();

        if (Gdx.input.isKeyPressed(Input.Keys.LEFT))
            this.move(1);
        if (Gdx.input.isKeyPressed(Input.Keys.RIGHT))
            this.move(2);
        if (Gdx.input.isKeyPressed(Input.Keys.UP))
            this.move(3);
        if (Gdx.input.isKeyPressed(Input.Keys.DOWN))
            this.move(4);
        if (this.x != 0) {
            int move = this.x > 0 ? 1 : -1;
            move = move * 2;
            camera.translate(move, 0);
            this.x = this.x + move * -1;
            this.walkState = this.walkState > 2 ? this.walkState = 0 : this.walkState + 1;
        }
        if (this.y != 0) {
            int move = this.y > 0 ? 1 : -1;
            move = move * 2;
            camera.translate(0, move);
            this.y = this.y + move * -1;
            this.walkState = this.walkState > 2 ? this.walkState = 0 : this.walkState + 1;
        }
    }
}
