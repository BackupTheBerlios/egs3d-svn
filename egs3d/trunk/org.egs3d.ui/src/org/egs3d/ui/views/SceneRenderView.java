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


import java.awt.BorderLayout;
import java.awt.Frame;

import javax.media.j3d.Alpha;
import javax.media.j3d.BoundingSphere;
import javax.media.j3d.BranchGroup;
import javax.media.j3d.Canvas3D;
import javax.media.j3d.RotationInterpolator;
import javax.media.j3d.TransformGroup;

import org.eclipse.swt.SWT;
import org.eclipse.swt.awt.SWT_AWT;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.ui.part.ViewPart;

import com.sun.j3d.utils.geometry.ColorCube;
import com.sun.j3d.utils.universe.SimpleUniverse;


/**
 * Rendu de sc�ne 3D.
 * 
 * @author romale
 */
public class SceneRenderView extends ViewPart {
    public static final String VIEW_ID = "org.egs3d.ui.views.sceneRender"; //$NON-NLS-1$
    private Composite awtComponent;
    private Canvas3D canvas3D;


    /**
     * Retourne le {@link Canvas3D} utilis� pour le rendu de la sc�ne.
     */
    public Canvas3D getCanvas3D() {
        return canvas3D;
    }


    @Override
    public void createPartControl(Composite parent) {
        // cr�ation d'un composant SWT pouvant accueillir un �l�ment AWT
        awtComponent = new Composite(parent, SWT.EMBEDDED);
        // le composant est "�tal�" sur toute la vue
        parent.setLayout(new FillLayout());

        // cr�ation du composant 3D
        canvas3D = new Canvas3D(SimpleUniverse.getPreferredConfiguration());
        final SimpleUniverse universe = new SimpleUniverse(canvas3D);
        universe.addBranchGraph(createSceneGraph());
        universe.getViewingPlatform().setNominalViewingTransform();

        // cr�ation d'un pont entre SWT et AWT
        final Frame frame = SWT_AWT.new_Frame(awtComponent);
        frame.setLayout(new BorderLayout());
        frame.add(canvas3D, BorderLayout.CENTER);

        // TODO connecter la vue � l'�l�ment s�lectionn� dans l'explorateur de
        // sc�ne pour adapter le rendu 3D
    }


    @Override
    public void setFocus() {
        if (awtComponent != null) {
            awtComponent.setFocus();
        }
    }


    @Override
    public void dispose() {
        if (awtComponent != null) {
            awtComponent.dispose();
            awtComponent = null;
        }
        canvas3D = null;
        super.dispose();
    }


    /**
     * Cr�ation de l'arbre sc�nique 3D. Cette m�thode est temporaire.
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
