/*******************************************************************************
 * Copyright (c) 2011 Oak Ridge National Laboratory.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 ******************************************************************************/
package org.csstudio.scan.ui.scantree;

import java.util.Collections;
import java.util.List;

import org.csstudio.scan.command.ScanCommand;
import org.csstudio.scan.ui.scantree.actions.OpenPropertiesAction;
import org.eclipse.jface.action.MenuManager;
import org.eclipse.jface.viewers.ColumnViewerToolTipSupport;
import org.eclipse.jface.viewers.DoubleClickEvent;
import org.eclipse.jface.viewers.IDoubleClickListener;
import org.eclipse.jface.viewers.ISelectionProvider;
import org.eclipse.jface.viewers.TreeViewer;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Menu;
import org.eclipse.swt.widgets.Tree;

/** GUI for the scan tree
 *  @author Kay Kasemir
 */
public class GUI
{
    /** Commands displayed and edited in this GUI */
    private List<ScanCommand> commands = Collections.emptyList();
    
    /** Tree that shows commands */
    private TreeViewer tree_view;

    /** Initialize
     *  @param parent
     */
    public GUI(final Composite parent)
    {
        createComponents(parent);
        createContextMenu();
    }

    /** Create GUI elements
     *  @param parent Parent widget
     */
    private void createComponents(final Composite parent)
    {
        parent.setLayout(new FillLayout());

        tree_view = new TreeViewer(parent,
                SWT.FULL_SELECTION | SWT.H_SCROLL | SWT.V_SCROLL | SWT.BORDER);
        final Tree tree = tree_view.getTree();
        tree.setLinesVisible(true);
        tree_view.setUseHashlookup(true);
        tree_view.setContentProvider(new CommandTreeContentProvider());
        tree_view.setLabelProvider(new CommandTreeLabelProvider());
        
        // Double-click opens property panel
        tree_view.addDoubleClickListener(new IDoubleClickListener()
        {
            @Override
            public void doubleClick(DoubleClickEvent event)
            {
                new OpenPropertiesAction().run();
            }
        });
        
        ColumnViewerToolTipSupport.enableFor(tree_view);
    }

    /** Create context menu */
    private void createContextMenu()
    {
        final MenuManager manager = new MenuManager();
        manager.add(new OpenPropertiesAction());
        
        final Menu menu = manager.createContextMenu(tree_view.getControl());
        tree_view.getControl().setMenu(menu);
    }
    
    /** Set focus */
    public void setFocus()
    {
        tree_view.getTree().setFocus();
    }
    
    /** @param commands Commands to display/edit */
    public void setCommands(final List<ScanCommand> commands)
    {
        this.commands = commands;
        tree_view.setInput(commands);
    }
    
    /** @return Commands displayed/edited in GUI */
    public List<ScanCommand> getCommands()
    {
        return commands;
    }
    
    /** @param command Command that has been updated, requiring a refresh of the GUI */
    public void refreshCommand(final ScanCommand command)
    {
        tree_view.refresh(command);
    }

    /** @return Selection provider for commands in scan tree */
    public ISelectionProvider getSelectionProvider()
    {
        return tree_view;
    }
}
