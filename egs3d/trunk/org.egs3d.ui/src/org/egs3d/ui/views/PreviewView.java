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
import org.egs3d.core.resources.ITexture;
import org.egs3d.ui.internal.Messages;
import org.egs3d.ui.internal.SWTUtils;


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
        if (texture.getImage() == null) {
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
        } else {
            // pas d'aperçu pour l'élément sélectionné
            showNoPreview();
        }

        selectionRef = new WeakReference<Object>(selectedObj);
    }
}
