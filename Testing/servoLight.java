package Testing;

import com.qualcomm.hardware.rev.RevBlinkinLedDriver;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.util.ElapsedTime;
import org.firstinspires.ftc.robotcore.external.Telemetry;

@Autonomous(group = "drive")
public class servoLight extends LinearOpMode {

    public Servo goBilda = null;
    private ElapsedTime runtime = new ElapsedTime();
    private DcMotor leftDrive = null;
    private DcMotor rightDrive = null;
    private RevBlinkinLedDriver blinkinLedDriver;
    RevBlinkinLedDriver.BlinkinPattern pattern;
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
        if (opModeIsActive()) {

            if (opModeIsActive()) {
                if (goBilda.getPosition() < .5) {
                    pattern = RevBlinkinLedDriver.BlinkinPattern.SHOT_RED;
                    blinkinLedDriver.setPattern(pattern);
                }
                else if (goBilda.getPosition() >= .5) {
                    pattern = RevBlinkinLedDriver.BlinkinPattern.DARK_BLUE;
                    blinkinLedDriver.setPattern(pattern);
                }


                goBilda.setPosition(0);
                telemetry.addData("Servo Position", goBilda.getPosition());
                telemetry.update();
                pattern = RevBlinkinLedDriver.BlinkinPattern.SHOT_RED;
                blinkinLedDriver.setPattern(pattern);
                sleep(500);
                goBilda.setPosition(1);
                pattern = RevBlinkinLedDriver.BlinkinPattern.DARK_BLUE;
                blinkinLedDriver.setPattern(pattern);
                telemetry.addData("Servo Position", goBilda.getPosition());
                telemetry.update();
                sleep(500);
                goBilda.setPosition(0);
                telemetry.addData("Servo Position", goBilda.getPosition());
                telemetry.update();
                pattern = RevBlinkinLedDriver.BlinkinPattern.SHOT_RED;
                blinkinLedDriver.setPattern(pattern);
                sleep(500);
                goBilda.setPosition(1);
                pattern = RevBlinkinLedDriver.BlinkinPattern.DARK_BLUE;
                blinkinLedDriver.setPattern(pattern);
                telemetry.addData("Servo Position", goBilda.getPosition());
                telemetry.update();
                sleep(500);
                goBilda.setPosition(0);
                pattern = RevBlinkinLedDriver.BlinkinPattern.SHOT_RED;
                blinkinLedDriver.setPattern(pattern);
                telemetry.addData("Servo Position", goBilda.getPosition());
                telemetry.update();
                sleep(500);
                goBilda.setPosition(1);
                pattern = RevBlinkinLedDriver.BlinkinPattern.DARK_BLUE;
                blinkinLedDriver.setPattern(pattern);
                telemetry.addData("Servo Position", goBilda.getPosition());
                telemetry.update();
                sleep(500);
                goBilda.setPosition(0);
                pattern = RevBlinkinLedDriver.BlinkinPattern.SHOT_RED;
                blinkinLedDriver.setPattern(pattern);
                telemetry.addData("Servo Position", goBilda.getPosition());
                telemetry.update();
                sleep(500);
                goBilda.setPosition(1);
                pattern = RevBlinkinLedDriver.BlinkinPattern.DARK_BLUE;
                blinkinLedDriver.setPattern(pattern);
                telemetry.addData("Servo Position", goBilda.getPosition());
                telemetry.update();
                sleep(500);

            }

        }


    }
}
