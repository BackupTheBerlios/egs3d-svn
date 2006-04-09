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

import org.egs3d.core.resources.IScene;
import org.egs3d.core.resources.ISceneWriter;


/**
 * Enregistrement d'arbre sc�nique de test.
 * 
 * @author romale
 */
public class MockSceneWriter implements ISceneWriter {
    /**
     * Cette m�thode ne fait rien.
     */
    public void write(File file, IScene universe) throws IOException {
        // rien � faire !
    }
}
