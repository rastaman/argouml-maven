// $Id$
// Copyright (c) 1996-2005 The Regents of the University of California. All
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

package org.argouml.uml.ui.foundation.core;

import java.awt.event.ActionEvent;
import java.beans.PropertyChangeEvent;

import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.border.TitledBorder;

import org.argouml.i18n.Translator;
import org.argouml.model.AttributeChangeEvent;
import org.argouml.model.Model;
import org.argouml.ui.LookAndFeelMgr;
import org.argouml.uml.ui.ActionDeleteSingleModelElement;
import org.argouml.uml.ui.ActionNavigateOwner;
import org.argouml.uml.ui.UMLAction;
import org.argouml.uml.ui.UMLComboBox2;
import org.argouml.uml.ui.UMLComboBoxModel2;
import org.argouml.uml.ui.UMLComboBoxNavigator;
import org.argouml.uml.ui.UMLPlainTextDocument;
import org.argouml.uml.ui.UMLTextArea2;
import org.argouml.uml.ui.UMLTextField2;
import org.argouml.util.ConfigLoader;
import org.tigris.swidgets.GridLayout2;

/**
 * A property panel for methods.
 *
 * @author thn@tigris.org
 */
public class PropPanelMethod extends PropPanelFeature {

    private JTextField languageTextField;
    private UMLComboBox2 specificationComboBox;
    private static UMLMethodSpecificationComboBoxModel specificationComboBoxModel;
    private UMLModelElementLanguageDocument languageDocument =
        new UMLModelElementLanguageDocument();

    ////////////////////////////////////////////////////////////////
    // contructors
    /**
     * The constructor.
     */
    public PropPanelMethod() {
        super("Method", lookupIcon("Method"), ConfigLoader
                .getTabPropsOrientation());
        UMLPlainTextDocument uptd = new UMLMethodBodyDocument();

        addField(Translator.localize("label.name"),
                getNameTextField());

        addField(Translator.localize("label.owner"),
                getOwnerScroll());
        
        /* The specification field shows the Operation: */
        addField(Translator.localize("label.specification"),
                new UMLComboBoxNavigator(
                        this,
                        Translator.localize("label.specification.navigate.tooltip"),
                        getSpecificationComboBox()));

        add(getVisibilityPanel());
        
        JPanel modifiersPanel = new JPanel(new GridLayout2(0, 3,
                GridLayout2.ROWCOLPREFERRED));
        modifiersPanel.setBorder(new TitledBorder(Translator.localize(
                "label.modifiers")));
        modifiersPanel.add(new UMLBehavioralFeatureQueryCheckBox());
        modifiersPanel.add(new UMLFeatureOwnerScopeCheckBox());
        add(modifiersPanel);
        
        addSeperator();

        addField(Translator.localize("label.language"),
                getLanguageTextField());

        UMLTextArea2 bodyArea = new UMLTextArea2(uptd);
        bodyArea.setLineWrap(true);
        bodyArea.setRows(5);
        bodyArea.setFont(LookAndFeelMgr.getInstance().getSmallFont());
        JScrollPane pane = new JScrollPane(bodyArea);
        addField(Translator.localize("label.body"), pane);

        addAction(new ActionNavigateOwner());
        addAction(new ActionDeleteSingleModelElement());
    }

    /**
     * @return a textfield for the name
     */
    protected JTextField getLanguageTextField() {
        if (languageTextField == null) {
            languageTextField = new UMLTextField2(languageDocument);
        }
        return languageTextField;
    }

    public UMLComboBox2 getSpecificationComboBox() {
        if (specificationComboBox == null) {
            if (specificationComboBoxModel == null) {
                specificationComboBoxModel =
                    new UMLMethodSpecificationComboBoxModel();
            }
            specificationComboBox =
                new UMLComboBox2(
                        specificationComboBoxModel,
                                 new ActionSetMethodSpecification());
        }
        return specificationComboBox;
    }

    private class UMLMethodSpecificationComboBoxModel extends UMLComboBoxModel2 {

        public UMLMethodSpecificationComboBoxModel() {
            super("specification", false);
            Model.getPump().addClassModelEventListener(this,
                    Model.getMetaTypes().getOperation(), "method");
        }

        protected boolean isValidElement(Object element) {
            return Model.getFacade().getSpecification(getTarget()) == element;
        }

