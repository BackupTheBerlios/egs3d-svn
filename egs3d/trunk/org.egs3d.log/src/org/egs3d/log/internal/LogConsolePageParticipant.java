/**
 * $Id: StringUtilsTest.java 16 2006-02-26 16:18:44Z romale $
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

package org.egs3d.log.internal;


import org.eclipse.ui.console.IConsole;
import org.eclipse.ui.console.IConsoleConstants;
import org.eclipse.ui.console.IConsolePageParticipant;
import org.eclipse.ui.console.actions.CloseConsoleAction;
import org.eclipse.ui.part.IPageBookViewPage;


/**
 * @author romale
 */
public class LogConsolePageParticipant implements IConsolePageParticipant {
    public void init(IPageBookViewPage page, IConsole console) {
        page.getSite().getActionBars().getToolBarManager()
                .appendToGroup(IConsoleConstants.LAUNCH_GROUP,
                        new CloseConsoleAction(console));
    }


    public void dispose() {
    }


    public void activated() {
    }


    public void deactivated() {
    }


    public Object getAdapter(Class adapter) {
        return null;
    }
}
