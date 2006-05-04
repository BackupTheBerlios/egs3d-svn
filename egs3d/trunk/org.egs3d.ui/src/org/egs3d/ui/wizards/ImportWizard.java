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


import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.jface.viewers.ILabelProvider;
import org.eclipse.jface.viewers.ILabelProviderListener;
import org.eclipse.jface.viewers.ISelectionChangedListener;
import org.eclipse.jface.viewers.IStructuredContentProvider;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.SelectionChangedEvent;
import org.eclipse.jface.viewers.TableViewer;
import org.eclipse.jface.viewers.TreeViewer;
import org.eclipse.jface.viewers.Viewer;
import org.eclipse.jface.wizard.Wizard;
import org.eclipse.jface.wizard.WizardPage;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.ModifyEvent;
import org.eclipse.swt.events.ModifyListener;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.FileDialog;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Text;
import org.egs3d.core.StringUtils;
import org.egs3d.core.resources.IScene;
import org.egs3d.core.resources.SceneConstants;
import org.egs3d.ui.views.SceneExplorerLabelProvider;

import com.sun.j3d.loaders.Loader;
import com.sun.j3d.loaders.lw3d.Lw3dLoader;
import com.sun.j3d.loaders.objectfile.ObjectFile;


/**
 * Assistant d'importation de ressources.
 * 
 * @author romale
 */
public class ImportWizard extends Wizard {
    public enum ImportType {
        TEXTURE, MODEL
    }

    private final Log log = LogFactory.getLog(getClass());

    private ImportType importType;
    private IScene scene;
    private Class<? extends Loader> modelLoaderClass;
    private String resourceName;
    private String resourcePath;

    private Combo loaderCombo;


    public ImportType getImportType() {
        return importType;
    }


    public IScene getScene() {
        return scene;
    }


    public Class<? extends Loader> getModelLoaderClass() {
        return modelLoaderClass;
    }


    public String getResourceName() {
        return resourceName;
    }


    public String getResourcePath() {
        return resourcePath;
    }


    @Override
    public boolean performFinish() {
        return true;
    }


    @Override
    public void addPages() {
        addPage(new ImportToScenePage());
        addPage(new ImportTypePage());
        addPage(new ImportResourcePage());
    }


    private class ImportTypePage extends WizardPage {
        public ImportTypePage() {
            super("importTypePage");
            setTitle("Importer une ressource");
            setMessage("Sélectionner le type de ressource à importer");
        }


        public void createControl(Composite parent) {
            final Composite comp = new Composite(parent, SWT.NONE);
            comp.setLayout(new GridLayout(1, false));

            final Label label = new Label(comp, SWT.NONE);
            label.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
            label.setText("&Type de ressource :");

            final TableViewer table = new TableViewer(comp, SWT.BORDER | SWT.H_SCROLL
                    | SWT.V_SCROLL);
            table.setContentProvider(new ImportTypeContentProvider());
            table.setLabelProvider(new ImportTypeLabelProvider());
            table.setInput("");
            table.getControl().setLayoutData(new GridData(GridData.FILL_BOTH));

            table.addSelectionChangedListener(new ISelectionChangedListener() {
                public void selectionChanged(SelectionChangedEvent event) {
                    importType = null;
                    if (!event.getSelection().isEmpty()) {
                        final IStructuredSelection sel = (IStructuredSelection) event
                                .getSelection();
                        importType = (ImportType) sel.getFirstElement();

                        if (ImportType.MODEL.equals(importType)) {
                            loaderCombo.setEnabled(true);
                            loaderCombo.select(0);
                        } else {
                            loaderCombo.setEnabled(false);
                            loaderCombo.select(0);
                            loaderCombo.setText("");
                        }

                        log.info("Type de ressource à importer : " + importType.name());
                    }
                    validate();
                }
            });

            validate();
            setControl(comp);
        }


        private void validate() {
            setPageComplete(importType != null);
        }
    }


    private class ImportTypeContentProvider implements IStructuredContentProvider {
        public Object[] getElements(Object e) {
            return ImportType.values();
        }


        public void dispose() {
        }


        public void inputChanged(Viewer viewer, Object oldInput, Object newInput) {
        }
    }


    private class ImportTypeLabelProvider implements ILabelProvider {
        public Image getImage(Object e) {
            return null;
        }


        public String getText(Object e) {
            if (ImportType.MODEL.equals(e)) {
                return "Modèle";
            }
            if (ImportType.TEXTURE.equals(e)) {
                return "Texture";
            }
            return null;
        }


        public void addListener(ILabelProviderListener listener) {
        }


        public void dispose() {
        }


        public boolean isLabelProperty(Object element, String property) {
            return true;
        }


        public void removeListener(ILabelProviderListener listener) {
        }
    }


    private class ImportResourcePage extends WizardPage {
        public ImportResourcePage() {
            super("importResourcePage");
            setTitle("Importer une ressource");
            setMessage("Entrer le nom et le chemin de la ressource à importer");
        }


        public void createControl(Composite parent) {
            final Composite comp = new Composite(parent, SWT.NONE);
            comp.setLayout(new GridLayout(3, false));

            final Label nameLabel = new Label(comp, SWT.NONE);
            nameLabel
                    .setLayoutData(new GridData(SWT.BEGINNING, SWT.CENTER, false, false));
            nameLabel.setText("&Nom :");

            final Text nameField = new Text(comp, SWT.BORDER);
            nameField
                    .setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 2, 1));
            nameField.addModifyListener(new ModifyListener() {
                public void modifyText(ModifyEvent e) {
                    resourceName = StringUtils.trimToNull(nameField.getText());
                    validate();
                }
            });

