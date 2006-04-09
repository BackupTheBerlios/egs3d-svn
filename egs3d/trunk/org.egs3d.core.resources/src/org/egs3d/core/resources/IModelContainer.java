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


/**
 * Conteneur pour {@link IModel}.
 * 
 * @author romale
 */
public interface IModelContainer extends Iterable<IModel> {
    /**
     * Retourne l'instance {@link IScene} associ�e � ce conteneur.
     */
    IScene getScene();


    /**
     * Ajoute un objet {@link IModel}.
     */
    void add(IModel model);


    /**
     * Enl�ve un objet {@link IModel}.
     */
    void remove(IModel model);


    /**
     * Retourne le nombre d'�l�ments {@link IModel} contenus.
     * 
     * @return
     */
    int getSize();


    /**
     * Retourne un objet {@link IModel} � l'indice <code>i</code>.
     * 
     * @throws IndexOutOfBoundsException si l'indice est incorrect
     */
    IModel getModel(int i);
}
