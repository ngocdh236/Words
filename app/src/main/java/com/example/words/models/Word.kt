package com.example.words

import androidx.room.Entity
import com.google.gson.annotations.SerializedName

//@Entity(primaryKeys = ["text"])
data class Word(
    @field:SerializedName("lang")
    val lang: String,
    @field:SerializedName("text")
    val text: String,
    @field:SerializedName("translations")
    private val translations: MutableSet<Word> = mutableSetOf()
) {

    val translationsSet: Set<Word>
        get() = translations

    fun addTranslation(t: Word) {
        translations.add(t)
    }

    fun addTranslations(ts: Set<Word>) {
        for (t in ts) this.addTranslation(t)
    }

    fun isTranslation(word: Word): Boolean {
        val langTranslations = (translations.filter { it.lang == word.lang })
        for (translation in langTranslations) {
            if (word.text == translation.text) return true
        }
        return false
    }

    fun translationCount(lang: String): Int {
        val langTranslations = (translations.filter { it.lang == lang })
        return langTranslations.count()
    }

    fun editDistance(another: Word): Int {
        val m = this.text.length
        val n = another.text.length

        val d: Array<IntArray> = Array(m + 1) { IntArray(n + 1) { 0 } } // set all (m+1) * (n+1) elements to zero

        for (i in 1..m) d[i][0] = i

        for (j in 1..n) d[0][j] = j

        for (j in 1..n) {
            for (i in 1..m) {
                var substitutionCost = 1
                if (this.text[i - 1] == another.text[j - 1]) substitutionCost = 0

                d[i][j] = minOf(
                    d[i - 1][j] + 1,
                    d[i][j - 1] + 1,
                    d[i - 1][j - 1] + substitutionCost
                )


            }
        }

        return d[m][n]
    }
}