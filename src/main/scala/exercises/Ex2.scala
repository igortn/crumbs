package exercises

case class Promotion(code: String, notCombinableWith: Seq[String])
case class PromotionCombo(promotions: Seq[String])

/*
 * See `exercises.Ex2Test` for unit tests.
 */
object Ex2 {

  def combinablePromotions(promotionCode: String,
                           allPromotions: Seq[Promotion]): Seq[PromotionCombo] = Seq()

}
