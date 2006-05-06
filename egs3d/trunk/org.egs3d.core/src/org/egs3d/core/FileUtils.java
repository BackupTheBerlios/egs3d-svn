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
 * Méthodes utilitaires sur des fichiers.
 * 
 * @author romale
 */
public final class FileUtils {
    private FileUtils() {
    }


    /**
     * Retourne l'extension d'un nom de fichier. Si le fichier n'a pas
     * d'extension, la valeur <code>null</code> est retournée.
     */
    public static String getExtension(String fileName) {
        if (fileName == null) {
            return null;
        }
        final int i = fileName.lastIndexOf('.');
        if (i == -1 || i == fileName.length() - 1) {
            return null;
        }
        return fileName.substring(i + 1);
    }
}
