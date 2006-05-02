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

package org.egs3d.ui.internal;


import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;

import javax.imageio.ImageIO;

import org.eclipse.swt.graphics.ImageData;


/**
 * Méthodes utilitaires pour SWT.
 * 
 * @author romale
 */
public final class SWTUtils {
    private SWTUtils() {
    }


    /**
     * Convertit une image {@link BufferedImage} en une image SWT.
     */
    public static ImageData convertToSWT(BufferedImage bufferedImage) {
        // TODO implémenter un algorithme plus rapide pour la conversion
        // (ne plus passer par ImageIO !)

        final ByteArrayOutputStream buf = new ByteArrayOutputStream();
        try {
            ImageIO.write(bufferedImage, "PNG", buf);
        } catch (Exception e) {
            throw new IllegalStateException("Erreur lors de la conversion de l'image", e);
        }
        return new ImageData(new ByteArrayInputStream(buf.toByteArray()));
    }
}
