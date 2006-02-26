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

package org.egs3d.core.resources.internal;


import java.io.File;
import java.io.IOException;

import javax.media.j3d.Alpha;
import javax.media.j3d.BoundingSphere;
import javax.media.j3d.BranchGroup;
import javax.media.j3d.RotationInterpolator;
import javax.media.j3d.TransformGroup;
import javax.media.j3d.VirtualUniverse;

import org.egs3d.core.resources.ISceneGraphReader;

import com.sun.j3d.utils.geometry.ColorCube;
import com.sun.j3d.utils.universe.SimpleUniverse;


/**
 * Lecteur d'arbre scénique de test.
 * 
 * @author romale
 */
public class MockSceneGraphReader implements ISceneGraphReader {
    /**
     * Renvoie un arbre scénique contenant un cube tournant sur lui-même.
     */
    public VirtualUniverse read(File file) throws IOException {
        final SimpleUniverse su = new SimpleUniverse();

        final TransformGroup cubeTG = new TransformGroup();
        cubeTG.setCapability(TransformGroup.ALLOW_TRANSFORM_WRITE);
        final RotationInterpolator cubeRI = new RotationInterpolator(new Alpha(
                -1, 3000), cubeTG);
        cubeRI.setSchedulingBounds(new BoundingSphere());
        cubeTG.addChild(new ColorCube(0.2));
        cubeTG.addChild(cubeRI);

        final BranchGroup root = new BranchGroup();
        root.addChild(cubeTG);

        su.addBranchGraph(root);

        return su;
    }
}
