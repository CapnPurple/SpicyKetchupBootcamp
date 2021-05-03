package Testing;

import com.qualcomm.hardware.rev.RevBlinkinLedDriver;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.util.ElapsedTime;

@Autonomous(group = "drive")
public class timedColor extends LinearOpMode {

    private ElapsedTime runtime = new ElapsedTime();
    private DcMotor leftDrive = null;
    private DcMotor rightDrive = null;
    private RevBlinkinLedDriver blinkinLedDriver;
    RevBlinkinLedDriver.BlinkinPattern pattern;
    public Servo goBilda = null;


    @Override
    public void runOpMode() throws InterruptedException {

        goBilda = hardwareMap.get(Servo.class, "goBilda");
        goBilda.setPosition(0);

        blinkinLedDriver = hardwareMap.get(RevBlinkinLedDriver.class, "blinkin");
        pattern = RevBlinkinLedDriver.BlinkinPattern.BLACK;  //COLOR_WAVES_FOREST_PALETTE
        blinkinLedDriver.setPattern(pattern);

        telemetry.addData(">", "Press Play to start op mode");
        // telemetry.addData("tihng",exp);
        telemetry.update();

        waitForStart();
        while (opModeIsActive()) {
            if (runtime.seconds() < 10){
                pattern = RevBlinkinLedDriver.BlinkinPattern.RED;  //COLOR_WAVES_FOREST_PALETTE
                blinkinLedDriver.setPattern(pattern);
            } else if (runtime.seconds() >= 10 && runtime.seconds() < 20){
                pattern = RevBlinkinLedDriver.BlinkinPattern.ORANGE;  //COLOR_WAVES_FOREST_PALETTE
                blinkinLedDriver.setPattern(pattern);
            } else if (runtime.seconds() >= 20 && runtime.seconds() < 30){
                pattern = RevBlinkinLedDriver.BlinkinPattern.YELLOW;  //COLOR_WAVES_FOREST_PALETTE
                blinkinLedDriver.setPattern(pattern);
            } else if (runtime.seconds() >= 30){
                pattern = RevBlinkinLedDriver.BlinkinPattern.GREEN;  //COLOR_WAVES_FOREST_PALETTE
                blinkinLedDriver.setPattern(pattern);
            }
        }
    }
}

