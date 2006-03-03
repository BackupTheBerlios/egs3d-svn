/**
 * $Id$
 *
 * EGS3D
 * http://egs3d.berlios.de
 * Copyright (c) 2006 EGS3D team
 *
 * This program is free software; you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation; either version 2 of the License, or
 * (at your option) any later version.

 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.

 * You should have received a copy of the GNU General Public License
 * along with this program; if not, write to the Free Software
 * Foundation, Inc., 51 Franklin St, Fifth Floor, Boston, MA  02110-1301  USA
 */

package org.egs3d.core;


/**
 * Méthodes utilitaires sur des {@link String}.
 * 
 * @author romale
 */
public final class StringUtils {
    /**
     * Constante de chaîne vide.
     */
    public static final String EMPTY_STRING = "";


    /**
     * Constructeur privé pour empêcher l'instanciation.
     */
    private StringUtils() {
    }


    /**
     * Supprime les espaces en début et en fin de chaîne. Si la chaîne de départ
     * est <code>null</code> ou si le résultat est une chaîne vide, retourne
     * <code>null</code>.
     */
    public static String trimToNull(String str) {
        if (str == null) {
            return null;
        }
        final String newStr = str.trim();
        return newStr.length() == 0 ? null : newStr;
    }


    /**
     * Retourne <code>true</code> si une chaîne est <code>null</code> ou
     * vide (zéro caractère ou que des espaces).
     */
    public static boolean isBlank(String str) {
        return trimToNull(str) == null;
    }
}
