package test.student;

import logic.card.*;
import logic.game.CardColor;
import logic.game.CardSymbol;
import logic.game.GameLogic;
import logic.game.PlayDirection;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class ChangeColorCardTest {
    ChangeColorCard c1;
    NumberCard c2;
    ChangeColorCard c3;
    SkipCard c4;
    NumberCard c5;
    NumberCard c6;
    DrawFourCard c7;

    @BeforeEach
    void setup() {
        c1 = new ChangeColorCard();
        c2 = new NumberCard(CardColor.RED, CardSymbol.NINE);
        c3 = new ChangeColorCard();
        c4 = new SkipCard(CardColor.RED);
        c5 = new NumberCard(CardColor.BLUE, CardSymbol.FIVE);
        c6 = new NumberCard(CardColor.YELLOW,CardSymbol.ONE);
        c7 = new DrawFourCard();
    }

    @AfterEach
    void tearDown() {
        GameLogic.clearInstance();
    }

    @Test
    void testConstructor() {
        assertNull(c1.getColor());
        assertEquals(CardSymbol.CHANGE_COLOR, c1.getSymbol());
    }

    @Test
    void testToString() {
        assertEquals("CHANGE COLOR", c1.toString());
        c1.setColor(CardColor.YELLOW);
        assertEquals("CHANGE COLOR (YELLOW color selected)", c1.toString());
    }

    @Test
    void testCanPlay() {
        GameLogic gameInstance = GameLogic.getInstance(1);

        gameInstance.setTopCard(c2);
        assertTrue(c1.canPlay());

        gameInstance.setTopCard(c3);
        assertTrue(c1.canPlay());

        gameInstance.setTopCard(c4);
        assertTrue(c1.canPlay());

        gameInstance.setTopCard(c5);
        assertTrue(c1.canPlay());
    }

    @Test
    void testPerformEffectHasColor() {
        // TODO Implement here
        ArrayList<BaseCard> abc = GameLogic.getInstance().getCurrentPlayerHand();
        abc.add(c5); c1.performEffect();
        assertEquals(CardColor.BLUE,c1.getColor());
        abc.remove(0); abc.add(c6);
        c3.performEffect();
        assertEquals(CardColor.YELLOW,c3.getColor());
    }

    @Test
    void testPerformEffectNoColor() {
        // TODO Implement here
        ArrayList<BaseCard> abc = GameLogic.getInstance().getCurrentPlayerHand();
        abc.add(c3); c1.performEffect();
        assertEquals(CardColor.RED,c1.getColor());
    }

    @Test
    void testPerformEffectNoCard() {
        // TODO Implement here
        c3.performEffect();
        assertEquals(CardColor.RED,c3.getColor());
    }

    @Test
    void testPlay() {
        GameLogic gameInstance = GameLogic.getInstance(2);
        gameInstance.getPlayerHand(0).add(c1);
        gameInstance.getPlayerHand(1).add(c3);
        gameInstance.getPlayerHand(1).add(c5);

        String message = c1.play();

        assertEquals(c1, gameInstance.getTopCard());
        assertEquals(0, gameInstance.getPlayerHand(0).size());
        assertEquals(CardColor.RED, c1.getColor());
        assertEquals("Set color to RED", message);

        gameInstance.goToNextPlayer();

        message = c3.play();

        assertEquals(c3, gameInstance.getTopCard());
        assertEquals(1, gameInstance.getPlayerHand(1).size());
        assertEquals(CardColor.BLUE, c3.getColor());
        assertEquals("Set color to BLUE", message);
    }

}
