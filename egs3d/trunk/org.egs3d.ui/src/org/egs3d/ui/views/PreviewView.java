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


import java.awt.Color;
import java.lang.ref.WeakReference;

import javax.media.j3d.AmbientLight;
import javax.media.j3d.Background;
import javax.media.j3d.BoundingSphere;
import javax.media.j3d.Bounds;
import javax.media.j3d.BranchGroup;
import javax.media.j3d.DirectionalLight;
import javax.media.j3d.Transform3D;
import javax.media.j3d.TransformGroup;
import javax.vecmath.Color3f;
import javax.vecmath.Point3d;
import javax.vecmath.Vector3f;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.CLabel;
import org.eclipse.swt.custom.ScrolledComposite;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.graphics.ImageData;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;
import org.eclipse.ui.ISelectionListener;
import org.eclipse.ui.IWorkbenchPart;
import org.eclipse.ui.part.PageBook;
import org.eclipse.ui.part.ViewPart;
import org.egs3d.core.resources.IModel;
import org.egs3d.core.resources.ITexture;
import org.egs3d.ui.internal.Messages;
import org.egs3d.ui.internal.SWTUtils;
import org.egs3d.ui.util.SWTCanvas3D;

import com.sun.j3d.utils.behaviors.keyboard.KeyNavigatorBehavior;
import com.sun.j3d.utils.behaviors.mouse.MouseRotate;
import com.sun.j3d.utils.behaviors.mouse.MouseWheelZoom;


/**
 * Vue d'aperçu.
 * 
 * @author romale
 */
public class PreviewView extends ViewPart implements ISelectionListener {
    private final Log log = LogFactory.getLog(getClass());
    private Label noPreviewLabel;
    private PageBook pageBook;
    private ScrolledComposite imageComp;
    private CLabel imageLabel;
    private SWTCanvas3D canvas3D;
    private WeakReference<Object> selectionRef;


    @Override
    public void createPartControl(Composite parent) {
        parent.setLayout(new FillLayout());
        pageBook = new PageBook(parent, SWT.NONE);

        noPreviewLabel = new Label(pageBook, SWT.NONE);
        noPreviewLabel.setText(Messages.PreviewView_noPreview);

        imageComp = new ScrolledComposite(pageBook, SWT.H_SCROLL | SWT.V_SCROLL);
        imageLabel = new CLabel(imageComp, SWT.NONE);
        imageComp.setContent(imageLabel);

        canvas3D = new SWTCanvas3D(pageBook, SWT.NONE);

        // l'élément sélectionné dans l'explorateur de scène est récupéré à
        // travers l'interface ISelectionListener
        getSite().getWorkbenchWindow().getSelectionService().addSelectionListener(
                SceneExplorerView.VIEW_ID, this);

        showNoPreview();
    }


    @Override
    public void setFocus() {
        pageBook.setFocus();
    }


    @Override
    public void dispose() {
        if (pageBook != null) {
            getSite().getWorkbenchWindow().getSelectionService().removeSelectionListener(
                    SceneExplorerView.VIEW_ID, this);

            pageBook.dispose();
            pageBook = null;
        }
        super.dispose();
    }


    private void showNoPreview() {
        pageBook.showPage(noPreviewLabel);
    }


    private void showTexture(ITexture texture) {
        if (texture == null || texture.getImage() == null) {
            showNoPreview();
            return;
        }

        final ImageData imageData = SWTUtils.convertToSWT(texture.getImage());
        final Image image = new Image(getSite().getWorkbenchWindow().getShell()
                .getDisplay(), imageData);
        imageLabel.setImage(image);

        imageLabel.setSize(imageLabel.computeSize(SWT.DEFAULT, SWT.DEFAULT));
        imageLabel.layout();

        pageBook.showPage(imageComp);
    }


