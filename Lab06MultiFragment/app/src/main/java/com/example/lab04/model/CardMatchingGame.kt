package com.example.lab04.model

class CardMatchingGame private constructor(private val cards: MutableList<Card>, private val groupSize: Int) : java.io.Serializable {
    constructor(count: Int, groupSize: Int = 3): this(deal(count, groupSize), groupSize)
    companion object {
        private const val serialVersionUID = 1L
        private fun deal(count:Int, groupSize: Int):MutableList<Card> {
            require(groupSize in 2..3 && count in groupSize..(groupSize * 13) && count % groupSize == 0)
            return Card.rankStrings.toList().shuffled().take(count / groupSize)
                .flatMap { rank -> Card.validSuits.toList().shuffled().take(groupSize).map { suit -> Card(suit, rank) } }
                .shuffled().toMutableList()
        }
        fun fixture(cards:List<Card>, groupSize: Int = 3) = CardMatchingGame(cards.toMutableList(), groupSize)
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
        val fresh = deal(cards.size, groupSize)
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
                if (chosen.size == groupSize - 1) {
                    val matchScore = card.match(chosen.toTypedArray(), groupSize)
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