            final Label pathLabel = new Label(comp, SWT.NONE);
            pathLabel
                    .setLayoutData(new GridData(SWT.BEGINNING, SWT.CENTER, false, false));
            pathLabel.setText("&Chemin :");

            final Text pathField = new Text(comp, SWT.BORDER);
            pathField.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false));
            pathField.setEditable(false);
            pathField.addModifyListener(new ModifyListener() {
                public void modifyText(ModifyEvent e) {
                    resourcePath = StringUtils.trimToNull(pathField.getText());
                    validate();
                }
            });

            final Button browseButton = new Button(comp, SWT.NONE);
            browseButton.setLayoutData(new GridData(SWT.RIGHT, SWT.CENTER, false, false));
            browseButton.setText("&Parcourir...");
            browseButton.addSelectionListener(new SelectionAdapter() {
                @Override
                public void widgetSelected(SelectionEvent e) {
                    final FileDialog dialog = new FileDialog(getShell());
                    final String path = StringUtils.trimToNull(dialog.open());
                    if (path != null) {
                        pathField.setText(path);
                    }
                }
            });

            final Label loaderLabel = new Label(comp, SWT.NONE);
            loaderLabel.setLayoutData(new GridData(SWT.BEGINNING, SWT.CENTER, false,
                    false));
            loaderLabel.setText("&Type :");

            loaderCombo = new Combo(comp, SWT.NONE);
            loaderCombo.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 2,
                    1));
            loaderCombo.add(ObjectFile.class.getName());
            loaderCombo.add(Lw3dLoader.class.getName());
            loaderCombo.addSelectionListener(new SelectionListener() {
                public void widgetDefaultSelected(SelectionEvent e) {
                    widgetSelected(e);
                }


                @SuppressWarnings("unchecked")
                public void widgetSelected(SelectionEvent e) {
                    modelLoaderClass = null;

                    final int i = loaderCombo.getSelectionIndex();
                    if (i == -1) {
                        return;
                    }

                    final String className = StringUtils.trimToNull(loaderCombo
                            .getItem(i));
                    if (className != null) {
                        try {
                            modelLoaderClass = (Class<? extends Loader>) Class
                                    .forName(className);
                        } catch (Exception exc) {
                            log.error(
                                    "Erreur lors de la sélection "
                                            + "de la classe du chargeur de modèle : "
                                            + className, exc);
                        }
                    }
                    validate();
                }
            });
            loaderCombo.select(0);

            validate();
            setControl(comp);
        }


        private void validate() {
            boolean ok = resourceName != null && resourcePath != null;
            if (ImportType.MODEL.equals(importType)) {
                ok = modelLoaderClass != null;
            }
            setPageComplete(ok);
        }
    }


    private class ImportToScenePage extends WizardPage {
        public ImportToScenePage() {
            super("importToScenePage");
            setTitle("Importer une ressource");
            setMessage("Sélectionner la scène dans laquelle "
                    + "la ressource sera importée");
        }


        public void createControl(Composite parent) {
            final Composite comp = new Composite(parent, SWT.NONE);
            comp.setLayout(new GridLayout(1, false));

            final Label label = new Label(comp, SWT.NONE);
            label.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
            label.setText("&Scène :");

            final TreeViewer tree = new TreeViewer(comp, SWT.BORDER | SWT.H_SCROLL
                    | SWT.V_SCROLL);
            tree.getControl().setLayoutData(new GridData(GridData.FILL_BOTH));
            tree.setContentProvider(new ProjectContentProvider(true));
            tree.setLabelProvider(new SceneExplorerLabelProvider());
            tree.setInput(ResourcesPlugin.getWorkspace().getRoot());
            tree.addSelectionChangedListener(new ISelectionChangedListener() {
                public void selectionChanged(SelectionChangedEvent event) {
                    scene = null;

                    final IStructuredSelection sel = (IStructuredSelection) event
                            .getSelection();
                    if (!sel.isEmpty()) {
                        final Object selectedObj = sel.getFirstElement();
                        if (selectedObj instanceof IResource) {
                            final IResource rsc = (IResource) selectedObj;
                            if (SceneConstants.SCENE_FILE_EXTENSION.equals(rsc
                                    .getFileExtension())) {
                                try {
                                    scene = (IScene) rsc
                                            .getSessionProperty(SceneConstants.SCENE_SESSION_RESOURCE_NAME);
                                    if (scene == null) {
                                        scene = org.egs3d.core.resources.ResourcesPlugin
                                                .createSceneReader().read(
                                                        rsc.getLocation().toFile());
                                        scene.setProject(rsc.getProject());
                                        rsc
                                                .setSessionProperty(
                                                        SceneConstants.SCENE_SESSION_RESOURCE_NAME,
                                                        scene);
                                    }
                                } catch (Exception e) {
                                    log.error("Erreur durant la "
                                            + "sélection d'une scène", e);
                                }
                            }
                        }
                    }
                    validate();
                }
            });

            validate();
            setControl(comp);
        }


        private void validate() {
            setPageComplete(scene != null);
        }
    }
}
