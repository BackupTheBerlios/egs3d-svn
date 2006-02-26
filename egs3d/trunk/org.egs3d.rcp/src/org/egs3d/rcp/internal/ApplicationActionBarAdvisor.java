/**
 * $Id: BLC.java,v 1.6 2005/11/28 16:08:16 romale Exp $
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

package org.egs3d.rcp.internal;


import org.eclipse.jface.action.IContributionItem;
import org.eclipse.jface.action.IMenuManager;
import org.eclipse.jface.action.MenuManager;
import org.eclipse.jface.action.Separator;
import org.eclipse.ui.IWorkbenchActionConstants;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.actions.ActionFactory;
import org.eclipse.ui.actions.ContributionItemFactory;
import org.eclipse.ui.actions.ActionFactory.IWorkbenchAction;
import org.eclipse.ui.application.ActionBarAdvisor;
import org.eclipse.ui.application.IActionBarConfigurer;


/**
 * @author romale
 */
public class ApplicationActionBarAdvisor extends ActionBarAdvisor {
    private final IWorkbenchWindow window;
    private IWorkbenchAction exitAction;
    private IWorkbenchAction aboutAction;
    private IWorkbenchAction preferencesAction;


    public ApplicationActionBarAdvisor(IActionBarConfigurer configurer) {
        super(configurer);
        window = configurer.getWindowConfigurer().getWindow();
    }


    @Override
    protected void makeActions(IWorkbenchWindow window) {
        exitAction = ActionFactory.QUIT.create(window);
        register(exitAction);

        aboutAction = ActionFactory.ABOUT.create(window);
        register(aboutAction);

        preferencesAction = ActionFactory.PREFERENCES.create(window);
        register(preferencesAction);
    }


    @Override
    public void dispose() {
        exitAction = null;
        aboutAction = null;
        preferencesAction = null;
        super.dispose();
    }


    @Override
    protected void fillMenuBar(IMenuManager menuBar) {
        menuBar.add(createFileMenu());
        menuBar.add(createWindowMenu());
        menuBar.add(createHelpMenu());
    }


    private MenuManager createFileMenu() {
        final MenuManager menu = new MenuManager(Messages.Menu_Fichier,
                IWorkbenchActionConstants.M_FILE);
        menu.add(exitAction);

        return menu;
    }


    private MenuManager createWindowMenu() {
        final MenuManager menu = new MenuManager(Messages.Menu_Fenetres,
                IWorkbenchActionConstants.M_WINDOW);

        // code inspiré de la classe WorkbenchActionBuilder du plugin
        // org.eclipse.ui.ide
        MenuManager showViewMenuMgr = new MenuManager(Messages.Menu_Vues,
                "showView"); //$NON-NLS-2$
        IContributionItem showViewMenu = ContributionItemFactory.VIEWS_SHORTLIST
                .create(window);
        showViewMenuMgr.add(showViewMenu);
        menu.add(showViewMenuMgr);
        menu.add(new Separator());

        menu.add(preferencesAction);

        return menu;
    }


    private MenuManager createHelpMenu() {
        final MenuManager menu = new MenuManager(Messages.Menu_Aide);
        menu.add(aboutAction);

        return menu;
    }
}
