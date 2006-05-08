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


import java.lang.ref.WeakReference;

import javax.media.j3d.BoundingSphere;
import javax.media.j3d.Bounds;
import javax.media.j3d.BranchGroup;
import javax.vecmath.Point3d;

import org.eclipse.jface.action.Action;
import org.eclipse.jface.action.IToolBarManager;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.ui.ISelectionListener;
import org.eclipse.ui.IWorkbenchPart;
import org.eclipse.ui.part.ViewPart;
import org.egs3d.core.Java3DUtils;
import org.egs3d.core.resources.IScene;
import org.egs3d.core.resources.ResourcesPlugin;
import org.egs3d.ui.internal.IconType;
import org.egs3d.ui.internal.Messages;
import org.egs3d.ui.internal.UIPlugin;
import org.egs3d.ui.util.SWTCanvas3D;


/**
 * Rendu de scène 3D.
 * 
 * @author romale
 */
public class SceneRenderView extends ViewPart implements ISelectionListener {
    public static final String VIEW_ID = "org.egs3d.ui.views.sceneRender"; //$NON-NLS-1$
    private SWTCanvas3D canvas3D;
    private WeakReference<IScene> lastSceneRef;
    private double scale = 0.01;


    @Override
    public void createPartControl(Composite parent) {
        canvas3D = new SWTCanvas3D(parent, SWT.NONE);
        parent.setLayout(new FillLayout());

        final IToolBarManager toolBarManager = getViewSite().getActionBars()
                .getToolBarManager();
        toolBarManager.add(new RefreshAction());

        getSite().getWorkbenchWindow().getSelectionService()
                .addPostSelectionListener(SceneExplorerView.VIEW_ID, this);
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
        lastSceneRef = null;
        getSite().getWorkbenchWindow().getSelectionService()
                .removePostSelectionListener(SceneExplorerView.VIEW_ID, this);
        super.dispose();
    }


    public void selectionChanged(IWorkbenchPart part, ISelection selection) {
        final IStructuredSelection sel = (IStructuredSelection) selection;
        if (sel.isEmpty() || sel.getFirstElement() == null) {
            return;
        }

        final Object selectedObj = sel.getFirstElement();
        final IScene scene = ResourcesPlugin.getScene(selectedObj);
        if (scene == null) {
            return;
        }
        if (lastSceneRef != null && scene.equals(lastSceneRef.get())) {
            return;
        }

        renderScene(scene);
    }


    private void renderScene(IScene scene) {
        if (scene == null || scene.getBranchGroupContainer().getSize() < 1) {
            canvas3D.setSceneGraph(new BranchGroup());
            lastSceneRef = null;
        } else {
            // reconstruction de la scène complète
            final BranchGroup completeSceneGraph = new BranchGroup();
            for (final BranchGroup sceneGraph : scene.getBranchGroupContainer()) {
                // chaque sous-scène est clonée pour ne pas interférer avec
                // la scène originale
                completeSceneGraph.addChild(sceneGraph.cloneTree());
            }

            final Bounds bounds = new BoundingSphere(new Point3d(), 100000);

            final BranchGroup root = new BranchGroup();
            root.addChild(Java3DUtils.addMouseBehavior(Java3DUtils.scale(
                    completeSceneGraph, scale), bounds));

            canvas3D.setSceneGraph(root);
            lastSceneRef = new WeakReference<IScene>(scene);
        }
    }


    private class RefreshAction extends Action {
        public RefreshAction() {
            super(Messages.SceneRenderView_refresh, UIPlugin.getDefault()
                    .getIcon(IconType.REFRESH));
            setToolTipText(getText());
        }


        @Override
        public void run() {
            final IScene scene = UIPlugin.getDefault().getSelectedScene();
            renderScene(scene);
        }
    }
}
