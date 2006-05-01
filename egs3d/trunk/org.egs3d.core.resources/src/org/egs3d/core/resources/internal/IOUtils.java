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

package org.egs3d.core.resources.internal;


import java.io.Closeable;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;


/**
 * Méthodes utilitaires d'entrées/sorties.
 * 
 * @author romale
 */
public final class IOUtils {
    private IOUtils() {
    }


    /**
     * Copie un flux d'entrée vers un flux de sortie. Les flux sont fermés à la
     * fin de l'opération.
     */
    public static void copy(InputStream input, OutputStream output) throws IOException {
        final byte[] buf = new byte[1024];

        try {
            for (int bytesRead = 0; (bytesRead = input.read(buf)) != -1;) {
                output.write(buf, 0, bytesRead);
            }
        } finally {
            close(input);
            close(output);
        }
    }


    /**
     * Ferme un objet implémentant l'interface {@link Closeable} sans lever
     * d'exception.
     */
    public static void close(Closeable closeable) {
        if (closeable == null) {
            return;
        }
        try {
            closeable.close();
        } catch (IOException ignore) {
        }
    }
}
