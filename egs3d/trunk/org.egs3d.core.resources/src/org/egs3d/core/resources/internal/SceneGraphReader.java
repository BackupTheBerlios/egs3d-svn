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


import java.io.File;
import java.io.IOException;

import org.egs3d.core.resources.ISceneGraphReader;

import com.sun.j3d.utils.scenegraph.io.SceneGraphFileReader;
import com.sun.j3d.utils.universe.SimpleUniverse;


/**
 * Implémentation du lecteur d'arbre scénique.
 * 
 * @author romale
 */
public class SceneGraphReader implements ISceneGraphReader {
    public SimpleUniverse read(File file) throws IOException {
        final SceneGraphFileReader reader = new SceneGraphFileReader(file);
        try {
            return reader.readUniverse(true);
        } finally {
            try {
                reader.close();
            } catch (Exception ignore) {
            }
        }
    }
}
