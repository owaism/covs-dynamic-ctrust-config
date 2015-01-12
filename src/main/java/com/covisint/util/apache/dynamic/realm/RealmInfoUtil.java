/**
 * 
 */
package com.covisint.util.apache.dynamic.realm;

import static com.covisint.util.apache.dynamic.util.Objects.listEqual;

import java.io.Externalizable;
import java.io.File;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectOutput;
import java.util.List;
import java.util.Map;

import com.covisint.util.apache.dynamic.main.CmdLineParams;
import com.covisint.util.apache.dynamic.main.CmdLineParams.ParamNamesE;
import com.covisint.util.apache.dynamic.realm.model.Attributes;
import com.covisint.util.apache.dynamic.realm.model.RealmInfo;
import com.covisint.util.apache.dynamic.util.FileUtil;
import com.covisint.util.apache.dynamic.util.NotEqualException;
import com.google.common.collect.ImmutableMap;

/**
 * Utilities to for Realm Information.
 * @author Owais
 */
public final class RealmInfoUtil {

    /**
     * Utility Class private constructor
     */
    private RealmInfoUtil() {
    }

    private static final String SERIALIZE_FILE_PATH = CmdLineParams.get().getParamValue(ParamNamesE.CACHE_FILE_LOCATION);

    /**
     * Checks the Serialized file to see if the realm information has chanced.
     * @param realmInfo
     * @return
     */
    public static boolean hasRealmInfoChange(List<RealmInfo> realmInfo) {
        File serializedFile = new File(SERIALIZE_FILE_PATH);

        RealmInfoSerializer existingInfo = FileUtil.deserialize(serializedFile);

        if (null != existingInfo) {
            try {
                listEqual(existingInfo.realmInfos, realmInfo);
            } catch (NotEqualException nee) {
                return true;
            }

            return false;
        }
        // If no exisiting info then this should be the first time.
        return true;

    }

    /**
     * Serializes the realm info to file.
     * @param realmInfos
     * @return
     */
    public static File serializeRealmInfo(List<RealmInfo> realmInfos) {
        RealmInfoSerializer serializer = new RealmInfoSerializer();
        serializer.realmInfos = realmInfos;
        File serializefile = new File(SERIALIZE_FILE_PATH);
        FileUtil.serialize(serializer, serializefile);
        return serializefile;
    }

    /**
     * Converts the attributes objects to attributes map.
     * Also replaces "." in the keys to "-". Mustache implementation is not that great a working with "." in keys.
     * @param realmInfo
     */
    public static void sanitize(RealmInfo realmInfo) {

        /*
         * Conversion being performed for the Realm Info Attributes.
         */
        realmInfo.setAttributeMap(convertAttributesToMap(realmInfo.getAttributes()));

        /*
         * Conversion being performed for the attributes of Realm within the realm Info.
         */
        realmInfo.getRealm().setAttributeMap(convertAttributesToMap(realmInfo.getRealm().getAttributes()));
    }

    private static Map<String, String> convertAttributesToMap(List<Attributes> attributes) {

        ImmutableMap.Builder<String, String> mapBuilder = new ImmutableMap.Builder<String, String>();

        if (null != attributes) {
            for (Attributes attribute : attributes) {
                mapBuilder.put(attribute.getKey().replace(".", "-"), attribute.getValue());
            }
        }
        return mapBuilder.build();
    }

    /**
     * Just a container object to be used for serialization.
     * @author Owais
     *
     */
    public static class RealmInfoSerializer implements Externalizable {
        
        private List<RealmInfo> realmInfos;

        @Override
        public void writeExternal(ObjectOutput out) throws IOException {
            out.writeObject(realmInfos);

        }

        @SuppressWarnings("unchecked")
        @Override
        public void readExternal(ObjectInput in) throws IOException, ClassNotFoundException {
            realmInfos = (List<RealmInfo>) in.readObject();

        }

        @Override
        public boolean equals(Object obj) {

            if (!(obj instanceof RealmInfoSerializer)) {
                return false;
            }

            RealmInfoSerializer object2 = (RealmInfoSerializer) obj;

            if (object2.realmInfos.size() != this.realmInfos.size()) {
                return false;
            }

            // TODO Auto-generated method stub
            return super.equals(obj);
        }

    }

}
