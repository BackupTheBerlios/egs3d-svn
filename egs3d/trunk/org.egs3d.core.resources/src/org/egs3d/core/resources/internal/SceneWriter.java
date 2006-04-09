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

import javax.media.j3d.BranchGroup;
import javax.media.j3d.Canvas3D;

import org.egs3d.core.resources.IScene;
import org.egs3d.core.resources.ISceneWriter;

import com.sun.j3d.utils.scenegraph.io.SceneGraphFileWriter;
import com.sun.j3d.utils.scenegraph.io.UnsupportedUniverseException;
import com.sun.j3d.utils.universe.SimpleUniverse;


/**
 * Implémentation de l'enregistreur d'arbre scénique.
 * 
 * @author romale
 */
public class SceneWriter implements ISceneWriter {
    public void write(File file, IScene scene) throws IOException {
        SceneGraphFileWriter writer = null;
        try {
            final SimpleUniverse su = new SimpleUniverse(new Canvas3D(SimpleUniverse
                    .getPreferredConfiguration()));
            for (final BranchGroup bg : scene.getBranchGroupContainer()) {
                su.addBranchGraph(bg);
            }
            writer = new SceneGraphFileWriter(file, su, true, "EGS3D", null);
        } catch (UnsupportedUniverseException e) {
            final IOException exc = new IOException("Unsupported universe");
            exc.initCause(e);
            throw exc;
        } finally {
            if (writer != null) {
                try {
                    writer.close();
                } catch (Exception ignore) {
                }
            }
        }
    }
}
