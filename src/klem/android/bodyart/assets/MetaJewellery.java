package klem.android.bodyart.assets;

import java.io.Serializable;
import java.lang.reflect.Constructor;

import android.content.Context;

/**
 * Class Holding the vales needed to build a jewellery object
 * 
 * @author Big
 * 
 */
public class MetaJewellery implements Serializable {

	private float size;
	private float thickness;
	private int centerX;
	private int centerY;
	private int color;
	private Class clazz;
	public static final String EXTRA = "JBUNDLE";

	public MetaJewellery(float size, float thickness, int centerX, int centerY, int color) {
		this.size = size;
		this.thickness = thickness;
		this.centerX = centerX;
		this.centerY = centerY;
		this.color = color;
	}

	public AJewellery createJewellery(Context context, Class clazz) {
		this.clazz = clazz;
		Constructor<?> constructor;
		AJewellery instance = null;
		try {
			constructor = this.clazz.getConstructor(new Class[] { Context.class, MetaJewellery.class });
			instance = (AJewellery) constructor.newInstance(context, this);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return instance;
	}

	public float getSize() {
		return size;
	}

	public void setSize(float size) {
		this.size = size;
	}

	public float getThickness() {
		return thickness;
	}

	public void setThickness(float thickness) {
		this.thickness = thickness;
	}

	public int getCenterX() {
		return centerX;
	}

	public void setCenterX(int centerX) {
		this.centerX = centerX;
	}

	public int getCenterY() {
		return centerY;
	}

	public void setCenterY(int centerY) {
		this.centerY = centerY;
	}

	public int getColor() {
		return color;
	}

	public void setColor(int color) {
		this.color = color;
	}

	public Class getClazz() {
		return clazz;
	}

	public void setClazz(Class clazz) {
		this.clazz = clazz;
	}

}
