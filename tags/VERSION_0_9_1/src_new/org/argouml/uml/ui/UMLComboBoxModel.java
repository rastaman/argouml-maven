// Copyright (c) 1996-2001 The Regents of the University of California. All
// Rights Reserved. Permission to use, copy, modify, and distribute this
// software and its documentation without fee, and without a written
// agreement is hereby granted, provided that the above copyright notice
// and this paragraph appear in all copies.  This software program and
// documentation are copyrighted by The Regents of the University of
// California. The software program and documentation are supplied "AS
// IS", without any accompanying services from The Regents. The Regents
// does not warrant that the operation of the program will be
// uninterrupted or error-free. The end-user understands that the program
// was developed for research purposes and is advised not to rely
// exclusively on the program for any reason.  IN NO EVENT SHALL THE
// UNIVERSITY OF CALIFORNIA BE LIABLE TO ANY PARTY FOR DIRECT, INDIRECT,
// SPECIAL, INCIDENTAL, OR CONSEQUENTIAL DAMAGES, INCLUDING LOST PROFITS,
// ARISING OUT OF THE USE OF THIS SOFTWARE AND ITS DOCUMENTATION, EVEN IF
// THE UNIVERSITY OF CALIFORNIA HAS BEEN ADVISED OF THE POSSIBILITY OF
// SUCH DAMAGE. THE UNIVERSITY OF CALIFORNIA SPECIFICALLY DISCLAIMS ANY
// WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED WARRANTIES OF
// MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE. THE SOFTWARE
// PROVIDED HEREUNDER IS ON AN "AS IS" BASIS, AND THE UNIVERSITY OF
// CALIFORNIA HAS NO OBLIGATIONS TO PROVIDE MAINTENANCE, SUPPORT,
// UPDATES, ENHANCEMENTS, OR MODIFICATIONS.

package org.argouml.uml.ui;
import org.argouml.uml.*;
import javax.swing.event.*;
import javax.swing.*;
import java.lang.reflect.*;
import ru.novosoft.uml.*;
import java.awt.event.*;
import java.util.*;
import ru.novosoft.uml.foundation.data_types.*;
import ru.novosoft.uml.foundation.core.*;
import ru.novosoft.uml.model_management.*;


