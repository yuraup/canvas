package shapes;

import java.awt.Point;
import java.awt.geom.Line2D;

public class GLine extends GShape {
   private int px, py; // 무브하려고 찍은 점

   public GLine() {
   }

   @Override
   public GShape clone() {
      return new GLine();
   }

   @Override
   public void setShape(int x1, int y1, int x2, int y2) {  // 항상 처음에 두 개의 점을 찍고 시작해야 움직일 수 있음
      this.shape = new Line2D.Double(x1, y1, x2, y2);
   }

   @Override
   public void resizePoint(int x2, int y2) { //drag
      Line2D line2D = (Line2D) shape;
      line2D.setLine(line2D.getX1(), line2D.getY1(), x2, y2);
   }

   public boolean onShape(Point p) {  //포인터 위에 점 있는지 
	      Line2D line2D = (Line2D) shape;
	      if (line2D.ptSegDist(p) < 5)
	         return true;
	      else
	         return false;
	   }

   @Override
   public void setPoint(int x, int y) { //움직이는 점의 원점을 잡자. 
      this.px = x;
      this.py = y;
   }

   @Override
   public void movePoint(int x, int y) {
      Line2D line2D = (Line2D) shape;
      line2D.setLine(line2D.getX1() + x - px, line2D.getY1() + y - py, line2D.getX2() + x - px,
            line2D.getY2() + y - py); //새 좌표로 위치 바꿈 
     
      this.px = x; //원점 다시 잡아야 누적되지 않음 
      this.py = y;

   }

}