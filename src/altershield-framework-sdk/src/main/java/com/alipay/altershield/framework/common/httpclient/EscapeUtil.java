/*
 * MIT License
 *
 * Copyright (c) [2023]
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 */
package com.alipay.altershield.framework.common.httpclient;

public class EscapeUtil {

    private static final String[][] BASIC_ESCAPE = {{"&", "&amp;"}, // & - ampersand
            {"<", "&lt;"}, // < - less-than
            {">", "&gt;"} // > - greater-than
    };

    private static final String[][] ISO8859_1_ESCAPE = {{"\u00A0", "&nbsp;"}, // non-breaking space
            {"\u00A1", "&iexcl;"}, // inverted exclamation mark
            {"\u00A2", "&cent;"}, // cent sign
            {"\u00A3", "&pound;"}, // pound sign
            {"\u00A4", "&curren;"}, // currency sign
            {"\u00A5", "&yen;"}, // yen sign = yuan sign
            {"\u00A6", "&brvbar;"}, // broken bar = broken vertical bar
            {"\u00A7", "&sect;"}, // section sign
            {"\u00A8", "&uml;"}, // diaeresis = spacing diaeresis
            {"\u00A9", "&copy;"}, // - copyright sign
            {"\u00AA", "&ordf;"}, // feminine ordinal indicator
            {"\u00AB", "&laquo;"}, // left-pointing double angle quotation mark = left pointing
            // guillemet
            {"\u00AC", "&not;"}, // not sign
            {"\u00AD", "&shy;"}, // soft hyphen = discretionary hyphen
            {"\u00AE", "&reg;"}, // - registered trademark sign
            {"\u00AF", "&macr;"}, // macron = spacing macron = overline = APL overbar
            {"\u00B0", "&deg;"}, // degree sign
            {"\u00B1", "&plusmn;"}, // plus-minus sign = plus-or-minus sign
            {"\u00B2", "&sup2;"}, // superscript two = superscript digit two = squared
            {"\u00B3", "&sup3;"}, // superscript three = superscript digit three = cubed
            {"\u00B4", "&acute;"}, // acute accent = spacing acute
            {"\u00B5", "&micro;"}, // micro sign
            {"\u00B6", "&para;"}, // pilcrow sign = paragraph sign
            {"\u00B7", "&middot;"}, // middle dot = Georgian comma = Greek middle dot
            {"\u00B8", "&cedil;"}, // cedilla = spacing cedilla
            {"\u00B9", "&sup1;"}, // superscript one = superscript digit one
            {"\u00BA", "&ordm;"}, // masculine ordinal indicator
            {"\u00BB", "&raquo;"}, // right-pointing double angle quotation mark = right pointing
            // guillemet
            {"\u00BC", "&frac14;"}, // vulgar fraction one quarter = fraction one quarter
            {"\u00BD", "&frac12;"}, // vulgar fraction one half = fraction one half
            {"\u00BE", "&frac34;"}, // vulgar fraction three quarters = fraction three quarters
            {"\u00BF", "&iquest;"}, // inverted question mark = turned question mark
            {"\u00C0", "&Agrave;"}, // - uppercase A, grave accent
            {"\u00C1", "&Aacute;"}, // - uppercase A, acute accent
            {"\u00C2", "&Acirc;"}, // - uppercase A, circumflex accent
            {"\u00C3", "&Atilde;"}, // - uppercase A, tilde
            {"\u00C4", "&Auml;"}, // - uppercase A, umlaut
            {"\u00C5", "&Aring;"}, // - uppercase A, ring
            {"\u00C6", "&AElig;"}, // - uppercase AE
            {"\u00C7", "&Ccedil;"}, // - uppercase C, cedilla
            {"\u00C8", "&Egrave;"}, // - uppercase E, grave accent
            {"\u00C9", "&Eacute;"}, // - uppercase E, acute accent
            {"\u00CA", "&Ecirc;"}, // - uppercase E, circumflex accent
            {"\u00CB", "&Euml;"}, // - uppercase E, umlaut
            {"\u00CC", "&Igrave;"}, // - uppercase I, grave accent
            {"\u00CD", "&Iacute;"}, // - uppercase I, acute accent
            {"\u00CE", "&Icirc;"}, // - uppercase I, circumflex accent
            {"\u00CF", "&Iuml;"}, // - uppercase I, umlaut
            {"\u00D0", "&ETH;"}, // - uppercase Eth, Icelandic
            {"\u00D1", "&Ntilde;"}, // - uppercase N, tilde
            {"\u00D2", "&Ograve;"}, // - uppercase O, grave accent
            {"\u00D3", "&Oacute;"}, // - uppercase O, acute accent
            {"\u00D4", "&Ocirc;"}, // - uppercase O, circumflex accent
            {"\u00D5", "&Otilde;"}, // - uppercase O, tilde
            {"\u00D6", "&Ouml;"}, // - uppercase O, umlaut
            {"\u00D7", "&times;"}, // multiplication sign
            {"\u00D8", "&Oslash;"}, // - uppercase O, slash
            {"\u00D9", "&Ugrave;"}, // - uppercase U, grave accent
            {"\u00DA", "&Uacute;"}, // - uppercase U, acute accent
            {"\u00DB", "&Ucirc;"}, // - uppercase U, circumflex accent
            {"\u00DC", "&Uuml;"}, // - uppercase U, umlaut
            {"\u00DD", "&Yacute;"}, // - uppercase Y, acute accent
            {"\u00DE", "&THORN;"}, // - uppercase THORN, Icelandic
            {"\u00DF", "&szlig;"}, // - lowercase sharps, German
            {"\u00E0", "&agrave;"}, // - lowercase a, grave accent
            {"\u00E1", "&aacute;"}, // - lowercase a, acute accent
            {"\u00E2", "&acirc;"}, // - lowercase a, circumflex accent
            {"\u00E3", "&atilde;"}, // - lowercase a, tilde
            {"\u00E4", "&auml;"}, // - lowercase a, umlaut
            {"\u00E5", "&aring;"}, // - lowercase a, ring
            {"\u00E6", "&aelig;"}, // - lowercase ae
            {"\u00E7", "&ccedil;"}, // - lowercase c, cedilla
            {"\u00E8", "&egrave;"}, // - lowercase e, grave accent
            {"\u00E9", "&eacute;"}, // - lowercase e, acute accent
            {"\u00EA", "&ecirc;"}, // - lowercase e, circumflex accent
            {"\u00EB", "&euml;"}, // - lowercase e, umlaut
            {"\u00EC", "&igrave;"}, // - lowercase i, grave accent
            {"\u00ED", "&iacute;"}, // - lowercase i, acute accent
            {"\u00EE", "&icirc;"}, // - lowercase i, circumflex accent
            {"\u00EF", "&iuml;"}, // - lowercase i, umlaut
            {"\u00F0", "&eth;"}, // - lowercase eth, Icelandic
            {"\u00F1", "&ntilde;"}, // - lowercase n, tilde
            {"\u00F2", "&ograve;"}, // - lowercase o, grave accent
            {"\u00F3", "&oacute;"}, // - lowercase o, acute accent
            {"\u00F4", "&ocirc;"}, // - lowercase o, circumflex accent
            {"\u00F5", "&otilde;"}, // - lowercase o, tilde
            {"\u00F6", "&ouml;"}, // - lowercase o, umlaut
            {"\u00F7", "&divide;"}, // division sign
            {"\u00F8", "&oslash;"}, // - lowercase o, slash
            {"\u00F9", "&ugrave;"}, // - lowercase u, grave accent
            {"\u00FA", "&uacute;"}, // - lowercase u, acute accent
            {"\u00FB", "&ucirc;"}, // - lowercase u, circumflex accent
            {"\u00FC", "&uuml;"}, // - lowercase u, umlaut
            {"\u00FD", "&yacute;"}, // - lowercase y, acute accent
            {"\u00FE", "&thorn;"}, // - lowercase thorn, Icelandic
            {"\u00FF", "&yuml;"}, // - lowercase y, umlaut
    };

    /**
     * Reverse the string that has been HTML escaped.
     *
     * @param html not null
     * @return
     */
    public static String unescapeHtmlEncodedStr(String html) {
        for (String[] item : BASIC_ESCAPE) {
            html = html.replace(item[1], item[0]);
        }
        for (String[] item : ISO8859_1_ESCAPE) {
            html = html.replace(item[1], item[0]);
        }
        return html;
    }

}
