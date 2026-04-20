package com.watchthis.business

/**
 * Heuristic: Ukrainian field should be mostly Cyrillic; English field mostly Latin letters.
 */
object WordScriptValidator {

    fun looksLikeUkrainianScript(text: String): Boolean {
        val letters = text.filter { it.isLetter() }
        if (letters.isEmpty()) return false
        var cyrillic = 0
        var latin = 0
        for (c in letters) {
            when {
                isCyrillic(c) -> cyrillic++
                isLatinLetter(c) -> latin++
            }
        }
        if (cyrillic == 0) return false
        return cyrillic > latin
    }

    fun looksLikeEnglishScript(text: String): Boolean {
        val letters = text.filter { it.isLetter() }
        if (letters.isEmpty()) return false
        var cyrillic = 0
        var latin = 0
        for (c in letters) {
            when {
                isCyrillic(c) -> cyrillic++
                isLatinLetter(c) -> latin++
            }
        }
        if (latin == 0) return false
        return latin > cyrillic
    }

    private fun isCyrillic(c: Char): Boolean =
        Character.UnicodeBlock.of(c) == Character.UnicodeBlock.CYRILLIC

    private fun isLatinLetter(c: Char): Boolean {
        val b = Character.UnicodeBlock.of(c)
        return b == Character.UnicodeBlock.BASIC_LATIN ||
            b == Character.UnicodeBlock.LATIN_EXTENDED_A ||
            b == Character.UnicodeBlock.LATIN_EXTENDED_B ||
            b == Character.UnicodeBlock.LATIN_EXTENDED_ADDITIONAL
    }
}
