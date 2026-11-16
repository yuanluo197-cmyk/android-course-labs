package com.example.lab04.model

class CardMatchingGame private constructor(private val cards: MutableList<Card>) : java.io.Serializable {
    constructor(count: Int): this(deal(count))
    companion object {
        private fun deal(count:Int):MutableList<Card> {
            require(count in 3..39 && count % 3 == 0)
            return Card.rankStrings.toList().shuffled().take(count / 3)
                .flatMap { rank -> Card.validSuits.toList().shuffled().take(3).map { suit -> Card(suit, rank) } }
                .shuffled().toMutableList()
        }
        fun fixture(cards:List<Card>) = CardMatchingGame(cards.toMutableList())
    }
    val matchedCount:Int get() = cards.count { it.isMatched }
    val count:Int get() = cards.size
    var score = 0
        private set

    fun cardAtIndex(index: Int): Card {
        return cards.get(index)
    }

    val MISMATCH_PENALTY = 2
    val MATCH_BONUS = 4
    val COST_TO_CHOOSE = 1

    fun reset() {
        val fresh = deal(cards.size)
        cards.clear()
        cards.addAll(fresh)
        score = 0
    }

    fun chooseCardAtIndex(index: Int) {
        val card= cardAtIndex(index)
        if (!card.isMatched) {
            if (card.isChosen) {
                card.isChosen = false
            } else {
                val chosen = cards.filter { it.isChosen && !it.isMatched }
                if (chosen.size == 2) {
                    val matchScore = card.match(chosen.toTypedArray())
                    if (matchScore > 0) {
                        score += matchScore * MATCH_BONUS
                        chosen.forEach { it.isMatched = true }
                        card.isMatched = true
                    } else {
                        score -= MISMATCH_PENALTY
                        chosen.forEach { it.isChosen = false }
                    }
                }
                score -= COST_TO_CHOOSE
                card.isChosen = true
            }
        }
    }
}
