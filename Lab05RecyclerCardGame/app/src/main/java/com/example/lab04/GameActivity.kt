package com.example.lab04

import android.app.Activity
import android.content.res.Configuration
import android.os.Bundle
import androidx.recyclerview.widget.GridLayoutManager
import com.example.lab04.databinding.ActivityGameBinding
import com.example.lab04.model.CardMatchingGame

class GameActivity : Activity() {
    private lateinit var binding: ActivityGameBinding
    private lateinit var game: CardMatchingGame
    private lateinit var adapter: CardAdapter

    override fun onCreate(state: Bundle?) {
        super.onCreate(state)
        binding = ActivityGameBinding.inflate(layoutInflater)
        setContentView(binding.root)
        applyInsets(binding.root)
        val landscape = resources.configuration.orientation == Configuration.ORIENTATION_LANDSCAPE
        val count = if (landscape) 18 else 24
        @Suppress("DEPRECATION")
        val restored = state?.getSerializable(getString(R.string.state_game)) as? CardMatchingGame
        game = restored?.takeIf { it.count == count } ?: CardMatchingGame(count)
        binding.cards.layoutManager = GridLayoutManager(this, if (landscape) 6 else 4)
        binding.cards.itemAnimator = null
        adapter = CardAdapter(game) { index ->
            game.chooseCardAtIndex(index)
            adapter.notifyItemRangeChanged(0, game.count)
            renderScore()
        }
        binding.cards.adapter = adapter
        binding.restart.setOnClickListener {
            game.reset()
            adapter.notifyItemRangeChanged(0, game.count)
            renderScore()
        }
        binding.exit.setOnClickListener { finish() }
        renderScore()
    }

    private fun renderScore() {
        binding.score.text = getString(R.string.score_format, game.score, game.matchedCount / 3, game.count / 3)
        binding.status.setText(if (game.matchedCount == game.count) R.string.finished else R.string.ready)
        val player = (application as PlayerApp).player
        player.best = maxOf(player.best, game.score)
    }

    override fun onSaveInstanceState(out: Bundle) {
        out.putSerializable(getString(R.string.state_game), game)
        super.onSaveInstanceState(out)
    }
}
