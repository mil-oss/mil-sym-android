package sec.geo.shape;

import sec.geo.GeoPath;
import java.util.ArrayList;
import sec.geo.GeoPoint;
import sec.geo.ShapeObject;
import sec.geo.kml.KmlOptions.AltitudeMode;

public class Polyarc /* extends APath implements IArc */ {   //APath extedns AExtruusion

    private double minAltitudeMeters;
    private double maxAltitudeMeters;
    private ShapeObject shape;  //was shape
    protected double maxDistanceMeters;
    protected double flatnessDistanceMeters;

    protected int limit;
    private GeoPoint pivot;
    private double radiusMeters;
    private double leftAzimuthDegrees, rightAzimuthDegrees;
    protected AltitudeMode altitudeMode;

    protected final ArrayList<GeoPoint> points;

    public Polyarc() {
        points = new ArrayList<GeoPoint>();
        maxDistanceMeters = 100000;
        flatnessDistanceMeters = 1;
        limit = 4;
    }

    public void addPoint(GeoPoint point) {
        points.add(point);
        shapeChanged();
    }

    public void addPoints(ArrayList<GeoPoint> points) {
        this.points.addAll(points);
        shapeChanged();
    }

    //@Override
    public void setRadius(double radiusMeters) {
        this.radiusMeters = radiusMeters;
        shapeChanged();
    }

    //@Override
    public void setPivot(GeoPoint pivot) {
        this.pivot = pivot;
        shapeChanged();
    }

    public void setRightAzimuthDegrees(double rightAzimuthDegrees) {
        this.rightAzimuthDegrees = rightAzimuthDegrees;
        shapeChanged();
    }

    public void setLeftAzimuthDegrees(double leftAzimuthDegrees) {
        this.leftAzimuthDegrees = leftAzimuthDegrees;
        shapeChanged();
    }

    //@Override
    protected ShapeObject createShape() {
        GeoPath path = new GeoPath(maxDistanceMeters, flatnessDistanceMeters, limit);
        int n = points.size();
        //for (int i = 0; i < points.size(); i++) 
        for (int i = 0; i < n; i++) {
            GeoPoint point = points.get(i);
            if (i == 0) {
                path.moveTo(point);
            } else {
                path.lineTo(point);
            }
        }
        path.arcTo(pivot, radiusMeters * 2, radiusMeters * 2, leftAzimuthDegrees, rightAzimuthDegrees);
        path.closePath();
        return new ShapeObject(path);
    }

    protected void shapeChanged() {
        shape = null;
    }

    public ShapeObject getShape() {
        if (shape == null) {
            shape = createShape();
        }
        return shape;
    }

	//protected abstract Shape createShape();
    public double getMinAltitude() {
        return minAltitudeMeters;
    }

    public void setMinAltitude(double minAltitudeMeters) {
        this.minAltitudeMeters = minAltitudeMeters;
        shapeChanged();
    }

    public double getMaxAltitude() {
        return maxAltitudeMeters;
    }

    public void setMaxAltitude(double maxAltitudeMeters) {
        this.maxAltitudeMeters = maxAltitudeMeters;
        shapeChanged();
    }

    public void setMaxDistance(double maxDistanceMeters) {
        this.maxDistanceMeters = maxDistanceMeters;
        shapeChanged();
    }

    public void setFlatness(double flatnessDistanceMeters) {
        this.flatnessDistanceMeters = flatnessDistanceMeters;
        shapeChanged();
    }

    public void setLimit(int limit) {
        this.limit = limit;
        shapeChanged();
    }

    public AltitudeMode getAltitudeMode() {
        return altitudeMode;
    }

    public void setAltitudeMode(AltitudeMode altitudeMode) {
        this.altitudeMode = altitudeMode;
    }
}
