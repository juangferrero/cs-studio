/*
 * Copyright (c) 2010 Stiftung Deutsches Elektronen-Synchrotron,
 * Member of the Helmholtz Association, (DESY), HAMBURG, GERMANY.
 *
 * THIS SOFTWARE IS PROVIDED UNDER THIS LICENSE ON AN "../AS IS" BASIS.
 * WITHOUT WARRANTY OF ANY KIND, EXPRESSED OR IMPLIED, INCLUDING BUT NOT LIMITED
 * TO THE WARRANTIES OF MERCHANTABILITY, FITNESS FOR PARTICULAR PURPOSE AND
 * NON-INFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE LIABLE
 * FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF CONTRACT,
 * TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR
 * THE USE OR OTHER DEALINGS IN THE SOFTWARE. SHOULD THE SOFTWARE PROVE DEFECTIVE
 * IN ANY RESPECT, THE USER ASSUMES THE COST OF ANY NECESSARY SERVICING, REPAIR OR
 * CORRECTION. THIS DISCLAIMER OF WARRANTY CONSTITUTES AN ESSENTIAL PART OF THIS LICENSE.
 * NO USE OF ANY SOFTWARE IS AUTHORIZED HEREUNDER EXCEPT UNDER THIS DISCLAIMER.
 * DESY HAS NO OBLIGATION TO PROVIDE MAINTENANCE, SUPPORT, UPDATES, ENHANCEMENTS,
 * OR MODIFICATIONS.
 * THE FULL LICENSE SPECIFYING FOR THE SOFTWARE THE REDISTRIBUTION, MODIFICATION,
 * USAGE AND OTHER RIGHTS AND OBLIGATIONS IS INCLUDED WITH THE DISTRIBUTION OF THIS
 * PROJECT IN THE FILE LICENSE.HTML. IF THE LICENSE IS NOT INCLUDED YOU MAY FIND A COPY
 * AT HTTP://WWW.DESY.DE/LEGAL/LICENSE.HTM
 *
 * $Id: DesyKrykCodeTemplates.xml,v 1.7 2010/04/20 11:43:22 bknerr Exp $
 */
package org.csstudio.config.ioconfig.view.actions;

import java.io.File;
import java.io.IOException;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

import org.apache.log4j.Logger;
import org.csstudio.config.ioconfig.model.Activator;
import org.csstudio.config.ioconfig.model.FacilityDBO;
import org.csstudio.config.ioconfig.model.IocDBO;
import org.csstudio.config.ioconfig.model.pbmodel.ProfibusSubnetDBO;
import org.csstudio.config.ioconfig.model.xml.ProfibusConfigXMLGenerator;
import org.csstudio.config.ioconfig.view.ProfiBusTreeView;
import org.csstudio.platform.logging.CentralLogger;
import org.eclipse.core.runtime.preferences.DefaultScope;
import org.eclipse.core.runtime.preferences.IEclipsePreferences;
import org.eclipse.jface.action.Action;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.DirectoryDialog;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.MessageBox;

/**
 * TODO (hrickens) :
 * 
 * @author hrickens
 * @author $Author: $
 * @since 08.10.2010
 */
public class CreateXMLConfigAction extends Action {

	private static final Logger LOG = CentralLogger.getInstance().getLogger(
			CreateXMLConfigAction.class);
	private final ProfiBusTreeView _pbtv;

	public CreateXMLConfigAction(@Nullable final String text,@Nonnull ProfiBusTreeView pbtv) {
		super(text);
		_pbtv = pbtv;
	}

	private void makeXMLFile(@Nullable final File path,
			@Nullable final ProfibusSubnetDBO subnet) {
		final ProfibusConfigXMLGenerator xml = new ProfibusConfigXMLGenerator(
				subnet.getName());
		xml.setSubnet(subnet);
		final File xmlFile = new File(path, subnet.getName() + ".xml");
		if (xmlFile.exists()) {
			final MessageBox box = new MessageBox(Display.getDefault()
					.getActiveShell(), SWT.ICON_WARNING | SWT.YES | SWT.NO);
			box.setMessage("The file " + xmlFile.getName()
					+ " exist! Overwrite?");
			final int erg = box.open();
			if (erg == SWT.YES) {
				try {
					xml.getXmlFile(xmlFile);
				} catch (final IOException e) {
					final MessageBox abortBox = new MessageBox(Display
							.getDefault().getActiveShell(), SWT.ICON_WARNING
							| SWT.ABORT);
					abortBox.setMessage("The file " + xmlFile.getName()
							+ " can not created!");
					abortBox.open();
				}
			}
		} else {
			try {
				xmlFile.createNewFile();
				xml.getXmlFile(xmlFile);
			} catch (final IOException e) {
				final MessageBox abortBox = new MessageBox(Display.getDefault()
						.getActiveShell(), SWT.ICON_WARNING | SWT.ABORT);
				abortBox.setMessage("The file " + xmlFile.getName()
						+ " can not created!");
				abortBox.open();
			}
		}
	}

	@Override
	public void run() {
		// TODO: Multi Selection XML Create.
		final String filterPathKey = "FilterPath";
		final IEclipsePreferences pref = new DefaultScope()
				.getNode(Activator.PLUGIN_ID);
		String filterPath = pref.get(filterPathKey, "");
		final DirectoryDialog dDialog = new DirectoryDialog(_pbtv.getShell());
		dDialog.setFilterPath(filterPath);
		filterPath = dDialog.open();
		final File path = new File(filterPath);
		pref.put(filterPathKey, filterPath);
		final Object selectedNode = _pbtv.getSelectedNode().getFirstElement();
		if (selectedNode instanceof ProfibusSubnetDBO) {
			final ProfibusSubnetDBO subnet = (ProfibusSubnetDBO) selectedNode;
			LOG.info("Create XML for Subnet: " + subnet);
			makeXMLFile(path, subnet);

		} else if (selectedNode instanceof IocDBO) {
			final IocDBO ioc = (IocDBO) selectedNode;
			LOG.info("Create XML for Ioc: " + ioc);
			for (final ProfibusSubnetDBO subnet : ioc.getProfibusSubnets()) {
				makeXMLFile(path, subnet);
			}
		} else if (selectedNode instanceof FacilityDBO) {
			final FacilityDBO facility = (FacilityDBO) selectedNode;
			LOG.info("Create XML for Facility: " + facility);
			for (final IocDBO ioc : facility.getIoc()) {
				for (final ProfibusSubnetDBO subnet : ioc.getProfibusSubnets()) {
					makeXMLFile(path, subnet);
				}
			}
		}
	}
}