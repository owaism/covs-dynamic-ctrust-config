/**
 * 
 */
package com.covisint.util.apache.dynamic.util;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;


/**
 * @author Owais
 */
public final class Objects {

    /**
     * Util Classes
     */
    private Objects() {
    }

    public static final void equal(Object a, Object b) throws NotEqualException {
        if (!com.google.common.base.Objects.equal(a, b)) {
            throw new NotEqualException(a, b);
        }
    }
    
    public static final <k extends Comparable<k>,l extends Comparable<l>> void listEqual(List<k> alist, List<l> blist) throws NotEqualException{
        List<k> alistCopy = new ArrayList<k>(alist);
        List<l> blistCopy = new ArrayList<l>(blist);
        
        Collections.sort(alistCopy);
        Collections.sort(blistCopy);
        
        if(!alistCopy.equals(blistCopy)){
            throw new NotEqualException(alistCopy, blistCopy);
        }
    }

}
