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


/**
 * Types d'image utilisée dans le plugin.
 * 
 * @author romale
 */
public enum ImageType {
    OPENED_PROJECT("prj_obj.gif"), CLOSED_PROJECT("cprj_obj.gif"), FILE("file_obj.gif"), FOLDER(
            "fldr_obj.gif"), BRANCH_GROUP("bgrp_obj.gif"), TRANSFORM_GROUP("tgrp_obj.gif"), TEXTURE(
            "txtr_obj.gif"), MODEL("mdl_obj.gif"), LOCALE("lcl_obj.gif"), SHAPE(
            "shp_obj.gif"), LIGHT("lgt_obj.gif"), BACKGROUND("bgd_obj.gif"), ;

    private static final String PREFIX_PATH = "/icons/obj16/"; //$NON-NLS-1$
    private final String path;


    private ImageType(final String path) {
        this.path = PREFIX_PATH + path;
    }


    /**
     * Retourne le chemin vers l'image (relatif au plugin).
     */
    public String path() {
        return path;
    }
}
