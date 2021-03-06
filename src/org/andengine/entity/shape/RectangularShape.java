package org.andengine.entity.shape;

import org.andengine.collision.RectangularShapeCollisionChecker;
import org.andengine.engine.camera.Camera;
import org.andengine.entity.primitive.Line;
import org.andengine.opengl.shader.ShaderProgram;

/**
 * (c) 2010 Nicolas Gramlich
 * (c) 2011 Zynga Inc.
 * 
 * @author Nicolas Gramlich
 * @since 11:37:50 - 04.04.2010
 */
public abstract class RectangularShape extends Shape implements IAreaShape {
	// ===========================================================
	// Constants
	// ===========================================================

	// ===========================================================
	// Fields
	// ===========================================================

	protected float mBaseWidth;
	protected float mBaseHeight;

	protected float mWidth;
	protected float mHeight;

	// ===========================================================
	// Constructors
	// ===========================================================

	public RectangularShape(final float pX, final float pY, final float pWidth, final float pHeight, final ShaderProgram pShaderProgram) {
		super(pX, pY, pShaderProgram);

		this.mBaseWidth = pWidth;
		this.mBaseHeight = pHeight;

		this.mWidth = pWidth;
		this.mHeight = pHeight;

		this.resetRotationCenter();
		this.resetScaleCenter();
		this.resetSkewCenter();
	}

	// ===========================================================
	// Getter & Setter
	// ===========================================================

	@Override
	public float getWidth() {
		return this.mWidth;
	}

	@Override
	public float getHeight() {
		return this.mHeight;
	}

	@Override
	public float getBaseWidth() {
		return this.mBaseWidth;
	}

	@Override
	public float getBaseHeight() {
		return this.mBaseHeight;
	}

	@Override
	public void setWidth(final float pWidth) {
		this.mWidth = pWidth;
		this.onUpdateVertices();
	}

	@Override
	public void setHeight(final float pHeight) {
		this.mHeight = pHeight;
		this.onUpdateVertices();
	}

	@Override
	public void setSize(final float pWidth, final float pHeight) {
		this.mWidth = pWidth;
		this.mHeight = pHeight;
		this.onUpdateVertices();
	}

	@Override
	public float getWidthScaled() {
		return this.getWidth() * this.mScaleX;
	}

	@Override
	public float getHeightScaled() {
		return this.getHeight() * this.mScaleY;
	}

	// ===========================================================
	// Methods for/from SuperClass/Interfaces
	// ===========================================================

	public void setBaseSize() {
		if(this.mWidth != this.mBaseWidth || this.mHeight != this.mBaseHeight) {
			this.mWidth = this.mBaseWidth;
			this.mHeight = this.mBaseHeight;
			this.onUpdateVertices();
		}
	}

	@Override
	protected boolean isCulled(final Camera pCamera) {
		return !RectangularShapeCollisionChecker.isVisible(pCamera, this);
	}

	@Override
	public void reset() {
		super.reset();
		this.setBaseSize();

		this.resetRotationCenter();
		this.resetSkewCenter();
		this.resetScaleCenter();
	}

	@Override
	public boolean contains(final float pX, final float pY) {
		return RectangularShapeCollisionChecker.checkContains(this, pX, pY);
	}

	@Override
	public float[] getSceneCenterCoordinates() {
		return this.convertLocalToSceneCoordinates(this.mWidth * 0.5f, this.mHeight * 0.5f);
	}

	@Override
	public boolean collidesWith(final IShape pOtherShape) {
		if(pOtherShape instanceof RectangularShape) {
			return RectangularShapeCollisionChecker.checkCollision(this, (RectangularShape) pOtherShape);
		} else if(pOtherShape instanceof Line) {
			return RectangularShapeCollisionChecker.checkCollision(this, (Line) pOtherShape);
		} else {
			return false;
		}
	}

	// ===========================================================
	// Methods
	// ===========================================================

	public void resetRotationCenter() {
		this.mRotationCenterX = this.mWidth * 0.5f;
		this.mRotationCenterY = this.mHeight * 0.5f;
	}

	public void resetScaleCenter() {
		this.mScaleCenterX = this.mWidth * 0.5f;
		this.mScaleCenterY = this.mHeight * 0.5f;
	}

	public void resetSkewCenter() {
		this.mSkewCenterX = this.mWidth * 0.5f;
		this.mSkewCenterY = this.mHeight * 0.5f;
	}

	// ===========================================================
	// Inner and Anonymous Classes
	// ===========================================================
}
