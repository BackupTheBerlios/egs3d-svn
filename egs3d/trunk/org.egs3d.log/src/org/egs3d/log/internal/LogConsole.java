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

package org.egs3d.log.internal;


import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.ui.console.MessageConsole;


/**
 * @author romale
 */
public class LogConsole extends MessageConsole {
    public static final String CONSOLE_TYPE = "org.egs3d.log.logConsole";


    public LogConsole(final String name, final ImageDescriptor imageDescriptor) {
        super(name, imageDescriptor);
        setTabWidth(4);
        setType(CONSOLE_TYPE);
    }


    public String getType() {
        return CONSOLE_TYPE;
    }
}
