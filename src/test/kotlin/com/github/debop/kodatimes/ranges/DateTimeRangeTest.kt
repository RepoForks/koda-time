package com.github.debop.kodatimes.ranges

import com.github.debop.kodatimes.AbstractKodaTimesTest
import com.github.debop.kodatimes.days
import com.github.debop.kodatimes.today
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.Test

class DateTimeRangeTest : AbstractKodaTimesTest() {

  @Test fun `simple creation`() {

    val start = today()
    val endInclusive = start + 5.days()
    val range = DateTimeRange(start, endInclusive)

    assertEquals(start, range.start)
    assertEquals(endInclusive, range.endInclusive)

    assertEquals(start, range.first)
    assertEquals(endInclusive, range.last)

    assertEquals("$start..$endInclusive", range.toString())
  }

  @Test fun `empty range`() {

    val start = today()
    val endInclusive = start - 1.days()

    val range = DateTimeRange(start, endInclusive)
    assertTrue { range.isEmpty() }

    assertEquals(DateTimeRange.EMPTY, range)
  }

}