    private void showModel(IModel model) {
        if (model == null || model.getBranchGroup() == null) {
            showNoPreview();
            return;
        }

        final Bounds bounds = new BoundingSphere(new Point3d(), 100000);
        final BranchGroup root = new BranchGroup();
        root.setCapability(BranchGroup.ALLOW_DETACH);

        final Background bg = new Background(new Color3f(Color.WHITE));
        bg.setApplicationBounds(bounds);
        root.addChild(bg);

        final BranchGroup sceneGraph = model.getBranchGroup();
        final Transform3D scale3D = new Transform3D();
        scale3D.setScale(0.7);
        final TransformGroup scaleGroup = new TransformGroup(scale3D);
        scaleGroup.addChild(sceneGraph);

        final TransformGroup mouseRotateTG = new TransformGroup();
        mouseRotateTG.setCapability(TransformGroup.ALLOW_TRANSFORM_WRITE);
        final MouseRotate mouseRotate = new MouseRotate(mouseRotateTG);
        mouseRotate.setSchedulingBounds(bounds);
        mouseRotateTG.addChild(mouseRotate);
        mouseRotateTG.addChild(scaleGroup);

        final TransformGroup mouseZoomTG = new TransformGroup();
        mouseZoomTG.setCapability(TransformGroup.ALLOW_TRANSFORM_WRITE);
        final MouseWheelZoom mouseWheelZoom = new MouseWheelZoom(mouseZoomTG);
        mouseWheelZoom.setSchedulingBounds(bounds);
        mouseZoomTG.addChild(mouseWheelZoom);
        mouseZoomTG.addChild(mouseRotateTG);

        final TransformGroup keyNavigatorTG = new TransformGroup();
        keyNavigatorTG.setCapability(TransformGroup.ALLOW_TRANSFORM_WRITE);
        final KeyNavigatorBehavior keyNavigator = new KeyNavigatorBehavior(keyNavigatorTG);
        keyNavigator.setSchedulingBounds(bounds);
        keyNavigatorTG.addChild(keyNavigator);
        keyNavigatorTG.addChild(mouseZoomTG);

        final AmbientLight ambientLightNode = new AmbientLight(new Color3f(0.1f, 0.1f,
                0.1f));
        ambientLightNode.setInfluencingBounds(bounds);
        root.addChild(ambientLightNode);

        final Vector3f light1Direction = new Vector3f(1.0f, 1.0f, 1.0f);
        final DirectionalLight light1 = new DirectionalLight(
                new Color3f(1.0f, 1.0f, 0.9f), light1Direction);
        light1.setInfluencingBounds(bounds);
        root.addChild(light1);

        final Vector3f light2Direction = new Vector3f(-1.0f, -1.0f, -1.0f);
        final DirectionalLight light2 = new DirectionalLight(
                new Color3f(1.0f, 1.0f, 0.9f), light2Direction);
        light2.setInfluencingBounds(bounds);
        root.addChild(light2);

        root.addChild(keyNavigatorTG);
        canvas3D.setSceneGraph(root);

        pageBook.showPage(canvas3D);
    }


    public void selectionChanged(IWorkbenchPart part, ISelection selection) {
        if (this == part || selection.isEmpty()
                || !(selection instanceof IStructuredSelection)) {
            showNoPreview();
            return;
        }

        final IStructuredSelection isl = (IStructuredSelection) selection;
        final Object selectedObj = isl.getFirstElement();
        if (selectedObj == null) {
            return;
        }
        log.debug("Elément sélectionné : " + selectedObj); //$NON-NLS-1$

        if (selectionRef != null && selectedObj.equals(selectionRef.get())) {
            log.debug("Pas de changement dans la sélection");
            return;
        }

        if (imageLabel.getImage() != null) {
            // libération des ressources occupées par l'image
            final Image image = imageLabel.getImage();
            imageLabel.setImage(null);
            image.dispose();
        }

        if (selectedObj instanceof ITexture) {
            showTexture((ITexture) selectedObj);
        } else if (selectedObj instanceof IModel) {
            showModel((IModel) selectedObj);
        } else {
            // pas d'aperçu pour l'élément sélectionné
            showNoPreview();
        }

        selectionRef = new WeakReference<Object>(selectedObj);
    }
}
