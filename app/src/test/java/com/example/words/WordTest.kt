/*
Name: Doan Ngoc
Student ID: 1605449
*/

import com.example.words.Word
import org.junit.Assert.*
import org.junit.Test

class WordTest {
    @Test
    fun create() {
        val w = Word("Finnish", "kaivo")
        assertEquals(w.text, "kaivo")
        assertEquals(w.lang, "Finnish")
        assertEquals(0, w.translationCount("English"))
    }

    @Test
    fun addTranslationIdentical() {
        val w = Word("Finnish", "kaivo")
        w.addTranslation(Word("English", "well"))
        assertEquals(true, w.isTranslation(Word("English", "well")))
    }

    @Test
    fun addTranslationDifferentLanguage() {
        val w = Word("Finnish", "kaivo")
        w.addTranslation(Word("English", "well"))
        assertEquals(false, w.isTranslation(Word("Swedish", "well")))
    }

    @Test
    fun addTranslationsNonEmpty() {
        val w = Word("Finnish", "well")
        w.addTranslations(setOf(Word("English", "well")))
        assertEquals(true, w.isTranslation(Word("English", "well")))
    }

    @Test
    fun addTranslationsEmpty() {
        val w = Word("Finnish", "kaivo")
        w.addTranslations(setOf())
        assertEquals(false, w.isTranslation(Word("English", "well")))
    }

    @Test
    fun addTranslationDifferent() {
        val w = Word("Finnish", "kaivo")
        w.addTranslation(Word("English", "well"))
        assertEquals(false, w.isTranslation(Word("English", "sell")))
    }

    @Test
    fun translationCountEmpty() {
        val w = Word("Finnish", "kaivo")
        assertEquals(0, w.translationCount("English"))
    }

    @Test
    fun translationCountOne() {
        val w = Word("Finnish", "kaivo")
        w.addTranslation(Word("English", "well"))
        assertEquals(1, w.translationCount("English"))
    }

    @Test
    fun translationCountMany() {
        val w = Word("Finnish", "kaivo")
        w.addTranslation(Word("English", "well"))
        w.addTranslation(Word("English", "spring"))
        assertEquals(2, w.translationCount("English"))
    }

    @Test
    fun distanceIdentical() {
        val w = Word("Finnish", "kaivo")
        assertEquals(0, w.editDistance(Word("Finnish", "kaivo")))
    }

    @Test
    fun distanceEmpty1() {
        val w = Word("Finnish", "kaivo")
        assertEquals(5, w.editDistance(Word("Finnish", "")))
    }

    @Test
    fun distanceEmpty2() {
        val w = Word("Finnish", "")
        assertEquals(5, w.editDistance(Word("Finnish", "kaivo")))
    }

    @Test
    fun distance1change() {
        val w = Word("Finnish", "kaivo")
        assertEquals(1, w.editDistance(Word("Finnish", "raivo")))
    }

    @Test
    fun distance1delete() {
        val w = Word("Finnish", "kaivo")
        assertEquals(1, w.editDistance(Word("Finnish", "aivo")))
    }

    @Test
    fun distance1add() {
        val w = Word("Finnish", "kaivo")
        assertEquals(1, w.editDistance(Word("Finnish", "kaivot")))
    }

    @Test
    fun distance1add1delete() {
        val w = Word("Finnish", "kaivo")
        assertEquals(2, w.editDistance(Word("Finnish", "aivot")))
    }

    @Test
    fun distanceManyAdd() {
        val w = Word("Finnish", "kaivo")
        assertEquals(12, w.editDistance(Word("Finnish", "vesikaivotkinkaan")))
    }

    @Test
    fun translationsContent() {
        val w = Word("Finnish", "abcde")
        w.addTranslation(Word("Finnish", "aaaa"))
        w.addTranslation(Word("Finnish", "abcd"))
        w.addTranslation(Word("Finnish", "abab"))
        w.addTranslation(Word("Swedish", "aaaa"))
        w.addTranslation(Word("Finnish", "aaaae"))
        assertEquals(
            true,
            w.isTranslation(Word("Finnish", "aaaa"))
                    && w.isTranslation(Word("Finnish", "abcd"))
                    && w.isTranslation(Word("Finnish", "abab"))
                    && w.isTranslation(Word("Swedish", "aaaa"))
                    && w.isTranslation(Word("Finnish", "aaaae"))
        )

    }
}