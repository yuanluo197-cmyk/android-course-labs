package com.example.lab04

import android.content.res.Configuration
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import com.example.lab04.databinding.ActivityGameBinding
import com.example.lab04.model.CardMatchingGame
import java.io.IOException

class PairGameFragment : GameFragment(2, 16, "pairs.dat")
class TripleGameFragment : GameFragment(3, 24, "triples.dat")

abstract class GameFragment(
    private val groupSize: Int,
    private val cardCount: Int,
    private val filename: String
) : Fragment(R.layout.activity_game) {
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        val binding = ActivityGameBinding.bind(view)
        val app = requireActivity().application as PlayerApp
        var newGame = false
        val game = app.store.load(filename, CardMatchingGame::class.java) {
            newGame = true
            CardMatchingGame(cardCount, groupSize)
        }
        val landscape = resources.configuration.orientation == Configuration.ORIENTATION_LANDSCAPE
        binding.cards.layoutManager = GridLayoutManager(requireContext(), if (landscape) 6 else 4)
        binding.cards.itemAnimator = null
        binding.rules.setText(if (groupSize == 2) R.string.pair_rules else R.string.rules)

        fun render() {
            binding.score.text = getString(R.string.score_format, game.score,
                game.matchedCount / groupSize, game.count / groupSize)
            binding.status.setText(when {
                game.matchedCount == game.count -> R.string.finished
                groupSize == 2 -> R.string.pair_ready
                else -> R.string.ready
            })
        }

        fun persist() {
            try {
                app.store.save(filename, game)
                if (game.score > app.player.best) {
                    val player = app.player.copy(best = game.score)
                    app.store.save(PlayerApp.PROFILE_FILE, player)
                    app.player = player
                }
            } catch (_: IOException) {
                Toast.makeText(requireContext(), R.string.save_failed, Toast.LENGTH_LONG).show()
            }
        }

        val adapter = CardAdapter(game) { index ->
            game.chooseCardAtIndex(index)
            persist()
            binding.cards.adapter?.notifyItemRangeChanged(0, game.count)
            render()
        }
        binding.cards.adapter = adapter
        if (newGame) persist()
        binding.restart.setOnClickListener {
            game.reset()
            persist()
            adapter.notifyItemRangeChanged(0, game.count)
            render()
        }
        render()
    }
}
