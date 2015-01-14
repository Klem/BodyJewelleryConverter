package klem.android.bodyart.assets;

import java.io.Serializable;
import java.lang.reflect.Constructor;

import android.content.Context;

public class MetaScrew implements Serializable {

	private float size;
	int align;
	private int centerX;
	private int centerY;
	private int color;
	private Class clazz;
	public static final String EXTRA = "SBUNDLE";

	public MetaScrew(float size, int align, int centerX, int centerY, int color) {
		this.size = size;
		this.align = align;
		this.centerX = centerX;
		this.centerY = centerY;
		this.color = color;
	}

	public AScrew createScrew(Context context, Class clazz) {
		Class<?> c = clazz;
		Constructor<?> constructor;
		AScrew instance = null;
		try {
			constructor = c.getConstructor(new Class[] { Context.class, MetaScrew.class });
			instance = (AScrew) constructor.newInstance(context, this);
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

	public int getAlign() {
		return align;
	}

	public void setAlign(int align) {
		this.align = align;
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

}
