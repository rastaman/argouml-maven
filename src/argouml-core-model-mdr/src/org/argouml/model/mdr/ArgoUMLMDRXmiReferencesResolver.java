package org.argouml.model.mdr;

import java.util.Map;

import org.netbeans.api.xmi.XMIReferenceResolver;

interface ArgoUMLMDRXmiReferencesResolver extends XMIReferenceResolver {

    void clearIdMaps();

    Map<? extends String, ? extends Object> getIdToObjectMap();

    Map<String, Map<String, Object>> getIdToObjectMaps();

}