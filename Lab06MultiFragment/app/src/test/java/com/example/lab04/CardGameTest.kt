package com.example.lab04

import com.example.lab04.model.Card
import com.example.lab04.model.CardMatchingGame
import com.example.lab04.model.Deck
import org.junit.Assert.*
import org.junit.Test

class CardGameTest {
    @Test fun pairModeMatchesOnSecondCard() {
        val game = CardMatchingGame.fixture(listOf(Card("♥", "A"), Card("♠", "A")), 2)
        game.chooseCardAtIndex(0)
        assertEquals(0, game.matchedCount)
        game.chooseCardAtIndex(1)
        assertEquals(2, game.matchedCount)
        assertEquals(14, game.score)
    }

    @Test fun matchingThreeRanks() {
        val game = CardMatchingGame.fixture(listOf(Card("♥", "A"), Card("♠", "A"), Card("♦", "A")))
        repeat(3) { game.chooseCardAtIndex(it) }
        assertEquals(33, game.score)
        assertEquals(3, game.matchedCount)
    }

    @Test fun matchingThreeSuits() {
        val game = CardMatchingGame.fixture(listOf(Card("♥", "A"), Card("♥", "2"), Card("♥", "3")))
        repeat(3) { game.chooseCardAtIndex(it) }
        assertEquals(9, game.score)
        assertEquals(3, game.matchedCount)
    }

    @Test fun twoCardsNeverMatch() {
        assertEquals(0, Card("♥", "A").match(arrayOf(Card("♠", "A"))))
        assertEquals(0, Card("♥", "A").match(arrayOf(Card("♠", "A"), Card("♦", "2"))))
    }

    @Test fun thirdCardMismatchLeavesThirdSelected() {
        val game = CardMatchingGame.fixture(listOf(Card("♥", "A"), Card("♠", "2"), Card("♦", "3")))
        repeat(3) { game.chooseCardAtIndex(it) }
        assertEquals(-5, game.score)
        assertFalse(game.cardAtIndex(0).isChosen)
        assertFalse(game.cardAtIndex(1).isChosen)
        assertTrue(game.cardAtIndex(2).isChosen)
    }

    @Test fun resetDealsFreshBoard() {
        val game = CardMatchingGame(24)
        repeat(3) { game.chooseCardAtIndex(it) }
        game.reset()
        assertEquals(24, game.count)
        assertEquals(0, game.score)
        assertEquals(0, game.matchedCount)
        assertTrue((0 until game.count).all { !game.cardAtIndex(it).isChosen })
    }

    @Test fun deckHasUniqueCards() {
        val deck = Deck()
        val seen = mutableSetOf<String>()
        repeat(52) { seen.add(deck.drawRandomCard()!!.toString()) }
        assertEquals(52, seen.size)
        assertNull(deck.drawRandomCard())
    }

    @Test fun generatedBoardHasEightThreeCardGroups() {
        val game = CardMatchingGame(24)
        val ranks = (0 until game.count).map { game.cardAtIndex(it).toString().drop(1) }
        assertEquals(8, ranks.distinct().size)
        assertTrue(ranks.groupingBy { it }.eachCount().values.all { it == 3 })
    }

    @Test fun gameSurvivesSerialization() {
        val game = CardMatchingGame(24)
        game.chooseCardAtIndex(0)
        val bytes = java.io.ByteArrayOutputStream()
        java.io.ObjectOutputStream(bytes).use { it.writeObject(game) }
        val copy = java.io.ObjectInputStream(java.io.ByteArrayInputStream(bytes.toByteArray())).use {
            it.readObject() as CardMatchingGame
        }
        assertEquals(-1, copy.score)
        assertTrue(copy.cardAtIndex(0).isChosen)
    }
}
