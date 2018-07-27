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

object Ex1 {

  def bestCabinPrices(rates: Seq[Rate],
                      prices: Seq[CabinPrice]): Seq[BestCabinPrice] = Seq()

}
