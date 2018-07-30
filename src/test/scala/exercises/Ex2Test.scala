package exercises

import exercises.Ex2._

import org.scalatest.FunSuite

class Ex2Test extends FunSuite {

  test("Apply an exclusion rule to a sequence of internally compatible subsets.") {
    val subsets = Seq(Set("P1", "P2", "P4", "P5"), Set("P2", "P3", "P4", "P5"))
    val rule1 = ("P2", Seq("P6"))
    val rule2 = ("P6", Seq("P1", "P2"))
    val rule3 = ("P2", Seq("P4", "P5"))

    assert(applyRule(subsets, rule1) === subsets)
    assert(applyRule(subsets, rule2) === subsets)
    assert(applyRule(subsets, rule3) === Seq(
      Set("P1", "P4", "P5"), Set("P1", "P2"), Set("P3", "P4", "P5"), Set("P2", "P3")))
  }

  val promotions: Seq[Promotion] = Seq(
    Promotion("P1", Seq("P3")),
    Promotion("P2", Seq("P4", "P5")),
    Promotion("P3", Seq("P1")),
    Promotion("P4", Seq("P2")),
    Promotion("P5", Seq("P2"))
  )

  test("The full set of combinable subsets is built correctly.") {
    assert(
      allCombinableSubsets(promotions).toSet ===
      Set(Set("P1","P2"), Set("P2","P3"), Set("P1","P4","P5"), Set("P3","P4","P5"))
    )
  }

  /*
   * This tests the data set presented in the problem statement.
   */
  test("Combinable promotions calculated correctly.") {
    assert(
      combinablePromotions("P1", promotions).map(_.promotions.toSet).toSet ===
        Set(Set("P1", "P2"), Set("P1", "P4", "P5"))
    )

    assert(
      combinablePromotions("P3", promotions).map(_.promotions.toSet).toSet ===
        Set(Set("P3", "P2"), Set("P3", "P4", "P5"))
    )
  }
}
