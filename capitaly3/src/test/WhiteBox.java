package test;
import game.*;
import players.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import players.Player;

class WhiteBox {
    private Game game;

    @BeforeEach
    void setUp() throws Exception {
        game = new Game("game.txt", "dice.txt");
    }

}

