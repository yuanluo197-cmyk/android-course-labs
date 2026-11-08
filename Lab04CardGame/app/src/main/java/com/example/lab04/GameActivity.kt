package com.example.lab04
import android.app.Activity
import android.os.Bundle
import android.widget.Button
import android.widget.GridLayout
import android.content.res.ColorStateList
import android.graphics.Color
import com.example.lab04.model.CardMatchingGame
import com.example.lab04.databinding.ActivityGameBinding
class GameActivity : Activity() {
 private lateinit var b:ActivityGameBinding
 private lateinit var game:CardMatchingGame
 private val buttons=mutableListOf<Button>()
 override fun onCreate(state:Bundle?) {
  super.onCreate(state);b=ActivityGameBinding.inflate(layoutInflater);setContentView(b.root);applyInsets(b.root)
  @Suppress("DEPRECATION")
  val restored=state?.getSerializable(getString(R.string.state_game)) as? CardMatchingGame
  game=restored?:CardMatchingGame(16)
  repeat(16){i->
   val button=Button(this)
   val params=GridLayout.LayoutParams(GridLayout.spec(i/4),GridLayout.spec(i%4,1f))
   params.width=0;params.height=(80*resources.displayMetrics.density).toInt();params.setMargins(4,4,4,4)
   button.layoutParams=params;button.textSize=20f;button.isAllCaps=false
   button.setOnClickListener { game.chooseCardAtIndex(i);render() }
   buttons.add(button);b.cards.addView(button)
  }
  b.restart.setOnClickListener { game=CardMatchingGame(16);render() }
  b.exit.setOnClickListener { finish() };render()
 }
 private fun render() {
  buttons.forEachIndexed{i,v->
   val card=game.cardAtIndex(i);val face=card.isChosen||card.isMatched
   v.text=if(face)card.toString() else getString(R.string.card_back)
   v.contentDescription=if(face)getString(R.string.card_face,i+1,card.toString()) else getString(R.string.card_hidden,i+1)
   v.isEnabled=!card.isMatched
   v.backgroundTintList=ColorStateList.valueOf(if(card.isMatched)0xFFD0E8DD.toInt() else if(face)Color.WHITE else 0xFF176B87.toInt())
   v.setTextColor(if(face)0xFF143449.toInt() else Color.WHITE)
  }
  b.score.text=getString(R.string.score_format,game.score,game.matchedCount/2)
  b.status.setText(if(game.matchedCount==16)R.string.finished else R.string.ready)
  val p=(application as PlayerApp).player;p.best=maxOf(p.best,game.score)
 }
 override fun onSaveInstanceState(out:Bundle){out.putSerializable(getString(R.string.state_game),game);super.onSaveInstanceState(out)}
}
