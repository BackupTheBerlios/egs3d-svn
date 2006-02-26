/**
 * $Id: BLC.java,v 1.6 2005/11/28 16:08:16 romale Exp $
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

package org.egs3d.core.resources;


import org.egs3d.core.resources.internal.MockSceneGraphReader;
import org.egs3d.core.resources.internal.MockSceneGraphWriter;
import org.egs3d.core.resources.internal.SceneGraphReader;
import org.egs3d.core.resources.internal.SceneGraphWriter;


/**
 * Lecture et enregistrement d'arbre scénique.
 * 
 * @author romale
 */
public final class SceneGraphFactory {
    /**
     * Mettre ce champ à true pour utiliser des lecteurs/enregistreurs de test.
     */
    private static final boolean USE_MOCK = true;


    private SceneGraphFactory() {
    }


    /**
     * Fournit une implémentation de l'interface {@link ISceneGraphReader}.
     */
    public static ISceneGraphReader createReader() {
        if (USE_MOCK) {
            return new MockSceneGraphReader();
        }
        return new SceneGraphReader();
    }


    /**
     * Fournit une implémentation de l'interface {@link ISceneGraphWriter}.
     */
    public static ISceneGraphWriter createWriter() {
        if (USE_MOCK) {
            return new MockSceneGraphWriter();
        }
        return new SceneGraphWriter();
    }
}
