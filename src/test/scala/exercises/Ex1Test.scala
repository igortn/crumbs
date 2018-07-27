package exercises

import exercises.Ex1._

import org.scalatest.FunSuite

class Ex1Test extends FunSuite {

  val rates: Seq[Rate] = Seq(
    Rate("M1", "Military"),
    Rate("M2", "Military"),
    Rate("S1", "Senior"),
    Rate("S2", "Senior")
  )

  val cabinPrices: Seq[CabinPrice] = Seq(
    CabinPrice("CA", "M1", 200.00),
    CabinPrice("CA", "M2", 250.00),
    CabinPrice("CA", "S1", 225.00),
    CabinPrice("CA", "S2", 260.00),
    CabinPrice("CB", "M1", 230.00),
    CabinPrice("CB", "M2", 260.00),
    CabinPrice("CB", "S1", 245.00),
    CabinPrice("CB", "S2", 270.00)
  )

  val expectedBestCabinPrices: Seq[BestCabinPrice] = Seq(
    BestCabinPrice("CA", "M1", "Military", 200.00),
    BestCabinPrice("CA", "S1", "Senior", 225.00),
    BestCabinPrice("CB", "M1", "Military", 230.00),
    BestCabinPrice("CB", "S1", "Senior", 245.00)
  )

  test("Best cabin prices calculated correctly.") {
    assert(bestCabinPrices(rates, cabinPrices) === expectedBestCabinPrices)
  }

}
