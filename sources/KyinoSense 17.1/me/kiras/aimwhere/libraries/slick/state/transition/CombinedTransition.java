/*
 * Decompiled with CFR 0.152.
 */
package me.kiras.aimwhere.libraries.slick.state.transition;

import java.util.ArrayList;
import me.kiras.aimwhere.libraries.slick.GameContainer;
import me.kiras.aimwhere.libraries.slick.Graphics;
import me.kiras.aimwhere.libraries.slick.SlickException;
import me.kiras.aimwhere.libraries.slick.state.GameState;
import me.kiras.aimwhere.libraries.slick.state.StateBasedGame;
import me.kiras.aimwhere.libraries.slick.state.transition.Transition;

public class CombinedTransition
implements Transition {
    private ArrayList transitions = new ArrayList();

    public void addTransition(Transition t) {
        this.transitions.add(t);
    }

    @Override
    public boolean isComplete() {
        for (int i = 0; i < this.transitions.size(); ++i) {
            if (((Transition)this.transitions.get(i)).isComplete()) continue;
            return false;
        }
        return true;
    }

    @Override
    public void postRender(StateBasedGame game, GameContainer container, Graphics g) throws SlickException {
        for (int i = this.transitions.size() - 1; i >= 0; --i) {
            ((Transition)this.transitions.get(i)).postRender(game, container, g);
        }
    }

    @Override
    public void preRender(StateBasedGame game, GameContainer container, Graphics g) throws SlickException {
        for (int i = 0; i < this.transitions.size(); ++i) {
            ((Transition)this.transitions.get(i)).postRender(game, container, g);
        }
    }

    @Override
    public void update(StateBasedGame game, GameContainer container, int delta) throws SlickException {
        for (int i = 0; i < this.transitions.size(); ++i) {
            Transition t = (Transition)this.transitions.get(i);
            if (t.isComplete()) continue;
            t.update(game, container, delta);
        }
    }

    @Override
    public void init(GameState firstState, GameState secondState) {
        for (int i = this.transitions.size() - 1; i >= 0; --i) {
            ((Transition)this.transitions.get(i)).init(firstState, secondState);
        }
    }
}

