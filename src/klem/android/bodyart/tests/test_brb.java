package klem.android.bodyart.tests;

import android.graphics.Point;

public class test_brb {

  // compoute all point from center Point
  private Point startPoint;
  private Point centerPoint;
  private Point endPoint;
  private float distance = 250f;
  private float angle = 0f;

  public Point getCenterPoint() {
    return centerPoint;
  }


  public void setCenterPoint(Point centerPoint) {
    this.centerPoint = centerPoint;
    this.endPoint = computeEndPoint(centerPoint);
    this.startPoint = computeStartPoint(centerPoint);
    
  }

  /**
   * Compute the StartPoint from the centerPoint
   * 
   * @param centerPoint
   * @return
   */
  public Point computeStartPoint(Point centerPoint) {
    double cos = Math.cos(angle) * (distance / 2);
    double sin = Math.sin(angle) * (distance / 2);

    int y = (int) (centerPoint.x - cos);
    int x = (int) (centerPoint.y - sin);

    return new Point(x, y);
  }


  /**
   * Compute the endPoint from the startPoint
   * 
   * @param centerPoint
   * @return
   */
  public Point computeEndPoint(Point centerPoint) {

    double cos = Math.cos(angle) * (distance / 2);
    double sin = Math.sin(angle) * (distance / 2);

    int y = (int) (centerPoint.x + cos);
    int x = (int) (centerPoint.y + sin);

    return new Point(x, y);
  }

  /**
   * Sets the line length, triggers recalculation
   * 
   * @param length
   */
  public void setDistance(float distance) {
    this.distance = distance;
    this.startPoint = computeStartPoint(centerPoint);
    this.endPoint = computeEndPoint(centerPoint);
  }

  /**
   * Sets the slope angle, triggers recalculation
   * 
   * @param angle
   */
  public void setAngle(float angle) {
    this.angle = angle;
    System.out.println("Angle :" + angle);
    this.startPoint = computeStartPoint(centerPoint);
    this.endPoint = computeEndPoint(centerPoint);
  }


  public Point getStartPoint() {
    return startPoint;
  }

  public Point getEndPoint() {
    return endPoint;
  }

  public static void main(String[] args) {
    test_brb b = new test_brb();
    Point center = new Point(10, 10);

    b.computeStartPoint(center);
    b.computeEndPoint(center);
  }

}
