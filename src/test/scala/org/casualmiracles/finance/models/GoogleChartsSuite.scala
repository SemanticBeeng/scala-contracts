package org.casualmiracles.finance.models

import org.scalatest._
import Matchers._

import ExampleModel._
import org.casualmiracles.finance.contracts._
import PR._

class GoogleChartsSuite extends FunSuite with Matchers {
  
  test("Pr1 Line Chart Url") {
    val pr1 = PR( Stream[RV[Double]]( 
        Stream[Double](8.641), 
        Stream[Double](9.246,8.901),
        Stream[Double](9.709,9.524,9.346), 
        Stream[Double](10,10,10,10)
      ) )
      
      val gcs = GoogleCharts.chartUrl( expectedValuePr( pr1 ) ) 
      
      print( gcs )
      val gcts = "http://chart.apis.google.com/chart?"+
                 "chs=300x200&cht=lc&chxt=x,y"+
                 "&chg=20,25,2,5" +
                 "&chxr=0,0,3|1,8.6,10.0"+
                 "&chd=t:0.0,31.8,65.1,100.0"

      gcs should equal ( gcts )
  }
}