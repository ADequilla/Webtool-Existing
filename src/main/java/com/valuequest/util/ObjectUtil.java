package com.valuequest.util;

import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.util.Collection;
import java.util.Iterator;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ObjectUtil { 
	
	@SuppressWarnings("unused")
	private transient static final Logger logger = LoggerFactory.getLogger(ObjectUtil.class);
	
	/**
	 * Check java.util.Collection whether empty or not
	 * @param collection
	 * @return true if not empty
	 */
	@SuppressWarnings("rawtypes")
	public static boolean isNotEmpty(Collection collection){
		if(collection != null && collection.size() > 0) return true;
		else return false;
	}
	
	/**
	 * Check java.util.Map whether empty or not
	 * @param collection
	 * @return true if not empty
	 */
	public static boolean isNotEmpty(Map collection){
        if(collection != null && collection.size() > 0) return true;
        else return false;
    }

	/**
	 * Check array of java.lang.String empty or not.
	 * @param args
	 * @return true if object is not empty
	 */
	public static boolean isNotEmpty(String[] args) {
		if(args != null && args.length > 0) return true;
		else return false;
	}
	
	public static String describe(Object o) {
		StringBuffer buffer = new StringBuffer();
		try {
			Class c = o.getClass();

			PropertyDescriptor[] descriptors = Introspector.getBeanInfo(c).getPropertyDescriptors();

			for (int i = 0; i < descriptors.length; i++) {
				PropertyDescriptor descriptor = descriptors[i];
				buffer.append(descriptor.getDisplayName());
				buffer.append(" : ");
				
				Object value = descriptor.getReadMethod().invoke(o, null);
				if (value instanceof Collection) {
					buffer.append(describe((Collection)value));
				}
				else {
					buffer.append(value);
				}
				
				buffer.append("\n");
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return buffer.toString();
	}
	public static String describe(Collection coll) {
		StringBuffer buffer = new StringBuffer();
		try {
			buffer.append("Size (" + coll.size() + ") ");
			buffer.append("[");
			for (Iterator iterator = coll.iterator(); iterator.hasNext();) {
				buffer.append(describe(iterator.next()));
				buffer.append(",");
			}
			buffer.append("]");
		}
		catch (Exception e) {
			e.printStackTrace();
		}
		return buffer.toString();
	}

}
