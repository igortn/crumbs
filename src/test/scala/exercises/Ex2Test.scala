package exercises

import exercises.Ex2._

import org.scalatest.FunSuite

class Ex2Test extends FunSuite {

  val promotions: Seq[Promotion] = Seq(
    Promotion("P1", Seq("P3")),
    Promotion("P2", Seq("P4", "P5")),
    Promotion("P3", Seq("P1")),
    Promotion("P4", Seq("P2")),
    Promotion("P5", Seq("P2"))
  )


  test("Combinable promotions calculated correctly.") {
    assert(combinablePromotions("P1", promotions) ===
      Seq(
        PromotionCombo(Seq("P1", "P2")),
        PromotionCombo(Seq("P1", "P4", "P5"))
      )
    )
    assert(combinablePromotions("P3", promotions) ===
      Seq(
        PromotionCombo(Seq("P3", "P2")),
        PromotionCombo(Seq("P3", "P4", "P5"))
      )
    )
  }
}
