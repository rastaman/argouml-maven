package org.argouml.model.mdr;

import java.io.IOException;
import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Logger;

import javax.jmi.reflect.RefObject;
import javax.jmi.reflect.RefPackage;
import javax.jmi.xmi.MalformedXMIException;

import org.netbeans.api.xmi.XMIInputConfig;
import org.netbeans.api.xmi.XMIReferenceResolver;
import org.netbeans.lib.jmi.xmi.InputConfig;

class XmiReferenceResolverImpl2 implements XMIReferenceResolver, ArgoUMLMDRXmiReferencesResolver {

    private static final Logger LOG = Logger.getLogger(XmiReferenceResolverImpl2.class.getName());

    private RefPackage[] refPackages;
    private InputConfig config;
    private Map<String, XmiReference> objectToId;
    private Map<String, String> public2SystemIds;
    private Map<String, Map<String, Object>> idToObject;
    private List<String> searchPath;
    private boolean readOnly;
    private String publicId;
    private String systemId;
    private MDRModelImplementation modelImpl;
 
    private Map<XmiReferenceResolverKey,RefObject> references = new LinkedHashMap<XmiReferenceResolverKey,RefObject>();

    public XmiReferenceResolverImpl2(RefPackage[] refPackages, InputConfig config, Map<String, XmiReference> objectToId,
            Map<String, String> public2SystemIds, Map<String, Map<String, Object>> idToObject, List<String> searchPath,
            boolean readOnly, String publicId, String systemId, MDRModelImplementation modelImpl) {
        this.refPackages = refPackages;
        this.config = config;
        this.objectToId = objectToId;
        this.public2SystemIds = public2SystemIds;
        this.idToObject = idToObject;
        this.searchPath = searchPath;
        this.readOnly = readOnly;
        this.publicId = publicId;
        this.systemId = systemId;
        this.modelImpl = modelImpl;
    }

    /* (non-Javadoc)
     * @see org.argouml.model.mdr.ArgoUMLMDRXmiReferencesResolver#register(java.lang.String, java.lang.String, javax.jmi.reflect.RefObject)
     */
    @Override
    public void register(String systemId, String xmiId, RefObject object) {
        XmiReferenceResolverKey key = new XmiReferenceResolverKey(systemId, xmiId);
        if (!references.containsKey(key)) {
            references.put(key, object);
        } else {
            LOG.warning(key +" is already registered with object " + object);
        }
    }

    /* (non-Javadoc)
     * @see org.argouml.model.mdr.ArgoUMLMDRXmiReferencesResolver#resolve(org.netbeans.api.xmi.XMIReferenceResolver.Client, javax.jmi.reflect.RefPackage, java.lang.String, org.netbeans.api.xmi.XMIInputConfig, java.util.Collection)
     */
    @Override
    public void resolve(org.netbeans.api.xmi.XMIReferenceResolver.Client client, RefPackage extent, String systemId,
            XMIInputConfig configuration, Collection hrefs) throws MalformedXMIException, IOException {
        //
        LOG.info("Resolve "+systemId+" with configuration "+configuration+" and client "+client+" and hrefs "+hrefs);
    }

    public class XmiReferenceResolverKey {
        private String systemId;
        private String xmiId;

        public XmiReferenceResolverKey(String systemId, String xmiId) {
            super();
            this.systemId = systemId;
            this.xmiId = xmiId;
        }

        public String getSystemId() {
            return systemId;
        }

        public String getXmiId() {
            return xmiId;
        }

        @Override
        public int hashCode() {
            final int prime = 31;
            int result = 1;
            result = prime * result + getOuterType().hashCode();
            result = prime * result + ((systemId == null) ? 0 : systemId.hashCode());
            result = prime * result + ((xmiId == null) ? 0 : xmiId.hashCode());
            return result;
        }

        @Override
        public boolean equals(Object obj) {
            if (this == obj)
                return true;
            if (obj == null)
                return false;
            if (getClass() != obj.getClass())
                return false;
            XmiReferenceResolverKey other = (XmiReferenceResolverKey) obj;
            if (!getOuterType().equals(other.getOuterType()))
                return false;
            if (systemId == null) {
                if (other.systemId != null)
                    return false;
            } else if (!systemId.equals(other.systemId))
                return false;
            if (xmiId == null) {
                if (other.xmiId != null)
                    return false;
            } else if (!xmiId.equals(other.xmiId))
                return false;
            return true;
        }

        private ArgoUMLMDRXmiReferencesResolver getOuterType() {
            return XmiReferenceResolverImpl2.this;
        }

    }

    /* (non-Javadoc)
     * @see org.argouml.model.mdr.ArgoUMLMDRXmiReferencesResolver#clearIdMaps()
     */
    @Override
    public void clearIdMaps() {
        LOG.info("Clear ids map");
        // TODO Auto-generated method stub
    }

    /* (non-Javadoc)
     * @see org.argouml.model.mdr.ArgoUMLMDRXmiReferencesResolver#getIdToObjectMap()
     */
    @Override
    public Map<? extends String, ? extends Object> getIdToObjectMap() {
        LOG.info("getIdToObjectMap");
        // TODO Auto-generated method stub
        return null;
    }

    /* (non-Javadoc)
     * @see org.argouml.model.mdr.ArgoUMLMDRXmiReferencesResolver#getIdToObjectMaps()
     */
    @Override
    public Map<String, Map<String, Object>> getIdToObjectMaps() {
        LOG.info("getIdToObjectMaps");
        return null;
    }
}
