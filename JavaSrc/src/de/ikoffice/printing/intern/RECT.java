/**
 *        (c) 2007-2008 IKOffice GmbH
 *
 *        http://www.ikoffice.de
 *        
 * This program and the accompanying materials are made available 
 * under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * @author 
 * Jun Huang, (huangjun78@gmail.com, junhuang@ikoffice.de) 
 */
package de.ikoffice.printing.intern;

import org.xvolks.jnative.exceptions.NativeException;
import org.xvolks.jnative.misc.basicStructures.AbstractBasicData;
import org.xvolks.jnative.misc.basicStructures.LONG;
import org.xvolks.jnative.pointers.Pointer;
import org.xvolks.jnative.pointers.memory.GlobalMemoryBlock;

/**
 * @author       Jun Huang
 */
public class RECT extends AbstractBasicData<RECT> {
	public LONG left;
	public LONG top;
	public LONG right;
	public LONG bottom;
	

	public RECT() throws NativeException {
		super(null);
		mValue = this;
		createPointer();
	}

	/* (non-Javadoc)
	 * @see org.xvolks.jnative.misc.basicStructures.BasicData#createPointer()
	 */
	public Pointer createPointer() throws NativeException {
		pointer = new Pointer(new GlobalMemoryBlock(getSizeOf()));
        return pointer;
	}

	/* (non-Javadoc)
	 * @see org.xvolks.jnative.misc.basicStructures.BasicData#getSizeOf()
	 */
	public int getSizeOf() {
		return 16;
	}

	/* (non-Javadoc)
	 * @see org.xvolks.jnative.misc.basicStructures.BasicData#getValueFromPointer()
	 */
	public RECT getValueFromPointer() throws NativeException {
		offset = 0;
		left = new LONG(getNextInt());
		top = new LONG(getNextInt());
		right = new LONG(getNextInt());
		bottom = new LONG(getNextInt());
		
		return getValue();
	}

	public void setLeft(LONG left) throws NativeException {
		this.left = left;
		pointer.setIntAt(0, left.getValue());
	}

	public void setTop(LONG top) throws NativeException {
		this.top = top;
		pointer.setIntAt(4, top.getValue());
	}

	public void setRight(LONG right) throws NativeException {
		this.right = right;
		pointer.setIntAt(8, right.getValue());
	}

	public void setBottom(LONG bottom) throws NativeException {
	    this.bottom = bottom;
		pointer.setIntAt(12, bottom.getValue());
	}
	
	

}
