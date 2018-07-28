package exercises


case class Rate(rateCode: String,
                rateGroup: String)

case class CabinPrice(cabinCode: String,
                      rateCode: String,
                      price: BigDecimal)

case class BestCabinPrice(cabinCode: String,
                          rateCode: String,
                          rateGroup: String,
                          price: BigDecimal)

/*
 * See `exercises.Ex1Test` for unit tests.
 */
object Ex1 {

  /*
   * The important assumption made here is that the 'rates' data set is comprehensive,
   * i.e. there is no rate code in the prices data set for which the rate group would be
   * unknown. Otherwise, this method would have to be implemented defensively.
   */
  def bestCabinPrices(rates: Seq[Rate],
                      prices: Seq[CabinPrice]): Seq[BestCabinPrice] = {
    val ratesCodeToGroup = rates.map(rate => Rate.unapply(rate).get).toMap
    // Group cabin prices by tuples (rateGroup, cabinCode)
    val groupedPrices = prices.groupBy {
      price => (ratesCodeToGroup(price.rateCode), price.cabinCode)
    }
    val minPrices = groupedPrices.mapValues(_.sortWith((p1, p2) => p1.price <= p2.price).head)
    minPrices.map {
      case ((group, cabin), cabinPrice) =>
        BestCabinPrice(cabin, cabinPrice.rateCode, group, cabinPrice.price)
    }.toSeq
  }

}
