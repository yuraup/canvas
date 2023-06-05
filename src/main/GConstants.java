package main;

import shapes.GLine;
import shapes.GOval;
import shapes.GPolygon;
import shapes.GRectangle;
import shapes.GSelect;
import shapes.GShape;

public class GConstants {

	public class CMainFrame {
		static final int x = 200;
		static final int y = 100;
		static final int w = 400;
		static final int h = 600;
	}

	public enum EUserAction {
		e2Point, eNPoint
	}

	public enum EAnchors {
		NW, NN, NE, EE, SE, SS, SW, WW, RR, // rotate
		MM // move
	}

	public enum EShape {
		// 객체를 달아서 drawing의 코드 개선

		eSelect("Select", new GSelect(), EUserAction.e2Point),
		eRectangle("Rectangle", new GRectangle(), EUserAction.e2Point), // 변수 이름을 0번 이자 스트링 타입 가능
		eOval("Oval", new GOval(), EUserAction.e2Point), eLine("Line", new GLine(), EUserAction.e2Point),
		ePolygon("Polygon", new GPolygon(), EUserAction.eNPoint); // user가 정의하는 순서를
		// 여기에 커서 집어넣어서 코드 품질 향상하기
		// 가진 집합을 정의

		private String name;
		private GShape gShape;
		private EUserAction eUserAction;

		private EShape(String name, GShape gShape, EUserAction eUserAction) {
			this.name = name;
			this.gShape = gShape;
			this.eUserAction = eUserAction;
		}

		public String getName() {
			return this.name;
		}

		public GShape getGShape() { // 같은 주소를 계속 가지고 오고 잇음 .새로 만들어야 하는데 -> 자기 복제 하자
			return this.gShape;
		}

		public EUserAction getEUserAction() {
			return this.eUserAction;
		}
	}
}
