import java.awt.Robot;
import java.awt.event.InputEvent;

public class clicker {
	public static void main(String[] args) {
		try {
			int h = 1;
			while (true) {
				Robot r = new Robot();
				int b = InputEvent.BUTTON1_DOWN_MASK;
				System.out.println("click = " + h++);
				r.mousePress(b);
				Thread.sleep(0);
				r.mouseRelease(b);
				Thread.sleep(3);
			}
		}catch (Exception e) {
			e.printStackTrace();
		}
	}
}