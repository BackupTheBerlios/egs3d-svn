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


import java.io.FilterOutputStream;
import java.io.IOException;
import java.io.OutputStream;


/**
 * Adapteur pour une classe de type {@link OutputStream} qui empêche la
 * fermeture du flux.
 * 
 * @author romale
 */
public class UncloseableOutputStream extends FilterOutputStream {
    public UncloseableOutputStream(final OutputStream outputStream) {
        super(outputStream);
    }


    @Override
    public void close() throws IOException {
    }
}
