/*
 * Copyright (c) 2006 Stiftung Deutsches Elektronen-Synchrotron,
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
 */
/*
 * $Id$
 */
package org.csstudio.utility.tine.reader;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.csstudio.utility.namespace.utility.ControlSystemItem;
import org.csstudio.utility.namespace.utility.NameSpaceSearchResult;

/**
 * @author hrickens
 * @author $Author$
 * @version $Revision$
 * @since 15.05.2007
 */
public class TineSearchResult extends NameSpaceSearchResult {

    /** The list with results from Tine namespace server.*/
    private List<ControlSystemItem> _csiResult = Collections.emptyList();


    /** {@inheritDoc}*/
    @Override
    public final NameSpaceSearchResult getNew() {
        return new TineSearchResult();
    }

    /** {@inheritDoc}*/
    @Override
    public final List<ControlSystemItem> getCSIResultList() {
        return _csiResult;
    }

    /** {@inheritDoc}*/
    @Override
    public final void notifyView() {
        setChanged();
        notifyObservers();
    }

    /**
     * Copies the list of {@link ControlSystemItem}.
     * {@inheritDoc}
     */
    @Override
    public final void setCSIResultList(final List<ControlSystemItem> resultList) {
        _csiResult = new ArrayList<ControlSystemItem>(resultList);
    }
}
