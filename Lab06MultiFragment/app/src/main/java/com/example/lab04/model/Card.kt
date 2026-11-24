package com.example.lab04.model

class Card(private var suit: String, private var rank: String, var isChosen:Boolean = false, var isMatched:Boolean = false) : java.io.Serializable {
    companion object {
        private const val serialVersionUID = 1L
        val rankStrings= arrayOf("A", "2", "3", "4", "5", "6", "7", "8", "9", "10", "J", "Q", "K")
        val validSuits = arrayOf("♥", "♦", "♠", "♣")
    }

    override fun toString(): String {
        return suit + rank
    }

    fun match(otherCards: Array<Card>, groupSize: Int = 3): Int {
        var score = 0
        if (groupSize in 2..3 && otherCards.size == groupSize - 1) {
            score = when {
                otherCards.all { it.rank == rank } -> if (groupSize == 2) 4 else 9
                otherCards.all { it.suit == suit } -> if (groupSize == 2) 1 else 3
                else -> 0
            }
        }
        return score
    }
}
