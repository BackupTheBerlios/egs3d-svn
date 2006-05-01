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

package org.egs3d.ui.wizards;


import java.text.MessageFormat;

import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.jface.viewers.ISelectionChangedListener;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.SelectionChangedEvent;
import org.eclipse.jface.viewers.TreeViewer;
import org.eclipse.jface.wizard.Wizard;
import org.eclipse.jface.wizard.WizardPage;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.ModifyEvent;
import org.eclipse.swt.events.ModifyListener;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Text;
import org.egs3d.core.StringUtils;
import org.egs3d.core.resources.SceneConstants;
import org.egs3d.ui.internal.Messages;
import org.egs3d.ui.views.SceneExplorerLabelProvider;


/**
 * Assistant de création de scène.
 * 
 * @author romale
 */
public class NewSceneWizard extends Wizard {
    private IProject project;
    private String fileName;


    public NewSceneWizard() {
        super();
        setWindowTitle(Messages.NewSceneWizard_windowTitle);
    }


    public IProject getProject() {
        return project;
    }


    public String getFileName() {
        return fileName + "." + SceneConstants.SCENE_FILE_EXTENSION; //$NON-NLS-1$
    }


    @Override
    public void addPages() {
        addPage(new FirstPage());
    }


    @Override
    public boolean performFinish() {
        return true;
    }


    private class FirstPage extends WizardPage {
        public FirstPage() {
            super("firstPage"); //$NON-NLS-1$
            setTitle(Messages.NewSceneWizard_title);
            setDescription(Messages.NewSceneWizard_description);
        }


        public void createControl(Composite parent) {
            final Composite comp = new Composite(parent, SWT.NONE);
            comp.setLayout(new GridLayout(2, false));
            parent.setLayout(new FillLayout());

            final Label projectLabel = new Label(comp, SWT.NONE);
            projectLabel.setText(Messages.NewSceneWizard_Project);
            projectLabel.setLayoutData(new GridData(SWT.BEGINNING, SWT.CENTER, true,
                    false, 2, 1));

            final TreeViewer projectList = new TreeViewer(comp, SWT.BORDER | SWT.H_SCROLL
                    | SWT.V_SCROLL);
            projectList.setContentProvider(new ProjectContentProvider());
            projectList.setLabelProvider(new SceneExplorerLabelProvider());
            projectList.setInput(ResourcesPlugin.getWorkspace().getRoot());
            GridData gd = new GridData(SWT.FILL, SWT.FILL, true, true, 2, 1);
            gd.heightHint = 100;
            projectList.getControl().setLayoutData(gd);
            projectList.addSelectionChangedListener(new ISelectionChangedListener() {
                public void selectionChanged(SelectionChangedEvent e) {
                    if (!e.getSelection().isEmpty()) {
                        final IStructuredSelection selection = (IStructuredSelection) e
                                .getSelection();
                        project = (IProject) selection.getFirstElement();
                    } else {
                        project = null;
                    }
                    validate();
                }
            });

            final Label fileNameLabel = new Label(comp, SWT.NONE);
            fileNameLabel.setText(Messages.NewSceneWizard_FileName);
            fileNameLabel.setLayoutData(new GridData(SWT.BEGINNING, SWT.CENTER, false,
                    false, 1, 1));

            final Text fileNameField = new Text(comp, SWT.BORDER);
            fileNameField.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
            fileNameField.addModifyListener(new ModifyListener() {
                public void modifyText(ModifyEvent e) {
                    fileName = StringUtils.trimToNull(fileNameField.getText());
                    validate();
                }
            });

            validate();
            setControl(comp);
        }


        private void validate() {
            if (project == null || fileName == null) {
                setPageComplete(false);
                return;
            }
            if (project.findMember(fileName + "." + SceneConstants.SCENE_FILE_EXTENSION) != null) { //$NON-NLS-1$
                setErrorMessage(MessageFormat.format(Messages.Error_sceneNameExists,
                        fileName));
                // une ressource existe déjà avec le nom de fichier
                setPageComplete(false);
                return;
            }
            setErrorMessage(null);
            setPageComplete(true);
        }
    }
}
