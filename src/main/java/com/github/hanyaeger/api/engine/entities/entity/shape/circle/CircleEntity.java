package com.github.hanyaeger.api.engine.entities.entity.shape.circle;

import com.github.hanyaeger.api.engine.entities.entity.Coordinate2D;
import com.github.hanyaeger.api.engine.entities.entity.YaegerEntity;
import com.github.hanyaeger.api.engine.entities.entity.shape.CenteredShapeEntity;
import com.google.inject.Injector;
import javafx.scene.shape.Circle;

/**
 * A {@link CircleEntity} provides the option to use a drawable Circle as an
 * {@link YaegerEntity}. As opposed to some of the other shapes
 * ({@link com.github.hanyaeger.api.engine.entities.entity.shape.rectangle.RectangleEntity}, for instance),
 * the reference point of a {@link CircleEntity} is its center.
 * <p>
 * For a {@link CircleEntity} it is possible to set the radius, through the methods {@link CircleEntity#setRadius(double)}.
 * By default, this value will be set to 1.
 */
public abstract class CircleEntity extends CenteredShapeEntity<Circle> {

    static final double DEFAULT_RADIUS = 1;
    private double radius = DEFAULT_RADIUS;

    /**
     * Create a new {@link CircleEntity} on the given {@code initialPosition}.
     *
     * @param initialPosition The initial position at which this {@link CircleEntity} should be placed
     */
    public CircleEntity(final Coordinate2D initialPosition) {
        super(initialPosition);
    }

    /**
     * Set the radius of the circle.
     *
     * @param radius The {@code radius} of the circle as a {@code double}
     */
    public void setRadius(final double radius) {
        shape.ifPresentOrElse(circle -> circle.setRadius(radius), () -> this.radius = radius);
    }

    /**
     * Return the {@code radius} of this {@link CircleEntity}.
     *
     * @return The {@code radius} as a {@code double}.
     */
    public double getRadius() {
        if (shape.isPresent()) {
            return shape.get().getRadius();
        } else {
            return radius;
        }
    }


    @Override
    public void setReferenceX(double x) {
        shape.ifPresentOrElse(circle -> circle.setCenterX(x), () -> this.x = x);
    }

    @Override
    public void setReferenceY(double y) {
        shape.ifPresentOrElse(circle -> circle.setCenterY(y), () -> this.y = y);
    }

    @Override
    public double getTopY() {
        return super.getTopY() + radius;
    }

    @Override
    public double getLeftX() {
        return super.getLeftX() + radius;
    }

    @Override
    public void init(final Injector injector) {
        super.init(injector);
        shape.get().setRadius(radius);
    }
}
