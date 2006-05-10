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
import java.math.BigDecimal;

import javax.media.j3d.AmbientLight;
import javax.media.j3d.BoundingSphere;
import javax.media.j3d.Bounds;
import javax.media.j3d.BranchGroup;
import javax.media.j3d.DirectionalLight;
import javax.media.j3d.Node;
import javax.vecmath.Color3f;
import javax.vecmath.Point3d;
import javax.vecmath.Vector3f;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.eclipse.jface.action.Action;
import org.eclipse.jface.action.IToolBarManager;
import org.eclipse.jface.dialogs.Dialog;
import org.eclipse.jface.dialogs.IDialogConstants;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.window.IShellProvider;
import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.CLabel;
import org.eclipse.swt.custom.ScrolledComposite;
import org.eclipse.swt.events.ModifyEvent;
import org.eclipse.swt.events.ModifyListener;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.graphics.ImageData;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Text;
import org.eclipse.ui.ISelectionListener;
import org.eclipse.ui.IWorkbenchPart;
import org.eclipse.ui.part.PageBook;
import org.eclipse.ui.part.ViewPart;
import org.egs3d.core.Java3DUtils;
import org.egs3d.core.StringUtils;
import org.egs3d.core.resources.IModel;
import org.egs3d.core.resources.ITexture;
import org.egs3d.ui.internal.IconType;
import org.egs3d.ui.internal.Messages;
import org.egs3d.ui.internal.SWTUtils;
import org.egs3d.ui.internal.UIPlugin;
import org.egs3d.ui.util.SWTCanvas3D;


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
    private boolean syncPreview = true;
    private boolean addLights = true;
    private BigDecimal scaleFactor = new BigDecimal("0.01"); //$NON-NLS-1$


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
        getSite().getWorkbenchWindow().getSelectionService().addPostSelectionListener(
                SceneExplorerView.VIEW_ID, this);

        final IToolBarManager toolBarManager = getViewSite().getActionBars()
                .getToolBarManager();
        toolBarManager.add(new RefreshAction());
        toolBarManager.add(new SynchronizePreviewAction());
        toolBarManager.add(new ShowOptionsDialog());

        showNoPreview();
    }


    @Override
    public void setFocus() {
        pageBook.setFocus();
    }


    @Override
    public void dispose() {
        if (pageBook != null) {
            getSite().getWorkbenchWindow().getSelectionService()
                    .removePostSelectionListener(SceneExplorerView.VIEW_ID, this);

            pageBook.dispose();
            pageBook = null;
        }
        super.dispose();
    }


    private void refresh() {
        if (selectionRef != null) {
            show(selectionRef.get(), true);
        }
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
        if (model == null) {
            showNoPreview();
            return;
        }

        BranchGroup sceneGraph = null;
        try {
            sceneGraph = model.getBranchGroup();
        } catch (Exception e) {
            log.error("Erreur lors du chargement du modèle : " + model, e); //$NON-NLS-1$
        }
        if (sceneGraph == null) {
            showNoPreview();
            return;
        }

        canvas3D.setSceneGraph(createSceneGraphWithLights(sceneGraph.cloneTree()));

        pageBook.showPage(canvas3D);
    }


    private void showNode(Node node) {
        if (node == null) {
            showNoPreview();
            return;
        }

        canvas3D.setSceneGraph(createSceneGraphWithLights(node.cloneTree()));

        pageBook.showPage(canvas3D);
    }


    private BranchGroup createSceneGraphWithLights(Node node) {
        final Bounds bounds = new BoundingSphere(new Point3d(), 100000);
        final BranchGroup root = new BranchGroup();

        if (addLights) {
            final BranchGroup lights = new BranchGroup();

            final AmbientLight ambientLightNode = new AmbientLight(new Color3f(0.1f,
                    0.1f, 0.1f));
            ambientLightNode.setInfluencingBounds(bounds);
            lights.addChild(ambientLightNode);

            final Vector3f light1Direction = new Vector3f(1.0f, 1.0f, 1.0f);
            final DirectionalLight light1 = new DirectionalLight(new Color3f(1.0f, 1.0f,
                    0.9f), light1Direction);
            light1.setInfluencingBounds(bounds);
            lights.addChild(light1);

            final Vector3f light2Direction = new Vector3f(-1.0f, -1.0f, -1.0f);
            final DirectionalLight light2 = new DirectionalLight(new Color3f(1.0f, 1.0f,
                    0.9f), light2Direction);
            light2.setInfluencingBounds(bounds);
            lights.addChild(light2);

            root.addChild(lights);
        }

        root.addChild(Java3DUtils.addMouseBehavior(Java3DUtils.scale(node, scaleFactor
                .doubleValue()), bounds));

        return root;
    }


    public void selectionChanged(IWorkbenchPart part, ISelection selection) {
        if (!syncPreview) {
            return;
        }
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

        show(selectedObj, false);
    }


    private void show(Object selectedObj, boolean force) {
        if (selectionRef != null) {
            if (!force && selectedObj.equals(selectionRef.get())) {
                log.debug("Pas de changement dans la sélection"); //$NON-NLS-1$
                return;
            }
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
        } else if (selectedObj instanceof Node) {
            showNode((Node) selectedObj);
        } else {
            // pas d'aperçu pour l'élément sélectionné
            showNoPreview();
        }

        selectionRef = new WeakReference<Object>(selectedObj);
    }


    private class RefreshAction extends Action {
        public RefreshAction() {
            super(Messages.PreviewView_refresh, UIPlugin.getDefault().getIcon(
                    IconType.REFRESH));
            setToolTipText(getText());
        }


        @Override
        public void run() {
            refresh();
        }
    }


    private class SynchronizePreviewAction extends Action {
        public SynchronizePreviewAction() {
            super(Messages.PreviewView_synchronizePreview, Action.AS_CHECK_BOX);
            setToolTipText(getText());
            setImageDescriptor(UIPlugin.getDefault().getIcon(IconType.SYNC));
            setChecked(syncPreview);
        }


        @Override
        public void run() {
            syncPreview = !syncPreview;
            setChecked(syncPreview);

            if (syncPreview) {
                final IStructuredSelection sel = (IStructuredSelection) getSite()
                        .getWorkbenchWindow().getSelectionService().getSelection(
                                SceneExplorerView.VIEW_ID);
                if (sel != null && !sel.isEmpty()) {
                    show(sel.getFirstElement(), true);
                }
            }
        }
    }


    private class ShowOptionsDialog extends Action {
        public ShowOptionsDialog() {
            super(Messages.PreviewView_showOptions, UIPlugin.getDefault().getIcon(
                    IconType.CONFIG));
            setToolTipText(getText());
        }


        @Override
        public void run() {
            new OptionsDialog(getViewSite()).open();
        }
    }


    private class OptionsDialog extends Dialog {
        public OptionsDialog(IShellProvider parentShell) {
            super(parentShell);
        }


        @Override
        protected Control createDialogArea(Composite parent) {
            final Composite comp = (Composite) super.createDialogArea(parent);

            final Group group3D = new Group(comp, SWT.SHADOW_ETCHED_IN);
            group3D.setText(Messages.PreviewView_options_3dOptions);
            group3D.setLayout(new GridLayout(2, false));

            final Label scaleLabel = new Label(group3D, SWT.NONE);
            scaleLabel.setText(Messages.PreviewView_options_scaleFactor);
            scaleLabel
                    .setLayoutData(new GridData(SWT.BEGINNING, SWT.CENTER, false, false));

            final Text scaleField = new Text(group3D, SWT.BORDER);
            scaleField.setText(scaleFactor.toPlainString());
            scaleField.setTextLimit(6);
            GridData data = new GridData(SWT.FILL, SWT.CENTER, true, false);
            data.widthHint = 50;
            scaleField.setLayoutData(data);
            scaleField.addModifyListener(new ModifyListener() {
                public void modifyText(ModifyEvent e) {
                    final String text = StringUtils.trimToNull(scaleField.getText());
                    if (text == null) {
                        getButton(IDialogConstants.OK_ID).setEnabled(false);
                        return;
                    }
                    try {
                        scaleFactor = new BigDecimal(text);
                        getButton(IDialogConstants.OK_ID).setEnabled(true);
                    } catch (NumberFormatException exc) {
                        getButton(IDialogConstants.OK_ID).setEnabled(false);
                    }
                }
            });

            final Button addLightsButton = new Button(group3D, SWT.CHECK);
            addLightsButton.setSelection(addLights);
            addLightsButton.setText(Messages.PreviewView_options_addLights);
            addLightsButton.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false,
                    2, 1));
            addLightsButton.addSelectionListener(new SelectionListener() {
                public void widgetDefaultSelected(SelectionEvent e) {
                    widgetSelected(e);
                }


                public void widgetSelected(SelectionEvent e) {
                    addLights = addLightsButton.getSelection();
                }
            });

            return comp;
        }


        @Override
        protected void configureShell(Shell newShell) {
            super.configureShell(newShell);
            newShell.setText(Messages.PreviewView_options_title);
        }


        @Override
        protected void okPressed() {
            super.okPressed();
            refresh();
        }
    }
}
