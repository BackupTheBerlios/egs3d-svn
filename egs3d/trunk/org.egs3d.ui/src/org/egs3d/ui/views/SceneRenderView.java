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

package org.egs3d.ui.views;


import javax.media.j3d.Alpha;
import javax.media.j3d.BoundingSphere;
import javax.media.j3d.BranchGroup;
import javax.media.j3d.RotationInterpolator;
import javax.media.j3d.TransformGroup;

import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.ui.part.ViewPart;
import org.egs3d.ui.util.SWTCanvas3D;

import com.sun.j3d.utils.geometry.ColorCube;


/**
 * Rendu de scène 3D.
 * 
 * @author romale
 */
public class SceneRenderView extends ViewPart {
    public static final String VIEW_ID = "org.egs3d.ui.views.sceneRender"; //$NON-NLS-1$
    private SWTCanvas3D canvas3D;


    @Override
    public void createPartControl(Composite parent) {
        canvas3D = new SWTCanvas3D(parent, SWT.NONE);
        canvas3D.setSceneGraph(createSceneGraph());

        parent.setLayout(new FillLayout());
    }


    @Override
    public void setFocus() {
        if (canvas3D != null) {
            canvas3D.setFocus();
        }
    }


    @Override
    public void dispose() {
        if (canvas3D != null) {
            canvas3D.dispose();
            canvas3D = null;
        }
        super.dispose();
    }


    /**
     * Création de l'arbre scénique 3D. Cette méthode est temporaire.
     */
    private BranchGroup createSceneGraph() {
        final BranchGroup root = new BranchGroup();

        final TransformGroup cubeTG = new TransformGroup();
        cubeTG.setCapability(TransformGroup.ALLOW_TRANSFORM_WRITE);

        final RotationInterpolator cubeRI = new RotationInterpolator(new Alpha(-1, 3000),
                cubeTG);
        cubeRI.setSchedulingBounds(new BoundingSphere());
        cubeTG.addChild(cubeRI);
        cubeTG.addChild(new ColorCube(0.2));

        root.addChild(cubeTG);

        return root;
    }
}
