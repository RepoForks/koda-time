package com.github.debop.kodatimes.ranges

import com.github.debop.kodatimes.*
import org.joda.time.Duration
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertNotEquals
import org.junit.jupiter.api.Assertions.assertThrows
import org.junit.jupiter.api.Test

class DateTimeProgressTest : AbstractKodaTimesTest() {

  @Test fun `create simple`() {
    val start = today()
    val endInclusive = start + 1.days()

    val progression = DateTimeProgression.fromClosedRange(start, endInclusive, standardHours(1))

    assertEquals(start, progression.first)
    assertEquals(endInclusive, progression.last)
    assertEquals(standardHours(1), progression.step)

    val list = progression.toList()
    assertEquals(25, list.count())
  }

  @Test fun `zero step`() {
    val instant = now()

    assertThrows(IllegalArgumentException::class.java) {
      DateTimeProgression.fromClosedRange(instant, instant, Duration(0))
    }
  }

  @Test fun `step greater than range`() {
    val start = today()
    val endInclusive = start + 1.days()

    val progression = DateTimeProgression.fromClosedRange(start, endInclusive, standardDays(7))

    assertEquals(start, progression.first)
    // last is not endInclusive, step over endInclusive, so last is equal to start
    assertEquals(start, progression.last)
    assertEquals(7.dayDuration(), progression.step)

    val list = progression.toList()
    assertEquals(1, list.count())
  }

  @Test fun `stepping not exact endInclusive`() {
    val start = today()
    val endInclusive = start + 1.days()

    val progression = DateTimeProgression.fromClosedRange(start, endInclusive, 5.hourDuration())

    assertEquals(start, progression.first)
    assertEquals(start + 20.hours(), progression.last)
    assertNotEquals(endInclusive, progression.last)

    val list = progression.toList()
    assertEquals(5, list.count())
    assertEquals(listOf(0, 5, 10, 15, 20), list.map { it.hourOfDay })
  }

  @Test fun `downTo progression`() {
    val start = today()
    val endInclusive = start - 5.days()
    val step = dayDurationOf(-1)

    val progression = DateTimeProgression.fromClosedRange(start, endInclusive, step)

    assertEquals(start, progression.first)
    assertEquals(endInclusive, progression.last)

    assertEquals("$start downTo $endInclusive step ${-step}", progression.toString())

    val list = progression.toList()
    assertEquals(6, list.count())
    assertEquals(start, list.first())
    assertEquals(endInclusive, list.last())
  }

}