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

package org.egs3d.ui.util;


import java.awt.BorderLayout;
import java.awt.Frame;

import javax.media.j3d.BranchGroup;
import javax.media.j3d.Canvas3D;

import org.eclipse.swt.SWT;
import org.eclipse.swt.awt.SWT_AWT;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.widgets.Composite;

import com.sun.j3d.utils.universe.SimpleUniverse;


/**
 * Composant SWT adaptant {@link Canvas3D}.
 * 
 * @author romale
 */
public class SWTCanvas3D extends Composite {
    private Composite awtComponent;
    private SimpleUniverse universe;
    private Canvas3D canvas3D;
    private BranchGroup branchGroup;


    public SWTCanvas3D(final Composite parent, final int style) {
        super(parent, style);

        // création d'un composant SWT pouvant accueillir un élément AWT
        awtComponent = new Composite(this, SWT.EMBEDDED);
        // le composant est "étalé" sur toute la vue
        setLayout(new FillLayout());

        branchGroup = new BranchGroup();
        branchGroup.setCapability(BranchGroup.ALLOW_CHILDREN_READ);
        branchGroup.setCapability(BranchGroup.ALLOW_CHILDREN_EXTEND);
        branchGroup.setCapability(BranchGroup.ALLOW_CHILDREN_WRITE);

        // création du composant 3D
        canvas3D = new Canvas3D(SimpleUniverse.getPreferredConfiguration());
        universe = new SimpleUniverse(canvas3D);
        universe.getLocale().addBranchGraph(branchGroup);
        universe.getViewingPlatform().setNominalViewingTransform();

        // création d'un pont entre SWT et AWT
        final Frame frame = SWT_AWT.new_Frame(awtComponent);
        frame.setLayout(new BorderLayout());
        frame.add(canvas3D, BorderLayout.CENTER);
    }


    @Override
    public void dispose() {
        if (awtComponent != null) {
            awtComponent.dispose();
            awtComponent = null;
        }

        if (universe != null) {
            universe.cleanup();
            universe = null;
        }

        canvas3D = null;
        branchGroup = null;

        super.dispose();
    }


    /**
     * Affiche un arbre scénique 3D.
     */
    public synchronized void setSceneGraph(BranchGroup node) {
        // la capacité ALLOW_DETACH est requise pour détacher le noeud plus tard
        node.setCapability(BranchGroup.ALLOW_DETACH);

        branchGroup.removeAllChildren();
        branchGroup.addChild(node);
        canvas3D.repaint();
    }
}
