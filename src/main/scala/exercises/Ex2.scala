package exercises

/*
 * In the problem statement, the type of `notCombinableWith` is defined as `Seq[Promotion]`.
 * This is an unnecessary complication, and it is probably an oversight because the sample data
 * are given differently. Therefore, we are using `Seq[String]` here instead.
 */
case class Promotion(code: String, notCombinableWith: Seq[String])

case class PromotionCombo(promotions: Seq[String])

/*
 * See `exercises.Ex2Test` for unit tests.
 */
object Ex2 {

/*
 * There are multiple ways to implement this algorithm, but there are two different conceptual
 * approaches that are dual to each other. The first approach would build all combinable promotions
 * for the given code "bottom-up", i.e. starting with the code and sequentially adding all possible
 * mutually allowed codes to grow the maximal combinable subsets. The second, "top-down" approach
 * starts with the full set and sequentially applies the exclusion rules to split the original set
 * into multiple allowed subsets. The "top-down" approach is simpler logically, and it is likely
 * more performant. Another advantage of the "top-down" approach is that we can start with
 * building the total set of maximal compatible subsets, and then this set can be reused to find
 * combinable promotions for any promotion code. Therefore, we choose to use the "top-down"
 * approach in this implementation.
 *
 * Note that this implementation is internally based on sets, not on sequences which feels
 * more natural (the external API is preserved). Therefore, the order of the elements
 * in the result is not guaranteed. The unit tests destructure the results from the API into
 * sets before doing comparisons. It would be easy to make this implementation based internally
 * on sequences if required.
 */

  /**
   * Applies an exclusion rule to one internally compatible subset. The result is the sequence
   * that contains either only initial subset, or two new internally compatible subsets.
   */
  private def splitSetWithRule(set: Set[String], rule: (String, Seq[String])): Seq[Set[String]] =
    if (set.contains(rule._1) && set.exists(code => rule._2.contains(code))) {
      val s1 = set - rule._1
      val s2 = set -- rule._2
      Seq(s1, s2)
    } else {
      Seq(set)
    }

  /**
    * Applies an exclusion rule to a sequence of internally compatible subsets by splitting
    * them as necessary.
    */
  def applyRule(subsets: Seq[Set[String]], rule: (String, Seq[String])): Seq[Set[String]] =
    subsets.foldLeft(Seq.empty[Set[String]]) {
      (acc, set) => acc ++ splitSetWithRule(set, rule)
    }

  /**
    * Builds the sequence of all maximal internally compatible subsets for a given
    * collection of promotions. The result of this method can be used to find combinable
    * promotions for any promotion code. This can lead to a substantial optimization for
    * large input sets. Another possible optimization can be based on the fact that the set
    * of exclusion rules is inherently symmetric which could allow to cut down it size
    * approximately in half. This was not pursued in the current implementation.
    */
  def allCombinableSubsets(allPromotions: Seq[Promotion]): Seq[Set[String]] = {
    val exclusionRules: Seq[(String, Seq[String])] =
      allPromotions.map { p => Promotion.unapply(p).get }
    val initial: Seq[Set[String]] = Seq(allPromotions.map(_.code).toSet)

    exclusionRules.foldLeft(initial) {
      (subsets, rule) => applyRule(subsets, rule)
    }
  }

  /**
    * Finds all combinable promotions for a given promotion code.
    */
  def combinablePromotions(promotionCode: String,
                           allPromotions: Seq[Promotion]): Seq[PromotionCombo] =
    allCombinableSubsets(allPromotions)
      .filter(_.contains(promotionCode))
      .map { subset => PromotionCombo(subset.toSeq) }

}
