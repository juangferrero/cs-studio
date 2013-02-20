/**
 * 
 */
package org.csstudio.graphene.opiwidgets;

import org.csstudio.csdata.ProcessVariable;
import org.csstudio.opibuilder.model.AbstractPVWidgetModel;
import org.csstudio.opibuilder.model.AbstractWidgetModel;
import org.csstudio.opibuilder.properties.BooleanProperty;
import org.csstudio.opibuilder.properties.StringProperty;
import org.csstudio.opibuilder.properties.WidgetPropertyCategory;

/**
 * @author shroffk
 * 
 */
public class Line2DPlotWidgetModel extends AbstractWidgetModel {

    public final String ID = "org.csstudio.graphene.opiwidgets.Line2DPlot"; //$NON-NLS-1$
    
    public static final String PROP_XPVNAME = "x_pv_name"; //$NON-NLS-1$
    public static final String PROP_SHOW_AXIS = "show_axis"; //$NON-NLS-1$

    /*
     * (non-Javadoc)
     * 
     * @see
     * org.csstudio.opibuilder.model.AbstractWidgetModel#configureProperties()
     */
    @Override
    protected void configureProperties() {
	addProperty(new StringProperty(AbstractPVWidgetModel.PROP_PVNAME,
		"PV Name", WidgetPropertyCategory.Basic, ""));
	addProperty(new StringProperty(Line2DPlotWidgetModel.PROP_XPVNAME,
		"X PV Name", WidgetPropertyCategory.Basic, ""));
	addProperty(new BooleanProperty(Line2DPlotWidgetModel.PROP_SHOW_AXIS,
		"Show Axis", WidgetPropertyCategory.Basic, true));
    }

    public ProcessVariable getProcessVariable() {
	return new ProcessVariable(
		(String) getCastedPropertyValue(AbstractPVWidgetModel.PROP_PVNAME));
    }
    
    public String getXPvName(){
	return (String) getCastedPropertyValue(Line2DPlotWidgetModel.PROP_XPVNAME);
    }

    public boolean getShowAxis(){
	return getCastedPropertyValue(Line2DPlotWidgetModel.PROP_SHOW_AXIS);
    }
    /*
     * (non-Javadoc)
     * 
     * @see org.csstudio.opibuilder.model.AbstractWidgetModel#getTypeID()
     */
    @Override
    public String getTypeID() {
	return ID;
    }

}
