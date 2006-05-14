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

package org.egs3d.codegen;


/**
 * Générateur de code.
 * 
 * @author romale
 */
public interface ICodeGenerator {
    /**
     * Génère du code à partir d'un objet. Si l'objet n'est pas supporté par le
     * générateur, la valeur <code>null</code> est renvoyée.
     */
    CharSequence generate(Object obj);


    /**
     * Teste si un objet peut être transformé en code.
     */
    boolean isSupported(Object obj);
}
