// $Id$
// Copyright (c) 1996-2004 The Regents of the University of California. All
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

package org.argouml.model.uml.foundation.extensionmechanisms;

import java.util.Collection;

import org.argouml.model.uml.foundation.core.CoreFactory;
import org.argouml.util.CheckUMLModelHelper;

import ru.novosoft.uml.foundation.core.MNamespace;
import ru.novosoft.uml.foundation.extension_mechanisms.MStereotype;

import junit.framework.TestCase;

/**
 * @since Oct 10, 2002
 * @author jaap.branderhorst@xs4all.nl
 */
public class TestExtensionMechanismsHelper extends TestCase {

    /**
     * Constructor for TestExtensionMechanismsHelper.
     *
     * @param arg0 is the name of the test case.
     */
    public TestExtensionMechanismsHelper(String arg0) {
        super(arg0);
    }
    
    /**
     * This test does not work yet since there are problems with 
     * isolating the project from the projectbrowser.
     */
    public void testGetAllPossibleStereotypes1() {
        MNamespace ns = CoreFactory.getFactory().createNamespace();
        Object clazz = CoreFactory.getFactory().buildClass(ns);
        MStereotype stereo1 =
	    ExtensionMechanismsFactory.getFactory().buildStereotype(clazz,
								    "test1");
        MStereotype stereo2 =
	    ExtensionMechanismsFactory.getFactory().buildStereotype(clazz,
								    "test2");
        Collection col =
	    ExtensionMechanismsHelper.getHelper()
	        .getAllPossibleStereotypes(clazz);
        assertTrue("stereotype not in list of possible stereotypes",
		   col.contains(stereo1));
        assertTrue("stereotype not in list of possible stereotypes",
		   col.contains(stereo2));
    }
    
    /**
     * Test if we can create modelelements with the names given.
     */
    public void testGetMetaModelName() {
        CheckUMLModelHelper.metaModelNameCorrect(this,
		ExtensionMechanismsFactory.getFactory(),
		TestExtensionMechanismsFactory.getAllModelElements());
    }
    
    /**
     * Test if we can create a valid stereotype for all the modelelements.
     */
    public void testIsValidStereoType() {
        CheckUMLModelHelper.isValidStereoType(this,
		ExtensionMechanismsFactory.getFactory(),
                TestExtensionMechanismsFactory.getAllModelElements());
    }
}
