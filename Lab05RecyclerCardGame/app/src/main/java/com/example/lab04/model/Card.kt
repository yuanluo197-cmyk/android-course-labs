package com.example.lab04.model

class Card(private var suit: String, private var rank: String, var isChosen:Boolean = false, var isMatched:Boolean = false) : java.io.Serializable {
    companion object {
        val rankStrings= arrayOf("A", "2", "3", "4", "5", "6", "7", "8", "9", "10", "J", "Q", "K")
        val validSuits = arrayOf("♥", "♦", "♠", "♣")
    }

    override fun toString(): String {
        return suit + rank
    }

    fun match(otherCards: Array<Card>): Int {
        var score = 0
        if (otherCards.size == 2) {
            score = when {
                otherCards.all { it.rank == rank } -> 9
                otherCards.all { it.suit == suit } -> 3
                else -> 0
            }
        }
        return score
    }
}
