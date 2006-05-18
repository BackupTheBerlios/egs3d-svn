/*
 * Créé le 13 mai 2006
 *
 *
 */
package org.egs3d.ui.wizards;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.eclipse.jface.viewers.ILabelProvider;
import org.eclipse.jface.viewers.ILabelProviderListener;
import org.eclipse.jface.viewers.ISelectionChangedListener;
import org.eclipse.jface.viewers.IStructuredContentProvider;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.SelectionChangedEvent;
import org.eclipse.jface.viewers.TableViewer;
import org.eclipse.jface.viewers.Viewer;
import org.eclipse.jface.wizard.Wizard;
import org.eclipse.jface.wizard.WizardPage;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.ModifyEvent;
import org.eclipse.swt.events.ModifyListener;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Text;
import org.egs3d.core.StringUtils;
import org.egs3d.ui.internal.Messages;



/**
 * Assistant d'ajout de geometry.
 * 
 * @author brachet
 */
public class InsertGeometryWizard extends Wizard {
    public enum GeometryType {
        CUBE, SPHERE
    }
    
    private final Log log = LogFactory.getLog(getClass());
    
    private String geometryName;
    private GeometryType geometryType;

    public InsertGeometryWizard() {
        super();
        setWindowTitle(Messages.InsertGeometryWizard_windowTitle);
    }


    public String getGeometryName() {
        return geometryName;
    }

    public GeometryType getGeometryType() {
        return geometryType;
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
            super("firstPage");
            setTitle(Messages.InsertGeometryWizard_title);
            setDescription(Messages.InsertGeometryWizard_description);
        }


        public void createControl(Composite parent) {
            final Composite comp = new Composite(parent, SWT.NONE);
            comp.setLayout(new GridLayout(2, false));
            parent.setLayout(new FillLayout());

            final Label geometryNameLabel = new Label(comp, SWT.NONE);
            geometryNameLabel.setText(Messages.InsertGeometryWizard_GeometryName);
            geometryNameLabel.setLayoutData(new GridData(SWT.BEGINNING, SWT.CENTER, false,
                    false, 1, 1));

            final Text geometryNameField = new Text(comp, SWT.BORDER);
            geometryNameField.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
            geometryNameField.addModifyListener(new ModifyListener() {
                public void modifyText(ModifyEvent e) {
                	geometryName = StringUtils.trimToNull(geometryNameField.getText());
                    validate();
                }
            });
            
            final Label geometryTypeLabel = new Label(comp, SWT.NONE);
            geometryTypeLabel.setText(Messages.InsertGeometryWizard_GeometryType);
            geometryTypeLabel.setLayoutData(new GridData(SWT.BEGINNING, SWT.CENTER, false,
                    false, 1, 1));
            
            final TableViewer table = new TableViewer(comp, SWT.BORDER | SWT.H_SCROLL
                    | SWT.V_SCROLL);
            table.setContentProvider(new GeometryTypeContentProvider());
            table.setLabelProvider(new GeometryTypeLabelProvider());
            table.setInput("");
            table.getControl().setLayoutData(new GridData(GridData.FILL_BOTH));

            table.addSelectionChangedListener(new ISelectionChangedListener() {
                public void selectionChanged(SelectionChangedEvent event) {
                	geometryType = null;
                    if (!event.getSelection().isEmpty()) {
                        final IStructuredSelection sel = (IStructuredSelection) event
                                .getSelection();
                        geometryType = (GeometryType) sel.getFirstElement();

                        log.info("Type de géométrie à ajouter : " + geometryType.name());
                    }
                    validate();
                }
            });

            validate();
            setControl(comp);
        }

        private class GeometryTypeContentProvider implements IStructuredContentProvider {
            public Object[] getElements(Object e) {
                return GeometryType.values();
            }


            public void dispose() {
            }


            public void inputChanged(Viewer viewer, Object oldInput, Object newInput) {
            }
        }


        private class GeometryTypeLabelProvider implements ILabelProvider {
            public Image getImage(Object e) {
                return null;
            }


            public String getText(Object e) {
                if (GeometryType.CUBE.equals(e)) {
                    return "Cube";
                }
                if (GeometryType.SPHERE.equals(e)) {
                    return "Sphère";
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

        private void validate() {
            if (geometryName == null) {
                setPageComplete(false);
                return;
            }
            
            if (geometryType == null) {
                setPageComplete(false);
                return;
            }

            setErrorMessage(null);
            setPageComplete(true);
        }
    }
}
