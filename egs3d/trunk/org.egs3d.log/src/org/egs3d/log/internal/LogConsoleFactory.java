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


import org.eclipse.ui.console.ConsolePlugin;
import org.eclipse.ui.console.IConsole;
import org.eclipse.ui.console.IConsoleFactory;
import org.eclipse.ui.console.IConsoleListener;
import org.eclipse.ui.console.IConsoleManager;
import org.eclipse.ui.console.MessageConsoleStream;


/**
 * @author romale
 */
public class LogConsoleFactory implements IConsoleFactory, IConsoleListener {
    private static final LogConsoleFactory INSTANCE = new LogConsoleFactory();
    private static LogConsole console;
    private static MessageConsoleStream consoleStream;


    public static LogConsoleFactory getInstance() {
        return INSTANCE;
    }


    private IConsoleManager getConsoleManager() {
        return ConsolePlugin.getDefault().getConsoleManager();
    }


    public synchronized void openConsole() {
        initConsole();
        getConsoleManager().showConsoleView(console);
    }


    public synchronized void println(String msg) {
        initConsole();
        consoleStream.println(msg);
    }


    private synchronized void initConsole() {
        if (console == null) {
            console = new LogConsole(Messages.LogConsole_title, null);
            getConsoleManager().addConsoles(new IConsole[] { console });
            getConsoleManager().addConsoleListener(this);
        }
        if (consoleStream == null) {
            consoleStream = console.newMessageStream();
        }
    }


    public void consolesAdded(IConsole[] consoles) {
    }


    public synchronized void consolesRemoved(IConsole[] consoles) {
        console = null;
        consoleStream = null;
        getConsoleManager().removeConsoleListener(this);
    }
}