        protected void buildModelList() {
            if (getTarget() != null) {
                removeAllElements();
                Object classifier = Model.getFacade().getOwner(getTarget());
                addAll(Model.getFacade().getOperations(classifier));
            }
        }

        protected Object getSelectedModelElement() {
            return Model.getFacade().getSpecification(getTarget());
        }
        
        public void propertyChange(PropertyChangeEvent evt) {
            if (evt instanceof AttributeChangeEvent) {
                if (evt.getPropertyName().equals("specification")) {
                    if (evt.getSource() == getTarget()
                            && (getChangedElement(evt) != null)) {
                        Object elem = getChangedElement(evt);
                        setSelectedItem(elem);
                    }
                }
            }
        }
    }

    private class ActionSetMethodSpecification extends UMLAction {

        /**
         * Constructor for ActionSetStructuralFeatureType.
         */
        protected ActionSetMethodSpecification() {
            super(Translator.localize("Set"), false, NO_ICON);
        }

        /**
         * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
         */
        public void actionPerformed(ActionEvent e) {
            super.actionPerformed(e);
            Object source = e.getSource();
            Object oldOperation = null;
            Object newOperation = null;
            Object method = null;
            if (source instanceof UMLComboBox2) {
                UMLComboBox2 box = (UMLComboBox2) source;
                Object o = box.getTarget(); // the method
                if (Model.getFacade().isAMethod(o)) {
                    method = o;
                    oldOperation = Model.getFacade().getSpecification(method);
                }
                o = box.getSelectedItem(); // the selected operation
                if (Model.getFacade().isAOperation(o)) {
                    newOperation = o;
                }
            }
            if (newOperation != oldOperation && method != null) {
                Model.getCoreHelper().setSpecification(method, newOperation);
            }
        }
    }

    private class UMLModelElementLanguageDocument extends UMLPlainTextDocument {

        /**
         * Constructor for UMLModelElementNameDocument.
         */
        public UMLModelElementLanguageDocument() {
             super("language");
        }

        /**
         * @see org.argouml.uml.ui.UMLPlainTextDocument#setProperty(java.lang.String)
         */
        protected void setProperty(String text) {
            Object meth = getTarget();
            if (Model.getFacade().isAMethod(meth)) {
                Object expr = Model.getFacade().getBody(meth);
                if (expr != null) {
                    Model.getDataTypesHelper().setLanguage(expr, text);
                } else {
                    Model.getCoreHelper().setBody(meth,
                            Model.getDataTypesFactory()
                            .createProcedureExpression(text, null));
                }
            }
        }

        /**
         * @see org.argouml.uml.ui.UMLPlainTextDocument#getProperty()
         */
        protected String getProperty() {
            Object expr = Model.getFacade().getBody(getTarget());
            if (expr == null) {
                return null;
            } else {
                return Model.getDataTypesHelper().getLanguage(expr);
            }
        }

    }

    private class UMLMethodBodyDocument extends UMLPlainTextDocument {
        /**
         * Constructor for UMLMethodBodyDocument.
         */
        public UMLMethodBodyDocument() {
            super("body");
            /*
             * TODO: This is probably not the right location
             * for switching off the "filterNewlines".
             * The setting gets lost after selecting a different
             * ModelElement in the diagram.
             * BTW, see how it is used in
             * javax.swing.text.PlainDocument.
             * See issue 1812.
             */
            putProperty("filterNewlines", Boolean.FALSE);
        }

        /**
         * @see org.argouml.uml.ui.UMLPlainTextDocument#setProperty(java.lang.String)
         */
        protected void setProperty(String text) {
            Object meth = getTarget();
            if (Model.getFacade().isAMethod(meth)) {
                Object expr = Model.getFacade().getBody(meth);
                if (expr != null) {
                    Model.getDataTypesHelper().setBody(expr, text);
                } else {
                    Model.getCoreHelper().setBody(meth,
                            Model.getDataTypesFactory()
                            .createProcedureExpression(null, text));
                }
            }
        }

        /**
         * @see org.argouml.uml.ui.UMLPlainTextDocument#getProperty()
         */
        protected String getProperty() {
            Object expr = Model.getFacade().getBody(getTarget());
            if (expr == null) {
                return null;
            } else {
                return (String) Model.getFacade().getBody(expr);
            }
        }
    }

} /* end class PropPanelMethod */
