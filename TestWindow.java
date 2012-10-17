import com.trolltech.qt.core.Qt;
import com.trolltech.qt.gui.*;

public class TestWindow extends QWidget {

	private final int WIDTH = 322,
		HEIGHT = 715;

	QFrame weightFrame,
	       tallFrame;

	QLabel weightText,
	       weightUnit;
	QSpinBox weightValue;
	QSlider weightSlider;

	QLabel tallText,
	       tallUnit;
	QSpinBox tallValue;
	QSlider tallSlider;

	QPushButton calculateButton;

	QLabel BMIText;
	QLCDNumber BMIValue;

	QFrame resultPicture;
	QLabel resultText;

	public TestWindow()
	{
		//獲取螢幕的寬高
		QDesktopWidget qdw = new QDesktopWidget();
		int screenWidth = qdw.width(),
		    screenHeight = qdw.height(),

		//計算主視窗的位置
		    x = (screenWidth - WIDTH) / 2,
		    y = (screenHeight - HEIGHT) / 2;

		//主視窗
		setWindowTitle("計算你的BMI值");
		setFixedSize(WIDTH, HEIGHT);
		move(x, y);
		show();

		//建立元件
		Init();
		//佈局
		Layout();
	}

	private void Init()
	{
		weightFrame = new QFrame();
		weightFrame.setFrameShape(QFrame.Shape.StyledPanel);
		weightFrame.setFrameShadow(QFrame.Shadow.Raised);

		tallFrame = new QFrame();
		tallFrame.setFrameShape(QFrame.Shape.StyledPanel);
		tallFrame.setFrameShadow(QFrame.Shadow.Raised);

		resultPicture = new QFrame();
		resultPicture.setFrameShape(QFrame.Shape.StyledPanel);
		resultPicture.setFrameShadow(QFrame.Shadow.Raised);
		resultPicture.setStyleSheet("background-image:url(images/man-question-mark.png)");
		resultPicture.setFixedWidth(300);
		resultPicture.setFixedHeight(420);

		weightText = new QLabel(tr("你的體重 : "), weightFrame);

		weightValue = new QSpinBox(weightFrame);
		weightValue.setValue(55);
		weightValue.setRange(25, 120);
		weightValue.setAlignment(Qt.AlignmentFlag.AlignRight);

		weightUnit = new QLabel(tr("公斤"), weightFrame);

		weightSlider = new QSlider(Qt.Orientation.Horizontal, weightFrame);
		weightSlider.setValue(55);
		weightSlider.setRange(25, 120);
		weightSlider.valueChanged.connect(weightValue, "setValue(int)");
		weightValue.valueChanged.connect(weightSlider, "setValue(int)");

		tallText = new QLabel(tr("你的身高 : "), tallFrame);

		tallValue = new QSpinBox(tallFrame);
		tallValue.setValue(160);
		tallValue.setRange(70, 200);
		tallValue.setAlignment(Qt.AlignmentFlag.AlignRight);

		tallUnit = new QLabel(tr("公分"), tallFrame);

		tallSlider = new QSlider(Qt.Orientation.Horizontal, tallFrame);
		tallSlider.setValue(160);
		tallSlider.setRange(70, 200);
		tallSlider.valueChanged.connect(tallValue, "setValue(int)");
		tallValue.valueChanged.connect(tallSlider, "setValue(int)");

		calculateButton = new QPushButton("算！");
		calculateButton.setDefault(true);
		calculateButton.clicked.connect(this, "calculate()");

		BMIText = new QLabel(tr("你的 BMI 值 : "));

		BMIValue = new QLCDNumber();
		BMIValue.setNumDigits(6);
		BMIValue.setSmallDecimalPoint(false);
		BMIValue.setMinimumHeight(25);
		BMIValue.setStyleSheet("background-color : black;font-weight : bold;");

		resultText = new QLabel(tr("快算算你的 BMI 吧！"));
	}

	private void Layout()
	{
		QVBoxLayout listLayout = new QVBoxLayout(),

			    weightFrameLayout = new QVBoxLayout(weightFrame),
			    tallFrameLayout = new QVBoxLayout(tallFrame);

		QHBoxLayout weightLayout = new QHBoxLayout(),
			    tallLayout = new QHBoxLayout(),

			    BMILayout = new QHBoxLayout();

		listLayout.addWidget(weightFrame);
		listLayout.addWidget(tallFrame);
		listLayout.addWidget(calculateButton, 1, Qt.AlignmentFlag.AlignHCenter);
		listLayout.addLayout(BMILayout);
		listLayout.addWidget(resultPicture);
		listLayout.addWidget(resultText, 1, Qt.AlignmentFlag.AlignHCenter);
		listLayout.addStretch();

		weightFrameLayout.addLayout(weightLayout);
		weightFrameLayout.addWidget(weightSlider);
		tallFrameLayout.addLayout(tallLayout);
		tallFrameLayout.addWidget(tallSlider);

		weightLayout.addWidget(weightText);
		weightLayout.addWidget(weightValue, 1, Qt.AlignmentFlag.AlignRight);
		weightLayout.addWidget(weightUnit);
		tallLayout.addWidget(tallText);
		tallLayout.addWidget(tallValue, 1, Qt.AlignmentFlag.AlignRight);
		tallLayout.addWidget(tallUnit);

		BMILayout.addWidget(BMIText);
		BMILayout.addWidget(BMIValue);

		setLayout(listLayout);
	}

	public static void main(String[] args)
	{
		QApplication.initialize(args);
		new TestWindow();
		QApplication.exec();
	}

	private void calculate()
	{
		double weight = weightValue.value(),
		       tall = tallValue.value(),
		       BMI = weight / Math.pow(tall / 100, 2);
		BMIValue.display(BMI);
		if(BMI < 18.5)
		{
			resultText.setText("「苗條」");
			resultPicture.setStyleSheet("background-image:url(images/thin.png)");
		}
		else if(18.5 <= BMI && BMI <= 30)
		{
			resultText.setText("「你很健康，請繼續保持！」");
			resultPicture.setStyleSheet("background-image:url(images/fit.png)");
		}
		else if(30 < BMI)
		{
			resultText.setText("「請委婉地叫我胖子好嗎?」");
			resultPicture.setStyleSheet("background-image:url(images/buxom.png)");
		}
	}
}
