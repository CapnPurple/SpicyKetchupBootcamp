/* Copyright (c) 2017 FIRST. All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without modification,
 * are permitted (subject to the limitations in the disclaimer below) provided that
 * the following conditions are met:
 *
 * Redistributions of source code must retain the above copyright notice, this list
 * of conditions and the following disclaimer.
 *
 * Redistributions in binary form must reproduce the above copyright notice, this
 * list of conditions and the following disclaimer in the documentation and/or
 * other materials provided with the distribution.
 *
 * Neither the name of FIRST nor the names of its contributors may be used to endorse or
 * promote products derived from this software without specific prior written permission.
 *
 * NO EXPRESS OR IMPLIED LICENSES TO ANY PARTY'S PATENT RIGHTS ARE GRANTED BY THIS
 * LICENSE. THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS
 * "AS IS" AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO,
 * THE IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE
 * ARE DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT OWNER OR CONTRIBUTORS BE LIABLE
 * FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL
 * DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR
 * SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER
 * CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY,
 * OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE
 * OF THIS SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 */

package Testing;

import android.app.Activity;
import android.graphics.Color;
import android.view.View;

import com.qualcomm.hardware.rev.RevBlinkinLedDriver;
import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.ColorSensor;
import com.qualcomm.robotcore.hardware.DistanceSensor;
import com.qualcomm.robotcore.hardware.Servo;

import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.firstinspires.ftc.robotcore.external.navigation.DistanceUnit;

import java.util.Locale;

@TeleOp(name = "Distance Test", group = "Sensor")
public class colorDistanceTest extends LinearOpMode {

    private RevBlinkinLedDriver blinkinLedDriver;
    RevBlinkinLedDriver.BlinkinPattern pattern;
    ColorSensor sensorColor;
    DistanceSensor sensorDistance;
    public Servo goBilda = null;

    int r = 0;

    @Override
    public void runOpMode() {
        sensorColor = hardwareMap.get(ColorSensor.class, "colSensor");

        sensorDistance = hardwareMap.get(DistanceSensor.class, "colSensor");

        float hsvValues[] = {0F, 0F, 0F};

        final float values[] = hsvValues;

        final double SCALE_FACTOR = 255;

        int relativeLayoutId = hardwareMap.appContext.getResources().getIdentifier("RelativeLayout", "id", hardwareMap.appContext.getPackageName());
        final View relativeLayout = ((Activity) hardwareMap.appContext).findViewById(relativeLayoutId);

        goBilda = hardwareMap.get(Servo.class, "goBilda");

        goBilda.setPosition(0);

        blinkinLedDriver = hardwareMap.get(RevBlinkinLedDriver.class, "blinkin");
        pattern = RevBlinkinLedDriver.BlinkinPattern.BLACK;  //COLOR_WAVES_FOREST_PALETTE
        blinkinLedDriver.setPattern(pattern);

        waitForStart();

        while (opModeIsActive()) {
            Color.RGBToHSV((int) (sensorColor.red() * SCALE_FACTOR),
                    (int) (sensorColor.green() * SCALE_FACTOR),
                    (int) (sensorColor.blue() * SCALE_FACTOR),
                    hsvValues);

            telemetry.addData("Distance (cm)",
                    String.format(Locale.US, "%.02f", sensorDistance.getDistance(DistanceUnit.CM)));
            telemetry.addData("Alpha", sensorColor.alpha());
            telemetry.addData("Red  ", sensorColor.red());
            telemetry.addData("Green", sensorColor.green());
            telemetry.addData("Blue ", sensorColor.blue());
            telemetry.addData("Hue", hsvValues[0]);
            telemetry.addData("Rings Intaked", r);

            relativeLayout.post(new Runnable() {
                public void run() {
                    relativeLayout.setBackgroundColor(Color.HSVToColor(0xff, values));
                }
            });

            if(sensorDistance.getDistance(DistanceUnit.CM) < 1 && r < 3){
                r++;
                sleep(500);
            } else if (r == 1) {
                pattern = RevBlinkinLedDriver.BlinkinPattern.GREEN;
                blinkinLedDriver.setPattern(pattern);
            } else if (r == 2) {
                pattern = RevBlinkinLedDriver.BlinkinPattern.YELLOW;
                blinkinLedDriver.setPattern(pattern);
            } else if (r == 3){
                pattern = RevBlinkinLedDriver.BlinkinPattern.RED;
                blinkinLedDriver.setPattern(pattern);
            } else if (r > 3){
                pattern = RevBlinkinLedDriver.BlinkinPattern.TWINKLES_LAVA_PALETTE;
                blinkinLedDriver.setPattern(pattern);
            }

            if (gamepad1.a){
                goBilda.setPosition(1);
                sleep(300);
                goBilda.setPosition(0);
                sleep(300);

                pattern = RevBlinkinLedDriver.BlinkinPattern.BLACK;
                blinkinLedDriver.setPattern(pattern);
                r = 0;
            }

            telemetry.update();
        }

        relativeLayout.post(new Runnable() {
            public void run() {
                relativeLayout.setBackgroundColor(Color.WHITE);
            }
        });
    }
}
