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


import javax.media.j3d.BranchGroup;


/**
 * Conteneur pour {@link BranchGroup}.
 * 
 * @author romale
 */
public interface IBranchGroupContainer extends Iterable<BranchGroup>, ISceneObject {
    /**
     * Retourne l'instance {@link IScene} associée à ce conteneur.
     */
    IScene getScene();


    /**
     * Ajoute un objet {@link BranchGroup}.
     */
    void add(BranchGroup branchGroup);


    /**
     * Enlève un objet {@link BranchGroup}.
     */
    void remove(BranchGroup branchGroup);


    /**
     * Retourne le nombre d'éléments {@link BranchGroup} contenus.
     */
    int getSize();


    /**
     * Retourne un objet {@link BranchGroup} à l'indice <code>i</code>.
     * 
     * @throws IndexOutOfBoundsException si l'indice est incorrect
     */
    BranchGroup getBranchGroup(int i);


    /**
     * Retourne un tableau avec les éléments contenus.
     */
    BranchGroup[] toArray();


    /**
     * Rafraîchit le conteneur suite à une modification structurelle.
     */
    void refresh();
}