public class UMLComboBoxModel extends AbstractListModel implements
    ComboBoxModel, UMLUserInterfaceComponent, ActionListener {

    private UMLUserInterfaceContainer _container;
    private String _property;
    //
    //   this method returns a boolean indicating
    //      whether the specific element should be allowed in the combo box
    private Method _filter;
    private Method _getMethod;
    private Method _setMethod;
    private TreeSet _set;
    private Object _selectedItem;
    private MModel _model;
    private boolean _allowVoid;
    private Object[] _noArgs = {};
    private boolean _shouldBeEnabled;
    private boolean _addElementsFromProfileModel;


    /**
     *   This method creates a UMLComboBoxModel
     *
     *    @param container container that provides access to target, formatting etc
     *    @param filter name of method on container that takes a MModelElement
     *         true if element should be in list, may be null
     *    @param property name of event that would indicate that the value has changed
     *    @param getMethod name of method on container to get value
     *    @param putMethod name of method on container to set value
     *    @param allowVoid allows an entry in the list
     *    @param elementType base type for all elements
     */
    public UMLComboBoxModel(UMLUserInterfaceContainer container,
        String filter,String property,String getMethod,
        String setMethod,boolean allowVoid,Class elementType,
        boolean addElementsFromProfileModel) {
        _container = container;
        _property = property;
        _allowVoid = allowVoid;
        _addElementsFromProfileModel = addElementsFromProfileModel;
        _set = new TreeSet();
        if(filter != null) {
            try {
                _filter = _container.getClass().getMethod(filter,new Class[] { MModelElement.class });
            }
            catch(Exception e) {
                e.printStackTrace();
            }
        }
        if(getMethod != null) {
            Class[] getArgs = {};
            try {
                _getMethod = container.getClass().getMethod(getMethod,getArgs);
            }
            catch(Exception e) {
                _shouldBeEnabled = false;
                System.out.println(getMethod + " not found in UMLComboBoxModel(): " + e.toString());
                e.printStackTrace();
            }
        }

        if(setMethod != null) {
            Class[] setArgs = { elementType };
            try {
                _setMethod = container.getClass().getMethod(setMethod,setArgs);
            }
            catch(Exception e) {
                _shouldBeEnabled = false;
                System.out.println(setMethod + " not found in UMLComboBoxModel(): " + e.toString());
                e.printStackTrace();
            }
        }
    }


    public void collectElements(MNamespace ns,Profile profile,boolean isPhantom) {
        Collection collection = ns.getOwnedElements();
        if(collection != null) {
            Iterator iter = collection.iterator();
            MModelElement element;
            while(iter.hasNext()) {
                element = (MModelElement) iter.next();
                if(isAcceptible(element)) {
                    UMLComboBoxEntry entry = new UMLComboBoxEntry(element,profile,isPhantom);
                    boolean addMe = true;
                    if(isPhantom) {
                        String shortName = entry.getShortName();
                        String setName;
                        //
                        //  scan tree to see if short name is already there
                        //
                        Iterator setIter = _set.iterator();
                        while(setIter.hasNext()) {
                            setName = ((UMLComboBoxEntry) setIter.next()).getShortName();
                            if(setName.equals(shortName)) {
                                addMe = false;
                                break;
                            }
                        }
                    }
                    if(addMe) {
                        _set.add(entry);
                    }
                }
                if(element instanceof MNamespace) {
                    collectElements((MNamespace) element,profile,isPhantom);
                }
            }
        }
    }


    public void targetChanged() {
        Object target = _container.getTarget();
        if(target instanceof MModelElement) {
            MModelElement element = (MModelElement) target;
            MModel model = element.getModel();
            //
            //   should not need to do this
            //
            if(model == null) {
                System.out.println("Error: getModel() == null for " + target.getClass().toString() + " in UMLComboBoxModel");
            }

            if(model != _model) {
                _model = model;
                _set.clear();
                Profile profile = _container.getProfile();
                if(_allowVoid) {
                    _set.add(new UMLComboBoxEntry(null,profile,false));
                }
                if(_model != null) {
                    collectElements(_model,profile,false);
                }
                if(_addElementsFromProfileModel) {
                    MModel profileModel = profile.getProfileModel();
                    if(profileModel != null) {
                        collectElements(profileModel,profile,true);
                    }
                }
                //
                //   scan for name collisions
                //
                Iterator iter = _set.iterator();
                String before = null;
                UMLComboBoxEntry currentEntry = null;
                String current = null;
                UMLComboBoxEntry afterEntry = null;
                String after = null;
                while(iter.hasNext()) {
                    before = current;
                    currentEntry = afterEntry;
                    current = after;
                    afterEntry = (UMLComboBoxEntry) iter.next();
                    after = afterEntry.getShortName();
                    if(currentEntry != null) currentEntry.checkCollision(before,after);
                }
                if(afterEntry != null) afterEntry.checkCollision(current,null);

                fireContentsChanged(this,0,_set.size());
            }

            //
            //   get current value
            //
            try {
                Object current = _getMethod.invoke(_container,_noArgs);
                Iterator iter = _set.iterator();
                UMLComboBoxEntry entry;
                while(iter.hasNext()) {
                    entry = (UMLComboBoxEntry) iter.next();
                    if(!entry.isPhantom() && entry.getElement(model) == current) {
                        _selectedItem = entry;
                    }
                }
            }
            catch(Exception e) {
                e.printStackTrace();
                _selectedItem = null;
            }
        }
    }


    public void targetReasserted() {
    }

    public void updateElement(MModelElement addedElement) {
        //
        //   check if element is of the right type
        //
        if(isAcceptible(addedElement)) {
            //
            //   double check that it doesn't have an entry already
            //
            boolean inSet = false;
            UMLComboBoxEntry existingEntry;
            String addedName = addedElement.getName();
            Iterator iter = _set.iterator();
            while(iter.hasNext() && !inSet) {
                existingEntry = (UMLComboBoxEntry) iter.next();
                //
                //  if this is a phantom
                //
                if(existingEntry.isPhantom()) {
                    //
                    //  if the added entry has the same name as a phantom
                    if(addedName != null && addedName.equals(existingEntry.getShortName())) {
                        existingEntry.setElement(addedElement,false);
                        inSet = true;
                    }
                }
                else {
                    inSet = (addedElement == existingEntry.getElement(null));
                }
            }

            if(!inSet) {
                _set.add(new UMLComboBoxEntry(addedElement,_container.getProfile(),false));
                fireContentsChanged(this,0,_set.size());
            }
        }
    }


    public void roleAdded(final MElementEvent event) {
        String eventName = event.getName();
        if(eventName != null && eventName.equals("ownedElement")) {
            MModelElement addedElement = (MModelElement) event.getAddedValue();
            updateElement(addedElement);
        }
    }


    public void recovered(final MElementEvent p1) {
    }
    public void roleRemoved(final MElementEvent p1) {
    }
    public void listRoleItemSet(final MElementEvent p1) {
    }

    public void removed(final MElementEvent event) {
        Object source = event.getSource();
        Iterator iter = _set.iterator();
        UMLComboBoxEntry entry = null;
        while(iter.hasNext()) {
            entry = (UMLComboBoxEntry) iter.next();
            if(!entry.isPhantom() && entry.getElement(null) == source) {
                _set.remove(entry);
                break;
            }
        }
    }

    public void propertySet(final MElementEvent event) {
        String eventName = event.getName();
        if(eventName != null && eventName.equals("name")) {
            Object source = event.getSource();
            //
            //    see if the source is of the right type
            //
            if(source instanceof MModelElement &&
                isAcceptible((MModelElement) source)) {
                //
                //  see if there is an entry with that element
                //
                Iterator iter = _set.iterator();
                UMLComboBoxEntry entry;
                for(int i = 0;iter.hasNext();i++) {
                    entry = (UMLComboBoxEntry) iter.next();
                    if(!entry.isPhantom() && entry.getElement(null) == source) {
                        entry.updateName();
                        fireContentsChanged(this,i,i);
                        break;
                    }
                }
            }
        }
    }



    public void setSelectedItem(final java.lang.Object selection) {
        _selectedItem = selection;
    }

    public Object getSelectedItem() {
        return _selectedItem;
    }


    public void actionPerformed(ActionEvent event) {
        if(_selectedItem != null) {
            UMLComboBoxEntry entry = (UMLComboBoxEntry) _selectedItem;
            MModelElement newValue = null;
            MModel model = null;
            Object target = _container.getTarget();
            if(target instanceof MModelElement) {
                model = ((MModelElement) target).getModel();
            }
            newValue = entry.getElement(model);
            try {
                _setMethod.invoke(_container,new Object[] { newValue });
            }
            catch(Exception e) {
                e.printStackTrace();
            }
        }
    }

    public int getSize() {
        return _set.size();
    }

    public java.lang.Object getElementAt(int index) {
        Object element = null;
        Iterator iter = _set.iterator();
        for(int i = 0; iter.hasNext(); i++) {
            element = iter.next();
            if(i == index) {
                return element;
            }
        }
        return null;
    }

    public boolean isAcceptible(MModelElement element) {
        boolean isAcceptible = false;
        try {
            Boolean boo = (Boolean) _filter.invoke(_container,new Object[] { element });
            if(boo != null) isAcceptible = boo.booleanValue();
        }
        catch(Exception e) {
            e.printStackTrace();
        }
        return isAcceptible;
    }

}