/*
 * Copyright 2010-2020 JetBrains s.r.o. and Kotlin Programming Language contributors.
 * Use of this source code is governed by the Apache 2.0 license that can be found in the license/LICENSE.txt file.
 */

package kotlin.text

import kotlin.js.RegExp

@kotlin.internal.InlineOnly
internal actual inline fun String.nativeIndexOf(ch: Char, fromIndex: Int): Int = nativeIndexOf(ch.toString(), fromIndex)

@kotlin.internal.InlineOnly
internal actual inline fun String.nativeLastIndexOf(ch: Char, fromIndex: Int): Int = nativeLastIndexOf(ch.toString(), fromIndex)

/**
 * Returns `true` if this string starts with the specified prefix.
 */
@Suppress("ACTUAL_FUNCTION_WITH_DEFAULT_ARGUMENTS")
public actual fun String.startsWith(prefix: String, ignoreCase: Boolean = false): Boolean {
    if (!ignoreCase)
        return nativeStartsWith(prefix, 0)
    else
        return regionMatches(0, prefix, 0, prefix.length, ignoreCase)
}

/**
 * Returns `true` if a substring of this string starting at the specified offset [startIndex] starts with the specified prefix.
 */
@Suppress("ACTUAL_FUNCTION_WITH_DEFAULT_ARGUMENTS")
public actual fun String.startsWith(prefix: String, startIndex: Int, ignoreCase: Boolean = false): Boolean {
    if (!ignoreCase)
        return nativeStartsWith(prefix, startIndex)
    else
        return regionMatches(startIndex, prefix, 0, prefix.length, ignoreCase)
}

/**
 * Returns `true` if this string ends with the specified suffix.
 */
@Suppress("ACTUAL_FUNCTION_WITH_DEFAULT_ARGUMENTS")
public actual fun String.endsWith(suffix: String, ignoreCase: Boolean = false): Boolean {
    if (!ignoreCase)
        return nativeEndsWith(suffix)
    else
        return regionMatches(length - suffix.length, suffix, 0, suffix.length, ignoreCase)
}


public fun String.matches(regex: String): Boolean {
    val result = this.match(regex)
    return result != null && result.size != 0
}

public actual fun CharSequence.isBlank(): Boolean = length == 0 || (if (this is String) this else this.toString()).matches("^[\\s\\xA0]+$")

@OptIn(ExperimentalStdlibApi::class)
@Suppress("ACTUAL_FUNCTION_WITH_DEFAULT_ARGUMENTS")
public actual fun String?.equals(other: String?, ignoreCase: Boolean = false): Boolean =
    if (this == null)
        other == null
    else if (!ignoreCase)
        this == other
    else
        other != null && this.lowercase() == other.lowercase()


@Suppress("ACTUAL_FUNCTION_WITH_DEFAULT_ARGUMENTS")
public actual fun CharSequence.regionMatches(thisOffset: Int, other: CharSequence, otherOffset: Int, length: Int, ignoreCase: Boolean = false): Boolean =
    regionMatchesImpl(thisOffset, other, otherOffset, length, ignoreCase)


/**
 * Returns a copy of this string having its first letter titlecased using the rules of the default locale,
 * or the original string if it's empty or already starts with a title case letter.
 *
 * The title case of a character is usually the same as its upper case with several exceptions.
 * The particular list of characters with the special title case form depends on the underlying platform.
 */
public actual fun String.capitalize(): String {
    return if (isNotEmpty()) substring(0, 1).toUpperCase() + substring(1) else this
}

/**
 * Returns a copy of this string having its first letter titlecased using Unicode mapping rules of the invariant locale,
 * or the original string if it's empty or already starts with a title case letter.
 *
 * @sample samples.text.Strings.capitalizeFirst
 */
@SinceKotlin("1.4")
@ExperimentalStdlibApi
public actual fun String.capitalizeFirst(): String {
    return if (isNotEmpty()) substring(0, 1).uppercase() + substring(1) else this
}

/**
 * Returns a copy of this string having its first letter lowercased using the rules of the default locale,
 * or the original string if it's empty or already starts with a lower case letter.
 */
public actual fun String.decapitalize(): String {
    return if (isNotEmpty()) substring(0, 1).toLowerCase() + substring(1) else this
}

/**
 * Returns a copy of this string having its first letter lowercased using Unicode mapping rules of the invariant locale,
 * or the original string if it's empty or already starts with a lower case letter.
 *
 * @sample samples.text.Strings.decapitalizeFirst
 */
@SinceKotlin("1.4")
@ExperimentalStdlibApi
public actual fun String.decapitalizeFirst(): String {
    return if (isNotEmpty()) substring(0, 1).lowercase() + substring(1) else this
}

/**
 * Returns a string containing this char sequence repeated [n] times.
 * @throws [IllegalArgumentException] when n < 0.
 */
public actual fun CharSequence.repeat(n: Int): String {
    require(n >= 0) { "Count 'n' must be non-negative, but was $n." }
    return when (n) {
        0 -> ""
        1 -> this.toString()
        else -> {
            var result = ""
            if (!isEmpty()) {
                var s = this.toString()
                var count = n
                while (true) {
                    if ((count and 1) == 1) {
                        result += s
                    }
                    count = count ushr 1
                    if (count == 0) {
                        break
                    }
                    s += s
                }
            }
            return result
        }
    }
}

@Suppress("ACTUAL_FUNCTION_WITH_DEFAULT_ARGUMENTS")
public actual fun String.replace(oldValue: String, newValue: String, ignoreCase: Boolean = false): String =
    nativeReplace(RegExp(Regex.escape(oldValue), if (ignoreCase) "gi" else "g"), Regex.escapeReplacement(newValue))

@Suppress("ACTUAL_FUNCTION_WITH_DEFAULT_ARGUMENTS")
public actual fun String.replace(oldChar: Char, newChar: Char, ignoreCase: Boolean = false): String =
    nativeReplace(RegExp(Regex.escape(oldChar.toString()), if (ignoreCase) "gi" else "g"), newChar.toString())

@Suppress("ACTUAL_FUNCTION_WITH_DEFAULT_ARGUMENTS")
public actual fun String.replaceFirst(oldValue: String, newValue: String, ignoreCase: Boolean = false): String =
    nativeReplace(RegExp(Regex.escape(oldValue), if (ignoreCase) "i" else ""), Regex.escapeReplacement(newValue))

@Suppress("ACTUAL_FUNCTION_WITH_DEFAULT_ARGUMENTS")
public actual fun String.replaceFirst(oldChar: Char, newChar: Char, ignoreCase: Boolean = false): String =
    nativeReplace(RegExp(Regex.escape(oldChar.toString()), if (ignoreCase) "i" else ""), newChar.toString())
