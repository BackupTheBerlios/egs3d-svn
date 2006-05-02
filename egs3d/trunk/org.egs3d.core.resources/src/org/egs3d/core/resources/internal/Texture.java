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


import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;

import javax.imageio.ImageIO;

import org.egs3d.core.resources.ITexture;


/**
 * Implémentation de l'interface {@link ITexture}.
 * 
 * @author romale
 */
public class Texture implements ITexture {
    private final String name;
    private final BufferedImage data;


    public Texture(final String name, final InputStream input) {
        this.name = name;

        try {
            data = ImageIO.read(input);
        } catch (IOException e) {
            throw new IllegalStateException("Erreur durant le chargement de la texture",
                    e);
        }
    }


    public String getName() {
        return name;
    }


    public BufferedImage getImage() {
        return data;
    }

}
