package com.example.lab04
import com.example.lab04.model.*
import org.junit.Assert.*
import org.junit.Test
class CardGameTest {
 private fun game(a:Card,b:Card)=CardMatchingGame.fixture(listOf(a,b))
 @Test fun rankMatch(){assertEquals(4,Card("♥","A").match(arrayOf(Card("♠","A"))))}
 @Test fun suitMatch(){assertEquals(1,Card("♥","A").match(arrayOf(Card("♥","2"))))}
 @Test fun noMatch(){assertEquals(0,Card("♥","A").match(arrayOf(Card("♠","2"))))}
 @Test fun rankScore(){val g=game(Card("♥","A"),Card("♠","A"));g.chooseCardAtIndex(0);g.chooseCardAtIndex(1);assertEquals(14,g.score);assertEquals(2,g.matchedCount);g.chooseCardAtIndex(0);assertEquals(14,g.score)}
 @Test fun suitScore(){val g=game(Card("♥","A"),Card("♥","2"));g.chooseCardAtIndex(0);g.chooseCardAtIndex(1);assertEquals(2,g.score)}
 @Test fun mismatch(){val g=game(Card("♥","A"),Card("♠","2"));g.chooseCardAtIndex(0);g.chooseCardAtIndex(1);assertEquals(-4,g.score);assertFalse(g.cardAtIndex(0).isChosen);assertTrue(g.cardAtIndex(1).isChosen)}
 @Test fun deselect(){val g=game(Card("♥","A"),Card("♠","2"));g.chooseCardAtIndex(0);g.chooseCardAtIndex(0);assertEquals(-1,g.score);assertFalse(g.cardAtIndex(0).isChosen)}
 @Test fun fullDeck(){val d=Deck();val seen=mutableSetOf<String>();repeat(52){seen.add(d.drawRandomCard()!!.toString())};assertEquals(52,seen.size);assertNull(d.drawRandomCard())}
 @Test fun freshGame(){val g=CardMatchingGame(16);assertEquals(16,g.count);assertEquals(0,g.score);assertEquals(0,g.matchedCount)}
 @Test fun saveRoundTrip(){val g=game(Card("♥","A"),Card("♠","A"));g.chooseCardAtIndex(0);val bytes=java.io.ByteArrayOutputStream();java.io.ObjectOutputStream(bytes).use{it.writeObject(g)};val copy=java.io.ObjectInputStream(java.io.ByteArrayInputStream(bytes.toByteArray())).use{it.readObject() as CardMatchingGame};assertEquals(-1,copy.score);assertTrue(copy.cardAtIndex(0).isChosen)}
}
