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

package org.egs3d.core.resources;


import java.nio.ByteBuffer;

import javax.media.j3d.BranchGroup;

import com.sun.j3d.loaders.Loader;


/**
 * Mod�le 3D.
 * 
 * @author romale
 */
public interface IModel extends ISceneObject {
    /**
     * Retourne le nom du mod�le.
     */
    String getName();


    /**
     * Retourne le mod�le sous la forme d'un noeud.
     */
    BranchGroup getBranchGroup();


    /**
     * Retourne le mod�le sous la forme de donn�es binaires.
     */
    ByteBuffer getBinaryData();


    /**
     * Retourne l'extension du mod�le.
     */
    String getExtension();


    /**
     * Retourne le nom de la classe utilis�e pour charger le mod�le.
     */
    Class<? extends Loader> getLoaderClass();
}
